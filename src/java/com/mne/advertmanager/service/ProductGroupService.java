/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.advertmanager.service;


import com.mne.advertmanager.dao.GenericDao;
import com.mne.advertmanager.model.ProductGroup;
import java.util.Collection;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Nina Eidelshtein and Misha Lebedev
 */
public class ProductGroupService {

    private GenericDao<ProductGroup, Long> productGroupDao;

    public void setProductGroupDao(GenericDao<ProductGroup, Long> productGroupDao) {
        this.productGroupDao = productGroupDao;
    }

//============================ findAllProducts =================================
    @Transactional(readOnly = true)//, propagation = Propagation.REQUIRED)
    public Collection<ProductGroup> findAffiliateProductGroups(String affName) {
        return productGroupDao.findByQuery("ProductGroup.findAllByAffName",affName);
    }

    @Transactional
    public void createProductGroup(ProductGroup productGroup) {
        productGroupDao.create(productGroup);
    }
}
