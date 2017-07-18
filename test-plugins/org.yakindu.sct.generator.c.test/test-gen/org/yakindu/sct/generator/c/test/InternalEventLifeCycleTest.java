/* Generated by YAKINDU Statechart Tools code generator. */
package org.yakindu.sct.generator.c.test;

import java.util.Collection;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.yakindu.sct.generator.c.gtest.GTest;
import org.yakindu.sct.generator.c.gtest.GTestRunner;
import org.yakindu.sct.generator.c.gtest.GTestHelper;

@GTest(sourceFile = "gtests/InternalEventLifeCycle/InternalEventLifeCycleTest.cc", program = "gtests/InternalEventLifeCycle/InternalEventLifeCycle", model = "testmodels/SCTUnit/InternalEventLifeCycle.sct" )
@RunWith(GTestRunner.class)
public class InternalEventLifeCycleTest {

	protected final GTestHelper helper = new GTestHelper(this) {
	
		@Override
		protected void getSourceFiles(Collection<String> files) {
			super.getSourceFiles(files);
			
			files.add(getFileName(getTestProgram()) + ".c");
		}
	};

	@Before
	public void setUp() {
	helper.generate();
	helper.compile();
	}
}
