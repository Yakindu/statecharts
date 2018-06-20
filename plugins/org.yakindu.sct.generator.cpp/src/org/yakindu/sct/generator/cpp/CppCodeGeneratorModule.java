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
package org.yakindu.sct.generator.cpp;

import static org.yakindu.sct.generator.cpp.features.CPPFeatureConstants.FEATURE_INCLUDES;
import static org.yakindu.sct.generator.cpp.features.CPPFeatureConstants.PARAMETER_INCLUDES_USE_RELATIVE_PATHS;

import org.yakindu.base.types.inferrer.ITypeSystemInferrer;
import org.yakindu.sct.generator.c.DefaultGenArtifactConfigurations;
import org.yakindu.sct.generator.c.IGenArtifactConfigurations;
import org.yakindu.sct.generator.c.IncludeProvider;
import org.yakindu.sct.generator.c.SimpleGenArtifactConfigurations;
import org.yakindu.sct.generator.c.extensions.GenmodelEntries;
import org.yakindu.sct.generator.c.extensions.Naming;
import org.yakindu.sct.generator.c.types.CTypeSystemAccess;
import org.yakindu.sct.generator.core.IExecutionFlowGenerator;
import org.yakindu.sct.generator.core.IGeneratorModule;
import org.yakindu.sct.generator.core.extensions.AnnotationExtensions;
import org.yakindu.sct.generator.core.types.ICodegenTypeSystemAccess;
import org.yakindu.sct.generator.cpp.eventdriven.EventDrivenCppIncludeProvider;
import org.yakindu.sct.generator.cpp.eventdriven.EventDrivenEventCode;
import org.yakindu.sct.generator.cpp.eventdriven.EventDrivenExpressionCode;
import org.yakindu.sct.generator.cpp.eventdriven.EventDrivenStatemachineHeader;
import org.yakindu.sct.generator.cpp.eventdriven.EventDrivenStatemachineImplementation;
import org.yakindu.sct.model.sexec.naming.INamingService;
import org.yakindu.sct.model.sgen.FeatureParameterValue;
import org.yakindu.sct.model.sgen.GeneratorEntry;
import org.yakindu.sct.model.stext.inferrer.STextTypeInferrer;

import com.google.inject.Binder;
import com.google.inject.name.Names;

/**
 *
 * @author andreas muelder - Initial contribution and API
 *
 */
public class CppCodeGeneratorModule implements IGeneratorModule {

	@Override
	public void configure(GeneratorEntry entry, Binder binder) {
		binder.bind(GeneratorEntry.class).toInstance(entry);
		binder.bind(String.class).annotatedWith(Names.named("Separator")).toInstance(getSeparator(entry));
		binder.bind(IExecutionFlowGenerator.class).to(CppGenerator.class);
		binder.bind(ICodegenTypeSystemAccess.class).to(CTypeSystemAccess.class);
		binder.bind(INamingService.class).to(CppNamingService.class);
		binder.bind(ITypeSystemInferrer.class).to(STextTypeInferrer.class);
		binder.bind(Naming.class).to(CppNaming.class);
		binder.bind(IncludeProvider.class).to(StandardCppIncludeProvider.class);
		bindEventDrivenClasses(entry, binder);
		bindIGenArtifactConfigurations(entry, binder);
	}

	protected void bindIGenArtifactConfigurations(GeneratorEntry entry, Binder binder) {
		FeatureParameterValue useRelativePathParam = entry.getFeatureParameterValue(FEATURE_INCLUDES,
				PARAMETER_INCLUDES_USE_RELATIVE_PATHS);
		boolean useRelativePath = useRelativePathParam != null ? useRelativePathParam.getBooleanValue() : true;
		if (useRelativePath) {
			binder.bind(IGenArtifactConfigurations.class).to(DefaultGenArtifactConfigurations.class);
		} else {
			binder.bind(IGenArtifactConfigurations.class).to(SimpleGenArtifactConfigurations.class);
		}
	}

	protected void bindEventDrivenClasses(GeneratorEntry entry, Binder binder) {
		if ((new AnnotationExtensions()).isEventDriven(entry)) {
			binder.bind(StatemachineHeader.class).to(EventDrivenStatemachineHeader.class);
			binder.bind(StatemachineImplementation.class).to(EventDrivenStatemachineImplementation.class);
			binder.bind(CppExpressionsGenerator.class).to(EventDrivenExpressionCode.class);
			binder.bind(EventCode.class).to(EventDrivenEventCode.class);
			binder.bind(StandardCppIncludeProvider.class).to(EventDrivenCppIncludeProvider.class);
		}
	}

	protected String getSeparator(GeneratorEntry entry) {
		GenmodelEntries entries = new GenmodelEntries();
		String separator = entries.getSeparator(entry);
		if (separator == null) {
			return "_";
		} else {
			return separator;
		}
	}

}
