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

import org.apache.tapestry.annotations.InjectObject;
import org.trails.page.PageResolver;


/**
 * @author fus8882
 *         <p/>
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public abstract class Link extends TrailsComponent
{
	@InjectObject("service:trails.core.PageResolver")
	public abstract PageResolver getPageResolver();

	public abstract String getTypeName();

	public String getUnqualifiedTypeName()
	{
		return Utils.unqualify(getTypeName());
	}

	//public abstract void setTypeName(String typeName);

}
