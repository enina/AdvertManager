/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.advertmanager.service;

import com.mne.advertmanager.dao.GenericDao;
import com.mne.advertmanager.model.Affiliate;
import java.util.Collection;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Nina Eidelshtein and Misha Lebedev
 */
public class AffiliateService {
 
    private GenericDao<Affiliate,Long> affiliateDao;

    public void setAffiliateDao(GenericDao<Affiliate, Long> affiliateDao) {
        this.affiliateDao = affiliateDao;
    }
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public Collection<Affiliate> findAllAffiliates() {
        return affiliateDao.findByQuery("Affiliate.findAll");
    }
}
