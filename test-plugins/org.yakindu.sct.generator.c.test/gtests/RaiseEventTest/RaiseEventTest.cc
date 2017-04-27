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
#include "RaiseEvent.h"

RaiseEvent handle;

class StatemachineTest : public ::testing::Test{
	protected:
	virtual void SetUp() {
		raiseEvent_init(&handle);
	}
};

TEST_F(StatemachineTest, raiseEvent) {					
	raiseEvent_enter(&handle);
	EXPECT_TRUE(raiseEvent_isStateActive(&handle, RaiseEvent_second_region_SateA));
	EXPECT_TRUE(raiseEvent_isStateActive(&handle, RaiseEvent_main_region_StateA));
	raiseEventIface_raise_e2(&handle);
	raiseEvent_runCycle(&handle);
	raiseEvent_runCycle(&handle);
	EXPECT_TRUE(raiseEvent_isStateActive(&handle, RaiseEvent_second_region_StateB));
	EXPECT_TRUE(raiseEvent_isStateActive(&handle, RaiseEvent_main_region_StateB));
}


