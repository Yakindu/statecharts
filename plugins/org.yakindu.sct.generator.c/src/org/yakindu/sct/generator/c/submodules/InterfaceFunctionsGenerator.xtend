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
import org.yakindu.sct.generator.c.extensions.Naming
import org.yakindu.sct.generator.core.types.ICodegenTypeSystemAccess
import org.yakindu.sct.model.sexec.ExecutionFlow
import org.yakindu.sct.model.sexec.extensions.SExecExtensions

/**
 * @author rbeckmann
 *
 */
@Singleton // Guice
class InterfaceFunctionsGenerator {
	@Inject protected extension SExecExtensions
	@Inject protected extension Naming
	@Inject protected extension ICodegenTypeSystemAccess
	@Inject protected extension EventCode
	
	def interfaceFunctions(ExecutionFlow it) '''
		«FOR scope : interfaceScopes»
			«FOR event : scope.incomingEvents»
				«interfaceIncomingEventRaiser(event)»
			«ENDFOR»
			
			«FOR event : scope.outgoingEvents»
				«interfaceOutgoingEventGetter(event)»
				«IF event.hasValue» 
					«interfaceOutgoingEventValueGetter(event)»
				«ENDIF»
			«ENDFOR»
			
			«FOR variable : scope.variableDefinitions»
				«IF variable.const»const «ENDIF»«variable.typeSpecifier.targetLanguageName» «variable.asGetter»(const «scHandleDecl»)
				{
					return «variable.access»;
				}
				«IF !variable.readonly && !variable.const»
				void «variable.asSetter»(«scHandleDecl», «variable.typeSpecifier.targetLanguageName» value)
				{
					«variable.access» = value;
				}
				«ENDIF»
			«ENDFOR»
		«ENDFOR»
	'''
}