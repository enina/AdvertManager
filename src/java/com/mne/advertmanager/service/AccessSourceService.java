/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.advertmanager.service;

import com.mne.advertmanager.dao.GenericDao;
import com.mne.advertmanager.model.AccessSource;
import java.util.Collection;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author ilyae
 */
public class AccessSourceService {
 
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(AccessSourceService.class);
    
    private GenericDao<AccessSource,Integer> accessSourceDao;

    public void setAccessSourceDao(GenericDao<AccessSource, Integer> accessSourceDao) {
        this.accessSourceDao = accessSourceDao;
    }
    
    @Transactional
    public Integer create(AccessSource accessSource) {
             
        return accessSourceDao.create(accessSource);
    }
    
    @Transactional(readOnly = true)
    public AccessSource findByDomain(String domain) {
        
        AccessSource result = null;
        
        Collection<AccessSource> data = accessSourceDao.findByQuery("AccessSource.findByAccessSourceDomain", domain);
        if (data != null && data.size()>0 )
            result = data.iterator().next();
        
        return result;
    }
    
    
    
}
