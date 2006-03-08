/*
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
package org.trails.page;

import java.util.List;

import org.apache.tapestry.IExternalPage;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.annotations.Persist;
import org.apache.tapestry.event.PageBeginRenderListener;
import org.apache.tapestry.event.PageEvent;
import org.hibernate.criterion.DetachedCriteria;
import org.trails.TrailsRuntimeException;
import org.trails.callback.ListCallback;
import org.trails.descriptor.IClassDescriptor;


/**
 * List all the instances of a type
 * 
 * @author Chris Nelson
 * 
 */
public abstract class ListPage extends TrailsPage implements IExternalPage, PageBeginRenderListener
{

	
    @Override
	public void pageBeginRender(PageEvent event)
	{
    	super.pageBeginRender(event);
    	if (!getRequestCycle().isRewinding())
    	{
    		setInstances(getPersistenceService().getInstances(getCriteria()));
    	}
	}

	/* (non-Javadoc)
     * @see org.apache.tapestry.IExternalPage#activateExternalPage(java.lang.Object[], org.apache.tapestry.IRequestCycle)
     */
    public void activateExternalPage(Object[] args, IRequestCycle cycle)
    {
        Class instanceClass = (Class) args[0];
        setTypeName(instanceClass.getName());
        setCriteria(DetachedCriteria.forClass(instanceClass));
    }

    public abstract List getInstances();

	public abstract void setInstances(List Instances);
	
	@Persist
    public abstract DetachedCriteria getCriteria();

	public abstract void setCriteria(DetachedCriteria Criteria);
	
    public abstract String getTypeName();

    public abstract void setTypeName(String typeName);

    public IClassDescriptor getClassDescriptor()
    {
        try
        {
            return getDescriptorService().getClassDescriptor(Class.forName(
                    getTypeName()));
        }catch (ClassNotFoundException e)
        {
            throw new TrailsRuntimeException(e);
        }
    }

    /**
     * 
     */
    public void pushCallback()
    {
        
        getCallbackStack().push(new ListCallback(getPageName(), getTypeName(), getCriteria()));
    }
}
