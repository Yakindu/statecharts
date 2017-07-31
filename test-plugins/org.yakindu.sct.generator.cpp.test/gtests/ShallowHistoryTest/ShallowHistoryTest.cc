/* Generated by YAKINDU Statechart Tools code generator. */
#include <string>
#include "gtest/gtest.h"
#include "ShallowHistory.h"
#include "sc_types.h"
static ShallowHistory* statechart;


class StatemachineTest : public ::testing::Test{
	protected:
	virtual void SetUp() {
		statechart = new ShallowHistory();
		statechart->init();
	}
	virtual void TearDown() {
		delete statechart;
	}
};


TEST_F(StatemachineTest, shallowHistoryTest) {
	
	statechart->enter();
	
	statechart->getDefaultSCI()->raise_event1();
	
	statechart->runCycle();
	
	statechart->getDefaultSCI()->raise_event3();
	
	statechart->runCycle();
	
	statechart->getDefaultSCI()->raise_event5();
	
	statechart->runCycle();
	
	statechart->getDefaultSCI()->raise_event7();
	
	statechart->runCycle();
	
	EXPECT_TRUE(!statechart->isStateActive(ShallowHistory::mainRegion_State1));
	
	EXPECT_TRUE(statechart->isStateActive(ShallowHistory::mainRegion_State2__region0_State4__region0_State7__region0_State9));
	
	statechart->getDefaultSCI()->raise_event6();
	
	statechart->runCycle();
	
	EXPECT_TRUE(!statechart->isStateActive(ShallowHistory::mainRegion_State2__region0_State4__region0_State7__region0_State9));
	
	EXPECT_TRUE(statechart->isStateActive(ShallowHistory::mainRegion_State2__region0_State4__region0_State6));
	
	statechart->getDefaultSCI()->raise_event5();
	
	statechart->runCycle();
	
	EXPECT_TRUE(!statechart->isStateActive(ShallowHistory::mainRegion_State2__region0_State4__region0_State7__region0_State8));
	
	EXPECT_TRUE(statechart->isStateActive(ShallowHistory::mainRegion_State2__region0_State4__region0_State7__region0_State9));
	
	statechart->getDefaultSCI()->raise_event2();
	
	statechart->runCycle();
	
	EXPECT_TRUE(!statechart->isStateActive(ShallowHistory::mainRegion_State2__region0_State4__region0_State7__region0_State9));
	
	EXPECT_TRUE(statechart->isStateActive(ShallowHistory::mainRegion_State1));
	
	statechart->getDefaultSCI()->raise_event1();
	
	statechart->runCycle();
	
	EXPECT_TRUE(statechart->isStateActive(ShallowHistory::mainRegion_State2__region0_State4__region0_State6));
	
	EXPECT_TRUE(!statechart->isStateActive(ShallowHistory::mainRegion_State1));
	
	statechart->getDefaultSCI()->raise_event5();
	
	statechart->runCycle();
	
	EXPECT_TRUE(!statechart->isStateActive(ShallowHistory::mainRegion_State2__region0_State4__region0_State6));
	
	EXPECT_TRUE(statechart->isStateActive(ShallowHistory::mainRegion_State2__region0_State4__region0_State7__region0_State9));
	
}
