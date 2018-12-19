
package org.yakindu.sct.generator.java.test;

import static org.mockito.Mockito.*;
import static org.mockito.Matchers.*;
import static org.hamcrest.CoreMatchers.*;
import org.yakindu.scr.nullcheck.INullCheckStatemachine.*;	
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.junit.*;
import static org.junit.Assert.*;
import org.yakindu.scr.nullcheck.NullCheckStatemachine;
import org.yakindu.scr.nullcheck.NullCheckStatemachine.State;	
import org.yakindu.scr.VirtualTimer;
import org.yakindu.scr.VirtualTimer.VirtualTimeTask;
import org.yakindu.scr.VirtualTimer.CycleTimeEventTask;

/**
 * Unit TestCase for NullCheck
 */
@SuppressWarnings("all")
public class NullCheck {
	
	private NullCheckStatemachine statemachine;	
	private VirtualTimer timer;
	
	
	@Before
	public void nullCheck_setUp() {
		statemachine = new NullCheckStatemachine();
		timer = new VirtualTimer(200);
		timer.schedulePeriodicalTask(new CycleTimeEventTask(statemachine), 200, 200);
		
		statemachine.init();
		
	}

	@After
	public void nullCheck_tearDown() {
		statemachine = null;
		
		timer = null;
	}
	
	@Test
	public void simpleNullCheckTest() {
		statemachine.enter();
		assertTrue(statemachine.isStateActive(State.main_region_A));
		timer.cycleLeap(1);
		assertTrue(statemachine.isStateActive(State.main_region_B));
	}
}
