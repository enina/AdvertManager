/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.advertmanager.service;

import com.mne.advertmanager.dao.GenericDao;
import com.mne.advertmanager.model.Partner;
import java.util.Collection;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Misha
 */
public class PartnerService {
    
    
    private GenericDao<Partner,Integer> partnerDao;

    public void setPartnerDao(GenericDao<Partner,Integer> accessLogDao) {
        this.partnerDao = accessLogDao;
    }
    
    @Transactional(readOnly = true)
    public Collection<Partner> findAllPartners() {
        return partnerDao.findByQuery("Partner.findAll");
    }

    @Transactional
    public Integer createPartner(Partner partner) {
        return partnerDao.create(partner);
    }
    
    @Transactional(readOnly = true)
    public Partner findPartnerByName(String itemValue) {
        return partnerDao.findSingleItemByQuery("Partner.findByName",itemValue);
    }
    
    @Transactional(readOnly = true)    
    public Partner findPartnerByEmail(String email) {
        return partnerDao.findSingleItemByQuery("Partner.findByEmail",email);
    }    
    
}
