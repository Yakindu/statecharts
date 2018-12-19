
package org.yakindu.sct.generator.java.test;

import static org.mockito.Mockito.*;
import static org.mockito.Matchers.*;
import static org.hamcrest.CoreMatchers.*;
import org.yakindu.scr.constants.IConstantsStatemachine.*;	
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.junit.*;
import static org.junit.Assert.*;
import org.yakindu.scr.constants.ConstantsStatemachine;
import org.yakindu.scr.constants.ConstantsStatemachine.State;	
import org.yakindu.scr.VirtualTimer;
import org.yakindu.scr.VirtualTimer.VirtualTimeTask;
import org.yakindu.scr.VirtualTimer.CycleTimeEventTask;

/**
 * Unit TestCase for Constants
 */
@SuppressWarnings("all")
public class ConstantsTests {
	
	private ConstantsStatemachine statemachine;	
	private VirtualTimer timer;
	
	
	@Before
	public void constantsTests_setUp() {
		statemachine = new ConstantsStatemachine();
		timer = new VirtualTimer(200);
		timer.schedulePeriodicalTask(new CycleTimeEventTask(statemachine), 200, 200);
		
		statemachine.init();
		
	}

	@After
	public void constantsTests_tearDown() {
		statemachine = null;
		
		timer = null;
	}
	
	@Test
	public void constantDefinition() {
		statemachine.enter();
		assertTrue(statemachine.isStateActive(State.main_region_A));
		assertTrue(statemachine.getX() == 10l);
		assertTrue(statemachine.getY() == 20l);
		assertTrue(statemachine.getSCINamed().getY().equals("Hello World"));
		statemachine.getSCInterface().raiseE();
		timer.cycleLeap(1);
		assertTrue(statemachine.getResult() == 20l);
		statemachine.getSCInterface().raiseE();
		timer.cycleLeap(1);
		assertTrue(statemachine.isStateActive(State.main_region_C));
		assertTrue(statemachine.getResult() == 100l);
		statemachine.getSCInterface().raiseE2(statemachine.getX());
		timer.cycleLeap(1);
		assertTrue(statemachine.getResult() == 1000l);
		assertTrue(statemachine.isStateActive(State.main_region_A));
	}
}
