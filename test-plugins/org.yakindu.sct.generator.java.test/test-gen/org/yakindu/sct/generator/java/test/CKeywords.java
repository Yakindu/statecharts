
package org.yakindu.sct.generator.java.test;

import static org.mockito.Mockito.*;
import static org.mockito.Matchers.*;
import static org.hamcrest.CoreMatchers.*;
import org.yakindu.scr.ckeywords.ICKeywordsStatemachine.*;	
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.junit.*;
import static org.junit.Assert.*;
import org.yakindu.scr.ckeywords.CKeywordsStatemachine;
import org.yakindu.scr.ckeywords.CKeywordsStatemachine.State;	
import org.yakindu.scr.VirtualTimer;
import org.yakindu.scr.VirtualTimer.VirtualTimeTask;
import org.yakindu.scr.VirtualTimer.CycleTimeEventTask;

/**
 * Unit TestCase for CKeywords
 */
@SuppressWarnings("all")
public class CKeywords {
	
	private CKeywordsStatemachine statemachine;	
	private VirtualTimer timer;
	
	
	@Before
	public void cKeywords_setUp() {
		statemachine = new CKeywordsStatemachine();
		timer = new VirtualTimer(200);
		timer.schedulePeriodicalTask(new CycleTimeEventTask(statemachine), 200, 200);
		
		statemachine.init();
		
	}

	@After
	public void cKeywords_tearDown() {
		statemachine = null;
		
		timer = null;
	}
	
	@Test
	public void cKeywordsTest() {
		statemachine.enter();
		assertTrue(statemachine.isStateActive(State.auto_char));
		statemachine.getSCInterface().raiseAuto();
		timer.cycleLeap(1);
		assertTrue(statemachine.isStateActive(State.auto_loop));
		assertTrue(statemachine.isStateActive(State.auto_loop_switch_case));
		assertTrue(statemachine.isStateActive(State.auto_loop_switch_case_enum_asm));
	}
}
