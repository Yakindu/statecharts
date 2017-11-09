/**
 * Copyright (c) 2012 committers of YAKINDU and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 * 	committers of YAKINDU - initial API and implementation
 * 
 */
package org.yakindu.sct.generator.cpp

import com.google.inject.Inject
import java.util.List
import org.yakindu.sct.generator.c.IContentTemplate
import org.yakindu.sct.generator.c.IGenArtifactConfigurations
import org.yakindu.sct.generator.core.types.ICodegenTypeSystemAccess
import org.yakindu.sct.generator.cpp.features.GenmodelEntriesExtension
import org.yakindu.sct.model.sexec.Check
import org.yakindu.sct.model.sexec.ExecutionFlow
import org.yakindu.sct.model.sexec.Step
import org.yakindu.sct.model.sexec.extensions.StateVectorExtensions
import org.yakindu.sct.model.sexec.naming.INamingService
import org.yakindu.sct.model.sgen.GeneratorEntry
import org.yakindu.sct.model.stext.stext.InterfaceScope
import org.yakindu.sct.model.stext.stext.StatechartScope
import org.yakindu.sct.model.stext.stext.VariableDefinition

import static org.eclipse.xtext.util.Strings.*
import org.yakindu.sct.generator.c.extensions.ExpressionsChecker

class StatemachineImplementation implements IContentTemplate {
	
	@Inject protected extension CppNaming
	@Inject protected extension Navigation
	@Inject protected extension FlowCode
	@Inject protected extension GenmodelEntriesExtension
	@Inject protected extension ICodegenTypeSystemAccess
	@Inject protected extension INamingService
	@Inject protected extension CppExpressionsGenerator
	@Inject protected extension StateVectorExtensions
	@Inject protected extension EventCode
	@Inject protected extension ExpressionsChecker
	
	protected GeneratorEntry entry
	
	override content(ExecutionFlow it, GeneratorEntry entry, IGenArtifactConfigurations artifactConfigs) {
		this.entry = entry
	'''	
		«entry.licenseText»
		
		#include "«module.h»"
		#include <string.h>
		«IF modOnReal»#include <math.h>«ENDIF»
		
		/*! \file Implementation of the state machine '«name»'
		*/
		
		«usingNamespaces»
		
		«constructorDefinition»
		
		«destructorDefinition»
		
		«constantDefinitions»
		
		«initFunction»
		
		«enterFunction»
		
		«exitFunction»
		
		«activeFunction»
		
		«finalFunction»
		
		«runCycleFunction»
		
		«clearInEventsFunction»
		
		«clearOutEventsFunction»

		«timedStatemachineFunctions»

		«isStateActiveFunction»
		
		«interfaceFunctions»
		
		«functionImplementations»
		
		«additionalFunctions»
	'''
	}
	

		
	
	def protected usingNamespaces(ExecutionFlow it) {
		''''''
	}
	
	def additionalFunctions(ExecutionFlow it) {
		/* Hook for child classes */
		''''''
	}
	

	def constructorDefinition(ExecutionFlow it){
	'''
		«module»::«module»():
			«initialisationList»
		{
			«constructorBody(it)»
		}
	'''
	}
	
	def protected initialisationList(ExecutionFlow it) {
		'''
			«IF timed»«timerInstance»(null),«ENDIF»
			stateConfVectorPosition(0)«FOR s : getInterfaces»,
			«s.instance»()«IF s.hasOperations && !entry.useStaticOPC»,
			«s.OCB_Instance»(null)«ENDIF»«ENDFOR»
		'''
	}
	
	def protected initialisationListCopy(ExecutionFlow it) {
		'''
			«IF timed»«timerInstance»(rhs.«timerInstance»),«ENDIF»
			stateConfVectorPosition(rhs.stateConfVectorPosition)«FOR s : getInterfaces»,
			«s.instance»(rhs.«s.instance»)«IF s.hasOperations && !entry.useStaticOPC»,
			«s.OCB_Instance»(rhs.«s.OCB_Instance»)«ENDIF»«ENDFOR»
		'''	
	}
	
	protected def CharSequence constructorBody(ExecutionFlow it)
		'''
		«IF hasHistory»
			for (int i = 0; i < «historyStatesConst»; ++i)
				historyVector[i] = «null_state»;
				
		«ENDIF»
		'''
	
	
	def destructorDefinition(ExecutionFlow it) '''
		«module»::~«module»()
		{
		}
	'''
	
