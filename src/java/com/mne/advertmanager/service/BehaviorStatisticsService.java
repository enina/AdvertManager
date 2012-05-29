/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.advertmanager.service;

import com.mne.advertmanager.dao.GenericDao;
import com.mne.advertmanager.model.AffProgram;
import com.mne.advertmanager.model.FilterableBehaviorStatistics;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Misha
 */
public class BehaviorStatisticsService {
    
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(BehaviorStatisticsService.class);
    
    private GenericDao<FilterableBehaviorStatistics,Integer> totalBehaviorStatsDao;
    private GenericDao<FilterableBehaviorStatistics,Integer> prevMonthBehaviorStatsDao;
    private GenericDao<FilterableBehaviorStatistics,Integer> curMonthBehaviorStatsDao;
    private GenericDao<FilterableBehaviorStatistics,Integer> dailyBehaviorStatsDao;
    private AccessLogService                             accessService;
    private AffProgramService                            affProgramService;
    private BillingProjectService                        blngService;


    public void setAccessService(AccessLogService accessService) {
        this.accessService = accessService;
    }

    public void setAffProgramService(AffProgramService affProgramService) {
        this.affProgramService = affProgramService;
    }

    public void setBlngService(BillingProjectService blngService) {
        this.blngService = blngService;
    }

    public void setTotalBehaviorStatsDao(GenericDao<FilterableBehaviorStatistics,Integer> totalBehaviorStatsDao) {
        this.totalBehaviorStatsDao = totalBehaviorStatsDao;
    }

    public void setPrevMonthBehaviorStatsDao(GenericDao<FilterableBehaviorStatistics, Integer> prevMonthBehaviorStatsDao) {
        this.prevMonthBehaviorStatsDao = prevMonthBehaviorStatsDao;
    }

    public void setCurMonthBehaviorStatsDao(GenericDao<FilterableBehaviorStatistics, Integer> curMonthBehaviorStatsDao) {
        this.curMonthBehaviorStatsDao = curMonthBehaviorStatsDao;
    }

    public void setDailyBehaviorStatsDao(GenericDao<FilterableBehaviorStatistics, Integer> dailyBehaviorStatsDao) {
        this.dailyBehaviorStatsDao = dailyBehaviorStatsDao;
    }

    
    
    @Transactional
    public Set<FilterableBehaviorStatistics> findTotalAffProgramStatistics(int affProgramId) {

        FilterableBehaviorStatistics result = findAffProgramStatistics(totalBehaviorStatsDao,"TotalBehaviorStats.findByAffProgId", affProgramId);
        
        logger.info("Retrieved total ,aggregation data ,Program {}",affProgramId);
        
        return result;
    }
    
    @Transactional
    public FilterableBehaviorStatistics findCurMonthAffProgramStatistics(int affProgramId) {

        FilterableBehaviorStatistics result = findAffProgramStatistics(curMonthBehaviorStatsDao,"CurMonthBehaviorStats.findByAffProgId", affProgramId);
        
        logger.info("Retrieved current month aggregation data ,Program {}",affProgramId);
        
        
        return result;
    }
    
    @Transactional
    public FilterableBehaviorStatistics findPrevMonthAffProgramStatistics(int affProgramId) {

        FilterableBehaviorStatistics result = findAffProgramStatistics(prevMonthBehaviorStatsDao,"PrevMonthBehaviorStats.findByAffProgId", affProgramId);
        
        logger.info("Retrieved prev month aggregation data ,Program {}",affProgramId);
        
        return result;
    }
    
    @Transactional
    public FilterableBehaviorStatistics findDailyAffProgramStatistics(int affProgramId) {

        FilterableBehaviorStatistics result = findAffProgramStatistics(dailyBehaviorStatsDao,"DailyBehaviorStats.findByAffProgId", affProgramId);
        
        logger.info("Retrieved daily aggregation data ,Program {}",affProgramId);
        
        return result;
    }    

