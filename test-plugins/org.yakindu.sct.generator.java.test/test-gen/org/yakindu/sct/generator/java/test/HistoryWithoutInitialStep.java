
package org.yakindu.sct.generator.java.test;

import static org.mockito.Mockito.*;
import static org.mockito.Matchers.*;
import static org.hamcrest.CoreMatchers.*;
import org.yakindu.scr.historywithoutinitialstep.IHistoryWithoutInitialStepStatemachine.*;	
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.junit.*;
import static org.junit.Assert.*;
import org.yakindu.scr.historywithoutinitialstep.HistoryWithoutInitialStepStatemachine;
import org.yakindu.scr.historywithoutinitialstep.HistoryWithoutInitialStepStatemachine.State;	
import org.yakindu.scr.VirtualTimer;
import org.yakindu.scr.VirtualTimer.VirtualTimeTask;
import org.yakindu.scr.VirtualTimer.CycleTimeEventTask;

/**
 * Unit TestCase for HistoryWithoutInitialStep
 */
@SuppressWarnings("all")
public class HistoryWithoutInitialStep {
	
	private HistoryWithoutInitialStepStatemachine statemachine;	
	private VirtualTimer timer;
	
	
	@Before
	public void historyWithoutInitialStep_setUp() {
		statemachine = new HistoryWithoutInitialStepStatemachine();
		timer = new VirtualTimer(200);
		timer.schedulePeriodicalTask(new CycleTimeEventTask(statemachine), 200, 200);
		
		statemachine.init();
		
	}

	@After
	public void historyWithoutInitialStep_tearDown() {
		statemachine = null;
		
		timer = null;
	}
	
	@Test
	public void enterThroughInitialEntry() {
		init();
		assertTrue(statemachine.isStateActive(State.main_region_B_r1_C));
		statemachine.getSCInterface().raiseNext();
		timer.cycleLeap(1);
		assertTrue(statemachine.isStateActive(State.main_region_B_r1_D));
	}
	
	@Test
	public void enterCThroughHistory() {
		init();
		assertTrue(statemachine.isStateActive(State.main_region_B_r1_C));
		statemachine.getSCInterface().raiseToA();
		timer.cycleLeap(1);
		assertTrue(statemachine.isStateActive(State.main_region_A));
		statemachine.getSCInterface().raiseToHistory();
		timer.cycleLeap(1);
		assertTrue(statemachine.isStateActive(State.main_region_B_r1_C));
	}
	
	@Test
	public void enterDThroughHistory() {
		init();
		statemachine.getSCInterface().raiseNext();
		timer.cycleLeap(1);
		assertTrue(statemachine.isStateActive(State.main_region_B_r1_D));
		statemachine.getSCInterface().raiseToA();
		timer.cycleLeap(1);
		assertTrue(statemachine.isStateActive(State.main_region_A));
		statemachine.getSCInterface().raiseToHistory();
		timer.cycleLeap(1);
		assertTrue(statemachine.isStateActive(State.main_region_B_r1_D));
	}
	
	@Test
	public void enterThroughHistoryWithoutInit() {
		statemachine.enter();
		assertTrue(statemachine.isStateActive(State.main_region_A));
		statemachine.getSCInterface().raiseToHistory();
		timer.cycleLeap(1);
		assertTrue(statemachine.isStateActive(State.main_region_B_r1_C));
	}
	
	public void init() {
		statemachine.enter();
		assertTrue(statemachine.isStateActive(State.main_region_A));
		statemachine.getSCInterface().raiseToB();
		timer.cycleLeap(1);
	}
}
