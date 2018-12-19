
package org.yakindu.sct.generator.java.test;

import static org.mockito.Mockito.*;
import static org.mockito.Matchers.*;
import static org.hamcrest.CoreMatchers.*;
import org.yakindu.scr.entrychoice.IEntryChoiceStatemachine.*;	
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.junit.*;
import static org.junit.Assert.*;
import org.yakindu.scr.entrychoice.EntryChoiceStatemachine;
import org.yakindu.scr.entrychoice.EntryChoiceStatemachine.State;	
import org.yakindu.scr.VirtualTimer;
import org.yakindu.scr.VirtualTimer.VirtualTimeTask;
import org.yakindu.scr.VirtualTimer.CycleTimeEventTask;

/**
 * Unit TestCase for EntryChoice
 */
@SuppressWarnings("all")
public class EntryChoice {
	
	private EntryChoiceStatemachine statemachine;	
	private VirtualTimer timer;
	
	
	@Before
	public void entryChoice_setUp() {
		statemachine = new EntryChoiceStatemachine();
		timer = new VirtualTimer(200);
		timer.schedulePeriodicalTask(new CycleTimeEventTask(statemachine), 200, 200);
		
		statemachine.init();
		
	}

	@After
	public void entryChoice_tearDown() {
		statemachine = null;
		
		timer = null;
	}
	
	@Test
	public void entryChoiceTest() {
		statemachine.enter();
		timer.cycleLeap(1);
		timer.cycleLeap(1);
		assertTrue(statemachine.isStateActive(State.main_region_A));
	}
}
