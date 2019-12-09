/**
 * Copyright (c) 2019 committers of YAKINDU and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     committers of YAKINDU - initial API and implementation
 */
package org.yakindu.sct.generator.cpp.submodules.lifecycle

import com.google.inject.Inject
import org.yakindu.sct.generator.c.types.CLiterals
import org.yakindu.sct.generator.core.submodules.lifecycle.Enter
import org.yakindu.sct.generator.core.submodules.lifecycle.Exit
import org.yakindu.sct.generator.core.submodules.lifecycle.Init
import org.yakindu.sct.generator.core.submodules.lifecycle.IsActive
import org.yakindu.sct.generator.core.submodules.lifecycle.IsFinal
import org.yakindu.sct.generator.core.submodules.lifecycle.IsStateActive
import org.yakindu.sct.generator.core.submodules.lifecycle.RunCycle
import org.yakindu.sct.generator.cpp.CppExpressionsGenerator
import org.yakindu.sct.generator.cpp.CppNaming
import org.yakindu.sct.generator.cpp.ErrorCode
import org.yakindu.sct.generator.cpp.features.GenmodelEntriesExtension
import org.yakindu.sct.model.sexec.ExecutionFlow
import org.yakindu.sct.model.sexec.extensions.SExecExtensions
import org.yakindu.sct.model.sexec.extensions.StateVectorExtensions
import org.yakindu.sct.model.sexec.naming.INamingService
import org.yakindu.sct.model.sgen.GeneratorEntry
import org.yakindu.sct.model.stext.stext.InterfaceScope
import org.yakindu.sct.model.stext.stext.InternalScope
import org.yakindu.sct.model.stext.stext.StatechartScope

import static org.yakindu.sct.generator.c.CGeneratorConstants.*
import static org.yakindu.sct.generator.cpp.CppGeneratorConstants.*
import org.yakindu.sct.generator.cpp.FlowCode
import org.yakindu.sct.model.stext.lib.StatechartAnnotations

class LifecycleFunctions implements Init, Enter, RunCycle, IsActive, IsStateActive, Exit, IsFinal {
	
	@Inject protected extension CppNaming
	@Inject protected extension FlowCode
	@Inject protected extension SExecExtensions
	@Inject protected extension GenmodelEntriesExtension
	@Inject protected extension INamingService
	@Inject protected extension CppExpressionsGenerator
	@Inject protected extension StateVectorExtensions
	@Inject protected extension CLiterals
	@Inject protected extension StatechartAnnotations
	
	@Inject protected GeneratorEntry entry
	
	def IStatemachineFunctions() '''
		/*
		 * Functions inherited from StatemachineInterface
		 */
		virtual «IF entry.checkUnimplementedOCBs»sc_errorCode«ELSE»void«ENDIF» init();
		
		virtual void «ENTER»();
		
		virtual void «EXIT»();
		
		virtual void «RUN_CYCLE»();
		
		/*!
		* Checks if the state machine is active (until 2.4.1 this method was used for states).
		* A state machine is active if it has been entered. It is inactive if it has not been entered at all or if it has been exited.
		*/
		virtual sc_boolean «IS_ACTIVE»() const;
		
		
		/*!
		* Checks if all active states are final. 
		* If there are no active states then the state machine is considered being inactive. In this case this method returns false.
		*/
		virtual sc_boolean «IS_FINAL»() const;
	'''
	
	override init(ExecutionFlow it) '''
		«IF entry.checkUnimplementedOCBs»«ERROR_TYPE»«ELSE»void«ENDIF» «module»::«initFctID»()
		{
			«IF entry.checkUnimplementedOCBs»
			«ERROR_TYPE» errorCode = 0;
			
			«unimplementedOCBErrors»«ENDIF»
			for («USHORT_TYPE» i = 0; i < «orthogonalStatesConst»; ++i)
				«STATEVECTOR»[i] = «null_state»;
			
			«IF hasHistory»
			for (sc_ushort i = 0; i < «historyStatesConst»; ++i)
				«HISTORYVECTOR»[i] = «null_state»;
			
			«ENDIF»
			«STATEVECTOR_POS» = 0;
			
			«IF statechart.isSuperStep»
			«STATEVECTOR_CHANGED» = false;
			«ENDIF»
		
			«clearInEventsFctID»();
			«clearOutEventsFctID»();
			
			«initSequence.code»
			«IF entry.checkUnimplementedOCBs»
			return errorCode;
			«ENDIF»
		}
	'''
	
	override enter(ExecutionFlow it) '''
		void «module»::«enterFctID»()
		{
			«enterSequences.defaultSequence.code»
		}
	'''
	
