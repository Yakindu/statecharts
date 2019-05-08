/** 
 * Copyright (c) 2019 committers of YAKINDU and others. 
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0 
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/epl-v10.html 
 * Contributors:
 * committers of YAKINDU - initial API and implementation
 *
*/
package org.yakindu.sct.model.sequencer.operations

import com.google.inject.Inject
import com.google.inject.Singleton
import java.util.List
import org.yakindu.base.expressions.util.ExpressionBuilder
import org.yakindu.base.types.Expression
import org.yakindu.base.types.TypeBuilder
import org.yakindu.sct.model.sequencer.expressions.EffectToExpression
import org.yakindu.sct.model.sequencer.types.RegionType
import org.yakindu.sct.model.sequencer.types.StatemachineProperties
import org.yakindu.sct.model.sequencer.types.StatemachinePublic
import org.yakindu.sct.model.sequencer.util.SgraphExtensions
import org.yakindu.sct.model.sequencer.vectors.HistoryVector
import org.yakindu.sct.model.sgraph.Entry
import org.yakindu.sct.model.sgraph.Region
import org.yakindu.sct.model.sgraph.Statechart

@Singleton class EntryReactOperation {
	
	@Inject extension SgraphExtensions
	@Inject extension RegionType
	@Inject extension TypeBuilder
	@Inject extension ExpressionBuilder
	@Inject extension StatemachineProperties
	@Inject extension HistoryVector
	@Inject extension StatemachinePublic
	@Inject extension EffectToExpression
	@Inject extension EnterShallowOperation
	@Inject extension EnterDeepOperation

	def declareEntryReactOperations(Statechart sc) {
		sc.eAllContents.filter(Region).forEach[ r |
			r.collectEntries.forEach[ e |
				e.entryReactOperation
			]
		]
	}
	
	def defineEntryReactOperations(Statechart sc) {
		sc.eAllContents.filter(Region).forEach[ r |
			r.collectEntries.forEach[ e |
				e.defineEntryReactOperation
			]
		]
	}

	def create _op entryReactOperation(Entry entry) {
		name = entry.reactionName
		entry.region.type.features += it
	}
	
	def protected defineEntryReactOperation(Entry entry) {
		entry.entryReactOperation => [
			body = _block(entry.reactions)
		]
	}
	
	def reactionName(Entry entry) {
		"entryreact_" + if(entry.name.isNullOrEmpty) "default" else entry.name
	}
	
	def protected reactions(Entry entry) {
		switch(entry.kind) {
			case INITIAL: return entry.initalEntryReactions
			case SHALLOW_HISTORY: return entry.historyReactions
			case DEEP_HISTORY: return entry.historyReactions
		}
	}
	
	def protected List<Expression> initalEntryReactions(Entry entry) {
		entry.createEntrySequence
	}
	
	
	def protected List<Expression> historyReactions(Entry entry) {
		val sc = entry.statechart
		
		val reaction = 
		_if(sc.historyVectorProperty._ref._get(entry.region.historyVector.offset._int)._notEquals(sc.statesEnumeration._ref._fc(sc.noState)))._then(
			_block(entry.historyReaction)
		)._else(
			_block(entry.initialReaction)
		)
		
		#[reaction]
	}
	
	def protected historyReaction(Entry entry) {
		switch(entry.kind) {
			case SHALLOW_HISTORY: return entry.region.enterShallow._call
			case DEEP_HISTORY: return entry.region.enterDeep._call
			default: {
			}
		}
	}

	def protected initialReaction(Entry entry) {
		val entryReaction = entry.createEntrySequence
		if(!entryReaction.isEmpty) 
			entryReaction 
		else 
			entry.parentRegion.entry.createEntrySequence
	}
	
	def protected createEntrySequence(Entry e) {
		val List<Expression> seq = newArrayList
		// transition effects
		val entryTransitionEffect = e?.transition?.effect
		if (entryTransitionEffect !== null) {
			seq += entryTransitionEffect.toEffectExpressions
		}
		
		// target enter
		val target = e.target
		if (target !== null && !e.outgoingTransitions.empty) { 
			seq += e.outgoingTransitions.mapToStateConfigurationEnterSequence
		}
		
		seq
	}
	
}
