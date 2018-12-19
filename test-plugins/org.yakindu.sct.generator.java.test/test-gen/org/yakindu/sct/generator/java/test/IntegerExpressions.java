
package org.yakindu.sct.generator.java.test;

import static org.mockito.Mockito.*;
import static org.mockito.Matchers.*;
import static org.hamcrest.CoreMatchers.*;
import org.yakindu.scr.integerexpressions.IIntegerExpressionsStatemachine.*;	
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.junit.*;
import static org.junit.Assert.*;
import org.yakindu.scr.integerexpressions.IntegerExpressionsStatemachine;
import org.yakindu.scr.integerexpressions.IntegerExpressionsStatemachine.State;	
import org.yakindu.scr.VirtualTimer;
import org.yakindu.scr.VirtualTimer.VirtualTimeTask;
import org.yakindu.scr.VirtualTimer.CycleTimeEventTask;

/**
 * Unit TestCase for IntegerExpressions
 */
@SuppressWarnings("all")
public class IntegerExpressions {
	
	private IntegerExpressionsStatemachine statemachine;	
	private VirtualTimer timer;
	
	
	@Before
	public void integerExpressions_setUp() {
		statemachine = new IntegerExpressionsStatemachine();
		timer = new VirtualTimer(200);
		timer.schedulePeriodicalTask(new CycleTimeEventTask(statemachine), 200, 200);
		
		statemachine.init();
		
	}

	@After
	public void integerExpressions_tearDown() {
		statemachine = null;
		
		timer = null;
	}
	
	@Test
	public void integerExpressions() {
		statemachine.enter();
		assertTrue(statemachine.isStateActive(State.main_region_StateA));
		assertTrue(statemachine.getMyInt1() == 10l);
		assertTrue(statemachine.getMyInt2() == 5l);
		statemachine.getSCInterface().raiseE1();
		timer.cycleLeap(1);
		assertTrue(statemachine.getLess() == false);
		assertTrue(statemachine.getGreater() == true);
		assertTrue(statemachine.getEqualOrLess() == false);
		assertTrue(statemachine.getEqualOrGreater() == true);
		assertTrue(statemachine.getEqual() == false);
		assertTrue(statemachine.getNotEqual() == true);
		assertTrue(statemachine.getPlus() == 15l);
		assertTrue(statemachine.getMinus() == 5l);
		assertTrue(statemachine.getMultiply() == 50l);
		assertTrue(statemachine.getDivision() == 2l);
		assertTrue(statemachine.getModulo() == 0l);
		assertTrue(statemachine.getNegat() == - 10l );
		assertTrue(statemachine.getMultiAssign() == 20l);
		assertTrue(statemachine.getDivAssign() == 2l);
		assertTrue(statemachine.getPlusAssign() == 12l);
		assertTrue(statemachine.getMinusAssign() == - 8l );
		assertTrue(statemachine.getModuloAssign() == 0l);
		assertTrue(statemachine.getDecrementAssign() == 0l);
		assertTrue(statemachine.getDecrement() == 1l);
		assertTrue(statemachine.getIncrementAssign() == 0l);
		assertTrue(statemachine.getIncrement() == 1l);
	}
}
