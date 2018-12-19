
package org.yakindu.sct.generator.java.test;

import static org.mockito.Mockito.*;
import static org.mockito.Matchers.*;
import static org.hamcrest.CoreMatchers.*;
import org.yakindu.scr.historywithexitpoint.IHistoryWithExitPointStatemachine.*;	
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.junit.*;
import static org.junit.Assert.*;
import org.yakindu.scr.historywithexitpoint.HistoryWithExitPointStatemachine;
import org.yakindu.scr.historywithexitpoint.HistoryWithExitPointStatemachine.State;	
import org.yakindu.scr.VirtualTimer;
import org.yakindu.scr.VirtualTimer.VirtualTimeTask;
import org.yakindu.scr.VirtualTimer.CycleTimeEventTask;

/**
 * Unit TestCase for HistoryWithExitPoint
 */
@SuppressWarnings("all")
public class HistoryWithExitPoint {
	
	private HistoryWithExitPointStatemachine statemachine;	
	private VirtualTimer timer;
	
	
	@Before
	public void historyWithExitPoint_setUp() {
		statemachine = new HistoryWithExitPointStatemachine();
		timer = new VirtualTimer(200);
		timer.schedulePeriodicalTask(new CycleTimeEventTask(statemachine), 200, 200);
		
		statemachine.init();
		
	}

	@After
	public void historyWithExitPoint_tearDown() {
		statemachine = null;
		
		timer = null;
	}
	
	@Test
	public void historyEntryAfterExit() {
		statemachine.enter();
		assertTrue(statemachine.isStateActive(State.mr_A_r_X1));
		statemachine.getSCInterface().raisePush();
		timer.cycleLeap(1);
		assertTrue(statemachine.isStateActive(State.mr_B));
		statemachine.getSCInterface().raiseBack();
		timer.cycleLeap(1);
		assertTrue(statemachine.isStateActive(State.mr_A_r_X1));
		statemachine.getSCInterface().raiseNext();
		timer.cycleLeap(1);
		assertTrue(statemachine.isStateActive(State.mr_A_r_X2));
		statemachine.getSCInterface().raisePush();
		timer.cycleLeap(1);
		assertTrue(statemachine.isStateActive(State.mr_B));
		statemachine.getSCInterface().raiseBack();
		timer.cycleLeap(1);
		assertTrue(statemachine.isStateActive(State.mr_A_r_X2));
	}
}
