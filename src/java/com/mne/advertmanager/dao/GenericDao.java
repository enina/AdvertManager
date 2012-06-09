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

/**
 *
 * @author Nina Eidelshtein and Misha Lebedev
 */
public interface GenericDao <T, PK extends Serializable> {

    /** Persist the newInstance object into database */
    PK create(T newInstance);

    /** Retrieve an object that was previously persisted to the database using
     *   the indicated id as primary key
     */
    T read(PK id);

    /** Save changes made to a persistent object.  */
    void update(T transientObject);

    /** Remove an object from persistent storage in the database */
    void delete(T persistentObject);
    
    public Collection<T> findByQuery(String queryName,Object ...params);
    
    public T findSingleItemByQuery(String queryName, Object... params);
    
    public <X> X findSingleItemByQueryString(String queryName,X target, Object... params);
    
    public Collection<T> findByExample(T example);
    
    public int executeUpdateByQuery(String queryName,Object ...params);
    
    public Page<T> findPageByQuery(String queryName,PageCtrl pageCtrl,Object ...params);
    
    
    // write all data from memory to db
    public void flush();

    // calculate number of pages ro given query and save in page control
    public void initPageCtrl(PageCtrl pageCtrl,String queryName,Object ... params);
    // calculate how many results in query
    public int findQueryResultSetSize( String queryName, Object ... params);
    // save group of entities in db
    public void saveDataSet(Collection<T> dataSet);
    
}