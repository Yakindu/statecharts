package org.yakindu.sct.generator.cpp.test;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.yakindu.sct.generator.c.gtest.GTest;
import org.yakindu.sct.generator.c.gtest.GTestRunner;
import org.yakindu.sct.generator.c.gtest.GTestHelper;
import org.yakindu.sct.generator.c.gtest.GTestHelper.Compiler;
import org.yakindu.sct.generator.c.gtest.GMockHelper;

@GTest(
	statechartBundle = "org.yakindu.sct.test.models",
	sourceFile = "gtests/ChildFirstOrthogonalReactions/ChildFirstOrthogonalReactionsTest.cc",
	program = "gtests/ChildFirstOrthogonalReactions/ChildFirstOrthogonalReactions",
	model = "testmodels/SCTUnit/executionorder/ChildFirstOrthogonalReactions.sct",
	additionalFilesToCopy = {
		"libraryTarget/sc_runner.h",
		"libraryTarget/sc_runner.cpp"
	},
	additionalFilesToCompile = {
		"ChildFirstOrthogonalReactions.cpp",
		"sc_runner.cpp"
	}
)
@RunWith(GTestRunner.class)
public class ChildFirstOrthogonalReactionsTest {
protected final GTestHelper helper = new GMockHelper(this);

	@Before
	public void setUp() {
		helper.generate();
		helper.compile();
	}

}
