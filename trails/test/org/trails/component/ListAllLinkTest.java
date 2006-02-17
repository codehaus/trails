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

import java.beans.Introspector;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.tapestry.IPage;
import org.apache.tapestry.IRequestCycle;

import org.jmock.cglib.Mock;
import org.jmock.cglib.MockObjectTestCase;

import org.springframework.context.support.ResourceBundleMessageSource;
import org.trails.descriptor.IClassDescriptor;
import org.trails.descriptor.TrailsClassDescriptor;
import org.trails.descriptor.DescriptorService;
import org.trails.i18n.DefaultTrailsResourceBundleMessageSource;
import org.trails.page.ListPage;
import org.trails.page.PageResolver;
import org.trails.page.TrailsPage;
import org.trails.persistence.PersistenceService;
import org.trails.test.BlogEntry;
import org.trails.test.Foo;


/**
 * @author fus8882
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ListAllLinkTest extends ComponentTest
{
    ListAllLink listLink;
    public void setUp()
    {
        
        DescriptorService descriptorService = (DescriptorService)descriptorServiceMock.proxy();
        DefaultTrailsResourceBundleMessageSource messageSource = new DefaultTrailsResourceBundleMessageSource();
        ResourceBundleMessageSource springMessageSource = new ResourceBundleMessageSource();
        springMessageSource.setBasename("messages");
        messageSource.setMessageSource(springMessageSource);
        listLink = (ListAllLink) creator.newInstance(ListAllLink.class, 
                new Object[] {"descriptorService", descriptorService,
        					  "resourceBundleMessageSource", messageSource});
        listLink.setTypeName(Foo.class.getName());
        
    }
    
    public void testGetLinkText() throws Exception
    {
 
        //listLink.setDescriptorService(descriptorService);
        IClassDescriptor descriptor = new TrailsClassDescriptor(BlogEntry.class, "BlogEntry");
        descriptorServiceMock.expects(once()).method("getClassDescriptor").will(returnValue(descriptor));
        assertEquals("List Blog Entries", listLink.getLinkText());
        
    }

	public void testGetPageName()
	{
		Mock pageMock = new Mock(IPage.class);
		Mock pageResolverMock = new Mock(PageResolver.class);
		Mock cycleMock = new Mock(IRequestCycle.class);
		pageResolverMock.expects(once()).method("resolvePage")
			.with(isA(IRequestCycle.class), eq(Foo.class.getName()), eq(TrailsPage.PageType.LIST))
			.will(returnValue(pageMock.proxy()));
		pageMock.expects(once()).method("getPageName").will(returnValue("FooList"));
		pageMock.expects(once()).method("getRequestCycle").will(returnValue(cycleMock.proxy()));
		ListAllLink listAllLink = (ListAllLink)creator.newInstance(ListAllLink.class, new Object[] {"pageResolver", pageResolverMock.proxy()});
		listAllLink.setPage((IPage)pageMock.proxy());
		listAllLink.setTypeName(Foo.class.getName());
		assertEquals("FooList", listAllLink.getListPageName());
	}
}
