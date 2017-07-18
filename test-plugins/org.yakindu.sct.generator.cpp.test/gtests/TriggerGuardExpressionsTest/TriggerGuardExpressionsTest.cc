/* Generated by YAKINDU Statechart Tools code generator. */
#include <string>
#include "gtest/gtest.h"
#include "TriggerGuardExpressions.h"
#include "sc_types.h"
static TriggerGuardExpressions* statechart;


class StatemachineTest : public ::testing::Test{
	protected:
	virtual void SetUp() {
		statechart = new TriggerGuardExpressions();
		statechart->init();
	}
	virtual void TearDown() {
		delete statechart;
	}
};


TEST_F(StatemachineTest, trueGuard) {
	
	statechart->enter();
	
	EXPECT_TRUE(statechart->isStateActive(TriggerGuardExpressions::main_region_A));
	
	statechart->getDefaultSCI()->raise_e1();
	
	statechart->getDefaultSCI()->set_b(true);
	
	statechart->runCycle();
	
	EXPECT_TRUE(statechart->isStateActive(TriggerGuardExpressions::main_region_B));
	
	statechart->runCycle();
	
	EXPECT_TRUE(statechart->isStateActive(TriggerGuardExpressions::main_region_A));
	
	statechart->getDefaultSCI()->raise_e2();
	
	statechart->runCycle();
	
	EXPECT_TRUE(statechart->isStateActive(TriggerGuardExpressions::main_region_B));
	
	statechart->runCycle();
	
	EXPECT_TRUE(statechart->isStateActive(TriggerGuardExpressions::main_region_A));
	
	statechart->getDefaultSCI()->raise_e1();
	
	statechart->getDefaultSCI()->raise_e2();
	
	statechart->runCycle();
	
	EXPECT_TRUE(statechart->isStateActive(TriggerGuardExpressions::main_region_B));
	
}
TEST_F(StatemachineTest, falseGuard) {
	
	statechart->enter();
	
	EXPECT_TRUE(statechart->isStateActive(TriggerGuardExpressions::main_region_A));
	
	statechart->getDefaultSCI()->set_b(false);
	
	statechart->getDefaultSCI()->raise_e1();
	
	statechart->runCycle();
	
	EXPECT_TRUE(statechart->isStateActive(TriggerGuardExpressions::main_region_A));
	
	statechart->getDefaultSCI()->raise_e2();
	
	statechart->runCycle();
	
	EXPECT_TRUE(statechart->isStateActive(TriggerGuardExpressions::main_region_A));
	
	statechart->getDefaultSCI()->raise_e1();
	
	statechart->getDefaultSCI()->raise_e2();
	
	statechart->runCycle();
	
	EXPECT_TRUE(statechart->isStateActive(TriggerGuardExpressions::main_region_A));
	
}
