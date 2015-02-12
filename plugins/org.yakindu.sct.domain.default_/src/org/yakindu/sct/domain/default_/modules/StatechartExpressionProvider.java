package org.yakindu.sct.domain.default_.modules;

import org.eclipse.emf.ecore.EObject;
import org.yakindu.sct.model.stext.stext.StatechartSpecification;

/**
 * 
 * @author andreas muelder - Initial contribution and API
 * 
 */
public class StatechartExpressionProvider extends AbstractSTextExpressionProvider {

	@Override
	protected Class<? extends EObject> getRule() {
		return StatechartSpecification.class;
	}
}