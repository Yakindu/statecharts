/**
 * Copyright (c) 2016 committers of YAKINDU and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     committers of YAKINDU - initial API and implementation
 */
package org.yakindu.sct.generator.core.execution;

import org.yakindu.base.base.NamedElement;
import org.yakindu.sct.generator.core.console.IConsoleLogger;
import org.yakindu.sct.generator.core.filesystem.DefaultFileSystemAccessFactory;
import org.yakindu.sct.generator.core.filesystem.ISCTFileSystemAccess;
import org.yakindu.sct.generator.core.library.ICoreLibraryHelper;
import org.yakindu.sct.model.sgen.GeneratorEntry;

import com.google.inject.Inject;

/**
 * 
 * @author andreas muelder - Initial contribution and API
 * 
 */
public abstract class AbstractGeneratorEntryExecutor implements IGeneratorEntryExecutor {

	@Inject
	protected DefaultFileSystemAccessFactory factory;
	@Inject
	protected IConsoleLogger logger;
	@Inject
	protected ICoreLibraryHelper helper;

	protected abstract void execute(ISCTFileSystemAccess access, GeneratorEntry generatorEntry);

	@Override
	public void execute(GeneratorEntry entry) {
		NamedElement element = (NamedElement) entry.getElementRef();
		logger.log("Generating '" + element.getName() + "' to target project ..."
				+ helper.getTargetProjectValue(entry).getStringValue());
		try {
			execute(factory.create(entry), entry);
		} catch (Exception ex) {
			logger.logError(ex);
		} finally {
			logger.log("done...");
		}

	}

}
