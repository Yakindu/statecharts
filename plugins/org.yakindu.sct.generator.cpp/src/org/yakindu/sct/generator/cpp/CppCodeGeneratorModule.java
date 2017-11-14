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
import static org.yakindu.sct.model.stext.lib.StatechartAnnotations.EVENT_DRIVEN_ANNOTATION;

import org.yakindu.base.types.Annotation;
import org.yakindu.base.types.inferrer.ITypeSystemInferrer;
import org.yakindu.sct.generator.c.DefaultGenArtifactConfigurations;
import org.yakindu.sct.generator.c.IGenArtifactConfigurations;
import org.yakindu.sct.generator.c.SimpleGenArtifactConfigurations;
import org.yakindu.sct.generator.c.extensions.Naming;
import org.yakindu.sct.generator.c.language.CFunctionFactory;
import org.yakindu.sct.generator.c.types.CTypeSystemAccess;
import org.yakindu.sct.generator.core.IExecutionFlowGenerator;
import org.yakindu.sct.generator.core.IGeneratorModule;
import org.yakindu.sct.generator.core.language.IFunction;
import org.yakindu.sct.generator.core.language.factory.FunctionFactory;
import org.yakindu.sct.generator.core.language.factory.IStandardFunctionProvider;
import org.yakindu.sct.generator.core.types.ICodegenTypeSystemAccess;
import org.yakindu.sct.generator.cpp.classes.StatechartClass;
import org.yakindu.sct.generator.cpp.classes.StatechartInterfaceClass;
import org.yakindu.sct.generator.cpp.classes.members.StandardFunctionProvider;
import org.yakindu.sct.generator.cpp.eventdriven.EventDrivenEventCode;
import org.yakindu.sct.generator.cpp.eventdriven.EventDrivenExpressionCode;
import org.yakindu.sct.generator.cpp.eventdriven.EventDrivenStandardFunctions;
import org.yakindu.sct.generator.cpp.eventdriven.EventDrivenStatechartClass;
import org.yakindu.sct.generator.cpp.eventdriven.EventDrivenStatechartInterfaceClass;
import org.yakindu.sct.generator.cpp.eventdriven.EventDrivenStatemachineHeader;
import org.yakindu.sct.generator.cpp.eventdriven.EventDrivenStatemachineImplementation;
import org.yakindu.sct.generator.cpp.language.Function;
import org.yakindu.sct.model.sexec.naming.INamingService;
import org.yakindu.sct.model.sgen.FeatureParameterValue;
import org.yakindu.sct.model.sgen.GeneratorEntry;
import org.yakindu.sct.model.sgraph.Statechart;
import org.yakindu.sct.model.stext.inferrer.STextTypeInferrer;

import com.google.inject.Binder;

/**
 * 
 * @author andreas muelder - Initial contribution and API
 * 
 */
public class CppCodeGeneratorModule implements IGeneratorModule {

	@Override
	public void configure(GeneratorEntry entry, Binder binder) {
		binder.bind(GeneratorEntry.class).toInstance(entry);
		binder.bind(IExecutionFlowGenerator.class).to(CppGenerator.class);
		binder.bind(ICodegenTypeSystemAccess.class).to(CTypeSystemAccess.class);
		binder.bind(INamingService.class).to(CppNamingService.class);
		binder.bind(ITypeSystemInferrer.class).to(STextTypeInferrer.class);
		binder.bind(Naming.class).to(CppNaming.class);
		bindLanguageModules(binder);
		bindEventDrivenClasses(entry, binder);
		bindIGenArtifactConfigurations(entry, binder);
	}
	
	/**
	 * @param binder
	 */
	private void bindLanguageModules(Binder binder) {
		binder.bind(IFunction.class).to(Function.class);
		binder.bind(FunctionFactory.class).to(CFunctionFactory.class);
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
		Statechart statechart = (Statechart) entry.getElementRef();
		Annotation eventDrivenAnnotation = statechart.getAnnotationOfType(EVENT_DRIVEN_ANNOTATION);
		
		if(eventDrivenAnnotation != null) {
			binder.bind(StatemachineHeader.class).to(EventDrivenStatemachineHeader.class);
			binder.bind(StatemachineImplementation.class).to(EventDrivenStatemachineImplementation.class);
			binder.bind(CppExpressionsGenerator.class).to(EventDrivenExpressionCode.class);
			binder.bind(EventCode.class).to(EventDrivenEventCode.class);
			binder.bind(StatechartClass.class).to(EventDrivenStatechartClass.class);
			binder.bind(StatechartInterfaceClass.class).to(EventDrivenStatechartInterfaceClass.class);
			binder.bind(IStandardFunctionProvider.class).to(EventDrivenStandardFunctions.class);
		} else {
			binder.bind(IStandardFunctionProvider.class).to(StandardFunctionProvider.class);
		}
	}

}
