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
import org.hibernate.transform.Transformers;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Nina Eidelshtein and Misha Lebedev
 */
public class GenericDaoHibernateImpl<T, PK extends Serializable> implements GenericDao<T, PK> {

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(GenericDaoHibernateImpl.class);
    
    private Class<T> type;
    private SessionFactory sessionFactory;
    private String entityName = null;
    

    public GenericDaoHibernateImpl(Class<T> type) {
        this.type = type;
    }

    public GenericDaoHibernateImpl(Class<T> type, String entityName) {
        this(type);
        this.entityName = entityName;
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public PK create(T o) {

        PK result = null;

        try {
            if (entityName == null) {
                result = (PK) getSession().save(o);
            } else {
                result = (PK) getSession().save(entityName, o);
            }
        } catch (HibernateException e) {
            logger.error("create ::: Entity:{}, Exception:{},Message:{}",new Object[]{o.getClass().getSimpleName(),e.getClass().getSimpleName(),e.getMessage()});
        }
        return result;
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public T read(PK id) {

        T result = null;

        try {
            if (entityName == null) {
                result = (T) getSession().get(type, id);
            } else {
                result = (T) getSession().get(entityName, id);
            }
        } catch (HibernateException e) {
            logger.error("read ::: ID:{}, Exception:{},Message:{}",new Object[]{id,e.getClass().getSimpleName(),e.getMessage()});
        }
        return result;

    }

    @Override
    public void update(T o) {
        try {
            if (entityName == null) {
                getSession().update(o);
            } else {
                getSession().update(entityName, o);
            }
        } catch (HibernateException e) {
            logger.error("Update Entity:{}, Exception:{},Message:{}",new Object[]{o.getClass().getSimpleName(),e.getClass().getSimpleName(),e.getMessage()});
        }
    }

    @Override
    public void delete(T o) {
        try {
            if (entityName == null) {
                getSession().delete(o);
            } else {
                getSession().delete(entityName, o);
            }
        }  catch (HibernateException e) {
            logger.error("Delete Entity:{}, Exception:{},Message:{}",new Object[]{o,e.getClass().getSimpleName(),e.getMessage()});
        }
    }

    @Override
    public void flush() {
        getSession().flush();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<T> findByQuery(String queryName, Object... params) {

        Collection<T> result = null;

        try {
            Query q = getNamedQuery(queryName, params);
            if (q != null) {
                result = q.list();
            }
        } catch (HibernateException e) {
            logger.error("findByQuery ::: query:{}, Exception:{},Message:{}",new Object[]{queryName,e.getClass().getSimpleName(),e.getMessage()});
        }

        return result;
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public T findSingleItemByQuery(String queryName, Object... params) {

        T result = null;
        try {
            result = null;
            Query q = getNamedQuery(queryName, params);
            
            if (q != null) {
                result = (T) q.uniqueResult();
            }
        } catch (HibernateException e) {
            logger.error("findSingleItemByQuery ::: query:{}, Exception:{},Message:{}",new Object[]{queryName,e.getClass().getSimpleName(),e.getMessage()});
        }

        return result;
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public <X> X findSingleItemByQueryString(String queryString, X target , Object... params) {
        
        X result = null;
       
        try {
            Query q = createQuery(queryString, params);
            
            if (q != null) {
                q.setResultTransformer(Transformers.aliasToBean(target.getClass()));
                result = (X) q.uniqueResult();
            }
        } catch (HibernateException e) {
            logger.error("findSingleItemByQueryString ::: query:{}, Exception:{},Message:{}",new Object[]{queryString,e.getClass().getSimpleName(),e.getMessage()});
        }
        
        return result;
    }   
    
    @Override
    public int executeUpdateByQuery(String queryName, Object... params) {
        int res  = -1;
        
        try {
            Query q = getNamedQuery(queryName, params);
            res = q.executeUpdate();
        } catch (HibernateException e) {
            logger.error("executeUpdateByQuery ::: query:{}, Exception:{},Message:{}",new Object[]{queryName,e.getClass().getSimpleName(),e.getMessage()});
        }
     
        return res;

    }

    @Override
    public Collection<T> findByExample(T example) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public Page<T> findPageByQuery(String queryName, PageCtrl pageCtrl, Object... params) {

        Page<T> result = null;
        
        try {
            Query q = getNamedQuery(queryName, params);
            q.setMaxResults(pageCtrl.getPageSize());
            q.setFirstResult(pageCtrl.getFirstResult());
            List data = q.list();
            result = new Page<T>(data, pageCtrl);
        } catch (HibernateException e) {
            logger.error("findPageByQuery ::: query:{}, Exception:{},Message:{}",new Object[]{queryName,e.getClass().getSimpleName(),e.getMessage()});
        }
        return result;
    }

    @Override
    public void initPageCtrl(PageCtrl pageCtrl, String queryName, Object... params) {
        
        int count = findQueryResultSetSize(queryName, params);
        pageCtrl.setTotalPages(count / pageCtrl.getPageSize() + ((count % pageCtrl.getPageSize() == 0) ? 0 : 1));
    }

    @Override
    public int findQueryResultSetSize( String queryName, Object... params) {
        int result = 0;
        try {
            Query q = getNamedQuery(queryName, params);
            result = ((Long) q.uniqueResult()).intValue();
        } catch (HibernateException e) {
            logger.error("findQueryResultSetSize ::: query:{}, Exception:{},Message:{}",new Object[]{queryName,e.getClass().getSimpleName(),e.getMessage()});
        }
        return result;
    }

    
    
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    private Query getNamedQuery(String queryName, Object[] params) throws HibernateException {

        Query q = getSession().getNamedQuery(queryName);
        return setQueryParameters(q, params);

    }
    
    private Query createQuery(String query, Object[] params) throws HibernateException {

        Query q = getSession().createSQLQuery(query);
        return setQueryParameters(q, params);

    }

    private Query setQueryParameters(Query q, Object[] params) throws HibernateException {
        if (q != null && params != null) {
            for (int i = 0; i < params.length; ++i) {
                q.setParameter(i, params[i]);
            }
        }
        return q;
    }
}