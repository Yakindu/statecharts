/**
  Copyright (c) 2013 committers of YAKINDU and others.
  All rights reserved. This program and the accompanying materials
  are made available under the terms of the Eclipse Public License v1.0
  which accompanies this distribution, and is available at
  http://www.eclipse.org/legal/epl-v10.html
  Contributors:
  	Markus Mühlbrandt - Initial contribution and API
 */
package org.yakindu.sct.generator.cpp

import org.yakindu.sct.model.sexec.ExecutionFlow
import org.yakindu.sct.model.sgen.GeneratorEntry
import org.eclipse.xtext.generator.IFileSystemAccess
import com.google.inject.Inject
import org.yakindu.sct.generator.c.GenmodelEntries
import org.yakindu.sct.generator.core.library.IOutletFeatureHelper
import org.yakindu.sct.generator.core.impl.IExecutionFlowGenerator

class TimerInterface {
	
	@Inject
	extension Naming
	
	@Inject
	extension GenmodelEntries
	
	@Inject
	protected IOutletFeatureHelper outletFeatureHelper;
	
	def generateITimerService(ExecutionFlow flow, GeneratorEntry entry, IFileSystemAccess fsa) {
		if (outletFeatureHelper.getLibraryTargetFolderValue(entry) != null)
			fsa.generateFile(timerInterface.h, IExecutionFlowGenerator.LIBRARY_TARGET_FOLDER_OUTPUT,
				flow.content(entry))
		else
			fsa.generateFile(timerInterface.h, flow.content(entry))
	}
	
	def protected content(ExecutionFlow it, GeneratorEntry entry) {
		'''
		«entry.licenseText»
		
		#ifndef «timerInterface.define»_H_
		#define «timerInterface.define»_H_
		
		#include "sc_types.h"
		
		//forward declaration of TimedStatemachineInterface to avoid cyclic dependency
		class TimedStatemachineInterface;
		
		/*
		 * Basic interface for state machines.
		 */
		class «timerInterface»
		{
			public:
				
				virtual ~«timerInterface»() = 0;
			
				/*
				 * Starts the timing for a time event.
				 */ 
				virtual void setTimer(«timedStatemachineInterface»* statemachine, sc_eventid event, sc_integer time, sc_boolean isPeriodic) = 0;
				
				/*
				 * Unsets the given time event.
				 */
				virtual void unsetTimer(«timedStatemachineInterface»* statemachine, sc_eventid event) = 0;
			
				/*
				 * Cancel timer service. Use this to end possible timing threads and free
				 * memory resources.
				 */
				virtual void cancel() = 0;
		};
		
		inline TimerInterface::~TimerInterface() {}
		
		#endif /* «timerInterface.define»_H_ */
		'''
	}
}