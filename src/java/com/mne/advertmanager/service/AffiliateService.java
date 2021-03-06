/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.advertmanager.service;

import com.mne.advertmanager.dao.GenericDao;
import com.mne.advertmanager.model.Affiliate;
import com.mne.advertmanager.model.AffProgramGroup;
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
    private AffProgramGroupService        apgService;
    
    private JdbcTemplate jdbcTemplate;

    /**
     * for each property defined on bean element in application context we must have a corresponding setter functions
     * For example:
     *  <property name="affiliateDao">
            <ref bean="affiliateDao"/>
        </property>
     * Corresponding setter functions is setAffiliateDao
     * @param affiliateDao 
     */
    public void setAffiliateDao(GenericDao<Affiliate, Long> affiliateDao) {
        this.affiliateDao = affiliateDao;
    }

    /**
     * setter for jdbcTemplate  property
     * @param jdbcTemplate 
     */
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void setApgService(AffProgramGroupService apgService) {
        this.apgService = apgService;
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
    public Affiliate findAffiliateWithAffPrograms(String affiliateName) {
        
        Affiliate result = null;
        
        Collection<Affiliate> data = affiliateDao.findByQuery("Affiliate.findByAffiliateNameWithAffProgramsAndGroups",affiliateName);

        result = getFirstElement(data);
        
        return result;
    }    

    /**
     * updates two different tables in database.not readonly because it changes database
     * creates atomic operation which consists of 2 sub-operations:
     *  1.update affiliate table
     *  2 update authorities table (insert role for created user)
     * in case any single insert fails transaction guarantees that none of the tables will be updated
     * @param affiliate 
     */
    @Transactional
    public void createAffiliate(Affiliate affiliate) {
        affiliateDao.create(affiliate);
        jdbcTemplate.update("insert into authorities values(?,?)",new Object[]{affiliate.getId(),"ROLE_USER"});
        AffProgramGroup pg = new AffProgramGroup();
        pg.setGroupName("Default");
        pg.setDescription("Default program group");
        pg.setAffiliateId(affiliate);
        apgService.createAffiliateProgramGroup(pg);
    }

    public Affiliate findAffiliateByName(String affName) {

        Affiliate result = null;
        
        Collection<Affiliate> data = affiliateDao.findByQuery("Affiliate.findByName",affName);

        result = getFirstElement(data);
        
        return result;
    }
    
    private Affiliate getFirstElement(Collection<Affiliate> data) {
        
        Affiliate result = null;
        
        if (data != null)
            result = data.iterator().next();
        
        return result;
    }
}
