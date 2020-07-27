/**
 * Copyright (c) 2020 committers of YAKINDU and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 * 	committers of YAKINDU - initial API and implementation
 * 
 */
package org.yakindu.sct.model.sexec.concepts

import com.google.inject.Inject
import com.google.inject.Singleton
import org.yakindu.base.types.TypeBuilder
import org.yakindu.sct.model.sexec.ExecutionFlow
import org.yakindu.sct.model.sexec.Step
import org.yakindu.sct.model.sexec.extensions.SexecBuilder

/**
 * This class defines the general concept of the runCycle method which implements 
 * the run to comlpetion step (RTCS) of a state machine. 
 * 
 * @author aterfloth
 */
@Singleton
class RunCycleMethod {
	
	@Inject extension ExecutionGuard
	@Inject extension EventProcessing
	@Inject extension SuperStep
	@Inject extension InitializedCheck
	
	@Inject extension TypeBuilder
	@Inject extension SexecBuilder
	

	public static val MICRO_STEP = StateMachineBehaviorConcept.CONCEPT_NAME_PREFIX + "microStep"
	
	def defineFeatures (ExecutionFlow it) {
		defineRunCycle
	}
			
	def defineRunCycle(ExecutionFlow it) {
		
		it._method("runCycle") => [ m |
			m._public
			m._type(_void)
			m._body(
				_isInitializedCheck,
				_guardExecution( _sequence(
					_traceBeginRunCycle,
					_eventProcessing(
						_superStepLoop(
							_microStep
						)
					),
					_traceEndRunCycle
				)) 
			)
			m.body.comment = "Performs a 'run to completion' step."
		]
	}
	
	def Step _microStep(ExecutionFlow it) {
		if (appliesSuperStep)
			defineMicroStep._call.toStep
		else
			_conceptSequence(MICRO_STEP)
	}

	def private create m : _method("microStep") defineMicroStep(ExecutionFlow it) {
		m._type(_void)
		m._body(
			_conceptSequence(MICRO_STEP)
		)
		features += m
	}
	
}