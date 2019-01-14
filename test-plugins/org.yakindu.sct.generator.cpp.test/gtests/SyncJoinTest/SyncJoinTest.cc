#include <string>
#include "gtest/gtest.h"
#include "SyncJoin.h"
#include "sc_runner.h"
#include "sc_types.h"

namespace  {

SyncJoin* statechart;



//! The timers are managed by a timer service. */
static SctUnitRunner * runner;

class SyncJoinTest : public ::testing::Test{
	protected:
	virtual void SetUp() {
		statechart = new SyncJoin();
		statechart->init();
		runner = new SctUnitRunner(
			statechart,
			false,
			200
		);
	}
	virtual void TearDown() {
		delete statechart;
		delete runner;
	}
};


TEST_F(SyncJoinTest, syncJoin_C2_Waits) {
	
	statechart->enter();
	
	EXPECT_TRUE(statechart->isStateActive(SyncJoin::main_region_B));
	
	EXPECT_TRUE(statechart->isStateActive(SyncJoin::main_region_B_r1_C1));
	
	EXPECT_TRUE(statechart->isStateActive(SyncJoin::main_region_B_r2_D1));
	
	statechart->getDefaultSCI()->raise_e();
	
	runner->proceed_cycles(1);
	
	EXPECT_TRUE(statechart->isStateActive(SyncJoin::main_region_B_r1_C2));
	
	EXPECT_TRUE(statechart->isStateActive(SyncJoin::main_region_B_r2_D1));
	
	statechart->getDefaultSCI()->raise_jc();
	
	runner->proceed_cycles(1);
	
	EXPECT_TRUE(statechart->isStateActive(SyncJoin::main_region_B_r1_C2));
	
	EXPECT_TRUE(statechart->isStateActive(SyncJoin::main_region_B_r2_D1));
	
	statechart->getDefaultSCI()->raise_jd();
	
	runner->proceed_cycles(1);
	
	EXPECT_TRUE(statechart->isStateActive(SyncJoin::main_region_B_r1_C2));
	
	EXPECT_TRUE(statechart->isStateActive(SyncJoin::main_region_B_r2_D1));
	
	statechart->getDefaultSCI()->raise_jc();
	
	statechart->getDefaultSCI()->raise_jd();
	
	runner->proceed_cycles(1);
	
	EXPECT_TRUE(statechart->isStateActive(SyncJoin::main_region_B_r1_C2));
	
	EXPECT_TRUE(statechart->isStateActive(SyncJoin::main_region_B_r2_D1));
	
	statechart->getDefaultSCI()->raise_f();
	
	runner->proceed_cycles(1);
	
	EXPECT_TRUE(statechart->isStateActive(SyncJoin::main_region_B_r1_C2));
	
	EXPECT_TRUE(statechart->isStateActive(SyncJoin::main_region_B_r2_D2));
	
	statechart->getDefaultSCI()->raise_jc();
	
	runner->proceed_cycles(1);
	
	EXPECT_TRUE(statechart->isStateActive(SyncJoin::main_region_B_r1_C2));
	
	EXPECT_TRUE(statechart->isStateActive(SyncJoin::main_region_B_r2_D2));
	
	statechart->getDefaultSCI()->raise_jd();
	
	runner->proceed_cycles(1);
	
	EXPECT_TRUE(statechart->isStateActive(SyncJoin::main_region_B_r1_C2));
	
	EXPECT_TRUE(statechart->isStateActive(SyncJoin::main_region_B_r2_D2));
	
	statechart->getDefaultSCI()->raise_jc();
	
	statechart->getDefaultSCI()->raise_jd();
	
	runner->proceed_cycles(1);
	
	EXPECT_TRUE(statechart->isStateActive(SyncJoin::main_region_A));
	
	
}
TEST_F(SyncJoinTest, syncJoin_D2_Waits) {
	
	statechart->enter();
	
	EXPECT_TRUE(statechart->isStateActive(SyncJoin::main_region_B));
	
	EXPECT_TRUE(statechart->isStateActive(SyncJoin::main_region_B_r1_C1));
	
	EXPECT_TRUE(statechart->isStateActive(SyncJoin::main_region_B_r2_D1));
	
	statechart->getDefaultSCI()->raise_f();
	
	runner->proceed_cycles(1);
	
	EXPECT_TRUE(statechart->isStateActive(SyncJoin::main_region_B_r1_C1));
	
	EXPECT_TRUE(statechart->isStateActive(SyncJoin::main_region_B_r2_D2));
	
	statechart->getDefaultSCI()->raise_jc();
	
	runner->proceed_cycles(1);
	
	EXPECT_TRUE(statechart->isStateActive(SyncJoin::main_region_B_r1_C1));
	
	EXPECT_TRUE(statechart->isStateActive(SyncJoin::main_region_B_r2_D2));
	
	statechart->getDefaultSCI()->raise_jd();
	
	runner->proceed_cycles(1);
	
	EXPECT_TRUE(statechart->isStateActive(SyncJoin::main_region_B_r1_C1));
	
	EXPECT_TRUE(statechart->isStateActive(SyncJoin::main_region_B_r2_D2));
	
	statechart->getDefaultSCI()->raise_jc();
	
	statechart->getDefaultSCI()->raise_jd();
	
	runner->proceed_cycles(1);
	
	EXPECT_TRUE(statechart->isStateActive(SyncJoin::main_region_B_r1_C1));
	
	EXPECT_TRUE(statechart->isStateActive(SyncJoin::main_region_B_r2_D2));
	
	statechart->getDefaultSCI()->raise_e();
	
	runner->proceed_cycles(1);
	
	EXPECT_TRUE(statechart->isStateActive(SyncJoin::main_region_B_r1_C2));
	
	EXPECT_TRUE(statechart->isStateActive(SyncJoin::main_region_B_r2_D2));
	
	
}
TEST_F(SyncJoinTest, doubleEntryActionBug) {
	
	statechart->enter();
	
	statechart->getDefaultSCI()->raise_e();
	
	statechart->getDefaultSCI()->raise_f();
	
	runner->proceed_cycles(1);
	
	statechart->getDefaultSCI()->raise_jc();
	
	statechart->getDefaultSCI()->raise_jd();
	
	runner->proceed_cycles(1);
	
	EXPECT_TRUE(statechart->isStateActive(SyncJoin::main_region_A));
	
	EXPECT_TRUE(statechart->getDefaultSCI()->get_x()== 1);
	
	
}

}

