/** Generated by YAKINDU Statechart Tools code generator. */

package org.yakindu.sct.generator.java.test;

import org.junit.*;
import static org.junit.Assert.*;
import org.yakindu.scr.internaleventlifecycleunbuffered.InternalEventLifeCycleStatemachine;
import org.yakindu.scr.internaleventlifecycleunbuffered.InternalEventLifeCycleStatemachine.State;	
import org.yakindu.scr.VirtualTimer;
import org.yakindu.scr.VirtualTimer.VirtualTimeTask;
import org.yakindu.scr.VirtualTimer.CycleTimeEventTask;

/**
 * Unit TestCase for InternalEventLifeCycle
 */
@SuppressWarnings("all")
public class InternalEventLifeCycleUnbuffered {
	
	private InternalEventLifeCycleStatemachine statemachine;	
	private VirtualTimer timer;
	
	
	@Before
	public void internalEventLifeCycleUnbuffered_setUp() {
		statemachine = new InternalEventLifeCycleStatemachine();
		timer = new VirtualTimer(200);
		timer.schedulePeriodicalTask(new CycleTimeEventTask(statemachine), 200, 200);
		
		statemachine.init();
		
	}

	@After
	public void internalEventLifeCycleUnbuffered_tearDown() {
		statemachine = null;
		
		timer = null;
	}
	
	@Test
	public void internalEventLifeCycleUnbufferedTest() {
		statemachine.enter();
		assertTrue(statemachine.isStateActive(State.internalEventLifeCycle_r1_A));
		assertTrue(statemachine.isStateActive(State.internalEventLifeCycle_r2_C));
		statemachine.raiseE();
		timer.cycleLeap(1l);
		assertTrue(statemachine.isStateActive(State.internalEventLifeCycle_r1_A));
		assertTrue(statemachine.isStateActive(State.internalEventLifeCycle_r2_D));
		timer.cycleLeap(1l);
		assertTrue(statemachine.isStateActive(State.internalEventLifeCycle_r1_A));
		assertTrue(statemachine.isStateActive(State.internalEventLifeCycle_r2_D));
		statemachine.raiseF();
		timer.cycleLeap(1l);
		assertTrue(statemachine.isStateActive(State.internalEventLifeCycle_r1_A));
		assertTrue(statemachine.isStateActive(State.internalEventLifeCycle_r2_C));
		timer.cycleLeap(1l);
		assertTrue(statemachine.isStateActive(State.internalEventLifeCycle_r1_A));
		assertTrue(statemachine.isStateActive(State.internalEventLifeCycle_r2_C));
	}
}
