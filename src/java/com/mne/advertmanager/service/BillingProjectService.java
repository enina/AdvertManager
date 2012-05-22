/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.advertmanager.service;

import com.mne.advertmanager.dao.GenericDao;
import com.mne.advertmanager.model.*;
import com.mne.advertmanager.parsergen.model.DataSpec;
import com.mne.advertmanager.parsergen.model.Project;
import com.mne.advertmanager.parsergen.model.SelectableItem;
import com.mne.advertmanager.util.BillingDataImporter;
import com.mne.advertmanager.util.JSoupTransport;
import java.util.*;
import org.hibernate.Hibernate;
import org.jsoup.Connection;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
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
    private Map<String, BillingDataImporter> importers = null;

    /**
     * empty C-tor
     */
    public BillingProjectService() {
    }

    /**
     * for each property defined on bean element in application context we must have a corresponding setter functions For example: <property name="projectDao"> <ref
     * bean="projectDao"/> </property> Corresponding setter functions is setProjectDao
     *
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

    /**
     * This function bring all Billing Projects from DB(using DAO obj) and return them as set. params: none. return: set (hashSet) of Billing Projects
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

    /**
     * this function persist given Project obj to DB by calling respective DAO objects
     *     
* @param project
     */
    @Transactional
    public void createProject(Project project) {
        projectDao.create(project);
        for (DataSpec ds : project.getDataSpecList()) {
            ds.setProject(project);
            dataSpecDao.create(ds);
            for (SelectableItem si : ds.getAllSubItems()) {
                si.setDataSpec(ds);
                selectableItemDao.create(si);
            }
        }

    }
//================================ importBillingData ===========================

    /**
     */
    public void importBillingData(AffProgram program) {

        //log action
        logger.info("Looking up project data by backoffice link  {}  ", program.getAffProgramLink());
        Project project = findProjectByAffProgram(program);
        if (project == null) {
            logger.error("Failed to find project for backoffice link  {}", program.getAffProgramLink());
        } else {
            logger.info("Started {} project data import. ", project.getName());
            //project baseUrl = programBackOfficeUrl - projectLoginUrl:
            String programBackOfficeUrl = program.getAffProgramLink();
            String projectLoginUrl = project.getLoginFormUrl();

            //inject to project: baseUrl of back office with userName and Password
            project.setBaseURL(programBackOfficeUrl.replace(projectLoginUrl, ""));
            project.setUsername(program.getUserName());
            project.setPassword(program.getPassword());

            // project

            //get Progects Data spec
            List<DataSpec> dsList = project.getDataSpecList();

            //make sure we process partners first
            DataSpec partnerDataSpec = project.getDataSpec("Partner");
            if (partnerDataSpec != null) {
                dsList.remove(partnerDataSpec);
                dsList.add(0, partnerDataSpec);
            }

            //connect to src web site
            Connection con = JSoupTransport.login(project);
            if (con == null) {
                logger.error("Failed to obtain connection for url:{}",project.getBaseURL()+project.getHomePage());
            } else {
                //get data of each dataSpec ( include all pages ) of given Project
                for (DataSpec ds : dsList) {
                    processDataSpec(ds, project, con, program);
                }
            }

            JSoupTransport.logout(con, project);
            logger.info("Finished {} project data import ", project.getName());
        }
    }
    @Transactional(readOnly=true)
    private Project findProjectByAffProgram(AffProgram program) {
        Project project = projectDao.findSingleItemByQuery("Project.findByBackOfficeURL", program.getAffProgramLink());
        
        for (DataSpec ds : project.getDataSpecList()) {
            List<SelectableItem> siList = ds.getAllSubItems();
            Hibernate.initialize(siList);
            for (SelectableItem si : siList) {
                Hibernate.initialize(si);
            }
        }
        return project;
    }

    private void processDataSpec(DataSpec ds, Project project, Connection con, AffProgram program) {
        int i = 1;
        boolean hasPaging = ds.getNumPages() > 1;

        //get data from all pages of current dataSpec 
        //if thare pages than we neet to add page var and val to end of url
        //else thare no pages -> don't add page val to url
        do {
            if (i > ds.getNumPages()) {
                break;
            }

            String url = project.getBaseURL() + ds.getDataURL();


            //append page var and val to url(if thare is paging)
            if (hasPaging) {
                //calculate url with pageing:
                if (url.indexOf('?') > 0) {
                    url += "&";
                } else {
                    url += "?";
                }

                url += ds.getPageParam() + "=" + i;
            }
            //get document with wanted data
            org.jsoup.nodes.Document doc = JSoupTransport.retrieveDocument(con, url, ds.getMethod());
            try {
                //extract data from current document to DB
                importPageData(program, doc, ds);
            } catch (Exception ex) {
                logger.error(ex.toString());
            }

            i++;
        } while (true);
    }

    /**
     * this function extract data from one page( given document ) to DB params:affiliate obj, preloaded document with wanted data, dataSpec that identify data location in document.
     * return:none
     */
    @Transactional(propagation= Propagation.REQUIRES_NEW)
    private void importPageData(AffProgram program, org.jsoup.nodes.Document doc, DataSpec dataSpec) {

        Element dataElem = null;    //hold data root element (used as relative start path to other elements)
        Elements dataList = null;   //hold all sibling html elements with wanted data;

        //get proper importer for given dataSpec
        BillingDataImporter importer = importers.get(dataSpec.getName());

        if (importer == null) {
            return;
        }

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
            importer.saveDataItem(program, curDataItem);
        }
    }
//================================ makeDataItem ================================

    /**
     * this function identify dataSpec by it's name, then create instance of proper data(model) type (one that match dataSpec name). params: dataSpec return: new instance of model
     * entity that mutch given dataSpec.
     */
    private Object makeDataItem(DataSpec dataSpec) {

        if ("Access".equals(dataSpec.getName())) {
            return new AccessLog();
        } else if ("Partner".equals(dataSpec.getName())) {
            return new Partner();
        } else if ("PurchaseOrder".equals(dataSpec.getName())) {
            return new PurchaseOrder();
        }

        return null;
    }

    @Transactional(readOnly = true)
    public Project findProjectById(int projectId) {

        Project res = projectDao.findSingleItemByQuery("Project.findProjectWithDataSpecList", projectId);

        return res;
    }

    @Transactional
    public void delete(int projectId) {
        projectDao.executeUpdateByQuery("Project.deleteById", projectId);
    }

    @Transactional(readOnly = true)
    public DataSpec findProjectDataSpec(int bpId, int dsId) {
        DataSpec result = null;

        result = dataSpecDao.findSingleItemByQuery("DataSpec.findDataSpecById", dsId, bpId);

        return result;
    }
}
