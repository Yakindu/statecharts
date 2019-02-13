/**
 * Copyright (c) 2014 committers of YAKINDU and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 * 	committers of YAKINDU - initial API and implementation
 * 
 */
package org.yakindu.base.expressions.inferrer;

import static org.yakindu.base.types.typesystem.ITypeSystem.ANY;
import static org.yakindu.base.types.typesystem.ITypeSystem.BOOLEAN;
import static org.yakindu.base.types.typesystem.ITypeSystem.INTEGER;
import static org.yakindu.base.types.typesystem.ITypeSystem.NULL;
import static org.yakindu.base.types.typesystem.ITypeSystem.REAL;
import static org.yakindu.base.types.typesystem.ITypeSystem.STRING;
import static org.yakindu.base.types.typesystem.ITypeSystem.VOID;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.EcoreUtil2;
import org.yakindu.base.expressions.expressions.Argument;
import org.yakindu.base.expressions.expressions.ArgumentExpression;
import org.yakindu.base.expressions.expressions.AssignmentExpression;
import org.yakindu.base.expressions.expressions.BitwiseAndExpression;
import org.yakindu.base.expressions.expressions.BitwiseOrExpression;
import org.yakindu.base.expressions.expressions.BitwiseXorExpression;
import org.yakindu.base.expressions.expressions.BlockExpression;
import org.yakindu.base.expressions.expressions.BoolLiteral;
import org.yakindu.base.expressions.expressions.ConditionalExpression;
import org.yakindu.base.expressions.expressions.DoubleLiteral;
import org.yakindu.base.expressions.expressions.ElementReferenceExpression;
import org.yakindu.base.expressions.expressions.EventRaisingExpression;
import org.yakindu.base.expressions.expressions.EventValueReferenceExpression;
import org.yakindu.base.expressions.expressions.FeatureCall;
import org.yakindu.base.expressions.expressions.FloatLiteral;
import org.yakindu.base.expressions.expressions.HexLiteral;
import org.yakindu.base.expressions.expressions.IfExpression;
import org.yakindu.base.expressions.expressions.IntLiteral;
import org.yakindu.base.expressions.expressions.LogicalAndExpression;
import org.yakindu.base.expressions.expressions.LogicalNotExpression;
import org.yakindu.base.expressions.expressions.LogicalOrExpression;
import org.yakindu.base.expressions.expressions.LogicalRelationExpression;
import org.yakindu.base.expressions.expressions.NullLiteral;
import org.yakindu.base.expressions.expressions.NumericalAddSubtractExpression;
import org.yakindu.base.expressions.expressions.NumericalMultiplyDivideExpression;
import org.yakindu.base.expressions.expressions.NumericalUnaryExpression;
import org.yakindu.base.expressions.expressions.ParenthesizedExpression;
import org.yakindu.base.expressions.expressions.PostFixUnaryExpression;
import org.yakindu.base.expressions.expressions.PrimitiveValueExpression;
import org.yakindu.base.expressions.expressions.ReturnExpression;
import org.yakindu.base.expressions.expressions.ShiftExpression;
import org.yakindu.base.expressions.expressions.StringLiteral;
import org.yakindu.base.expressions.expressions.SwitchExpression;
import org.yakindu.base.expressions.expressions.TypeCastExpression;
import org.yakindu.base.expressions.expressions.UnaryOperator;
import org.yakindu.base.expressions.expressions.WhileExpression;
import org.yakindu.base.expressions.util.ExpressionExtensions;
import org.yakindu.base.types.Annotation;
import org.yakindu.base.types.AnnotationType;
import org.yakindu.base.types.EnumerationType;
import org.yakindu.base.types.Enumerator;
import org.yakindu.base.types.Event;
import org.yakindu.base.types.Expression;
import org.yakindu.base.types.GenericElement;
import org.yakindu.base.types.Operation;
import org.yakindu.base.types.Parameter;
import org.yakindu.base.types.Property;
import org.yakindu.base.types.Type;
import org.yakindu.base.types.TypeAlias;
import org.yakindu.base.types.TypeParameter;
import org.yakindu.base.types.TypeSpecifier;
import org.yakindu.base.types.inferrer.AbstractTypeSystemInferrer;
import org.yakindu.base.types.validation.IValidationIssueAcceptor;

