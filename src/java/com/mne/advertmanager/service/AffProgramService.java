/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.advertmanager.service;

import java.util.Collection;

import org.springframework.transaction.annotation.Transactional;

import com.mne.advertmanager.dao.GenericDao;
import com.mne.advertmanager.model.AffProgram;
import com.mne.advertmanager.model.AffProgramGroup;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

/**
 *
 * @author Nina Eidelshtein and Misha Lebedev
 */
public class AffProgramService {

    private GenericDao<AffProgram, Integer> affProgramDao;
    private AffProgramGroupService afPrGrService;

    public void setAffProgramDao(GenericDao<AffProgram, Integer> AffProgramDao) {
        this.affProgramDao = AffProgramDao;
    }

  

    public void setAffProgramGroupService(AffProgramGroupService afPrGrService) {
        this.afPrGrService = afPrGrService;
    }
    
    

//============================ findAllAffPrograms ==============================
    @Transactional(readOnly = true)
    public Collection<AffProgram> findAllAffPrograms() {
        return affProgramDao.findByQuery("AffProgram.findAll");
    }
//=========================== findAffProgramByLink =============================
    @Transactional(readOnly = true)
    public AffProgram findAffProgramByLink(String link) {
        
        AffProgram result = null;

        Collection<AffProgram> data =  affProgramDao.findByQuery("AffProgram.findByAffProgramLink",link);
        if (data != null && data.size()>0)
            result = data.iterator().next();
        
        return result;
    }    
//============================= findAffProgramByLink ===========================
    @Transactional(readOnly = true)
    public AffProgram findAffProgramByID(int id) {
        
        AffProgram result = null;
        String userName = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        Collection<AffProgram> tmp = affProgramDao.findByQuery("AffProgram.findByIdAndAffiliate",id,userName);
        if (tmp != null && tmp.size()>0)
            result = tmp.iterator().next();
       
        return result;
    }  
//============================ createAffProgram ================================
    @Transactional
    public void createAffProgram(AffProgram AffProgram) {
        
        AffProgramGroup pg = null;
        pg = AffProgram.getAffProgramGroup();
        
 
        if (pg != null)
            pg = afPrGrService.createOrUpdate(pg);
        
        AffProgram.setAffProgramGroup(pg);
        affProgramDao.create(AffProgram);
    }
    
}
