/** Generated by YAKINDU Statechart Tools code generator. */

package org.yakindu.sct.generator.java.test;

import org.junit.*;
import static org.junit.Assert.*;
import org.yakindu.scr.eventdriven.NoLocalEventsStatemachine;
import org.yakindu.scr.eventdriven.NoLocalEventsStatemachine.State;

/**
 * Unit TestCase for NoLocalEvents
 */
@SuppressWarnings("all")
public class NoLocalEvents {
	
	private NoLocalEventsStatemachine statemachine;	
	
	
	@Before
	public void noLocalEvents_setUp() {
		statemachine = new NoLocalEventsStatemachine();
		
		statemachine.init();
		
	}

	@After
	public void noLocalEvents_tearDown() {
		statemachine = null;
		
	}
	
	@Test
	public void test() {
		statemachine.enter();
		assertTrue(statemachine.isStateActive(NoLocalEventsStatemachine.State.noLocalEvents_main_region_StateA));
		statemachine.raiseE();
		assertTrue(statemachine.isStateActive(NoLocalEventsStatemachine.State.noLocalEvents_main_region_StateB));
		statemachine.raiseE();
		assertTrue(statemachine.isStateActive(NoLocalEventsStatemachine.State.noLocalEvents_main_region_StateA));
		assertTrue((statemachine.getX() == 0l));
		statemachine.raiseI(42l);
		assertTrue(statemachine.isStateActive(NoLocalEventsStatemachine.State.noLocalEvents_main_region_StateB));
		assertTrue((statemachine.getX() == 42l));
		statemachine.exit();
	}
}
