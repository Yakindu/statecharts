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

package org.yakindu.sct.generator.java.test;

import org.junit.*;
import static org.junit.Assert.*;
import org.yakindu.scr.internaleventlifecycle.InternalEventLifeCycleStatemachine;
import org.yakindu.scr.internaleventlifecycle.InternalEventLifeCycleStatemachine.State;	

/**
 * Unit TestCase for InternalEventLifeCycle
 */
@SuppressWarnings("all")
public class InternalEventLifeCycle {
	
	private InternalEventLifeCycleStatemachine statemachine;	
	
	
	
	@Before
	public void internalEventLifeCycle_setUp() {
		statemachine = new InternalEventLifeCycleStatemachine();
		statemachine.init();
	}

	@After
	public void internalEventLifeCycle_tearDown() {
		statemachine = null;
	}
	
	@Test
	public void internalEventLifeCycleTest() {
		statemachine.enter();
		assertTrue(statemachine.isStateActive(State.r1_A));
		assertTrue(statemachine.isStateActive(State.r2_C));
		statemachine.raiseE();
		statemachine.runCycle();
		assertTrue(statemachine.isStateActive(State.r1_A));
		assertTrue(statemachine.isStateActive(State.r2_D));
		statemachine.runCycle();
		assertTrue(statemachine.isStateActive(State.r1_A));
		assertTrue(statemachine.isStateActive(State.r2_D));
		statemachine.raiseF();
		statemachine.runCycle();
		assertTrue(statemachine.isStateActive(State.r1_A));
		assertTrue(statemachine.isStateActive(State.r2_C));
		statemachine.runCycle();
		assertTrue(statemachine.isStateActive(State.r1_A));
		assertTrue(statemachine.isStateActive(State.r2_C));
	}
}
