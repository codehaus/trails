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

import org.trails.descriptor.IPropertyDescriptor;
import org.trails.descriptor.IdentifierDescriptor;
import org.trails.hibernate.HasAssignedIdentifier;


/**
 * @author fus8882
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public abstract class Identifier extends PropertyEditor
{
    public abstract IPropertyDescriptor getDescriptor();

    public abstract void setDescriptor(IPropertyDescriptor Descriptor);

    public abstract Object getModel();

    public abstract void setModel(Object Model);

    /**
     * @return
     */
    public boolean isEditable()
    {
        if (!((IdentifierDescriptor)getDescriptor()).isGenerated())
        {
            return !((HasAssignedIdentifier)getModel()).isSaved();
        }
        else return false;
    }
}
