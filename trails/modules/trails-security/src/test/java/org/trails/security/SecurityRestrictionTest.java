package org.trails.security;

import junit.framework.TestCase;
import org.trails.descriptor.IPropertyDescriptor;
import org.trails.descriptor.TrailsPropertyDescriptor;
import org.trails.test.Foo;
import org.trails.test.IBar;

public class SecurityRestrictionTest extends TestCase
{
	protected SecurityAuthorities authorities;
	protected IPropertyDescriptor propertyDescriptor;

	public SecurityRestrictionTest()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public SecurityRestrictionTest(String arg0)
	{
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	protected void setUp() throws Exception
	{
		propertyDescriptor = new TrailsPropertyDescriptor(Foo.class, "bar", IBar.class);
		authorities = new SecurityAuthorities();
	}

	/**
	 * Some test so All test cases can be executed.
	 */
	public void testFoo()
	{

	}

}
