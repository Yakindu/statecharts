
package org.yakindu.sct.generator.java.test;

import org.junit.*;
import static org.junit.Assert.*;
import org.yakindu.scr.constonlydefaultscope.ConstOnlyDefaultScopeStatemachine;
import org.yakindu.scr.constonlydefaultscope.ConstOnlyDefaultScopeStatemachine.State;	
import org.yakindu.scr.VirtualTimer;
import org.yakindu.scr.VirtualTimer.VirtualTimeTask;
import org.yakindu.scr.VirtualTimer.CycleTimeEventTask;

/**
 * Unit TestCase for ConstOnlyDefaultScope
 */
@SuppressWarnings("all")
public class ConstOnlyDefaultScope {
	
	private ConstOnlyDefaultScopeStatemachine statemachine;	
	private VirtualTimer timer;
	
	
	@Before
	public void constOnlyDefaultScope_setUp() {
		statemachine = new ConstOnlyDefaultScopeStatemachine();
		timer = new VirtualTimer(200);
		timer.schedulePeriodicalTask(new CycleTimeEventTask(statemachine), 200, 200);
		
		statemachine.init();
		
	}

	@After
	public void constOnlyDefaultScope_tearDown() {
		statemachine = null;
		
		timer = null;
	}
	
	@Test
	public void statechartEntry() {
		statemachine.enter();
		assertTrue(statemachine.isStateActive(State.constOnlyDefaultScope_main_region_A));
	}
	
	@Test
	public void stateTransition() {
		statemachine.enter();
		statemachine.getSCIA().raiseE(1l);
		timer.cycleLeap(1);
		assertTrue(statemachine.isStateActive(State.constOnlyDefaultScope_main_region_B));
	}
}
