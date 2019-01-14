
#include "gtest/gtest.h"
#include "EnterState.h"

#include "sc_timer_service.h"

static EnterState statechart;


class EnterStateTest : public ::testing::Test
{
public:
	/* All operations from the SCTUnit test class. */
	void defaultEntry();
	void namedEntryThroughNamedTransition();
	void namedEntryThroughDefaultTransition();
	void setTimer(EnterState* statechart, const sc_eventid evid, const sc_integer time_ms, const sc_boolean periodic);
	void unsetTimer(EnterState* handle, const sc_eventid evid);
protected:
	sc_unit_timer_service_t timer_service;
	virtual void SetUp();
};

static EnterStateTest * tc;


void EnterStateTest::SetUp()
{
	enterState_init(&statechart);
	sc_timer_service_init(
		&timer_service,
		0,
		(sc_run_cycle_fp) &enterState_runCycle,
		false,
		200,
		&statechart
	);
	
	
	tc = this;
}
void EnterStateTest::defaultEntry()
{
	enterState_enter(&statechart);
	EXPECT_TRUE(enterState_isStateActive(&statechart, EnterState_r_A));
	enterStateIface_raise_e(&statechart);
	sc_timer_service_proceed_cycles(&timer_service, 1);
	EXPECT_TRUE(enterState_isStateActive(&statechart, EnterState_r_B_r_E));
}
void EnterStateTest::namedEntryThroughNamedTransition()
{
	enterState_enter(&statechart);
	EXPECT_TRUE(enterState_isStateActive(&statechart, EnterState_r_A));
	enterStateIface_raise_f(&statechart);
	sc_timer_service_proceed_cycles(&timer_service, 1);
	EXPECT_TRUE(enterState_isStateActive(&statechart, EnterState_r_B_r_F));
}
void EnterStateTest::namedEntryThroughDefaultTransition()
{
	enterState_enter(&statechart);
	EXPECT_TRUE(enterState_isStateActive(&statechart, EnterState_r_A));
	enterStateIface_raise_g(&statechart);
	sc_timer_service_proceed_cycles(&timer_service, 1);
	EXPECT_TRUE(enterState_isStateActive(&statechart, EnterState_r_B_r_E));
}

void EnterStateTest::setTimer(EnterState* statechart, const sc_eventid evid, const sc_integer time_ms, const sc_boolean periodic){
	sc_timer_t timer;
	sc_timer_init(&timer, time_ms, periodic, evid);
	insert_timer(&(tc->timer_service), timer);
}

void EnterStateTest::unsetTimer(EnterState* handle, const sc_eventid evid){
	delete_task(&(tc->timer_service), find_time_event(&timer_service, evid));
}

TEST_F(EnterStateTest, defaultEntry) {
	defaultEntry();
}
TEST_F(EnterStateTest, namedEntryThroughNamedTransition) {
	namedEntryThroughNamedTransition();
}
TEST_F(EnterStateTest, namedEntryThroughDefaultTransition) {
	namedEntryThroughDefaultTransition();
}


