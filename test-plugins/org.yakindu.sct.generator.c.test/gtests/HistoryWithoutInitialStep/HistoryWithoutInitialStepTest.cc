
#include "gtest/gtest.h"
#include "HistoryWithoutInitialStep.h"

#include "sc_timer_service.h"

static HistoryWithoutInitialStep statechart;


class HistoryWithoutInitialStepTest : public ::testing::Test
{
public:
	/* All operations from the SCTUnit test class. */
	void enterThroughInitialEntry();
	void enterCThroughHistory();
	void enterDThroughHistory();
	void enterThroughHistoryWithoutInit();
	void init();
	void setTimer(HistoryWithoutInitialStep* statechart, const sc_eventid evid, const sc_integer time_ms, const sc_boolean periodic);
	void unsetTimer(HistoryWithoutInitialStep* handle, const sc_eventid evid);
protected:
	sc_unit_timer_service_t timer_service;
	virtual void SetUp();
};

static HistoryWithoutInitialStepTest * tc;

void init();

void HistoryWithoutInitialStepTest::SetUp()
{
	historyWithoutInitialStep_init(&statechart);
	sc_timer_service_init(
		&timer_service,
		0,
		(sc_run_cycle_fp) &historyWithoutInitialStep_runCycle,
		false,
		200,
		&statechart
	);
	
	
	tc = this;
}
void HistoryWithoutInitialStepTest::enterThroughInitialEntry()
{
	tc->init();
	EXPECT_TRUE(historyWithoutInitialStep_isStateActive(&statechart, HistoryWithoutInitialStep_main_region_B_r1_C));
	historyWithoutInitialStepIface_raise_next(&statechart);
	sc_timer_service_proceed_cycles(&timer_service, 1);
	EXPECT_TRUE(historyWithoutInitialStep_isStateActive(&statechart, HistoryWithoutInitialStep_main_region_B_r1_D));
}
void HistoryWithoutInitialStepTest::enterCThroughHistory()
{
	tc->init();
	EXPECT_TRUE(historyWithoutInitialStep_isStateActive(&statechart, HistoryWithoutInitialStep_main_region_B_r1_C));
	historyWithoutInitialStepIface_raise_toA(&statechart);
	sc_timer_service_proceed_cycles(&timer_service, 1);
	EXPECT_TRUE(historyWithoutInitialStep_isStateActive(&statechart, HistoryWithoutInitialStep_main_region_A));
	historyWithoutInitialStepIface_raise_toHistory(&statechart);
	sc_timer_service_proceed_cycles(&timer_service, 1);
	EXPECT_TRUE(historyWithoutInitialStep_isStateActive(&statechart, HistoryWithoutInitialStep_main_region_B_r1_C));
}
void HistoryWithoutInitialStepTest::enterDThroughHistory()
{
	tc->init();
	historyWithoutInitialStepIface_raise_next(&statechart);
	sc_timer_service_proceed_cycles(&timer_service, 1);
	EXPECT_TRUE(historyWithoutInitialStep_isStateActive(&statechart, HistoryWithoutInitialStep_main_region_B_r1_D));
	historyWithoutInitialStepIface_raise_toA(&statechart);
	sc_timer_service_proceed_cycles(&timer_service, 1);
	EXPECT_TRUE(historyWithoutInitialStep_isStateActive(&statechart, HistoryWithoutInitialStep_main_region_A));
	historyWithoutInitialStepIface_raise_toHistory(&statechart);
	sc_timer_service_proceed_cycles(&timer_service, 1);
	EXPECT_TRUE(historyWithoutInitialStep_isStateActive(&statechart, HistoryWithoutInitialStep_main_region_B_r1_D));
}
void HistoryWithoutInitialStepTest::enterThroughHistoryWithoutInit()
{
	historyWithoutInitialStep_enter(&statechart);
	EXPECT_TRUE(historyWithoutInitialStep_isStateActive(&statechart, HistoryWithoutInitialStep_main_region_A));
	historyWithoutInitialStepIface_raise_toHistory(&statechart);
	sc_timer_service_proceed_cycles(&timer_service, 1);
	EXPECT_TRUE(historyWithoutInitialStep_isStateActive(&statechart, HistoryWithoutInitialStep_main_region_B_r1_C));
}
void HistoryWithoutInitialStepTest::init()
{
	historyWithoutInitialStep_enter(&statechart);
	EXPECT_TRUE(historyWithoutInitialStep_isStateActive(&statechart, HistoryWithoutInitialStep_main_region_A));
	historyWithoutInitialStepIface_raise_toB(&statechart);
	sc_timer_service_proceed_cycles(&timer_service, 1);
}

void HistoryWithoutInitialStepTest::setTimer(HistoryWithoutInitialStep* statechart, const sc_eventid evid, const sc_integer time_ms, const sc_boolean periodic){
	sc_timer_t timer;
	sc_timer_init(&timer, time_ms, periodic, evid);
	insert_timer(&(tc->timer_service), timer);
}

void HistoryWithoutInitialStepTest::unsetTimer(HistoryWithoutInitialStep* handle, const sc_eventid evid){
	delete_task(&(tc->timer_service), find_time_event(&timer_service, evid));
}

TEST_F(HistoryWithoutInitialStepTest, enterThroughInitialEntry) {
	enterThroughInitialEntry();
}
TEST_F(HistoryWithoutInitialStepTest, enterCThroughHistory) {
	enterCThroughHistory();
}
TEST_F(HistoryWithoutInitialStepTest, enterDThroughHistory) {
	enterDThroughHistory();
}
TEST_F(HistoryWithoutInitialStepTest, enterThroughHistoryWithoutInit) {
	enterThroughHistoryWithoutInit();
}


