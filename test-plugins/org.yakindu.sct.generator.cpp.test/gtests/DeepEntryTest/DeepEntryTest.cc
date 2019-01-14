#include <string>
#include "gtest/gtest.h"
#include "DeepEntry.h"
#include "sc_runner.h"
#include "sc_types.h"

namespace  {

DeepEntry* statechart;



//! The timers are managed by a timer service. */
static SctUnitRunner * runner;

class DeepEntryTest : public ::testing::Test{
	protected:
	virtual void SetUp() {
		statechart = new DeepEntry();
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


TEST_F(DeepEntryTest, enterToSubstate) {
	
	statechart->enter();
	
	sc_integer vergleich = 4;
	
	EXPECT_TRUE(vergleich== 4);
	
	statechart->exit();
	
	
}

}

