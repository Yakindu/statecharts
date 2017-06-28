
#ifndef BITEXPRESSIONS_H_
#define BITEXPRESSIONS_H_

#include "sc_types.h"
		
#ifdef __cplusplus
extern "C" { 
#endif 

/*! \file Header of the state machine 'BitExpressions'.
*/

/*! Enumeration of all states */ 
typedef enum
{
	BitExpressions_last_state,
	BitExpressions_main_region_StateA,
	BitExpressions_main_region_StateB
} BitExpressionsStates;

/*! Type definition of the data structure for the BitExpressionsIface interface scope. */
typedef struct
{
	sc_integer myBit1;
	sc_integer myBit2;
	sc_integer leftBitshift;
	sc_integer rightBitshift;
	sc_integer complementBitshift;
	sc_integer bitwiseAnd;
	sc_integer bitwiseOr;
	sc_integer bitwiseXor;
	sc_boolean e1_raised;
} BitExpressionsIface;


/*! Define dimension of the state configuration vector for orthogonal states. */
#define BITEXPRESSIONS_MAX_ORTHOGONAL_STATES 1

/*! Define indices of states in the StateConfVector */
#define SCVI_BITEXPRESSIONS_MAIN_REGION_STATEA 0
#define SCVI_BITEXPRESSIONS_MAIN_REGION_STATEB 0

/*! 
 * Type definition of the data structure for the BitExpressions state machine.
 * This data structure has to be allocated by the client code. 
 */
typedef struct
{
	BitExpressionsStates stateConfVector[BITEXPRESSIONS_MAX_ORTHOGONAL_STATES];
	sc_ushort stateConfVectorPosition; 
	
	BitExpressionsIface iface;
} BitExpressions;

/*! Initializes the BitExpressions state machine data structures. Must be called before first usage.*/
extern void bitExpressions_init(BitExpressions* handle);

/*! Activates the state machine */
extern void bitExpressions_enter(BitExpressions* handle);

/*! Deactivates the state machine */
extern void bitExpressions_exit(BitExpressions* handle);

/*! Performs a 'run to completion' step. */
extern void bitExpressions_runCycle(BitExpressions* handle);


/*! Gets the value of the variable 'myBit1' that is defined in the default interface scope. */ 
extern sc_integer bitExpressionsIface_get_myBit1(const BitExpressions* handle);
/*! Sets the value of the variable 'myBit1' that is defined in the default interface scope. */ 
extern void bitExpressionsIface_set_myBit1(BitExpressions* handle, sc_integer value);
/*! Gets the value of the variable 'myBit2' that is defined in the default interface scope. */ 
extern sc_integer bitExpressionsIface_get_myBit2(const BitExpressions* handle);
/*! Sets the value of the variable 'myBit2' that is defined in the default interface scope. */ 
extern void bitExpressionsIface_set_myBit2(BitExpressions* handle, sc_integer value);
/*! Gets the value of the variable 'leftBitshift' that is defined in the default interface scope. */ 
extern sc_integer bitExpressionsIface_get_leftBitshift(const BitExpressions* handle);
/*! Sets the value of the variable 'leftBitshift' that is defined in the default interface scope. */ 
extern void bitExpressionsIface_set_leftBitshift(BitExpressions* handle, sc_integer value);
/*! Gets the value of the variable 'rightBitshift' that is defined in the default interface scope. */ 
extern sc_integer bitExpressionsIface_get_rightBitshift(const BitExpressions* handle);
/*! Sets the value of the variable 'rightBitshift' that is defined in the default interface scope. */ 
extern void bitExpressionsIface_set_rightBitshift(BitExpressions* handle, sc_integer value);
/*! Gets the value of the variable 'complementBitshift' that is defined in the default interface scope. */ 
extern sc_integer bitExpressionsIface_get_complementBitshift(const BitExpressions* handle);
/*! Sets the value of the variable 'complementBitshift' that is defined in the default interface scope. */ 
extern void bitExpressionsIface_set_complementBitshift(BitExpressions* handle, sc_integer value);
/*! Gets the value of the variable 'bitwiseAnd' that is defined in the default interface scope. */ 
extern sc_integer bitExpressionsIface_get_bitwiseAnd(const BitExpressions* handle);
/*! Sets the value of the variable 'bitwiseAnd' that is defined in the default interface scope. */ 
extern void bitExpressionsIface_set_bitwiseAnd(BitExpressions* handle, sc_integer value);
/*! Gets the value of the variable 'bitwiseOr' that is defined in the default interface scope. */ 
extern sc_integer bitExpressionsIface_get_bitwiseOr(const BitExpressions* handle);
/*! Sets the value of the variable 'bitwiseOr' that is defined in the default interface scope. */ 
extern void bitExpressionsIface_set_bitwiseOr(BitExpressions* handle, sc_integer value);
/*! Gets the value of the variable 'bitwiseXor' that is defined in the default interface scope. */ 
extern sc_integer bitExpressionsIface_get_bitwiseXor(const BitExpressions* handle);
/*! Sets the value of the variable 'bitwiseXor' that is defined in the default interface scope. */ 
extern void bitExpressionsIface_set_bitwiseXor(BitExpressions* handle, sc_integer value);
/*! Raises the in event 'e1' that is defined in the default interface scope. */ 
extern void bitExpressionsIface_raise_e1(BitExpressions* handle);


/*!
 * Checks whether the state machine is active (until 2.4.1 this method was used for states).
 * A state machine is active if it was entered. It is inactive if it has not been entered at all or if it has been exited.
 */
extern sc_boolean bitExpressions_isActive(const BitExpressions* handle);

/*!
 * Checks if all active states are final. 
 * If there are no active states then the state machine is considered being inactive. In this case this method returns false.
 */
extern sc_boolean bitExpressions_isFinal(const BitExpressions* handle);

/*! Checks if the specified state is active (until 2.4.1 the used method for states was called isActive()). */
extern sc_boolean bitExpressions_isStateActive(const BitExpressions* handle, BitExpressionsStates state);

#ifdef __cplusplus
}
#endif 

#endif /* BITEXPRESSIONS_H_ */
