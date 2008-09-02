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
package org.trails.link;

import org.apache.tapestry.annotations.ComponentClass;
import org.trails.page.PageType;

/**
 * This component displays a link to the EditPage for an object
 */
@ComponentClass
public abstract class EditLink extends ModelLink
{

	public PageType getPageType()
	{
		return PageType.EDIT;
	}

}
