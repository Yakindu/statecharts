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
package org.yakindu.sct.generator.c.extensions

import com.google.inject.Inject
import com.google.inject.name.Named
import java.util.List
import org.eclipse.emf.ecore.EObject
import org.yakindu.base.types.Declaration
import org.yakindu.base.types.Enumerator
import org.yakindu.base.types.Event
import org.yakindu.base.types.Operation
import org.yakindu.base.types.Property
import org.yakindu.sct.generator.core.types.ICodegenTypeSystemAccess
import org.yakindu.sct.model.sexec.ExecutionFlow
import org.yakindu.sct.model.sexec.ExecutionState
import org.yakindu.sct.model.sexec.Step
import org.yakindu.sct.model.sexec.TimeEvent
import org.yakindu.sct.model.sexec.extensions.SExecExtensions
import org.yakindu.sct.model.sexec.naming.INamingService
import org.yakindu.sct.model.sgen.GeneratorEntry
import org.yakindu.sct.model.sgraph.Scope
import org.yakindu.sct.model.sgraph.ScopedElement
import org.yakindu.sct.model.sgraph.State
import org.yakindu.sct.model.sgraph.Statechart
import org.yakindu.sct.model.stext.naming.StextNameProvider
import org.yakindu.sct.model.stext.stext.EventDefinition
import org.yakindu.sct.model.stext.stext.InterfaceScope
import org.yakindu.sct.model.stext.stext.InternalScope
import org.yakindu.sct.model.stext.stext.OperationDefinition
import org.yakindu.sct.model.stext.stext.VariableDefinition
import org.yakindu.sct.model.sexec.Method

class Naming {
	@Inject @Named("Separator") protected String sep;

	@Inject protected extension SExecExtensions

	@Inject protected extension ICodegenTypeSystemAccess

	@Inject protected StextNameProvider provider
	
	@Inject GeneratorEntry entry

	@Inject protected extension INamingService
	
	@Inject extension GenmodelEntries

	public static final String NULL_STRING = "null";

	def getFullyQualifiedName(State state) {
		provider.getFullyQualifiedName(state).toString.asEscapedIdentifier
	}

	def module(ExecutionFlow it) {
		if (entry.moduleName.nullOrEmpty) {
			return name.asIdentifier.toFirstUpper
		}
		return entry.moduleName.toFirstUpper
	}

	def filterNullOrEmptyAndJoin(Iterable<CharSequence> it) {
		filter[!it?.toString.nullOrEmpty].join('\n')
	}

	def filterNullOrEmptyAndJoin(List<String> it) {
		filter[!it?.toString.nullOrEmpty].join('\n')
	}

	def client(String it) {
		it + "Required"
	}

	def timerModule(ExecutionFlow it) {
		'sc_timer'
	}

	def typesModule(ExecutionFlow it) {
		'sc_types'
	}

	def timerType(ExecutionFlow it) {
		'SCTimer'
	}
	
	def statesEnumType(ExecutionFlow it) {
		containerType + 'States'
	}
	
	def protected String entryStatemachinePrefix() {
		entry.statemachinePrefix
	}

	def dispatch String type(InterfaceScope it) {
		containerType + 'Iface' + (if(name.nullOrEmpty) '' else name).asIdentifier.toFirstUpper
	}

	def dispatch String type(InternalScope it) {
		containerType + 'Internal'
	}

	def dispatch String type(Scope it) {
		containerType + 'TimeEvents'
	}

	def dispatch String type(ExecutionFlow it) {
		if (entryStatemachinePrefix.nullOrEmpty) {
			return name.asIdentifier.toFirstUpper
		}
		return entryStatemachinePrefix.toFirstUpper
	}
	
	def dispatch String type(Statechart it) {
		if(entryStatemachinePrefix.nullOrEmpty) {
			return name.asIdentifier.toFirstUpper
		}
		return entryStatemachinePrefix.toFirstUpper
	}
	
	def String getContainerType(EObject it) {
		if (flow !== null) {
			return flow.type
		}
		return statechart.type
	}

