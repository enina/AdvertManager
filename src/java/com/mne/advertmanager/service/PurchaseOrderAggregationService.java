/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.advertmanager.service;

import com.mne.advertmanager.dao.GenericDao;
import com.mne.advertmanager.model.AffProgram;
import com.mne.advertmanager.model.PurchaseOrderAggregation;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Misha
 */
public class PurchaseOrderAggregationService {
    
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(PurchaseOrderAggregationService.class);
    
    private GenericDao<PurchaseOrderAggregation,Integer> totalPoAggrDao;
    private GenericDao<PurchaseOrderAggregation,Integer> prevMonthPoAggrDao;
    private GenericDao<PurchaseOrderAggregation,Integer> curMonthPoAggrDao;
    private GenericDao<PurchaseOrderAggregation,Integer> dailyPoAggrDao;
    private AccessLogService                             accessService;
    private AffProgramService                            affProgramService;
    private BillingProjectService                        blngService;

    public void setTotalAggr(GenericDao<PurchaseOrderAggregation,Integer> totalPoAggrDao) {
        this.totalPoAggrDao = totalPoAggrDao;
    }

    public void setAccessService(AccessLogService accessService) {
        this.accessService = accessService;
    }

    public void setCurMonthPoAggrDao(GenericDao<PurchaseOrderAggregation, Integer> curMonthPoAggrDao) {
        this.curMonthPoAggrDao = curMonthPoAggrDao;
    }

    public void setDailyPoAggrDao(GenericDao<PurchaseOrderAggregation, Integer> dailyPoAggrDao) {
        this.dailyPoAggrDao = dailyPoAggrDao;
    }

    public void setPrevMonthPoAggrDao(GenericDao<PurchaseOrderAggregation, Integer> prevMonthPoAggrDao) {
        this.prevMonthPoAggrDao = prevMonthPoAggrDao;
    }

    public void setTotalPoAggrDao(GenericDao<PurchaseOrderAggregation, Integer> totalPoAggrDao) {
        this.totalPoAggrDao = totalPoAggrDao;
    }

    public void setAffProgramService(AffProgramService affProgramService) {
        this.affProgramService = affProgramService;
    }

    public void setBlngService(BillingProjectService blngService) {
        this.blngService = blngService;
    }
    
    
    
    @Transactional
    public PurchaseOrderAggregation findAffProgramTotalAggrData(int affProgramId) {

        PurchaseOrderAggregation result = findAffProgramAggrData(totalPoAggrDao,"PurchaseOrderTotalAggregation.findByAffProgId", affProgramId);
        
        logger.info("Retrieved total ,aggregation data ,Program {}",affProgramId);
        
        return result;
    }
    
    @Transactional
    public PurchaseOrderAggregation findAffProgramCurMonthAggrData(int affProgramId) {

        PurchaseOrderAggregation result = findAffProgramAggrData(curMonthPoAggrDao,"PurchaseOrderCurMonthAggregation.findByAffProgId", affProgramId);
        
        logger.info("Retrieved current month aggregation data ,Program {}",affProgramId);
        
        
        return result;
    }
    
    @Transactional
    public PurchaseOrderAggregation findAffProgramPrevMonthAggrData(int affProgramId) {

        PurchaseOrderAggregation result = findAffProgramAggrData(prevMonthPoAggrDao,"PurchaseOrderPrevMonthAggregation.findByAffProgId", affProgramId);
        
        logger.info("Retrieved prev month aggregation data ,Program {}",affProgramId);
        
        return result;
    }
    
    @Transactional
    public PurchaseOrderAggregation findAffProgramDailyAggrData(int affProgramId) {

        PurchaseOrderAggregation result = findAffProgramAggrData(dailyPoAggrDao,"PurchaseOrderDailyAggregation.findByAffProgId", affProgramId);
        
        logger.info("Retrieved daily aggregation data ,Program {}",affProgramId);
        
        return result;
    }    

    public void calculateAggrData() {
        
        HashSet<AffProgram> affProgs = new HashSet<AffProgram>(affProgramService.findAllAffPrograms());
        Iterator<AffProgram> affProgIter = affProgs.iterator();
        AffProgram curAffProgram = null;
        while (affProgIter.hasNext()) {
            curAffProgram = affProgIter.next();
            if (curAffProgram != null) {
                blngService.importBillingData(curAffProgram);
                calculateAffProgramAggrData(curAffProgram.getId());
            }
        }
    }

    
    private PurchaseOrderAggregation findDailyAffProgramAggrData(int affProgramId, Date refTime) {
        
        PurchaseOrderAggregation result = dailyPoAggrDao.findSingleItemByQuery("PurchaseOrderAggregation.calculateAffProgDataByIdAndTime",affProgramId,refTime);
        
        logger.debug("Found {} purchase orders of total={} for program {} after {}",
                        new Object[]{result.getPurchaseAmount(),result.getTotalSum(),affProgramId,refTime});
        
        return result;
    }
    

    @Transactional
    public void calculateAffProgramAggrData(int affProgramId) {

        logger.info("Aggregation data calculation , Program {}",affProgramId);
        Calendar cal = Calendar.getInstance();
        //cal.add(Calendar.DATE, -1);
        cal.clear();
        cal.set(2012,Calendar.APRIL, 1,1,0);
        Date refTime = cal.getTime();
        
        
        int accessCount = accessService.findDailyAffProgramAccessAmount(affProgramId,refTime);
        PurchaseOrderAggregation dailyPOData = findDailyAffProgramAggrData(affProgramId, refTime);
        dailyPOData.setAccessAmount(accessCount);
         
        
        PurchaseOrderAggregation totalAggrData = findAffProgramTotalAggrData(affProgramId);
        totalAggrData.addData(dailyPOData);
        
        PurchaseOrderAggregation curMonthAggrData = findAffProgramCurMonthAggrData(affProgramId);
        if (cal.get(Calendar.DAY_OF_MONTH)==1) {
            updatePrevMonthData(affProgramId,curMonthAggrData,dailyPOData);
            logger.info("Shifting monthly statistics");
        }else {
            curMonthAggrData.addData(dailyPOData);
            logger.info("Update current month statistics");
        }
        
        updateDailyAggrData(affProgramId,dailyPOData);
    }


    private void updatePrevMonthData(int affProgramId,PurchaseOrderAggregation curMonthAggrData,PurchaseOrderAggregation dailyPOData) {
        
        PurchaseOrderAggregation prevMonthAggrData = findAffProgramPrevMonthAggrData(affProgramId);
        
        if (prevMonthAggrData != null)
            prevMonthAggrData.copyFrom(curMonthAggrData);

        if (curMonthAggrData != null)
            curMonthAggrData.copyFrom(dailyPOData);
        
    }
    
    
    private PurchaseOrderAggregation findAffProgramAggrData(GenericDao<PurchaseOrderAggregation,Integer> aggrPODao,String query,int affProgramId) {

        PurchaseOrderAggregation result = aggrPODao.findSingleItemByQuery(query, affProgramId);
        
        if (result==null) {
            result = new PurchaseOrderAggregation(0,0,0,new AffProgram(affProgramId));
            aggrPODao.create(result);
        }
        
        return result;
    }
    
    
    private void updateDailyAggrData(int affProgramId,PurchaseOrderAggregation dailyPOData) {
        
        PurchaseOrderAggregation dailyAggrData = findAffProgramDailyAggrData(affProgramId);
        
        if (dailyAggrData != null)
            dailyAggrData.copyFrom(dailyPOData);
    }




    
}
