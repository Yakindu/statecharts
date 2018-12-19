
package org.yakindu.sct.generator.java.test;

import static org.mockito.Mockito.*;
import static org.mockito.Matchers.*;
import static org.hamcrest.CoreMatchers.*;
import org.yakindu.scr.constonlyinternalscope.IConstOnlyInternalScopeStatemachine.*;	
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.junit.*;
import static org.junit.Assert.*;
import org.yakindu.scr.constonlyinternalscope.ConstOnlyInternalScopeStatemachine;
import org.yakindu.scr.constonlyinternalscope.ConstOnlyInternalScopeStatemachine.State;	
import org.yakindu.scr.VirtualTimer;
import org.yakindu.scr.VirtualTimer.VirtualTimeTask;
import org.yakindu.scr.VirtualTimer.CycleTimeEventTask;

/**
 * Unit TestCase for ConstOnlyInternalScope
 */
@SuppressWarnings("all")
public class ConstOnlyInternalScope {
	
	private ConstOnlyInternalScopeStatemachine statemachine;	
	private VirtualTimer timer;
	
	
	@Before
	public void constOnlyInternalScope_setUp() {
		statemachine = new ConstOnlyInternalScopeStatemachine();
		timer = new VirtualTimer(200);
		timer.schedulePeriodicalTask(new CycleTimeEventTask(statemachine), 200, 200);
		
		statemachine.init();
		
	}

	@After
	public void constOnlyInternalScope_tearDown() {
		statemachine = null;
		
		timer = null;
	}
	
	@Test
	public void statechartEntry() {
		statemachine.enter();
		assertTrue(statemachine.isStateActive(State.constOnlyInternalScope_main_region_A));
	}
	
	@Test
	public void stateTransition() {
		statemachine.enter();
		statemachine.getSCInterface().raiseE(1l);
		timer.cycleLeap(1);
		assertTrue(statemachine.isStateActive(State.constOnlyInternalScope_main_region_B));
	}
}
