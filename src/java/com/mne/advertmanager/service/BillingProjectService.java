/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.advertmanager.service;

import com.mne.advertmanager.dao.GenericDao;
import com.mne.advertmanager.model.AccessLog;
import com.mne.advertmanager.model.AffProgram;
import com.mne.advertmanager.model.Partner;
import com.mne.advertmanager.model.PurchaseOrder;
import com.mne.advertmanager.parsergen.model.DataSpec;
import com.mne.advertmanager.parsergen.model.Project;
import com.mne.advertmanager.parsergen.model.SelectableItem;
import com.mne.advertmanager.util.BillingDataImporter;
import com.mne.advertmanager.util.JSoupTransport;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
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
    private ExecutorService executor = null;
    
    private int numThread = 5;



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
    public void setNumThread(int numThread) {
        this.numThread = numThread;
        executor = Executors.newFixedThreadPool(this.numThread);
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
        logger.info("Looking up project data by backoffice link  {}  ", program.getAffProgramLink());
        Project project = findProjectByAffProgram(program);
        if (project == null) {
            logger.error("Failed to find project for backoffice link  {}", program.getAffProgramLink());
        } else {
            prepareProject(program, project);
            List<DataSpec> dsList = prepareDataSpecList(project);
            importProjectData(project, dsList, program);
        }
    }

    @Transactional(readOnly = true)
    private Project findProjectByAffProgram(AffProgram program) {

        Project project = projectDao.findSingleItemByQuery("Project.findByBackOfficeURL", program.getAffProgramLink());
        if (project != null) {
            List<DataSpec> dsList = project.getDataSpecList();
            if (dsList != null) {
                for (DataSpec ds : dsList) {
                    List<SelectableItem> siList = ds.getAllSubItems();
                    if (siList != null) {
                        Hibernate.initialize(siList);
                        for (SelectableItem si : siList) {
                            Hibernate.initialize(si);
                        }
                    }
                }
            }
        }
        return project;
    }

    private List<Future> processDataSpec(DataSpec ds, Project project, Connection con, AffProgram program) {

        int blockSize = ds.getNumPages() / numThread + 1;
        int firstPage = 0;
        int lastPage = 0;
        int nTask = Math.min(blockSize, numThread);
        String url = buildDataSpecURL(project, ds);
        List<Future> result = new ArrayList<Future>();
        
        for (int j = 0; j < nTask;++j) {
            firstPage= j*blockSize+1;
            lastPage = Math.min( (j+1)*blockSize,ds.getNumPages() );
            PageDownloadTask pdTask = new PageDownloadTask(program, ds, con, url,firstPage ,lastPage);
            result.add(executor.submit(pdTask));
        }
        return result;
    }

    /**
     * this function extract data from one page( given document ) to DB params:affiliate obj, preloaded document with wanted data, dataSpec that identify data location in document.
     * return:none
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    private void importPageData(AffProgram program, org.jsoup.nodes.Document doc, DataSpec dataSpec) {

        Element dataElem = null;    //hold data root element (used as relative start path to other elements)
        Elements dataList = null;   //hold all sibling html elements with wanted data;

        //get proper importer for given dataSpec
        BillingDataImporter importer = importers.get(dataSpec.getName());

        if (importer == null) {
            return;
        }

        Elements dsElements = doc.select(dataSpec.getSelector());
        if (dsElements == null || (dataElem=dsElements.first())==null) {
            logger.error("ImportPageData:::Data root not found.DataSpec={},Selector={}",dataSpec.getName(),dataSpec.getSelector());
            return;
        }
        
        dataList = dataElem.select(dataSpec.getListEntrySelector());
        if (dataList == null ) {
            logger.error("ImportPageData:::Item List not found.DataSpec={},Selector={}",dataSpec.getName(),dataSpec.getListEntrySelector());
            return;
        }        

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

    private void prepareProject(AffProgram program, Project project) {
        //project baseUrl = programBackOfficeUrl - projectLoginUrl:
        String programBackOfficeUrl = program.getAffProgramLink();
        String projectLoginUrl = project.getLoginFormUrl();

        //inject to project: baseUrl of back office with userName and Password
        project.setBaseURL(programBackOfficeUrl.replace(projectLoginUrl, ""));
        project.setUsername(program.getUserName());
        project.setPassword(program.getPassword());
    }

    private List<DataSpec> prepareDataSpecList(Project project) {
        // project
        //get Progects Data spec
        List<DataSpec> dsList = project.getDataSpecList();
        //make sure we process partners first
        DataSpec partnerDataSpec = project.getDataSpec("Partner");
        if (partnerDataSpec != null) {
            dsList.remove(partnerDataSpec);
            dsList.add(0, partnerDataSpec);
        }
        return dsList;
    }

    private List<Future> processDataSpecList(List<DataSpec> dsList, Project project, Connection con, AffProgram program) {
        //get data of each dataSpec ( include all pages ) of given Project
        List<Future> futures = new ArrayList<Future>();
        for (DataSpec ds : dsList) {
            List<Future> specFutures =  processDataSpec(ds, project, con, program);
            futures.addAll(specFutures);
        }
        return futures;
    }

    private void awaitProcessingCompletion(Project project, List<Future> futures) {
        try {
            logger.debug("{} awaiting termination of {} tasks ", project.getName(),futures.size());
            Iterator<Future> futureIter = futures.iterator();
            while (futureIter.hasNext()) {
                Future future = futureIter.next();
                future.get();
                futureIter.remove();
            }
            logger.debug("{} all task terminated ", project.getName());
        }catch(Exception e) {
            logger.error("{} download was interrupted {} ", project.getName(),e.toString());
        }
    }

    private void finalizeImportProcessing(List<DataSpec> dsList) {
        for (DataSpec ds : dsList) {
            BillingDataImporter<Object> importer = importers.get(ds.getName());
            importer.finalizeImport();
        }
    }

    private void importProjectData(Project project, List<DataSpec> dsList, AffProgram program) {
        //connect to src web site
        logger.info("{} started import ", project.getName());
        Connection con = JSoupTransport.login(project);
        if (con == null) {
            logger.error("Failed to obtain connection for url:{}", project.getBaseURL() + project.getHomePage());
        } else {
            List<Future> futures = processDataSpecList(dsList, project, con, program);
            awaitProcessingCompletion(project, futures);
            finalizeImportProcessing(dsList);
        }
        JSoupTransport.logout(con, project);
        logger.info("{} finished  import ", project.getName());
    }

    private String buildDataSpecURL(Project project, DataSpec ds) {
        String url = project.getBaseURL() + ds.getDataURL();
        //calculate url with paging:
        if (url.indexOf('?') > 0) {
            url += "&";
        } else {
            url += "?";
        }
        return url;
    }
    
    private class PageDownloadTask implements Runnable {

        private int firstPage = 0;
        private int lastPage = 0;
        private Connection con;
        private String dataSpecUrl="";
        private DataSpec ds=null;
        private AffProgram program = null;
        
        public PageDownloadTask(AffProgram p,DataSpec ds,Connection con,String url, int firstPage,int lastPage) {
            program = p;
            this.ds = ds;
            this.con = con;
            this.firstPage = firstPage;
            this.lastPage = lastPage;
            this.dataSpecUrl = url;
        }
        
        @Override
        public void run() {
            String curPageUrl="";
            for (int i = firstPage;i <= lastPage;++i) {
            //get document with wanted data
                curPageUrl = dataSpecUrl+ds.getPageParam() + "=" + i;
                org.jsoup.nodes.Document doc = JSoupTransport.retrieveDocument(con, curPageUrl, ds.getMethod());
                try {
                    //extract data from current document to DB
                    logger.info("ProcessDataSpec:::Start Page={}:Program={}:DataImport:Spec={}",new Object[]{i,program.getName(),ds.getName()});
                    importPageData(program, doc, ds);
                } catch (Exception ex) {
                    logger.error("ProcessDataSpec:::Program={}:DataImport:Spec={}:Page={} ,Exception={}",new Object[]{program.getName(),ds.getName(),i,ex.toString()});
                }finally {
                    logger.info("ProcessDataSpec:::Finish Page={}:Program={}:DataImport:Spec={}",new Object[]{i,program.getName(),ds.getName()});
                }
            }

            
        }
        
    }    
}
