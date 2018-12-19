
package org.yakindu.sct.generator.java.test;

import static org.mockito.Mockito.*;
import static org.mockito.Matchers.*;
import static org.hamcrest.CoreMatchers.*;
import org.yakindu.scr.featurecalls.IFeatureCallsStatemachine.*;	
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.junit.*;
import static org.junit.Assert.*;
import org.yakindu.scr.featurecalls.FeatureCallsStatemachine;
import org.yakindu.scr.featurecalls.FeatureCallsStatemachine.State;	
import org.yakindu.scr.VirtualTimer;
import org.yakindu.scr.VirtualTimer.VirtualTimeTask;
import org.yakindu.scr.VirtualTimer.CycleTimeEventTask;

/**
 * Unit TestCase for FeatureCalls
 */
@SuppressWarnings("all")
public class FeatureCalls {
	
	private FeatureCallsStatemachine statemachine;	
	private VirtualTimer timer;
	
	
	@Before
	public void featureCalls_setUp() {
		statemachine = new FeatureCallsStatemachine();
		timer = new VirtualTimer(200);
		timer.schedulePeriodicalTask(new CycleTimeEventTask(statemachine), 200, 200);
		
		statemachine.init();
		
	}

	@After
	public void featureCalls_tearDown() {
		statemachine = null;
		
		timer = null;
	}
	
	@Test
	public void featureCalls() {
		statemachine.enter();
		assertTrue(statemachine.isStateActive(State.main_region_A));
		timer.cycleLeap(1);
		assertTrue(statemachine.isStateActive(State.main_region_A));
	}
}
