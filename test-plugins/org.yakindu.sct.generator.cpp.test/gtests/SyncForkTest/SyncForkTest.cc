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
#include "SyncFork.h"
#include "sc_types.h"
SyncFork* statechart;

class StatemachineTest : public ::testing::Test{
	protected:
	virtual void SetUp() {
		statechart = new SyncFork();
		statechart->init();
	}
	virtual void TearDown() {
		delete statechart;
	}
};


TEST_F(StatemachineTest, syncForkTest) {
	
	
	statechart->enter();
	
	EXPECT_TRUE(statechart->isStateActive(SyncFork::main_region_A));
	
	statechart->raise_f();
	
	statechart->runCycle();
	
	EXPECT_TRUE(statechart->isStateActive(SyncFork::main_region_B));
	
	EXPECT_TRUE(statechart->isStateActive(SyncFork::main_region_B_r1_C1));
	
	EXPECT_TRUE(statechart->isStateActive(SyncFork::main_region_B_r2_D1));
	
	statechart->raise_f();
	
	statechart->runCycle();
	
	EXPECT_TRUE(statechart->isStateActive(SyncFork::main_region_B));
	
	EXPECT_TRUE(statechart->isStateActive(SyncFork::main_region_B_r1_C2));
	
	EXPECT_TRUE(statechart->isStateActive(SyncFork::main_region_B_r2_D2));
	
	statechart->raise_e();
	
	statechart->runCycle();
	
	EXPECT_TRUE(statechart->isStateActive(SyncFork::main_region_A));
	
	statechart->raise_f();
	
	statechart->runCycle();
	
	EXPECT_TRUE(statechart->isStateActive(SyncFork::main_region_B));
	
	EXPECT_TRUE(statechart->isStateActive(SyncFork::main_region_B_r1_C1));
	
	EXPECT_TRUE(statechart->isStateActive(SyncFork::main_region_B_r2_D1));
	
}
