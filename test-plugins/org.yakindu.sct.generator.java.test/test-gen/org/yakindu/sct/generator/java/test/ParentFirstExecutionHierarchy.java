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
import org.yakindu.scr.parentfirstexecutionhierarchy.ParentFirstExecutionHierarchyStatemachine;
import org.yakindu.scr.parentfirstexecutionhierarchy.ParentFirstExecutionHierarchyStatemachine.State;

/**
 * Unit TestCase for ParentFirstExecutionHierarchy
 */
@SuppressWarnings("all")
public class ParentFirstExecutionHierarchy {
	private ParentFirstExecutionHierarchyStatemachine statemachine;	
	
	
	@Before
	public void setUp() {
		statemachine = new ParentFirstExecutionHierarchyStatemachine();
		statemachine.init();
	}

	@After
	public void tearDown() {
		statemachine = null;
	}
	
	@Test
	public void childShouldTakeTransition() {
		statemachine.enter();;
		assertTrue(statemachine.isStateActive(State.parentFirstExecutionHierarchy_r_A_r_AA_r_AAA));
		statemachine.setDisable_a(true);
		statemachine.setDisable_aa(true);
		statemachine.raiseE();
		statemachine.runCycle();
		;
		assertTrue(statemachine.isStateActive(State.parentFirstExecutionHierarchy_r_B));
		assertTrue(!statemachine.getA_reacted());
		assertTrue(!statemachine.getAa_reacted());
		assertTrue(statemachine.getAaa_reacted());
		assertTrue(statemachine.getA_local());
		assertTrue(statemachine.getAa_local());
		assertTrue(!statemachine.getAaa_local());
	}
	@Test
	public void parentShouldTakeTransition() {
		statemachine.enter();;
		assertTrue(statemachine.isStateActive(State.parentFirstExecutionHierarchy_r_A_r_AA_r_AAA));
		statemachine.setDisable_a(true);
		statemachine.raiseE();
		statemachine.runCycle();
		;
		assertTrue(statemachine.isStateActive(State.parentFirstExecutionHierarchy_r_B));
		assertTrue(!statemachine.getA_reacted());
		assertTrue(statemachine.getAa_reacted());
		assertTrue(!statemachine.getAaa_reacted());
		assertTrue(statemachine.getA_local());
		assertTrue(!statemachine.getAa_local());
		assertTrue(!statemachine.getAaa_local());
	}
	@Test
	public void grandparentShouldTakeTransition() {
		statemachine.enter();;
		assertTrue(statemachine.isStateActive(State.parentFirstExecutionHierarchy_r_A_r_AA_r_AAA));
		statemachine.raiseE();
		statemachine.runCycle();
		;
		assertTrue(statemachine.isStateActive(State.parentFirstExecutionHierarchy_r_B));
		assertTrue(statemachine.getA_reacted());
		assertTrue(!statemachine.getAa_reacted());
		assertTrue(!statemachine.getAaa_reacted());
		assertNoLocalReaction();
	}
	@Test
	public void expectLocalReactrionsExecuteWithNoTransition() {
		statemachine.enter();;
		assertTrue(statemachine.isStateActive(State.parentFirstExecutionHierarchy_r_A_r_AA_r_AAA));
		statemachine.runCycle();
		;
		assertTrue(statemachine.isStateActive(State.parentFirstExecutionHierarchy_r_A_r_AA_r_AAA));
		assertTrue(statemachine.getA_local());
		assertTrue(statemachine.getAa_local());
		assertTrue(statemachine.getAaa_local());
	}
	public void assertNoLocalReaction() {
		assertTrue(!statemachine.getA_local());
		assertTrue(!statemachine.getAa_local());
		assertTrue(!statemachine.getAaa_local());
	}
}
