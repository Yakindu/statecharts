package org.yakindu.sct.model.sexec.transformation.ng

import com.google.inject.Inject
import org.yakindu.sct.model.sexec.transformation.SexecExtensions
import org.yakindu.sct.model.sexec.transformation.SgraphExtensions
import org.yakindu.sct.model.sgraph.Region
import org.yakindu.sct.model.sgraph.Statechart

class HistoryVector {
	
	@Inject extension SexecExtensions sexec
	@Inject extension SgraphExtensions sgraph
	
	def create sexec.factory.createStateVector historyVector(Statechart sc) {
		var offset = -1
		for ( r : sc.eAllContents.filter(typeof(Region)).toIterable) {
			if (r.requireHistory) {
				offset = offset+1
				r.historyVector.offset = offset;
				r.historyVector.size = 1
			}
		}
		if (offset != -1) {
			it.offset = 0;
			it.size = offset+1
		}
	}
	
	def create sexec.factory.createStateVector historyVector(Region r) {}
}