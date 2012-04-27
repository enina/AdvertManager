/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.advertmanager.service;

import com.mne.advertmanager.dao.GenericDao;
import com.mne.advertmanager.model.Affiliate;
import com.mne.advertmanager.model.AffProgramGroup;
import java.util.Collection;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Nina Eidelshtein and Misha Lebedev
 */
public class AffProgramGroupService {

    private GenericDao<AffProgramGroup, Integer> apgDao;
    private AffiliateService affiliateService;

    public void setAPGDao(GenericDao<AffProgramGroup, Integer> apgDao) {
        this.apgDao = apgDao;
    }

    public void setAffiliateService(AffiliateService affiliateService) {
        this.affiliateService = affiliateService;
    }

//============================ findAllPrograms =================================
    @Transactional(readOnly = true)//, propagation = Propagation.REQUIRED)
    public Collection<AffProgramGroup> findAffiliateProgramGroups(String affName) {
        return apgDao.findByQuery("AffProgramGroup.findAllByAffName", affName);
    }

    @Transactional
    public void createAffiliateProgramGroup(AffProgramGroup progGroup) {
        apgDao.create(progGroup);
    }

    @Transactional(readOnly = true)
    public AffProgramGroup findById(int pgId) {
        return apgDao.read(pgId);
    }

    public AffProgramGroup createOrUpdate(AffProgramGroup pg) {

        String userName = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        Affiliate aff = affiliateService.findAffiliateByName(userName);
        pg.setAffiliateId(aff);
        if (pg.getId() == 0) {
            apgDao.create(pg);
        } else {
            apgDao.update(pg);
        }

        return pg;
    }
}
