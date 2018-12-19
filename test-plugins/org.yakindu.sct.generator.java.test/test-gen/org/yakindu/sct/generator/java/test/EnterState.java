
package org.yakindu.sct.generator.java.test;

import static org.mockito.Mockito.*;
import static org.mockito.Matchers.*;
import static org.hamcrest.CoreMatchers.*;
import org.yakindu.scr.enterstate.IEnterStateStatemachine.*;	
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.junit.*;
import static org.junit.Assert.*;
import org.yakindu.scr.enterstate.EnterStateStatemachine;
import org.yakindu.scr.enterstate.EnterStateStatemachine.State;	
import org.yakindu.scr.VirtualTimer;
import org.yakindu.scr.VirtualTimer.VirtualTimeTask;
import org.yakindu.scr.VirtualTimer.CycleTimeEventTask;

/**
 * Unit TestCase for EnterState
 */
@SuppressWarnings("all")
public class EnterState {
	
	private EnterStateStatemachine statemachine;	
	private VirtualTimer timer;
	
	
	@Before
	public void enterState_setUp() {
		statemachine = new EnterStateStatemachine();
		timer = new VirtualTimer(200);
		timer.schedulePeriodicalTask(new CycleTimeEventTask(statemachine), 200, 200);
		
		statemachine.init();
		
	}

	@After
	public void enterState_tearDown() {
		statemachine = null;
		
		timer = null;
	}
	
	@Test
	public void defaultEntry() {
		statemachine.enter();
		assertTrue(statemachine.isStateActive(State.r_A));
		statemachine.getSCInterface().raiseE();
		timer.cycleLeap(1);
		assertTrue(statemachine.isStateActive(State.r_B_r_E));
	}
	
	@Test
	public void namedEntryThroughNamedTransition() {
		statemachine.enter();
		assertTrue(statemachine.isStateActive(State.r_A));
		statemachine.getSCInterface().raiseF();
		timer.cycleLeap(1);
		assertTrue(statemachine.isStateActive(State.r_B_r_F));
	}
	
	@Test
	public void namedEntryThroughDefaultTransition() {
		statemachine.enter();
		assertTrue(statemachine.isStateActive(State.r_A));
		statemachine.getSCInterface().raiseG();
		timer.cycleLeap(1);
		assertTrue(statemachine.isStateActive(State.r_B_r_E));
	}
}
