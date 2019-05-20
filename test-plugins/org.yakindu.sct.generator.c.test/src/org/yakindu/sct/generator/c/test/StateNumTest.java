/* Generated by YAKINDU Statechart Tools code generator. */
package org.yakindu.sct.generator.c.test;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.yakindu.sct.generator.c.gtest.GTest;
import org.yakindu.sct.generator.c.gtest.GTestHelper;
import org.yakindu.sct.generator.c.gtest.GTestRunner;
import org.yakindu.sct.generator.c.gtest.RunIfEnv;

@GTest(
		statechartBundle = "org.yakindu.sct.test.models",
		sourceFile = "gtests/StateNumTest/StateNumTest.cc", program = "gtests/StateNumTest/StateNumTest", model = "testmodels/SCTUnit/HistoryWithoutInitialStep.sct",
		additionalFilesToCopy = {
				"libraryTarget/sc_timer_service.c",
				"libraryTarget/sc_timer_service.h"
		},
		additionalFilesToCompile = {
				"HistoryWithoutInitialStep.c",
				"sc_timer_service.c"
		}
		)
@RunWith(GTestRunner.class)
@RunIfEnv("FAILING_GENERATOR_TESTS")
public class StateNumTest {

	protected final GTestHelper helper = new GTestHelper(this);

	@Before
	public void setUp() {
		helper.generate();
		helper.compile();
	}
}
