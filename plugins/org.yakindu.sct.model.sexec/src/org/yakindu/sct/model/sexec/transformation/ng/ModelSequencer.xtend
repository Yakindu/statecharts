/** 
 * Copyright (c) 2015-2018 committers of YAKINDU and others. 
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0 
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/epl-v10.html 
 * Contributors:
 * committers of YAKINDU - initial API and implementation
 *
*/
package org.yakindu.sct.model.sexec.transformation.ng

import com.google.inject.Inject
import org.yakindu.base.types.Package
import org.yakindu.base.types.validation.IValidationIssueAcceptor
import org.yakindu.base.types.validation.IValidationIssueAcceptor.ListBasedValidationIssueAcceptor
import org.yakindu.sct.model.sexec.ExecutionFlow
import org.yakindu.sct.model.sexec.transformation.IModelSequencer
import org.yakindu.sct.model.sexec.transformation.ReactionBuilder
import org.yakindu.sct.model.sexec.transformation.RetargetReferences
import org.yakindu.sct.model.sexec.transformation.SexecElementMapping
import org.yakindu.sct.model.sexec.transformation.StateVectorBuilder
import org.yakindu.sct.model.sexec.transformation.StructureMapping
import org.yakindu.sct.model.sexec.transformation.ng.reactions.ReactOperation
import org.yakindu.sct.model.sgraph.Statechart

class ModelSequencer implements IModelSequencer {
	 
	@Inject extension SexecElementMapping mapping
	@Inject extension StructureMapping structureMapping
	@Inject extension BehaviorMapping behaviorMapping
	@Inject extension ReactionBuilder reactBuilder
	@Inject extension SequenceBuilder seqBuilder
	@Inject extension StateVectorBuilder svBuilder
	@Inject extension RetargetReferences retageting
	@Inject extension RetargetReferencesInPackage retageting2
	
	@Inject extension ReactOperation reactMethod
	@Inject extension EntryReactOperation
	@Inject extension ExitReactOperation
	@Inject extension EnterOperation
	@Inject extension EnterShallowOperation
	@Inject extension EnterDeepOperation
	
	@Inject extension ReactMethod reactMethodOld
	
	@Inject extension StatemachinePublic sc2Type
	@Inject extension StatemachineMethods smMethods
	@Inject	extension Sequence2Method s2m
	@Inject	extension StatemachineProperties smProps
	
	
	/* ==========================================================================
	 * TRANSFORMATION ROOT
	 */ 
	override ExecutionFlow transform(Statechart sc) {
		transform(sc, new ListBasedValidationIssueAcceptor)
	}

	override Package transformToPackage(Statechart sc, IValidationIssueAcceptor acceptor) {
				
		return sc.makePackage
		
	}
	
	override ExecutionFlow transform(Statechart sc, IValidationIssueAcceptor acceptor) {
		sc.makePackage
		val ef = sc.makeFlow
		
		//clear create caches to avoid memory leak with repetetive generator cycles
		mapping.cleanup
		
		return ef
	}

	protected def create pkg:sc.statemachinePackage makePackage(Statechart sc) {

		val sctype = sc.statemachineType

		sctype.defineProperties(sc)

		sc.declareEntryReactOperations
		sc.declareExitReactOperations
		sc.declareEnterOperations
		sc.declareEnterShallowOperations
		sc.declareEnterDeepOperations
		sc.declareReactMethods
		
		val ef = sc.makeFlow

		sc.defineEntryReactOperations
		sc.defineExitReactOperations
		sc.defineEnterOperations
		sc.defineEnterShallowOperations
		sc.defineEnterDeepOperations
		sc.defineReactMethods
		
//		sctype.declareSequenceMethods(ef)
		
		sctype.defineEnterMethod(sc)
		
		sctype.defineExitMethod(sc)
		
		sctype.defineInitMethod(sc)
		
		sctype.defineIsActiveMethod(sc)
		
		sctype.defineIsFinalMethod(sc)
		
		sctype.defineRunCycleMethod(sc)
		
		sctype.defineIsStateActiveMethod(sc)
		
		sctype.defineClearOutEventsMethod(sc)
		sctype.defineClearEventsMethod(sc)
		
		pkg.retargetReferences
		
	}

	protected def create ef:sc.create makeFlow(Statechart sc) {
				
		// during mapping the basic structural elements will be mapped from the source statechart to the execution flow
		sc.mapScopes(ef)
		sc.mapRegularStates(ef)
		sc.mapPseudoStates(ef)
		sc.mapRegions(ef)
		sc.mapTimeEvents(ef)

		ef.declareReactMethods

		// calculate state vectors
		ef.defineStateVector(sc)
		ef.defineHistoryVector(sc)
		
		// derive all additional information that is necessary for the execution
		sc.mapEntryActions(ef)
		sc.mapExitActions(ef)
		
		 
		ef.defineEnterSequences(sc)
		ef.defineStateExitSequences(sc)
		ef.defineDeepEnterSequences(sc)
		ef.defineShallowEnterSequences(sc)
		ef.defineStatechartEnterSequence(sc)
		ef.defineStatechartExitSequence(sc)

		ef.defineStatechartInitSequence(sc)
		ef.defineStatechartStaticInitSequence(sc)
		
		sc.mapLocalReactions(ef)
		sc.mapTransitions(ef)
		sc.mapEntries(ef)
		sc.mapChoiceTransitions(ef)
		sc.mapSyncTransitions(ef)
	
		sc.defineEntryReactions(ef)
		ef.definePseudoStateReactions(sc)
		
		ef.defineReactMethods
		
		// retarget declaration refs
		ef.retargetDeclRefs
				
	}

	/************** retarget declaration refs **************/
	
		 	
}