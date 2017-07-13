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
import org.yakindu.scr.statechartlocalreactions.StatechartLocalReactionsStatemachine;
import org.yakindu.scr.statechartlocalreactions.StatechartLocalReactionsStatemachine.State;	

/**
 * Unit TestCase for StatechartLocalReactions
 */
@SuppressWarnings("all")
public class StatechartLocalReactions {
	
	private StatechartLocalReactionsStatemachine statemachine;	
	
	
	
	@Before
	public void statechartLocalReactions_setUp() {
		statemachine = new StatechartLocalReactionsStatemachine();
		statemachine.init();
	}

	@After
	public void statechartLocalReactions_tearDown() {
		statemachine = null;
	}
	
	@Test
	public void statechartLocalReactionsTest() {
		statemachine.enter();
		assertTrue(statemachine.isStateActive(State.main_region_S1));
		assertTrue(statemachine.isStateActive(State.region2_a));
		while (statemachine.getMyInt() < 10l) {
			assertTrue(statemachine.isStateActive(State.region2_a));
			if (statemachine.getMyInt()%2l == 0l) {
				assertTrue(statemachine.isStateActive(State.main_region_S1));
			}
			else {
				assertTrue(statemachine.isStateActive(State.main_region_S2));
			}
			statemachine.runCycle();
		}
	}
}
