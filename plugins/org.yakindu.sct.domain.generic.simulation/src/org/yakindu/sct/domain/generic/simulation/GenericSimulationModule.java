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
package org.yakindu.sct.domain.generic.simulation;

import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.impl.ResourceSetBasedResourceDescriptions;
import org.eclipse.xtext.service.AbstractGenericModule;
import org.yakindu.base.expressions.interpreter.IExpressionInterpreter;
import org.yakindu.base.expressions.interpreter.IOperationExecutor;
import org.yakindu.base.types.inferrer.ITypeSystemInferrer;
import org.yakindu.base.types.typesystem.GenericTypeSystem;
import org.yakindu.base.types.typesystem.ITypeSystem;
import org.yakindu.sct.model.sexec.naming.DefaultNamingService;
import org.yakindu.sct.model.sexec.naming.INamingService;
import org.yakindu.sct.model.sexec.transformation.IModelSequencer;
import org.yakindu.sct.model.sexec.transformation.config.IFlowConfiguration;
import org.yakindu.sct.model.sexec.transformation.ng.ModelSequencer;
import org.yakindu.sct.model.sruntime.ExecutionContext;
import org.yakindu.sct.model.sruntime.impl.ExecutionContextImpl;
import org.yakindu.sct.model.stext.inferrer.STextTypeInferrer;
import org.yakindu.sct.model.stext.naming.StextNameProvider;
import org.yakindu.sct.simulation.core.sexec.container.ComplexTypeAwareContextInitializer;
import org.yakindu.sct.simulation.core.sexec.container.DefaultSimulationEngineFactory;
import org.yakindu.sct.simulation.core.sexec.container.IExecutionContextInitializer;
import org.yakindu.sct.simulation.core.sexec.container.ISimulationEngineFactory;
import org.yakindu.sct.simulation.core.sexec.container.SimulationFlowConfiguration;
import org.yakindu.sct.simulation.core.sexec.interpreter.DefaultExecutionFlowInterpreter;
import org.yakindu.sct.simulation.core.sexec.interpreter.IEventRaiser;
import org.yakindu.sct.simulation.core.sexec.interpreter.IExecutionFlowInterpreter;
import org.yakindu.sct.simulation.core.sexec.interpreter.JavaOperationMockup;
import org.yakindu.sct.simulation.core.sexec.interpreter.SexecInterpreterAdapter;
import org.yakindu.sct.simulation.core.sexec.interpreter.StextExpressionInterpreter;

import com.google.inject.Binder;
import com.google.inject.multibindings.Multibinder;
import com.google.inject.name.Names;

/**
 * 
 * @author andreas muelder - Initial contribution and API
 * @author axel terfloth - additions
 * 
 */
public class GenericSimulationModule extends AbstractGenericModule {

	public void configure(Binder binder) {
		super.configure(binder);
		binder.bind(Boolean.class).annotatedWith(Names.named(IModelSequencer.ADD_TRACES)).toInstance(Boolean.TRUE);
		binder.bind(ITypeSystem.class).toInstance(getTypeSystem());
		
		bindOperationMockups(binder);
	}

	protected void bindOperationMockups(Binder binder) {
		Multibinder<IOperationExecutor> mockupBinder = Multibinder.newSetBinder(binder, IOperationExecutor.class);
		mockupBinder.addBinding().to(JavaOperationMockup.class);
	}

	protected ITypeSystem getTypeSystem() {
		return GenericTypeSystem.getInstance();
	}

	public Class<? extends IExecutionContextInitializer> bindIExecutionContextInitializer() {
		return ComplexTypeAwareContextInitializer.class;
	}

	public Class<? extends ISimulationEngineFactory> bindISimulationEngineFactory() {
		return DefaultSimulationEngineFactory.class;
	}

	public Class<? extends IModelSequencer> bindIModelSequencer() {
		return ModelSequencer.class;
	}
	
	public Class<? extends IFlowConfiguration> bindIFlowConfiguration() {
		return SimulationFlowConfiguration.class;
	}
	
	public Class<? extends INamingService> bindINamingService(){
		return DefaultNamingService.class;
	}

	public Class<? extends ExecutionContext> bindExecutionContext() {
		return ExecutionContextImpl.class;
	}

	public Class<? extends IExecutionFlowInterpreter> bindIExecutionFlowInterpreter() {
		return SexecInterpreterAdapter.class;
	}

	public Class<? extends IEventRaiser> bindIEventRaiser() {
		return SexecInterpreterAdapter.class;
	}

	public Class<? extends IExpressionInterpreter> bindIExpressionInterpreter() {
		return StextExpressionInterpreter.class;
	}

	public Class<? extends IQualifiedNameProvider> bindIQualifiedNameProvider() {
		return StextNameProvider.class;
	}

	public Class<? extends ITypeSystemInferrer> bindITypeSystemInferrer() {
		return STextTypeInferrer.class;
	}

	public Class<? extends IResourceDescriptions> bindIResourceDescriptions() {
		return ResourceSetBasedResourceDescriptions.class;
	}

}
