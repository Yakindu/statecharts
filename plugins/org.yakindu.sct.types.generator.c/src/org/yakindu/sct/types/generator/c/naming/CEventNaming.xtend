/** 
 * Copyright (c) 2019 committers of YAKINDU and others. 
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0 
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/epl-v10.html 
 * Contributors:
 * committers of YAKINDU - initial API and implementation
 *
*/
package org.yakindu.sct.types.generator.c.naming

import org.yakindu.sct.types.generator.naming.IEventNaming

class CEventNaming implements IEventNaming {
	override String nameEventRaiser(CharSequence s) {
		return '''raise_«s.toString.toFirstLower»''' // TODO: can be removed with 4.0. Needed to keep API.
	}

	override String nameEventRaised(CharSequence s) {
		return '''«s»_raised'''
	}

	override String nameEventIsRaised(CharSequence s) {
		return '''israised_«s»'''
	}
	
	override nameEventValue(CharSequence s) {
		return '''«s»_value'''
	}
	
	override nameEventValueGetter(CharSequence s) {
		return '''get_«s»_value'''
	}
}
