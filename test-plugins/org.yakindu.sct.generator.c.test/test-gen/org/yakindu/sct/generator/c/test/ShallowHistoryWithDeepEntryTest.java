package org.yakindu.sct.generator.c.test;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.yakindu.sct.generator.c.gtest.GTest;
import org.yakindu.sct.generator.c.gtest.GTestRunner;
import org.yakindu.sct.generator.c.gtest.GTestHelper;

@GTest(
	statechartBundle = "org.yakindu.sct.test.models",
	sourceFile = "gtests/ShallowHistoryWithDeepEntry/ShallowHistoryWithDeepEntryTest.cc",
	program = "gtests/ShallowHistoryWithDeepEntry/ShallowHistoryWithDeepEntry",
	model = "testmodels/SCTUnit/ShallowHistoryWithDeepEntry.sct",
	additionalFilesToCopy = {
		"libraryTarget/sc_timer_service.c",
		"libraryTarget/sc_timer_service.h"
	},
	additionalFilesToCompile = {
		"ShallowHistoryWithDeepEntry.c",
		"sc_timer_service.c"
	}
)
@RunWith(GTestRunner.class)
public class ShallowHistoryWithDeepEntryTest {

	protected final GTestHelper helper = new GTestHelper(this);

	@Before
	public void setUp() {
		helper.generate();
		helper.compile();
	}
}
