package org.trails.demo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.trails.descriptor.annotation.ClassDescriptor;
import org.trails.descriptor.annotation.PropertyDescriptor;

/**
 * @hibernate.class table="Director" lazy="true"
 *
 * A Director belongs to an organization
 *
 * @author kenneth.colassi        nhhockeyplayer@hotmail.com
 */
@Entity
@ClassDescriptor(hasCyclicRelationships = true, hidden = true)
public class Director extends Person {
    private static final Log log = LogFactory.getLog(Director.class);

    private Organization organization;

    /**
     * CTOR
     */
    public Director(Director dto) {
        super(dto);

        try {
            BeanUtils.copyProperties(this, dto);
        } catch (Exception e) {
            log.error(e.toString());
            e.printStackTrace();
        }
    }

    public Director() {
        setERole(ERole.USER);
    }

    @OneToOne
    @JoinTable(name = "OrganizationsDirectors",
            joinColumns = @JoinColumn(name = "organization_fk"),
            inverseJoinColumns = {@JoinColumn(name = "director_fk")}
    )
    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    @Override
    public Director clone() {
        return new Director(this);
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((getId() == null) ? 0 : getId().hashCode());
        return result;
    }

    @Override
    public boolean equals(Object rhs) {
        if (this == rhs)
            return true;
        if (rhs == null)
            return false;
        if (!(rhs instanceof Director))
            return false;
        final Director castedObject = (Director) rhs;
        if (getId() == null) {
            if (castedObject.getId() != null)
                return false;
        } else if (!getId().equals(castedObject.getId()))
            return false;
        return true;
    }
}
