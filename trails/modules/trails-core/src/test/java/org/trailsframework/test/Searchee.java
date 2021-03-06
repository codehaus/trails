package org.trails.test;

import java.util.HashSet;
import java.util.Set;

import org.trails.descriptor.annotation.PropertyDescriptor;

public class Searchee
{

	private Integer id;

	private String name;

	private String nonSearchableProperty;

	private Set<Foo> foos = new HashSet<Foo>();

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	@PropertyDescriptor(searchable = false)
	public String getNonSearchableProperty()
	{
		return nonSearchableProperty;
	}

	public void setNonSearchableProperty(String nonSearchableProperty)
	{
		this.nonSearchableProperty = nonSearchableProperty;
	}

}
