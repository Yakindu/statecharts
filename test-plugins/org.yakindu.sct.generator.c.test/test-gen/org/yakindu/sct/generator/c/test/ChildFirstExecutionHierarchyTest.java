package org.yakindu.sct.generator.c.test;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.yakindu.sct.generator.c.gtest.GTest;
import org.yakindu.sct.generator.c.gtest.GTestRunner;
import org.yakindu.sct.generator.c.gtest.GTestHelper;

@GTest(
	statechartBundle = "org.yakindu.sct.test.models",
	sourceFile = "gtests/ChildFirstExecutionHierarchy/ChildFirstExecutionHierarchyTest.cc",
	program = "gtests/ChildFirstExecutionHierarchy/ChildFirstExecutionHierarchy",
	model = "testmodels/SCTUnit/executionorder/ChildFirstExecutionHierarchy.sct",
	additionalFilesToCopy = {
		"libraryTarget/sc_timer_service.c",
		"libraryTarget/sc_timer_service.h"
	},
	additionalFilesToCompile = {
		"ChildFirstExecutionHierarchy.c",
		"sc_timer_service.c"
	}
)
@RunWith(GTestRunner.class)
public class ChildFirstExecutionHierarchyTest {

	protected final GTestHelper helper = new GTestHelper(this);

	@Before
	public void setUp() {
		helper.generate();
		helper.compile();
	}
}
