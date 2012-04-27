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

/**
 *
 * @author Nina Eidelshtein and Misha Lebedev
 */
public class AffProgramService {

    private GenericDao<AffProgram, Long> AffProgramDao;
    private AffProgramGroupService afPrGrService;

    public void setAffProgramDao(GenericDao<AffProgram, Long> AffProgramDao) {
        this.AffProgramDao = AffProgramDao;
    }

  

    public void setAffProgramGroupService(AffProgramGroupService afPrGrService) {
        this.afPrGrService = afPrGrService;
    }
    
    

//============================ findAllAffPrograms =================================
    @Transactional(readOnly = true)
    public Collection<AffProgram> findAllAffPrograms() {
        return AffProgramDao.findByQuery("AffProgram.findAll");
    }
    
    @Transactional(readOnly = true)
    public AffProgram findAffProgramByLink(String link) {
        
        AffProgram result = null;

        Collection<AffProgram> data =  AffProgramDao.findByQuery("AffProgram.findByAffProgramLink",link);
        if (data != null && data.size()>0)
            result = data.iterator().next();
        
        return result;
    }    

//============================ createAffProgram ===================================
    @Transactional
    public void createAffProgram(AffProgram AffProgram) {
        
        AffProgramGroup pg = null;
        pg = AffProgram.getAffProgramGroupId();
        
 
        if (pg != null)
            pg = afPrGrService.createOrUpdate(pg);
        
        AffProgram.setAffProgramGroupId(pg);
        AffProgramDao.create(AffProgram);
    }
    
}
