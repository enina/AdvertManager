/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.advertmanager.service;

import com.mne.advertmanager.dao.GenericDao;
import com.mne.advertmanager.model.PurchaseOrder;
import com.mne.advertmanager.util.POAggrData;
import java.util.Collection;
import java.util.Date;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author ilyae
 */
public class PurchaseOrderService {
 

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(PurchaseOrderService.class);
    
    
    private GenericDao<PurchaseOrder,Integer> poDao;

    public void setPoDao(GenericDao<PurchaseOrder,Integer> poDao) {
        this.poDao = poDao;
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
    
    @Transactional(readOnly=true)
    POAggrData findDailyAffProgramAggrData(int affProgramId, Date refTime) {
        
        POAggrData result = (POAggrData)poDao.findSingleItemByQuery("PurchaseOrder.findAffProgramPOAggrDataByDate",affProgramId,refTime);
        
        logger.debug("Found {} purchase orders of total={} for program {} after {}",
                        new Object[]{result.getPoAmount(),result.getTotalPoSum(),affProgramId,refTime});
        
        return result;
    }
    

}
