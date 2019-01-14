
#include "gtest/gtest.h"
#include "SyncJoin.h"

#include "sc_timer_service.h"

static SyncJoin statechart;


class SyncJoinTest : public ::testing::Test
{
public:
	/* All operations from the SCTUnit test class. */
	void syncJoin_C2_Waits();
	void syncJoin_D2_Waits();
	void doubleEntryActionBug();
	void setTimer(SyncJoin* statechart, const sc_eventid evid, const sc_integer time_ms, const sc_boolean periodic);
	void unsetTimer(SyncJoin* handle, const sc_eventid evid);
protected:
	sc_unit_timer_service_t timer_service;
	virtual void SetUp();
};

static SyncJoinTest * tc;


void SyncJoinTest::SetUp()
{
	syncJoin_init(&statechart);
	sc_timer_service_init(
		&timer_service,
		0,
		(sc_run_cycle_fp) &syncJoin_runCycle,
		false,
		200,
		&statechart
	);
	
	
	tc = this;
}
void SyncJoinTest::syncJoin_C2_Waits()
{
	syncJoin_enter(&statechart);
	EXPECT_TRUE(syncJoin_isStateActive(&statechart, SyncJoin_main_region_B));
	EXPECT_TRUE(syncJoin_isStateActive(&statechart, SyncJoin_main_region_B_r1_C1));
	EXPECT_TRUE(syncJoin_isStateActive(&statechart, SyncJoin_main_region_B_r2_D1));
	syncJoinIface_raise_e(&statechart);
	sc_timer_service_proceed_cycles(&timer_service, 1);
	EXPECT_TRUE(syncJoin_isStateActive(&statechart, SyncJoin_main_region_B_r1_C2));
	EXPECT_TRUE(syncJoin_isStateActive(&statechart, SyncJoin_main_region_B_r2_D1));
	syncJoinIface_raise_jc(&statechart);
	sc_timer_service_proceed_cycles(&timer_service, 1);
	EXPECT_TRUE(syncJoin_isStateActive(&statechart, SyncJoin_main_region_B_r1_C2));
	EXPECT_TRUE(syncJoin_isStateActive(&statechart, SyncJoin_main_region_B_r2_D1));
	syncJoinIface_raise_jd(&statechart);
	sc_timer_service_proceed_cycles(&timer_service, 1);
	EXPECT_TRUE(syncJoin_isStateActive(&statechart, SyncJoin_main_region_B_r1_C2));
	EXPECT_TRUE(syncJoin_isStateActive(&statechart, SyncJoin_main_region_B_r2_D1));
	syncJoinIface_raise_jc(&statechart);
	syncJoinIface_raise_jd(&statechart);
	sc_timer_service_proceed_cycles(&timer_service, 1);
	EXPECT_TRUE(syncJoin_isStateActive(&statechart, SyncJoin_main_region_B_r1_C2));
	EXPECT_TRUE(syncJoin_isStateActive(&statechart, SyncJoin_main_region_B_r2_D1));
	syncJoinIface_raise_f(&statechart);
	sc_timer_service_proceed_cycles(&timer_service, 1);
	EXPECT_TRUE(syncJoin_isStateActive(&statechart, SyncJoin_main_region_B_r1_C2));
	EXPECT_TRUE(syncJoin_isStateActive(&statechart, SyncJoin_main_region_B_r2_D2));
	syncJoinIface_raise_jc(&statechart);
	sc_timer_service_proceed_cycles(&timer_service, 1);
	EXPECT_TRUE(syncJoin_isStateActive(&statechart, SyncJoin_main_region_B_r1_C2));
	EXPECT_TRUE(syncJoin_isStateActive(&statechart, SyncJoin_main_region_B_r2_D2));
	syncJoinIface_raise_jd(&statechart);
	sc_timer_service_proceed_cycles(&timer_service, 1);
	EXPECT_TRUE(syncJoin_isStateActive(&statechart, SyncJoin_main_region_B_r1_C2));
	EXPECT_TRUE(syncJoin_isStateActive(&statechart, SyncJoin_main_region_B_r2_D2));
	syncJoinIface_raise_jc(&statechart);
	syncJoinIface_raise_jd(&statechart);
	sc_timer_service_proceed_cycles(&timer_service, 1);
	EXPECT_TRUE(syncJoin_isStateActive(&statechart, SyncJoin_main_region_A));
}
void SyncJoinTest::syncJoin_D2_Waits()
{
	syncJoin_enter(&statechart);
	EXPECT_TRUE(syncJoin_isStateActive(&statechart, SyncJoin_main_region_B));
	EXPECT_TRUE(syncJoin_isStateActive(&statechart, SyncJoin_main_region_B_r1_C1));
	EXPECT_TRUE(syncJoin_isStateActive(&statechart, SyncJoin_main_region_B_r2_D1));
	syncJoinIface_raise_f(&statechart);
	sc_timer_service_proceed_cycles(&timer_service, 1);
	EXPECT_TRUE(syncJoin_isStateActive(&statechart, SyncJoin_main_region_B_r1_C1));
	EXPECT_TRUE(syncJoin_isStateActive(&statechart, SyncJoin_main_region_B_r2_D2));
	syncJoinIface_raise_jc(&statechart);
	sc_timer_service_proceed_cycles(&timer_service, 1);
	EXPECT_TRUE(syncJoin_isStateActive(&statechart, SyncJoin_main_region_B_r1_C1));
	EXPECT_TRUE(syncJoin_isStateActive(&statechart, SyncJoin_main_region_B_r2_D2));
	syncJoinIface_raise_jd(&statechart);
	sc_timer_service_proceed_cycles(&timer_service, 1);
	EXPECT_TRUE(syncJoin_isStateActive(&statechart, SyncJoin_main_region_B_r1_C1));
	EXPECT_TRUE(syncJoin_isStateActive(&statechart, SyncJoin_main_region_B_r2_D2));
	syncJoinIface_raise_jc(&statechart);
	syncJoinIface_raise_jd(&statechart);
	sc_timer_service_proceed_cycles(&timer_service, 1);
	EXPECT_TRUE(syncJoin_isStateActive(&statechart, SyncJoin_main_region_B_r1_C1));
	EXPECT_TRUE(syncJoin_isStateActive(&statechart, SyncJoin_main_region_B_r2_D2));
	syncJoinIface_raise_e(&statechart);
	sc_timer_service_proceed_cycles(&timer_service, 1);
	EXPECT_TRUE(syncJoin_isStateActive(&statechart, SyncJoin_main_region_B_r1_C2));
	EXPECT_TRUE(syncJoin_isStateActive(&statechart, SyncJoin_main_region_B_r2_D2));
}
void SyncJoinTest::doubleEntryActionBug()
{
	syncJoin_enter(&statechart);
	syncJoinIface_raise_e(&statechart);
	syncJoinIface_raise_f(&statechart);
	sc_timer_service_proceed_cycles(&timer_service, 1);
	syncJoinIface_raise_jc(&statechart);
	syncJoinIface_raise_jd(&statechart);
	sc_timer_service_proceed_cycles(&timer_service, 1);
	EXPECT_TRUE(syncJoin_isStateActive(&statechart, SyncJoin_main_region_A));
	EXPECT_TRUE(syncJoinIface_get_x(&statechart)== 1);
}

void SyncJoinTest::setTimer(SyncJoin* statechart, const sc_eventid evid, const sc_integer time_ms, const sc_boolean periodic){
	sc_timer_t timer;
	sc_timer_init(&timer, time_ms, periodic, evid);
	insert_timer(&(tc->timer_service), timer);
}

void SyncJoinTest::unsetTimer(SyncJoin* handle, const sc_eventid evid){
	delete_task(&(tc->timer_service), find_time_event(&timer_service, evid));
}

TEST_F(SyncJoinTest, syncJoin_C2_Waits) {
	syncJoin_C2_Waits();
}
TEST_F(SyncJoinTest, syncJoin_D2_Waits) {
	syncJoin_D2_Waits();
}
TEST_F(SyncJoinTest, doubleEntryActionBug) {
	doubleEntryActionBug();
}


