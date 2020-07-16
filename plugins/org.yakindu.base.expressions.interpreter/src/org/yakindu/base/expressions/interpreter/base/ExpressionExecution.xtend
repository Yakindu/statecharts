package org.yakindu.base.expressions.interpreter.base

import com.google.inject.Inject
import java.util.List
import java.util.Map
import org.eclipse.xtext.naming.IQualifiedNameProvider
import org.yakindu.base.base.NamedElement
import org.yakindu.base.expressions.expressions.Argument
import org.yakindu.base.expressions.expressions.ArgumentExpression
import org.yakindu.base.expressions.expressions.AssignmentExpression
import org.yakindu.base.expressions.expressions.AssignmentOperator
import org.yakindu.base.expressions.expressions.BinaryExpression
import org.yakindu.base.expressions.expressions.BitwiseAndExpression
import org.yakindu.base.expressions.expressions.BitwiseOrExpression
import org.yakindu.base.expressions.expressions.BitwiseXorExpression
import org.yakindu.base.expressions.expressions.BoolLiteral
import org.yakindu.base.expressions.expressions.ConditionalExpression
import org.yakindu.base.expressions.expressions.DoubleLiteral
import org.yakindu.base.expressions.expressions.ElementReferenceExpression
import org.yakindu.base.expressions.expressions.FeatureCall
import org.yakindu.base.expressions.expressions.FloatLiteral
import org.yakindu.base.expressions.expressions.IntLiteral
import org.yakindu.base.expressions.expressions.LogicalAndExpression
import org.yakindu.base.expressions.expressions.LogicalNotExpression
import org.yakindu.base.expressions.expressions.LogicalOrExpression
import org.yakindu.base.expressions.expressions.MetaCall
import org.yakindu.base.expressions.expressions.NullLiteral
import org.yakindu.base.expressions.expressions.NumericalUnaryExpression
import org.yakindu.base.expressions.expressions.ParenthesizedExpression
import org.yakindu.base.expressions.expressions.PostFixUnaryExpression
import org.yakindu.base.expressions.expressions.PrimitiveValueExpression
import org.yakindu.base.expressions.expressions.StringLiteral
import org.yakindu.base.expressions.expressions.TypeCastExpression
import org.yakindu.base.expressions.expressions.util.ArgumentSorter
import org.yakindu.base.expressions.interpreter.CoreFunction
import org.yakindu.base.types.EnumerationType
import org.yakindu.base.types.Enumerator
import org.yakindu.base.types.Expression
import org.yakindu.base.types.Operation
import org.yakindu.base.types.Property
import org.yakindu.base.types.Type
import org.yakindu.base.types.typesystem.GenericTypeSystem
import org.yakindu.base.types.typesystem.ITypeSystem
import org.yakindu.sct.model.sruntime.ExecutionEvent
import org.yakindu.sct.model.sruntime.ExecutionVariable

class ExpressionExecution extends BaseExecution {
	
