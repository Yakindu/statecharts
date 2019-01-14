#include <string>
#include "gtest/gtest.h"
#include "ExitOnSelfTransition.h"
#include "sc_runner.h"
#include "sc_types.h"

namespace  {

ExitOnSelfTransition* statechart;



//! The timers are managed by a timer service. */
static SctUnitRunner * runner;

class ExitOnSelfTransitionTest : public ::testing::Test{
	protected:
	virtual void SetUp() {
		statechart = new ExitOnSelfTransition();
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


TEST_F(ExitOnSelfTransitionTest, ExitOnSelfTransitionTest) {
	
	statechart->enter();
	
	EXPECT_TRUE(statechart->isStateActive(ExitOnSelfTransition::main_region_A));
	
	EXPECT_TRUE(statechart->getDefaultSCI()->get_entryCount()== 1);
	
	EXPECT_TRUE(statechart->getDefaultSCI()->get_exitCount()== 0);
	
	statechart->getDefaultSCI()->raise_e();
	
	runner->proceed_cycles(1);
	
	EXPECT_TRUE(statechart->getDefaultSCI()->get_entryCount()== 2);
	
	EXPECT_TRUE(statechart->getDefaultSCI()->get_exitCount()== 1);
	
	statechart->getDefaultSCI()->raise_f();
	
	runner->proceed_cycles(1);
	
	EXPECT_TRUE(statechart->getDefaultSCI()->get_entryCount()== 2);
	
	EXPECT_TRUE(statechart->getDefaultSCI()->get_exitCount()== 2);
	
	
}

}

