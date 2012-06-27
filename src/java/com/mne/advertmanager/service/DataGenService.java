/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.advertmanager.service;

import java.util.ArrayList;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mne.advertmanager.dao.GenericDao;
import com.mne.advertmanager.model.AccessLog;
import com.mne.advertmanager.model.AccessSource;
import com.mne.advertmanager.model.AffProgram;
import com.mne.advertmanager.model.AffProgramGroup;
import com.mne.advertmanager.model.Affiliate;
import com.mne.advertmanager.model.PurchaseOrder;
import com.mne.advertmanager.util.EntityFactory;

/**
 * 
 * @author Nina Eidelshtein and Misha Lebedev
 */
public class DataGenService {

	private AffiliateService affService;
	private GenericDao<AffProgramGroup, Long> AffProgramGroupDao;
	private GenericDao<AccessSource, Long> accessSourceDao;
	private GenericDao<AccessLog, Long> accessLogDao;
	private GenericDao<PurchaseOrder, Long> purchaseOrderDao;

	private EntityFactory entityFactory;

	public void setPurchaseOrderDao(GenericDao<PurchaseOrder, Long> purchaseOrderDao) {
		this.purchaseOrderDao = purchaseOrderDao;
	}

	public void setEntityFactory(EntityFactory entityFactory) {
		this.entityFactory = entityFactory;
	}

	public void setAccessLogDao(GenericDao<AccessLog, Long> accessLogDao) {
		this.accessLogDao = accessLogDao;
	}

	public void setAccessSourceDao(GenericDao<AccessSource, Long> accessSourceDao) {
		this.accessSourceDao = accessSourceDao;
	}

	public void setAffiliateService(AffiliateService affiliateService) {
		this.affService = affiliateService;
	}

	public void setAffProgramGroupDao(GenericDao<AffProgramGroup, Long> AffProgramGroupDao) {
		this.AffProgramGroupDao = AffProgramGroupDao;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void generateDummyData() {

		// ////////////////////////////////////////////////////////////////////////////////////////////
		ArrayList<Affiliate> affList = new ArrayList<Affiliate>();
		for (int i = 0; i < 5; ++i) {
			Affiliate curAff = entityFactory.makeAffiliate();
			affList.add(curAff);
			affService.createAffiliate(curAff);
		}

		// ////////////////////////////////////////////////////////////////////////////////////////////
		ArrayList<AffProgramGroup> pgList = new ArrayList<AffProgramGroup>();
		for (int i = 0; i < 10; ++i) {
			AffProgramGroup curProdGroup = entityFactory.makeAffProgramGroup();
			curProdGroup.setAffiliateId(affList.get(i % affList.size()));
			pgList.add(curProdGroup);
			AffProgramGroupDao.create(curProdGroup);
		}

		// ////////////////////////////////////////////////////////////////////////////////////////////
		ArrayList<AffProgram> affProgramSet = new ArrayList<AffProgram>();
		int fromIdx = 0;
		int toIdx = 0;
		for (int i = 0; i < 100; ++i) {
			AffProgram curProd = entityFactory.makeAffProgram();
			affProgramSet.add(curProd);
			curProd.setAffProgramGroup(pgList.get(i / pgList.size()));
		}

		// /////////////////////////////////////////////////////////////////////////////////////////////
		ArrayList<AccessSource> accessSourceList = new ArrayList<AccessSource>();
		for (int i = 0; i < 10; ++i) {
			AccessSource curAccessSource = entityFactory.makeAccessSource();
			accessSourceList.add(curAccessSource);
			accessSourceDao.create(curAccessSource);
		}

		// /////////////////////////////////////////////////////////////////////////////////////////////
		ArrayList<AccessLog> accessLogList = new ArrayList<AccessLog>();
		ArrayList<PurchaseOrder> orderList = new ArrayList<PurchaseOrder>();
		fromIdx = toIdx = 0;
		for (int i = 0; i < 100000; ++i) {
			AccessLog curAccessLog = entityFactory.makeAccessLog();
			AffProgram curAffProgram = affProgramSet.get(i % affProgramSet.size());
			curAccessLog.setAffProgram(curAffProgram);
			curAccessLog.setSourceDomainId(accessSourceList.get(i % accessSourceList.size()));
			accessLogList.add(curAccessLog);
			toIdx++;
			if (toIdx % 10 == 0) {
				accessLogDao.saveDataSet(accessLogList.subList(fromIdx, toIdx));
				fromIdx = toIdx;
			}
			if ((i % 1000) == 0) {
				PurchaseOrder curOrder = entityFactory.makePurchaseOrder();
				curOrder.setAffProgram(curAffProgram);
				orderList.add(curOrder);
				purchaseOrderDao.create(curOrder);
			}
		}
	}

}
