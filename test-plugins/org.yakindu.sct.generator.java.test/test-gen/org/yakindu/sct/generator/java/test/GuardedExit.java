
package org.yakindu.sct.generator.java.test;

import static org.mockito.Mockito.*;
import static org.mockito.Matchers.*;
import static org.hamcrest.CoreMatchers.*;
import org.yakindu.scr.guardedexit.IGuardedExitStatemachine.*;	
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.junit.*;
import static org.junit.Assert.*;
import org.yakindu.scr.guardedexit.GuardedExitStatemachine;
import org.yakindu.scr.guardedexit.GuardedExitStatemachine.State;	
import org.yakindu.scr.VirtualTimer;
import org.yakindu.scr.VirtualTimer.VirtualTimeTask;
import org.yakindu.scr.VirtualTimer.CycleTimeEventTask;

/**
 * Unit TestCase for GuardedExit
 */
@SuppressWarnings("all")
public class GuardedExit {
	
	private GuardedExitStatemachine statemachine;	
	private VirtualTimer timer;
	
	
	@Before
	public void guardedExit_setUp() {
		statemachine = new GuardedExitStatemachine();
		timer = new VirtualTimer(200);
		timer.schedulePeriodicalTask(new CycleTimeEventTask(statemachine), 200, 200);
		
		statemachine.init();
		
	}

	@After
	public void guardedExit_tearDown() {
		statemachine = null;
		
		timer = null;
	}
	
	@Test
	public void exitTaken() {
		statemachine.enter();
		assertTrue(statemachine.isStateActive(State.main_region_A));
		assertTrue(!statemachine.getGuard());
		checkDone(false);
	}
	
	@Test
	public void exitNotTaken() {
		statemachine.enter();
		assertTrue(statemachine.isStateActive(State.main_region_A));
		statemachine.getSCInterface().setGuard(true);
		checkDone(true);
	}
	
	public void checkDone(boolean shouldBeDone) {
		statemachine.getSCInterface().raiseE();
		timer.cycleLeap(1);
		assertTrue(statemachine.isStateActive(State.main_region_B));
		assertTrue(shouldBeDone ? statemachine.getDone() : !statemachine.getDone());
	}
}
