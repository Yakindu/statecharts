/**
  Copyright (c) 2018 committers of YAKINDU and others.
  All rights reserved. This program and the accompanying materials
  are made available under the terms of the Eclipse Public License v1.0
  which accompanies this distribution, and is available at
  http://www.eclipse.org/legal/epl-v10.html
  Contributors:
  	René Beckmann - refactored from Statemachine
*/
package org.yakindu.sct.generator.java.submodules

import com.google.inject.Inject
import com.google.inject.Singleton
import org.yakindu.sct.generator.java.GenmodelEntries
import org.yakindu.sct.generator.java.JavaNamingService
import org.yakindu.sct.generator.java.Naming
import org.yakindu.sct.generator.java.features.Synchronized
import org.yakindu.sct.model.sexec.ExecutionFlow
import org.yakindu.sct.model.sexec.extensions.SExecExtensions
import org.yakindu.sct.model.sgen.GeneratorEntry
import org.yakindu.sct.model.stext.stext.InterfaceScope

@Singleton
class InterfaceFunctionsGenerator {
	
	@Inject extension Naming
	@Inject extension JavaNamingService
	@Inject extension GenmodelEntries
	@Inject extension SExecExtensions
	@Inject extension EventCode
	@Inject extension VariableCode
	@Inject extension Synchronized
	
	def interfaceClasses(ExecutionFlow it, GeneratorEntry entry) '''
		«FOR scope : flow.interfaceScopes»
			«scope.toImplementation(entry)»
			
		«ENDFOR»
	'''
	
	def toImplementation(InterfaceScope scope, GeneratorEntry entry) '''
		protected class «scope.getInterfaceImplName» implements «scope.getInterfaceName» {
		
			«IF entry.createInterfaceObserver && scope.hasOutgoingEvents»
				«scope.generateListenerSupport»
			«ENDIF»
			«IF scope.hasOperations»
				«scope.generateOperationCallback»
			«ENDIF»
			«FOR event : scope.eventDefinitions»
				«generateEventDefinition(event, entry, scope)»
				
			«ENDFOR»
			«FOR variable : scope.variableDefinitions»
				«variable.code»
				
			«ENDFOR»
		}
	'''
		
	protected def generateOperationCallback(InterfaceScope scope) '''
		private «scope.getInterfaceOperationCallbackName()» operationCallback;
		
		public «sync»void set«scope.getInterfaceOperationCallbackName»(
				«scope.getInterfaceOperationCallbackName» operationCallback) {
			this.operationCallback = operationCallback;
		}
	'''
	
	protected def generateListenerSupport(InterfaceScope scope) '''
		private List<«scope.getInterfaceListenerName»> listeners = new LinkedList<«scope.getInterfaceListenerName»>();
		
		public List<«scope.getInterfaceListenerName»> getListeners() {
			return listeners;
		}
	'''
}