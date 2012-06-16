/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.advertmanager.service;

import com.mne.advertmanager.dao.GenericDao;
import com.mne.advertmanager.model.AffProgram;
import com.mne.advertmanager.model.FilterableBehaviorStatistics;
import com.mne.advertmanager.util.Page;
import com.mne.advertmanager.util.PageCtrl;
import java.util.*;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Misha
 */
public class BehaviorStatisticsService {

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(BehaviorStatisticsService.class);
    
    private GenericDao<FilterableBehaviorStatistics, Integer> statsDao;
    private AccessLogService accessService;
    private AffProgramService affProgramService;
    private BillingProjectService blngService;
    private SearchQueryStatService searchQueryStatService;

    
    public void setSearchQueryStatService(SearchQueryStatService searchQueryStatService) {
        this.searchQueryStatService = searchQueryStatService;
    }

    public void setAccessService(AccessLogService accessService) {

	this.accessService = accessService;
    }

    public void setAffProgramService(AffProgramService affProgramService) {
	this.affProgramService = affProgramService;
    }

    public void setBlngService(BillingProjectService blngService) {
	this.blngService = blngService;
    }

    public void setStatsDao(GenericDao<FilterableBehaviorStatistics, Integer> statsDao) {
	this.statsDao = statsDao;
    }

    @Transactional(readOnly = true)
    public Set<FilterableBehaviorStatistics> findTotalAffiliateStatistics(int affiliateId) {

	Collection<FilterableBehaviorStatistics> data = statsDao.findByQuery("Fbs.findAffiliateCountryTypeStats", affiliateId, "Filter.All", FilterableBehaviorStatistics.StatType.Total);

	LinkedHashSet<FilterableBehaviorStatistics> result = new LinkedHashSet<FilterableBehaviorStatistics>(data);

	logger.info("AffiliateId={},Retrieved total statistics for all programs", affiliateId);

	return result;
    }

    @Transactional(readOnly = true)
    public Set<FilterableBehaviorStatistics> findAffProgramStatistics(int affProgramId) {

	Collection<FilterableBehaviorStatistics> data = statsDao.findByQuery("Fbs.findAffProgCountryStats", affProgramId, "Filter.All");
	LinkedHashSet<FilterableBehaviorStatistics> result = new LinkedHashSet<FilterableBehaviorStatistics>(data);


	//findAffProgramStatistics(totalBehaviorStatsDao, "TotalBehaviorStats.findAffProgCountryStats", affProgramId, "Filter.All");

	logger.info("ProgramId={},Retrieved program  statistics ", affProgramId);

	return result;
    }
  
    //================================ calculate ===================================
    //@Scheduled(cron="0 0 4 * * ?")
    public void calculate() {

	logger.info("Started behavior statistics calculation");

	HashSet<AffProgram> affProgs = new HashSet<AffProgram>(affProgramService.findAllAffPrograms());
	if (affProgs != null) {
	    logger.info("Retrieved {} affiliate programs", affProgs.size());
	    Iterator<AffProgram> affProgIter = affProgs.iterator();
	    AffProgram curAffProgram = null;
	    while (affProgIter.hasNext()) {
		curAffProgram = affProgIter.next();
		if (curAffProgram != null) {
		    blngService.importBillingData(curAffProgram);
		    calculateAffProgramStatistics(curAffProgram.getId());
                    searchQueryStatService.calculateQueryStats(curAffProgram);
		}
	    }
	}

	logger.info("Finished behavior statistics calculation");
    }

