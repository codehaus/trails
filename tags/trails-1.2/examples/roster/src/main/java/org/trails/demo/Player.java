package org.trails.demo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.trails.descriptor.annotation.ClassDescriptor;
import org.trails.descriptor.annotation.Collection;
import org.trails.descriptor.annotation.PropertyDescriptor;
import org.trails.security.RestrictionType;
import org.trails.security.annotation.UpdateRequiresRole;
import org.trails.security.annotation.RemoveRequiresRole;
import org.trails.security.annotation.ViewRequiresRole;

/**
 * A player has a photo, team, clips and stats
 * 
 * @author kenneth.colassi nhhockeyplayer@hotmail.com
 */
@Entity
@RemoveRequiresRole({"ROLE_ADMIN", "ROLE_MANAGER"})
@UpdateRequiresRole({"ROLE_ADMIN", "ROLE_MANAGER"})
@ViewRequiresRole({"ROLE_ADMIN", "ROLE_MANAGER", "ROLE_USER"})
@ClassDescriptor(hasCyclicRelationships = true)
public class Player extends Person
{
	private static final Log log = LogFactory.getLog(Player.class);

	public enum EPosition
	{
		GOALIE, DEFENSE, LEFTWING, RIGHTWING, CENTER, ALTERNATE, SPARE, BACKUP, WALKON
	}

	public enum EDexterity
	{
		LEFTY, RIGHTY
	}

	private Integer playerNumber;

	private EPosition playerPosition;

	private EDexterity dexterity;

	private Team team = null;

	private Set<UploadableMedia> clips = new HashSet<UploadableMedia>();

	private Set<PlayerStat> stats = new HashSet<PlayerStat>();

	/**
	 * CTOR
	 */
	public Player()
	{
	}

	public Player(Player dto)
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

	@PropertyDescriptor(summary = true, index = 1)
	public Integer getPlayerNumber()
	{
		return playerNumber;
	}

	@Enumerated(value = EnumType.STRING)
	@PropertyDescriptor(summary = true)
	public EPosition getPlayerPosition()
	{
		return playerPosition;
	}

	@Enumerated(value = EnumType.STRING)
	@PropertyDescriptor(summary = true)
	public EDexterity getDexterity()
	{
		return dexterity;
	}

	@ManyToOne
	@JoinColumn(name = "player_team_fk")
	public Team getTeam()
	{
		return team;
	}

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "clips_player_fk", insertable = true, updatable = true, nullable = true)
	@Collection(child = true, inverse = "player")
	@PropertyDescriptor(searchable = false, readOnly = false)
	@OrderBy("name")
	public Set<UploadableMedia> getClips()
	{
		return clips;
	}

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "playerstat_player_fk")
	@Collection(child = true, inverse = "player")
	@PropertyDescriptor(searchable = true, readOnly = false)
	public Set<PlayerStat> getStats()
	{
		return stats;
	}

	public void setPlayerNumber(Integer number)
	{
		this.playerNumber = number;
	}

	public void setPlayerPosition(EPosition position)
	{
		this.playerPosition = position;
	}

	public void setDexterity(EDexterity dexterity)
	{
		this.dexterity = dexterity;
	}

	public void setTeam(Team team)
	{
		this.team = team;
	}

	public void setClips(Set<UploadableMedia> clips)
	{
		this.clips = clips;
	}

	public void setStats(Set<PlayerStat> playerStat)
	{
		this.stats = playerStat;
	}

	@Override
	public Player clone()
	{
		return new Player(this);
	}
}
