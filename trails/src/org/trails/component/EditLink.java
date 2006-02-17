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
package org.trails.component;

import java.io.Serializable;

import org.apache.commons.beanutils.PropertyUtils;
import org.trails.TrailsRuntimeException;
import org.apache.tapestry.IRequestCycle;
import org.trails.descriptor.DescriptorService;
import org.trails.descriptor.IClassDescriptor;
import org.trails.page.EditPage;
import org.trails.page.TrailsPage;
import org.trails.persistence.PersistenceService;


/*
 * Created on Sep 23, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

/**
 * @author fus8882
 *
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public abstract class EditLink extends Link
{
    
    public String getTypeName()
    {
        return getModel().getClass().getName();
    }
    
    // so we don't get enhanced
    public void setTypeName() {}
    
    public abstract Object getModel();

    public abstract void setModel(Object model);

	public String getEditPageName()
	{
		return getPageResolver().resolvePage(
				getPage().getRequestCycle(), 
				getModel().getClass().getName(),
				TrailsPage.PageType.EDIT).getPageName();
	}

}
