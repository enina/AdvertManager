/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.advertmanager.service;

import com.mne.advertmanager.dao.GenericDao;
import com.mne.advertmanager.model.AccessLog;
import com.mne.advertmanager.model.Affiliate;
import com.mne.advertmanager.model.Partner;
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
    
    /**empty C-tor*/
    public BillingProjectService() {
    }

    /**
     * for each property defined on bean element in application context we must
     * have a corresponding setter functions For example:
     * <property name="projectDao">
     *      <ref bean="projectDao"/> 
     * </property>
     * Corresponding setter functions is setProjectDao
     * @param projectDao
     */
    //seter used by Spring context injection
    public void setProjectDao(GenericDao<Project, Integer> projectDao) {
        this.projectDao = projectDao;
    }
    //seter used by Spring context injection
    public void setDataSpecDao(GenericDao<DataSpec, Integer> dataSpecDao) {
        this.dataSpecDao = dataSpecDao;
    }
    //seter used by Spring context injection
    public void setSelectableItemDao(GenericDao<SelectableItem, Integer> selectableItemDao) {
        this.selectableItemDao = selectableItemDao;
    }
    //seter used by Spring context injection
    public void setImporters(Map<String, BillingDataImporter> importers) {
        this.importers = importers;
    }
//============================== findAllBillingProjects ======================== 
/**This function bring all Billing Projects from DB(using DAO obj) and return
 * them as set.
 * params: none.
 * return: set (hashSet) of Billing Projects
*/
    @Transactional(readOnly = true) 
    public Set<Project> findAllBillingProjects() {

        HashSet<Project> result = new HashSet<Project>();

        Collection<Project> data = projectDao.findByQuery("Project.findAll");

        if (data != null) {
            result.addAll(data);
        }
        return result;
    }
//================================ createProject ===============================
/**this function persist given Project obj to DB by calling respective DAO
 * objects
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
//================================ importBillingData ===========================
/**
*/
    @Transactional
    public void importBillingData(Affiliate aff,int blngProjId) {

        //load projec from DB
        Project project = projectDao.read(blngProjId);
        
        //get Progects Data spec
        List<DataSpec> dsList = project.getDataSpecList();  

        //log action
        logger.info("Started {} project data import. ",project.getName());
        
        //connect to src web site
        Connection con = JSoupTransport.login(project);

        //get data of each dataSpec ( include all pages ) of given Project
        for (DataSpec ds : dsList) {
            int i = 0;
            boolean hasPaging = ds.getNumPages()>0;
            
            //get data from all pages of current dataSpec 
            do{
                if (i > ds.getNumPages())
                    break;
                
                String url = project.getBaseURL() + ds.getDataURL();
                i++;
                
                if (hasPaging) {
                    //calculate url with pageing:
                    if (url.indexOf('?')>0)
                        url+= "&" ;
                    else
                        url+= "?" ;
                    
                    url+= ds.getPageParam() + "=" + i;
                }
                //get document with wanted data
                org.jsoup.nodes.Document doc = JSoupTransport.retrieveDocument(con, url, ds.getMethod());
                try {
                    //extract data from current document to DB
                    importPageData(aff,doc, ds);
                } catch (Exception ex) {
                    logger.error(ex.toString());
                }                
            }while(true);
        }

        JSoupTransport.logout(con, project);
        logger.info("Finished {} project data import ",project.getName());
    }
//================================ importPageData ==============================
/**this function extract data from one page( given document ) to DB
 * params:affiliate obj, preloaded document with wanted data, dataSpec that
 *        identify data location in document.
 * return:none
*/
    private void importPageData(Affiliate aff,org.jsoup.nodes.Document doc, DataSpec dataSpec) {

        Element dataElem = null;    //hold data root element (used as relative start path to other elements)
        Elements dataList = null;   //hold all sibling html elements with wanted data;
        
        //get proper importer for given dataSpec
        BillingDataImporter importer = importers.get(dataSpec.getName());
        
        if (importer==null)
            return;
        
        dataElem = doc.select(dataSpec.getSelector()).first();
        dataList = dataElem.select(dataSpec.getListEntrySelector());

        for (int i = 0; i < dataList.size(); ++i) {
            
            //create new data entity (type of entity defined in makeDataItem fun.
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
//================================ makeDataItem ================================
/**this function identify dataSpec by it's name, then create instance of proper
 * data(model) type (one that match dataSpec name).
 * params: dataSpec
 * return: new instance of model entity that mutch given dataSpec.
*/
    private Object makeDataItem(DataSpec dataSpec) {
        
        if("Access".equals(dataSpec.getName()))
            return new AccessLog();
        else if ("Affiliate".equals(dataSpec.getName()))
            return new Affiliate();
        else if ("Partner".equals(dataSpec.getName()))
                return new Partner();
        
        return null;
    }
}
