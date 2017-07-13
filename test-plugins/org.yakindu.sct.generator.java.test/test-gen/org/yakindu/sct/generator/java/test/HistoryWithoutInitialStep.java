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
import org.yakindu.scr.historywithoutinitialstep.HistoryWithoutInitialStepStatemachine;
import org.yakindu.scr.historywithoutinitialstep.HistoryWithoutInitialStepStatemachine.State;	

/**
 * Unit TestCase for HistoryWithoutInitialStep
 */
@SuppressWarnings("all")
public class HistoryWithoutInitialStep {
	
	private HistoryWithoutInitialStepStatemachine statemachine;	
	
	
	
	@Before
	public void historyWithoutInitialStep_setUp() {
		statemachine = new HistoryWithoutInitialStepStatemachine();
		statemachine.init();
	}

	@After
	public void historyWithoutInitialStep_tearDown() {
		statemachine = null;
	}
	
	@Test
	public void enterThroughInitialEntry() {
		init();
		assertTrue(statemachine.isStateActive(State.main_region_B_r1_C));
		statemachine.raiseNext();
		statemachine.runCycle();
		assertTrue(statemachine.isStateActive(State.main_region_B_r1_D));
	}
	@Test
	public void enterCThroughHistory() {
		init();
		assertTrue(statemachine.isStateActive(State.main_region_B_r1_C));
		statemachine.raiseToA();
		statemachine.runCycle();
		assertTrue(statemachine.isStateActive(State.main_region_A));
		statemachine.raiseToHistory();
		statemachine.runCycle();
		assertTrue(statemachine.isStateActive(State.main_region_B_r1_C));
	}
	@Test
	public void enterDThroughHistory() {
		init();
		statemachine.raiseNext();
		statemachine.runCycle();
		assertTrue(statemachine.isStateActive(State.main_region_B_r1_D));
		statemachine.raiseToA();
		statemachine.runCycle();
		assertTrue(statemachine.isStateActive(State.main_region_A));
		statemachine.raiseToHistory();
		statemachine.runCycle();
		assertTrue(statemachine.isStateActive(State.main_region_B_r1_D));
	}
	public void init() {
		statemachine.enter();
		assertTrue(statemachine.isStateActive(State.main_region_A));
		statemachine.raiseToB();
		statemachine.runCycle();
	}
}
