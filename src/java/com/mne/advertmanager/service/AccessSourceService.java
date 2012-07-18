/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.advertmanager.service;

import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.mne.advertmanager.dao.GenericDao;
import com.mne.advertmanager.model.AccessSource;

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
        
        result = accessSourceDao.findSingleItemByQuery("AccessSource.findByAccessSourceDomain", domain);
        
        if (result != null)
        	logger.info("AccessSource Found ::: domain={}",domain);
        else
        	logger.info("AccessSource not dound ::: domain={}",domain);
        
        return result;
    }
    
    
    
}
