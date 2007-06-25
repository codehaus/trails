package org.trails.demo;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.trails.descriptor.annotation.ClassDescriptor;
import org.trails.descriptor.annotation.PropertyDescriptor;
import org.trails.security.RestrictionType;
import org.trails.security.annotation.UpdateRequiresRole;
import org.trails.security.annotation.RemoveRequiresRole;
import org.trails.security.annotation.ViewRequiresRole;

/**
 * A Officer belongs to an league
 * 
 * @author kenneth.colassi nhhockeyplayer@hotmail.com
 */
@Entity
@RemoveRequiresRole({"ROLE_ADMIN", "ROLE_MANAGER"})
@UpdateRequiresRole({"ROLE_ADMIN", "ROLE_MANAGER"})
@ViewRequiresRole({"ROLE_ADMIN", "ROLE_MANAGER", "ROLE_USER"})
@ClassDescriptor(hasCyclicRelationships = true, hidden = true)
public class Officer extends Person
{
	private static final Log log = LogFactory.getLog(Officer.class);

	protected enum EOfficerRole
	{
		COMMISSIONER, SECRETARY, TREASURER, OFFICIALS, MEDIA, MARKETING
	}

	private EOfficerRole eOfficerRole;

	private League league = null;

	/**
	 * CTOR
	 */
	public Officer(Officer dto)
	{
		super(dto);

		try
		{
			BeanUtils.copyProperties(this, dto);
		} catch (Exception e)
		{
			log.error(e.toString());
			e.printStackTrace();
		}
	}

	public Officer()
	{
		setERole(ERole.USER);
	}

	@Enumerated(value = EnumType.STRING)
	@PropertyDescriptor(summary = true)
	public EOfficerRole getEOfficerRole()
	{
		return eOfficerRole;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "officer_league_fk", insertable = false, updatable = true, nullable = true)
	public League getLeague()
	{
		return league;
	}

	public void setEOfficerRole(EOfficerRole role)
	{
		this.eOfficerRole = role;
	}

	public void setLeague(League league)
	{
		this.league = league;
	}

	@Override
	public Officer clone()
	{
		return new Officer(this);
	}

	@Override
	public int hashCode()
	{
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((getId() == null) ? 0 : getId().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object rhs)
	{
		if (this == rhs)
			return true;
		if (rhs == null)
			return false;
		if (!(rhs instanceof Officer))
			return false;
		final Officer castedObject = (Officer) rhs;
		if (getId() == null)
		{
			if (castedObject.getId() != null)
				return false;
		} else if (!getId().equals(castedObject.getId()))
			return false;
		return true;
	}
}