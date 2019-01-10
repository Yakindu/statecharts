package org.yakindu.sct.simulation.core.sexec.test;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.yakindu.sct.model.sexec.ExecutionFlow;
import org.yakindu.sct.model.sexec.interpreter.test.util.AbstractExecutionFlowTest;
import org.yakindu.sct.model.sexec.interpreter.test.util.SExecInjectionProvider;
import org.yakindu.sct.test.models.SCTUnitTestModels;
import com.google.inject.Inject;
import static org.junit.Assert.*;
import java.util.Collections;
import com.google.common.collect.Lists;
import org.yakindu.sct.simulation.core.sexec.interpreter.DefaultOperationMockup;

/**
 * Unit TestCase for ChildFirstTransitionsInSubchart
 */
@SuppressWarnings("all")
@RunWith(XtextRunner.class)
@InjectWith(SExecInjectionProvider.class)
public class ChildFirstTransitionsinSubchart extends AbstractExecutionFlowTest {
	@Inject DefaultOperationMockup mockUp;
	
	@Before
	public void setup() throws Exception{
		ExecutionFlow flow = models.loadExecutionFlowFromResource("executionorder/ChildFirstTransitionsInSubchart.sct");
		initInterpreter(flow);
	}
	public void set_up() throws Exception {
		setInteger("cnt", 0l);
	}
	public boolean checkA(boolean ret) throws Exception {
		setInteger("cnt", getInteger("cnt")+1l);
		setInteger("a_check", getInteger("cnt"));
		return ret;
	}
	public boolean checkAA(boolean ret) throws Exception {
		setInteger("cnt", getInteger("cnt")+1l);
		setInteger("aa_check", getInteger("cnt"));
		return ret;
	}
	public boolean checkAB(boolean ret) throws Exception {
		setInteger("cnt", getInteger("cnt")+1l);
		setInteger("ab_check", getInteger("cnt"));
		return ret;
	}
	public boolean checkAC(boolean ret) throws Exception {
		setInteger("cnt", getInteger("cnt")+1l);
		setInteger("ac_check", getInteger("cnt"));
		return ret;
	}
	public boolean checkAD(boolean ret) throws Exception {
		setInteger("cnt", getInteger("cnt")+1l);
		setInteger("ad_check", getInteger("cnt"));
		return ret;
	}
	public boolean checkC(boolean ret) throws Exception {
		setInteger("cnt", getInteger("cnt")+1l);
		setInteger("c_check", getInteger("cnt"));
		return ret;
	}
	public long nextCounter() throws Exception {
		setInteger("cnt", getInteger("cnt")+1l);
		return getInteger("cnt");
	}
	@Test
	public void executionOrderWithNoTransitionInOrthogonalSubstates() throws Exception {
		set_up();
		mockUp.mockReturnValue("check", Lists.newArrayList("A"), checkA(false));
		mockUp.mockReturnValue("check", Lists.newArrayList("AA"), checkAA(false));
		mockUp.mockReturnValue("check", Lists.newArrayList("AB"), checkAB(false));
		mockUp.mockReturnValue("check", Lists.newArrayList("AC"), checkAC(false));
		mockUp.mockReturnValue("check", Lists.newArrayList("AD"), checkAD(false));
		mockUp.mockReturnValue("check", Lists.newArrayList("C"), checkC(false));
		mockUp.mockReturnValue("next", Lists.newArrayList(), nextCounter());
		interpreter.enter();
		timer.timeLeap(getCyclePeriod());
		assertTrue(getInteger("aa_check") == 1l);
		assertTrue(getInteger("aa_local") == 2l);
		assertTrue(getInteger("ac_check") == 3l);
		assertTrue(getInteger("ac_local") == 4l);
		assertTrue(getInteger("a_check") == 5l);
		assertTrue(getInteger("a_local") == 6l);
		assertTrue(getInteger("c_check") == 7l);
		assertTrue(getInteger("c_local") == 8l);
		assertTrue(getInteger("sm_local") == 9l);
	}
	@Test
	public void executionOrderWithTransitionInFirstSubRegion() throws Exception {
		set_up();
		mockUp.mockReturnValue("check", Lists.newArrayList("A"), checkA(false));
		mockUp.mockReturnValue("check", Lists.newArrayList("AA"), checkAA(true));
		mockUp.mockReturnValue("check", Lists.newArrayList("AB"), checkAB(false));
		mockUp.mockReturnValue("check", Lists.newArrayList("AC"), checkAC(false));
		mockUp.mockReturnValue("check", Lists.newArrayList("AD"), checkAD(false));
		mockUp.mockReturnValue("check", Lists.newArrayList("C"), checkC(false));
		mockUp.mockReturnValue("next", Lists.newArrayList(), nextCounter());
		interpreter.enter();
		timer.timeLeap(getCyclePeriod());
		assertTrue(getInteger("aa_check") == 1l);
		assertTrue(getInteger("aa_local") == 0l);
		assertTrue(getInteger("ac_check") == 2l);
		assertTrue(getInteger("ac_local") == 3l);
		assertTrue(getInteger("a_check") == 4l);
		assertTrue(getInteger("a_local") == 5l);
		assertTrue(getInteger("c_check") == 6l);
		assertTrue(getInteger("c_local") == 7l);
		assertTrue(getInteger("sm_local") == 8l);
	}
	@Test
	public void executionOrderWithTransitionInLastSubRegion() throws Exception {
		set_up();
		mockUp.mockReturnValue("check", Lists.newArrayList("A"), checkA(false));
		mockUp.mockReturnValue("check", Lists.newArrayList("AA"), checkAA(false));
		mockUp.mockReturnValue("check", Lists.newArrayList("AB"), checkAB(false));
		mockUp.mockReturnValue("check", Lists.newArrayList("AC"), checkAC(true));
		mockUp.mockReturnValue("check", Lists.newArrayList("AD"), checkAD(false));
		mockUp.mockReturnValue("check", Lists.newArrayList("C"), checkC(false));
		mockUp.mockReturnValue("next", Lists.newArrayList(), nextCounter());
		interpreter.enter();
		timer.timeLeap(getCyclePeriod());
		assertTrue(getInteger("aa_check") == 1l);
		assertTrue(getInteger("aa_local") == 2l);
		assertTrue(getInteger("ac_check") == 3l);
		assertTrue(getInteger("ac_local") == 0l);
		assertTrue(getInteger("a_check") == 0l);
		assertTrue(getInteger("a_local") == 4l);
		assertTrue(getInteger("c_check") == 5l);
		assertTrue(getInteger("c_local") == 6l);
		assertTrue(getInteger("sm_local") == 7l);
	}
	@Test
	public void executionOrderWithTransitionInAllSubRegion() throws Exception {
		set_up();
		mockUp.mockReturnValue("check", Lists.newArrayList("A"), checkA(false));
		mockUp.mockReturnValue("check", Lists.newArrayList("AA"), checkAA(true));
		mockUp.mockReturnValue("check", Lists.newArrayList("AB"), checkAB(false));
		mockUp.mockReturnValue("check", Lists.newArrayList("AC"), checkAC(true));
		mockUp.mockReturnValue("check", Lists.newArrayList("AD"), checkAD(false));
		mockUp.mockReturnValue("check", Lists.newArrayList("C"), checkC(false));
		mockUp.mockReturnValue("next", Lists.newArrayList(), nextCounter());
		interpreter.enter();
		timer.timeLeap(getCyclePeriod());
		assertTrue(getInteger("aa_check") == 1l);
		assertTrue(getInteger("aa_local") == 0l);
		assertTrue(getInteger("a_local") == 3l);
		assertTrue(getInteger("ac_check") == 2l);
		assertTrue(getInteger("ac_local") == 0l);
		assertTrue(getInteger("a_check") == 0l);
		assertTrue(getInteger("a_local") == 3l);
		assertTrue(getInteger("c_check") == 4l);
		assertTrue(getInteger("c_local") == 5l);
		assertTrue(getInteger("sm_local") == 6l);
	}
}
