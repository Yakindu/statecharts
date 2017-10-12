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
import com.google.inject.Singleton
import java.util.LinkedList
import java.util.List
import java.util.Map
import java.util.Queue
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.xtend.lib.annotations.Data
import org.yakindu.base.expressions.interpreter.IExpressionInterpreter
import org.yakindu.base.types.typesystem.ITypeValueProvider
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
import org.yakindu.sct.model.sexec.StateSwitch
import org.yakindu.sct.model.sexec.Step
import org.yakindu.sct.model.sexec.Trace
import org.yakindu.sct.model.sexec.UnscheduleTimeEvent
import org.yakindu.sct.model.sexec.extensions.StateVectorExtensions
import org.yakindu.sct.model.sexec.transformation.SexecExtensions
import org.yakindu.sct.model.sgraph.FinalState
import org.yakindu.sct.model.sgraph.RegularState
import org.yakindu.sct.model.sruntime.EventDirection
import org.yakindu.sct.model.sruntime.ExecutionContext
import org.yakindu.sct.model.sruntime.ExecutionEvent
import org.yakindu.sct.model.stext.lib.StatechartAnnotations
import org.yakindu.sct.simulation.core.engine.scheduling.ITimeTaskScheduler
import org.yakindu.sct.simulation.core.engine.scheduling.ITimeTaskScheduler.TimeTask
import org.yakindu.sct.simulation.core.util.ExecutionContextExtensions

/**
 * 
 * @author andreas muelder - Initial contribution and API
 * @author axel terfloth - minimized changes on execution context
 * 
 */
@Singleton
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
	@Inject
	extension ITypeValueProvider

	protected ExecutionFlow flow
	protected ExecutionContext executionContext
	protected ExecutionState[] activeStateConfiguration
	protected Map<Integer, ExecutionState> historyStateConfiguration
	protected List<Step> executionStack
	protected int activeStateIndex
	protected boolean useInternalEventQueue

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

		if (!executionContext.snapshot) {
			flow.staticInitSequence.scheduleAndRun
			flow.initSequence.scheduleAndRun
		}
	}

	override enter() {
		if (!executionContext.snapshot)
			flow.enterSequences?.defaultSequence?.scheduleAndRun
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
		var Event event = null
		do {
			// activate an event if there is one
			if (event !== null) {
				event.event.raised = true
				event.event.value = event.value
				event = null
			}
			// perform a run to completion step
			rtcStep
			// get next event if available
			if(! internalEventQueue.empty) event = internalEventQueue.poll
		} while (event !== null)
	}

	def rtcStep() {
		activeStateIndex = 0
		if(executionContext.executedElements.size > 0) executionContext.executedElements.clear
		executionContext.clearOutEvents
		while (activeStateIndex < activeStateConfiguration.size) {
			var state = activeStateConfiguration.get(activeStateIndex)
			state?.reactSequence?.scheduleAndRun
			activeStateIndex = activeStateIndex + 1
		}
		executionContext.clearLocalAndInEvents
	}

	def protected clearLocalAndInEvents(ExecutionContext executionContext) {
		executionContext.allEvents.filter[direction == EventDirection.IN || direction == EventDirection.LOCAL].forEach [
			if (raised) {
				raised = false;
				value = if(type !== null) type.defaultValue else null
			}
		]
	}

	override exit() {
		flow.exitSequence.scheduleAndRun
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
		if (useInternalEventQueue) {
			internalEventQueue.add(new Event(ev, value));

		} else {
			ev.raised = true
			ev.value = value
		}
	}

	override boolean isActive() {
		var List<RegularState> activeStates = executionContext.getAllActiveStates()

		for (RegularState regularState : activeStates) {
			if (!(regularState instanceof FinalState)) {
				return true;
			}
		}
		return false;
	}

	override boolean isFinal() {
		var List<ExecutionState>[] list = stateVectorExtensions.finalStateImpactVector(flow);
		var boolean isCompletlyCovered = stateVectorExtensions.isCompletelyCovered(list);
		if (!isCompletlyCovered) {
			return false;
		} else {
			var List<RegularState> activeStates = executionContext.getAllActiveStates();
			for (RegularState regularState : activeStates) {
				if (!(regularState instanceof FinalState)) {
					return false;
				}
			}
			return true;
		}
	}
}
