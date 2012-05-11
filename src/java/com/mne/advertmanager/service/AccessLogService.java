/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.advertmanager.service;

import com.mne.advertmanager.dao.GenericDao;
import com.mne.advertmanager.model.AccessLog;
import java.util.Collection;
import org.springframework.transaction.annotation.Transactional;


/**
 *
 * @author Nina Eidelshtein and Misha Lebedev
 */
public class AccessLogService {
 
    private GenericDao<AccessLog,Integer> accessLogDao;

    public void setAccessLogDao(GenericDao<AccessLog,Integer> accessLogDao) {
        this.accessLogDao = accessLogDao;
    }
    @Transactional(readOnly = true)
    public Collection<AccessLog> findAllAccessLog() {
        return accessLogDao.findByQuery("AccessLog.findAll");
    }

    @Transactional
    public Integer createAccessLog(AccessLog access) {
        return accessLogDao.create(access);
    }

    public AccessLog findAccessByIP(String ipAddress) {
        return accessLogDao.findSingleItemByQuery("AccessLog.findByIpAddress", ipAddress);
    }
}
