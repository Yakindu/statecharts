/** 
 * Copyright (c) 2015-2019 committers of YAKINDU and others. 
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0 
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/epl-v10.html 
 * Contributors:
 * committers of YAKINDU - initial API and implementation
 *
*/
package org.yakindu.sct.model.sexec.transformation

import com.google.inject.Inject
import org.eclipse.xtext.EcoreUtil2
import org.yakindu.base.expressions.expressions.BoolLiteral
import org.yakindu.base.expressions.expressions.PrimitiveValueExpression
import org.yakindu.sct.model.sexec.Check
import org.yakindu.sct.model.sexec.ExecutionFlow
import org.yakindu.sct.model.sexec.ExecutionNode
import org.yakindu.sct.model.sexec.Sequence
import org.yakindu.sct.model.sexec.StateVector
import org.yakindu.sct.model.sexec.Step
import org.yakindu.sct.model.sgraph.Choice
import org.yakindu.sct.model.sgraph.Entry
import org.yakindu.sct.model.sgraph.EntryKind
import org.yakindu.sct.model.sgraph.Exit
import org.yakindu.sct.model.sgraph.FinalState
import org.yakindu.sct.model.sgraph.Region
import org.yakindu.sct.model.sgraph.RegularState
import org.yakindu.sct.model.sgraph.State
import org.yakindu.sct.model.sgraph.Statechart
import org.yakindu.sct.model.sgraph.Synchronization
import org.yakindu.sct.model.sgraph.Transition
import org.yakindu.sct.model.stext.stext.DefaultTrigger
import org.yakindu.sct.model.stext.stext.ExitPointSpec
import org.yakindu.sct.model.stext.lib.StatechartAnnotations

class ReactionBuilder {
	@Inject extension SexecElementMapping mapping
	@Inject extension SexecExtensions sexec
	@Inject extension SgraphExtensions sgraph
	@Inject extension StatechartExtensions sct
	@Inject extension TraceExtensions trace
	@Inject extension BehaviorMapping behaviorMapping
	@Inject extension StatechartAnnotations
	
	def definePseudoStateReactions(ExecutionFlow flow, Statechart sc) {
		
		sc.allChoices().forEach( choice | choice.defineReaction() )
		sc.allSynchronizations().forEach( sync | sync.defineReaction() )
		sc.allExits().forEach( exit | exit.defineReaction() )
	}
	

	def Sequence defineReaction(Choice choice) {
	
		val execChoice = choice.create
		
		// move the default transition to the end of the reaction list
		val defaultTransition = choice.outgoingTransitions.filter( t | t.trigger === null || t.trigger instanceof DefaultTrigger ).head
		if ( defaultTransition !== null ) {
			val defaultReaction = defaultTransition.create		
			execChoice.reactions.move(execChoice.reactions.size -1, defaultReaction)
		}
		// TODO: raise an error if no default exists 
		
		val stateReaction = execChoice.createReactionSequence(null)
		execChoice.reactSequence.steps.addAll(stateReaction.steps)

		execChoice.reactSequence.name = 'react'
		execChoice.reactSequence.comment = 'The reactions of state ' + choice.name + '.'
		
		if ( trace.addTraceSteps ) execChoice.reactSequence.steps.add(0,choice.create.newTraceNodeExecuted)
		
		return execChoice.reactSequence
	}	
	

	def Sequence defineReaction(Synchronization sync) {
	
		val execSync = sync.create
		
		// move the default transition to the end of the reaction list
		val _default_ = execSync.reactions.head
		
		execSync.reactSequence.steps.addAll(_default_.effect)

		execSync.reactSequence.name = 'react'
		execSync.reactSequence.comment = 'The reactions of state ' + sync.name + '.'
		
		if ( trace.addTraceSteps ) execSync.reactSequence.steps.add(0,sync.create.newTraceNodeExecuted)
		
		return execSync.reactSequence
	}	
	

	def Sequence defineReaction(Exit it) {
	
		val execExit = it.create
		val realName = if (name.empty) 'default' else name 
				
		execExit.reactSequence.name = 'react'
		execExit.reactSequence.comment = 'The reactions of exit ' + realName + '.'
		
		val parentState = it.parentRegion.composite as State
	
		// find a transition that explicitly handles the exit 
		var exitTrans = 	parentState.outgoingTransitions.filter[ t | t.explicitlyHandlesExit(realName)].head
		// or choose the default exit transition
		if (exitTrans === null) exitTrans = parentState.defaultExitTransition
		
		if (exitTrans !== null) {
			val exitReaction = exitTrans.create
			execExit.reactSequence.steps.add(exitReaction.effect.newCall)
		}
		
		if ( trace.addTraceSteps ) execExit.reactSequence.steps.add(0,it.create.newTraceNodeExecuted)
		
		return execExit.reactSequence
	}
	
