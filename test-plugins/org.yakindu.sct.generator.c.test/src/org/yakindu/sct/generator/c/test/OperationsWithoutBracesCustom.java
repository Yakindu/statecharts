/**
 * Copyright (c) 2014 committers of YAKINDU and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     committers of YAKINDU - initial API and implementation
 */
package org.yakindu.sct.generator.c.test;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.yakindu.sct.generator.c.gtest.GTest;
import org.yakindu.sct.generator.c.gtest.GTestHelper;
import org.yakindu.sct.generator.c.gtest.GTestRunner;

@GTest(
		sourceFile = "gtests/OperationsWithoutBraces/OperationsWithoutBracesCustom.cc", 
		program = "gtests/OperationsWithoutBraces/OperationsWithoutBraces", 
		model = "testmodels/SCTUnit/OperationsWithoutBraces.sct", 
		statechartBundle = "org.yakindu.sct.test.models",
		additionalFilesToCompile = {
				"OperationsWithoutBraces.c"
			}
		)
@RunWith(GTestRunner.class)
public class OperationsWithoutBracesCustom {

	protected final GTestHelper helper = new GTestHelper(this) {
	};

	@Before
	public void setUp() {
		helper.generate();
		helper.compile();
	}
}
