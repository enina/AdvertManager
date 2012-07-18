/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.advertmanager.service;

import java.util.Collection;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.transaction.annotation.Transactional;

import com.mne.advertmanager.dao.GenericDao;
import com.mne.advertmanager.model.AffProgram;
import com.mne.advertmanager.model.AffProgramGroup;

/**
 * 
 * @author Nina Eidelshtein and Misha Lebedev
 */
public class AffProgramService {

	private GenericDao<AffProgram, Integer> affProgramDao;
	private AffProgramGroupService afPrGrService;


	// ============================ findAllAffPrograms ==============================
	@Transactional(readOnly = true)
	public Collection<AffProgram> findAllAffPrograms() {
		return affProgramDao.findByQuery("AffProgram.findAll");
	}

	// =========================== findAffProgramByLink =============================
	@Transactional(readOnly = true)
	public AffProgram findAffProgramByLink(String link) {

		AffProgram result = null;

		Collection<AffProgram> data = affProgramDao.findByQuery("AffProgram.findByAffProgramLink", link);
		if (data != null && data.size() > 0)
			result = data.iterator().next();

		return result;
	}

	// ============================= findAffProgramByID ===========================
	@Transactional(readOnly = true)
	public AffProgram findAffProgramByID(int id) {

		AffProgram result = null;

		String userName = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();

		result = affProgramDao.findSingleItemByQuery("AffProgram.findByIdAndAffiliate", id, userName);

		return result;
	}

	// ============================ createAffProgram  ================================
	@Transactional
	public void createAffProgram(AffProgram affProgram) {

		AffProgramGroup pg = null;
		pg = affProgram.getAffProgramGroup();

		if (pg != null)
			pg = afPrGrService.createOrUpdate(pg);

		affProgram.setAffProgramGroup(pg);
		affProgramDao.create(affProgram);
	}

	@Transactional
	public void save(AffProgram program) {
		affProgramDao.update(program);
	}

	// ==================================== delete ==================================
	@Transactional
	public void delete(int affProgId) {
		affProgramDao.executeUpdateByQuery("AffProgram.deleteById", affProgId);
	}

	public void setAffProgramDao(GenericDao<AffProgram, Integer> AffProgramDao) {
		this.affProgramDao = AffProgramDao;
	}

	public void setAffProgramGroupService(AffProgramGroupService afPrGrService) {
		this.afPrGrService = afPrGrService;
	}


}
