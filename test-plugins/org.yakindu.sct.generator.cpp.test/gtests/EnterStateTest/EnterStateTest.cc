#include <string>
#include "gtest/gtest.h"
#include "EnterState.h"
#include "sc_runner.h"
#include "sc_types.h"

namespace  {

EnterState* statechart;



//! The timers are managed by a timer service. */
static SctUnitRunner * runner;

class EnterStateTest : public ::testing::Test{
	protected:
	virtual void SetUp() {
		statechart = new EnterState();
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


TEST_F(EnterStateTest, defaultEntry) {
	
	statechart->enter();
	
	EXPECT_TRUE(statechart->isStateActive(EnterState::r_A));
	
	statechart->getDefaultSCI()->raise_e();
	
	runner->proceed_cycles(1);
	
	EXPECT_TRUE(statechart->isStateActive(EnterState::r_B_r_E));
	
	
}
TEST_F(EnterStateTest, namedEntryThroughNamedTransition) {
	
	statechart->enter();
	
	EXPECT_TRUE(statechart->isStateActive(EnterState::r_A));
	
	statechart->getDefaultSCI()->raise_f();
	
	runner->proceed_cycles(1);
	
	EXPECT_TRUE(statechart->isStateActive(EnterState::r_B_r_F));
	
	
}
TEST_F(EnterStateTest, namedEntryThroughDefaultTransition) {
	
	statechart->enter();
	
	EXPECT_TRUE(statechart->isStateActive(EnterState::r_A));
	
	statechart->getDefaultSCI()->raise_g();
	
	runner->proceed_cycles(1);
	
	EXPECT_TRUE(statechart->isStateActive(EnterState::r_B_r_E));
	
	
}

}

