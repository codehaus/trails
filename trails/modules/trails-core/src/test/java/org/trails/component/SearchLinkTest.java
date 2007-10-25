package org.trails.component;

import org.apache.tapestry.IRequestCycle;
import org.jmock.Mock;
import org.trails.descriptor.DescriptorService;
import org.trails.page.PageResolver;
import org.trails.page.PageType;
import org.trails.page.SearchPage;
import org.trails.test.Foo;

public class SearchLinkTest extends ComponentTest
{

	public void testClick()
	{
		DescriptorService descriptorService = (DescriptorService) descriptorServiceMock.proxy();

		Mock cycleMock = new Mock(IRequestCycle.class);
		Mock pageResolverMock = new Mock(PageResolver.class);


		SearchPage searchPage = buildTrailsPage(SearchPage.class);
		SearchLink searchLink = (SearchLink) creator.newInstance(SearchLink.class,
			new Object[]{"descriptorService", descriptorServiceMock.proxy(),
				"resourceBundleMessageSource", getNewTrailsMessageSource(),
				"pageResolver", pageResolverMock.proxy()});
		searchLink.setType(Foo.class);

		// Pretend Foo has a custom page
		pageResolverMock.expects(once()).method("resolvePage").with(
			isA(IRequestCycle.class),
			eq(Foo.class),
			eq(PageType.Search)).will(returnValue(searchPage));
		cycleMock.expects(once()).method("activate").with(eq(searchPage));
		searchLink.click((IRequestCycle) cycleMock.proxy());
		assertNotNull(searchPage.getType());
		assertEquals(Foo.class, searchPage.getType());
	}
}
