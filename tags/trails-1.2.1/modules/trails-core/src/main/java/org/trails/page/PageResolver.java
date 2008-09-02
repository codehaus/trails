package org.trails.page;

import org.apache.tapestry.IPage;
import org.apache.tapestry.IRequestCycle;
import org.trails.page.PageType;

public interface PageResolver
{

	public IPage resolvePage(IRequestCycle cycle, Class type, PageType edit);

}