	protected static Map<AssignmentOperator, String> assignFunctionMap = #{
		AssignmentOperator.MULT_ASSIGN -> "*",
		AssignmentOperator.DIV_ASSIGN -> "/",
		AssignmentOperator.MOD_ASSIGN -> "%",
		AssignmentOperator.ADD_ASSIGN -> "+",
		AssignmentOperator.SUB_ASSIGN -> "-",
		AssignmentOperator.LEFT_SHIFT_ASSIGN -> "<<",
		AssignmentOperator.RIGHT_SHIFT_ASSIGN -> ">>",
		AssignmentOperator.AND_ASSIGN -> "&",
		AssignmentOperator.XOR_ASSIGN -> "^",
		AssignmentOperator.OR_ASSIGN -> "|"
	};
	
	@Inject protected extension CoreFunctionExecution cf 
	@Inject protected extension IQualifiedNameProvider qnp
	@Inject protected extension ITypeSystem ts;
		
	override provideExecution(Object program) {
		program.execution
	}
	
		
	def dispatch void execution(Object it) {
	}


	def dispatch void execution(PrimitiveValueExpression expr) {
		_return(expr.value.valueLiteral.toString, [
			expr.value.valueLiteral
		])
	}
	
	def dispatch void execution(ParenthesizedExpression expr) {
		expr.expression._exec
	}
	

	def dispatch void execution(AssignmentExpression expr) {
		expr.expression._exec
		_value
		expr.varRef._exec
		_return( expr.operator.literal, [ 
			val f = assignFunctionMap.get(expr.operator)
			val varRef = popValue
			var value = popValue

			if ( expr.operator !== AssignmentOperator.ASSIGN) {
				val varValue = varRef.value
				value = evaluate(f, varValue, value)
			}
			
			varRef.setValue = cast(value, (varRef as ExecutionVariable).type)
			
			return value
		])
	}
	
	def dispatch void execution(ElementReferenceExpression expr) {
		expr.reference.execution(expr)		
	}

	def dispatch void execution(FeatureCall expr) {
		expr.feature.execution(expr)
	}

	def dispatch void execution(Object item, Object context){	
		throw new IllegalArgumentException('''Don't know how to execute «item.class.simpleName» in context «context.class.simpleName»''')
	}
	
	def dispatch void execution(Operation item, ArgumentExpression context){	
		item.executeArguments(context.arguments)
		context._call(item)
	}
	
	def dispatch void execution(NamedElement item, ElementReferenceExpression context){	
		_return ('''revolve «item.symbol»''', [
			resolve(null, item.symbol)
		])
	}
	
	def dispatch void execution(NamedElement item, FeatureCall context){	
		context.owner._exec
		_return ('''revolve «item.symbol»''', [
			popValue.resolve(item.symbol)
		])
	}
	
	
	
	def protected void executeArguments( Operation op, List<Argument> args) {
		ArgumentSorter
			.getOrderedExpressions(args, op)
			.forEach[ 
				value._exec 
				_value
			]			
	}
	
	
	def dispatch void execution(MetaCall expr) {
		
		val metaFeature = expr.feature
		if (metaFeature instanceof Property) {
			if (metaFeature.name == "value") {
				expr.owner._exec
				_return[
					val eventSlot = popValue()
					(eventSlot as ExecutionEvent).value
				]
				return		
			}
		} 
		throw new IllegalArgumentException("Cannot resolve meta call '" + expr +"'")
	}

	def dispatch String symbol(Object it) {
		null
	}
	
	def dispatch String symbol(NamedElement it) {
		it.fullyQualifiedName.lastSegment
	}
	
	def dispatch void execution(BinaryExpression it) {
		binaryExpressionExecution(operator.getName, leftOperand, rightOperand)
	}

	protected def void binaryExpressionExecution(String operator, Expression left, Expression right) {
		left._exec  
		_value
		right._exec 
		_value
		_return (operator, [ 
			val rightValue = popValue
			val leftValue = popValue
			
			evaluate(operator, leftValue, rightValue)
		])
	}
	
	protected def void unaryExpressionExecution(String operator, Expression expr) {
		expr._exec  
		_value
		_return (operator, [ 
			val value = popValue
			
			evaluate(operator, value)
		])
	}
	
	
	
	def dispatch void execution(LogicalOrExpression expr) {
		expr.leftOperand._exec
		_value
		_execute ('||', [ 
			if (popValue == true) { 
				_return[true]
			}
			else {
				expr.rightOperand._exec
				_value
				_return[popValue]
			}
		])
	}
	

	def dispatch void execution(LogicalAndExpression expr) {
		expr.leftOperand._exec
		_value
		_execute ('&&', [ 
			if (popValue == false) { 
				_return[false]
			}
			else {
				expr.rightOperand._exec
				_value
				_return[popValue]
			}
		])
	}
	
	def dispatch void execution(LogicalNotExpression it) {
		operand._exec
		_value
		_return [
			popValue == false
		]
	}
	
	
	def dispatch void execution(ConditionalExpression it) {
		condition._exec
		_value
		_execute[
			if (popValue == true) {
				trueCase._exec
			} else  {
				falseCase._exec
			}
		]
	}
	
	
	def dispatch void execution(BitwiseAndExpression it) {
		binaryExpressionExecution(CoreFunction.BIT_AND, leftOperand, rightOperand)
	}
	
	def dispatch void execution(BitwiseOrExpression it) {
		binaryExpressionExecution(CoreFunction.BIT_OR, leftOperand, rightOperand)
	}
	
	def dispatch void execution(BitwiseXorExpression it) {
		binaryExpressionExecution(CoreFunction.BIT_XOR, leftOperand, rightOperand)
	}
	
	def dispatch void execution(NumericalUnaryExpression expression) {
		unaryExpressionExecution(expression.operator.literal, expression.operand)
	}
	
	def dispatch void execution(PostFixUnaryExpression it) {
		operand._exec
		_return( it.operator.literal, [ 
			val varRef = popValue
			val varValue = varRef.value
			val opValue = evaluate(operator.literal, varValue)
			varRef.setValue(opValue)
			varValue
		])
	}	
	
	def dispatch void execution(TypeCastExpression expression) {
		expression.operand._exec
		_value
		_return("cast("+ expression.type.originType.name + ")", [
			val operandValue = popValue
			typeCast(operandValue, expression.type.originType)		
		])
	}


	def Object cast(Object value, Type type) {
		if (type !== null) {
			typeCast(value, type.originType)
		} else {
			value
		}
	}

	def protected dispatch Object typeCast(Long value, Type type) {
		if(type instanceof EnumerationType) return value
		if(ts.isSuperType(type, ts.getType(GenericTypeSystem.INTEGER))) return value
		if(ts.isSuperType(type, ts.getType(GenericTypeSystem.REAL))) return Double.valueOf(value)
		throw new IllegalArgumentException("unknown type " + type.name)
	}

	def protected dispatch Object typeCast(Float value, Type type) {
		if(ts.isSuperType(type, ts.getType(GenericTypeSystem.INTEGER))) return value.longValue
		if(ts.isSuperType(type, ts.getType(GenericTypeSystem.REAL))) return Double.valueOf(value)
		throw new IllegalArgumentException("Invalid cast from Float to " + type.name)
	}

	def protected dispatch Object typeCast(Double value, Type type) {
		if(ts.isSuperType(type, ts.getType(ITypeSystem.INTEGER))) return value.longValue
		if(ts.isSuperType(type, ts.getType(ITypeSystem.REAL))) return Double.valueOf(value)
		throw new IllegalArgumentException("Invalid cast from Double to " + type.name)
	}

	def protected dispatch Object typeCast(Boolean value, Type type) {
		if(ts.isSuperType(type, ts.getType(ITypeSystem.BOOLEAN))) return value
		throw new IllegalArgumentException("Invalid cast from Boolean to " + type.name)
	}

	def protected dispatch Object typeCast(String value, Type type) {
		if(ts.isSuperType(type, ts.getType(ITypeSystem.STRING))) return value
		throw new IllegalArgumentException("Invalid cast from String to " + type.name)
	}

	def protected dispatch Object typeCast(Enumerator value, Type type) {
		if(ts.isSuperType(type, value.owningEnumeration)) return value
		throw new IllegalArgumentException("Invalid cast from Enumerator to " + type.name)
	}

	
		
	def dispatch valueLiteral(IntLiteral literal) {
		return literal.value as long
	}

	def dispatch valueLiteral(BoolLiteral bool) {
		return bool.value
	}

	def dispatch valueLiteral(DoubleLiteral literal) {
		return literal.value
	}

	def dispatch valueLiteral(FloatLiteral literal) {
		return literal.value
	}

	def dispatch valueLiteral(StringLiteral literal) {
		return literal.value
	}

	def dispatch valueLiteral(NullLiteral literal) {
		return null
	}
	
	def void _call(Object caller, Object operation) {
		executionCall(new Invokation(caller, operation))
	}

	def dispatch void executionCall(Object it) {
		throw new UnsupportedOperationException("Don't know how to call operations")		
	}
	
	def dispatch void executionCall(Invokation it) {
		_delegate				
	}
	
}