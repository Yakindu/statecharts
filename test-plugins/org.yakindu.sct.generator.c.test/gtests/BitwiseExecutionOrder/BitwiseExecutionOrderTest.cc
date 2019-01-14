
#include "gtest/gtest.h"
#include "BitwiseExecutionOrder.h"

#include "sc_timer_service.h"

static BitwiseExecutionOrder statechart;


class BitwiseExecutionOrderTest : public ::testing::Test
{
public:
	/* All operations from the SCTUnit test class. */
	void test();
	void setTimer(BitwiseExecutionOrder* statechart, const sc_eventid evid, const sc_integer time_ms, const sc_boolean periodic);
	void unsetTimer(BitwiseExecutionOrder* handle, const sc_eventid evid);
protected:
	sc_unit_timer_service_t timer_service;
	virtual void SetUp();
};

static BitwiseExecutionOrderTest * tc;


void BitwiseExecutionOrderTest::SetUp()
{
	bitwiseExecutionOrder_init(&statechart);
	sc_timer_service_init(
		&timer_service,
		0,
		(sc_run_cycle_fp) &bitwiseExecutionOrder_runCycle,
		false,
		200,
		&statechart
	);
	
	
	tc = this;
}
void BitwiseExecutionOrderTest::test()
{
	bitwiseExecutionOrder_enter(&statechart);
	sc_timer_service_proceed_cycles(&timer_service, 1);
	EXPECT_TRUE(bitwiseExecutionOrder_isFinal(&statechart));
	bitwiseExecutionOrder_exit(&statechart);
}

void BitwiseExecutionOrderTest::setTimer(BitwiseExecutionOrder* statechart, const sc_eventid evid, const sc_integer time_ms, const sc_boolean periodic){
	sc_timer_t timer;
	sc_timer_init(&timer, time_ms, periodic, evid);
	insert_timer(&(tc->timer_service), timer);
}

void BitwiseExecutionOrderTest::unsetTimer(BitwiseExecutionOrder* handle, const sc_eventid evid){
	delete_task(&(tc->timer_service), find_time_event(&timer_service, evid));
}

TEST_F(BitwiseExecutionOrderTest, test) {
	test();
}


