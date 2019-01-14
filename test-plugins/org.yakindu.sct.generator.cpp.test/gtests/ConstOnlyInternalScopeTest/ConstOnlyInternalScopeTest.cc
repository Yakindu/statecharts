#include <string>
#include "gtest/gtest.h"
#include "ConstOnlyInternalScope.h"
#include "sc_runner.h"
#include "sc_types.h"

namespace  {

declarations::ConstOnlyInternalScope* statechart;



//! The timers are managed by a timer service. */
static SctUnitRunner * runner;

class ConstOnlyInternalScopeTest : public ::testing::Test{
	protected:
	virtual void SetUp() {
		statechart = new declarations::ConstOnlyInternalScope();
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


TEST_F(ConstOnlyInternalScopeTest, statechartEntry) {
	
	statechart->enter();
	
	EXPECT_TRUE(statechart->isStateActive(declarations::ConstOnlyInternalScope::ConstOnlyInternalScope_main_region_A));
	
	
}
TEST_F(ConstOnlyInternalScopeTest, stateTransition) {
	
	statechart->enter();
	
	statechart->getDefaultSCI()->raise_e( 1);
	
	runner->proceed_cycles(1);
	
	EXPECT_TRUE(statechart->isStateActive(declarations::ConstOnlyInternalScope::ConstOnlyInternalScope_main_region_B));
	
	
}

}

