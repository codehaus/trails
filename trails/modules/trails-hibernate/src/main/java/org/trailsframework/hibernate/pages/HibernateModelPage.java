package org.trailsframework.hibernate.pages;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.beaneditor.BeanModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.trailsframework.descriptor.TrailsClassDescriptor;
import org.trailsframework.pages.ModelPage;


public abstract class HibernateModelPage extends ModelPage
{

	private static final Logger LOGGER = LoggerFactory.getLogger(HibernateModelPage.class);

	private TrailsClassDescriptor classDescriptor;

	@Property(write = false)
	private BeanModel beanModel;

	private Object bean;

	protected void activate(Object bean, TrailsClassDescriptor classDescriptor, BeanModel beanModel)
	{
		this.bean = bean;
		this.classDescriptor = classDescriptor;
		this.beanModel = beanModel;
	}

	protected void cleanupRender()
	{
		bean = null;
		classDescriptor = null;
		beanModel = null;
	}

	final void onActivate(Class clazz, String id) throws Exception
	{
		activate(getContextValueEncoder().toValue(clazz, id), getDescriptorService().getClassDescriptor(clazz), createBeanModel(clazz));
	}

	/**
	 * This tells Tapestry to put type & id into the URL, making it bookmarkable.
	 *
	 * @return
	 */
	protected Object[] onPassivate()
	{
		return new Object[]{getClassDescriptor().getType(), getBean()};
	}

	public final TrailsClassDescriptor getClassDescriptor()
	{
		return classDescriptor;
	}

	public final Object getBean()
	{
		return bean;
	}

	public final void setBean(Object bean)
	{
		this.bean = bean;
	}
}
