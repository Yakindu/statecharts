/* Generated by YAKINDU Statechart Tools code generator. */

#include "gtest/gtest.h"
#include "ConstOnlyDefaultScope.h"

#include "sc_timer_service.h"


static ConstOnlyDefaultScope statechart;


//! The timers are managed by a timer service. */
static sc_unit_timer_service_t timer_service;
			
class ConstOnlyDefaultScopeTest : public ::testing::Test{
	protected:
	virtual void SetUp() {
		constOnlyDefaultScope_init(&statechart);
		sc_timer_service_init(
			&timer_service,
			0,
			(sc_run_cycle_fp) &constOnlyDefaultScope_runCycle,
			false,
			200,
			&statechart
		);
	}
};


TEST_F(ConstOnlyDefaultScopeTest, statechartEntry) {
	
	constOnlyDefaultScope_enter(&statechart);
	EXPECT_TRUE(constOnlyDefaultScope_isStateActive(&statechart, ConstOnlyDefaultScope_ConstOnlyDefaultScope_main_region_A));
}
TEST_F(ConstOnlyDefaultScopeTest, stateTransition) {
	
	constOnlyDefaultScope_enter(&statechart);
	constOnlyDefaultScopeIfaceA_raise_e(&statechart, 1l);
	sc_timer_service_proceed_cycles(&timer_service, 1);
	EXPECT_TRUE(constOnlyDefaultScope_isStateActive(&statechart, ConstOnlyDefaultScope_ConstOnlyDefaultScope_main_region_B));
}


