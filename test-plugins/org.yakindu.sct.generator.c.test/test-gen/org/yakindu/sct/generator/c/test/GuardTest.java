package org.yakindu.sct.generator.c.test;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.yakindu.sct.generator.c.gtest.GTest;
import org.yakindu.sct.generator.c.gtest.GTestRunner;
import org.yakindu.sct.generator.c.gtest.GTestHelper;

@GTest(
	statechartBundle = "org.yakindu.sct.test.models",
	sourceFile = "gtests/Guard/GuardTest.cc",
	program = "gtests/Guard/Guard",
	model = "testmodels/SCTUnit/Guard.sct",
	additionalFilesToCopy = {
		"libraryTarget/sc_timer_service.c",
		"libraryTarget/sc_timer_service.h"
	},
	additionalFilesToCompile = {
		"Guard.c",
		"sc_timer_service.c"
	}
)
@RunWith(GTestRunner.class)
public class GuardTest {

	protected final GTestHelper helper = new GTestHelper(this);

	@Before
	public void setUp() {
		helper.generate();
		helper.compile();
	}
}