	def initFunction(ExecutionFlow it) '''
		void «module»::init()
		{
			for (int i = 0; i < «orthogonalStatesConst»; ++i)
				stateConfVector[i] = «null_state»;
			
			«IF hasHistory»
			for (int i = 0; i < «historyStatesConst»; ++i)
				historyVector[i] = «null_state»;
			
			«ENDIF»
			stateConfVectorPosition = 0;
		
			clearInEvents();
			clearOutEvents();
			
			«initSequence.code»
		}
	'''
	
	def enterFunction(ExecutionFlow it) '''
		void «module»::enter()
		{
			«enterSequences.defaultSequence.code»
		}
	'''
	
	def exitFunction(ExecutionFlow it) '''
		void «module»::exit()
		{
			«exitSequence.code»
		}
	'''
	
	def clearInEventsFunction(ExecutionFlow it) '''
		void «module»::clearInEvents()
		{
			«FOR scope : it.scopes»
				«FOR event : scope.incomingEvents»
				«event.access» = false;
				«ENDFOR»
			«ENDFOR»
			«IF hasLocalScope»
				«FOR event : internalScope.events»
				«event.access» = false; 
				«ENDFOR»
			«ENDIF»
			«IF timed»
				«FOR event : timeEventScope.events»
				«event.access» = false; 
				«ENDFOR»
			«ENDIF»
		}
	'''
	
	def clearOutEventsFunction(ExecutionFlow it) '''
		void «module»::clearOutEvents()
		{
			«FOR scope : it.scopes»
				«FOR event : scope.outgoingEvents»
				«event.access» = false;
				«ENDFOR»
			«ENDFOR»
		}
	'''
	
	def runCycleFunction(ExecutionFlow it) '''
		void «module»::runCycle()
		{
			
			clearOutEvents();
			«runCycleFunctionForLoop»			
			clearInEvents();
		}
	'''
	
	def runCycleFunctionForLoop(ExecutionFlow it) {
		'''
		for (stateConfVectorPosition = 0;
			stateConfVectorPosition < «orthogonalStatesConst»;
			stateConfVectorPosition++)
			{
				
			switch (stateConfVector[stateConfVectorPosition])
			{
			«FOR state : states»
				«IF state.reactSequence !== null»
				case «state.shortName.asEscapedIdentifier» :
				{
					«state.reactSequence.shortName»();
					break;
				}
				«ENDIF»
			«ENDFOR»
			default:
				break;
			}
		}
		'''
	}
	
	def timedStatemachineFunctions(ExecutionFlow it) '''
		«IF timed»
			
			void «module»::setTimer(«timerInterface»* timerInterface)
			{
				this->«timerInstance» = timerInterface;
			}
			
			«timerInterface»* «module»::getTimer()
			{
				return «timerInstance»;
			}
			
			«raiseTimeEventFunction»
		«ENDIF»
	'''
	
	def raiseTimeEventFunction(ExecutionFlow it) '''
		void «module»::«raiseTimeEventFctID»(sc_eventid evid)
		{
			if ((evid >= (sc_eventid)«timeEventsInstance») && (evid < (sc_eventid)(&«timeEventsInstance»[«timeEventsCountConst»])))
			{
				*(sc_boolean*)evid = true;
			}				
		}
	'''
	
	def isStateActiveFunction(ExecutionFlow it) '''
		sc_boolean «module»::«stateActiveFctID»(«statesEnumType» state) const
		{
			switch (state)
			{
				«FOR s : states»
				case «s.shortName.asEscapedIdentifier» : 
					return (sc_boolean) («IF s.leaf»stateConfVector[«s.stateVectorDefine»] == «s.shortName.asEscapedIdentifier»
					«ELSE»stateConfVector[«s.stateVectorDefine»] >= «s.shortName.asEscapedIdentifier»
						&& stateConfVector[«s.stateVectorDefine»] <= «s.subStates.last.shortName.asEscapedIdentifier»«ENDIF»);
				«ENDFOR»
				default: return false;
			}
		}
	'''
	
	
	def isActiveFunction(ExecutionFlow it) '''
		sc_boolean «module»::isActive() const
		{
			return «FOR i : 0 ..< stateVector.size SEPARATOR '||'»stateConfVector[«i»] != «null_state»«ENDFOR»;
		}
	'''
	