import com.google.common.collect.Maps;
import com.google.inject.Inject;

/**
 * @author andreas muelder - Initial contribution and API
 * 
 */
public class ExpressionsTypeInferrer extends AbstractTypeSystemInferrer implements ExpressionsTypeInferrerMessages {
	
	@Inject
	protected TypeParameterInferrer typeParameterInferrer;

	@Inject
	protected ExpressionExtensions utils;

	public InferenceResult doInfer(AssignmentExpression e) {
		InferenceResult result1 = inferTypeDispatch(e.getVarRef());
		InferenceResult result2 = inferTypeDispatch(e.getExpression());
		assertAssignable(result1, result2, String.format(ASSIGNMENT_OPERATOR, e.getOperator(), result1, result2));
		return inferTypeDispatch(e.getVarRef());
	}

	public InferenceResult doInfer(ConditionalExpression e) {
		InferenceResult result1 = inferTypeDispatch(e.getTrueCase());
		InferenceResult result2 = inferTypeDispatch(e.getFalseCase());
		assertCompatible(result1, result2, String.format(COMMON_TYPE, result1, result2));
		assertIsSubType(inferTypeDispatch(e.getCondition()), getResultFor(BOOLEAN), CONDITIONAL_BOOLEAN);
		return getCommonType(result1, result2);
	}

	public InferenceResult doInfer(LogicalOrExpression e) {
		InferenceResult result1 = inferTypeDispatch(e.getLeftOperand());
		InferenceResult result2 = inferTypeDispatch(e.getRightOperand());
		assertIsSubType(result1, getResultFor(BOOLEAN), String.format(LOGICAL_OPERATORS, "||", result1, result2));
		assertIsSubType(result2, getResultFor(BOOLEAN), String.format(LOGICAL_OPERATORS, "||", result1, result2));
		return getResultFor(BOOLEAN);
	}

	public InferenceResult doInfer(LogicalAndExpression e) {
		InferenceResult result1 = inferTypeDispatch(e.getLeftOperand());
		InferenceResult result2 = inferTypeDispatch(e.getRightOperand());
		assertIsSubType(result1, getResultFor(BOOLEAN), String.format(LOGICAL_OPERATORS, "&&", result1, result2));
		assertIsSubType(result2, getResultFor(BOOLEAN), String.format(LOGICAL_OPERATORS, "&&", result1, result2));
		return getResultFor(BOOLEAN);
	}

	public InferenceResult doInfer(LogicalNotExpression e) {
		InferenceResult type = inferTypeDispatch(e.getOperand());
		assertIsSubType(type, getResultFor(BOOLEAN), String.format(LOGICAL_OPERATOR, "!", type));
		return getResultFor(BOOLEAN);
	}

	public InferenceResult doInfer(BitwiseXorExpression e) {
		InferenceResult result1 = inferTypeDispatch(e.getLeftOperand());
		InferenceResult result2 = inferTypeDispatch(e.getRightOperand());
		assertIsSubType(result1, getResultFor(INTEGER), String.format(BITWISE_OPERATORS, "^", result1, result2));
		assertIsSubType(result2, getResultFor(INTEGER), String.format(BITWISE_OPERATORS, "^", result1, result2));
		return getResultFor(INTEGER);
	}

	public InferenceResult doInfer(BitwiseOrExpression e) {
		InferenceResult result1 = inferTypeDispatch(e.getLeftOperand());
		InferenceResult result2 = inferTypeDispatch(e.getRightOperand());
		assertIsSubType(result1, getResultFor(INTEGER), String.format(BITWISE_OPERATORS, "|", result1, result2));
		assertIsSubType(result2, getResultFor(INTEGER), String.format(BITWISE_OPERATORS, "|", result1, result2));
		return getResultFor(INTEGER);
	}

	public InferenceResult doInfer(BitwiseAndExpression e) {
		InferenceResult result1 = inferTypeDispatch(e.getLeftOperand());
		InferenceResult result2 = inferTypeDispatch(e.getRightOperand());
		assertIsSubType(result1, getResultFor(INTEGER), String.format(BITWISE_OPERATORS, "&", result1, result2));
		assertIsSubType(result2, getResultFor(INTEGER), String.format(BITWISE_OPERATORS, "&", result1, result2));
		return getResultFor(INTEGER);
	}

