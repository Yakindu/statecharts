
#ifndef SHALLOWHISTORY_H_
#define SHALLOWHISTORY_H_

#include "sc_types.h"
		
#ifdef __cplusplus
extern "C" { 
#endif 

/*! \file Header of the state machine 'ShallowHistory'.
*/

/*! Enumeration of all states */ 
typedef enum
{
	ShallowHistory_last_state,
	ShallowHistory_mainRegion_State1,
	ShallowHistory_mainRegion_State2,
	ShallowHistory_mainRegion_State2__region0_State3,
	ShallowHistory_mainRegion_State2__region0_State4,
	ShallowHistory_mainRegion_State2__region0_State4__region0_State6,
	ShallowHistory_mainRegion_State2__region0_State4__region0_State7,
	ShallowHistory_mainRegion_State2__region0_State4__region0_State7__region0_State8,
	ShallowHistory_mainRegion_State2__region0_State4__region0_State7__region0_State9,
	ShallowHistory_mainRegion_State2__region0_State5
} ShallowHistoryStates;

/*! Type definition of the data structure for the ShallowHistoryIface interface scope. */
typedef struct
{
	sc_boolean event1_raised;
	sc_boolean event2_raised;
	sc_boolean event3_raised;
	sc_boolean event4_raised;
	sc_boolean event5_raised;
	sc_boolean event6_raised;
	sc_boolean event7_raised;
	sc_boolean event8_raised;
} ShallowHistoryIface;


/*! Define dimension of the state configuration vector for orthogonal states. */
#define SHALLOWHISTORY_MAX_ORTHOGONAL_STATES 1
	/*! Define dimension of the state configuration vector for history states. */
#define SHALLOWHISTORY_MAX_HISTORY_STATES 2

/*! Define indices of states in the StateConfVector */
#define SCVI_SHALLOWHISTORY_MAINREGION_STATE1 0
#define SCVI_SHALLOWHISTORY_MAINREGION_STATE2 0
#define SCVI_SHALLOWHISTORY_MAINREGION_STATE2__REGION0_STATE3 0
#define SCVI_SHALLOWHISTORY_MAINREGION_STATE2__REGION0_STATE4 0
#define SCVI_SHALLOWHISTORY_MAINREGION_STATE2__REGION0_STATE4__REGION0_STATE6 0
#define SCVI_SHALLOWHISTORY_MAINREGION_STATE2__REGION0_STATE4__REGION0_STATE7 0
#define SCVI_SHALLOWHISTORY_MAINREGION_STATE2__REGION0_STATE4__REGION0_STATE7__REGION0_STATE8 0
#define SCVI_SHALLOWHISTORY_MAINREGION_STATE2__REGION0_STATE4__REGION0_STATE7__REGION0_STATE9 0
#define SCVI_SHALLOWHISTORY_MAINREGION_STATE2__REGION0_STATE5 0

/*! 
 * Type definition of the data structure for the ShallowHistory state machine.
 * This data structure has to be allocated by the client code. 
 */
typedef struct
{
	ShallowHistoryStates stateConfVector[SHALLOWHISTORY_MAX_ORTHOGONAL_STATES];
	ShallowHistoryStates historyVector[SHALLOWHISTORY_MAX_HISTORY_STATES];
	sc_ushort stateConfVectorPosition; 
	
	ShallowHistoryIface iface;
} ShallowHistory;

/*! Initializes the ShallowHistory state machine data structures. Must be called before first usage.*/
extern void shallowHistory_init(ShallowHistory* handle);

/*! Activates the state machine */
extern void shallowHistory_enter(ShallowHistory* handle);

/*! Deactivates the state machine */
extern void shallowHistory_exit(ShallowHistory* handle);

/*! Performs a 'run to completion' step. */
extern void shallowHistory_runCycle(ShallowHistory* handle);


/*! Raises the in event 'event1' that is defined in the default interface scope. */ 
extern void shallowHistoryIface_raise_event1(ShallowHistory* handle);

/*! Raises the in event 'event2' that is defined in the default interface scope. */ 
extern void shallowHistoryIface_raise_event2(ShallowHistory* handle);

/*! Raises the in event 'event3' that is defined in the default interface scope. */ 
extern void shallowHistoryIface_raise_event3(ShallowHistory* handle);

/*! Raises the in event 'event4' that is defined in the default interface scope. */ 
extern void shallowHistoryIface_raise_event4(ShallowHistory* handle);

/*! Raises the in event 'event5' that is defined in the default interface scope. */ 
extern void shallowHistoryIface_raise_event5(ShallowHistory* handle);

/*! Raises the in event 'event6' that is defined in the default interface scope. */ 
extern void shallowHistoryIface_raise_event6(ShallowHistory* handle);

/*! Raises the in event 'event7' that is defined in the default interface scope. */ 
extern void shallowHistoryIface_raise_event7(ShallowHistory* handle);

/*! Raises the in event 'event8' that is defined in the default interface scope. */ 
extern void shallowHistoryIface_raise_event8(ShallowHistory* handle);


/*!
 * Checks whether the state machine is active (until 2.4.1 this method was used for states).
 * A state machine is active if it was entered. It is inactive if it has not been entered at all or if it has been exited.
 */
extern sc_boolean shallowHistory_isActive(const ShallowHistory* handle);

/*!
 * Checks if all active states are final. 
 * If there are no active states then the state machine is considered being inactive. In this case this method returns false.
 */
extern sc_boolean shallowHistory_isFinal(const ShallowHistory* handle);

/*! Checks if the specified state is active (until 2.4.1 the used method for states was called isActive()). */
extern sc_boolean shallowHistory_isStateActive(const ShallowHistory* handle, ShallowHistoryStates state);

#ifdef __cplusplus
}
#endif 

#endif /* SHALLOWHISTORY_H_ */
