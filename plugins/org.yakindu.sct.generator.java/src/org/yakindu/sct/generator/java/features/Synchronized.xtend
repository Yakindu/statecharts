package org.yakindu.sct.generator.java.features

import com.google.inject.Inject
import org.yakindu.sct.generator.java.GeneratorPredicate

class Synchronized {
	@Inject protected extension GeneratorPredicate
	
	def sync() {
		if(needsSynchronized) "synchronized " else ""
	}
	
	def sync(String object, CharSequence code) {
		if(needsSynchronized) {
			'''
			synchronized(«object») {
				«code»
			}
			'''
		} else {
			code
		}
	}
}