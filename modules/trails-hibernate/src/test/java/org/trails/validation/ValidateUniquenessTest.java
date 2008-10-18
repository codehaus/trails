package org.trails.validation;

import java.util.List;

import org.springframework.test.AbstractTransactionalSpringContextTests;
import org.trails.persistence.HibernatePersistenceService;
import org.trails.testhibernate.Baz;
import org.trails.testhibernate.Bing;
import org.trails.testhibernate.Bar;

public class ValidateUniquenessTest extends AbstractTransactionalSpringContextTests
{

	public ValidateUniquenessTest()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String[] getConfigLocations()
	{
		return new String[]{"applicationContext-test.xml"};
	}

	public void onSetUpInTransaction() throws Exception
	{
		persistenceService = (HibernatePersistenceService) applicationContext.getBean(
			"persistenceService");
	}

	HibernatePersistenceService persistenceService;

	public void testUniquenessQuery() {
		Baz baz = new Baz();
		baz.setDescription("one");
		persistenceService.save(baz);
		Baz baz2 = new Baz();
		baz2.setDescription("second");
		persistenceService.save(baz2);

		Object[] values = {baz.getDescription(), baz2.getId()};
		// Should refect the query used in ValidateUniqueAspect.aj
		List result = persistenceService.find("select count(*) from " 
        + baz.getClass().getName() + " where description " 
        + " = ? and id " + " != ?", values);
		assertTrue((Long)result.get(0) == 1);

		Object[] values2 = {baz.getDescription(), baz.getId()};
		result = persistenceService.find("select count(*) from " 
        + baz.getClass().getName() + " where description " 
        + " = ? and id " + " != ?", values2);
		assertTrue((Long)result.get(0) == 0);

	}
	
	public void testUniqueness() throws Exception
	{

		Baz baz = new Baz();
		baz.setDescription("same");
		persistenceService.save(baz);
		Baz baz2 = new Baz();
		baz2.setDescription("same");
		UniquenessException caught = null;
		try
		{
			persistenceService.save(baz2);
		}
		catch (UniquenessException pe)
		{
			caught = pe;
			//pe.printStackTrace();
		}
		assertNotNull(caught);
		assertEquals("right message", "Description must be unique.",
			caught.getMessage());
	}	

	public void testUniquenessWithNonString() throws Exception
	{
		Bar bar = new Bar();
		bar.setNumber(5);

		UniquenessException caught = null;
		try
		{
			// should not blow up
			bar = persistenceService.save(bar);

			// should also still work
			bar = persistenceService.save(bar);
		}
		catch (UniquenessException pe)
		{
			caught = pe;
		}

		assertNull(caught);


		Bar bar2 = new Bar();
		bar2.setNumber(5);
		try
		{
			// now it should
			bar2 = persistenceService.save(bar2);
		}
		catch (UniquenessException pe)
		{
			caught = pe;
		}

		assertEquals("right message", "Number must be unique.",caught.getMessage());

	}
}
