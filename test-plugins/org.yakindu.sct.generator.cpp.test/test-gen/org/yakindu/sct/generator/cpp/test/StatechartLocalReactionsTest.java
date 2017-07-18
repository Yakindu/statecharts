/* Generated by YAKINDU Statechart Tools code generator. */
package org.yakindu.sct.generator.cpp.test;

import java.util.Collection;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.yakindu.sct.generator.c.gtest.GTest;
import org.yakindu.sct.generator.c.gtest.GTestRunner;
import org.yakindu.sct.generator.c.gtest.GTestHelper;

@GTest(sourceFile = "gtests/StatechartLocalReactionsTest/StatechartLocalReactionsTest.cc", program = "gtests/StatechartLocalReactionsTest/StatechartLocalReactions", model = "testmodels/SCTUnit/StatechartLocalReactions.sct" )
@RunWith(GTestRunner.class)
public class StatechartLocalReactionsTest {
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
