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
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.yakindu.base.types.TypesFactory;
import org.yakindu.base.types.TypesPackage;
import org.yakindu.sct.model.sexec.ExecutionState;
import org.yakindu.sct.model.sexec.SexecFactory;
import org.yakindu.sct.model.sexec.SexecPackage;
import org.yakindu.sct.model.sgraph.SGraphFactory;

/**
 * This is the item provider adapter for a {@link org.yakindu.sct.model.sexec.ExecutionState} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class ExecutionStateItemProvider
	extends ExecutionNodeItemProvider {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ExecutionStateItemProvider(AdapterFactory adapterFactory) {
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

			addSubScopesPropertyDescriptor(object);
			addSuperScopePropertyDescriptor(object);
			addIdPropertyDescriptor(object);
			addAbstractPropertyDescriptor(object);
			addVisiblePropertyDescriptor(object);
			addSuperTypesPropertyDescriptor(object);
			addLeafPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Sub Scopes feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addSubScopesPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_ExecutionScope_subScopes_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_ExecutionScope_subScopes_feature", "_UI_ExecutionScope_type"),
				 SexecPackage.Literals.EXECUTION_SCOPE__SUB_SCOPES,
				 true,
				 false,
				 true,
				 null,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Super Scope feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addSuperScopePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_ExecutionScope_superScope_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_ExecutionScope_superScope_feature", "_UI_ExecutionScope_type"),
				 SexecPackage.Literals.EXECUTION_SCOPE__SUPER_SCOPE,
				 true,
				 false,
				 true,
				 null,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Id feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addIdPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_PackageMember_id_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_PackageMember_id_feature", "_UI_PackageMember_type"),
				 TypesPackage.Literals.PACKAGE_MEMBER__ID,
				 false,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Abstract feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addAbstractPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Type_abstract_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Type_abstract_feature", "_UI_Type_type"),
				 TypesPackage.Literals.TYPE__ABSTRACT,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Visible feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addVisiblePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Type_visible_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Type_visible_feature", "_UI_Type_type"),
				 TypesPackage.Literals.TYPE__VISIBLE,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Super Types feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addSuperTypesPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Type_superTypes_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Type_superTypes_feature", "_UI_Type_type"),
				 TypesPackage.Literals.TYPE__SUPER_TYPES,
				 true,
				 false,
				 true,
				 null,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Leaf feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addLeafPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_ExecutionState_leaf_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_ExecutionState_leaf_feature", "_UI_ExecutionState_type"),
				 SexecPackage.Literals.EXECUTION_STATE__LEAF,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
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
			childrenFeatures.add(SexecPackage.Literals.EXECUTION_SCOPE__STATE_VECTOR);
			childrenFeatures.add(SexecPackage.Literals.EXECUTION_SCOPE__ENTER_SEQUENCES);
			childrenFeatures.add(SexecPackage.Literals.EXECUTION_SCOPE__EXIT_SEQUENCE);
			childrenFeatures.add(SexecPackage.Literals.EXECUTION_SCOPE__INIT_SEQUENCE);
			childrenFeatures.add(TypesPackage.Literals.PACKAGE_MEMBER__ANNOTATIONS);
			childrenFeatures.add(TypesPackage.Literals.TYPE__CONSTRAINT);
			childrenFeatures.add(TypesPackage.Literals.GENERIC_ELEMENT__TYPE_PARAMETERS);
			childrenFeatures.add(TypesPackage.Literals.COMPLEX_TYPE__FEATURES);
			childrenFeatures.add(SexecPackage.Literals.EXECUTION_STATE__ENTRY_ACTION);
			childrenFeatures.add(SexecPackage.Literals.EXECUTION_STATE__EXIT_ACTION);
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
	 * This returns ExecutionState.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/ExecutionState"));
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		String label = ((ExecutionState)object).getName();
		return label == null || label.length() == 0 ?
			getString("_UI_ExecutionState_type") :
			getString("_UI_ExecutionState_type") + " " + label;
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

		switch (notification.getFeatureID(ExecutionState.class)) {
			case SexecPackage.EXECUTION_STATE__ID:
			case SexecPackage.EXECUTION_STATE__ABSTRACT:
			case SexecPackage.EXECUTION_STATE__VISIBLE:
			case SexecPackage.EXECUTION_STATE__LEAF:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
				return;
			case SexecPackage.EXECUTION_STATE__STATE_VECTOR:
			case SexecPackage.EXECUTION_STATE__ENTER_SEQUENCES:
			case SexecPackage.EXECUTION_STATE__EXIT_SEQUENCE:
			case SexecPackage.EXECUTION_STATE__INIT_SEQUENCE:
			case SexecPackage.EXECUTION_STATE__ANNOTATIONS:
			case SexecPackage.EXECUTION_STATE__CONSTRAINT:
			case SexecPackage.EXECUTION_STATE__TYPE_PARAMETERS:
			case SexecPackage.EXECUTION_STATE__FEATURES:
			case SexecPackage.EXECUTION_STATE__ENTRY_ACTION:
			case SexecPackage.EXECUTION_STATE__EXIT_ACTION:
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
				(SexecPackage.Literals.EXECUTION_SCOPE__STATE_VECTOR,
				 SexecFactory.eINSTANCE.createStateVector()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.EXECUTION_SCOPE__ENTER_SEQUENCES,
				 SexecFactory.eINSTANCE.createSequence()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.EXECUTION_SCOPE__EXIT_SEQUENCE,
				 SexecFactory.eINSTANCE.createSequence()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.EXECUTION_SCOPE__INIT_SEQUENCE,
				 SexecFactory.eINSTANCE.createSequence()));

		newChildDescriptors.add
			(createChildParameter
				(TypesPackage.Literals.PACKAGE_MEMBER__ANNOTATIONS,
				 TypesFactory.eINSTANCE.createAnnotation()));

		newChildDescriptors.add
			(createChildParameter
				(TypesPackage.Literals.TYPE__CONSTRAINT,
				 TypesFactory.eINSTANCE.createTypeConstraint()));

		newChildDescriptors.add
			(createChildParameter
				(TypesPackage.Literals.TYPE__CONSTRAINT,
				 TypesFactory.eINSTANCE.createRangeConstraint()));

		newChildDescriptors.add
			(createChildParameter
				(TypesPackage.Literals.GENERIC_ELEMENT__TYPE_PARAMETERS,
				 TypesFactory.eINSTANCE.createTypeParameter()));

		newChildDescriptors.add
			(createChildParameter
				(TypesPackage.Literals.COMPLEX_TYPE__FEATURES,
				 SexecFactory.eINSTANCE.createMethod()));

		newChildDescriptors.add
			(createChildParameter
				(TypesPackage.Literals.COMPLEX_TYPE__FEATURES,
				 SexecFactory.eINSTANCE.createTimeEvent()));

		newChildDescriptors.add
			(createChildParameter
				(TypesPackage.Literals.COMPLEX_TYPE__FEATURES,
				 SGraphFactory.eINSTANCE.createImportDeclaration()));

		newChildDescriptors.add
			(createChildParameter
				(TypesPackage.Literals.COMPLEX_TYPE__FEATURES,
				 TypesFactory.eINSTANCE.createOperation()));

		newChildDescriptors.add
			(createChildParameter
				(TypesPackage.Literals.COMPLEX_TYPE__FEATURES,
				 TypesFactory.eINSTANCE.createProperty()));

		newChildDescriptors.add
			(createChildParameter
				(TypesPackage.Literals.COMPLEX_TYPE__FEATURES,
				 TypesFactory.eINSTANCE.createEvent()));

		newChildDescriptors.add
			(createChildParameter
				(TypesPackage.Literals.COMPLEX_TYPE__FEATURES,
				 TypesFactory.eINSTANCE.createEnumerator()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.EXECUTION_STATE__ENTRY_ACTION,
				 SexecFactory.eINSTANCE.createSequence()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.EXECUTION_STATE__ENTRY_ACTION,
				 SexecFactory.eINSTANCE.createCheck()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.EXECUTION_STATE__ENTRY_ACTION,
				 SexecFactory.eINSTANCE.createCheckRef()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.EXECUTION_STATE__ENTRY_ACTION,
				 SexecFactory.eINSTANCE.createIf()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.EXECUTION_STATE__ENTRY_ACTION,
				 SexecFactory.eINSTANCE.createExecution()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.EXECUTION_STATE__ENTRY_ACTION,
				 SexecFactory.eINSTANCE.createEnterState()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.EXECUTION_STATE__ENTRY_ACTION,
				 SexecFactory.eINSTANCE.createExitState()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.EXECUTION_STATE__ENTRY_ACTION,
				 SexecFactory.eINSTANCE.createCall()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.EXECUTION_STATE__ENTRY_ACTION,
				 SexecFactory.eINSTANCE.createScheduleTimeEvent()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.EXECUTION_STATE__ENTRY_ACTION,
				 SexecFactory.eINSTANCE.createUnscheduleTimeEvent()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.EXECUTION_STATE__ENTRY_ACTION,
				 SexecFactory.eINSTANCE.createStateSwitch()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.EXECUTION_STATE__ENTRY_ACTION,
				 SexecFactory.eINSTANCE.createSaveHistory()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.EXECUTION_STATE__ENTRY_ACTION,
				 SexecFactory.eINSTANCE.createHistoryEntry()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.EXECUTION_STATE__ENTRY_ACTION,
				 SexecFactory.eINSTANCE.createReturn()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.EXECUTION_STATE__ENTRY_ACTION,
				 SexecFactory.eINSTANCE.createTraceNodeExecuted()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.EXECUTION_STATE__ENTRY_ACTION,
				 SexecFactory.eINSTANCE.createReactionFired()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.EXECUTION_STATE__ENTRY_ACTION,
				 SexecFactory.eINSTANCE.createTraceReactionWillFire()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.EXECUTION_STATE__ENTRY_ACTION,
				 SexecFactory.eINSTANCE.createTraceStateEntered()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.EXECUTION_STATE__ENTRY_ACTION,
				 SexecFactory.eINSTANCE.createTraceStateExited()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.EXECUTION_STATE__ENTRY_ACTION,
				 SexecFactory.eINSTANCE.createTraceBeginRunCycle()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.EXECUTION_STATE__ENTRY_ACTION,
				 SexecFactory.eINSTANCE.createTraceEndRunCycle()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.EXECUTION_STATE__EXIT_ACTION,
				 SexecFactory.eINSTANCE.createSequence()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.EXECUTION_STATE__EXIT_ACTION,
				 SexecFactory.eINSTANCE.createCheck()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.EXECUTION_STATE__EXIT_ACTION,
				 SexecFactory.eINSTANCE.createCheckRef()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.EXECUTION_STATE__EXIT_ACTION,
				 SexecFactory.eINSTANCE.createIf()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.EXECUTION_STATE__EXIT_ACTION,
				 SexecFactory.eINSTANCE.createExecution()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.EXECUTION_STATE__EXIT_ACTION,
				 SexecFactory.eINSTANCE.createEnterState()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.EXECUTION_STATE__EXIT_ACTION,
				 SexecFactory.eINSTANCE.createExitState()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.EXECUTION_STATE__EXIT_ACTION,
				 SexecFactory.eINSTANCE.createCall()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.EXECUTION_STATE__EXIT_ACTION,
				 SexecFactory.eINSTANCE.createScheduleTimeEvent()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.EXECUTION_STATE__EXIT_ACTION,
				 SexecFactory.eINSTANCE.createUnscheduleTimeEvent()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.EXECUTION_STATE__EXIT_ACTION,
				 SexecFactory.eINSTANCE.createStateSwitch()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.EXECUTION_STATE__EXIT_ACTION,
				 SexecFactory.eINSTANCE.createSaveHistory()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.EXECUTION_STATE__EXIT_ACTION,
				 SexecFactory.eINSTANCE.createHistoryEntry()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.EXECUTION_STATE__EXIT_ACTION,
				 SexecFactory.eINSTANCE.createReturn()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.EXECUTION_STATE__EXIT_ACTION,
				 SexecFactory.eINSTANCE.createTraceNodeExecuted()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.EXECUTION_STATE__EXIT_ACTION,
				 SexecFactory.eINSTANCE.createReactionFired()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.EXECUTION_STATE__EXIT_ACTION,
				 SexecFactory.eINSTANCE.createTraceReactionWillFire()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.EXECUTION_STATE__EXIT_ACTION,
				 SexecFactory.eINSTANCE.createTraceStateEntered()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.EXECUTION_STATE__EXIT_ACTION,
				 SexecFactory.eINSTANCE.createTraceStateExited()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.EXECUTION_STATE__EXIT_ACTION,
				 SexecFactory.eINSTANCE.createTraceBeginRunCycle()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.EXECUTION_STATE__EXIT_ACTION,
				 SexecFactory.eINSTANCE.createTraceEndRunCycle()));
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
			childFeature == SexecPackage.Literals.EXECUTION_NODE__REACT_SEQUENCE ||
			childFeature == SexecPackage.Literals.EXECUTION_NODE__LOCAL_REACT_SEQUENCE ||
			childFeature == SexecPackage.Literals.EXECUTION_SCOPE__ENTER_SEQUENCES ||
			childFeature == SexecPackage.Literals.EXECUTION_SCOPE__EXIT_SEQUENCE ||
			childFeature == SexecPackage.Literals.EXECUTION_SCOPE__INIT_SEQUENCE ||
			childFeature == SexecPackage.Literals.EXECUTION_STATE__ENTRY_ACTION ||
			childFeature == SexecPackage.Literals.EXECUTION_STATE__EXIT_ACTION;

		if (qualify) {
			return getString
				("_UI_CreateChild_text2",
				 new Object[] { getTypeText(childObject), getFeatureText(childFeature), getTypeText(owner) });
		}
		return super.getCreateChildText(owner, feature, child, selection);
	}

}
