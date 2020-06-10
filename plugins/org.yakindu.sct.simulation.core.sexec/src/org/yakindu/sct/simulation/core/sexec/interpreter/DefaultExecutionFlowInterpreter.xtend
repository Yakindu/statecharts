/**
 * Copyright (c) 2013-2017 committers of YAKINDU and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 * 	committers of YAKINDU - initial API and implementation
 * 
 */
package org.yakindu.sct.simulation.core.sexec.interpreter

import com.google.inject.Inject
import java.util.LinkedList
import java.util.List
import java.util.Map
import java.util.Queue
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.xtend.lib.annotations.Data
import org.yakindu.base.expressions.interpreter.IExpressionInterpreter
import org.yakindu.base.types.Direction
import org.yakindu.sct.model.sexec.Call
import org.yakindu.sct.model.sexec.Check
import org.yakindu.sct.model.sexec.EnterState
import org.yakindu.sct.model.sexec.Execution
import org.yakindu.sct.model.sexec.ExecutionFlow
import org.yakindu.sct.model.sexec.ExecutionState
import org.yakindu.sct.model.sexec.ExitState
import org.yakindu.sct.model.sexec.HistoryEntry
import org.yakindu.sct.model.sexec.If
import org.yakindu.sct.model.sexec.SaveHistory
import org.yakindu.sct.model.sexec.ScheduleTimeEvent
import org.yakindu.sct.model.sexec.Sequence
import org.yakindu.sct.model.sexec.SexecFactory
import org.yakindu.sct.model.sexec.StateSwitch
import org.yakindu.sct.model.sexec.Step
import org.yakindu.sct.model.sexec.Trace
import org.yakindu.sct.model.sexec.UnscheduleTimeEvent
import org.yakindu.sct.model.sexec.extensions.StateVectorExtensions
import org.yakindu.sct.model.sexec.transformation.SexecExtensions
import org.yakindu.sct.model.sgraph.FinalState
import org.yakindu.sct.model.sgraph.RegularState
import org.yakindu.sct.model.sgraph.Statechart
import org.yakindu.sct.model.sruntime.ExecutionContext
import org.yakindu.sct.model.sruntime.ExecutionEvent
import org.yakindu.sct.model.stext.lib.StatechartAnnotations
import org.yakindu.sct.simulation.core.engine.scheduling.ITimeTaskScheduler
import org.yakindu.sct.simulation.core.engine.scheduling.ITimeTaskScheduler.TimeTask
import org.yakindu.sct.simulation.core.sexec.container.EventDrivenSimulationEngine.EventDrivenCycleAdapter
import org.yakindu.sct.simulation.core.util.ExecutionContextExtensions

/**
 * 
 * @author andreas muelder - Initial contribution and API
 * @author axel terfloth - minimized changes on execution context
 * 
 */
class DefaultExecutionFlowInterpreter implements IExecutionFlowInterpreter, IEventRaiser {

	@Data static class Event {

		public ExecutionEvent event;
		public Object value;

		new(ExecutionEvent ev, Object value) {
			this.event = ev
			this.value = value
		}
	}

	protected Queue<Event> internalEventQueue = new LinkedList<Event>()
	protected Queue<Event> inEventQueue = new LinkedList<Event>()

	@Inject
	protected IExpressionInterpreter statementInterpreter
	@Inject
	ITimeTaskScheduler timingService
	@Inject extension SexecExtensions
	@Inject(optional=true)
	ITraceStepInterpreter traceInterpreter
	@Inject protected extension ExecutionContextExtensions
	@Inject
	protected StateVectorExtensions stateVectorExtensions;
	@Inject
	protected extension StatechartAnnotations

	protected ExecutionFlow flow
	protected ExecutionContext executionContext
	protected ExecutionState[] activeStateConfiguration
	protected Map<Integer, ExecutionState> historyStateConfiguration
	protected List<Step> executionStack
	protected int activeStateIndex
	protected boolean useInternalEventQueue
	protected boolean useInEventQueue

	protected boolean useSuperStep
	protected boolean stateVectorChanged

	protected boolean isRunning = false

	protected static final Trace beginRunCycleTrace = SexecFactory.eINSTANCE.createTraceBeginRunCycle
	protected static final Trace endRunCycleTrace = SexecFactory.eINSTANCE.createTraceEndRunCycle

