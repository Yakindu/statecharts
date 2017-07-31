
#ifndef LOGICALAND_H_
#define LOGICALAND_H_

#include "sc_types.h"
#include "StatemachineInterface.h"

/*! \file Header of the state machine 'LogicalAnd'.
*/

/*! Define indices of states in the StateConfVector */
#define SCVI_MAIN_REGION_A 0

class LogicalAnd : public StatemachineInterface
{
	
	public:
		
		LogicalAnd();
		
		~LogicalAnd();
		
		/*! Enumeration of all states */ 
		typedef enum
		{
			LogicalAnd_last_state,
			main_region_A
		} LogicalAndStates;
		
		//! Inner class for default interface scope.
		class DefaultSCI
		{
			
			public:
				/*! Gets the value of the variable 'x' that is defined in the default interface scope. */
				sc_integer get_x() const;
				
				/*! Sets the value of the variable 'x' that is defined in the default interface scope. */
				void set_x(sc_integer value);
				
				/*! Gets the value of the variable 'b' that is defined in the default interface scope. */
				sc_boolean get_b() const;
				
				/*! Sets the value of the variable 'b' that is defined in the default interface scope. */
				void set_b(sc_boolean value);
				
				
			private:
				friend class LogicalAnd;
				sc_integer x;
				sc_boolean b;
		};
				
		
		/*! Returns an instance of the interface class 'DefaultSCI'. */
		DefaultSCI* getDefaultSCI();
		
		/*! Gets the value of the variable 'x' that is defined in the default interface scope. */
		sc_integer get_x() const;
		
		/*! Sets the value of the variable 'x' that is defined in the default interface scope. */
		void set_x(sc_integer value);
		
		/*! Gets the value of the variable 'b' that is defined in the default interface scope. */
		sc_boolean get_b() const;
		
		/*! Sets the value of the variable 'b' that is defined in the default interface scope. */
		void set_b(sc_boolean value);
		
		
		/*
		 * Functions inherited from StatemachineInterface
		 */
		virtual void init();
		
		virtual void enter();
		
		virtual void exit();
		
		virtual void runCycle();
		
		/*!
		* Checks if the state machine is active (until 2.4.1 this method was used for states).
		* A state machine is active if it has been entered. It is inactive if it has not been entered at all or if it has been exited.
		*/
		virtual sc_boolean isActive() const;
		
		
		/*!
		* Checks if all active states are final. 
		* If there are no active states then the state machine is considered being inactive. In this case this method returns false.
		*/
		virtual sc_boolean isFinal() const;
		
		
		/*! Checks if the specified state is active (until 2.4.1 the used method for states was calles isActive()). */
		sc_boolean isStateActive(LogicalAndStates state) const;
	
	private:
	
	
		//! the maximum number of orthogonal states defines the dimension of the state configuration vector.
		static const sc_integer maxOrthogonalStates = 1;
		
		
		LogicalAndStates stateConfVector[maxOrthogonalStates];
		
		sc_ushort stateConfVectorPosition;
		
		DefaultSCI iface;
		
		// prototypes of all internal functions
		
		sc_boolean check_main_region_A_tr0_tr0();
		void effect_main_region_A_tr0();
		void enseq_main_region_A_default();
		void enseq_main_region_default();
		void exseq_main_region_A();
		void exseq_main_region();
		void react_main_region_A();
		void react_main_region__entry_Default();
		void clearInEvents();
		void clearOutEvents();
		
};
#endif /* LOGICALAND_H_ */
