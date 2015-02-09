/**
 * Copyright (c) 2015 committers of YAKINDU and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     committers of YAKINDU - initial API and implementation
 */

package org.yakindu.sct.generator.java.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.yakindu.scr.dynamicchoice.DynamicChoiceStatemachine;
import org.yakindu.scr.dynamicchoice.DynamicChoiceStatemachine.State;
/**
 *  Unit TestCase for DynamicChoice
 */
@SuppressWarnings("all")
public class DynamicChoiceTest {

	private DynamicChoiceStatemachine statemachine;

	@Before
	public void setUp() {
		statemachine = new DynamicChoiceStatemachine();
		statemachine.init();
	}

	@After
	public void tearDown() {
		statemachine = null;
	}

	@Test
	public void testDynamicChoiceTest() {
		statemachine.enter();
		assertTrue(statemachine.isStateActive(State.main_region_Start));
		statemachine.runCycle();
		assertTrue(statemachine.isStateActive(State.main_region_A));
	}
}
