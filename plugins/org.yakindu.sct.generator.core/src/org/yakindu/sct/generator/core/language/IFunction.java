/**
 * Copyright (c) 2017 committers of YAKINDU and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 * 	rbeckmann - initial API and implementation
 * 
 */
package org.yakindu.sct.generator.core.language;

import java.util.List;

/**
 * @author rbeckmann
 *
 */
public interface IFunction extends ICodeBlock, IModifierOwner, ITypeOwner, ITypeQualifierOwner, INameOwner, IDocumentationOwner, IModuleMember {
	public List<CharSequence> getParameters();
	public void setParameters(List<CharSequence> parameter);
}