	override initialize(ExecutionFlow flow, ExecutionContext context) {
		initialize(flow, context, false)
	}

	override initialize(ExecutionFlow flow, ExecutionContext context, boolean useInternalEventQueue) {
		this.flow = flow
		executionContext = context
		executionStack = newLinkedList()
		activeStateConfiguration = newArrayOfSize(flow.stateVector.size)
		activeStateIndex = 0
		historyStateConfiguration = newHashMap()
		
		this.useInternalEventQueue = useInternalEventQueue

		useInEventQueue = (flow.sourceElement as Statechart).isEventDriven
		useSuperStep = (flow.sourceElement as Statechart).isSuperStep

		if (!executionContext.snapshot) {
			flow.staticInitSequence.scheduleAndRun
			flow.initSequence.scheduleAndRun
		}
	}

	override enter() {
		if (!executionContext.snapshot) {
			isRunning = true
			flow.enterSequences?.defaultSequence?.scheduleAndRun
			isRunning = false
		}
		else {
			executionContext.activeStates.filter(RegularState).forEach [ state |
				activeStateConfiguration.set(state.toExecutionState.stateVector.offset, state.toExecutionState)
				// schedule all time events
				state.toExecutionState.enterSequences?.forEach[executeAfterRestore]
			]
			flow.enterSequences?.forEach[executeAfterRestore]
		}
	}

	def dispatch protected void executeAfterRestore(Step it) {
		// fall back
	}

	def dispatch protected void executeAfterRestore(Sequence it) {
		steps.forEach[executeAfterRestore]
	}

	def dispatch protected void executeAfterRestore(Call it) {
		step.executeAfterRestore
	}

	def dispatch protected void executeAfterRestore(ScheduleTimeEvent it) {
		scheduleAndRun
	}

	def ExecutionState toExecutionState(RegularState state) {
		return flow.eAllContents.filter(ExecutionState).findFirst [
			EcoreUtil.equals(sourceElement, state)
		]
	}

	override runCycle() {
		// TODO Should not care about cycle adapter here - move to simulation engine where it is defined.  
		val cycleAdapter = EcoreUtil.getExistingAdapter(executionContext,
			EventDrivenCycleAdapter) as EventDrivenCycleAdapter
		try {
			if (cycleAdapter !== null)
				executionContext.eAdapters.remove(cycleAdapter)

			if (isRunning) return;
			isRunning = true;

			executionContext.clearOutEvents

			var Event event = internalEventQueue.poll ?: inEventQueue.poll
			do {
				traceInterpreter.evaluate(beginRunCycleTrace, executionContext)
				// activate an event if there is one
				if (event !== null) {
					event.event.value = event.value
					event.event.raised = true
					event = null
				}
				// perform a run to completion step
				if (useSuperStep) {
					superStepLoop([rtcStep])
				} else {
					rtcStep
				}
				executionContext.clearLocalAndInEvents

				// get next event if available
				event = internalEventQueue.poll ?: inEventQueue.poll

				traceInterpreter.evaluate(endRunCycleTrace, executionContext)
			} while (event !== null)
		} finally {
			isRunning = false;

			if (cycleAdapter !== null)
				executionContext.eAdapters.add(cycleAdapter)
			
		}
	}

	def superStepLoop(()=>void microStep) {
		do {
			stateVectorChanged = false
			microStep.apply
		} while (stateVectorChanged && !Thread.currentThread.isInterrupted)
	}

	def rtcStep() {
		activeStateIndex = 0
		if(executionContext.executedElements.size > 0) executionContext.executedElements.clear
		while (activeStateIndex < activeStateConfiguration.size) {
			var state = activeStateConfiguration.get(activeStateIndex)
			state?.reactSequence?.scheduleAndRun
			activeStateIndex = activeStateIndex + 1
		}
	}

	override exit() {
		isRunning = true
		flow.exitSequence.scheduleAndRun
		isRunning = false
	}

	override tearDown() {
	}

	def scheduleAndRun(Step step) {
		schedule(step)
		run
	}

	def run() {
		while (!executionStack.empty) {
			var head = executionStack.head
			executionStack.remove(head)
			head.execute
		}
	}

	def schedule(Step step) {
		executionStack.add(0, step)
	}

	def dispatch Object execute(Call call) {
		call.step.schedule
		null
	}

	def dispatch Object execute(Step step) {
	}