	/**
	 * Checks if a transition explicitly handles the specified exit. A transition is handling an exit 
	 * if it has neither trigger nor guard and if the specified exit point is listed as a transition property or if no 
	 * exit point is specified for the case of the exit named 'default'.
	 */
	def explicitlyHandlesExit(Transition it, String exitName) {
		val exits = exitPoints
		return 
			   hasNoTrigger 
			&& (('default'.equals(exitName) && exits.nullOrEmpty) 
				|| exits.contains(exitName))
	}
	
	/**
	 * Returns the transition that handles the default exit for a state if it exists or null if not.
	 * The default transition is that one that explicitly handles 'default'.
	 */
	def defaultExitTransition(State it) {
		outgoingTransitions.filter[ t | t.hasNoTrigger && t.explicitlyHandlesExit('default')].head
	}
	
	/**
	 * Returns the names of all exit points specified by a transition.
	 */
	def exitPoints(Transition it) {
		 properties.filter(ExitPointSpec).map[ eps | eps.exitpoint].toList
	}
	
	
	def protected hasNoTrigger(Transition t) {
		return t.trigger === null && !(t.target instanceof Synchronization)
	}

	def Sequence createReactionSequence(ExecutionNode state, Step localStep) {	
		val cycle = sexec.factory.createSequence
		cycle.name = "react"
		
		var localSteps = sexec.factory.createSequence		
		if (localStep !== null) localSteps.steps += localStep
		if (localSteps.steps.empty) localSteps = null
				
		val transitionReactions = state.reactions.filter(r | r.transition && ! r.unchecked ).toList
		val transitionStep = transitionReactions.reverseView.fold(localSteps as Step, [s, reaction | {
				var ifStep = sexec.factory.createIf
				ifStep.check = reaction.check.newRef		
				ifStep.thenStep = reaction.effect.newCall
				ifStep.elseStep = s
				ifStep as Step
			}])

		if (transitionStep !== null) cycle.steps.add(transitionStep)		
		else if (localSteps!== null) cycle.steps.add(localSteps)
		
		return cycle
	}
	
	def ExecutionFlow defineEntryReactions(Statechart statechart, ExecutionFlow r) {
		statechart.allEntries.forEach(e|e.defineReaction)
		return r
	}
	
	def defineReaction(Entry e) {

		// first get the mapped control flow element for the entry
		val execEntry = e.create
		
		// if the entry defines a transition then we will derive the entry transition sequence
		var Sequence entryTransSeq = e.createEntrySequence
		
		// we add behavior to the already created react sequence from defineStateEnterSequence(Entry) 
		val seq = execEntry.reactSequence
		
		if ( trace.addTraceSteps ) seq.steps.add(0,execEntry.newTraceNodeExecuted)
		
		if (e.kind == EntryKind::INITIAL) {			
			if (entryTransSeq !== null) seq.steps += entryTransSeq
			
		} else if (e.kind == EntryKind::SHALLOW_HISTORY) {
			val entryStep = sexec.factory.createHistoryEntry
			entryStep.name = "HistoryEntry"
			entryStep.comment = "Enter the region with shallow history"
			entryStep.deep = false
			entryStep.region = (e.eContainer as Region).create
			
			// if history does not have outgoing transition => take the default entry as fall-back
			if (entryTransSeq === null) entryTransSeq = e.parentRegion.entry.createEntrySequence
			
			if (entryTransSeq !== null) entryStep.initialStep = entryTransSeq
			
			entryStep.historyStep = (e.eContainer as Region).create.shallowEnterSequence.newCall
			
			seq.steps += entryStep
		} else if (e.kind == EntryKind::DEEP_HISTORY) {
			val entryStep = sexec.factory.createHistoryEntry
			entryStep.name = "HistoryEntry"
			entryStep.comment = "Enter the region with deep history"
			entryStep.region = (e.eContainer as Region).create
			entryStep.deep = true
			
			// if history does not have outgoing transition => take the default entry as fall-back
			if (entryTransSeq === null) entryTransSeq = e.parentRegion.entry.createEntrySequence
			
			if (entryTransSeq !== null) entryStep.initialStep = entryTransSeq
			
			entryStep.historyStep =  (e.eContainer as Region).create.deepEnterSequence.newCall

			seq.steps += entryStep
		}
	}
	
	def protected createEntrySequence(Entry e) {
		var Sequence entryTransSeq = null
		val entryTransitionEffect = e?.transition?.effect		
		val target = e.target.create
		val targetEnterSequence = if (target !== null && e.outgoingTransitions.size > 0) { e.outgoingTransitions.mapToStateConfigurationEnterSequence } else null
			
		if ( entryTransitionEffect !== null || targetEnterSequence !== null) {
			entryTransSeq = sexecFactory.createSequence
			if (entryTransitionEffect !== null) {
				entryTransSeq.steps += entryTransitionEffect.mapEffect	
			}
			if (targetEnterSequence !== null) {
				entryTransSeq.steps += targetEnterSequence
			}
		}
		entryTransSeq
	}
}
