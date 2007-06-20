package org.trails.demo;

import javax.persistence.Entity;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.trails.io.Squeezable;

@Entity
public class Apple extends Fruit implements Squeezable
{

	public Apple()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	private String color;

	public String getColor()
	{
		return color;
	}

	public void setColor(String color)
	{
		this.color = color;
	}

	@Override
	public boolean equals(Object obj)
	{
		// TODO Auto-generated method stub
		return EqualsBuilder.reflectionEquals(this, obj);
	}
}
