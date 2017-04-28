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
#include <string>
#include "gtest/gtest.h"
#include "SimpleEvent.h"
#include "sc_types.h"
SimpleEvent* statechart;

class StatemachineTest : public ::testing::Test{
	protected:
	virtual void SetUp() {
		statechart = new SimpleEvent();
		statechart->init();
	}
	virtual void TearDown() {
		delete statechart;
	}
};


TEST_F(StatemachineTest, simpleEventTest) {
	
	
	statechart->enter();
	
	EXPECT_TRUE(statechart->isStateActive(SimpleEvent::main_region_A)) << "Expected A to be active" ;
	
	EXPECT_TRUE(5l== 5l);
	
	statechart->raise_event1();
	
	statechart->runCycle();
	
	EXPECT_TRUE(statechart->isStateActive(SimpleEvent::main_region_B)) << "Expected B to be active" ;
	
	statechart->runCycle();
	
	EXPECT_TRUE(!statechart->isStateActive(SimpleEvent::main_region_B));
	
}