	def dispatch instance(InterfaceScope it) {
		'iface' + (if(name.nullOrEmpty) '' else name).asIdentifier.toFirstUpper
	}

	def dispatch instance(Scope it) {
		'timeEvents'
	}

	def dispatch instance(InternalScope it) {
		'internal'
	}

	def functionPrefix(Scope it, Declaration decl) {
		// only non-unique declarations in different scopes will be prefixed with the name of the scope
		if (!isUniqueName(it, decl) && !entryStatemachinePrefix.nullOrEmpty)
			return entryStatemachinePrefix + separator + it.instance.toFirstUpper
		if (!entryStatemachinePrefix.nullOrEmpty)
			return entryStatemachinePrefix
		return type.toFirstLower
	}

	protected def boolean isUniqueName(Scope scope, Declaration decl) {
		(scope.eContainer as ScopedElement).scopes.map[declarations].flatten.filter[it.name == decl.name].size == 1
	}

	def functionPrefix(ExecutionFlow it) {
		if (!entryStatemachinePrefix.nullOrEmpty) {
			return entryStatemachinePrefix + separator
		}
		type.toFirstLower + separator
	}

	def separator() {
		var sep = this.sep
		if (sep.nullOrEmpty) {
			sep = "_"
		}
		return sep
	}

	def clearInEventsFctID(ExecutionFlow it) {
		"clearInEvents"
	}

	def clearOutEventsFctID(ExecutionFlow it) {
		"clearOutEvents"
	}

	def dispatch String null_state(ExecutionFlow it) {
		type + lastStateID
	}

	def dispatch String null_state(Step it) {
		execution_flow.null_state
	}

	def lastStateID() {
		separator + "last" + separator + "state"
	}

	def stateVectorDefine(ExecutionState it) {
		'''SCVI_«shortName»'''.toString.toUpperCase
	}

	def ExecutionFlow execution_flow(EObject element) {
		var ret = element;

		while (ret !== null) {
			if (ret instanceof ExecutionFlow) {
				return ret as ExecutionFlow
			} else {
				ret = ret.eContainer;
			}
		}
		return null;
	}
	
	def Statechart statechart(EObject element) {
		var ret = element;

		while (ret !== null) {
			if (ret instanceof Statechart) {
				return ret as Statechart
			} else {
				ret = ret.eContainer;
			}
		}
		return null;
	}
	
	def bool() {
		"sc_boolean"
	}
	
	def dispatch scopeTypeDeclMember(EventDefinition it) '''
		«bool» «eventRaisedFlag»;
		«IF type !== null && type.name != 'void'»«typeSpecifier.targetLanguageName» «eventValueVariable»;«ENDIF»
	'''

	def dispatch scopeTypeDeclMember(TimeEvent it) '''
		«bool» «timeEventRaisedFlag»;
	'''

	def dispatch scopeTypeDeclMember(VariableDefinition it) '''
		«IF type.name != 'void' && !isConst»«typeSpecifier.targetLanguageName» «variable»;«ENDIF»
	'''
	
	def dispatch scopeTypeDeclMember(Declaration it) ''''''

	def constantName(VariableDefinition it) {
		(containerType + separator + scope.type + separator + name.asEscapedIdentifier).toUpperCase
	}

	def raiseTimeEventFctID(ExecutionFlow it) {
		functionPrefix + "raiseTimeEvent"
	}

	def isStateActiveFctID(ExecutionFlow it) {
		functionPrefix + "isStateActive"
	}

	def isActiveFctID(ExecutionFlow it) {
		functionPrefix + "isActive"
	}

	def isFinalFctID(ExecutionFlow it) {
		functionPrefix + "isFinal"
	}
	
	def initFctID(ExecutionFlow it) {
		functionPrefix + "init"
	}
	
	def enterFctID(ExecutionFlow it) {
		functionPrefix + "enter"
	}

	def exitFctID(ExecutionFlow it) {
		functionPrefix + "exit"
	}
	
