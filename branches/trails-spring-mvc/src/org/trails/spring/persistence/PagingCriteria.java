/*
 * Copyright 2005, Inspiring BV, the Netherlands
 *
 * info@inspiring.nl
 */

package org.trails.spring.persistence;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.trails.descriptor.IClassDescriptor;

/**
 * @author Jurjan Woltman
 */
public class PagingCriteria extends DetachedCriteria {

  /** Root entity class. */
  private Class entityClass;
  
  private int pageNumber;
  private int pageSize;
  private int totalPageNumbers;
  
  public PagingCriteria(IClassDescriptor classDescriptor, int pageNumber, int pageSize) {
    super(classDescriptor.getType().getName());
    
    setEntityClass(classDescriptor.getType());
    setPageNumber(pageNumber);
    setPageSize(pageSize);
  }
  
  /**
   * @see org.hibernate.criterion.DetachedCriteria#getExecutableCriteria(org.hibernate.Session)
   */
  @Override
  public Criteria getExecutableCriteria(Session session) {
    Criteria criteria = super.getExecutableCriteria(session).addOrder(Order.asc("id"));
    criteria.setProjection(Projections.rowCount());
    
    setTotalPageNumbers(((Integer)criteria.uniqueResult()).intValue() / getPageSize());
    
    //Restore original criteria
    criteria.setProjection(null);
    criteria.setResultTransformer(Criteria.ROOT_ENTITY);
    
    criteria.setMaxResults(getPageSize());
    criteria.setFirstResult(getPageSize() * getPageNumber());

    return criteria;
  }

  /**
   * Returns the entityClass.
   * @return Returns the entityClass.
   */
  public Class getEntityClass() {
    return entityClass;
  }

  /**
   * Sets the entityClass.
   * @param entityClass The entityClass to set.
   */
  public void setEntityClass(Class entityClass) {
    this.entityClass = entityClass;
  }

  /**
   * Returns the pageNumber.
   * @return Returns the pageNumber.
   */
  public int getPageNumber() {
    return pageNumber;
  }

  /**
   * Sets the pageNumber.
   * @param pageNumber The pageNumber to set.
   */
  public void setPageNumber(int pageNumber) {
    this.pageNumber = pageNumber;
  }

  /**
   * Returns the pageSize.
   * @return Returns the pageSize.
   */
  public int getPageSize() {
    return pageSize;
  }

  /**
   * Sets the pageSize.
   * @param pageSize The pageSize to set.
   */
  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }

  /**
   * Returns the totalPageNumbers.
   * @return Returns the totalPageNumbers.
   */
  public int getTotalPageNumbers() {
    return totalPageNumbers;
  }

  /**
   * Sets the totalPageNumbers.
   * @param totalPageNumbers The totalPageNumbers to set.
   */
  public void setTotalPageNumbers(int totalPageNumbers) {
    this.totalPageNumbers = totalPageNumbers;
  }
}
