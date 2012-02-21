/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.advertmanager.service;

import com.mne.advertmanager.model.Product;
import com.mne.advertmanager.dao.GenericDao;
import com.mne.advertmanager.model.AccessLog;
import com.mne.advertmanager.model.AccessSource;
import com.mne.advertmanager.model.Affiliate;
import com.mne.advertmanager.model.Author;
import com.mne.advertmanager.model.ProductGroup;
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
    
    private GenericDao<Product,Long> productDao;
    private AffiliateService        affService;
    private GenericDao<Author,Long> authorDao;
    private GenericDao<ProductGroup,Long> productGroupDao;
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

    public void setAuthorDao(GenericDao<Author, Long> authorDao) {
        this.authorDao = authorDao;
    }

    public void setProductGroupDao(GenericDao<ProductGroup, Long> productGroupDao) {
        this.productGroupDao = productGroupDao;
    }

    public void setProductDao(GenericDao<Product,Long> productDao) {
        this.productDao = productDao;
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
        ArrayList<ProductGroup> pgList = new ArrayList<ProductGroup>();
        for (int i = 0; i < 10;++i) {
            ProductGroup curProdGroup = entityFactory.makeProductGroup();
            curProdGroup.setAffiliateId(affList.get(i%affList.size()));
            pgList.add(curProdGroup);        
            productGroupDao.create(curProdGroup);
            rowCounter++;
        }
        
        //////////////////////////////////////////////////////////////////////////////////////////////                
        ArrayList<Author> authorSet = new ArrayList<Author>();
        for (int i = 0; i < 10;++i) {
            Author curAuthor = entityFactory.makeAuthor();
            authorSet.add(curAuthor);        
            authorDao.create(curAuthor);
            rowCounter++;
        }
        
        //////////////////////////////////////////////////////////////////////////////////////////////        
        ArrayList<Product> productSet = new ArrayList<Product>();
        for (int i = 0; i < 100;++i) {
            Product curProd = entityFactory.makeProduct();
            productSet.add(curProd);
            curProd.setAuthorId(authorSet.get(i/authorSet.size()));
            curProd.setProductGroupId(pgList.get(i/pgList.size()));
            productDao.create(curProd);
            rowCounter++;
        }
        
        productDao.flush();
        
        
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
            Product curProduct  = productSet.get(i%productSet.size());
            curAccessLog.setProductId(curProduct);
            curAccessLog.setSourceDomainId(accessSourceList.get(i%accessSourceList.size()));
            accessLogList.add(curAccessLog);  
            accessLogDao.create(curAccessLog);
            rowCounter++;
            if ((i % 1000)==0) {
                PurchaseOrder curOrder = entityFactory.makePurchaseOrder();
                curOrder.setAccessId(curAccessLog);
                curOrder.setProductId(curProduct);
                orderList.add(curOrder);
                purchaseOrderDao.create(curOrder);
                rowCounter++;
            }
            
            if ((rowCounter% 50)==0)
                accessLogDao.flush();
        }
    }
}
