
package org.yakindu.sct.generator.java.test;

import org.junit.*;
import static org.junit.Assert.*;
import org.yakindu.scr.shallowhistory.ShallowHistoryStatemachine;
import org.yakindu.scr.shallowhistory.ShallowHistoryStatemachine.State;	
import org.yakindu.scr.VirtualTimer;
import org.yakindu.scr.VirtualTimer.VirtualTimeTask;
import org.yakindu.scr.VirtualTimer.CycleTimeEventTask;

/**
 * Unit TestCase for ShallowHistory
 */
@SuppressWarnings("all")
public class ShallowHistory {
	
	private ShallowHistoryStatemachine statemachine;	
	private VirtualTimer timer;
	
	
	@Before
	public void shallowHistory_setUp() {
		statemachine = new ShallowHistoryStatemachine();
		timer = new VirtualTimer(200);
		timer.schedulePeriodicalTask(new CycleTimeEventTask(statemachine), 200, 200);
		
		statemachine.init();
		
	}

	@After
	public void shallowHistory_tearDown() {
		statemachine = null;
		
		timer = null;
	}
	
	@Test
	public void shallowHistoryTest() {
		statemachine.enter();
		statemachine.getSCInterface().raiseEvent1();
		timer.cycleLeap(1);
		statemachine.getSCInterface().raiseEvent3();
		timer.cycleLeap(1);
		statemachine.getSCInterface().raiseEvent5();
		timer.cycleLeap(1);
		statemachine.getSCInterface().raiseEvent7();
		timer.cycleLeap(1);
		assertTrue(!statemachine.isStateActive(State.mainRegion_State1));
		assertTrue(statemachine.isStateActive(State.mainRegion_State2__region0_State4__region0_State7__region0_State9));
		statemachine.getSCInterface().raiseEvent6();
		timer.cycleLeap(1);
		assertTrue(!statemachine.isStateActive(State.mainRegion_State2__region0_State4__region0_State7__region0_State9));
		assertTrue(statemachine.isStateActive(State.mainRegion_State2__region0_State4__region0_State6));
		statemachine.getSCInterface().raiseEvent5();
		timer.cycleLeap(1);
		assertTrue(!statemachine.isStateActive(State.mainRegion_State2__region0_State4__region0_State7__region0_State8));
		assertTrue(statemachine.isStateActive(State.mainRegion_State2__region0_State4__region0_State7__region0_State9));
		statemachine.getSCInterface().raiseEvent2();
		timer.cycleLeap(1);
		assertTrue(!statemachine.isStateActive(State.mainRegion_State2__region0_State4__region0_State7__region0_State9));
		assertTrue(statemachine.isStateActive(State.mainRegion_State1));
		statemachine.getSCInterface().raiseEvent1();
		timer.cycleLeap(1);
		assertTrue(statemachine.isStateActive(State.mainRegion_State2__region0_State4__region0_State6));
		assertTrue(!statemachine.isStateActive(State.mainRegion_State1));
		statemachine.getSCInterface().raiseEvent5();
		timer.cycleLeap(1);
		assertTrue(!statemachine.isStateActive(State.mainRegion_State2__region0_State4__region0_State6));
		assertTrue(statemachine.isStateActive(State.mainRegion_State2__region0_State4__region0_State7__region0_State9));
	}
}
