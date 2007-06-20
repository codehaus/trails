package org.trails.component;

import org.apache.tapestry.annotations.ComponentClass;
import org.apache.tapestry.annotations.InjectObject;
import org.apache.tapestry.annotations.Parameter;
import org.apache.tapestry.annotations.Asset;
import org.apache.tapestry.IAsset;
import org.hibernate.criterion.DetachedCriteria;
import org.trails.persistence.HibernatePersistenceService;

/**
 * This guy interacts with persistence service to produce a Select
 * containing all the elements of the PropertyDescriptor's type.  If
 * a criteria is specified, it will filter the list by it.
 */
@ComponentClass(allowBody = false, allowInformalParameters = true)
public abstract class HibernateAssociationSelect extends AssociationSelect
{

	@Asset(value = "/org/trails/component/AssociationSelect.html")
	public abstract IAsset get$template();

	/**
	 * @todo: remove when the components reuse issue goes away
	 */
	@InjectObject("spring:persistenceService")
	public abstract HibernatePersistenceService getHibernatePersistenceService();

	/**
	 * @todo: remove when the components reuse issue goes away
	 */
	@Override
	public HibernatePersistenceService getPersistenceService()
	{
		return getHibernatePersistenceService();
	}

	@Parameter(required = false)
	public abstract DetachedCriteria getCriteria();

	public void buildSelectionModel()
	{
		DetachedCriteria criteria = getCriteria() != null ? getCriteria() : DetachedCriteria.forClass(getClassDescriptor().getType());
		IdentifierSelectionModel selectionModel = new IdentifierSelectionModel(
			getPersistenceService().getInstances(getClassDescriptor().getType(), criteria),
			getClassDescriptor().getIdentifierDescriptor().getName(),
			isAllowNone());
		selectionModel.setNoneLabel(getNoneLabel());
		setPropertySelectionModel(selectionModel);
	}
}
