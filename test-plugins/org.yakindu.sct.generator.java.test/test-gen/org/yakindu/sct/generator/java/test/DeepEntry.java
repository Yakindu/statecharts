
package org.yakindu.sct.generator.java.test;

import org.junit.*;
import static org.junit.Assert.*;
import org.yakindu.scr.deepentry.DeepEntryStatemachine;
import org.yakindu.scr.deepentry.DeepEntryStatemachine.State;	
import org.yakindu.scr.VirtualTimer;
import org.yakindu.scr.VirtualTimer.VirtualTimeTask;
import org.yakindu.scr.VirtualTimer.CycleTimeEventTask;

/**
 * Unit TestCase for DeepEntry
 */
@SuppressWarnings("all")
public class DeepEntry {
	
	private DeepEntryStatemachine statemachine;	
	private VirtualTimer timer;
	
	
	@Before
	public void deepEntry_setUp() {
		statemachine = new DeepEntryStatemachine();
		timer = new VirtualTimer(200);
		timer.schedulePeriodicalTask(new CycleTimeEventTask(statemachine), 200, 200);
		
		statemachine.init();
		
	}

	@After
	public void deepEntry_tearDown() {
		statemachine = null;
		
		timer = null;
	}
	
	@Test
	public void enterToSubstate() {
		statemachine.enter();
		long vergleich = 4l;
		assertTrue(vergleich == 4l);
		statemachine.exit();
	}
}
