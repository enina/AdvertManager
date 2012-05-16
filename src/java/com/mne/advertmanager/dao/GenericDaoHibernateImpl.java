/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.advertmanager.dao;

import com.mne.advertmanager.util.Page;
import com.mne.advertmanager.util.PageCtrl;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
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
    public void flush() {
        getSession().flush();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<T> findByQuery(String queryName, Object... params) {

        Collection<T> result = null;

        Query q = initQueryObject(queryName, params);
        if (q != null) {
            result = q.list();
        }

        return result;
    }

    private Query initQueryObject(String queryName, Object[] params) throws HibernateException {

        Query q = getSession().getNamedQuery(queryName);

        if (q != null && params != null) {
            for (int i = 0; i < params.length; ++i) {
                q.setParameter(i, params[i]);
            }
        }
        return q;

    }

    @Override
    public T findSingleItemByQuery(String queryName, Object... params) {

        T result = null;
        Collection<T> data = findByQuery(queryName, params);
        if (data != null && data.size() > 0) {
            result = data.iterator().next();
        }

        return result;
    }

    @Override
    public int executeUpdateByQuery(String queryName, Object... params) {
        Query q = initQueryObject(queryName, params);
        int res = q.executeUpdate();
        return res;

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

    @Override
    public Page<T> findPageByQuery(String queryName, PageCtrl pageCtrl, Object... params) {

        Query q = initQueryObject(queryName, params);
        q.setMaxResults(pageCtrl.getPageSize());
        q.setFirstResult(pageCtrl.getFirstResult());
        List data =  q.list();
        Page <T> result = new Page<T>(data,pageCtrl);
        
        return result;
    }

    @Override
    public void initPageCtrl(PageCtrl pageCtrl, String queryName, Object... params) {
        Query q = initQueryObject(queryName, params);
        int count = ((Long)q.uniqueResult()).intValue();
        pageCtrl.setTotalPages(count/pageCtrl.getPageSize()-((count%pageCtrl.getPageSize()==0)?1:0));
    }
    
    
}