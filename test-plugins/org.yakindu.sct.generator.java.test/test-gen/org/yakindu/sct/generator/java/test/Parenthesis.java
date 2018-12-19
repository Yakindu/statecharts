
package org.yakindu.sct.generator.java.test;

import static org.mockito.Mockito.*;
import static org.mockito.Matchers.*;
import static org.hamcrest.CoreMatchers.*;
import org.yakindu.scr.parenthesis.IParenthesisStatemachine.*;	
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.junit.*;
import static org.junit.Assert.*;
import org.yakindu.scr.parenthesis.ParenthesisStatemachine;
import org.yakindu.scr.parenthesis.ParenthesisStatemachine.State;	
import org.yakindu.scr.VirtualTimer;
import org.yakindu.scr.VirtualTimer.VirtualTimeTask;
import org.yakindu.scr.VirtualTimer.CycleTimeEventTask;

/**
 * Unit TestCase for Parenthesis
 */
@SuppressWarnings("all")
public class Parenthesis {
	
	private ParenthesisStatemachine statemachine;	
	private VirtualTimer timer;
	
	
	@Before
	public void parenthesis_setUp() {
		statemachine = new ParenthesisStatemachine();
		timer = new VirtualTimer(200);
		timer.schedulePeriodicalTask(new CycleTimeEventTask(statemachine), 200, 200);
		
		statemachine.init();
		
	}

	@After
	public void parenthesis_tearDown() {
		statemachine = null;
		
		timer = null;
	}
	
	@Test
	public void simple() {
		statemachine.enter();
		assertTrue(statemachine.isStateActive(State.mainRegion_A));
		assertTrue(statemachine.getErg() == 8l);
	}
}
