
#include "gtest/gtest.h"
#include "NullCheck.h"

#include "sc_timer_service.h"

static NullCheck statechart;


class NullCheckTest : public ::testing::Test
{
public:
	/* All operations from the SCTUnit test class. */
	void SimpleNullCheckTest();
	void setTimer(NullCheck* statechart, const sc_eventid evid, const sc_integer time_ms, const sc_boolean periodic);
	void unsetTimer(NullCheck* handle, const sc_eventid evid);
protected:
	sc_unit_timer_service_t timer_service;
	virtual void SetUp();
};

static NullCheckTest * tc;


void NullCheckTest::SetUp()
{
	nullCheck_init(&statechart);
	sc_timer_service_init(
		&timer_service,
		0,
		(sc_run_cycle_fp) &nullCheck_runCycle,
		false,
		200,
		&statechart
	);
	
	
	tc = this;
}
void NullCheckTest::SimpleNullCheckTest()
{
	nullCheck_enter(&statechart);
	EXPECT_TRUE(nullCheck_isStateActive(&statechart, NullCheck_main_region_A));
	sc_timer_service_proceed_cycles(&timer_service, 1);
	EXPECT_TRUE(nullCheck_isStateActive(&statechart, NullCheck_main_region_B));
}

void NullCheckTest::setTimer(NullCheck* statechart, const sc_eventid evid, const sc_integer time_ms, const sc_boolean periodic){
	sc_timer_t timer;
	sc_timer_init(&timer, time_ms, periodic, evid);
	insert_timer(&(tc->timer_service), timer);
}

void NullCheckTest::unsetTimer(NullCheck* handle, const sc_eventid evid){
	delete_task(&(tc->timer_service), find_time_event(&timer_service, evid));
}

TEST_F(NullCheckTest, SimpleNullCheckTest) {
	SimpleNullCheckTest();
}