	def dispatch Object execute(Trace trace) {
		traceInterpreter?.evaluate(trace, executionContext)
		null
	}

	def dispatch Object execute(Check check) {
		if (check.condition === null)
			return true
		return statementInterpreter.evaluate(check.condition, executionContext)

	}

	def dispatch Object execute(EnterState enterState) {
		activeStateConfiguration.set(enterState.state.stateVector.offset, enterState.state)
		executionContext.activeStates += enterState.state.sourceElement as RegularState
		activeStateIndex = enterState.state.stateVector.offset // mark all state vector elements up to this as processed ...
		stateVectorChanged = true
		null
	}

	def dispatch Object execute(Execution execution) {
		statementInterpreter.evaluate(execution.statement, executionContext)
	}

	def dispatch Object execute(ExitState exitState) {
		activeStateConfiguration.set(exitState.state.stateVector.offset, null)
		var activeStates = executionContext.activeStates.filter[EcoreUtil::equals(it, exitState.state.sourceElement)]
		executionContext.activeStates.removeAll(activeStates)
		null
	}

	def dispatch Object execute(If ifStep) {
		var check = execute(ifStep.check)
		if (check as Boolean) {
			ifStep.thenStep.schedule
		} else if (ifStep.elseStep !== null) {
			ifStep.elseStep.schedule
		}
		null
	}

	def dispatch Object execute(Sequence sequence) {
		sequence.steps.reverseView.forEach[schedule]
		null
	}

	def dispatch Object execute(SaveHistory action) {
		var region = action.region
		historyStateConfiguration.put(region.historyVector.offset,
			activeStateConfiguration.get(region.stateVector.offset))
	}

	def dispatch Object execute(HistoryEntry entry) {
		if (historyStateConfiguration.get(entry.region.historyVector.offset) !== null) {
			entry.historyStep?.execute
		} else {
			entry.initialStep?.execute
		}
		null
	}

	def dispatch Object execute(StateSwitch stateSwitch) {
		val historyRegion = stateSwitch.historyRegion
		if (historyRegion !== null) {
			val historyState = historyStateConfiguration.get(historyRegion.historyVector.offset)
			stateSwitch.cases.filter[it.state == historyState].forEach[step.schedule]
		} else {
			stateSwitch.cases.filter[activeStateConfiguration.contains(state)].forEach[step.schedule]
		}
		null
	}

	def dispatch Object execute(ScheduleTimeEvent scheduleTimeEvent) {
		val timeEvent = scheduleTimeEvent.timeEvent
		val duration = statementInterpreter.evaluate(scheduleTimeEvent.timeValue, executionContext)
		timingService.scheduleTimeTask(new TimeTask(timeEvent.name, [
			executionContext.getEvent(timeEvent.name).raised = true
		]), timeEvent.periodic, duration as Long)
		null
	}

	def dispatch Object execute(UnscheduleTimeEvent timeEvent) {
		timingService.unscheduleTimeTask(timeEvent.timeEvent.name)
		null
	}

	override raise(ExecutionEvent ev, Object value) {
		if (useInternalEventQueue && ev.direction == Direction::LOCAL) {
			internalEventQueue.add(new Event(ev, value));
		} else if (useInEventQueue && ev.direction == Direction::IN) {
			inEventQueue.add(new Event(ev, value));
			runCycle
		} else {
			ev.value = value
			ev.raised = true
		}
	}

	override boolean isActive() {
		return executionContext.getAllActiveStates().size > 0
	}

	override boolean isFinal() {
		var List<ExecutionState>[] list = stateVectorExtensions.finalStateImpactVector(flow);
		var boolean isCompletlyCovered = stateVectorExtensions.isCompletelyCovered(list);
		if (!isCompletlyCovered) {
			return false;
		} else {
			var List<RegularState> activeStates = executionContext.getAllActiveStates();
			if (activeStates.isEmpty) {
				return false;
			}
			for (RegularState regularState : activeStates) {
				if (!(regularState instanceof FinalState)) {
					return false;
				}
			}
			return true;
		}
	}
	
	override isStateActive(String stateName) {
		return if(stateName === null){
			activeStateConfiguration.exists[it === null]
		} else
			activeStateConfiguration.filterNull.exists[(it.sourceElement as RegularState).name == stateName]
	}

	override getExecutionContext() {
		executionContext
	}
	
	override getExecutionFlow() {
		flow
	}
	
	
	
}