    /*
     *
     * <query name="TotalBehaviorStats.findAffProgrCountryStats"><![CDATA[ from TotalBehaviorStats where countryName=? and affProgram.id=? ]]> </query> *
     */
//=============================== calcDailyAffProgramStatsSet ==================
    private List<FilterableBehaviorStatistics> calcDailyAffProgramStatsSet(int affProgramId, Date refTime) {

	ArrayList<FilterableBehaviorStatistics> result = new ArrayList<FilterableBehaviorStatistics>();

	logger.info("Program={}:::DailyStatCals:Start Financial Calc", affProgramId);
	//get total purchase num and total commision amount
	FilterableBehaviorStatistics total = statsDao.findSingleItemByQuery("Fbs.calcAffProgPeriodFinancialStats", affProgramId, refTime);
	logger.info("Program={}:::DailyStatCals:Finish Financial  Calc", affProgramId);

	if (total != null) {
	    logger.info("Program={}:::DailyStatCals:Start Period Access ", affProgramId);
	    int accessCount = accessService.findDailyAffProgramAccessAmount(affProgramId, refTime);
	    logger.info("Program={}:::DailyStatCals:Finish Period Access ", affProgramId);
	    total.setCountryName("Filter.All");
	    total.setAccessAmount(accessCount);
	    result.add(total);
	    logger.debug("Program={}:::DailyStatCals : Period=After {} ::Access={}, PO={} , Commision={}",
		    new Object[]{affProgramId, refTime, accessCount, total.getPurchaseAmount(), total.getTotalCommision()});

	}

	//domain statistics
	logger.info("Program={}:::DailyStatCals:Start Period Access by Domain Stats Calculation", affProgramId);
	result.addAll(statsDao.findByQuery("Fbs.calcAffProgPeriodAccessDomainStats", affProgramId, refTime));
	logger.info("Program={}:::DailyStatCals:Finish Period Access by  Domain Stats Calculation", affProgramId);

	//country accesses statistics
	logger.info("Program={}:::DailyStatCals:Start Period Access by Country Calc", affProgramId);
	Collection<FilterableBehaviorStatistics> fsl = statsDao.findByQuery("Fbs.calcAffProgPeriodAccessCountryStats", affProgramId, refTime);
	logger.info("Program={}:::DailyStatCals:Finish Period Access by Country Calc", affProgramId);

	//country purchase statistics
	logger.info("Program={}:::DailyStatCals:Start Period Financial by Country Stats Calculation", affProgramId);
	Collection<FilterableBehaviorStatistics> countryFsl = statsDao.findByQuery("Fbs.calcAffProgPeriodFinancialCountryStats", affProgramId, refTime);
	logger.info("Program={}:::DailyStatCals:Finish Period Financial by Country Stats Calculation", affProgramId);
	TreeMap<String, FilterableBehaviorStatistics> finStat = builFBSTree(fsl);

	FilterableBehaviorStatistics countryFBS = null;

	for (FilterableBehaviorStatistics fbs : countryFsl) {
	    countryFBS = finStat.get(fbs.getKey());
	    if (countryFBS != null) {
		countryFBS.setPurchaseAmount(fbs.getPurchaseAmount());
		countryFBS.setTotalCommision(fbs.getTotalCommision());
	    }
	}
	result.addAll(finStat.values());

	return result;
    }
//============================================== builFBSTree ===================

    private TreeMap<String, FilterableBehaviorStatistics> builFBSTree(Collection<FilterableBehaviorStatistics> data) {

	TreeMap<String, FilterableBehaviorStatistics> result = new TreeMap<String, FilterableBehaviorStatistics>();
	if (data != null) {
	    for (FilterableBehaviorStatistics fbs : data) {
		result.put(fbs.getKey(), fbs);
	    }
	}

	return result;
    }

    private TreeMap<String, FilterableBehaviorStatistics> findAffProgramTypeStats(int affProgramId, FilterableBehaviorStatistics.StatType statType) {

	Collection<FilterableBehaviorStatistics> data = statsDao.findByQuery("Fbs.findAffProgTypeStats", affProgramId, statType);

	return builFBSTree(data);
    }
//============================== calculateAffProgramStatistics =================

    @Transactional
    public void calculateAffProgramStatistics(int affProgramId) {

	logger.info("Program={}:::StatsCalc:Start", affProgramId);
	Calendar cal = Calendar.getInstance();
	//cal.add(Calendar.DATE, -1);
	cal.clear();
	//just for testing should be removed
	cal.set(2012, Calendar.FEBRUARY, 1, 1, 0);
	Date refTime = cal.getTime();

	//calculate today statistics
	logger.info("Program={}:::StatsCalc:Start Daily Calculation", affProgramId);
	List<FilterableBehaviorStatistics> todayStats = calcDailyAffProgramStatsSet(affProgramId, refTime);
	logger.info("Program={}:::StatsCalc:Finish Daily Calculation", affProgramId);

	//retrive current total stats and update them 
	TreeMap<String, FilterableBehaviorStatistics> totalStats = findAffProgramTypeStats(affProgramId, FilterableBehaviorStatistics.StatType.Total);
	updateStats(totalStats, todayStats, FilterableBehaviorStatistics.StatType.Total);
	logger.info("Program={}:::StatsCalc:Total statistics updated", affProgramId);


	//retrive current month statistics 
	TreeMap<String, FilterableBehaviorStatistics> cmStats = findAffProgramTypeStats(affProgramId, FilterableBehaviorStatistics.StatType.CurMonth);

	//check if current month has ended, if so make it previous month and
	//begin new current month. add today statistics to current month.
	if (cal.get(Calendar.DAY_OF_MONTH) == 1) {
	    shiftStats(affProgramId, FilterableBehaviorStatistics.StatType.PrevMonth, cmStats.values());
	    shiftStats(affProgramId, FilterableBehaviorStatistics.StatType.CurMonth, todayStats);
	    logger.info("Program={}:::StatsCalc:PrevMonth stats updated", affProgramId);
	    logger.info("Program={}:::StatsCalc:CurvMonth stats updated", affProgramId);
	} else {
	    updateStats(cmStats, todayStats, FilterableBehaviorStatistics.StatType.CurMonth);
	    logger.info("Program={}:::StatsCalc:CurvMonth stats updated", affProgramId);
	}

	//delete outdate statistics from the daily table
	cleanAffProgStats(affProgramId, FilterableBehaviorStatistics.StatType.CurDay);
	//save today statistics
	processFBSList(todayStats);
	logger.info("Program={}:::StatsCalc:Daily stats updated", affProgramId);

	logger.info("Program={}:::StatsCalc:Finish", affProgramId);
    }
    //============================== shiftStats ====================================

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    private void shiftStats(int affProgramId, FilterableBehaviorStatistics.StatType statType, Collection<FilterableBehaviorStatistics> stats) {

	cleanAffProgStats(affProgramId, statType);
	ArrayList<FilterableBehaviorStatistics> shifted = new ArrayList<FilterableBehaviorStatistics>();
	FilterableBehaviorStatistics curFbs;
	for (FilterableBehaviorStatistics fbs : stats) {
	    curFbs = new FilterableBehaviorStatistics(fbs, statType);
	    shifted.add(curFbs);
	}
	processFBSList(shifted);

    }

