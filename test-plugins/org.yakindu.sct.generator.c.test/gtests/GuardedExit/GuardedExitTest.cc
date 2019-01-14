
#include "gtest/gtest.h"
#include "GuardedExit.h"

#include "sc_timer_service.h"

static GuardedExit statechart;


class GuardedExitTest : public ::testing::Test
{
public:
	/* All operations from the SCTUnit test class. */
	void ExitTaken();
	void ExitNotTaken();
	void checkDone(sc_boolean shouldBeDone);
	void setTimer(GuardedExit* statechart, const sc_eventid evid, const sc_integer time_ms, const sc_boolean periodic);
	void unsetTimer(GuardedExit* handle, const sc_eventid evid);
protected:
	sc_unit_timer_service_t timer_service;
	virtual void SetUp();
};

static GuardedExitTest * tc;

void checkDone(sc_boolean shouldBeDone);

void GuardedExitTest::SetUp()
{
	guardedExit_init(&statechart);
	sc_timer_service_init(
		&timer_service,
		0,
		(sc_run_cycle_fp) &guardedExit_runCycle,
		false,
		200,
		&statechart
	);
	
	
	tc = this;
}
void GuardedExitTest::ExitTaken()
{
	guardedExit_enter(&statechart);
	EXPECT_TRUE(guardedExit_isStateActive(&statechart, GuardedExit_main_region_A));
	EXPECT_TRUE(!guardedExitIface_get_guard(&statechart));
	tc->checkDone(false);
}
void GuardedExitTest::ExitNotTaken()
{
	guardedExit_enter(&statechart);
	EXPECT_TRUE(guardedExit_isStateActive(&statechart, GuardedExit_main_region_A));
	guardedExitIface_set_guard(&statechart,true);
	tc->checkDone(true);
}
void GuardedExitTest::checkDone(sc_boolean shouldBeDone)
{
	guardedExitIface_raise_e(&statechart);
	sc_timer_service_proceed_cycles(&timer_service, 1);
	EXPECT_TRUE(guardedExit_isStateActive(&statechart, GuardedExit_main_region_B));
	EXPECT_TRUE(shouldBeDone ? guardedExitIface_get_done(&statechart) : !guardedExitIface_get_done(&statechart));
}

void GuardedExitTest::setTimer(GuardedExit* statechart, const sc_eventid evid, const sc_integer time_ms, const sc_boolean periodic){
	sc_timer_t timer;
	sc_timer_init(&timer, time_ms, periodic, evid);
	insert_timer(&(tc->timer_service), timer);
}

void GuardedExitTest::unsetTimer(GuardedExit* handle, const sc_eventid evid){
	delete_task(&(tc->timer_service), find_time_event(&timer_service, evid));
}

TEST_F(GuardedExitTest, ExitTaken) {
	ExitTaken();
}
TEST_F(GuardedExitTest, ExitNotTaken) {
	ExitNotTaken();
}