    @Scheduled(cron="0 0 4 * * ?")
    public void calculate() {
        
        logger.info("Started behavior statistics calculation");
        
        HashSet<AffProgram> affProgs = new HashSet<AffProgram>(affProgramService.findAllAffPrograms());
        if (affProgs != null) {
            logger.info("Retrieved {} affiliate programs",affProgs.size());
            Iterator<AffProgram> affProgIter = affProgs.iterator();
            AffProgram curAffProgram = null;
            while (affProgIter.hasNext()) {
                curAffProgram = affProgIter.next();
                if (curAffProgram != null) {
                    blngService.importBillingData(curAffProgram);
                    calculateAffProgramStatistics(curAffProgram.getId());
                }
            }
        }
        
        logger.info("Finished behavior statistics calculation");
    }

    
    private Set<FilterableBehaviorStatistics> createDailyAffProgramStatistics(int affProgramId, Date refTime) {
        
        FilterableBehaviorStatistics total = dailyBehaviorStatsDao.findSingleItemByQuery("BehaviorStats.calculateAffProgDataByIdAndTime",affProgramId,refTime);
        
        int accessCount = accessService.findDailyAffProgramAccessAmount(affProgramId,refTime);
        
        logger.debug("Program={} , Period=After {} ::Access={}, PO={} , Commision={}",
                      new Object[]{affProgramId,refTime,accessCount , total.getPurchaseAmount(),total.getTotalCommision()});
        
        total.setCountryName("TOTAL");
        total.setAccessAmount(accessCount);
        
        HashSet<FilterableBehaviorStatistics> result = new HashSet<FilterableBehaviorStatistics>();
        result.addAll(dailyBehaviorStatsDao.findByQuery("BehaviorStats.countAffProgramPeriodicAccessByDomain", affProgramId,refTime));
        result.add(total);
                
        
        return result;
    }
    

    @Transactional
    public void calculateAffProgramStatistics(int affProgramId) {

        logger.info("Started behavior statistics calculation , for program {}",affProgramId);
        Calendar cal = Calendar.getInstance();
        //cal.add(Calendar.DATE, -1);
        cal.clear();
        //just for testing should be removed
        cal.set(2012,Calendar.FEBRUARY, 1,1,0);
        Date refTime = cal.getTime();
        
        
        
        Set<FilterableBehaviorStatistics> dailyStatSet = createDailyAffProgramStatistics(affProgramId, refTime);
        
        Set<FilterableBehaviorStatistics> totalStatSet = findTotalAffProgramStatistics(affProgramId);
        //totalStats.add(dailyStatSet);
        
        FilterableBehaviorStatistics curMonthAggrData = findCurMonthAffProgramStatistics(affProgramId);
        if (cal.get(Calendar.DAY_OF_MONTH)==1) {
            updatePrevMonthStatistics(affProgramId,curMonthAggrData,dailyPOData);
            logger.info("Shifting monthly statistics");
        }else {
            curMonthAggrData.add(dailyStatSet);
            logger.info("Update current month statistics");
        }
        
        updateDailyStatistics(affProgramId,dailyStatSet);
        
        logger.info("Finished behavior statistics calculation , Program {}",affProgramId);
    }


    private void updatePrevMonthStatistics(int affProgramId,FilterableBehaviorStatistics curMonthStats,FilterableBehaviorStatistics dailyStats) {
        
        FilterableBehaviorStatistics prevMonthAggrData = findPrevMonthAffProgramStatistics(affProgramId);
        
        if (prevMonthAggrData != null)
            prevMonthAggrData.copyFrom(curMonthStats);

        if (curMonthStats != null)
            curMonthStats.copyFrom(dailyStats);
        
    }
    
    
    private FilterableBehaviorStatistics findAffProgramStatistics(GenericDao<FilterableBehaviorStatistics,Integer> statsDao,String query,int affProgramId) {

        FilterableBehaviorStatistics result = statsDao.findSingleItemByQuery(query, affProgramId);
        
        if (result==null) {
            result = new FilterableBehaviorStatistics(0,0,0,new AffProgram(affProgramId));
            statsDao.create(result);
        }
        
        return result;
    }
    
    
    private void updateDailyStatistics(int affProgramId,FilterableBehaviorStatistics curDailyStats) {
        
        FilterableBehaviorStatistics prevDailyStats = findDailyAffProgramStatistics(affProgramId);
        
        if (prevDailyStats != null)
            prevDailyStats.copyFrom(curDailyStats);
    }
    
}
