/**
  Copyright (c) 2012 committers of YAKINDU and others.
  All rights reserved. This program and the accompanying materials
  are made available under the terms of the Eclipse Public License v1.0
  which accompanies this distribution, and is available at
  http://www.eclipse.org/legal/epl-v10.html
  Contributors:
  	Markus Muehlbrandt - Initial contribution and API
  	Andreas Muelder - Added generation of constants
*/
package org.yakindu.sct.generator.java.files

import com.google.inject.Inject
import java.util.Set
import java.util.TreeSet
import org.eclipse.xtext.generator.IFileSystemAccess
import org.yakindu.base.types.typesystem.ITypeSystem
import org.yakindu.sct.generator.core.types.ICodegenTypeSystemAccess
import org.yakindu.sct.generator.java.FlowCode
import org.yakindu.sct.generator.java.GenmodelEntries
import org.yakindu.sct.generator.java.JavaIncludeProvider
import org.yakindu.sct.generator.java.JavaNamingService
import org.yakindu.sct.generator.java.Naming
import org.yakindu.sct.generator.java.submodules.EventCode
import org.yakindu.sct.generator.java.submodules.InterfaceFunctionsGenerator
import org.yakindu.sct.generator.java.submodules.InternalFunctionsGenerator
import org.yakindu.sct.generator.java.submodules.StatemachineFunctionsGenerator
import org.yakindu.sct.generator.java.submodules.eventdriven.EventDrivenTimingFunctions
import org.yakindu.sct.generator.java.submodules.lifecycle.Enter
import org.yakindu.sct.generator.java.submodules.lifecycle.Exit
import org.yakindu.sct.generator.java.submodules.lifecycle.Init
import org.yakindu.sct.generator.java.submodules.lifecycle.IsActive
import org.yakindu.sct.generator.java.submodules.lifecycle.IsFinal
import org.yakindu.sct.generator.java.submodules.lifecycle.IsStateActive
import org.yakindu.sct.generator.java.submodules.lifecycle.RunCycle
import org.yakindu.sct.generator.java.templates.ClassTemplate
import org.yakindu.sct.generator.java.templates.FileTemplate
import org.yakindu.sct.model.sexec.ExecutionFlow
import org.yakindu.sct.model.sexec.extensions.SExecExtensions
import org.yakindu.sct.model.sexec.extensions.StateVectorExtensions
import org.yakindu.sct.model.sgen.GeneratorEntry

class Statemachine {
	@Inject protected Set<JavaIncludeProvider> includeProviders
	@Inject protected extension Naming
	@Inject protected extension JavaNamingService
	@Inject protected extension GenmodelEntries
	@Inject protected extension SExecExtensions
	@Inject protected extension ICodegenTypeSystemAccess
	@Inject protected extension ITypeSystem
	@Inject protected extension FlowCode
	@Inject protected extension StateVectorExtensions
	
	@Inject protected extension EventCode
	@Inject protected extension InterfaceFunctionsGenerator
	@Inject protected extension InternalFunctionsGenerator
	@Inject protected extension StatemachineFunctionsGenerator
	@Inject protected extension EventDrivenTimingFunctions
	
	@Inject protected extension Init
	@Inject protected extension Enter
	@Inject protected extension Exit
	@Inject protected extension RunCycle
	@Inject protected extension IsActive
	@Inject protected extension IsStateActive
	@Inject protected extension IsFinal
	
	protected ExecutionFlow flow
	protected GeneratorEntry entry
		
	def generateStatemachine(ExecutionFlow flow, GeneratorEntry entry, IFileSystemAccess fsa) {
		this.flow = flow
		this.entry = entry
		var filename = flow.getImplementationPackagePath(entry) + '/' + flow.statemachineClassName.java
		fsa.generateFile(filename, content)
	}
	
	def protected content() { 
		FileTemplate
			.create
			.fileComment(entry.licenseText)
			.packageName(getImplementationPackageName(flow, entry))
			.addImports(imports)
			.addImports(includeProviders.map[getImports(flow)].flatten)
			.classTemplate(
				ClassTemplate
					.create
					.className(flow.statemachineClassName)
					.addInterface(flow.statemachineInterfaceName)
					.classContent(
						classContent
					)
			)
			.generate
	}
	
	def protected classContent() {
		'''
		«flow.createFieldDeclarations(entry)»
		«flow.createConstructor»
		«flow.init»
		«flow.enter»
		«flow.runCycle»
		«flow.exit»
		«flow.isActive»
		«flow.isFinal»
		«flow.clearInEvents»
		«flow.clearOutEvents»
		«flow.isStateActive»
		«flow.timingFunctions»
		«flow.interfaceAccessors(entry)»
		«flow.internalScopeFunctions»
		«flow.defaultInterfaceFunctions(entry)»
		«flow.functionImplementations»
		'''
	}
	
	def protected Set<CharSequence> imports() {
		// we need a sorted set for the imports
		val Set<CharSequence> importSet = new TreeSet
		val String JavaList = "java.util.List"
		val String JavaLinkedList = "java.util.LinkedList"
		
		if (entry.createInterfaceObserver && flow.hasOutgoingEvents) {
			importSet += JavaList
			importSet += JavaLinkedList
		}
		
		if (flow.timed) {
			importSet += "" + entry.getBasePackageName() + ".ITimer"
		}
		
		for(JavaIncludeProvider jip : includeProviders) {
			importSet += jip.getImports(flow).map[toString]
		}
		
		if (tracingUsed(entry)) {
			importSet += entry.getBasePackageName() + "." + traceInterface
			importSet += JavaList
			importSet += JavaLinkedList
		}
		return importSet
	}
}
