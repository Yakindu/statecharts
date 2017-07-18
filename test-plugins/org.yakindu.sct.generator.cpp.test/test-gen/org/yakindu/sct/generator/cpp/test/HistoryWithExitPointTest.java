/* Generated by YAKINDU Statechart Tools code generator. */
package org.yakindu.sct.generator.cpp.test;

import java.util.Collection;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.yakindu.sct.generator.c.gtest.GTest;
import org.yakindu.sct.generator.c.gtest.GTestRunner;
import org.yakindu.sct.generator.c.gtest.GTestHelper;

@GTest(sourceFile = "gtests/HistoryWithExitPointTest/HistoryWithExitPointTest.cc", program = "gtests/HistoryWithExitPointTest/HistoryWithExitPoint", model = "testmodels/SCTUnit/HistoryWithExitPoint.sct" )
@RunWith(GTestRunner.class)
public class HistoryWithExitPointTest {
protected final GTestHelper helper = new GTestHelper(this) {
	@Override
	protected void getSourceFiles(Collection<String> files) {
		super.getSourceFiles(files);
		files.add(getFileName(getTestProgram()) + ".cpp");
		}
	};

	@Before
	public void setUp() {
	helper.generate();
	helper.compile();
	}

}
