/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.advertmanager.service;

import com.mne.advertmanager.dao.GenericDao;
import com.mne.advertmanager.model.Affiliate;
import com.mne.advertmanager.model.ProductGroup;
import java.util.Collection;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Nina Eidelshtein and Misha Lebedev
 */
public class ProductGroupService {

    private GenericDao<ProductGroup, Integer> productGroupDao;
    private AffiliateService affiliateService;

    public void setProductGroupDao(GenericDao<ProductGroup, Integer> productGroupDao) {
        this.productGroupDao = productGroupDao;
    }

    public void setAffiliateService(AffiliateService affiliateService) {
        this.affiliateService = affiliateService;
    }

//============================ findAllProducts =================================
    @Transactional(readOnly = true)//, propagation = Propagation.REQUIRED)
    public Collection<ProductGroup> findAffiliateProductGroups(String affName) {
        return productGroupDao.findByQuery("ProductGroup.findAllByAffName", affName);
    }

    @Transactional
    public void createProductGroup(ProductGroup productGroup) {
        productGroupDao.create(productGroup);
    }

    @Transactional(readOnly = true)
    public ProductGroup findById(int pgId) {
        return productGroupDao.read(pgId);
    }

    public ProductGroup createOrUpdate(ProductGroup pg) {

        String userName = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        Affiliate aff = affiliateService.findAffiliateByName(userName);
        pg.setAffiliateId(aff);
        if (pg.getId() == 0) {
            productGroupDao.create(pg);
        } else {
            productGroupDao.update(pg);
        }

        return pg;
    }
}