    //============================== updateStats ===================================
    private void updateStats(TreeMap<String, FilterableBehaviorStatistics> curStats,
	    List<FilterableBehaviorStatistics> todayStats,
	    FilterableBehaviorStatistics.StatType type) {

	ArrayList<FilterableBehaviorStatistics> updatedStats = new ArrayList<FilterableBehaviorStatistics>();
	for (FilterableBehaviorStatistics todayFbs : todayStats) {
	    FilterableBehaviorStatistics curFbs = curStats.get(todayFbs.getKey());
	    if (curFbs != null) {
		curFbs.add(todayFbs);
		curFbs.setType(type);
		updatedStats.add(curFbs);
	    } else {
		updatedStats.add(new FilterableBehaviorStatistics(todayFbs, type));
	    }
	}

	processFBSList(updatedStats);
    }


//=============================== cleanAffProgStats ============================

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    private void cleanAffProgStats(int affProgramId, FilterableBehaviorStatistics.StatType type) {
	//delete outdate statistics from the daily table
	statsDao.executeUpdateByQuery("Fbs.deleteAffProgTypeStats", affProgramId, type);
    }
//=============================== processFBSList ===============================

    private void processFBSList(List<FilterableBehaviorStatistics> fbsList) {

	if (fbsList == null) {
	    return;
	}

	int setSize = fbsList.size();
	int fromIdx = 0;
	int toIdx = 0;

	List<FilterableBehaviorStatistics> subList = null;

	while (toIdx < setSize) {
	    fromIdx = toIdx;
	    toIdx = Math.min(fromIdx + 100, setSize);
	    subList = fbsList.subList(fromIdx, toIdx);
	    try {
		persistItemList(subList);
	    } catch (Exception e) {
		logger.error("{} while processing FBS list.Message={}", e.getClass().getSimpleName(), e.getMessage());
	    }
	}
    }
//=============================== persistItemList ==============================

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    private void persistItemList(List<FilterableBehaviorStatistics> itemList) {
	statsDao.saveDataSet(itemList);
    }

    @Transactional(readOnly = true)
    public Set<FilterableBehaviorStatistics> findTotalAccessAmountByDomain(int programId) {

	Page<FilterableBehaviorStatistics> data = statsDao.findPageByQuery("Fbs.findTypeAccessByDomain", new PageCtrl(1, 0, 10), programId, FilterableBehaviorStatistics.StatType.Total);

	logger.info("affProgId={},Retrieved total access by domain", programId);

	return new LinkedHashSet<FilterableBehaviorStatistics>(data.getItems());
    }
    
    @Transactional(readOnly =true)
    public Set<FilterableBehaviorStatistics> findTotalAccessByCountry(int programId){
        
	Page<FilterableBehaviorStatistics> data = statsDao.findPageByQuery("Fbs.findTypeAccessByCountry", new PageCtrl(1, 0, 10), programId, FilterableBehaviorStatistics.StatType.Total);

	logger.info("affProgId={},Retrieved total access by country", programId);

	return new LinkedHashSet<FilterableBehaviorStatistics>(data.getItems());
    }
}
