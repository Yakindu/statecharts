#include <string>
#include "gtest/gtest.h"
#include "Parenthesis.h"
#include "sc_runner.h"
#include "sc_types.h"

namespace  {

Parenthesis* statechart;



//! The timers are managed by a timer service. */
static SctUnitRunner * runner;

class ParenthesisTest : public ::testing::Test{
	protected:
	virtual void SetUp() {
		statechart = new Parenthesis();
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


TEST_F(ParenthesisTest, simple) {
	
	statechart->enter();
	
	EXPECT_TRUE(statechart->isStateActive(Parenthesis::mainRegion_A));
	
	EXPECT_TRUE(statechart->getDefaultSCI()->get_erg()== 8);
	
	
}

}

