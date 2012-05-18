/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.advertmanager.service;

import com.mne.advertmanager.model.AffProgram;
import com.mne.advertmanager.dao.GenericDao;
import com.mne.advertmanager.model.AccessLog;
import com.mne.advertmanager.model.AccessSource;
import com.mne.advertmanager.model.Affiliate;
import com.mne.advertmanager.model.AffProgramGroup;
import com.mne.advertmanager.model.PurchaseOrder;
import com.mne.advertmanager.util.EntityFactory;
import java.util.ArrayList;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Nina Eidelshtein and Misha Lebedev
 */
public class DataGenService {
    
    private GenericDao<AffProgram,Long> AffProgramDao;
    private AffiliateService        affService;
    private GenericDao<AffProgramGroup,Long> AffProgramGroupDao;
    private GenericDao<AccessSource,Long> accessSourceDao;
    private GenericDao<AccessLog,Long> accessLogDao;
    private GenericDao<PurchaseOrder,Long> purchaseOrderDao;

    private EntityFactory entityFactory;    
    
    public void setPurchaseOrderDao(GenericDao<PurchaseOrder, Long> purchaseOrderDao) {
        this.purchaseOrderDao = purchaseOrderDao;
    }

    public void setEntityFactory(EntityFactory entityFactory) {
        this.entityFactory = entityFactory;
    }

    public void setAccessLogDao(GenericDao<AccessLog, Long> accessLogDao) {
        this.accessLogDao = accessLogDao;
    }

    public void setAccessSourceDao(GenericDao<AccessSource, Long> accessSourceDao) {
        this.accessSourceDao = accessSourceDao;
    }

    public void setAffiliateService(AffiliateService affiliateService) {
        this.affService = affiliateService;
    }

  

    public void setAffProgramGroupDao(GenericDao<AffProgramGroup, Long> AffProgramGroupDao) {
        this.AffProgramGroupDao = AffProgramGroupDao;
    }

    public void setAffProgramDao(GenericDao<AffProgram,Long> AffProgramDao) {
        this.AffProgramDao = AffProgramDao;
    }
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void generateDummyData() {
        
        int rowCounter=0;
        //////////////////////////////////////////////////////////////////////////////////////////////        
        ArrayList<Affiliate> affList = new ArrayList<Affiliate>();
        for (int i = 0; i < 5;++i) {
            Affiliate curAff = entityFactory.makeAffiliate();
            affList.add(curAff);        
            affService.createAffiliate(curAff);
            rowCounter++;
        }
        
        //////////////////////////////////////////////////////////////////////////////////////////////        
        ArrayList<AffProgramGroup> pgList = new ArrayList<AffProgramGroup>();
        for (int i = 0; i < 10;++i) {
            AffProgramGroup curProdGroup = entityFactory.makeAffProgramGroup();
            curProdGroup.setAffiliateId(affList.get(i%affList.size()));
            pgList.add(curProdGroup);        
            AffProgramGroupDao.create(curProdGroup);
            rowCounter++;
        }
        

        
        //////////////////////////////////////////////////////////////////////////////////////////////        
        ArrayList<AffProgram> AffProgramSet = new ArrayList<AffProgram>();
        for (int i = 0; i < 100;++i) {
            AffProgram curProd = entityFactory.makeAffProgram();
            AffProgramSet.add(curProd);
            curProd.setAffProgramGroup(pgList.get(i/pgList.size()));
            AffProgramDao.create(curProd);
            rowCounter++;
        }
        
        AffProgramDao.flush();
        
        
        ///////////////////////////////////////////////////////////////////////////////////////////////        
        ArrayList<AccessSource> accessSourceList = new ArrayList<AccessSource>();
        for (int i = 0; i < 10;++i) {
            AccessSource curAccessSource = entityFactory.makeAccessSource();
            accessSourceList.add(curAccessSource);  
            accessSourceDao.create(curAccessSource);
        }
        
        ///////////////////////////////////////////////////////////////////////////////////////////////        
        ArrayList<AccessLog> accessLogList = new ArrayList<AccessLog>();
        ArrayList<PurchaseOrder> orderList = new ArrayList<PurchaseOrder>();
        for (int i = 0; i < 100000;++i) {
            AccessLog curAccessLog = entityFactory.makeAccessLog();
            AffProgram curAffProgram  = AffProgramSet.get(i%AffProgramSet.size());
            curAccessLog.setAffProgram(curAffProgram);
            curAccessLog.setSourceDomainId(accessSourceList.get(i%accessSourceList.size()));
            accessLogList.add(curAccessLog);  
            accessLogDao.create(curAccessLog);
            rowCounter++;
            if ((i % 1000)==0) {
                PurchaseOrder curOrder = entityFactory.makePurchaseOrder();
                curOrder.setAffProgram(curAffProgram);
                orderList.add(curOrder);
                purchaseOrderDao.create(curOrder);
                rowCounter++;
            }
            
            if ((rowCounter% 50)==0)
                accessLogDao.flush();
        }
    }
}
