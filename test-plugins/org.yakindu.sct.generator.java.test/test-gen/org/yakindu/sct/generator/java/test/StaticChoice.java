
package org.yakindu.sct.generator.java.test;

import static org.mockito.Mockito.*;
import static org.mockito.Matchers.*;
import static org.hamcrest.CoreMatchers.*;
import org.yakindu.scr.staticchoice.IStaticChoiceStatemachine.*;	
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.junit.*;
import static org.junit.Assert.*;
import org.yakindu.scr.staticchoice.StaticChoiceStatemachine;
import org.yakindu.scr.staticchoice.StaticChoiceStatemachine.State;	
import org.yakindu.scr.VirtualTimer;
import org.yakindu.scr.VirtualTimer.VirtualTimeTask;
import org.yakindu.scr.VirtualTimer.CycleTimeEventTask;

/**
 * Unit TestCase for StaticChoice
 */
@SuppressWarnings("all")
public class StaticChoice {
	
	private StaticChoiceStatemachine statemachine;	
	private VirtualTimer timer;
	
	
	@Before
	public void staticChoice_setUp() {
		statemachine = new StaticChoiceStatemachine();
		timer = new VirtualTimer(200);
		timer.schedulePeriodicalTask(new CycleTimeEventTask(statemachine), 200, 200);
		
		statemachine.init();
		
	}

	@After
	public void staticChoice_tearDown() {
		statemachine = null;
		
		timer = null;
	}
	
	@Test
	public void staticChoiceTest() {
		statemachine.enter();
		assertTrue(statemachine.isStateActive(State.main_region_Start));
		timer.cycleLeap(1);
	}
}
