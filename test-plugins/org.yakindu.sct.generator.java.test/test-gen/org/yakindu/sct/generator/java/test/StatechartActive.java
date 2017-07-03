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
import org.yakindu.scr.statechartactive.StatechartActiveStatemachine;
import org.yakindu.scr.statechartactive.StatechartActiveStatemachine.State;

/**
 * Unit TestCase for StatechartActive
 */
@SuppressWarnings("all")
public class StatechartActive {
	private StatechartActiveStatemachine statemachine;	
	
	@Before
	public void setUp() {
		statemachine = new StatechartActiveStatemachine();
		statemachine.init();
	}

	@After
	public void tearDown() {
		statemachine = null;
	}
	
	@Test
	public void inactiveBeforeEnter() {
		assertTrue(!statemachine.isActive());
	}
	@Test
	public void activeAfterEnter() {
		statemachine.enter();;
		assertTrue(statemachine.isActive());
	}
	@Test
	public void inactiveAfterExit() {
		statemachine.enter();;
		statemachine.exit();;
		assertTrue(!statemachine.isActive());
	}
	@Test
	public void activeAfterReenter() {
		statemachine.enter();;
		statemachine.exit();;
		statemachine.enter();;
		assertTrue(statemachine.isActive());
	}
}
