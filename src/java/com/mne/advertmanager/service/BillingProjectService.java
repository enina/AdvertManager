/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.advertmanager.service;

import com.mne.advertmanager.dao.GenericDao;
import com.mne.advertmanager.model.AccessLog;
import com.mne.advertmanager.model.Affiliate;
import com.mne.advertmanager.parsergen.model.DataSpec;
import com.mne.advertmanager.parsergen.model.Project;
import com.mne.advertmanager.parsergen.model.SelectableItem;
import com.mne.advertmanager.util.BillingDataImporter;
import com.mne.advertmanager.util.JSoupTransport;
import java.util.*;
import org.jsoup.Connection;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Nina Eidelshtein and Misha Lebedev
 */
public class BillingProjectService {

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(BillingProjectService.class);
    
    private GenericDao<Project, Integer> projectDao;
    private GenericDao<DataSpec, Integer> dataSpecDao;
    private GenericDao<SelectableItem, Integer> selectableItemDao;
    
    
    private Map<String,BillingDataImporter> importers = null;
    

    public BillingProjectService() {
    }

    /**
     * for each property defined on bean element in application context we must have a corresponding setter functions For example: <property name="projectDao">
     * <ref bean="projectDao"/> </property> Corresponding setter functions is setProjectDao
     *
     * @param projectDao
     */
    public void setProjectDao(GenericDao<Project, Integer> projectDao) {
        this.projectDao = projectDao;
    }

    public void setDataSpecDao(GenericDao<DataSpec, Integer> dataSpecDao) {
        this.dataSpecDao = dataSpecDao;
    }

    public void setSelectableItemDao(GenericDao<SelectableItem, Integer> selectableItemDao) {
        this.selectableItemDao = selectableItemDao;
    }


    
    
    public void setImporters(Map<String, BillingDataImporter> importers) {
        this.importers = importers;
    }
    
    

    @Transactional(readOnly = true)
    public Set<Project> findAllBillingProjects() {

        HashSet<Project> result = new HashSet<Project>();

        Collection<Project> data = projectDao.findByQuery("Project.findAll");

        if (data != null) {
            result.addAll(data);
        }

        return result;
    }

    /**
     *
     * @param project
     */
    @Transactional
    public void createProject(Project project) {
        projectDao.create(project);
        for (DataSpec ds:project.getDataSpecList()) {
            ds.setProject(project);
            dataSpecDao.create(ds);
            for(SelectableItem si:ds.getAllSubItems()) {
                si.setDataSpec(ds);
                selectableItemDao.create(si);
            }
        }
        
    }

    @Transactional
    public void importBillingData(Affiliate aff,int blngProjId) {

        Project project = projectDao.read(blngProjId);

        List<DataSpec> dsList = project.getDataSpecList();

        logger.info("Started {} project data import. ",project.getName());
        
        Connection con = JSoupTransport.login(project);

        
        for (DataSpec ds : dsList) {
            int i = 0;
            boolean hasPaging = ds.getNumPages()>0;
            do{
                String url = project.getBaseURL() + ds.getDataURL();
                i++;
                if (hasPaging) {
                    if (url.indexOf('?')>0)
                        url+= "&" ;
                    else
                        url+= "?" ;
                    
                    url+= ds.getPageParam() + "=" + i;
                }
                org.jsoup.nodes.Document doc = JSoupTransport.retrieveDocument(con, url, ds.getMethod());
                try {
                    importPageData(aff,doc, ds);
                } catch (Exception ex) {
                    logger.error(ex.toString());
                }                
            }while(i <= ds.getNumPages());
        }

        JSoupTransport.logout(con, project);
        logger.info("Finished {} project data import ",project.getName());
    }

    private void importPageData(Affiliate aff,org.jsoup.nodes.Document doc, DataSpec dataSpec) {

        Element dataElem = null;
        Elements dataList = null;
        
        BillingDataImporter importer = importers.get(dataSpec.getName());
        
        if (importer==null)
            return;
        
        dataElem = doc.select(dataSpec.getSelector()).first();
        dataList = dataElem.select(dataSpec.getListEntrySelector());

        for (int i = 0; i < dataList.size(); ++i) {
            Object curDataItem = makeDataItem(dataSpec);
            for (SelectableItem item : dataSpec.getDataItems()) {
                Element listEntryElement = dataList.get(i);
                String selector = item.getSelector();
                Elements selectedList = listEntryElement.select(selector);
                if (selectedList != null) {
                    Element targetElement = selectedList.first();
                    String propName = item.getName();
                    String propValue = targetElement.text();
                    importer.importDataItemProperty(curDataItem, propName, propValue);
                } else {
                    logger.error("Error retrieving value of " + selector);
                }
            }
            importer.saveDataItem(aff,curDataItem);
        }
    }

    private Object makeDataItem(DataSpec dataSpec) {
        
        if("Access".equals(dataSpec.getName()))
            return new AccessLog();
        else if ("Affiliate".equals(dataSpec.getName()))
            return new Affiliate();
        
        return null;
    }
}
