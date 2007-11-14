/*
 * Created on Jan 30, 2005
 *
 * Copyright 2004 Chris Nelson
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, 
 * software distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License.
 */
package org.trails.component;

import org.apache.tapestry.annotations.InjectObject;
import org.apache.tapestry.annotations.Parameter;
import org.trails.descriptor.DescriptorService;
import org.trails.descriptor.IClassDescriptor;
import org.trails.i18n.TrailsMessageSource;

/**
 * Common functionality for ListAllLink, NewLink, SearchLink
 *
 * @author fus8882
 */
public abstract class AbstractTypeNavigationLink extends Link
{
	@InjectObject("service:trails.core.DescriptorService")
	public abstract DescriptorService getDescriptorService();

	/**
	 * @return Class object that this link targets.
	 */
	@Parameter(required = true)
	public abstract Class getType();

	public abstract void setType(Class type);

	/**
	 * @return the class descriptor for the class that this link targets
	 */
	public IClassDescriptor getClassDescriptor()
	{
		return getDescriptorService().getClassDescriptor(getType());
	}

	protected String generateLinkText(String displayName, String bundleKey, String defaultMessage)
	{
		Object[] params = new Object[]{displayName};
		TrailsMessageSource messageSource = getResourceBundleMessageSource();
		return messageSource.getMessageWithDefaultValue(bundleKey, params, defaultMessage);
	}
}
