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
package org.yakindu.sct.generator.c.submodules.eventdriven

import com.google.inject.Inject
import com.google.inject.Singleton
import org.yakindu.sct.generator.c.extensions.EventNaming
import org.yakindu.sct.generator.c.submodules.StatechartTypes
import org.yakindu.sct.model.sexec.ExecutionFlow

/**
 * @author rbeckmann
 *
 */
@Singleton // Guice
class EventDrivenStatechartTypes extends StatechartTypes {
	@Inject protected extension EventNaming
	
	override statemachineStructContent(ExecutionFlow it) {
		'''
		«super.statemachineStructContent(it)»
		«IF hasLocalEvents»
		«eventQueueTypeName» internal_event_queue;
		«ENDIF»
		'''
	}
}