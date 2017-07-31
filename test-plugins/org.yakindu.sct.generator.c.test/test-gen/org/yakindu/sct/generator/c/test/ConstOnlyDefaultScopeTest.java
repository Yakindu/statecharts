/* Generated by YAKINDU Statechart Tools code generator. */
package org.yakindu.sct.generator.c.test;

import java.util.Collection;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.yakindu.sct.generator.c.gtest.GTest;
import org.yakindu.sct.generator.c.gtest.GTestRunner;
import org.yakindu.sct.generator.c.gtest.GTestHelper;

@GTest(sourceFile = "gtests/ConstOnlyDefaultScope/ConstOnlyDefaultScopeTest.cc", program = "gtests/ConstOnlyDefaultScope/ConstOnlyDefaultScope", model = "testmodels/SCTUnit/declarations/ConstOnlyDefaultScope.sct" )
@RunWith(GTestRunner.class)
public class ConstOnlyDefaultScopeTest {

	protected final GTestHelper helper = new GTestHelper(this) {
	
		@Override
		protected void getSourceFiles(Collection<String> files) {
			super.getSourceFiles(files);
			
			files.add(getFileName(getTestProgram()) + ".c");
		}
	};

	@Before
	public void setUp() {
	helper.generate();
	helper.compile();
	}
}
