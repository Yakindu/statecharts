/**
* Copyright (c) 2017 committers of YAKINDU and others.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*
* Contributors:
*     committers of YAKINDU - initial API and implementation
*/
#include "gtest/gtest.h"
#include "ExitOnSelfTransition.h"

ExitOnSelfTransition handle;

class StatemachineTest : public ::testing::Test{
	protected:
	virtual void SetUp() {
		exitOnSelfTransition_init(&handle);
	}
};

TEST_F(StatemachineTest, ExitOnSelfTransitionTest) {					
	exitOnSelfTransition_enter(&handle);
	EXPECT_TRUE(exitOnSelfTransition_isStateActive(&handle, ExitOnSelfTransition_main_region_A));
	EXPECT_TRUE(exitOnSelfTransitionIface_get_entryCount(&handle)== 1l);
	EXPECT_TRUE(exitOnSelfTransitionIface_get_exitCount(&handle)== 0l);
	exitOnSelfTransitionIface_raise_e(&handle);
	exitOnSelfTransition_runCycle(&handle);
	EXPECT_TRUE(exitOnSelfTransitionIface_get_entryCount(&handle)== 2l);
	EXPECT_TRUE(exitOnSelfTransitionIface_get_exitCount(&handle)== 1l);
	exitOnSelfTransitionIface_raise_f(&handle);
	exitOnSelfTransition_runCycle(&handle);
	EXPECT_TRUE(exitOnSelfTransitionIface_get_entryCount(&handle)== 2l);
	EXPECT_TRUE(exitOnSelfTransitionIface_get_exitCount(&handle)== 2l);
}


