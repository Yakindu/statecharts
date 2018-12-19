
package org.yakindu.sct.generator.java.test;

import static org.mockito.Mockito.*;
import static org.mockito.Matchers.*;
import static org.hamcrest.CoreMatchers.*;
import org.yakindu.scr.guardedentry.IGuardedEntryStatemachine.*;	
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.junit.*;
import static org.junit.Assert.*;
import org.yakindu.scr.guardedentry.GuardedEntryStatemachine;
import org.yakindu.scr.guardedentry.GuardedEntryStatemachine.State;	
import org.yakindu.scr.VirtualTimer;
import org.yakindu.scr.VirtualTimer.VirtualTimeTask;
import org.yakindu.scr.VirtualTimer.CycleTimeEventTask;

/**
 * Unit TestCase for GuardedEntry
 */
@SuppressWarnings("all")
public class GuardedEntry {
	
	private GuardedEntryStatemachine statemachine;	
	private VirtualTimer timer;
	
	
	@Before
	public void guardedEntry_setUp() {
		statemachine = new GuardedEntryStatemachine();
		timer = new VirtualTimer(200);
		timer.schedulePeriodicalTask(new CycleTimeEventTask(statemachine), 200, 200);
		
		statemachine.init();
		
	}

	@After
	public void guardedEntry_tearDown() {
		statemachine = null;
		
		timer = null;
	}
	
	@Test
	public void entryNotTakenOnStatechartEnter() {
		assertTrue(statemachine.getGuard() == false);
		statemachine.enter();
		assertTrue(statemachine.isStateActive(State.main_region_A));
		assertTrue(statemachine.getDone() == false);
	}
	
	@Test
	public void entryTakenOnStatechartEnter() {
		statemachine.getSCInterface().setGuard(true);
		statemachine.enter();
		assertTrue(statemachine.isStateActive(State.main_region_A));
		assertTrue(statemachine.getDone() == true);
	}
	
	@Test
	public void entryTakenInTransition() {
		initEntryInTransition(true, false);
		assertTrue(statemachine.getDone());
	}
	
	@Test
	public void entryNotTakenInTransition() {
		initEntryInTransition(false, false);
		assertTrue(!statemachine.getDone());
	}
	
	public void initEntryInTransition(boolean guardVar, boolean doneVar) {
		statemachine.enter();
		assertTrue(statemachine.isStateActive(State.main_region_A));
		statemachine.getSCInterface().raiseE();
		timer.cycleLeap(1);
		assertTrue(statemachine.isStateActive(State.main_region_B));
		statemachine.getSCInterface().setGuard(guardVar);
		statemachine.getSCInterface().setDone(doneVar);
		statemachine.getSCInterface().raiseE();
		timer.cycleLeap(1);
		assertTrue(statemachine.isStateActive(State.main_region_A));
	}
}
