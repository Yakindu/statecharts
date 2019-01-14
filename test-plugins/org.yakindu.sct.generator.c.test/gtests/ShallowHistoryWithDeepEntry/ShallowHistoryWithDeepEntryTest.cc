
#include "gtest/gtest.h"
#include "ShallowHistoryWithDeepEntry.h"

#include "sc_timer_service.h"

static ShallowHistoryWithDeepEntry statechart;


class ShallowHistoryWithDeepEntryTest : public ::testing::Test
{
public:
	/* All operations from the SCTUnit test class. */
	void noDeepEntryWithinHistory();
	void deepEntryWithinHistory();
	void directDeepEntryIntoHistory();
	void setTimer(ShallowHistoryWithDeepEntry* statechart, const sc_eventid evid, const sc_integer time_ms, const sc_boolean periodic);
	void unsetTimer(ShallowHistoryWithDeepEntry* handle, const sc_eventid evid);
protected:
	sc_unit_timer_service_t timer_service;
	virtual void SetUp();
};

static ShallowHistoryWithDeepEntryTest * tc;


void ShallowHistoryWithDeepEntryTest::SetUp()
{
	shallowHistoryWithDeepEntry_init(&statechart);
	sc_timer_service_init(
		&timer_service,
		0,
		(sc_run_cycle_fp) &shallowHistoryWithDeepEntry_runCycle,
		false,
		200,
		&statechart
	);
	
	
	tc = this;
}
void ShallowHistoryWithDeepEntryTest::noDeepEntryWithinHistory()
{
	shallowHistoryWithDeepEntry_enter(&statechart);
	EXPECT_TRUE(shallowHistoryWithDeepEntry_isStateActive(&statechart, ShallowHistoryWithDeepEntry_main_region_Y));
	shallowHistoryWithDeepEntryIface_raise_toZ(&statechart);
	sc_timer_service_proceed_cycles(&timer_service, 1);
	EXPECT_TRUE(shallowHistoryWithDeepEntry_isStateActive(&statechart, ShallowHistoryWithDeepEntry_main_region_Z__region0_A));
	shallowHistoryWithDeepEntryIface_raise_toY(&statechart);
	sc_timer_service_proceed_cycles(&timer_service, 1);
	EXPECT_TRUE(shallowHistoryWithDeepEntry_isStateActive(&statechart, ShallowHistoryWithDeepEntry_main_region_Y));
	shallowHistoryWithDeepEntryIface_raise_toZ(&statechart);
	sc_timer_service_proceed_cycles(&timer_service, 1);
	EXPECT_TRUE(shallowHistoryWithDeepEntry_isStateActive(&statechart, ShallowHistoryWithDeepEntry_main_region_Z__region0_A));
}
void ShallowHistoryWithDeepEntryTest::deepEntryWithinHistory()
{
	shallowHistoryWithDeepEntry_enter(&statechart);
	EXPECT_TRUE(shallowHistoryWithDeepEntry_isStateActive(&statechart, ShallowHistoryWithDeepEntry_main_region_Y));
	shallowHistoryWithDeepEntryIface_raise_toZ(&statechart);
	sc_timer_service_proceed_cycles(&timer_service, 1);
	EXPECT_TRUE(shallowHistoryWithDeepEntry_isStateActive(&statechart, ShallowHistoryWithDeepEntry_main_region_Z__region0_A));
	shallowHistoryWithDeepEntryIface_raise_toC(&statechart);
	sc_timer_service_proceed_cycles(&timer_service, 1);
	EXPECT_TRUE(shallowHistoryWithDeepEntry_isStateActive(&statechart, ShallowHistoryWithDeepEntry_main_region_Z__region0_B__region0_C));
	EXPECT_TRUE(shallowHistoryWithDeepEntry_isStateActive(&statechart, ShallowHistoryWithDeepEntry_main_region_Z__region0_B));
	shallowHistoryWithDeepEntryIface_raise_toY(&statechart);
	sc_timer_service_proceed_cycles(&timer_service, 1);
	EXPECT_TRUE(shallowHistoryWithDeepEntry_isStateActive(&statechart, ShallowHistoryWithDeepEntry_main_region_Y));
	shallowHistoryWithDeepEntryIface_raise_toZ(&statechart);
	sc_timer_service_proceed_cycles(&timer_service, 1);
	EXPECT_TRUE(shallowHistoryWithDeepEntry_isStateActive(&statechart, ShallowHistoryWithDeepEntry_main_region_Z__region0_B__region0_C));
	EXPECT_TRUE(shallowHistoryWithDeepEntry_isStateActive(&statechart, ShallowHistoryWithDeepEntry_main_region_Z__region0_B));
}
void ShallowHistoryWithDeepEntryTest::directDeepEntryIntoHistory()
{
	shallowHistoryWithDeepEntry_enter(&statechart);
	EXPECT_TRUE(shallowHistoryWithDeepEntry_isStateActive(&statechart, ShallowHistoryWithDeepEntry_main_region_Y));
	shallowHistoryWithDeepEntryIface_raise_toC(&statechart);
	sc_timer_service_proceed_cycles(&timer_service, 1);
	EXPECT_TRUE(shallowHistoryWithDeepEntry_isStateActive(&statechart, ShallowHistoryWithDeepEntry_main_region_Z__region0_B__region0_C));
	EXPECT_TRUE(shallowHistoryWithDeepEntry_isStateActive(&statechart, ShallowHistoryWithDeepEntry_main_region_Z__region0_B));
	shallowHistoryWithDeepEntryIface_raise_toY(&statechart);
	sc_timer_service_proceed_cycles(&timer_service, 1);
	EXPECT_TRUE(shallowHistoryWithDeepEntry_isStateActive(&statechart, ShallowHistoryWithDeepEntry_main_region_Y));
	shallowHistoryWithDeepEntryIface_raise_toZ(&statechart);
	sc_timer_service_proceed_cycles(&timer_service, 1);
	EXPECT_TRUE(shallowHistoryWithDeepEntry_isStateActive(&statechart, ShallowHistoryWithDeepEntry_main_region_Z__region0_B__region0_C));
	EXPECT_TRUE(shallowHistoryWithDeepEntry_isStateActive(&statechart, ShallowHistoryWithDeepEntry_main_region_Z__region0_B));
}

void ShallowHistoryWithDeepEntryTest::setTimer(ShallowHistoryWithDeepEntry* statechart, const sc_eventid evid, const sc_integer time_ms, const sc_boolean periodic){
	sc_timer_t timer;
	sc_timer_init(&timer, time_ms, periodic, evid);
	insert_timer(&(tc->timer_service), timer);
}

void ShallowHistoryWithDeepEntryTest::unsetTimer(ShallowHistoryWithDeepEntry* handle, const sc_eventid evid){
	delete_task(&(tc->timer_service), find_time_event(&timer_service, evid));
}

TEST_F(ShallowHistoryWithDeepEntryTest, noDeepEntryWithinHistory) {
	noDeepEntryWithinHistory();
}
TEST_F(ShallowHistoryWithDeepEntryTest, deepEntryWithinHistory) {
	deepEntryWithinHistory();
}
TEST_F(ShallowHistoryWithDeepEntryTest, directDeepEntryIntoHistory) {
	directDeepEntryIntoHistory();
}