	public InferenceResult doInfer(ShiftExpression e) {
		InferenceResult result1 = inferTypeDispatch(e.getLeftOperand());
		InferenceResult result2 = inferTypeDispatch(e.getRightOperand());
		assertIsSubType(result1, getResultFor(INTEGER),
				String.format(BITWISE_OPERATORS, e.getOperator(), result1, result2));
		assertIsSubType(result2, getResultFor(INTEGER),
				String.format(BITWISE_OPERATORS, e.getOperator(), result1, result2));
		return getResultFor(INTEGER);
	}

	public InferenceResult doInfer(LogicalRelationExpression e) {
		InferenceResult result1 = inferTypeDispatch(e.getLeftOperand());
		InferenceResult result2 = inferTypeDispatch(e.getRightOperand());
		assertCompatible(result1, result2, String.format(COMPARSION_OPERATOR, e.getOperator(), result1, result2));
		return getResultFor(BOOLEAN);
	}

	public InferenceResult doInfer(NumericalAddSubtractExpression e) {
		InferenceResult result1 = inferTypeDispatch(e.getLeftOperand());
		InferenceResult result2 = inferTypeDispatch(e.getRightOperand());
		assertCompatible(result1, result2, String.format(ARITHMETIC_OPERATORS, e.getOperator(), result1, result2));
		assertIsSubType(result1, getResultFor(REAL),
				String.format(ARITHMETIC_OPERATORS, e.getOperator(), result1, result2));
		return getCommonType(result1, result2);
	}

	public InferenceResult doInfer(NumericalMultiplyDivideExpression e) {
		InferenceResult result1 = inferTypeDispatch(e.getLeftOperand());
		InferenceResult result2 = inferTypeDispatch(e.getRightOperand());
		assertCompatible(result1, result2, String.format(ARITHMETIC_OPERATORS, e.getOperator(), result1, result2));
		assertIsSubType(result1, getResultFor(REAL),
				String.format(ARITHMETIC_OPERATORS, e.getOperator(), result1, result2));
		return getCommonType(result1, result2);
	}

	public InferenceResult doInfer(NumericalUnaryExpression e) {
		InferenceResult result1 = inferTypeDispatch(e.getOperand());
		if (e.getOperator() == UnaryOperator.COMPLEMENT)
			assertIsSubType(result1, getResultFor(INTEGER), String.format(BITWISE_OPERATOR, '~', result1));
		else {
			assertIsSubType(result1, getResultFor(REAL), String.format(ARITHMETIC_OPERATOR, e.getOperator(), result1));
		}
		return result1;
	}

	public InferenceResult doInfer(PostFixUnaryExpression expression) {
		InferenceResult result = inferTypeDispatch(expression.getOperand());
		assertIsSubType(result, getResultFor(REAL), null);
		return result;
	}

	public InferenceResult doInfer(TypeCastExpression e) {
		InferenceResult result1 = inferTypeDispatch(e.getOperand());
		InferenceResult result2 = inferTypeDispatch(e.getType());
		assertCompatible(result1, result2, String.format(CAST_OPERATORS, result1, result2));
		return inferTypeDispatch(e.getType());
	}

	public InferenceResult doInfer(WhileExpression e) {
		assertIsSubType(inferTypeDispatch(e.getCondition()), getResultFor(BOOLEAN), CONDITIONAL_BOOLEAN);
		return getResultFor(VOID);
	}
	
	public InferenceResult doInfer(ReturnExpression exp) {
		if(exp.getExpression() == null)
			return getResultFor(VOID);
		return inferTypeDispatch(exp.getExpression());
	}

	public InferenceResult doInfer(IfExpression it) {
		InferenceResult condition = inferTypeDispatch(it.getCondition());
		assertIsSubType(condition, getResultFor(BOOLEAN), CONDITIONAL_BOOLEAN);
		if(it.getElse() != null) {
			InferenceResult thenResult = inferTypeDispatch(it.getThen());
			InferenceResult elseResult = inferTypeDispatch(it.getElse());
			return getCommonType(thenResult, elseResult);
		}
		return getResultFor(VOID);
	}

	public InferenceResult doInfer(SwitchExpression it) {
		//TODO
		return getResultFor(VOID);
	}
	
