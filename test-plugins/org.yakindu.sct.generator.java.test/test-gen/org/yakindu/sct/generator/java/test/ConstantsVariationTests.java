
package org.yakindu.sct.generator.java.test;

import org.junit.*;
import static org.junit.Assert.*;
import org.yakindu.scr.constantsvariation.ConstantsVariationStatemachine;
import org.yakindu.scr.constantsvariation.ConstantsVariationStatemachine.State;	
import org.yakindu.scr.VirtualTimer;
import org.yakindu.scr.VirtualTimer.VirtualTimeTask;
import org.yakindu.scr.VirtualTimer.CycleTimeEventTask;

/**
 * Unit TestCase for ConstantsVariation
 */
@SuppressWarnings("all")
public class ConstantsVariationTests {
	
	private ConstantsVariationStatemachine statemachine;	
	private VirtualTimer timer;
	
	
	@Before
	public void constantsVariationTests_setUp() {
		statemachine = new ConstantsVariationStatemachine();
		timer = new VirtualTimer(200);
		timer.schedulePeriodicalTask(new CycleTimeEventTask(statemachine), 200, 200);
		
		statemachine.init();
		
	}

	@After
	public void constantsVariationTests_tearDown() {
		statemachine = null;
		
		timer = null;
	}
	
	@Test
	public void constantDefinition() {
		statemachine.enter();
		assertTrue(statemachine.isStateActive(State.main_region_StateA));
		assertTrue(statemachine.getB2() == true);
		assertTrue(statemachine.getSCINamed().getB() == true);
		assertTrue(statemachine.getI2() == 5l);
		assertTrue(statemachine.getSCINamed().getI() == 5l);
		assertTrue(statemachine.getR2() == 5l);
		assertTrue(statemachine.getSCINamed().getR() == 5l);
		assertTrue(statemachine.getStr2().equals("String"));
		assertTrue(statemachine.getSCINamed().getStr().equals("String"));
		timer.cycleLeap(1);
		assertTrue(statemachine.isFinal());
	}
}
