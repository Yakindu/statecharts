/** 
 * Copyright (c) 2015 committers of YAKINDU and others. 
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0 
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/epl-v10.html 
 * Contributors:
 * committers of YAKINDU - initial API and implementation
 *
*/
package org.yakindu.sct.model.stext.stext.provider;


import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.yakindu.base.expressions.expressions.ExpressionsFactory;
import org.yakindu.base.types.TypesPackage;
import org.yakindu.base.types.provider.OperationItemProvider;
import org.yakindu.sct.model.sgraph.SGraphFactory;
import org.yakindu.sct.model.stext.stext.OperationDefinition;
import org.yakindu.sct.model.stext.stext.StextFactory;

/**
 * This is the item provider adapter for a {@link org.yakindu.sct.model.stext.stext.OperationDefinition} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class OperationDefinitionItemProvider extends OperationItemProvider
{
  /**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public OperationDefinitionItemProvider(AdapterFactory adapterFactory)
  {
		super(adapterFactory);
	}

  /**
	 * This returns the property descriptors for the adapted class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  @Override
  public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object)
  {
		if (itemPropertyDescriptors == null) {
			super.getPropertyDescriptors(object);

		}
		return itemPropertyDescriptors;
	}

  /**
	 * This returns OperationDefinition.gif.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  @Override
  public Object getImage(Object object)
  {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/OperationDefinition"));
	}

  /**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated Not
	 */
	@Override
	public String getText(Object object) {
		return super.getText(object);
	}

		/**
	 * This handles model notifications by calling {@link #updateChildren} to update any cached
	 * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  @Override
  public void notifyChanged(Notification notification)
  {
		updateChildren(notification);
		super.notifyChanged(notification);
	}

  /**
	 * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s describing the children
	 * that can be created under this object.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  @Override
  protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object)
  {
		super.collectNewChildDescriptors(newChildDescriptors, object);

		newChildDescriptors.add
			(createChildParameter
				(TypesPackage.Literals.ANNOTATABLE_ELEMENT__ANNOTATION_INFO,
				 StextFactory.eINSTANCE.createStatechartSpecification()));

		newChildDescriptors.add
			(createChildParameter
				(TypesPackage.Literals.ANNOTATABLE_ELEMENT__ANNOTATION_INFO,
				 StextFactory.eINSTANCE.createEventDefinition()));

		newChildDescriptors.add
			(createChildParameter
				(TypesPackage.Literals.ANNOTATABLE_ELEMENT__ANNOTATION_INFO,
				 StextFactory.eINSTANCE.createVariableDefinition()));

		newChildDescriptors.add
			(createChildParameter
				(TypesPackage.Literals.ANNOTATABLE_ELEMENT__ANNOTATION_INFO,
				 StextFactory.eINSTANCE.createOperationDefinition()));

		newChildDescriptors.add
			(createChildParameter
				(TypesPackage.Literals.ANNOTATABLE_ELEMENT__ANNOTATION_INFO,
				 StextFactory.eINSTANCE.createTypeAliasDefinition()));

		newChildDescriptors.add
			(createChildParameter
				(TypesPackage.Literals.ANNOTATABLE_ELEMENT__ANNOTATION_INFO,
				 SGraphFactory.eINSTANCE.createStatechart()));

		newChildDescriptors.add
			(createChildParameter
				(TypesPackage.Literals.ANNOTATABLE_ELEMENT__ANNOTATION_INFO,
				 SGraphFactory.eINSTANCE.createImportDeclaration()));

		newChildDescriptors.add
			(createChildParameter
				(TypesPackage.Literals.META_COMPOSITE__META_FEATURES,
				 StextFactory.eINSTANCE.createEventDefinition()));

		newChildDescriptors.add
			(createChildParameter
				(TypesPackage.Literals.META_COMPOSITE__META_FEATURES,
				 StextFactory.eINSTANCE.createVariableDefinition()));

		newChildDescriptors.add
			(createChildParameter
				(TypesPackage.Literals.META_COMPOSITE__META_FEATURES,
				 StextFactory.eINSTANCE.createOperationDefinition()));

		newChildDescriptors.add
			(createChildParameter
				(TypesPackage.Literals.META_COMPOSITE__META_FEATURES,
				 StextFactory.eINSTANCE.createTypeAliasDefinition()));

		newChildDescriptors.add
			(createChildParameter
				(TypesPackage.Literals.META_COMPOSITE__META_FEATURES,
				 SGraphFactory.eINSTANCE.createImportDeclaration()));

		newChildDescriptors.add
			(createChildParameter
				(TypesPackage.Literals.OPERATION__BODY,
				 StextFactory.eINSTANCE.createVariableDefinition()));

		newChildDescriptors.add
			(createChildParameter
				(TypesPackage.Literals.OPERATION__BODY,
				 StextFactory.eINSTANCE.createEventValueReferenceExpression()));

		newChildDescriptors.add
			(createChildParameter
				(TypesPackage.Literals.OPERATION__BODY,
				 StextFactory.eINSTANCE.createActiveStateReferenceExpression()));

		newChildDescriptors.add
			(createChildParameter
				(TypesPackage.Literals.OPERATION__BODY,
				 ExpressionsFactory.eINSTANCE.createAssignmentExpression()));

		newChildDescriptors.add
			(createChildParameter
				(TypesPackage.Literals.OPERATION__BODY,
				 ExpressionsFactory.eINSTANCE.createConditionalExpression()));

		newChildDescriptors.add
			(createChildParameter
				(TypesPackage.Literals.OPERATION__BODY,
				 ExpressionsFactory.eINSTANCE.createLogicalOrExpression()));

		newChildDescriptors.add
			(createChildParameter
				(TypesPackage.Literals.OPERATION__BODY,
				 ExpressionsFactory.eINSTANCE.createLogicalAndExpression()));

		newChildDescriptors.add
			(createChildParameter
				(TypesPackage.Literals.OPERATION__BODY,
				 ExpressionsFactory.eINSTANCE.createLogicalNotExpression()));

		newChildDescriptors.add
			(createChildParameter
				(TypesPackage.Literals.OPERATION__BODY,
				 ExpressionsFactory.eINSTANCE.createBitwiseXorExpression()));

		newChildDescriptors.add
			(createChildParameter
				(TypesPackage.Literals.OPERATION__BODY,
				 ExpressionsFactory.eINSTANCE.createBitwiseOrExpression()));

		newChildDescriptors.add
			(createChildParameter
				(TypesPackage.Literals.OPERATION__BODY,
				 ExpressionsFactory.eINSTANCE.createBitwiseAndExpression()));

		newChildDescriptors.add
			(createChildParameter
				(TypesPackage.Literals.OPERATION__BODY,
				 ExpressionsFactory.eINSTANCE.createLogicalRelationExpression()));

		newChildDescriptors.add
			(createChildParameter
				(TypesPackage.Literals.OPERATION__BODY,
				 ExpressionsFactory.eINSTANCE.createShiftExpression()));

		newChildDescriptors.add
			(createChildParameter
				(TypesPackage.Literals.OPERATION__BODY,
				 ExpressionsFactory.eINSTANCE.createNumericalAddSubtractExpression()));

		newChildDescriptors.add
			(createChildParameter
				(TypesPackage.Literals.OPERATION__BODY,
				 ExpressionsFactory.eINSTANCE.createNumericalMultiplyDivideExpression()));

		newChildDescriptors.add
			(createChildParameter
				(TypesPackage.Literals.OPERATION__BODY,
				 ExpressionsFactory.eINSTANCE.createNumericalUnaryExpression()));

		newChildDescriptors.add
			(createChildParameter
				(TypesPackage.Literals.OPERATION__BODY,
				 ExpressionsFactory.eINSTANCE.createPostFixUnaryExpression()));

		newChildDescriptors.add
			(createChildParameter
				(TypesPackage.Literals.OPERATION__BODY,
				 ExpressionsFactory.eINSTANCE.createPrimitiveValueExpression()));

		newChildDescriptors.add
			(createChildParameter
				(TypesPackage.Literals.OPERATION__BODY,
				 ExpressionsFactory.eINSTANCE.createFeatureCall()));

		newChildDescriptors.add
			(createChildParameter
				(TypesPackage.Literals.OPERATION__BODY,
				 ExpressionsFactory.eINSTANCE.createMetaCall()));

		newChildDescriptors.add
			(createChildParameter
				(TypesPackage.Literals.OPERATION__BODY,
				 ExpressionsFactory.eINSTANCE.createElementReferenceExpression()));

		newChildDescriptors.add
			(createChildParameter
				(TypesPackage.Literals.OPERATION__BODY,
				 ExpressionsFactory.eINSTANCE.createParenthesizedExpression()));

		newChildDescriptors.add
			(createChildParameter
				(TypesPackage.Literals.OPERATION__BODY,
				 ExpressionsFactory.eINSTANCE.createTypeCastExpression()));

		newChildDescriptors.add
			(createChildParameter
				(TypesPackage.Literals.OPERATION__BODY,
				 ExpressionsFactory.eINSTANCE.createIfExpression()));

		newChildDescriptors.add
			(createChildParameter
				(TypesPackage.Literals.OPERATION__BODY,
				 ExpressionsFactory.eINSTANCE.createBlockExpression()));

		newChildDescriptors.add
			(createChildParameter
				(TypesPackage.Literals.OPERATION__BODY,
				 ExpressionsFactory.eINSTANCE.createWhileExpression()));

		newChildDescriptors.add
			(createChildParameter
				(TypesPackage.Literals.OPERATION__BODY,
				 ExpressionsFactory.eINSTANCE.createReturnExpression()));

		newChildDescriptors.add
			(createChildParameter
				(TypesPackage.Literals.OPERATION__BODY,
				 ExpressionsFactory.eINSTANCE.createSwitchExpression()));

		newChildDescriptors.add
			(createChildParameter
				(TypesPackage.Literals.OPERATION__BODY,
				 ExpressionsFactory.eINSTANCE.createSwitchCase()));

		newChildDescriptors.add
			(createChildParameter
				(TypesPackage.Literals.OPERATION__BODY,
				 ExpressionsFactory.eINSTANCE.createEventRaisingExpression()));

		newChildDescriptors.add
			(createChildParameter
				(TypesPackage.Literals.OPERATION__BODY,
				 ExpressionsFactory.eINSTANCE.createEventValueReferenceExpression()));
	}

  /**
	 * This returns the label text for {@link org.eclipse.emf.edit.command.CreateChildCommand}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getCreateChildText(Object owner, Object feature, Object child, Collection<?> selection) {
		Object childFeature = feature;
		Object childObject = child;

		boolean qualify =
			childFeature == TypesPackage.Literals.ANNOTATABLE_ELEMENT__ANNOTATION_INFO ||
			childFeature == TypesPackage.Literals.META_COMPOSITE__META_FEATURES ||
			childFeature == TypesPackage.Literals.OPERATION__BODY ||
			childFeature == TypesPackage.Literals.OPERATION__PARAMETERS ||
			childFeature == TypesPackage.Literals.GENERIC_ELEMENT__TYPE_PARAMETERS;

		if (qualify) {
			return getString
				("_UI_CreateChild_text2",
				 new Object[] { getTypeText(childObject), getFeatureText(childFeature), getTypeText(owner) });
		}
		return super.getCreateChildText(owner, feature, child, selection);
	}

		/**
	 * Return the resource locator for this item provider's resources.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  @Override
  public ResourceLocator getResourceLocator()
  {
		return STextEditPlugin.INSTANCE;
	}

}