	public InferenceResult doInfer(BlockExpression it) {
		InferenceResult result = getResultFor(VOID);
		EList<Expression> expressions = it.getExpressions();
		for (Expression expression : expressions) {
			result = inferTypeDispatch(expression);
		}
		return result;
	}
	
	public InferenceResult doInfer(EventRaisingExpression e) {
		Event event = null;
		EObject element = utils.featureOrReference(e.getEvent());
		if (element instanceof Event) {
			event = (Event) element;
		}
		InferenceResult eventType = null;
		if (event != null)
			eventType = inferTypeDispatch(event.getTypeSpecifier());
		eventType = eventType != null ? eventType : getResultFor(VOID);
		if (e.getValue() == null) {
			assertSame(eventType, getResultFor(VOID), String.format(MISSING_VALUE, eventType));
			return getResultFor(VOID);
		}
		InferenceResult valueType = inferTypeDispatch(e.getValue());
		assertAssignable(eventType, valueType, String.format(EVENT_DEFINITION, valueType, eventType));
		return valueType;

	}

	public InferenceResult doInfer(EventValueReferenceExpression e) {
		Event definition = null;
		EObject element = utils.featureOrReference(e.getValue());
		if (element instanceof Event) {
			definition = (Event) element;
		}
		if (definition != null)
			return definition.getTypeSpecifier() == null ? getResultFor(VOID)
					: inferTypeDispatch(definition.getTypeSpecifier());
		return inferTypeDispatch(e.getValue());
	}

	public InferenceResult doInfer(EnumerationType enumType) {
		return InferenceResult.from(enumType);
	}

	public InferenceResult doInfer(Enumerator enumerator) {
		return InferenceResult.from(EcoreUtil2.getContainerOfType(enumerator, Type.class));
	}

	public InferenceResult doInfer(Type type) {
		return InferenceResult.from(type.getOriginType());
	}

	/**
	 * The type of a type alias is its (recursively inferred) base type, i.e. type
	 * aliases are assignable if their inferred base types are assignable.
	 */
	public InferenceResult doInfer(TypeAlias typeAlias) {
		return inferTypeDispatch(typeAlias.getTypeSpecifier());
	}

	public InferenceResult doInfer(FeatureCall e) {
		// map to hold inference results for type parameters
		Map<TypeParameter, InferenceResult> inferredTypeParameterTypes = Maps.newHashMap();
		typeParameterInferrer.inferTypeParametersFromOwner(inferTypeDispatch(e.getOwner()), inferredTypeParameterTypes);

		if (e.isOperationCall()) {
			if (!e.getFeature().eIsProxy()) {
				return inferOperation(e, (Operation) e.getFeature(), inferredTypeParameterTypes);
			} else {
				return getAnyType();
			}
		}
		InferenceResult result = inferTypeDispatch(e.getFeature());
		if (result != null) {
			result = typeParameterInferrer.buildInferenceResult(result, inferredTypeParameterTypes, acceptor);
		}
		if (result == null) {
			return getAnyType();
		}
		return result;
	}

	public InferenceResult doInfer(ElementReferenceExpression e) {
		if (e.isOperationCall()) {
			if (e.getReference() != null && !e.getReference().eIsProxy()) {
				return inferOperation(e, (Operation) e.getReference(),
						Maps.<TypeParameter, InferenceResult>newHashMap());
			} else {
				return getAnyType();
			}
		}
		return inferTypeDispatch(e.getReference());
	}

	protected InferenceResult inferOperation(ArgumentExpression e, Operation op,
			Map<TypeParameter, InferenceResult> typeParameterMapping) {

		// resolve type parameter from operation call
		List<InferenceResult> argumentTypes = getArgumentTypes(getOperationArguments(e));
		List<Parameter> parameters = op.getParameters();

		List<InferenceResult> argumentsToInfer = new ArrayList<>();
		List<Parameter> parametersToInfer = new ArrayList<>();

		for (int i = 0; i < parameters.size(); i++) {
			if (!typeParameterMapping.containsKey(parameters.get(i).getType())) {
				parametersToInfer.add(parameters.get(i));
				if (i < argumentTypes.size()) {
					argumentsToInfer.add(argumentTypes.get(i));
				}

			}
		}

		typeParameterInferrer.inferTypeParametersFromOperationArguments(parametersToInfer, argumentsToInfer,
				typeParameterMapping, acceptor);
		validateParameters(typeParameterMapping, op, getOperationArguments(e), acceptor);

		return inferReturnType(e, op, typeParameterMapping);
	}

