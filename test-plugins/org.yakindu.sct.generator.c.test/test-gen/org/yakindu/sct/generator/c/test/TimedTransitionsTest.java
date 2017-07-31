/* Generated by YAKINDU Statechart Tools code generator. */
package org.yakindu.sct.generator.c.test;

import java.util.Collection;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.yakindu.sct.generator.c.gtest.GTest;
import org.yakindu.sct.generator.c.gtest.GTestRunner;
import org.yakindu.sct.generator.c.gtest.GTestHelper;

@GTest(sourceFile = "gtests/TimedTransitions/TimedTransitionsTest.cc", program = "gtests/TimedTransitions/TimedTransitions", model = "testmodels/SCTUnit/TimedTransitions.sct" )
@RunWith(GTestRunner.class)
public class TimedTransitionsTest {

	protected final GTestHelper helper = new GTestHelper(this) {
	
		@Override
		protected void getTestDataFiles(Collection<String> files) {
			super.getTestDataFiles(files);
			files.add(getTargetPath().toOSString() + "/sc_timer_service.h");
			files.add(getTargetPath().toOSString() + "/sc_timer_service.c");
		}
		@Override
		protected void getSourceFiles(Collection<String> files) {
			super.getSourceFiles(files);
			
			files.add(getFileName(getTestProgram()) + ".c");
			files.add("sc_timer_service.c");
		}
	};

	@Before
	public void setUp() {
	helper.generate();
	helper.compile();
	}
}
