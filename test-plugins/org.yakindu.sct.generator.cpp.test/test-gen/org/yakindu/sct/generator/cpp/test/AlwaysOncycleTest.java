/* Generated by YAKINDU Statechart Tools code generator. */
package org.yakindu.sct.generator.cpp.test;

import java.util.Collection;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.yakindu.sct.generator.c.gtest.GTest;
import org.yakindu.sct.generator.c.gtest.GTestRunner;
import org.yakindu.sct.generator.c.gtest.GTestHelper;

@GTest(sourceFile = "gtests/AlwaysOncycleTest/AlwaysOncycleTest.cc", program = "gtests/AlwaysOncycleTest/AlwaysOncycle", model = "testmodels/SCTUnit/AlwaysOncycle.sct" )
@RunWith(GTestRunner.class)
public class AlwaysOncycleTest {
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
