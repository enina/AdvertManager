/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.advertmanager.service;

import com.mne.advertmanager.dao.GenericDao;
import com.mne.advertmanager.model.AccessLog;
import com.mne.advertmanager.model.AffProgram;
import com.mne.advertmanager.model.PurchaseOrder;
import com.mne.advertmanager.util.AccessStats;
import com.mne.advertmanager.util.GeoData;
import com.mne.advertmanager.util.Page;
import com.mne.advertmanager.util.PageCtrl;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
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
        return accessLogDao.findSingleItemByQuery("AccessLog.findByIpAddress", ipAddress);
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
//===================================== findDailyAffProgramAccessAmount ========
    @Transactional(readOnly=true)
    int findDailyAffProgramAccessAmount(int affProgramId, Date refTime) {
        
        int result = accessLogDao.findQueryResultSetSize("AccessLog.countAffProgramAccessByDate",affProgramId,refTime);
        
        logger.debug("Found {} access for program {} after {}",new Object[]{result,affProgramId,refTime});
        
        return result;
    }

    @Transactional(readOnly=true)
    public GeoData findCountryDataByIP(String ipAddress) {

        GeoData result = null;
        long ip=0;
        try {
            ip = ip2long(ipAddress);
            result = accessLogDao.findSingleItemByQueryString(AccessLog.ACCESSLOG_FINDGEODATABYIP_QUERY,new GeoData(), ip);
        }catch(Exception e) {
            logger.error("Failed to retrieve country by IP.Exception={}, IPAddress={} , IP2Long={}",new Object[]{e,ipAddress,ip});
        }
        return result;
    }
    
    @Transactional(readOnly=true)
    public Collection<AccessLog> findAccessByIDList(List<Integer> idList) {

        Collection<AccessLog> result = null;

        try {
            result = accessLogDao.findByQuery("AccessLog.findByIdList","aclIdList", idList);
        }catch(Exception e) {
            logger.error("Failed to retrieve access for ids .Exception={}, ids={}",e,idList.toArray(new Integer[]{}));
        }
        return result;
    }    
    
    @Transactional(readOnly=true)
    public Collection<AccessLog> findAccessLogByPO(int orderId) {

        Collection<AccessLog> result = null;

        try {
            Page<AccessLog> aclPage = accessLogDao.findPageByQuery("AccessLog.findByPo",new PageCtrl(1,0,10) ,orderId);
            result = aclPage.getItems();
        }catch(Exception e) {
            logger.error("Failed to retrieve access for ids .Exception={}, Po.Id={}",e,orderId);
        }
        return result;
    }    
    
    @Transactional(readOnly=true)    
    public Collection<AccessStats> findAccessAffProgStats(AffProgram prog) {

        Collection<AccessStats> result = null;

        try {
            result = accessLogDao.findByQueryString(AccessLog.ACCESSLOG_STATS_QUERY,new AccessStats(), prog.getId());
        }catch(Exception e) {
            logger.error("Failed to find access affprogram stats  ids .Exception={}",e);
        }
        return result;
    }
    
    @Transactional(propagation= Propagation.REQUIRES_NEW)
    public void saveACLSet(Collection<AccessLog> aclSet) {
        accessLogDao.saveDataSet(aclSet);
    }        
    
    private long ip2long(String addr) {

        String[] addrArray = addr.split("\\.");

        long num = 0;

        for (int i=0;i<addrArray.length;i++) {

            int power = 3-i;

            num += ((Integer.parseInt(addrArray[i])%256 * Math.pow(256,power)));

        }

        return num;
    }
}
