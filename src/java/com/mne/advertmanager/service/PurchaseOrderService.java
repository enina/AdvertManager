/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.advertmanager.service;

import com.mne.advertmanager.dao.GenericDao;
import com.mne.advertmanager.model.AccessLog;
import com.mne.advertmanager.model.AffProgram;
import com.mne.advertmanager.model.PurchaseOrder;
import com.mne.advertmanager.util.BatchingSaverService;
import com.mne.advertmanager.util.POStats;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author ilyae
 */
public class PurchaseOrderService implements BatchingSaverService<PurchaseOrder> {
 

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(PurchaseOrderService.class);
    
    
    private GenericDao<PurchaseOrder,Integer> poDao;
    private AccessLogService  aclService;

    public void setPoDao(GenericDao<PurchaseOrder,Integer> poDao) {
        this.poDao = poDao;
    }

    public void setAclService(AccessLogService aclService) {
        this.aclService = aclService;
    }

    
    
    @Transactional(readOnly = true)
    public Collection<PurchaseOrder> findAllPurchaseOrders() {
        return poDao.findByQuery("AccessLog.findAll");
    }

    @Transactional
    public Integer createPurchaseOrder(PurchaseOrder purchaseOrder) {
        return poDao.create(purchaseOrder);
    }
    
    @Transactional(readOnly = true)
    public Collection<PurchaseOrder> findPurchaseOrdersByAffProgamId(int affProgramId) {
        return poDao.findByQuery("PurchaseOrder.findByAffProgramId",affProgramId);
    }
    
    @Transactional(readOnly = true)
    public void calculatePOStats(int progId) {
        
        Collection<POStats> stats = poDao.findByQueryString(PurchaseOrder.PO_STAT_QUERY, new POStats(),progId);
        
        if (stats == null) {
            logger.info("Program={} ,No access found for the purchases.",progId);
            return;
        }
        HashMap<Integer,POStats> poStatMap  = new HashMap<Integer,POStats>();
        HashMap<Integer,POStats> aclMap = new HashMap<Integer,POStats>();
        HashMap<Integer,PurchaseOrder>      poMap = new HashMap<Integer,PurchaseOrder>();
        
        for (POStats pos:stats) {
            poStatMap.put(pos.getPoId(),pos);
            aclMap.put(pos.getAclId(),pos);

        }
        
        Set<PurchaseOrder> poList = new HashSet<PurchaseOrder>(poDao.findByQuery("PurchaseOrder.findByIdList","poIdList", poStatMap.keySet()));
        HashSet<AccessLog> aclList = new HashSet<AccessLog>(aclService.findAccessByIDList(new ArrayList<Integer>(aclMap.keySet())));
        
        POStats pos = null;
        if (poList == null || aclList == null) {
            logger.error("No purchases or accesses were retrieved");
            return;
        }
        for (PurchaseOrder po:poList) {
            pos = poStatMap.get(po.getId());
            po.setAccessAmount(pos.getAclCount());
            poMap.put(po.getId(), po);
        }

       

        
        for (AccessLog acl:aclList) {
            pos = aclMap.get(acl.getId());
            acl.setPo(poMap.get(pos.getPoId()));
        }        
        
        //saveBatch(poList);
        logger.debug("Saving po batch.Size={}",poList.size());
        poDao.saveDataSet(poList);
        logger.debug("Saving acl batch.Size={}",aclList.size());
        aclService.saveBatch(aclList);
    }    
    
    @Transactional (readOnly = true)
    public Collection<POStats> retrieveAffProgPOByDateStats(AffProgram prog) {
        
        Collection<POStats> result = poDao.findByQueryString(PurchaseOrder.PO_AMOUNT_BY_DATE_QUERY, new POStats(),prog.getId());   

        return result;
    }
    
    @Override
    @Transactional(propagation= Propagation.REQUIRES_NEW)
    public void saveBatch(Set<PurchaseOrder> batch) {
        poDao.saveDataSet(batch);
    }    

}
