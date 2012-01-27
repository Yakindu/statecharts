/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.yakindu.sct.model.sexec.provider;


import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;

import org.yakindu.sct.model.sexec.HistoryEntry;
import org.yakindu.sct.model.sexec.SexecFactory;
import org.yakindu.sct.model.sexec.SexecPackage;

/**
 * This is the item provider adapter for a {@link org.yakindu.sct.model.sexec.HistoryEntry} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class HistoryEntryItemProvider
	extends StepItemProvider
	implements
		IEditingDomainItemProvider,
		IStructuredItemContentProvider,
		ITreeItemContentProvider,
		IItemLabelProvider,
		IItemPropertySource {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public HistoryEntryItemProvider(AdapterFactory adapterFactory) {
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

			addDeepPropertyDescriptor(object);
			addRegionPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Deep feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addDeepPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_HistoryEntry_deep_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_HistoryEntry_deep_feature", "_UI_HistoryEntry_type"),
				 SexecPackage.Literals.HISTORY_ENTRY__DEEP,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Region feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addRegionPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_HistoryEntry_region_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_HistoryEntry_region_feature", "_UI_HistoryEntry_type"),
				 SexecPackage.Literals.HISTORY_ENTRY__REGION,
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
			childrenFeatures.add(SexecPackage.Literals.HISTORY_ENTRY__INITIAL_STEP);
			childrenFeatures.add(SexecPackage.Literals.HISTORY_ENTRY__HISTORY_STEP);
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
	 * This returns HistoryEntry.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/HistoryEntry"));
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		String label = ((HistoryEntry)object).getName();
		return label == null || label.length() == 0 ?
			getString("_UI_HistoryEntry_type") :
			getString("_UI_HistoryEntry_type") + " " + label;
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

		switch (notification.getFeatureID(HistoryEntry.class)) {
			case SexecPackage.HISTORY_ENTRY__DEEP:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
				return;
			case SexecPackage.HISTORY_ENTRY__INITIAL_STEP:
			case SexecPackage.HISTORY_ENTRY__HISTORY_STEP:
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
				(SexecPackage.Literals.HISTORY_ENTRY__INITIAL_STEP,
				 SexecFactory.eINSTANCE.createSequence()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.HISTORY_ENTRY__INITIAL_STEP,
				 SexecFactory.eINSTANCE.createCheck()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.HISTORY_ENTRY__INITIAL_STEP,
				 SexecFactory.eINSTANCE.createCheckRef()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.HISTORY_ENTRY__INITIAL_STEP,
				 SexecFactory.eINSTANCE.createIf()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.HISTORY_ENTRY__INITIAL_STEP,
				 SexecFactory.eINSTANCE.createExecution()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.HISTORY_ENTRY__INITIAL_STEP,
				 SexecFactory.eINSTANCE.createEnterState()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.HISTORY_ENTRY__INITIAL_STEP,
				 SexecFactory.eINSTANCE.createExitState()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.HISTORY_ENTRY__INITIAL_STEP,
				 SexecFactory.eINSTANCE.createCall()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.HISTORY_ENTRY__INITIAL_STEP,
				 SexecFactory.eINSTANCE.createScheduleTimeEvent()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.HISTORY_ENTRY__INITIAL_STEP,
				 SexecFactory.eINSTANCE.createUnscheduleTimeEvent()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.HISTORY_ENTRY__INITIAL_STEP,
				 SexecFactory.eINSTANCE.createStateSwitch()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.HISTORY_ENTRY__INITIAL_STEP,
				 SexecFactory.eINSTANCE.createTraceNodeExecuted()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.HISTORY_ENTRY__INITIAL_STEP,
				 SexecFactory.eINSTANCE.createReactionFired()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.HISTORY_ENTRY__INITIAL_STEP,
				 SexecFactory.eINSTANCE.createTraceStateEntered()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.HISTORY_ENTRY__INITIAL_STEP,
				 SexecFactory.eINSTANCE.createTraceStateExited()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.HISTORY_ENTRY__INITIAL_STEP,
				 SexecFactory.eINSTANCE.createTraceBeginRunCycle()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.HISTORY_ENTRY__INITIAL_STEP,
				 SexecFactory.eINSTANCE.createTraceEndRunCycle()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.HISTORY_ENTRY__INITIAL_STEP,
				 SexecFactory.eINSTANCE.createSaveHistory()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.HISTORY_ENTRY__INITIAL_STEP,
				 SexecFactory.eINSTANCE.createHistoryEntry()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.HISTORY_ENTRY__HISTORY_STEP,
				 SexecFactory.eINSTANCE.createSequence()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.HISTORY_ENTRY__HISTORY_STEP,
				 SexecFactory.eINSTANCE.createCheck()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.HISTORY_ENTRY__HISTORY_STEP,
				 SexecFactory.eINSTANCE.createCheckRef()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.HISTORY_ENTRY__HISTORY_STEP,
				 SexecFactory.eINSTANCE.createIf()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.HISTORY_ENTRY__HISTORY_STEP,
				 SexecFactory.eINSTANCE.createExecution()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.HISTORY_ENTRY__HISTORY_STEP,
				 SexecFactory.eINSTANCE.createEnterState()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.HISTORY_ENTRY__HISTORY_STEP,
				 SexecFactory.eINSTANCE.createExitState()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.HISTORY_ENTRY__HISTORY_STEP,
				 SexecFactory.eINSTANCE.createCall()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.HISTORY_ENTRY__HISTORY_STEP,
				 SexecFactory.eINSTANCE.createScheduleTimeEvent()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.HISTORY_ENTRY__HISTORY_STEP,
				 SexecFactory.eINSTANCE.createUnscheduleTimeEvent()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.HISTORY_ENTRY__HISTORY_STEP,
				 SexecFactory.eINSTANCE.createStateSwitch()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.HISTORY_ENTRY__HISTORY_STEP,
				 SexecFactory.eINSTANCE.createTraceNodeExecuted()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.HISTORY_ENTRY__HISTORY_STEP,
				 SexecFactory.eINSTANCE.createReactionFired()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.HISTORY_ENTRY__HISTORY_STEP,
				 SexecFactory.eINSTANCE.createTraceStateEntered()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.HISTORY_ENTRY__HISTORY_STEP,
				 SexecFactory.eINSTANCE.createTraceStateExited()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.HISTORY_ENTRY__HISTORY_STEP,
				 SexecFactory.eINSTANCE.createTraceBeginRunCycle()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.HISTORY_ENTRY__HISTORY_STEP,
				 SexecFactory.eINSTANCE.createTraceEndRunCycle()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.HISTORY_ENTRY__HISTORY_STEP,
				 SexecFactory.eINSTANCE.createSaveHistory()));

		newChildDescriptors.add
			(createChildParameter
				(SexecPackage.Literals.HISTORY_ENTRY__HISTORY_STEP,
				 SexecFactory.eINSTANCE.createHistoryEntry()));
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
			childFeature == SexecPackage.Literals.HISTORY_ENTRY__INITIAL_STEP ||
			childFeature == SexecPackage.Literals.HISTORY_ENTRY__HISTORY_STEP;

		if (qualify) {
			return getString
				("_UI_CreateChild_text2",
				 new Object[] { getTypeText(childObject), getFeatureText(childFeature), getTypeText(owner) });
		}
		return super.getCreateChildText(owner, feature, child, selection);
	}

}
