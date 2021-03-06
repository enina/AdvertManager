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
import javax.validation.Validation;
import javax.validation.Validator;
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
    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public GenericDaoHibernateImpl(Class<T> type) {
        this(type, type.getName());
    }

    public GenericDaoHibernateImpl(Class<T> type, String entityName) {
        this.type = type;
        this.entityName = entityName;
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public PK create(T o) {

        PK result = null;

        try {
            if (validator.validate(o).isEmpty()) {
                result = (PK) getSession().save(entityName, o);
            }
        } catch (Exception e) {
            logger.error("create ::: Entity:{}, Exception:{},Message:{}", new Object[]{o.getClass().getSimpleName(), e.getClass().getSimpleName(), e.getMessage()});
        }
        return result;
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public T read(PK id) {

        T result = null;

        try {
            result = (T) getSession().get(entityName, id);
        } catch (Exception e) {
            logger.error("read ::: ID:{}, Exception:{},Message:{}", new Object[]{id, e.getClass().getSimpleName(), e.getMessage()});
        }
        return result;

    }

    @Override
    public void update(T o) {
        try {
            if (validator.validate(o).isEmpty()) {
                getSession().update(entityName, o);
            }
        } catch (Exception e) {
            logger.error("update ::: Entity:{}, Exception:{},Message:{}", new Object[]{o.getClass().getSimpleName(), e.getClass().getSimpleName(), e.getMessage()});
        }
    }

    @Override
    public void delete(T o) {
        try {
            getSession().delete(entityName, o);
        } catch (Exception e) {
            logger.error("delete ::: Entity:{}, Exception:{},Message:{}", new Object[]{o, e.getClass().getSimpleName(), e.getMessage()});
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
                //execute query
                result = q.list();
            }
        } catch (Exception e) {
            logger.error("findByQuery ::: query:{}, Exception:{},Message:{}", new Object[]{queryName, e.getClass().getSimpleName(), e.getMessage()});
        }

        return result;
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public Collection<T> findByQuery(String queryName, String listParamName, Collection valueList) {
                Collection<T> result = null;

        try {
            Query q = getSession().getNamedQuery(queryName);
            if (q != null) {
                q.setParameterList(listParamName, valueList);
                result = q.list();
            }
        } catch (Exception e) {
            logger.error("findByQuery ::: query:{}, Exception:{},Message:{}", new Object[]{queryName, e.getClass().getSimpleName(), e.getMessage()});
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
        } catch (Exception e) {
            logger.error("findSingleItemByQuery ::: query:{}, Exception:{},Message:{}", new Object[]{queryName, e.getClass().getSimpleName(), e.getMessage()});
        }

        return result;
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    /*
     * needed to execute ACCESSLOG_FINDGEODATABYIP_QUERY native sql query , defined in AccessLog
     */
    public <X> X findSingleItemByQueryString(String queryString, X target, Object... params) {

        X result = null;

        try {
            Query q = prepareQuery(queryString, params, target);

            if (q != null) {
                result = (X) q.uniqueResult();
            }
        } catch (Exception e) {
            logger.error("findSingleItemByQueryString ::: query:{}, Exception:{},Message:{}", new Object[]{queryString, e.getClass().getSimpleName(), e.getMessage()});
        }

        return result;
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public <X> Collection<X> findByQueryString(String queryString, X target, Object... params) {
       
        Collection<X> result = null;
        
        try {
            
            Query q = prepareQuery(queryString, params, target);

            if (q != null) 
                result = q.list();
            
        } catch (Exception e) {
            logger.error("findByQueryString ::: query:{}, Exception:{},Message:{}", new Object[]{queryString, e.getClass().getSimpleName(), e.getMessage()});
        }
        
        return result;        

    }

    private <X> Query  prepareQuery(String queryString, Object[] params, X target) {
        Query result = null;
        try {
            result = createQuery(queryString, params);

            if (result != null) {
                result.setResultTransformer(Transformers.aliasToBean(target.getClass()));
            }
        } catch (Exception e) {
            logger.error("findByQueryString ::: query:{}, Exception:{},Message:{}", new Object[]{queryString, e.getClass().getSimpleName(), e.getMessage()});
        }
        return result;
    }
    
    

    @Override
    public int executeUpdateByQuery(String queryName, Object... params) {
        int res = -1;

        try {
            Query q = getNamedQuery(queryName, params);
            res = q.executeUpdate();
        } catch (Exception e) {
            logger.error("executeUpdateByQuery ::: query:{}, Exception:{},Message:{}", new Object[]{queryName, e.getClass().getSimpleName(), e.getMessage()});
        }

        return res;

    }
    // Not supported yet

    @Override
    public Collection<T> findByExample(T example) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
//================================ findPageByQuery =============================

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
        } catch (Exception e) {
            logger.error("findPageByQuery ::: query:{}, Exception:{},Message:{}", new Object[]{queryName, e.getClass().getSimpleName(), e.getMessage()});
        }
        return result;
    }
//================================ initPageCtrl ================================

    @Override
    //initialization page controle structure
    public void initPageCtrl(PageCtrl pageCtrl, String queryName, Object... params) {
        //quantity of results from query
        int count = findQueryResultSetSize(queryName, params);
        pageCtrl.setTotalPages(count / pageCtrl.getPageSize() + ((count % pageCtrl.getPageSize() == 0) ? 0 : 1));
    }

    @Override
    //find number of results in query
    public int findQueryResultSetSize(String queryName, Object... params) {
        int result = 0;
        try {
            Query q = getNamedQuery(queryName, params);
            result = ((Long) q.uniqueResult()).intValue();
        } catch (Exception e) {
            logger.error("findQueryResultSetSize ::: query:{}, Exception:{},Message:{}", new Object[]{queryName, e.getClass().getSimpleName(), e.getMessage()});
        }
        return result;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }
    /*
     * create and initialize object of class Query from Hibernate library This object contains query parameters inside and can be used to execute query and
     * retrieve its results
     */

    private Query getNamedQuery(String queryName, Object[] params) throws HibernateException {

        Query q = getSession().getNamedQuery(queryName);
        return setQueryParameters(q, params);

    }
//=========================== createQuery ======================================

    private Query createQuery(String query, Object[] params) throws HibernateException {

        Query q = getSession().createSQLQuery(query);
        return setQueryParameters(q, params);

    }
//=========================== setQueryParameters ===============================

    private Query setQueryParameters(Query q, Object[] params) throws HibernateException {
        if (q != null && params != null) {
            for (int i = 0; i < params.length; ++i) {
                q.setParameter(i, params[i]);
            }
        }
        return q;
    }
//=============================== saveDataSet ==================================

    @Override
    public void saveDataSet(Collection<T> dataSet) {

        if (dataSet == null) {
            return;
        }

        try {
            for (T item : dataSet) {
                if (item != null) {
                    getSession().saveOrUpdate(entityName, item);
                }
            }
        } catch (Exception e) {
            logger.error("saveDataSet ::: Exception:{},Message:{}", e.getClass().getSimpleName(), e.getMessage());
        }
    }
}