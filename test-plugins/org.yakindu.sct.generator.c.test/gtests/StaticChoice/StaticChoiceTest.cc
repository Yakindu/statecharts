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
#include "StaticChoice.h"

static StaticChoice statechart;

class StatemachineTest : public ::testing::Test{
	protected:
	virtual void SetUp() {
		staticChoice_init(&statechart);
	}
};


TEST_F(StatemachineTest, StaticChoiceTest) {					
	staticChoice_enter(&statechart);
	EXPECT_TRUE(staticChoice_isStateActive(&statechart, StaticChoice_main_region_Start));
	staticChoice_runCycle(&statechart);
}


