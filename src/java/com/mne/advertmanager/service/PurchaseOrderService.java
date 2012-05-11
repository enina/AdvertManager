/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.advertmanager.service;

import com.mne.advertmanager.dao.GenericDao;
import com.mne.advertmanager.model.PurchaseOrder;
import java.util.Collection;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author ilyae
 */
public class PurchaseOrderService {
 
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
}
