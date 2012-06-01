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
    private GenericDao<FilterableBehaviorStatistics, Integer> totalBehaviorStatsDao;
    private GenericDao<FilterableBehaviorStatistics, Integer> prevMonthBehaviorStatsDao;
    private GenericDao<FilterableBehaviorStatistics, Integer> curMonthBehaviorStatsDao;
    private GenericDao<FilterableBehaviorStatistics, Integer> dailyBehaviorStatsDao;
    private AccessLogService accessService;
    private AffProgramService affProgramService;
    private BillingProjectService blngService;

    public void setAccessService(AccessLogService accessService) {
	this.accessService = accessService;
    }

    public void setAffProgramService(AffProgramService affProgramService) {
	this.affProgramService = affProgramService;
    }

    public void setBlngService(BillingProjectService blngService) {
	this.blngService = blngService;
    }

    public void setTotalBehaviorStatsDao(GenericDao<FilterableBehaviorStatistics, Integer> totalBehaviorStatsDao) {
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

	FilterableBehaviorStatistics result = findAffProgramStatistics(totalBehaviorStatsDao, "TotalBehaviorStats.findAffProgCountryStats", affProgramId, "Filter.All");

	logger.info("ProgramId={},Retrieved total statistics for all filters", affProgramId);

	return result;
    }

    @Transactional
    public FilterableBehaviorStatistics findCurMonthAffProgramStatistics(int affProgramId) {

	FilterableBehaviorStatistics result = findAffProgramStatistics(curMonthBehaviorStatsDao, "CurMonthBehaviorStats.findAffProgCountryStats", affProgramId, "Filter.All");

	logger.info("ProgramId={}.Retrieved current month stats ", affProgramId);


	return result;
    }

    @Transactional
    public FilterableBehaviorStatistics findPrevMonthAffProgramStatistics(int affProgramId) {

	FilterableBehaviorStatistics result = findAffProgramStatistics(prevMonthBehaviorStatsDao, "PrevMonthBehaviorStats.findAffProgCountryStats", affProgramId, "Filter.All");

	logger.info("Retrieved prev month aggregation data ,Program {}", affProgramId);

	return result;
    }

    @Transactional
    public FilterableBehaviorStatistics findDailyAffProgramGeneralStats(int affProgramId) {

	FilterableBehaviorStatistics result = findAffProgramStatistics(dailyBehaviorStatsDao, "DailyBehaviorStats.findAffProgCountryStats", affProgramId, "Filter.All");

	logger.info("ProgramId={}:::Retrieved daily statistics", affProgramId);

	return result;
    }

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
		}
	    }
	}

	logger.info("Finished behavior statistics calculation");
    }

    /*
     *
     * <query name="TotalBehaviorStats.findAffProgrCountryStats"><![CDATA[ from TotalBehaviorStats where countryName=? and affProgram.id=? ]]> </query> *
     */
    private List<FilterableBehaviorStatistics> calcDailyAffProgramStatsSet(int affProgramId, Date refTime) {

	FilterableBehaviorStatistics total = dailyBehaviorStatsDao.findSingleItemByQuery("BehaviorStats.calcAffProgPeriodTotalFinancialStats", affProgramId, refTime);

	int accessCount = accessService.findDailyAffProgramAccessAmount(affProgramId, refTime);

	logger.debug("Program={} , Period=After {} ::Access={}, PO={} , Commision={}",
		new Object[]{affProgramId, refTime, accessCount, total.getPurchaseAmount(), total.getTotalCommision()});

	total.setCountryName("Filter.ALL");
	total.setAccessAmount(accessCount);

	ArrayList<FilterableBehaviorStatistics> result = new ArrayList<FilterableBehaviorStatistics>();
	result.add(total);
	result.addAll(dailyBehaviorStatsDao.findByQuery("BehaviorStats.calcAffProgPeriodDomainStats", affProgramId, refTime));
	//result.addAll(dailyBehaviorStatsDao.findByQuery("BehaviorStats.calcAffProgPeriodCountryStats", affProgramId,refTime));
	Collection<FilterableBehaviorStatistics> fsl = dailyBehaviorStatsDao.findByQuery("BehaviorStats.calcAffProgPeriodCountryStats", affProgramId, refTime);
	Collection<FilterableBehaviorStatistics> countryFsl = dailyBehaviorStatsDao.findByQuery("BehaviorStats.calcAffProgPeriodFinancialStatsByCountry", affProgramId, refTime);
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

    private TreeMap<String, FilterableBehaviorStatistics> builFBSTree(Collection<FilterableBehaviorStatistics> data) {

	TreeMap<String, FilterableBehaviorStatistics> result = new TreeMap<String, FilterableBehaviorStatistics>();
	if (data != null) {
	    for (FilterableBehaviorStatistics fbs : data) {
		result.put(fbs.getKey(), fbs);
	    }
	}

	return result;
    }

    private TreeMap<String, FilterableBehaviorStatistics> findAffProgramStats(GenericDao<FilterableBehaviorStatistics, Integer> statsDao, String queryName, int affProgramId) {

	Collection<FilterableBehaviorStatistics> data = statsDao.findByQuery(queryName, affProgramId);

	return builFBSTree(data);
    }

    @Transactional
    public void calculateAffProgramStatistics(int affProgramId) {

	logger.info("Program={}:::Started behavior statistics calculation", affProgramId);
	Calendar cal = Calendar.getInstance();
	//cal.add(Calendar.DATE, -1);
	cal.clear();
	//just for testing should be removed
	cal.set(2012, Calendar.FEBRUARY, 1, 1, 0);
	Date refTime = cal.getTime();



	List<FilterableBehaviorStatistics> todayStats = calcDailyAffProgramStatsSet(affProgramId, refTime);

	TreeMap<String, FilterableBehaviorStatistics> totalStats = findAffProgramStats(totalBehaviorStatsDao, "TotalBehaviorStats.findAffProgStats", affProgramId);
	updateStats(totalBehaviorStatsDao, totalStats, todayStats);
	logger.info("Updated total statistics");

	TreeMap<String, FilterableBehaviorStatistics> cmStats = findAffProgramStats(curMonthBehaviorStatsDao, "CurMonthBehaviorStats.findAffProgStats", affProgramId);

	if (cal.get(Calendar.DAY_OF_MONTH) == 1) {
	    shiftStats(prevMonthBehaviorStatsDao, "PrevMonthBehaviorStats.deleteAffProgStats", affProgramId, cmStats.values());
	    shiftStats(curMonthBehaviorStatsDao, "CurMonthBehaviorStats.deleteAffProgStats", affProgramId, todayStats);
	    logger.info("Shifted monthly statistics");
	} else {
	    updateStats(curMonthBehaviorStatsDao, cmStats, todayStats);
	    logger.info("Updated current month statistics");
	}

	//delete outdate statistics from the daily table
	cleanAffProgStats(dailyBehaviorStatsDao, "DailyBehaviorStats.deleteAffProgStats", affProgramId);
	//save today statistics
	processFBSList(dailyBehaviorStatsDao, todayStats);
	logger.info("Updated daily statistics");

	logger.info("Program={}::Finished behavior statistics calculation .", affProgramId);
    }

    private void shiftStats(GenericDao<FilterableBehaviorStatistics, Integer> statsDao, String queryName, int affProgramId, Collection<FilterableBehaviorStatistics> stats) {

	cleanAffProgStats(statsDao, queryName, affProgramId);
	ArrayList<FilterableBehaviorStatistics> shifted = new ArrayList<FilterableBehaviorStatistics>();
	FilterableBehaviorStatistics curFbs;
	for (FilterableBehaviorStatistics fbs : stats) {
	    curFbs = new FilterableBehaviorStatistics(fbs);
	    shifted.add(curFbs);
	}
	processFBSList(statsDao, shifted);

    }

    private void updateStats(GenericDao<FilterableBehaviorStatistics, Integer> statsDao,
	    TreeMap<String, FilterableBehaviorStatistics> curStats,
	    List<FilterableBehaviorStatistics> todayStats) {

	ArrayList<FilterableBehaviorStatistics> updatedStats = new ArrayList<FilterableBehaviorStatistics>();
	for (FilterableBehaviorStatistics todayFbs : todayStats) {
	    FilterableBehaviorStatistics curFbs = curStats.get(todayFbs.getKey());
	    if (curFbs != null) {
		curFbs.add(todayFbs);
		updatedStats.add(curFbs);
	    }
	}

	processFBSList(statsDao, updatedStats);
    }

    @SuppressWarnings("unchecked")
    private FilterableBehaviorStatistics findAffProgramStatistics(GenericDao<FilterableBehaviorStatistics, Integer> statsDao, String query, int affProgramId, Object... params) {


	ArrayList allParams = new ArrayList(Arrays.asList(params));
	allParams.add(0, affProgramId);

	FilterableBehaviorStatistics result = statsDao.findSingleItemByQuery(query, allParams.toArray());

	if (result == null) {
	    result = new FilterableBehaviorStatistics(0, 0, 0, new AffProgram(affProgramId));
	    result.setCountryName("Filter.ALL");
	    statsDao.create(result);
	}

	return result;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    private void cleanAffProgStats(GenericDao<FilterableBehaviorStatistics, Integer> statsDao, String queryName, int affProgramId) {
	statsDao.executeUpdateByQuery(queryName, affProgramId);
    }

    private void processFBSList(GenericDao<FilterableBehaviorStatistics, Integer> statsDao, List<FilterableBehaviorStatistics> fbsList) {

	if (fbsList == null) {
	    return;
	}

	int setSize = fbsList.size();
	int fromIdx = 0;
	int toIdx = 0;

	List<FilterableBehaviorStatistics> subList = null;

	while (toIdx < setSize) {
	    fromIdx = toIdx;
	    toIdx = Math.min(fromIdx + 10, setSize);
	    subList = fbsList.subList(fromIdx, toIdx);
	    persistItemList(statsDao, subList);
	}
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    private void persistItemList(GenericDao<FilterableBehaviorStatistics, Integer> statsDao, List<FilterableBehaviorStatistics> itemList) {
	statsDao.saveDataSet(itemList);
    }
}
