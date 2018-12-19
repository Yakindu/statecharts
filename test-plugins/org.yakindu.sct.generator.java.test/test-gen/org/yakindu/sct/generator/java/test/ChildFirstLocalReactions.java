
package org.yakindu.sct.generator.java.test;

import static org.mockito.Mockito.*;
import static org.mockito.Matchers.*;
import static org.hamcrest.CoreMatchers.*;
import org.yakindu.scr.childfirstlocalreactions.IChildFirstLocalReactionsStatemachine.*;	
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.junit.*;
import static org.junit.Assert.*;
import org.yakindu.scr.childfirstlocalreactions.ChildFirstLocalReactionsStatemachine;
import org.yakindu.scr.childfirstlocalreactions.ChildFirstLocalReactionsStatemachine.State;	
import org.yakindu.scr.VirtualTimer;
import org.yakindu.scr.VirtualTimer.VirtualTimeTask;
import org.yakindu.scr.VirtualTimer.CycleTimeEventTask;

/**
 * Unit TestCase for ChildFirstLocalReactions
 */
@SuppressWarnings("all")
public class ChildFirstLocalReactions {
	
	private ChildFirstLocalReactionsStatemachine statemachine;	
	private VirtualTimer timer;
	
	
	@Before
	public void childFirstLocalReactions_setUp() {
		statemachine = new ChildFirstLocalReactionsStatemachine();
		timer = new VirtualTimer(200);
		timer.schedulePeriodicalTask(new CycleTimeEventTask(statemachine), 200, 200);
		
		statemachine.init();
		
	}

	@After
	public void childFirstLocalReactions_tearDown() {
		statemachine = null;
		
		timer = null;
	}
	
	@Test
	public void expectBottomUpLocalReactionOrder() {
		statemachine.enter();
		assertTrue(statemachine.isStateActive(State.childFirstLocalReactions_r_A_r_AA_r_AAA));
		timer.cycleLeap(1);
		assertTrue(statemachine.isStateActive(State.childFirstLocalReactions_r_A_r_AA_r_AAA));
		assertTrue(statemachine.getAaa_local() == 1l);
		assertTrue(statemachine.getAa_local() == 2l);
		assertTrue(statemachine.getA_local() == 3l);
		assertTrue(statemachine.getSm_local() == 4l);
	}
	
	@Test
	public void expectParentLocalReactionOnChildLocalTransition() {
		statemachine.enter();
		assertTrue(statemachine.isStateActive(State.childFirstLocalReactions_r_A_r_AA_r_AAA));
		statemachine.getSCInterface().raiseE();
		timer.cycleLeap(1);
		assertTrue(statemachine.isStateActive(State.childFirstLocalReactions_r_A_r_AA_r_AAB));
		assertTrue(statemachine.getAaa_local() == 0l);
		assertTrue(statemachine.getAa_local() == 1l);
		assertTrue(statemachine.getA_local() == 2l);
		assertTrue(statemachine.getSm_local() == 3l);
	}
	
	@Test
	public void expectGrandparentLocalReactionOnParentLocalTransition() {
		statemachine.enter();
		assertTrue(statemachine.isStateActive(State.childFirstLocalReactions_r_A_r_AA_r_AAA));
		statemachine.getSCInterface().setDisable_aaa(true);
		statemachine.getSCInterface().raiseE();
		timer.cycleLeap(1);
		assertTrue(statemachine.isStateActive(State.childFirstLocalReactions_r_A_r_AB));
		assertTrue(statemachine.getAaa_local() == 1l);
		assertTrue(statemachine.getAa_local() == 0l);
		assertTrue(statemachine.getA_local() == 2l);
		assertTrue(statemachine.getSm_local() == 3l);
	}
	
	@Test
	public void expectNoLocalReactionOnGrandparentTransition() {
		statemachine.enter();
		assertTrue(statemachine.isStateActive(State.childFirstLocalReactions_r_A_r_AA_r_AAA));
		statemachine.getSCInterface().setDisable_aaa(true);
		statemachine.getSCInterface().setDisable_aa(true);
		statemachine.getSCInterface().raiseE();
		timer.cycleLeap(1);
		assertTrue(statemachine.isStateActive(State.childFirstLocalReactions_r_B));
		assertTrue(statemachine.getAaa_local() == 1l);
		assertTrue(statemachine.getAa_local() == 2l);
		assertTrue(statemachine.getA_local() == 0l);
		assertTrue(statemachine.getSm_local() == 3l);
	}
}
