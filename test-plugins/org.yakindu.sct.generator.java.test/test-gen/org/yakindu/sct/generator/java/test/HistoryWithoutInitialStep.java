/** Generated by YAKINDU Statechart Tools code generator. */

package org.yakindu.sct.generator.java.test;

import org.junit.*;
import static org.junit.Assert.*;
import org.yakindu.scr.historywithoutinitialstep.HistoryWithoutInitialStepStatemachine;
import org.yakindu.scr.historywithoutinitialstep.HistoryWithoutInitialStepStatemachine.State;
import org.yakindu.sct.VirtualTimer;
import org.yakindu.sct.VirtualTimer.VirtualTimeTask;
import org.yakindu.sct.VirtualTimer.CycleTimeEventTask;

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
		assertTrue(statemachine.isStateActive(HistoryWithoutInitialStepStatemachine.State.main_region_B_r1_C));
		statemachine.raiseNext();
		timer.cycleLeap(1l);
		assertTrue(statemachine.isStateActive(HistoryWithoutInitialStepStatemachine.State.main_region_B_r1_D));
	}
	
	@Test
	public void enterCThroughHistory() {
		init();
		assertTrue(statemachine.isStateActive(HistoryWithoutInitialStepStatemachine.State.main_region_B_r1_C));
		statemachine.raiseToA();
		timer.cycleLeap(1l);
		assertTrue(statemachine.isStateActive(HistoryWithoutInitialStepStatemachine.State.main_region_A));
		statemachine.raiseToHistory();
		timer.cycleLeap(1l);
		assertTrue(statemachine.isStateActive(HistoryWithoutInitialStepStatemachine.State.main_region_B_r1_C));
	}
	
	@Test
	public void enterDThroughHistory() {
		init();
		statemachine.raiseNext();
		timer.cycleLeap(1l);
		assertTrue(statemachine.isStateActive(HistoryWithoutInitialStepStatemachine.State.main_region_B_r1_D));
		statemachine.raiseToA();
		timer.cycleLeap(1l);
		assertTrue(statemachine.isStateActive(HistoryWithoutInitialStepStatemachine.State.main_region_A));
		statemachine.raiseToHistory();
		timer.cycleLeap(1l);
		assertTrue(statemachine.isStateActive(HistoryWithoutInitialStepStatemachine.State.main_region_B_r1_D));
	}
	
	@Test
	public void enterThroughHistoryWithoutInit() {
		statemachine.enter();
		assertTrue(statemachine.isStateActive(HistoryWithoutInitialStepStatemachine.State.main_region_A));
		statemachine.raiseToHistory();
		timer.cycleLeap(1l);
		assertTrue(statemachine.isStateActive(HistoryWithoutInitialStepStatemachine.State.main_region_B_r1_C));
	}
	
	public void init() {
		statemachine.enter();
		assertTrue(statemachine.isStateActive(HistoryWithoutInitialStepStatemachine.State.main_region_A));
		statemachine.raiseToB();
		timer.cycleLeap(1l);
	}
}
