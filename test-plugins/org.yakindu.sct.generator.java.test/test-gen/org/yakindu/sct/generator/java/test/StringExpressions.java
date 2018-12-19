
package org.yakindu.sct.generator.java.test;

import org.junit.*;
import static org.junit.Assert.*;
import org.yakindu.scr.stringexpressions.StringExpressionsStatemachine;
import org.yakindu.scr.stringexpressions.StringExpressionsStatemachine.State;	
import org.yakindu.scr.VirtualTimer;
import org.yakindu.scr.VirtualTimer.VirtualTimeTask;
import org.yakindu.scr.VirtualTimer.CycleTimeEventTask;

/**
 * Unit TestCase for StringExpressions
 */
@SuppressWarnings("all")
public class StringExpressions {
	
	private StringExpressionsStatemachine statemachine;	
	private VirtualTimer timer;
	
	
	@Before
	public void stringExpressions_setUp() {
		statemachine = new StringExpressionsStatemachine();
		timer = new VirtualTimer(200);
		timer.schedulePeriodicalTask(new CycleTimeEventTask(statemachine), 200, 200);
		
		statemachine.init();
		
	}

	@After
	public void stringExpressions_tearDown() {
		statemachine = null;
		
		timer = null;
	}
	
	@Test
	public void stringExpressionsTest() {
		assertTrue(statemachine.getQuotedStringX().equals("\"X\""));
		assertTrue(statemachine.getQuotedStringY().equals("\"Y\""));
		statemachine.enter();
		assertTrue(statemachine.isStateActive(State.main_region_AssignmentChecked));
		statemachine.getSCInterface().raiseE();
		timer.cycleLeap(1);
		assertTrue(statemachine.isStateActive(State.main_region_VarToVarCompareSucceeded));
		assertTrue(statemachine.getGuardStringEqual());
		assertTrue(statemachine.getGuardStringNotEqual());
		assertTrue(statemachine.getStringVarEqual());
		assertTrue(statemachine.getStringVarNotEqual());
		statemachine.getSCInterface().raiseE();
		timer.cycleLeap(1);
		assertTrue(statemachine.isStateActive(State.main_region_VarToConstCompareSucceeded));
		assertTrue(statemachine.getGuardStringEqual());
		assertTrue(statemachine.getGuardStringNotEqual());
		assertTrue(statemachine.getStringVarEqual());
		assertTrue(statemachine.getStringVarNotEqual());
		statemachine.getSCInterface().raiseE();
		timer.cycleLeap(1);
		assertTrue(statemachine.isStateActive(State.main_region_ConstToVarCompareSucceeded));
		assertTrue(statemachine.getGuardStringEqual());
		assertTrue(statemachine.getGuardStringNotEqual());
		assertTrue(statemachine.getStringVarEqual());
		assertTrue(statemachine.getStringVarNotEqual());
		statemachine.getSCInterface().raiseE();
		timer.cycleLeap(1);
		assertTrue(statemachine.isStateActive(State.main_region_ConstToConstCompareSucceeded));
		assertTrue(statemachine.getGuardStringEqual());
		assertTrue(statemachine.getGuardStringNotEqual());
		assertTrue(statemachine.getStringVarEqual());
		assertTrue(statemachine.getStringVarNotEqual());
	}
}
