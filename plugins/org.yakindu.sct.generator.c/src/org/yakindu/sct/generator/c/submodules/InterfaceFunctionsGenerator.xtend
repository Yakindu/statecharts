/**
 * Copyright (c) 2018 committers of YAKINDU and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 * 	rbeckmann - initial API and implementation
 * 
 */
package org.yakindu.sct.generator.c.submodules

import com.google.inject.Inject
import com.google.inject.Singleton
import org.yakindu.base.types.Event
import org.yakindu.sct.generator.c.FlowCode
import org.yakindu.sct.generator.c.GeneratorPredicate
import org.yakindu.sct.generator.c.extensions.ExpressionsChecker
import org.yakindu.sct.generator.c.extensions.GenmodelEntries
import org.yakindu.sct.generator.c.extensions.Naming
import org.yakindu.sct.generator.c.types.CLiterals
import org.yakindu.sct.generator.core.types.ICodegenTypeSystemAccess
import org.yakindu.sct.model.sexec.ExecutionFlow
import org.yakindu.sct.model.sexec.extensions.SExecExtensions
import org.yakindu.sct.model.sexec.extensions.ShadowEventExtensions
import org.yakindu.sct.model.sgen.GeneratorEntry
import org.yakindu.sct.model.stext.stext.VariableDefinition

/**
 * @author rbeckmann
 * @author rherrmann
 * @author aterfloth
 *
 */
@Singleton // Guice
class InterfaceFunctionsGenerator {
	@Inject protected extension SExecExtensions
	@Inject protected extension Naming
	@Inject protected extension ICodegenTypeSystemAccess
	@Inject protected extension EventCode
	@Inject protected extension FlowCode
	@Inject protected extension ExpressionsChecker
	@Inject protected extension GeneratorPredicate
	@Inject protected extension TraceCode
	@Inject protected extension ShadowEventExtensions
	@Inject protected extension CLiterals
	
	@Inject protected extension GeneratorEntry entry
	@Inject protected extension GenmodelEntries
	
	def interfaceFunctions(ExecutionFlow it) '''
		«FOR scope : interfaceScopes»
			«FOR event : scope.incomingEvents»
				«interfaceIncomingEventRaiser(event)»
			«ENDFOR»
			
			«FOR event : scope.outgoingEvents»
				«IF useOutEventObservables»
					«interfaceOutgoingEventObservableGetter(event)»
				«ENDIF»
				«IF useOutEventGetters»
					«interfaceOutgoingEventGetter(event)»
					«IF event.hasValue» 
						«interfaceOutgoingEventValueGetter(event)»
					«ENDIF»
				«ENDIF»
			«ENDFOR»
			
			«FOR variable : scope.variableDefinitions»
				«variable.typeSpecifier.targetLanguageName» «variable.asGetter»(const «scHandleDecl»)
				{
					«IF variable.isConst»
						«unusedParam(scHandle)»
					«ENDIF»
					return «variable.access»;
				}
				«IF !variable.readonly && !variable.const»
				«val traceCode = variable.traceCode('&value')»
				void «variable.asSetter»(«scHandleDecl», «variable.typeSpecifier.targetLanguageName» value)
				{
					«IF traceCode !== null»
						«traceCode»;
					«ENDIF»
					«observerHandler(variable, "unsubscribe")»
					«variable.access» = value;
					«observerHandler(variable, "subscribe")»
				}
				«ENDIF»
			«ENDFOR»
		«ENDFOR»
	'''
	
	protected def CharSequence observerHandler(VariableDefinition variable, String method)
		'''«IF variable.needsShadowEventMapping»
		if(«variable.access» != «NULL_LITERAL»)
		{
			«FOR e : variable.shadowEventsByScope.keySet.map[members].flatten.filter(Event)»
				«val outEvent = variable.getShadowEvent(e)»
				«IF outEvent !== null»sc_single_subscription_observer_«method»(&(«variable.getShadowEvent(e).accessObservable»), «e.asObservableGetter»(«variable.access»));«ENDIF»
			«ENDFOR»
		}
		«ENDIF»'''
	
}