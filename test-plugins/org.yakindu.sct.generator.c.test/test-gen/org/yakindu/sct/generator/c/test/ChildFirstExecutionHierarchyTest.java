/* Generated by YAKINDU Statechart Tools code generator. */
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
		"gtests/ChildFirstExecutionHierarchy/sc_timer_service.c",
		"gtests/ChildFirstExecutionHierarchy/sc_timer_service.h"
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
