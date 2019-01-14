#include <string>
#include "gtest/gtest.h"
#include "GuardedEntry.h"
#include "sc_runner.h"
#include "sc_types.h"

namespace  {

void initEntryInTransition(sc_boolean guardVar, sc_boolean doneVar);
GuardedEntry* statechart;



//! The timers are managed by a timer service. */
static SctUnitRunner * runner;

class GuardedEntryTest : public ::testing::Test{
	protected:
	virtual void SetUp() {
		statechart = new GuardedEntry();
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

void initEntryInTransition(sc_boolean guardVar, sc_boolean doneVar){
	statechart->enter();
	
	EXPECT_TRUE(statechart->isStateActive(GuardedEntry::main_region_A));
	
	statechart->getDefaultSCI()->raise_e();
	
	runner->proceed_cycles(1);
	
	EXPECT_TRUE(statechart->isStateActive(GuardedEntry::main_region_B));
	
	statechart->getDefaultSCI()->set_guard(guardVar);
	
	statechart->getDefaultSCI()->set_done(doneVar);
	
	statechart->getDefaultSCI()->raise_e();
	
	runner->proceed_cycles(1);
	
	EXPECT_TRUE(statechart->isStateActive(GuardedEntry::main_region_A));
	
	
}

TEST_F(GuardedEntryTest, EntryNotTakenOnStatechartEnter) {
	
	EXPECT_TRUE(statechart->getDefaultSCI()->get_guard()== false);
	
	statechart->enter();
	
	EXPECT_TRUE(statechart->isStateActive(GuardedEntry::main_region_A));
	
	EXPECT_TRUE(statechart->getDefaultSCI()->get_done()== false);
	
	
}
TEST_F(GuardedEntryTest, EntryTakenOnStatechartEnter) {
	
	statechart->getDefaultSCI()->set_guard(true);
	
	statechart->enter();
	
	EXPECT_TRUE(statechart->isStateActive(GuardedEntry::main_region_A));
	
	EXPECT_TRUE(statechart->getDefaultSCI()->get_done()== true);
	
	
}
TEST_F(GuardedEntryTest, EntryTakenInTransition) {
	
	initEntryInTransition(true, false);
	
	EXPECT_TRUE(statechart->getDefaultSCI()->get_done());
	
	
}
TEST_F(GuardedEntryTest, EntryNotTakenInTransition) {
	
	initEntryInTransition(false, false);
	
	EXPECT_TRUE(!statechart->getDefaultSCI()->get_done());
	
	
}

}