	protected InferenceResult getTargetType(ArgumentExpression exp) {
		EObject container = exp.eContainer();

		// Assignment
		if (container instanceof AssignmentExpression) {
			AssignmentExpression assignment = (AssignmentExpression) container;
			if (assignment.getExpression() == exp) {
				Expression varRef = ((AssignmentExpression) container).getVarRef();
				return inferTypeDispatch(varRef);
			}
		}
		// Variable Initialization
		if (container instanceof Property) {
			Property property = (Property) container;
			if (property.getInitialValue() == exp) {
				return inferTypeDispatch(property.getTypeSpecifier());
			}
		}
		// Operation Argument
		if (container instanceof Argument) {
			Argument argument = (Argument) container;
			if (argument.getValue() == exp) {
				ArgumentExpression argumentExpression = (ArgumentExpression) argument.eContainer();
				int index = getOperationArguments(argumentExpression).indexOf(argument.getValue());
				Operation op = (Operation) utils.featureOrReference(argumentExpression);
				Parameter param = op.getParameters().get(index);
				return inferTypeDispatch(param);
			}
		}
		return null;
	}

	/**
	 * Can be extended to e.g. add operation caller to argument list for extension
	 * methods
	 */
	protected List<Expression> getOperationArguments(ArgumentExpression e) {
		return e.getExpressions();
	}

	protected List<InferenceResult> getArgumentTypes(List<Expression> args) {
		List<InferenceResult> argumentTypes = new ArrayList<>();
		for (Expression arg : args) {
			argumentTypes.add(inferTypeDispatch(arg));
		}
		return argumentTypes;
	}

	protected InferenceResult inferReturnType(ArgumentExpression e, Operation operation,
			Map<TypeParameter, InferenceResult> inferredTypeParameterTypes) {
		InferenceResult returnType = inferTypeDispatch(operation);

		// if return type is not generic nor type parameter, we can return it
		// immediately
		if (returnType != null) {
			Type type = returnType.getType();
			if (!(type instanceof TypeParameter)
					&& (!(type instanceof GenericElement) || ((GenericElement) type).getTypeParameters().isEmpty())) {
				return returnType;
			}
		}

		inferByTargetType(e, operation, inferredTypeParameterTypes);

		returnType = typeParameterInferrer.buildInferenceResult(returnType, inferredTypeParameterTypes, acceptor);
		if (returnType == null) {
			return getAnyType();
		}
		return returnType;
	}

	private void inferByTargetType(ArgumentExpression e, Operation operation,
			Map<TypeParameter, InferenceResult> inferredTypeParameterTypes) {
		// use target type inference
		InferenceResult targetType = getTargetType(e);
		if (targetType != null) {
			typeParameterInferrer.inferTypeParametersFromTargetType(targetType, operation, inferredTypeParameterTypes,
					acceptor);
		}
	}

	private InferenceResult getAnyType() {
		return InferenceResult.from(registry.getType(ANY));
	}

	/**
	 * Takes the operation parameter type and performs a lookup for all contained
	 * type parameters by using the given type parameter inference map.<br>
	 * The parameter types are validated against the operation call's argument
	 * types.
	 * 
	 * @throws TypeParameterInferrenceException
	 */
	public Map<TypeParameter, InferenceResult> validateParameters(
			Map<TypeParameter, InferenceResult> typeParameterMapping, Operation operation, List<Expression> args,
			IValidationIssueAcceptor acceptor) {
		List<Parameter> parameters = operation.getParameters();
		for (int i = 0; i < parameters.size(); i++) {
			if (args.size() > i) {
				Parameter parameter = parameters.get(i);
				Expression argument = args.get(i);
				InferenceResult parameterType = inferTypeDispatch(parameter);
				InferenceResult argumentType = inferTypeDispatch(argument);
				parameterType = typeParameterInferrer.buildInferenceResult(parameterType, typeParameterMapping,
						acceptor);
				assertAssignable(parameterType, argumentType,
						String.format(INCOMPATIBLE_TYPES, argumentType, parameterType));
			}
		}
		if (operation.isVariadic() && args.size() - 1 >= operation.getVarArgIndex()) {
			Parameter parameter = operation.getParameters().get(operation.getVarArgIndex());
			List<Expression> varArgs = args.subList(operation.getVarArgIndex(), args.size() - 1);
			InferenceResult parameterType = inferTypeDispatch(parameter);
			for (Expression expression : varArgs) {
				parameterType = typeParameterInferrer.buildInferenceResult(parameterType, typeParameterMapping,
						acceptor);
				InferenceResult argumentType = inferTypeDispatch(expression);
				assertAssignable(parameterType, argumentType,
						String.format(INCOMPATIBLE_TYPES, argumentType, parameterType));
			}
		}
		return typeParameterMapping;
	}

