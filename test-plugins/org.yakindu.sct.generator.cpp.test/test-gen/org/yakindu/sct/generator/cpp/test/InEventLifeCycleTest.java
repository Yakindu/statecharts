package org.yakindu.sct.generator.cpp.test;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.yakindu.sct.generator.c.gtest.GTest;
import org.yakindu.sct.generator.c.gtest.GTestRunner;
import org.yakindu.sct.generator.c.gtest.GTestHelper;
import org.yakindu.sct.generator.c.gtest.GTestHelper.Compiler;

@GTest(
	statechartBundle = "org.yakindu.sct.test.models",
	sourceFile = "gtests/InEventLifeCycleTest/InEventLifeCycleTest.cc",
	program = "gtests/InEventLifeCycleTest/InEventLifeCycle",
	model = "testmodels/SCTUnit/InEventLifeCycle.sct",
	additionalFilesToCopy = {
		"libraryTarget/sc_runner.h",
		"libraryTarget/sc_runner.cpp"
	},
	additionalFilesToCompile = {
		"InEventLifeCycle.cpp",
		"sc_runner.cpp"
	}
)
@RunWith(GTestRunner.class)
public class InEventLifeCycleTest {
protected final GTestHelper helper = new GTestHelper(this, Compiler.GPLUSPLUS);

	@Before
	public void setUp() {
		helper.generate();
		helper.compile();
	}

}
