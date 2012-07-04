/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.advertmanager.service;

import com.mne.advertmanager.dao.GenericDao;
import com.mne.advertmanager.model.AffProgram;
import com.mne.advertmanager.model.SearchQueryStatistics;
import com.mne.advertmanager.util.Page;
import com.mne.advertmanager.util.PageCtrl;
import java.util.Collection;
import java.util.LinkedHashSet;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Misha
 */
public class SearchQueryStatService {
    
    
    private GenericDao<SearchQueryStatistics,Integer> searchQueryStatDao;
    
//=================================== calculateQueryStats ======================
    @Transactional
    public void calculateQueryStats( AffProgram affProg) {
        
        searchQueryStatDao.executeUpdateByQuery("SearchQueryStatistics.deleteByAffProgram", affProg);
        Collection<SearchQueryStatistics> queryStats =  searchQueryStatDao.findByQuery("SearchQueryStatistics.calcStatistics",affProg);
        searchQueryStatDao.saveDataSet(queryStats);
    }
//=================================== getAllSearchQueryStats ===================
    @Transactional(readOnly = true)
    public LinkedHashSet<SearchQueryStatistics> getAllSearchQueryStats(AffProgram affProg){
        LinkedHashSet<SearchQueryStatistics> result = null;
        result = new LinkedHashSet<SearchQueryStatistics>();
        Page<SearchQueryStatistics> page =  searchQueryStatDao.findPageByQuery("SearchQueryStatistics.findAll",new PageCtrl(1,0,15),affProg);
        result.addAll(page.getItems());
        return  result;
    }

    public void setSearchQueryStatDao(GenericDao<SearchQueryStatistics, Integer> searchQueryStatDao) {
        this.searchQueryStatDao = searchQueryStatDao;
    }


}
