
#include "gtest/gtest.h"
#include "Choice.h"

#include "sc_timer_service.h"

static Choice statechart;


class ChoiceTest : public ::testing::Test
{
public:
	/* All operations from the SCTUnit test class. */
	void elseChoiceUsingNonDefaultTransition();
	void elseChoiceUsingDefaultTransition();
	void defaultChoiceUsingNonDefaultTransition();
	void defaultChoiceUsingDefaultTransition();
	void uncheckedChoiceUsingNonDefaultTransition();
	void uncheckedChoiceUsingDefaultTransition();
	void alwaysTrueTransitionInChoice();
	void initForEventE(sc_boolean valueForC);
	void initForEventF(sc_boolean valueForC);
	void initForEventG(sc_boolean valueForC);
	void initForEventH(sc_boolean valueForC);
	void setTimer(Choice* statechart, const sc_eventid evid, const sc_integer time_ms, const sc_boolean periodic);
	void unsetTimer(Choice* handle, const sc_eventid evid);
protected:
	sc_unit_timer_service_t timer_service;
	virtual void SetUp();
};

static ChoiceTest * tc;

void initForEventE(sc_boolean valueForC);
void initForEventF(sc_boolean valueForC);
void initForEventG(sc_boolean valueForC);
void initForEventH(sc_boolean valueForC);

void ChoiceTest::SetUp()
{
	choice_init(&statechart);
	sc_timer_service_init(
		&timer_service,
		0,
		(sc_run_cycle_fp) &choice_runCycle,
		false,
		200,
		&statechart
	);
	
	
	tc = this;
}
void ChoiceTest::elseChoiceUsingNonDefaultTransition()
{
	tc->initForEventE(true);
	EXPECT_TRUE(choice_isStateActive(&statechart, Choice_main_region_C));
}
void ChoiceTest::elseChoiceUsingDefaultTransition()
{
	tc->initForEventE(false);
	EXPECT_TRUE(choice_isStateActive(&statechart, Choice_main_region_B));
}
void ChoiceTest::defaultChoiceUsingNonDefaultTransition()
{
	tc->initForEventG(true);
	EXPECT_TRUE(choice_isStateActive(&statechart, Choice_main_region_C));
}
void ChoiceTest::defaultChoiceUsingDefaultTransition()
{
	tc->initForEventG(false);
	EXPECT_TRUE(choice_isStateActive(&statechart, Choice_main_region_B));
}
void ChoiceTest::uncheckedChoiceUsingNonDefaultTransition()
{
	tc->initForEventF(true);
	EXPECT_TRUE(choice_isStateActive(&statechart, Choice_main_region_C));
}
void ChoiceTest::uncheckedChoiceUsingDefaultTransition()
{
	tc->initForEventF(false);
	EXPECT_TRUE(choice_isStateActive(&statechart, Choice_main_region_B));
}
void ChoiceTest::alwaysTrueTransitionInChoice()
{
	tc->initForEventH(true);
	EXPECT_TRUE(choice_isStateActive(&statechart, Choice_main_region_C));
}
void ChoiceTest::initForEventE(sc_boolean valueForC)
{
	choice_enter(&statechart);
	EXPECT_TRUE(choice_isStateActive(&statechart, Choice_main_region_A));
	choiceIface_set_c(&statechart,valueForC);
	choiceIface_raise_e(&statechart);
	sc_timer_service_proceed_cycles(&timer_service, 1);
}
void ChoiceTest::initForEventF(sc_boolean valueForC)
{
	choice_enter(&statechart);
	EXPECT_TRUE(choice_isStateActive(&statechart, Choice_main_region_A));
	choiceIface_set_c(&statechart,valueForC);
	choiceIface_raise_f(&statechart);
	sc_timer_service_proceed_cycles(&timer_service, 1);
}
void ChoiceTest::initForEventG(sc_boolean valueForC)
{
	choice_enter(&statechart);
	EXPECT_TRUE(choice_isStateActive(&statechart, Choice_main_region_A));
	choiceIface_set_c(&statechart,valueForC);
	choiceIface_raise_g(&statechart);
	sc_timer_service_proceed_cycles(&timer_service, 1);
}
void ChoiceTest::initForEventH(sc_boolean valueForC)
{
	choice_enter(&statechart);
	EXPECT_TRUE(choice_isStateActive(&statechart, Choice_main_region_A));
	choiceIface_set_c(&statechart,valueForC);
	choiceIface_raise_h(&statechart);
	sc_timer_service_proceed_cycles(&timer_service, 1);
}

void ChoiceTest::setTimer(Choice* statechart, const sc_eventid evid, const sc_integer time_ms, const sc_boolean periodic){
	sc_timer_t timer;
	sc_timer_init(&timer, time_ms, periodic, evid);
	insert_timer(&(tc->timer_service), timer);
}

void ChoiceTest::unsetTimer(Choice* handle, const sc_eventid evid){
	delete_task(&(tc->timer_service), find_time_event(&timer_service, evid));
}

TEST_F(ChoiceTest, elseChoiceUsingNonDefaultTransition) {
	elseChoiceUsingNonDefaultTransition();
}
TEST_F(ChoiceTest, elseChoiceUsingDefaultTransition) {
	elseChoiceUsingDefaultTransition();
}
TEST_F(ChoiceTest, defaultChoiceUsingNonDefaultTransition) {
	defaultChoiceUsingNonDefaultTransition();
}
TEST_F(ChoiceTest, defaultChoiceUsingDefaultTransition) {
	defaultChoiceUsingDefaultTransition();
}
TEST_F(ChoiceTest, uncheckedChoiceUsingNonDefaultTransition) {
	uncheckedChoiceUsingNonDefaultTransition();
}
TEST_F(ChoiceTest, uncheckedChoiceUsingDefaultTransition) {
	uncheckedChoiceUsingDefaultTransition();
}
TEST_F(ChoiceTest, alwaysTrueTransitionInChoice) {
	alwaysTrueTransitionInChoice();
}


