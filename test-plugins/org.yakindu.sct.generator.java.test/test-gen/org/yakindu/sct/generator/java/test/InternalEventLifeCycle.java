
package org.yakindu.sct.generator.java.test;

import static org.mockito.Mockito.*;
import static org.mockito.Matchers.*;
import static org.hamcrest.CoreMatchers.*;
import org.yakindu.scr.internaleventlifecycle.IInternalEventLifeCycleStatemachine.*;	
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.junit.*;
import static org.junit.Assert.*;
import org.yakindu.scr.internaleventlifecycle.InternalEventLifeCycleStatemachine;
import org.yakindu.scr.internaleventlifecycle.InternalEventLifeCycleStatemachine.State;	
import org.yakindu.scr.VirtualTimer;
import org.yakindu.scr.VirtualTimer.VirtualTimeTask;
import org.yakindu.scr.VirtualTimer.CycleTimeEventTask;

/**
 * Unit TestCase for InternalEventLifeCycle
 */
@SuppressWarnings("all")
public class InternalEventLifeCycle {
	
	private InternalEventLifeCycleStatemachine statemachine;	
	private VirtualTimer timer;
	
	
	@Before
	public void internalEventLifeCycle_setUp() {
		statemachine = new InternalEventLifeCycleStatemachine();
		timer = new VirtualTimer(200);
		timer.schedulePeriodicalTask(new CycleTimeEventTask(statemachine), 200, 200);
		
		statemachine.init();
		
	}

	@After
	public void internalEventLifeCycle_tearDown() {
		statemachine = null;
		
		timer = null;
	}
	
	@Test
	public void internalEventLifeCycleTest() {
		statemachine.enter();
		assertTrue(statemachine.isStateActive(State.r1_A));
		assertTrue(statemachine.isStateActive(State.r2_C));
		statemachine.getSCInterface().raiseE();
		timer.cycleLeap(1);
		assertTrue(statemachine.isStateActive(State.r1_A));
		assertTrue(statemachine.isStateActive(State.r2_D));
		timer.cycleLeap(1);
		assertTrue(statemachine.isStateActive(State.r1_A));
		assertTrue(statemachine.isStateActive(State.r2_D));
		statemachine.getSCInterface().raiseF();
		timer.cycleLeap(1);
		assertTrue(statemachine.isStateActive(State.r1_A));
		assertTrue(statemachine.isStateActive(State.r2_C));
		timer.cycleLeap(1);
		assertTrue(statemachine.isStateActive(State.r1_A));
		assertTrue(statemachine.isStateActive(State.r2_C));
	}
}
