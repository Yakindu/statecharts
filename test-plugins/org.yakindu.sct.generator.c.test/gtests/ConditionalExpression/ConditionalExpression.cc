
#include "gtest/gtest.h"
#include "ConditionalExpressions.h"

#include "sc_timer_service.h"

static ConditionalExpressions statechart;


class ConditionalExpression : public ::testing::Test
{
public:
	/* All operations from the SCTUnit test class. */
	void ConditionalExpressionTest();
	void setTimer(ConditionalExpressions* statechart, const sc_eventid evid, const sc_integer time_ms, const sc_boolean periodic);
	void unsetTimer(ConditionalExpressions* handle, const sc_eventid evid);
protected:
	sc_unit_timer_service_t timer_service;
	virtual void SetUp();
};

static ConditionalExpression * tc;


void ConditionalExpression::SetUp()
{
	conditionalExpressions_init(&statechart);
	sc_timer_service_init(
		&timer_service,
		0,
		(sc_run_cycle_fp) &conditionalExpressions_runCycle,
		false,
		200,
		&statechart
	);
	
	
	tc = this;
}
void ConditionalExpression::ConditionalExpressionTest()
{
	conditionalExpressions_enter(&statechart);
	EXPECT_TRUE(conditionalExpressions_isStateActive(&statechart, ConditionalExpressions_main_region_A));
	EXPECT_TRUE(conditionalExpressionsIface_get_condition(&statechart)== 1);
	conditionalExpressionsIface_raise_e(&statechart);
	sc_timer_service_proceed_cycles(&timer_service, 1);
	EXPECT_TRUE(conditionalExpressions_isStateActive(&statechart, ConditionalExpressions_main_region_B));
	EXPECT_TRUE(conditionalExpressionsIface_get_condition(&statechart)== 2);
}

void ConditionalExpression::setTimer(ConditionalExpressions* statechart, const sc_eventid evid, const sc_integer time_ms, const sc_boolean periodic){
	sc_timer_t timer;
	sc_timer_init(&timer, time_ms, periodic, evid);
	insert_timer(&(tc->timer_service), timer);
}

void ConditionalExpression::unsetTimer(ConditionalExpressions* handle, const sc_eventid evid){
	delete_task(&(tc->timer_service), find_time_event(&timer_service, evid));
}

TEST_F(ConditionalExpression, ConditionalExpressionTest) {
	ConditionalExpressionTest();
}


