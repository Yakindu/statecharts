/*
 * Timer Service for SCTUnit
 */

#ifndef SC_TIMER_SERVICE_H_
#define SC_TIMER_SERVICE_H_

#include <list>

#include "sc_types.h"
#include "StatemachineInterface.h"
#include "TimedStatemachineInterface.h"
#include "TimerInterface.h"

#ifdef __cplusplus
extern "C" {
#endif

class TimedSctUnitRunner : public TimerInterface {
	public:
		TimedSctUnitRunner(StatemachineInterface * statemachine, bool event_driven, sc_integer cycle_period);
		~TimedSctUnitRunner(){}
		void proceed_time(sc_integer time_ms);
		void proceed_cycles(sc_integer cycles);
		virtual void setTimer(TimedStatemachineInterface* statemachine, sc_eventid event, sc_integer time_ms, sc_boolean isPeriodic);
		virtual void unsetTimer(TimedStatemachineInterface* statemachine, sc_eventid event);
		virtual void cancel();
	private:
		class SctTimer;
		StatemachineInterface * statemachine;
		
		bool event_driven;
		sc_integer cycle_period;
		sc_integer current_time_ms;

		std::list<SctTimer> timer_queue;
		void insert_timer(SctTimer timer);
};

class TimedSctUnitRunner::SctTimer {
	friend class TimedSctUnitRunner;
	public:
		SctTimer(sc_integer time_ms, bool periodic, sc_eventid evid, sc_integer priority, sc_boolean is_runcycle);
		~SctTimer(){}
		
		sc_integer compare(SctTimer * other);
		
	private:
		sc_integer rel_time_ms;
		sc_integer abs_time_ms;
		sc_boolean periodic;
		sc_eventid pt_evid;
		sc_integer priority;
		sc_boolean is_runcycle;
};

#ifdef __cplusplus
}
#endif

#endif /* SC_TIMER_SERVICE_H_ */

