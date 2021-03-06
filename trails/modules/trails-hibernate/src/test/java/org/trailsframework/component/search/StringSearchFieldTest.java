/**
 *
 */
package org.trailsframework.component.search;

import org.hibernate.criterion.DetachedCriteria;
import org.trails.component.HibernateComponentTest;
import org.trails.descriptor.IPropertyDescriptor;
import org.trails.descriptor.TrailsPropertyDescriptor;
import org.trails.testhibernate.Foo;

/**
 * @author cnelson
 */
public class StringSearchFieldTest extends HibernateComponentTest
{
	public void testAddCriterion() throws Exception
	{
		StringSearchField stringSearchField = (StringSearchField) creator.newInstance(StringSearchField.class);
		DetachedCriteria criteria = DetachedCriteria.forClass(Foo.class);
		stringSearchField.setCriteria(criteria);
		IPropertyDescriptor propDescriptor = new TrailsPropertyDescriptor(Foo.class, "name", String.class);
		stringSearchField.setPropertyDescriptor(propDescriptor);
		stringSearchField.setValue("bunk");
		stringSearchField.buildCriterion();
		assertTrue(stringSearchField.getCriteria().toString().indexOf("name like %bunk%") > -1);

	}
}
