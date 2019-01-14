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
package org.yakindu.sct.generator.cpp.submodules.eventdriven

import com.google.inject.Inject
import org.yakindu.sct.generator.core.submodules.lifecycle.RunCycle
import org.yakindu.sct.generator.cpp.eventdriven.EventNaming
import org.yakindu.sct.generator.cpp.submodules.lifecycle.LifecycleFunctions
import org.yakindu.sct.model.sexec.ExecutionFlow

import static org.yakindu.sct.generator.cpp.CppGeneratorConstants.*

class EventDrivenRunCycle extends LifecycleFunctions implements RunCycle {
	@Inject extension EventNaming eventNaming
	
	override runCycle(ExecutionFlow it) {
		val cE = "currentEvent"
		if(!hasLocalEvents) {
			return super.runCycle(it)
		}
		'''
			void «module»::«runCycleFctID»()
			{
				«clearOutEventsFctID»();
				
				«SCT_EVENT» * «cE» = «nextEventFctID»();
				
				do
				{
					/* Set event flags as usual */
					«dispatchEventFctID»(«cE»);
					
					«runCycleFunctionForLoop»
					
					/* Delete event from memory */
					delete «cE»;
					«clearInEventsFctID»();
				} while((«cE» = «nextEventFctID»()));
			}
		'''
	}
}