	public InferenceResult doInfer(ParenthesizedExpression e) {
		return inferTypeDispatch(e.getExpression());
	}

	public InferenceResult doInfer(PrimitiveValueExpression e) {
		return inferTypeDispatch(e.getValue());
	}

	public InferenceResult doInfer(BoolLiteral literal) {
		return getResultFor(BOOLEAN);
	}

	public InferenceResult doInfer(IntLiteral literal) {
		return getResultFor(INTEGER);
	}

	public InferenceResult doInfer(HexLiteral literal) {
		return getResultFor(INTEGER);
	}

	public InferenceResult doInfer(DoubleLiteral literal) {
		return getResultFor(REAL);
	}

	public InferenceResult doInfer(FloatLiteral literal) {
		return getResultFor(REAL);
	}

	public InferenceResult doInfer(StringLiteral literal) {
		return getResultFor(STRING);
	}

	public InferenceResult doInfer(NullLiteral literal) {
		return getResultFor(NULL);
	}

	public InferenceResult doInfer(Property e) {
		InferenceResult result = inferTypeDispatch(e.getTypeSpecifier());
		assertNotType(result, VARIABLE_VOID_TYPE, getResultFor(VOID));
		if (e.getInitialValue() == null)
			return result;
		InferenceResult result2 = inferTypeDispatch(e.getInitialValue());
		assertAssignable(result, result2, String.format(PROPERTY_INITIAL_VALUE, result2, result));
		return result;
	}
	
	public InferenceResult doInfer(Event e) {
		// if an event is used within an expression, the type is boolean and the
		// value indicates if the event is raised or not
		return getResultFor(BOOLEAN);
	}

	public InferenceResult doInfer(Operation e) {
		return e.getTypeSpecifier() == null ? getResultFor(VOID) : inferTypeDispatch(e.getTypeSpecifier());
	}

	public InferenceResult doInfer(Parameter e) {
		return inferTypeDispatch(e.getTypeSpecifier());
	}

	public InferenceResult doInfer(TypeSpecifier specifier) {
		if (specifier.getType() instanceof GenericElement
				&& ((GenericElement) specifier.getType()).getTypeParameters().size() > 0) {
			List<InferenceResult> bindings = new ArrayList<>();
			EList<TypeSpecifier> arguments = specifier.getTypeArguments();
			for (TypeSpecifier typeSpecifier : arguments) {
				InferenceResult binding = inferTypeDispatch(typeSpecifier);
				if (binding != null) {
					bindings.add(binding);
				}
			}
			Type type = inferTypeDispatch(specifier.getType()).getType();
			return InferenceResult.from(type, bindings);
		}
		return inferTypeDispatch(specifier.getType());
	}

	public InferenceResult doInfer(Annotation ad) {
		EList<Expression> arguments = ad.getArguments();
		inferAnnotationProperty(ad.getType(), arguments);
		return getResultFor(VOID);
	}

	protected void inferAnnotationProperty(AnnotationType type, EList<Expression> arguments) {
		EList<Property> properties = type.getProperties();
		if (properties.size() == arguments.size()) {
			for (int i = 0; i < properties.size(); i++) {
				InferenceResult type1 = inferTypeDispatch(properties.get(i));
				InferenceResult type2 = inferTypeDispatch(arguments.get(i));
				assertCompatible(type1, type2, String.format(INCOMPATIBLE_TYPES, type1, type2));
			}
		}
	}

}
