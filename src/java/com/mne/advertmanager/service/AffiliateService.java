/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.advertmanager.service;

import com.mne.advertmanager.dao.GenericDao;
import com.mne.advertmanager.model.Affiliate;
import java.util.Collection;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Nina Eidelshtein and Misha Lebedev
 */
public class AffiliateService {
 
    private GenericDao<Affiliate,Long> affiliateDao;
    
    private JdbcTemplate jdbcTemplate;

    public void setAffiliateDao(GenericDao<Affiliate, Long> affiliateDao) {
        this.affiliateDao = affiliateDao;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    
    
    @Transactional(readOnly = true)
    public Collection<Affiliate> findAllAffiliates() {
        return affiliateDao.findByQuery("Affiliate.findAll");
    }

    @Transactional
    public void createAffiliate(Affiliate affiliate) {
        affiliateDao.create(affiliate);
        jdbcTemplate.update("insert into authorities values(?,?)",new Object[]{affiliate.getId(),"ROLE_USER"});
    }
}