	def protected isFinalFunction(ExecutionFlow it){
		val finalStateImpactVector = flow.finalStateImpactVector
		'''
			«IF !finalStateImpactVector.isCompletelyCovered»
			/* 
			 * Always returns 'false' since this state machine can never become final.
			 */
			«ENDIF»
			sc_boolean «module»::isFinal() const
			{
		''' +
		// only if the impact vector is completely covered by final states the state machine 
		// can become final
		{if (finalStateImpactVector.isCompletelyCovered) {'''	return «FOR i : 0 ..<finalStateImpactVector.size SEPARATOR ' && '»(«FOR fs : finalStateImpactVector.get(i) SEPARATOR ' || '»stateConfVector[«i»] == «IF fs.stateVector.offset == i»«fs.shortName»«ELSE»«null_state»«ENDIF»«ENDFOR»)«ENDFOR»;
		'''} else {'''   return false;'''} }		
		+ '''
		}'''
	}
	
	def constantDefinitions(ExecutionFlow it) '''
		«FOR scope : statechartScopes»
			«FOR d : scope.declarations.filter(typeof(VariableDefinition)).filter[const] AFTER newLine»
				«IF d.type.name != 'void'»const «d.typeSpecifier.targetLanguageName» «d.access» = «d.initialValue.code»;«ENDIF»
			«ENDFOR»
		«ENDFOR»
	'''
	
	/* ===================================================================================
	 * Implementation of interface element access functions
	 */
	
	def interfaceFunctions(ExecutionFlow it) '''
		«FOR scope : statechartScopes»
			«IF scope instanceof InterfaceScope»
			«module»::«scope.interfaceName»* «module»::get«scope.interfaceName»()
			{
				return &«scope.instance»;
			}
			«ENDIF»
			«generateEvents(scope)»
			«generateVariables(scope)»
			«IF scope.hasOperations && !entry.useStaticOPC»
				«scope.OCB_InterfaceSetterDeclaration(true)»
				{
					«scope.OCB_Instance» = operationCallback;
				}
			«ENDIF»
		«ENDFOR»
	'''
	
	def generateVariables(ExecutionFlow it, StatechartScope scope)
		'''
			«FOR variable : scope.variableDefinitions»
				«IF variable.const»const «ENDIF»«variable.typeSpecifier.targetLanguageName» «module»::«scope.interfaceName»::«variable.asGetter»() const
				{
					return «variable.localAccess»;
				}
				
				«IF scope.defaultInterface»
					«IF variable.const»const «ENDIF»«variable.typeSpecifier.targetLanguageName» «module»::«variable.asGetter»() const
					{
						return «variable.access»;
					}
					
				«ENDIF»
				«IF !variable.readonly && !variable.const»
					void «module»::«scope.interfaceName»::«variable.asSetter»(«variable.typeSpecifier.targetLanguageName» value)
					{
						«variable.localAccess» = value;
					}
					
					«IF scope.defaultInterface»
						void «module»::«variable.asSetter»(«variable.typeSpecifier.targetLanguageName» value)
						{
							«variable.access» = value;
						}
						
					«ENDIF»
				«ENDIF»
			«ENDFOR»
		'''
	
	/* ===================================================================================
	 * Handling implementation of internal functions
	 */
	 
	/** */
	def functionImplementations(ExecutionFlow it) '''
		// implementations of all internal functions
		
		«checkFunctions.toImplementation»
		«effectFunctions.toImplementation»
		«entryActionFunctions.toImplementation»
		«exitActionFunctions.toImplementation»
		«enterSequenceFunctions.toImplementation»
		«exitSequenceFunctions.toImplementation»
		«reactFunctions.toImplementation»
		
	'''
	 
	def toImplementation(List<Step> steps) '''
		«FOR s : steps»
			«s.functionImplementation»
		«ENDFOR»
	'''
	
	def dispatch functionImplementation(Check it) '''
		«stepComment»
		sc_boolean «execution_flow.module»::«shortName»()
		{
			return «code»;
		}
		
	'''
	
	def dispatch functionImplementation(Step it) '''
		«stepComment»
		void «execution_flow.module»::«shortName»()
		{
			«code»
		}
		
	'''
}