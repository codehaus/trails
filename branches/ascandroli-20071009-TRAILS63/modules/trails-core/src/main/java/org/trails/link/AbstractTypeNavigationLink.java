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
package org.trails.link;

import org.apache.tapestry.BaseComponent;
import org.apache.tapestry.IAsset;
import org.apache.tapestry.IComponent;
import org.apache.tapestry.annotations.*;
import org.trails.descriptor.DescriptorService;
import org.trails.descriptor.IClassDescriptor;
import org.trails.page.PageType;

/**
 * Common functionality for ListAllLink, NewLink, SearchLink
 */
@ComponentClass
public abstract class AbstractTypeNavigationLink extends BaseComponent
{
	@InjectObject("spring:descriptorService")
	public abstract DescriptorService getDescriptorService();

	/**
	 * @return Class object that this link targets.
	 */
	@Parameter(required = true)
	public abstract Class getType();

	/**
	 * @return the class descriptor for the class that this link targets
	 */
	public IClassDescriptor getClassDescriptor()
	{
		return getDescriptorService().getClassDescriptor(getType());
	}

	@Asset(value = "/org/trails/link/AbstractTypeNavigationLink.html")
	public abstract IAsset get$template();

	@Parameter(required = true)
	public abstract PageType getPageType();

	@Parameter(required = true)
	public abstract String getBundleKey();

	@Parameter(required = true)
	public abstract String getDefaultMessage();


	@Parameter(required = true)
	public abstract Object getParams();

	@Component(id = "trailsLink", type = "TrailsLink", inheritInformalParameters = true,
			bindings = {"classDescriptor=ognl:classDescriptor", "pageType=ognl:pageType"})
	public abstract IComponent getTrailsLink();

}
