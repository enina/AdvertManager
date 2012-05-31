/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.advertmanager.service;

import com.mne.advertmanager.dao.GenericDao;
import com.mne.advertmanager.model.AffProgram;
import com.mne.advertmanager.model.FilterableBehaviorStatistics;
import java.util.*;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Propagation;
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
    public FilterableBehaviorStatistics findTotalAffProgramStatistics(int affProgramId) {

        FilterableBehaviorStatistics result = findAffProgramStatistics(totalBehaviorStatsDao,"TotalBehaviorStats.findAffProgCountryStats", affProgramId,"Filter.All");
        
        logger.info("ProgramId={},Retrieved total statistics for all filters",affProgramId);
        
        return result;
    }
    
    @Transactional
    public FilterableBehaviorStatistics findCurMonthAffProgramStatistics(int affProgramId) {

        FilterableBehaviorStatistics result = findAffProgramStatistics(curMonthBehaviorStatsDao,"CurMonthBehaviorStats.findByAffProgId", affProgramId,"Filter.All");
        
        logger.info("ProgramId={}.Retrieved current month stats ",affProgramId);
        
        
        return result;
    }
    
    @Transactional
    public FilterableBehaviorStatistics findPrevMonthAffProgramStatistics(int affProgramId) {

        FilterableBehaviorStatistics result = findAffProgramStatistics(prevMonthBehaviorStatsDao,"PrevMonthBehaviorStats.findByAffProgId", affProgramId);
        
        logger.info("Retrieved prev month aggregation data ,Program {}",affProgramId);
        
        return result;
    }
    
    @Transactional
    public FilterableBehaviorStatistics findDailyAffProgramGeneralStats(int affProgramId) {

        FilterableBehaviorStatistics result = findAffProgramStatistics(dailyBehaviorStatsDao,"DailyBehaviorStats.findByAffProgId", affProgramId);
        
        logger.info("ProgramId={}:::Retrieved daily statistics",affProgramId);
        
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

    /*
     * 
    <query name="TotalBehaviorStats.findAffProgrCountryStats"><![CDATA[
        from TotalBehaviorStats where countryName=? and affProgram.id=? 
    ]]>
    </query>     * 
     */
    private Set<FilterableBehaviorStatistics> findDailyAffProgramStatsSet(int affProgramId, Date refTime) {
        
        FilterableBehaviorStatistics total = dailyBehaviorStatsDao.findSingleItemByQuery("BehaviorStats.calculateAffProgStatsByIdAndTime",affProgramId,refTime);
        
        int accessCount = accessService.findDailyAffProgramAccessAmount(affProgramId,refTime);
        
        logger.debug("Program={} , Period=After {} ::Access={}, PO={} , Commision={}",
                      new Object[]{affProgramId,refTime,accessCount , total.getPurchaseAmount(),total.getTotalCommision()});
        
        total.setCountryName("Filter.ALL");
        total.setAccessAmount(accessCount);
        
        HashSet<FilterableBehaviorStatistics> result = new HashSet<FilterableBehaviorStatistics>();
        result.addAll(dailyBehaviorStatsDao.findByQuery("BehaviorStats.countAffProgramPeriodicAccessByDomain", affProgramId,refTime));
        result.add(total);
                
        return result;
    }
    
    private TreeMap<String,FilterableBehaviorStatistics> builFBSTree(Collection<FilterableBehaviorStatistics> data) {
        
        TreeMap<String,FilterableBehaviorStatistics> result = new TreeMap<String,FilterableBehaviorStatistics>();
        if (data != null) {
            for(FilterableBehaviorStatistics fbs:data)
                result.put(fbs.getKey(), fbs);
        }
                
        return result;
    }
    
    private TreeMap<String,FilterableBehaviorStatistics> findTotalAffProgramStatsSet(int affProgramId) {    
        
        Collection<FilterableBehaviorStatistics> data = totalBehaviorStatsDao.findByQuery("TotalBehaviorStats.findAffProgStats", affProgramId);
                
        return builFBSTree(data);
    }    
    
    private TreeMap<String,FilterableBehaviorStatistics> findCurMonthAffProgramStatsSet(int affProgramId) {    

        Collection<FilterableBehaviorStatistics> data = curMonthBehaviorStatsDao.findByQuery("CurMonthBehaviorStats.findAffProgStats", affProgramId);
               
        return builFBSTree(data);
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
        
        
        
        Set<FilterableBehaviorStatistics> todayStatSet = findDailyAffProgramStatsSet(affProgramId, refTime);
        
        TreeMap<String,FilterableBehaviorStatistics> totalTree = findTotalAffProgramStatsSet(affProgramId);
        
        updateTotalStatistics(totalTree, todayStatSet);
        
        TreeMap<String,FilterableBehaviorStatistics> cmTree = findCurMonthAffProgramStatsSet(affProgramId);
        
        if (cal.get(Calendar.DAY_OF_MONTH)==1) {
            updatePrevMonthStatistics(affProgramId,cmTree,todayStatSet);
        }else {
            updateCurMonthStatistics(cmTree,todayStatSet);
        }
        
        updateDailyStatistics(affProgramId,todayStatSet);
        
        logger.info("Finished behavior statistics calculation , Program {}",affProgramId);
    }


    private void updatePrevMonthStatistics(int affProgramId,TreeMap<String,FilterableBehaviorStatistics> curMonthStatsSet,Set<FilterableBehaviorStatistics> todayStatsSet) {
        
        cleanAffProgPrevMonthStatistics(affProgramId);
        
        for (FilterableBehaviorStatistics fbs:curMonthStatsSet.values()) {
            fbs.setId(-1);
            prevMonthBehaviorStatsDao.create(fbs);
        }
        cleanAffProgCurMonthStatistics(affProgramId);

        for (FilterableBehaviorStatistics fbs:todayStatsSet) {
            fbs.setId(-1);
            curMonthBehaviorStatsDao.create(fbs);
        }
        
        logger.info("Shifted monthly statistics");
        
    }
    
    private void updateCurMonthStatistics(TreeMap<String,FilterableBehaviorStatistics> cmTree,Set<FilterableBehaviorStatistics> todayStatsSet) {
        for (FilterableBehaviorStatistics todayFbs:todayStatsSet) {
            FilterableBehaviorStatistics cmFbs =  cmTree.get(todayFbs.getKey());
            if (cmFbs!= null)
                cmFbs.add(todayFbs);
            else
                curMonthBehaviorStatsDao.create(todayFbs);
        }

        logger.info("Updated current month statistics");
    }
    
    private void updateTotalStatistics(TreeMap<String,FilterableBehaviorStatistics> totalTree,Set<FilterableBehaviorStatistics> todayStatsSet) {
        for (FilterableBehaviorStatistics todayFbs:todayStatsSet) {
            FilterableBehaviorStatistics cmFbs =  totalTree.get(todayFbs.getKey());
            if (cmFbs!= null)
                cmFbs.add(todayFbs);
            totalBehaviorStatsDao.create(todayFbs);
        }

        logger.info("Updated total statistics");
    }
    
    
    private FilterableBehaviorStatistics findAffProgramStatistics(GenericDao<FilterableBehaviorStatistics,Integer> statsDao,String query,int affProgramId,Object... params) {

        FilterableBehaviorStatistics result = statsDao.findSingleItemByQuery(query, affProgramId,params);
        
        if (result==null) {
            result = new FilterableBehaviorStatistics(0,0,0,new AffProgram(affProgramId));
            statsDao.create(result);
        }
        
        return result;
    }
    @Transactional(propagation= Propagation.REQUIRES_NEW)
    private void cleanAffProgDailyStatistics(int affProgramId) {
        dailyBehaviorStatsDao.executeUpdateByQuery("DailyBehaviorStats.deleteAffProgStats", affProgramId);
    }
    
    @Transactional(propagation= Propagation.REQUIRES_NEW)
    private void cleanAffProgPrevMonthStatistics(int affProgramId) {
        prevMonthBehaviorStatsDao.executeUpdateByQuery("PrevMonthBehaviorStats.deleteAffProgStats", affProgramId);
    }    
    
    @Transactional(propagation= Propagation.REQUIRES_NEW)
    private void cleanAffProgCurMonthStatistics(int affProgramId) {
        prevMonthBehaviorStatsDao.executeUpdateByQuery("CurMonthBehaviorStats.deleteAffProgStats", affProgramId);
    }        
    
    private void updateDailyStatistics(int affProgramId,Set<FilterableBehaviorStatistics> curDailyStats) {

        cleanAffProgDailyStatistics(affProgramId);
        
        for (FilterableBehaviorStatistics fbs:curDailyStats)
            dailyBehaviorStatsDao.create(fbs);
        
    }
    
}
