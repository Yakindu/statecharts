/**
 * Copyright (c) 2017 committers of YAKINDU and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     committers of YAKINDU - initial API and implementation
 */
package org.yakindu.sct.generator.c.submodules.eventdriven

import com.google.inject.Inject
import org.yakindu.sct.generator.c.CGeneratorConstants
import org.yakindu.sct.generator.c.IGenArtifactConfigurations
import org.yakindu.sct.generator.c.extensions.EventNaming
import org.yakindu.sct.generator.c.extensions.Naming
import org.yakindu.sct.generator.core.types.ICodegenTypeSystemAccess
import org.yakindu.sct.model.sexec.ExecutionFlow
import org.yakindu.sct.model.sexec.extensions.SExecExtensions
import org.yakindu.sct.model.sexec.naming.INamingService
import org.yakindu.sct.model.sgen.GeneratorEntry
import org.yakindu.sct.generator.c.IHeaderFragment

class EventDrivenStatemachineHeaderFragment implements IHeaderFragment {
	@Inject protected extension Naming
	@Inject protected extension SExecExtensions
	@Inject protected extension ICodegenTypeSystemAccess
	@Inject protected extension INamingService
	@Inject extension EventNaming
	@Inject extension EventDrivenStatechartTypes
	
	override defines(ExecutionFlow it, GeneratorEntry entry, IGenArtifactConfigurations artifactConfigs) {
		'''
		#ifndef «bufferSize»
		#define «bufferSize» «CGeneratorConstants.EVENT_QUEUE_BUFFER_SIZE»
		#endif
		#ifndef «invalidEvent»
		#define «invalidEvent» 0
		#endif
		'''
	}
	
	override fileComment(ExecutionFlow it, GeneratorEntry entry, IGenArtifactConfigurations artifactConfigs) {
		''''''
	}
	
	override functions(ExecutionFlow it, GeneratorEntry entry, IGenArtifactConfigurations artifactConfigs) {
		''''''
	}
	
	override includes(ExecutionFlow it, GeneratorEntry entry, IGenArtifactConfigurations artifactConfigs) {
		''''''
	}
	
	override types(ExecutionFlow it, GeneratorEntry entry, IGenArtifactConfigurations artifactConfigs) {
		'''
		«generateEventValueUnion»

		«IF hasLocalEvents»
		«generateEventsEnum»
		
		«generateEventStruct»
		
		«generateEventQueue»
		«ENDIF»
		'''
	}
}
