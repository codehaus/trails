package org.trails.callback;

import org.hibernate.criterion.DetachedCriteria;
import org.trails.page.HibernateListPage;
import org.trails.page.ListPage;

public class HibernateListCallback extends ListCallback
{


	private DetachedCriteria criteria;

	public HibernateListCallback(String pageName, Class clazz, DetachedCriteria criteria)
	{
		super(pageName, clazz);
		this.criteria = criteria;
	}

	protected void preparePageForCycleActivate(ListPage listPage)
	{
		super.preparePageForCycleActivate(listPage);
		((HibernateListPage) listPage).setCriteria(criteria);
	}
}
