/**
 * Copyright (c) 2018-2020 committers of YAKINDU and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 * 	rbeckmann - initial API and implementation
 *
 */
package org.yakindu.sct.generator.c;

/**
 * @author rbeckmann
 * @author axel terfloth
 *
 */
public abstract class CGeneratorConstants {
	
	/*
	 * TYPES
	 */
	public static final String BOOL_TYPE = "sc_boolean";
	public static final String STRING_TYPE = "sc_string";
	public static final String INT_TYPE = "sc_integer";
	public static final String UINT_TYPE = "sc_uinteger";
	public static final String SHORT_TYPE = "sc_short";
	public static final String USHORT_TYPE = "sc_ushort";
	public static final String REAL_TYPE = "sc_real";
	public static final String EVENT_TYPE = "sc_eventid";
	public static final String INTPTR_TYPE = "sc_intptr_t";
	public static final String OBSERVABLE_TYPE = "sc_observable";
	public static final String SINGLE_SUBSCRIPTION_OBSERVER_TYPE = "sc_single_subscription_observer";
	public static final String OBSERVER_NEXT_FP_TYPE = "sc_observer_next_fp";
		
	
	public static final String TYPES_MODULE = "sc_types";

	/*
	 * FUNCTIONS
	 */
	public static final String RAISE_TIME_EVENT = "raiseTimeEvent";
	public static final String IS_STATE_ACTIVE = "isStateActive";
	public static final String IS_ACTIVE = "isActive";
	public static final String IS_FINAL = "isFinal";
	public static final String INIT = "init";
	public static final String ENTER = "enter";
	public static final String EXIT = "exit";
	public static final String RUN_CYCLE = "runCycle";
	public static final String SET_TIMER = "setTimer";
	public static final String UNSET_TIMER = "unsetTimer";
	public static final String STATE_ENTERED = "stateEntered";
	public static final String STATE_EXITED = "stateExited";

	/*
	 * VARIABLES
	 */
	public static final String STATEVECTOR = "stateConfVector";
	public static final String STATEVECTOR_POS = "stateConfVectorPosition";
	public static final String HISTORYVECTOR = "historyVector";
	public static final String HANDLE = "handle";

	/*
	 * MISC
	 */
	public static final int EVENT_QUEUE_BUFFER_SIZE = 20;
	public static final String NO_EVENT = "SC_NO_EVENT";
	
	/*
	 * TRACING
	 */
	public static final String TRACE_HANDLER_TYPE = "sc_trace_handler";
	public static final String TRACE_HANDLER = "trace_handler";
	public static final String TRACING_MODULE = "sc_tracing";
	public static final String RXC_MODULE = "sc_rxc";
	public static final String INIT_TRACING = "initWithTracing";
	public static final String SET_TRACING = "setTraceHandler";
	public static final String TRACE_CALL = "SC_TRACE";
	
	public static final String META_MODULE = "Meta";
	
	public static final String TRACE_MACHINE_ENTER = "sc_trace_machine_enter";
	public static final String TRACE_MACHINE_EXIT = "sc_trace_machine_exit";
	public static final String TRACE_MACHINE_CYCLE_START = "sc_trace_machine_run_cycle_start";
	public static final String TRACE_MACHINE_CYCLE_END = "sc_trace_machine_run_cycle_end";
	public static final String TRACE_MACHINE_ENTERED = "sc_trace_state_entered";
	public static final String TRACE_MACHINE_EXITED = "sc_trace_state_exited";
	public static final String TRACE_MACHINE_EVENT_RAISED = "sc_trace_event_raised";
	public static final String TRACE_MACHINE_VARIABLE_SET = "sc_trace_variable_set";
	public static final String TRACE_MACHINE_TIME_EVENT_RAISED = "sc_trace_time_event_raised";
	public static final String TRACE_MACHINE_TIME_EVENT_SET = "sc_trace_time_event_set";
	public static final String TRACE_MACHINE_TIME_EVENT_UNSET = "sc_trace_time_event_unset";
	
	
}
