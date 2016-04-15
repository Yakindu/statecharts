/**
 * Copyright (c) 2011 committers of YAKINDU and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     committers of YAKINDU - initial API and implementation
 */
package org.yakindu.sct.generator.cpp;

import org.yakindu.sct.generator.c.GenArtifactConfigurations;
import org.yakindu.sct.generator.c.IGenArtifactConfigurations;
import org.yakindu.sct.generator.c.types.CTypeSystemAccess;
import org.yakindu.sct.generator.core.impl.GenericJavaBasedGenerator;
import org.yakindu.sct.generator.core.types.ICodegenTypeSystemAccess;
import org.yakindu.sct.model.sexec.ExecutionFlow;
import org.yakindu.sct.model.sexec.naming.INamingService;
import org.yakindu.sct.model.sgen.GeneratorEntry;
import org.yakindu.sct.model.sgraph.Statechart;

import com.google.inject.Binder;
import com.google.inject.Inject;
import com.google.inject.Module;
import com.google.inject.name.Names;
import com.google.inject.util.Modules;

/**
 * 
 * @author Markus Mühlbrandt - Initial contribution and API
 * 
 */
public class CppCodeGenerator extends GenericJavaBasedGenerator {

	@Inject
	private CppGenerator delegate;
	
	@Override
	public void runGenerator(Statechart statechart, GeneratorEntry entry) {
		ExecutionFlow flow = createExecutionFlow(statechart, entry);
		if (debugFeatureHelper.isDumpSexec(entry)) {
			dumpSexec(entry, flow);
		}
		delegate.generate(flow, entry, sctFsa.getIFileSystemAccess());
	}

	@Override
	public Module getOverridesModule(final GeneratorEntry entry) {
		Module module = super.getOverridesModule(entry);
		return Modules.override(module).with(new Module() {
			public void configure(Binder binder) {
				binder.bind(ICodegenTypeSystemAccess.class).to(CTypeSystemAccess.class);
				binder.bind(INamingService.class).to(CppNamingService.class);
				binder.bind(GeneratorEntry.class).toInstance(entry);
				binder.bind(IGenArtifactConfigurations.class)
						.annotatedWith(Names.named(IGenArtifactConfigurations.DEFAULT))
						.toInstance(GenArtifactConfigurations.DEFAULT);
			}
		});
	}
}