	override runCycle(ExecutionFlow it) '''
		void «module»::«runCycleFctID»()
		{
			«clearOutEventsFctID»();
			«runCycleFunctionForLoop»
			«clearInEventsFctID»();
		}
	'''
	
	protected def superStepLoop(CharSequence microStep) '''
		do {
			«STATEVECTOR_CHANGED» = false;
			«microStep»
		} while(«STATEVECTOR_CHANGED»);
	'''
	
	def runCycleFunctionForLoop(ExecutionFlow it) {
		val microStep = '''
		for («STATEVECTOR_POS» = 0;
			«STATEVECTOR_POS» < «orthogonalStatesConst»;
			«STATEVECTOR_POS»++)
			{
				
			switch («STATEVECTOR»[«STATEVECTOR_POS»])
			{
			«FOR state : states.filter[isLeaf]»
				«IF state.reactMethod !== null»
				case «state.shortName.asEscapedIdentifier» :
				{
					«state.reactMethod.shortName»(true);
					break;
				}
				«ENDIF»
			«ENDFOR»
			default:
				break;
			}
		}
		'''
		return if (statechart.isSuperStep) superStepLoop(microStep) else microStep
	}
	
	override isActive(ExecutionFlow it) '''
		«BOOL_TYPE» «module»::«isActiveFctID»() const
		{
			return «FOR i : 0 ..< stateVector.size SEPARATOR '||'»«STATEVECTOR»[«i»] != «null_state»«ENDFOR»;
		}
	'''
	
	override isStateActive(ExecutionFlow it) '''
		«BOOL_TYPE» «module»::«stateActiveFctID»(«statesEnumType» state) const
		{
			switch (state)
			{
				«FOR s : states»
				case «s.shortName.asEscapedIdentifier» : 
					return («BOOL_TYPE») («IF s.leaf»«STATEVECTOR»[«s.stateVectorDefine»] == «s.shortName.asEscapedIdentifier»
					«ELSE»«STATEVECTOR»[«s.stateVectorDefine»] >= «s.shortName.asEscapedIdentifier»
						&& «STATEVECTOR»[«s.stateVectorDefine»] <= «s.subStates.last.shortName.asEscapedIdentifier»«ENDIF»);
				«ENDFOR»
				default: return false;
			}
		}
	'''
	
	override exit(ExecutionFlow it) '''
		void «module»::«exitFctID»()
		{
			«exitSequence.code»
		}
	'''
	
	override isFinal(ExecutionFlow it){
		val finalStateImpactVector = flow.finalStateImpactVector
		'''
			«IF !finalStateImpactVector.isCompletelyCovered»
			/* 
			 * Always returns 'false' since this state machine can never become final.
			 */
			«ENDIF»
			«BOOL_TYPE» «module»::«isFinalFctID»() const
			{
		''' +
		// only if the impact vector is completely covered by final states the state machine 
		// can become final
		{if (finalStateImpactVector.isCompletelyCovered) {'''	return «FOR i : 0 ..<finalStateImpactVector.size SEPARATOR ' && '»(«FOR fs : finalStateImpactVector.get(i) SEPARATOR ' || '»stateConfVector[«i»] == «IF fs.stateVector.offset == i»«fs.shortName»«ELSE»«null_state»«ENDIF»«ENDFOR»)«ENDFOR»;
		'''} else {'''   return false;'''} }		
		+ '''
		}'''
	}
	
	def unimplementedOCBErrors(ExecutionFlow it)'''
		«FOR iface : getInterfaces.filter[hasOperations && !entry.useStaticOPC]»
			«IF iface instanceof InternalScope»
				«checkInternalOCB(iface)»
			«ELSEIF iface instanceof InterfaceScope»
				«checkInterfaceOCB(iface)»
			«ENDIF»
		«ENDFOR»
	'''
	
	def checkInternalOCB(StatechartScope it) '''
		if (this->«OCB_Instance» == «NULL_LITERAL») { 
			errorCode |= (short) «ErrorCode.OCB_INTERNAL_INIT.getName()»;
		}
	'''
	
	def checkInterfaceOCB(StatechartScope it) '''
		«IF defaultInterface»
			if (this->«OCB_Instance» == «NULL_LITERAL») { 
				errorCode |=  (short) «ErrorCode.OCB_DEFAULT_INIT.getName()»;
			}
		«ELSE»
			if (this->«OCB_Instance» == «NULL_LITERAL») { 
				errorCode |= (short) «ErrorCode.OCB_NAMED_INIT.getName()»;
			}
		«ENDIF»
	'''
	
}