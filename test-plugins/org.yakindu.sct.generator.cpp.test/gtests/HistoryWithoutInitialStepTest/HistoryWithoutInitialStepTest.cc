#include <string>
#include "gtest/gtest.h"
#include "HistoryWithoutInitialStep.h"
#include "sc_runner.h"
#include "sc_types.h"

namespace  {

void init();
HistoryWithoutInitialStep* statechart;



//! The timers are managed by a timer service. */
static SctUnitRunner * runner;

class HistoryWithoutInitialStepTest : public ::testing::Test{
	protected:
	virtual void SetUp() {
		statechart = new HistoryWithoutInitialStep();
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

void init(){
	statechart->enter();
	
	EXPECT_TRUE(statechart->isStateActive(HistoryWithoutInitialStep::main_region_A));
	
	statechart->getDefaultSCI()->raise_toB();
	
	runner->proceed_cycles(1);
	
	
}

TEST_F(HistoryWithoutInitialStepTest, enterThroughInitialEntry) {
	
	init();
	
	EXPECT_TRUE(statechart->isStateActive(HistoryWithoutInitialStep::main_region_B_r1_C));
	
	statechart->getDefaultSCI()->raise_next();
	
	runner->proceed_cycles(1);
	
	EXPECT_TRUE(statechart->isStateActive(HistoryWithoutInitialStep::main_region_B_r1_D));
	
	
}
TEST_F(HistoryWithoutInitialStepTest, enterCThroughHistory) {
	
	init();
	
	EXPECT_TRUE(statechart->isStateActive(HistoryWithoutInitialStep::main_region_B_r1_C));
	
	statechart->getDefaultSCI()->raise_toA();
	
	runner->proceed_cycles(1);
	
	EXPECT_TRUE(statechart->isStateActive(HistoryWithoutInitialStep::main_region_A));
	
	statechart->getDefaultSCI()->raise_toHistory();
	
	runner->proceed_cycles(1);
	
	EXPECT_TRUE(statechart->isStateActive(HistoryWithoutInitialStep::main_region_B_r1_C));
	
	
}
TEST_F(HistoryWithoutInitialStepTest, enterDThroughHistory) {
	
	init();
	
	statechart->getDefaultSCI()->raise_next();
	
	runner->proceed_cycles(1);
	
	EXPECT_TRUE(statechart->isStateActive(HistoryWithoutInitialStep::main_region_B_r1_D));
	
	statechart->getDefaultSCI()->raise_toA();
	
	runner->proceed_cycles(1);
	
	EXPECT_TRUE(statechart->isStateActive(HistoryWithoutInitialStep::main_region_A));
	
	statechart->getDefaultSCI()->raise_toHistory();
	
	runner->proceed_cycles(1);
	
	EXPECT_TRUE(statechart->isStateActive(HistoryWithoutInitialStep::main_region_B_r1_D));
	
	
}
TEST_F(HistoryWithoutInitialStepTest, enterThroughHistoryWithoutInit) {
	
	statechart->enter();
	
	EXPECT_TRUE(statechart->isStateActive(HistoryWithoutInitialStep::main_region_A));
	
	statechart->getDefaultSCI()->raise_toHistory();
	
	runner->proceed_cycles(1);
	
	EXPECT_TRUE(statechart->isStateActive(HistoryWithoutInitialStep::main_region_B_r1_C));
	
	
}

}

