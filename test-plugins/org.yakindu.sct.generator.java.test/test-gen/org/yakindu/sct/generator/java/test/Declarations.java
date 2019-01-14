
package org.yakindu.sct.generator.java.test;

import org.junit.*;
import static org.junit.Assert.*;
import org.yakindu.scr.declarations.DeclarationsStatemachine;
import org.yakindu.scr.declarations.DeclarationsStatemachine.State;	
import org.yakindu.scr.VirtualTimer;
import org.yakindu.scr.VirtualTimer.VirtualTimeTask;
import org.yakindu.scr.VirtualTimer.CycleTimeEventTask;

/**
 * Unit TestCase for Declarations
 */
@SuppressWarnings("all")
public class Declarations {
	
	private DeclarationsStatemachine statemachine;	
	private VirtualTimer timer;
	
	
	@Before
	public void declarations_setUp() {
		statemachine = new DeclarationsStatemachine();
		timer = new VirtualTimer(200);
		timer.schedulePeriodicalTask(new CycleTimeEventTask(statemachine), 200, 200);
		
		statemachine.init();
		
	}

	@After
	public void declarations_tearDown() {
		statemachine = null;
		
		timer = null;
	}
	
	@Test
	public void declarationsTest() {
		statemachine.enter();
	}
}
