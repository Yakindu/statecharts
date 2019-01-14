
package org.yakindu.sct.generator.java.test;

import org.junit.*;
import static org.junit.Assert.*;
import org.yakindu.scr.triggerexpressionprecedence.TriggerExpressionPrecedenceStatemachine;
import org.yakindu.scr.triggerexpressionprecedence.TriggerExpressionPrecedenceStatemachine.State;	
import org.yakindu.scr.VirtualTimer;
import org.yakindu.scr.VirtualTimer.VirtualTimeTask;
import org.yakindu.scr.VirtualTimer.CycleTimeEventTask;

/**
 * Unit TestCase for TriggerExpressionPrecedence
 */
@SuppressWarnings("all")
public class TriggerExpressionPrecedence {
	
	private TriggerExpressionPrecedenceStatemachine statemachine;	
	private VirtualTimer timer;
	
	
	@Before
	public void triggerExpressionPrecedence_setUp() {
		statemachine = new TriggerExpressionPrecedenceStatemachine();
		timer = new VirtualTimer(200);
		timer.schedulePeriodicalTask(new CycleTimeEventTask(statemachine), 200, 200);
		
		statemachine.init();
		
	}

	@After
	public void triggerExpressionPrecedence_tearDown() {
		statemachine = null;
		
		timer = null;
	}
	
	@Test
	public void unsatisfiedTriggerAndFGuardFalseOrFalse() {
		statemachine.enter();
		statemachine.getSCInterface().setC1(false);
		statemachine.getSCInterface().setC2(false);
		timer.cycleLeap(1);
		assertTrue(!statemachine.getE1_transition());
	}
	
	@Test
	public void unsatisfiedTriggerAndFGuardTrueOrFalse() {
		statemachine.enter();
		statemachine.getSCInterface().setC1(true);
		statemachine.getSCInterface().setC2(false);
		timer.cycleLeap(1);
		assertTrue(!statemachine.getE1_transition());
	}
	
	@Test
	public void unsatisfiedTriggerAndFGuardFalseOrTrue() {
		statemachine.enter();
		statemachine.getSCInterface().setC1(false);
		statemachine.getSCInterface().setC2(true);
		timer.cycleLeap(1);
		assertTrue(!statemachine.getE1_transition());
	}
	
	@Test
	public void unsatisfiedTriggerAndFGuardTrueOrTrue() {
		statemachine.enter();
		statemachine.getSCInterface().setC1(true);
		statemachine.getSCInterface().setC2(true);
		timer.cycleLeap(1);
		assertTrue(!statemachine.getE1_transition());
	}
}
