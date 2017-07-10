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
import org.yakindu.scr.guardedexit.GuardedExitStatemachine;
import org.yakindu.scr.guardedexit.GuardedExitStatemachine.State;	

/**
 * Unit TestCase for GuardedExit
 */
@SuppressWarnings("all")
public class GuardedExit {
	
	private GuardedExitStatemachine statemachine;	
	
	@Before
	public void setUp() {
		statemachine = new GuardedExitStatemachine();
		statemachine.init();
	}

	@After
	public void tearDown() {
		statemachine = null;
	}
	
	@Test
	public void exitTaken() {
		statemachine.enter();
		assertTrue(statemachine.isStateActive(State.main_region_A));
		assertTrue(!statemachine.getGuard());
		checkDone(false);
	}
	@Test
	public void exitNotTaken() {
		statemachine.enter();
		assertTrue(statemachine.isStateActive(State.main_region_A));
		statemachine.setGuard(true);
		checkDone(true);
	}
	public void checkDone(boolean shouldBeDone) {
		statemachine.raiseE();
		statemachine.runCycle();
		assertTrue(statemachine.isStateActive(State.main_region_B));
		assertTrue(shouldBeDone ? statemachine.getDone()  : !statemachine.getDone());
	}
}
