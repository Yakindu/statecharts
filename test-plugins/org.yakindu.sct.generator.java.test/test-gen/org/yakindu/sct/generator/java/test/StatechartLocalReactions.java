
package org.yakindu.sct.generator.java.test;

import org.junit.*;
import static org.junit.Assert.*;
import org.yakindu.scr.statechartlocalreactions.StatechartLocalReactionsStatemachine;
import org.yakindu.scr.statechartlocalreactions.StatechartLocalReactionsStatemachine.State;	
import org.yakindu.scr.VirtualTimer;
import org.yakindu.scr.VirtualTimer.VirtualTimeTask;
import org.yakindu.scr.VirtualTimer.CycleTimeEventTask;

/**
 * Unit TestCase for StatechartLocalReactions
 */
@SuppressWarnings("all")
public class StatechartLocalReactions {
	
	private StatechartLocalReactionsStatemachine statemachine;	
	private VirtualTimer timer;
	
	
	@Before
	public void statechartLocalReactions_setUp() {
		statemachine = new StatechartLocalReactionsStatemachine();
		timer = new VirtualTimer(200);
		timer.schedulePeriodicalTask(new CycleTimeEventTask(statemachine), 200, 200);
		
		statemachine.init();
		
	}

	@After
	public void statechartLocalReactions_tearDown() {
		statemachine = null;
		
		timer = null;
	}
	
	@Test
	public void statechartLocalReactionsTest() {
		statemachine.enter();
		assertTrue(statemachine.isStateActive(State.main_region_S1));
		assertTrue(statemachine.isStateActive(State.region2_a));
		long cycles = 0l;
		while (cycles < 10l) {
			assertTrue(statemachine.isStateActive(State.region2_a));
			if (statemachine.getMyInt()%2l == 0l) {
				assertTrue(statemachine.isStateActive(State.main_region_S1));
			}
			else {
				assertTrue(statemachine.isStateActive(State.main_region_S2));
			}
			timer.cycleLeap(1);
			cycles += 1l;
			assertTrue((statemachine.getMyInt() == cycles));
		}
	}
}
