package org.trails.demo;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Transient;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.validator.NotNull;
import org.trails.descriptor.BlobDescriptorExtension.ContentDisposition;
import org.trails.descriptor.BlobDescriptorExtension.RenderType;
import org.trails.descriptor.annotation.BlobDescriptor;
import org.trails.descriptor.annotation.ClassDescriptor;
import org.trails.descriptor.annotation.Collection;
import org.trails.descriptor.annotation.PropertyDescriptor;
import org.trails.util.DatePattern;
import org.trails.validation.ValidateUniqueness;


/**
 * Organizations have one Director, and Years, Coaches, Teams
 *
 * @author kenneth.colassi        nhhockeyplayer@hotmail.com
 */
@Entity
@ValidateUniqueness(property = "name")
@ClassDescriptor(hasCyclicRelationships=true)
public class Organization implements Serializable {
    private static final Log log = LogFactory.getLog(Organization.class);

    private Integer id = null;

    private Director director;

    private Set<Year> years = new HashSet<Year>();

    private Set<Coach> coaches = new HashSet<Coach>();

    private Set<Team> teams = new HashSet<Team>();

    private String name;

    private String city;

    private String state;

    private Long created = new Long(GregorianCalendar.getInstance()
            .getTimeInMillis());

    private Long accessed = new Long(GregorianCalendar.getInstance()
            .getTimeInMillis());

    /**
     * CTOR
     */
    public Organization() {
    }

    public Organization(Organization dto) {
        try {
            BeanUtils.copyProperties(this, dto);
        } catch (Exception e) {
            log.error(e.toString());
            e.printStackTrace();
        }
    }

    /**
     * Accessor for id
     *
     * @return Integer
     * @hibernate.id generator-class="increment" unsaved-value="-1"
     *               type="java.lang.Integer" unique="true" insert="true"
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @PropertyDescriptor(readOnly = true, summary = true, index = 0)
    public Integer getId() {
        return id;
    }

    @OneToMany
    @JoinColumn(name = "organization_id", insertable = true, updatable = true, nullable = true)
    @Collection(child = true, inverse = "organization")
    @PropertyDescriptor(readOnly = false, index = 1)
    @OrderBy("yearStart")
    public Set<Year> getYears() {
        return years;
    }

    @OneToOne
    @JoinTable(name = "OrganizationsDirectors",
        joinColumns = @JoinColumn(name = "director_fk"),
        inverseJoinColumns = {@JoinColumn(name = "organization_fk")}
    )
    @PropertyDescriptor(index = 2)
    public Director getDirector() {
        return director;
    }

    @OneToMany
    @JoinColumn(name = "coach_organization_fk", insertable = true, updatable = true, nullable = true)
    @Collection(child = true, inverse = "organization")
    @PropertyDescriptor(readOnly = false, searchable = true)
    @OrderBy("lastName")
    public Set<Coach> getCoaches() {
        return coaches;
    }

    @OneToMany
    @JoinColumn(name = "team_organization_fk", insertable = true, updatable = true, nullable = true)
    @Collection(child = true, inverse = "organization")
    @PropertyDescriptor(readOnly = false)
    @OrderBy("division")
    public Set<Team> getTeams() {
        return teams;
    }

    @Column(unique = true)
    @NotNull(message = "is required")
    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    private UploadableMedia photo = new UploadableMedia();
    @BlobDescriptor(renderType = RenderType.IMAGE, contentDisposition = ContentDisposition.ATTACHMENT)
    @PropertyDescriptor(summary = false, index = 3)
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public UploadableMedia getPhoto() {
        return photo;
    }

    private UploadableMedia header = new UploadableMedia();
    @BlobDescriptor(renderType = RenderType.IMAGE, contentDisposition = ContentDisposition.ATTACHMENT)
    @PropertyDescriptor(summary = false, index = 4)
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public UploadableMedia getHeader() {
        return header;
    }

    private UploadableMedia logo = new UploadableMedia();
    @BlobDescriptor(renderType = RenderType.IMAGE, contentDisposition = ContentDisposition.ATTACHMENT)
    @PropertyDescriptor(summary = true, index = 5)
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public UploadableMedia getLogo() {
        return logo;
    }

    @PropertyDescriptor(hidden = true, summary = false, searchable = false)
    public Long getCreated() {
        return created;
    }

    @PropertyDescriptor(hidden = true, summary = false, searchable = false)
    public Long getAccessed() {
        return accessed;
    }

    @Transient
    @PropertyDescriptor(hidden = true, summary = false, searchable = false)
    public String getCreatedAsString() {
        Calendar cal = new GregorianCalendar();
        cal.setTimeInMillis(created.longValue());
        return DatePattern.sdf.format(cal.getTime());
    }

    @Transient
    @PropertyDescriptor(hidden = true, summary = false, searchable = false)
    public String getAccessedAsString() {
        Calendar cal = new GregorianCalendar();
        cal.setTimeInMillis(accessed.longValue());
        return DatePattern.sdf.format(cal.getTime());
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setYears(Set<Year> years) {
        this.years = years;
    }

    public void setDirector(Director director) {
        this.director = director;
    }

    public void setCoaches(Set<Coach> coaches) {
        this.coaches = coaches;
    }

    public void setTeams(Set<Team> teams) {
        this.teams = teams;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setPhoto(UploadableMedia photo) {
        this.photo = photo;
    }

    public void setHeader(UploadableMedia header) {
        this.header = header;
    }

    public void setLogo(UploadableMedia logo) {
        this.logo = logo;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public void setAccessed(Long accessed) {
        this.accessed = accessed;
    }

    @Transient
    @PropertyDescriptor(hidden = true, summary = false, searchable = false)
    public void setCreatedAsString(String value) throws Exception {
        Calendar cal = new GregorianCalendar();
        cal.setTimeInMillis(DatePattern.sdf.parse(value).getTime());
        this.created = new Long(cal.getTimeInMillis());
    }

    @Transient
    @PropertyDescriptor(hidden = true, summary = false, searchable = false)
    public void setAccessedAsString(String value) throws Exception {
        Calendar cal = new GregorianCalendar();
        cal.setTimeInMillis(DatePattern.sdf.parse(value).getTime());
        this.accessed = new Long(cal.getTimeInMillis());
    }

    public String toString() {
        return getName();
    }

    @Override
    public Organization clone() {
        return new Organization(this);
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
        if (!(rhs instanceof Organization))
            return false;
        final Organization castedObject = (Organization) rhs;
        if (getId() == null) {
            if (castedObject.getId() != null)
                return false;
        } else if (!getId().equals(castedObject.getId()))
            return false;
        return true;
    }
}
