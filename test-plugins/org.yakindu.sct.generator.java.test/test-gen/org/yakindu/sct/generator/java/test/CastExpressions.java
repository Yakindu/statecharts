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
import org.yakindu.scr.castexpressions.CastExpressionsStatemachine;
import org.yakindu.scr.castexpressions.CastExpressionsStatemachine.State;	

/**
 * Unit TestCase for CastExpressions
 */
@SuppressWarnings("all")
public class CastExpressions {
	
	private CastExpressionsStatemachine statemachine;	
	
	
	
	@Before
	public void castExpressions_setUp() {
		statemachine = new CastExpressionsStatemachine();
		statemachine.init();
	}

	@After
	public void castExpressions_tearDown() {
		statemachine = null;
	}
	
	@Test
	public void castExpressionTest() {
		statemachine.enter();
		assertTrue(statemachine.getRealValue() == 5l);
		assertTrue(statemachine.getIntValue() == 5l);
		statemachine.runCycle();
		assertTrue(statemachine.getRealValue() == 15l);
		statemachine.runCycle();
		assertTrue(statemachine.isStateActive(State.main_region_C));
		assertTrue(statemachine.getRealValue() == 757l);
	}
}