	def runCycleFctID(ExecutionFlow it) {
		functionPrefix + "runCycle"
	}
	
	def eventValueVariable(EventDefinition it) {
		name.asIdentifier.value
	}
	
	def timeEventRaisedFlag(TimeEvent it) {
		shortName.raised
	}
	
	def eventRaisedFlag(EventDefinition it) {
		 name.asIdentifier.raised
	}
	
	def setTimerFctID(ExecutionFlow it) {
		functionPrefix + "setTimer"
	}
	
	def unsetTimerFctID(ExecutionFlow it) {
		functionPrefix + "unsetTimer"
	}
	
	def enterStateTracingFctID(ExecutionFlow it) {
		functionPrefix + "stateEntered"
	}
	
	def exitStateTracingFctID(ExecutionFlow it) {
		functionPrefix + "stateExited"
	}
 
	def asRaiser(EventDefinition it) {
		accessFunction("raise")
	}

	def asRaised(EventDefinition it) {
		accessFunction("israised")
	}

	def asGetter(EventDefinition it) {
		accessFunction("get").value
	}

	def asGetter(VariableDefinition it) {
		accessFunction("get")
	}

	def asSetter(VariableDefinition it) {
		accessFunction("set")
	}

	def asFunction(OperationDefinition it) {
		scope.functionPrefix(it) + separator + name.asIdentifier.toFirstLower
	}
	
	def accessFunction(Declaration it, String funcName) {
		scope.functionPrefix(it) + separator + funcName + separator + name.asIdentifier.toFirstLower
	}
	
	def variable(VariableDefinition it) {
		name.asEscapedIdentifier
	}
	
	def stateName(ExecutionState it) {
		shortName
	}
	
	def stateName(State it) {
		shortName
	}

	def raised(CharSequence it) { it + separator + 'raised' }

	def value(CharSequence it) { it + separator + 'value' }

	def h(String it) { it + ".h" }

	def c(String it) { it + ".c" }

	def define(String it) { it.replaceAll('\\.', '_').toUpperCase }

	def dispatch scopeDescription(Scope it) '''scope'''

	def dispatch scopeDescription(InterfaceScope it) '''«IF name.nullOrEmpty»default interface scope«ELSE»interface scope '«name»'«ENDIF»'''

	def dispatch scopeDescription(InternalScope it) '''internal scope'''

	def scHandleDecl(EObject it) { containerType + '* ' + scHandle }

	def scHandle() { 'handle' }

	def valueParams(EventDefinition it) {
		if(hasValue) ', ' + typeSpecifier.targetLanguageName + ' value' else ''
	}

	def dispatch access(VariableDefinition it) {
		if (isConst) '''«it.constantName»''' else '''«scHandle»->«scope.instance».«name.asEscapedIdentifier»'''
	}

	def dispatch access(Property it) {
		'''«name.asEscapedIdentifier»'''
	}

	def dispatch access(Operation it) {
		'''«name.asEscapedIdentifier»'''
	}

	def dispatch access(Method it) '''«shortName»'''
	
	def dispatch access(Enumerator it) {
		'''«name.asEscapedIdentifier»'''
	}

	def dispatch access(OperationDefinition it) '''«asFunction»'''

	def dispatch access(Event it) '''«scHandle»->«scope.instance».«name.asIdentifier.raised»'''

	def dispatch access(TimeEvent it) '''«scHandle»->«scope.instance».«shortName.raised»'''

	def dispatch access(EObject it) '''#error cannot access elements of type «getClass().name»'''

	def valueAccess(Event it) '''«scHandle»->«scope.instance».«name.asIdentifier.value»'''

	def maxOrthogonalStates(ExecutionFlow it) '''«type.toUpperCase»_MAX_ORTHOGONAL_STATES'''

	def maxHistoryStates(ExecutionFlow it) '''«type.toUpperCase»_MAX_HISTORY_STATES'''

	def maxParallelTimeEvents(ExecutionFlow it) '''«type.toUpperCase»_MAX_PARALLEL_TIME_EVENTS'''
}
