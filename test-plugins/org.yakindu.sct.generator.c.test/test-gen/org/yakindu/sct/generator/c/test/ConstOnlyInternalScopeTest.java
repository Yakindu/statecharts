package org.yakindu.sct.generator.c.test;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.yakindu.sct.generator.c.gtest.GTest;
import org.yakindu.sct.generator.c.gtest.GTestRunner;
import org.yakindu.sct.generator.c.gtest.GTestHelper;

@GTest(
	statechartBundle = "org.yakindu.sct.test.models",
	sourceFile = "gtests/ConstOnlyInternalScope/ConstOnlyInternalScopeTest.cc",
	program = "gtests/ConstOnlyInternalScope/ConstOnlyInternalScope",
	model = "testmodels/SCTUnit/declarations/ConstOnlyInternalScope.sct",
	additionalFilesToCopy = {
		"libraryTarget/sc_timer_service.c",
		"libraryTarget/sc_timer_service.h"
	},
	additionalFilesToCompile = {
		"ConstOnlyInternalScope.c",
		"sc_timer_service.c"
	}
)
@RunWith(GTestRunner.class)
public class ConstOnlyInternalScopeTest {

	protected final GTestHelper helper = new GTestHelper(this);

	@Before
	public void setUp() {
		helper.generate();
		helper.compile();
	}
}
