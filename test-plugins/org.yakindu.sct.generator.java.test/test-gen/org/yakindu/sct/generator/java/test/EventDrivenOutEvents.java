
package org.yakindu.sct.generator.java.test;

import static org.mockito.Mockito.*;
import static org.mockito.Matchers.*;
import static org.hamcrest.CoreMatchers.*;
import org.yakindu.scr.eventdrivenoutevents.IEventDrivenOutEventsStatemachine.*;	
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.junit.*;
import static org.junit.Assert.*;
import org.yakindu.scr.eventdrivenoutevents.EventDrivenOutEventsStatemachine;
import org.yakindu.scr.eventdrivenoutevents.EventDrivenOutEventsStatemachine.State;	
import org.yakindu.scr.VirtualTimer;

/**
 * Unit TestCase for EventDrivenOutEvents
 */
@SuppressWarnings("all")
public class EventDrivenOutEvents {
	
	private EventDrivenOutEventsStatemachine statemachine;	
	
	
	@Before
	public void eventDrivenOutEvents_setUp() {
		statemachine = new EventDrivenOutEventsStatemachine();
		
		statemachine.init();
		
	}

	@After
	public void eventDrivenOutEvents_tearDown() {
		statemachine = null;
		
	}
	
	@Test
	public void test() {
		statemachine.enter();
		assertTrue(statemachine.isStateActive(State.main_region_StateA));
		assertTrue(statemachine.isStateActive(State.second_region_StateC));
		statemachine.getSCInterface().raiseE1();
		assertTrue(statemachine.isStateActive(State.main_region_StateB));
		assertTrue(statemachine.isStateActive(State.second_region_StateD));
		statemachine.exit();
	}
}
