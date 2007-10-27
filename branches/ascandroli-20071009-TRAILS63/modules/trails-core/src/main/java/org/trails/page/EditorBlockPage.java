package org.trails.page;

import org.apache.tapestry.annotations.InjectObject;
import org.trails.descriptor.IPropertyDescriptor;
import org.trails.validation.ValidatorTranslatorService;

/**
 * This page contains all the default editor Blocks.  This allows
 * Trails to dynamically pick at runtime which editor to use for a
 * specific property.
 *
 * @author Chris Nelson
 */
public abstract class EditorBlockPage extends ModelPage implements IEditorBlockPage
{

	@InjectObject("service:trails.core.ValidatorTranslatorService")
	public abstract ValidatorTranslatorService getValidatorTranslatorService();

	public abstract IPropertyDescriptor getDescriptor();

	public abstract void setDescriptor(IPropertyDescriptor Descriptor);

	public boolean isModelNew()
	{
		return ((IModelPage) getRequestCycle().getPage()).isModelNew();
	}

	public void setModelNew(boolean modelNew)
	{}
}