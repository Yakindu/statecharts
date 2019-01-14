
#include "gtest/gtest.h"
#include "ExitState.h"

#include "sc_timer_service.h"

static ExitState statechart;


class ExitStateTest : public ::testing::Test
{
public:
	/* All operations from the SCTUnit test class. */
	void defaultExit();
	void namedExitThroughNamedTransition();
	void namedExitThroughDefaultTransition();
	void remainInA();
	void setTimer(ExitState* statechart, const sc_eventid evid, const sc_integer time_ms, const sc_boolean periodic);
	void unsetTimer(ExitState* handle, const sc_eventid evid);
protected:
	sc_unit_timer_service_t timer_service;
	virtual void SetUp();
};

static ExitStateTest * tc;


void ExitStateTest::SetUp()
{
	exitState_init(&statechart);
	sc_timer_service_init(
		&timer_service,
		0,
		(sc_run_cycle_fp) &exitState_runCycle,
		false,
		200,
		&statechart
	);
	
	
	tc = this;
}
void ExitStateTest::defaultExit()
{
	exitState_enter(&statechart);
	EXPECT_TRUE(exitState_isStateActive(&statechart, ExitState_r_A));
	exitStateIface_raise_e(&statechart);
	sc_timer_service_proceed_cycles(&timer_service, 1);
	EXPECT_TRUE(exitState_isStateActive(&statechart, ExitState_r_E));
}
void ExitStateTest::namedExitThroughNamedTransition()
{
	exitState_enter(&statechart);
	EXPECT_TRUE(exitState_isStateActive(&statechart, ExitState_r_A));
	exitStateIface_raise_f(&statechart);
	sc_timer_service_proceed_cycles(&timer_service, 1);
	EXPECT_TRUE(exitState_isStateActive(&statechart, ExitState_r_F));
}
void ExitStateTest::namedExitThroughDefaultTransition()
{
	exitState_enter(&statechart);
	EXPECT_TRUE(exitState_isStateActive(&statechart, ExitState_r_A));
	exitStateIface_raise_g(&statechart);
	sc_timer_service_proceed_cycles(&timer_service, 1);
	EXPECT_TRUE(exitState_isStateActive(&statechart, ExitState_r_E));
}
void ExitStateTest::remainInA()
{
	exitState_enter(&statechart);
	EXPECT_TRUE(exitState_isStateActive(&statechart, ExitState_r_A));
	sc_timer_service_proceed_cycles(&timer_service, 1);
	EXPECT_TRUE(exitState_isStateActive(&statechart, ExitState_r_A));
}

void ExitStateTest::setTimer(ExitState* statechart, const sc_eventid evid, const sc_integer time_ms, const sc_boolean periodic){
	sc_timer_t timer;
	sc_timer_init(&timer, time_ms, periodic, evid);
	insert_timer(&(tc->timer_service), timer);
}

void ExitStateTest::unsetTimer(ExitState* handle, const sc_eventid evid){
	delete_task(&(tc->timer_service), find_time_event(&timer_service, evid));
}

TEST_F(ExitStateTest, defaultExit) {
	defaultExit();
}
TEST_F(ExitStateTest, namedExitThroughNamedTransition) {
	namedExitThroughNamedTransition();
}
TEST_F(ExitStateTest, namedExitThroughDefaultTransition) {
	namedExitThroughDefaultTransition();
}
TEST_F(ExitStateTest, remainInA) {
	remainInA();
}


