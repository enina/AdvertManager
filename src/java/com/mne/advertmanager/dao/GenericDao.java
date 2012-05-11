/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.advertmanager.dao;

import com.mne.advertmanager.model.AffProgram;
import java.io.Serializable;
import java.util.Collection;

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
    
    public Collection<T> findByExample(T example);
    
    public void flush();

}