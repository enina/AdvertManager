/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.advertmanager.service;

import com.mne.advertmanager.dao.GenericDao;
import com.mne.advertmanager.model.AccessLog;
import com.mne.advertmanager.util.Page;
import com.mne.advertmanager.util.PageCtrl;
import java.util.Date;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;


/**
 *
 * @author Nina Eidelshtein and Misha Lebedev
 */
public class AccessLogService {
 
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(AccessLogService.class);
    
    private GenericDao<AccessLog,Integer> accessLogDao;

    public void setAccessLogDao(GenericDao<AccessLog,Integer> accessLogDao) {
        this.accessLogDao = accessLogDao;
    }
    @Transactional(readOnly = true)
    public Page<AccessLog> findAllAccessLog(PageCtrl pageCtrl) {
        
        if (pageCtrl.getTotalPages()<=0) {
            accessLogDao.initPageCtrl(pageCtrl, "AccessLog.countAccessLog");
        }

        Page<AccessLog> result =  accessLogDao.findPageByQuery("AccessLog.findAll", pageCtrl);

        return result;
    }

    @Transactional
    public Integer createAccessLog(AccessLog access) {
        return accessLogDao.create(access);
    }

    public AccessLog findAccessByIP(String ipAddress) {
        return (AccessLog)accessLogDao.findSingleItemByQuery("AccessLog.findByIpAddress", ipAddress);
    }
    
    @Transactional(readOnly = true)
    public Page<AccessLog> findAccessByAffProgamId(PageCtrl pageCtrl,int affProgramId) {
        
        if (pageCtrl.getTotalPages()<=0) {
            accessLogDao.initPageCtrl(pageCtrl, "AccessLog.countAffProgramAccessLog",affProgramId);
        }
        
        Page<AccessLog> result =  accessLogDao.findPageByQuery("AccessLog.findByAffProgramId", pageCtrl,affProgramId);

        for(AccessLog a:result.getItems()) {
            a.setAffProgram(null);
        }
        
        return result;        

    }

    @Transactional(readOnly=true)
    int findDailyAffProgramAccessAmount(int affProgramId, Date refTime) {
        
        int result = accessLogDao.findQueryResultSetSize("AccessLog.countAffProgramAccessByDate",affProgramId,refTime);
        
        logger.debug("Found {} access for program {} after {}",new Object[]{result,affProgramId,refTime});
        
        return result;
    }
}
