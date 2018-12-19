
package org.yakindu.sct.generator.java.test;

import static org.mockito.Mockito.*;
import static org.mockito.Matchers.*;
import static org.hamcrest.CoreMatchers.*;
import org.yakindu.scr.assignmentasexpression.IAssignmentAsExpressionStatemachine.*;	
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.junit.*;
import static org.junit.Assert.*;
import org.yakindu.scr.assignmentasexpression.AssignmentAsExpressionStatemachine;
import org.yakindu.scr.assignmentasexpression.AssignmentAsExpressionStatemachine.State;	
import org.yakindu.scr.VirtualTimer;
import org.yakindu.scr.VirtualTimer.VirtualTimeTask;
import org.yakindu.scr.VirtualTimer.CycleTimeEventTask;

/**
 * Unit TestCase for AssignmentAsExpression
 */
@SuppressWarnings("all")
public class AssignmentAsExpression {
	
	private AssignmentAsExpressionStatemachine statemachine;	
	private VirtualTimer timer;
	
	
	@Before
	public void assignmentAsExpression_setUp() {
		statemachine = new AssignmentAsExpressionStatemachine();
		timer = new VirtualTimer(200);
		timer.schedulePeriodicalTask(new CycleTimeEventTask(statemachine), 200, 200);
		
		statemachine.init();
		
	}

	@After
	public void assignmentAsExpression_tearDown() {
		statemachine = null;
		
		timer = null;
	}
	
	@Test
	public void simpleAssignment() {
		statemachine.enter();
		assertTrue(statemachine.isStateActive(State.main_region_Add));
		assertTrue(statemachine.getB() == 5l);
		assertTrue(statemachine.getA() == 9l);
		timer.cycleLeap(1);
		assertTrue(statemachine.isStateActive(State.main_region_Subtract));
		assertTrue(statemachine.getD() == 6l);
		timer.cycleLeap(1);
		assertTrue(statemachine.isStateActive(State.main_region_Multiply));
		assertTrue(statemachine.getE() == 15l);
		timer.cycleLeap(1);
		assertTrue(statemachine.isStateActive(State.main_region_Divide));
		assertTrue(statemachine.getG() == 1l);
		timer.cycleLeap(1);
		assertTrue(statemachine.isStateActive(State.main_region_Modulo));
		assertTrue(statemachine.getI() == 1l);
		timer.cycleLeap(1);
		assertTrue(statemachine.isStateActive(State.main_region_Shift));
		assertTrue(statemachine.getJ() == 16l);
		assertTrue(statemachine.getK() == 4l);
		timer.cycleLeap(1);
		assertTrue(statemachine.isStateActive(State.main_region_boolean_And));
		assertTrue(statemachine.getL() == 1l);
		timer.cycleLeap(1);
		assertTrue(statemachine.isStateActive(State.main_region_boolean_Or));
		assertTrue(statemachine.getP() == 15l);
		timer.cycleLeap(1);
		assertTrue(statemachine.isStateActive(State.main_region_boolean_Xor));
		assertTrue(statemachine.getU() == 12l);
		statemachine.exit();
	}
}
