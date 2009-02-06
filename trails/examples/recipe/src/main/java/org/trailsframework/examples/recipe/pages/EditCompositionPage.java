package org.trailsframework.examples.recipe.pages;


import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.beaneditor.BeanModel;
import org.apache.tapestry5.corelib.components.BeanEditForm;
import org.apache.tapestry5.corelib.components.PageLink;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.BeanModelSource;
import org.apache.tapestry5.services.ContextValueEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.trailsframework.descriptor.CollectionDescriptor;
import org.trailsframework.descriptor.TrailsClassDescriptor;
import org.trailsframework.descriptor.TrailsPropertyDescriptor;
import org.trailsframework.services.DescriptorService;
import org.trailsframework.services.PersistenceService;

public class EditCompositionPage
{

	private static final Logger LOGGER = LoggerFactory.getLogger(EditCompositionPage.class);

	@Inject
	private ContextValueEncoder contextValueEncoder;

	@Inject
	private BeanModelSource beanModelSource;

	@Inject
	private Messages messages;

	@Inject
	private PersistenceService persitenceService;

	@Inject
	private DescriptorService descriptorService;

	@Inject
	private ComponentResources resources;

	@Property(write = false)
	private TrailsClassDescriptor classDescriptor;

	@Property(write = false)
	private CollectionDescriptor collectionDescriptor;

	@Property(write = false)
	private BeanModel beanModel;

	@Property(write = false)
	private Object bean;

	@Property(write = false)
	private Object parentBean;

	@Component
	private BeanEditForm form;

	@Component
	private PageLink link;

	void pageLoaded()
	{
		// Make other changes to the bean here.
	}

	void onActivate(Class clazz, String parentId, String property, String id) throws Exception
	{
		TrailsPropertyDescriptor propertyDescriptor = descriptorService.getClassDescriptor(clazz).getPropertyDescriptor(property);
		collectionDescriptor = ((CollectionDescriptor) propertyDescriptor);
		classDescriptor = descriptorService.getClassDescriptor(collectionDescriptor.getElementType());
		bean = contextValueEncoder.toValue(classDescriptor.getType(), id);
		beanModel = beanModelSource.create(classDescriptor.getType(), true, messages);

		parentBean = contextValueEncoder.toValue(clazz, parentId);

//		BeanModelUtils.modify(_beanModel, null, null, null, null);
	}

	/**
	 * This tells Tapestry to put type & id into the URL, making it bookmarkable.
	 *
	 * @return
	 */
	Object[] onPassivate()
	{
		return new Object[]{collectionDescriptor.getBeanType(), parentBean, collectionDescriptor.getName(), bean};
	}

	boolean onValidateForm()
	{
		LOGGER.debug("validating");
		//add validation logic here
		return true;
	}

	Object onSuccess()
	{
		try
		{

			LOGGER.debug("saving....");
			persitenceService.save(bean);

			return backToList();
		}

		catch (Exception e)
		{
//			missing ExceptionUtils (Lang 2.3 API)
//			form.recordError(ExceptionUtil.getRootCause(e));
			form.recordError(e.getMessage());
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}

		return null;
	}

	void cleanupRender()
	{
		bean = null;
		classDescriptor = null;
		beanModel = null;
	}

	public Link onActionFromDelete()
	{
		persitenceService.remove(bean);
		return backToList();
	}

	public Link onActionFromCancel()
	{
		return backToList();
	}

	public Link backToList()
	{
		return resources.createPageLink(Edit.class, false, collectionDescriptor.getBeanType(), parentBean);
	}

}