/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.advertmanager.service;

import com.mne.advertmanager.dao.GenericDao;
import com.mne.advertmanager.model.Affiliate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.springframework.jdbc.core.JdbcTemplate;
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
    public Set<Affiliate> findAllAffiliates() {
        
        HashSet<Affiliate> result = new HashSet<Affiliate>();
        Collection<Affiliate> data = affiliateDao.findByQuery("Affiliate.findAll");

        if (data != null)
            result.addAll(data);
        
        return result;
    }
    
    @Transactional(readOnly = true)
    public Affiliate findAffiliateWithProductGroupsAndProducts(String affiliateName) {
        
        Affiliate result = null;
        
        Collection<Affiliate> data = affiliateDao.findByQuery("Affiliate.findByAffiliateName",affiliateName);

        if (data != null)
            result = data.iterator().next();
        
        return result;
    }    

    @Transactional
    public void createAffiliate(Affiliate affiliate) {
        affiliateDao.create(affiliate);
        jdbcTemplate.update("insert into authorities values(?,?)",new Object[]{affiliate.getId(),"ROLE_USER"});
    }
}
