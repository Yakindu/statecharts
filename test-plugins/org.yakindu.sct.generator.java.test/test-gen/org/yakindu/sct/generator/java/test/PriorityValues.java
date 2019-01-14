
package org.yakindu.sct.generator.java.test;

import org.junit.*;
import static org.junit.Assert.*;
import org.yakindu.scr.priorityvalues.PriorityValuesStatemachine;
import org.yakindu.scr.priorityvalues.PriorityValuesStatemachine.State;	
import org.yakindu.scr.VirtualTimer;
import org.yakindu.scr.VirtualTimer.VirtualTimeTask;
import org.yakindu.scr.VirtualTimer.CycleTimeEventTask;

/**
 * Unit TestCase for PriorityValues
 */
@SuppressWarnings("all")
public class PriorityValues {
	
	private PriorityValuesStatemachine statemachine;	
	private VirtualTimer timer;
	
	
	@Before
	public void priorityValues_setUp() {
		statemachine = new PriorityValuesStatemachine();
		timer = new VirtualTimer(200);
		timer.schedulePeriodicalTask(new CycleTimeEventTask(statemachine), 200, 200);
		
		statemachine.init();
		
	}

	@After
	public void priorityValues_tearDown() {
		statemachine = null;
		
		timer = null;
	}
	
	@Test
	public void transitionPriority() {
		statemachine.enter();
		assertTrue(statemachine.isStateActive(State.main_region_A));
		statemachine.getSCInterface().raiseEvent1();
		timer.cycleLeap(1);
		assertTrue(statemachine.isStateActive(State.main_region_C));
	}
	
	@Test
	public void regionPriority() {
		statemachine.enter();
		assertTrue(statemachine.isStateActive(State.someRegion_A));
		statemachine.getSCInterface().raiseEvent2();
		timer.cycleLeap(1);
		assertTrue(statemachine.isStateActive(State.someRegion_B));
		assertTrue(!statemachine.isStateActive(State.main_region_E));
	}
}
