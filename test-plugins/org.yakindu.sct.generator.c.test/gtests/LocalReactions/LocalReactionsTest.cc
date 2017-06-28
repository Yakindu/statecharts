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
#include "LocalReactions.h"

static LocalReactions statechart;

class StatemachineTest : public ::testing::Test{
	protected:
	virtual void SetUp() {
		localReactions_init(&statechart);
	}
};


TEST_F(StatemachineTest, LocalReactionsTest) {					
	localReactions_enter(&statechart);
	EXPECT_TRUE(localReactions_isStateActive(&statechart, LocalReactions_main_region_A));
	localReactions_runCycle(&statechart);
	EXPECT_TRUE(localReactionsIface_get_x(&statechart)== 1l);
}


