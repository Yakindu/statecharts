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
package org.yakindu.sct.model.sexec.provider;


import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.yakindu.base.expressions.expressions.ExpressionsFactory;
import org.yakindu.base.types.TypesFactory;
import org.yakindu.sct.model.sexec.ScheduleTimeEvent;
import org.yakindu.sct.model.sexec.SexecPackage;

/**
 * This is the item provider adapter for a {@link org.yakindu.sct.model.sexec.ScheduleTimeEvent} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class ScheduleTimeEventItemProvider
	extends StepItemProvider {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ScheduleTimeEventItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * This returns the property descriptors for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
		if (itemPropertyDescriptors == null) {
			super.getPropertyDescriptors(object);

			addTimeEventPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Time Event feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addTimeEventPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_ScheduleTimeEvent_timeEvent_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_ScheduleTimeEvent_timeEvent_feature", "_UI_ScheduleTimeEvent_type"),
				 SexecPackage.Literals.SCHEDULE_TIME_EVENT__TIME_EVENT,
				 true,
				 false,
				 true,
				 null,
				 null,
				 null));
	}

	/**
	 * This specifies how to implement {@link #getChildren} and is used to deduce an appropriate feature for an
	 * {@link org.eclipse.emf.edit.command.AddCommand}, {@link org.eclipse.emf.edit.command.RemoveCommand} or
	 * {@link org.eclipse.emf.edit.command.MoveCommand} in {@link #createCommand}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Collection<? extends EStructuralFeature> getChildrenFeatures(Object object) {
		if (childrenFeatures == null) {
			super.getChildrenFeatures(object);
			childrenFeatures.add(SexecPackage.Literals.SCHEDULE_TIME_EVENT__TIME_VALUE);
		}
		return childrenFeatures;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EStructuralFeature getChildFeature(Object object, Object child) {
		// Check the type of the specified child object and return the proper feature to use for
		// adding (see {@link AddCommand}) it as a child.

		return super.getChildFeature(object, child);
	}

	/**
	 * This returns ScheduleTimeEvent.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/ScheduleTimeEvent"));
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		String label = ((ScheduleTimeEvent)object).getName();
		return label == null || label.length() == 0 ?
			getString("_UI_ScheduleTimeEvent_type") :
			getString("_UI_ScheduleTimeEvent_type") + " " + label;
	}

	/**
	 * This handles model notifications by calling {@link #updateChildren} to update any cached
	 * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void notifyChanged(Notification notification) {
		updateChildren(notification);

		switch (notification.getFeatureID(ScheduleTimeEvent.class)) {
			case SexecPackage.SCHEDULE_TIME_EVENT__TIME_VALUE:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
				return;
		}
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
	protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.SCHEDULE_TIME_EVENT__TIME_VALUE,
				 ExpressionsFactory.eINSTANCE.createAssignmentExpression()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.SCHEDULE_TIME_EVENT__TIME_VALUE,
				 ExpressionsFactory.eINSTANCE.createConditionalExpression()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.SCHEDULE_TIME_EVENT__TIME_VALUE,
				 ExpressionsFactory.eINSTANCE.createLogicalOrExpression()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.SCHEDULE_TIME_EVENT__TIME_VALUE,
				 ExpressionsFactory.eINSTANCE.createLogicalAndExpression()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.SCHEDULE_TIME_EVENT__TIME_VALUE,
				 ExpressionsFactory.eINSTANCE.createLogicalNotExpression()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.SCHEDULE_TIME_EVENT__TIME_VALUE,
				 ExpressionsFactory.eINSTANCE.createBitwiseXorExpression()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.SCHEDULE_TIME_EVENT__TIME_VALUE,
				 ExpressionsFactory.eINSTANCE.createBitwiseOrExpression()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.SCHEDULE_TIME_EVENT__TIME_VALUE,
				 ExpressionsFactory.eINSTANCE.createBitwiseAndExpression()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.SCHEDULE_TIME_EVENT__TIME_VALUE,
				 ExpressionsFactory.eINSTANCE.createLogicalRelationExpression()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.SCHEDULE_TIME_EVENT__TIME_VALUE,
				 ExpressionsFactory.eINSTANCE.createShiftExpression()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.SCHEDULE_TIME_EVENT__TIME_VALUE,
				 ExpressionsFactory.eINSTANCE.createNumericalAddSubtractExpression()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.SCHEDULE_TIME_EVENT__TIME_VALUE,
				 ExpressionsFactory.eINSTANCE.createNumericalMultiplyDivideExpression()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.SCHEDULE_TIME_EVENT__TIME_VALUE,
				 ExpressionsFactory.eINSTANCE.createNumericalUnaryExpression()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.SCHEDULE_TIME_EVENT__TIME_VALUE,
				 ExpressionsFactory.eINSTANCE.createPostFixUnaryExpression()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.SCHEDULE_TIME_EVENT__TIME_VALUE,
				 ExpressionsFactory.eINSTANCE.createPrimitiveValueExpression()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.SCHEDULE_TIME_EVENT__TIME_VALUE,
				 ExpressionsFactory.eINSTANCE.createFeatureCall()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.SCHEDULE_TIME_EVENT__TIME_VALUE,
				 ExpressionsFactory.eINSTANCE.createMetaCall()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.SCHEDULE_TIME_EVENT__TIME_VALUE,
				 ExpressionsFactory.eINSTANCE.createElementReferenceExpression()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.SCHEDULE_TIME_EVENT__TIME_VALUE,
				 ExpressionsFactory.eINSTANCE.createParenthesizedExpression()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.SCHEDULE_TIME_EVENT__TIME_VALUE,
				 ExpressionsFactory.eINSTANCE.createTypeCastExpression()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.SCHEDULE_TIME_EVENT__TIME_VALUE,
				 ExpressionsFactory.eINSTANCE.createIfExpression()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.SCHEDULE_TIME_EVENT__TIME_VALUE,
				 ExpressionsFactory.eINSTANCE.createBlockExpression()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.SCHEDULE_TIME_EVENT__TIME_VALUE,
				 ExpressionsFactory.eINSTANCE.createWhileExpression()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.SCHEDULE_TIME_EVENT__TIME_VALUE,
				 ExpressionsFactory.eINSTANCE.createReturnExpression()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.SCHEDULE_TIME_EVENT__TIME_VALUE,
				 ExpressionsFactory.eINSTANCE.createSwitchExpression()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.SCHEDULE_TIME_EVENT__TIME_VALUE,
				 ExpressionsFactory.eINSTANCE.createEventRaisingExpression()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.SCHEDULE_TIME_EVENT__TIME_VALUE,
				 ExpressionsFactory.eINSTANCE.createEventValueReferenceExpression()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.SCHEDULE_TIME_EVENT__TIME_VALUE,
				 ExpressionsFactory.eINSTANCE.createForExpression()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.SCHEDULE_TIME_EVENT__TIME_VALUE,
				 ExpressionsFactory.eINSTANCE.createForVarDecl()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.SCHEDULE_TIME_EVENT__TIME_VALUE,
				 ExpressionsFactory.eINSTANCE.createThrowExpression()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.SCHEDULE_TIME_EVENT__TIME_VALUE,
				 TypesFactory.eINSTANCE.createProperty()));
	}

}
