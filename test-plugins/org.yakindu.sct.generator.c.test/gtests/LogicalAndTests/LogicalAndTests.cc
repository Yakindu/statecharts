
#include "gtest/gtest.h"
#include "LogicalAnd.h"

#include "sc_timer_service.h"

static LogicalAnd statechart;


class LogicalAndTests : public ::testing::Test
{
public:
	/* All operations from the SCTUnit test class. */
	void operandEvaluationOrder();
	void setTimer(LogicalAnd* statechart, const sc_eventid evid, const sc_integer time_ms, const sc_boolean periodic);
	void unsetTimer(LogicalAnd* handle, const sc_eventid evid);
protected:
	sc_unit_timer_service_t timer_service;
	virtual void SetUp();
};

static LogicalAndTests * tc;


void LogicalAndTests::SetUp()
{
	logicalAnd_init(&statechart);
	sc_timer_service_init(
		&timer_service,
		0,
		(sc_run_cycle_fp) &logicalAnd_runCycle,
		false,
		200,
		&statechart
	);
	
	
	tc = this;
}
void LogicalAndTests::operandEvaluationOrder()
{
	logicalAnd_enter(&statechart);
	sc_timer_service_proceed_cycles(&timer_service, 1);
	EXPECT_TRUE(logicalAndIface_get_x(&statechart)== 4 && logicalAndIface_get_b(&statechart)) << "logical and expression was executed in wrong order..." ;
}

void LogicalAndTests::setTimer(LogicalAnd* statechart, const sc_eventid evid, const sc_integer time_ms, const sc_boolean periodic){
	sc_timer_t timer;
	sc_timer_init(&timer, time_ms, periodic, evid);
	insert_timer(&(tc->timer_service), timer);
}

void LogicalAndTests::unsetTimer(LogicalAnd* handle, const sc_eventid evid){
	delete_task(&(tc->timer_service), find_time_event(&timer_service, evid));
}

TEST_F(LogicalAndTests, operandEvaluationOrder) {
	operandEvaluationOrder();
}


