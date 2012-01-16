/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.advertmanager.dao;

import java.io.Serializable;
import java.util.Collection;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author Nina Eidelshtein and Misha Lebedev
 */
public class GenericDaoHibernateImpl<T, PK extends Serializable> implements GenericDao<T, PK> {

    private Class<T> type;
    private SessionFactory sessionFactory;    

    public GenericDaoHibernateImpl(Class<T> type) {
        this.type = type;
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public PK create(T o) {
        return (PK) getSession().save(o);
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public T read(PK id) {
        return (T) getSession().get(type, id);
    }

    @Override
    public void update(T o) {
        getSession().update(o);
    }

    @Override
    public void delete(T o) {
        getSession().delete(o);
    }
    
    @Override
    public Collection<T> findByQuery(String queryName,Object ...params) {
        return null;
    }
    


    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    // Not showing implementations of getSession() and setSessionFactory()

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public Collection<T> findByExample(T example) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}