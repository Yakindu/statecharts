
#include "gtest/gtest.h"
#include "Guard.h"

#include "sc_timer_service.h"

static Guard statechart;


class GuardTest : public ::testing::Test
{
public:
	/* All operations from the SCTUnit test class. */
	void guardTest();
	void setTimer(Guard* statechart, const sc_eventid evid, const sc_integer time_ms, const sc_boolean periodic);
	void unsetTimer(Guard* handle, const sc_eventid evid);
protected:
	sc_unit_timer_service_t timer_service;
	virtual void SetUp();
};

static GuardTest * tc;


void GuardTest::SetUp()
{
	guard_init(&statechart);
	sc_timer_service_init(
		&timer_service,
		0,
		(sc_run_cycle_fp) &guard_runCycle,
		false,
		200,
		&statechart
	);
	
	
	tc = this;
}
void GuardTest::guardTest()
{
	guard_enter(&statechart);
	EXPECT_TRUE(guard_isStateActive(&statechart, Guard_main_region_A));
	guardIface_raise_event1(&statechart);
	sc_timer_service_proceed_cycles(&timer_service, 1);
	EXPECT_TRUE(guard_isStateActive(&statechart, Guard_main_region_A));
	guardIface_raise_event2(&statechart);
	sc_timer_service_proceed_cycles(&timer_service, 1);
	EXPECT_TRUE(guard_isStateActive(&statechart, Guard_main_region_B));
	guardIface_raise_return(&statechart);
	sc_timer_service_proceed_cycles(&timer_service, 1);
	EXPECT_TRUE(guard_isStateActive(&statechart, Guard_main_region_A));
	guardIface_raise_event1(&statechart);
	sc_timer_service_proceed_cycles(&timer_service, 1);
	EXPECT_TRUE(guard_isStateActive(&statechart, Guard_main_region_B));
	guardIface_raise_return(&statechart);
	sc_timer_service_proceed_cycles(&timer_service, 1);
	EXPECT_TRUE(guard_isStateActive(&statechart, Guard_main_region_A));
}

void GuardTest::setTimer(Guard* statechart, const sc_eventid evid, const sc_integer time_ms, const sc_boolean periodic){
	sc_timer_t timer;
	sc_timer_init(&timer, time_ms, periodic, evid);
	insert_timer(&(tc->timer_service), timer);
}

void GuardTest::unsetTimer(Guard* handle, const sc_eventid evid){
	delete_task(&(tc->timer_service), find_time_event(&timer_service, evid));
}

TEST_F(GuardTest, guardTest) {
	guardTest();
}


