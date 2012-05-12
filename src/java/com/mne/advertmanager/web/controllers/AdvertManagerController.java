/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.advertmanager.web.controllers;

import com.google.gson.Gson;
import com.mne.advertmanager.model.*;
import com.mne.advertmanager.parsergen.model.Project;
import com.mne.advertmanager.service.*;
import com.mne.advertmanager.web.model.BillingSpec;
import java.io.IOException;
import java.net.URL;
import java.security.Principal;
import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Nina Eidelshtein and Misha Lebedev
 */
@Controller
@RequestMapping("/")
public class AdvertManagerController {

    //const definitions
    private static final String ADD = "/add";
    private static final String NEW = "/new";
    private static final String LIST = "/list";
    @SuppressWarnings("unused")
    private static final String GET = "/get";
    private static final String AFFILIATES = "affiliates";
    private static final String AFFPROGRAM = "affprograms";
    private static final String AFFPROG_GROUPS = "afprgroups";
    private static final String AUTHORS = "authors";
    private static final String DATAGEN = "dataGen";
    private static final String APPS = "apps";
    private static final String BILLING = "billing";
    private static final String ACCESS = "access";
    private static final String APPS_PARSERGEN_REQ_MAPPING = APPS + "/parsergen";
    private static final String AFF_LIST_REQ_MAPPING = AFFILIATES + LIST;
    private static final String AFF_NEW_REQ_MAPPING = AFFILIATES + NEW;
    private static final String AFF_ADD_REQ_MAPPING = AFFILIATES + ADD;
    private static final String DG_GEN_REQ_MAPPING = DATAGEN + "/generate";
    private static final String AFFPROGRAM_NEW_REQ_MAPPING = AFFPROGRAM + NEW;
    private static final String AFFPROGRAM_ADD_REQ_MAPPING = AFFPROGRAM + ADD;
    private static final String AFFPROGRAM_LIST_REQ_MAPPING = AFFPROGRAM + LIST;
    private static final String AFFPROGRAM_DETAILS_REQ_MAPPING = AFFPROGRAM + "/details";

    private static final String BLNG_LIST_REQ_MAPPING = BILLING + LIST;
    private static final String BLNG_NEW_REQ_MAPPING = BILLING + NEW;
    private static final String BLNG_ADD_REQ_MAPPING = BILLING + ADD;
    private static final String BLNG_IMPORT_REQ_MAPPING = BILLING + "/import";
    private static final String BLNG_DETAILS_REQ_MAPPING = BILLING + "/details";
    private static final String BLNG_DELETE_REQ_MAPPING = BILLING + "/delete";
    private static final String ACS_LIST_REQ_MAPPING = ACCESS + LIST;
    //============= variables and objects ======================================
    private static Logger logger = LoggerFactory.getLogger(AdvertManagerController.class);
    private DataGenService dataGenerator;
    private AffiliateService affiliateService;
    private AffProgramService affProgramService;
    private AffProgramGroupService apgService;
    private BillingProjectService billingProjectService;
    private AccessLogService accessLogService;
    private Gson gson = new Gson();
    private Unmarshaller jaxbUnmarshaller;

    //C-tor
    public AdvertManagerController() {
        try {
            //preapare XML marshaler
            JAXBContext jaxbCtx = JAXBContext.newInstance(com.mne.advertmanager.parsergen.model.Project.class);
            jaxbUnmarshaller = jaxbCtx.createUnmarshaller();
        } catch (JAXBException ex) {
            logger.error(ex.toString());
        }
    }

//=========================== redirect =========================================
    /**
     * this ctrl function redirect users from root URL to home page URL
     */
    @RequestMapping("/")
    public String redirect() {
        logger.info("redirecting to home page");
        return "redirect:mvc/home/";
    }
//======================== generateHome ========================================

    /**
     * view resolution works through tiles configuration file WEB-INF/tiles-def/templates.xml tile which defines presentation automatically equals the url for example for url
     * "home" corresponding tile is <definition name="home" extends=".mainTemplate"> <put-attribute name="content" value="/WEB-INF/view/home.jsp" /> </definition>
     *
     * @param securityContext
     * @return
     */
    @RequestMapping(value = "home", method = RequestMethod.GET)
    public @ModelAttribute("data")
    Affiliate generateHome(SecurityContextHolderAwareRequestWrapper securityContext) {
        String affName = securityContext.getUserPrincipal().getName();
        Affiliate aff = affiliateService.findAffiliateWithAffPrograms(affName);
        return aff;
    }
//========================== generateData ======================================

    @RequestMapping(value = DG_GEN_REQ_MAPPING, method = RequestMethod.GET)
    public ModelAndView generateData() {
        new Thread() {

            @Override
            public void run() {
                setName(DATAGEN + "Thread");
                dataGenerator.generateDummyData();
            }
        }.start();

        return forwardToView(DG_GEN_REQ_MAPPING, "home", "message",
                "Greetings from AdMan DataGen .Dummy Data is being generated!");
    }

//========================== viewAffProgDefintionForm ==========================
    @RequestMapping(value = AFFPROGRAM_NEW_REQ_MAPPING, method = RequestMethod.GET)
    public ModelAndView viewAffProgDefintionForm(SecurityContextHolderAwareRequestWrapper securityContext) {

        ModelAndView mav = new ModelAndView(AFFPROGRAM_NEW_REQ_MAPPING);
        String affName = securityContext.getUserPrincipal().getName();

        Collection<AffProgramGroup> apGroups = apgService.findAffiliateProgramGroups(affName);

        mav.addObject("affprogram", new AffProgram());
        mav.addObject("apGroups", apGroups);

        return mav;

    }
//============================ addAffProgram ===================================

    @RequestMapping(value = AFFPROGRAM_ADD_REQ_MAPPING, method = RequestMethod.POST)
    public ModelAndView addAffProgram(@ModelAttribute("affprogram") AffProgram affprogram,
            SecurityContextHolderAwareRequestWrapper securityContext) {

        String status = "";
        try {
            affProgramService.createAffProgram(affprogram);
            status = "Affprogram:" + affprogram.getName() + " created successfully";
        } catch (Exception e) {
            status = handleException(e, "create", "Affprogram", affprogram.getName());
        }

        ModelAndView mav = forwardToView(AFFPROGRAM_ADD_REQ_MAPPING, "home",
                "data", generateHome(securityContext));

        mav.addObject("status", status);

        return mav;
    }

//=============================== viewAffPrograms ==============================
    @RequestMapping(value = AFFPROGRAM_LIST_REQ_MAPPING, method = RequestMethod.GET)
    public @ModelAttribute("data")
    Collection<AffProgram> viewAffPrograms() {


        logger.info("getting affprogram data");

        Collection<AffProgram> affprograms = affProgramService.findAllAffPrograms();

        int size = (affprograms != null) ? affprograms.size() : 0;

        logger.info("return affprogram data to view. found {} affprograms", size);


        return affprograms;
    }
//=============================== viewProgramDetails() =========================

    /**
     * This function response when user want see an affProgram detailed information and graphs. the function find all relevant data, then redirect user view to proper window
     */
    @RequestMapping(value = AFFPROGRAM_DETAILS_REQ_MAPPING + "/{affProgramId}",  method = RequestMethod.GET)
    public ModelAndView viewProgramDetails(@PathVariable final int affProgramId) {


        AffProgram program = affProgramService.findAffProgramByID(affProgramId);
        
        return forwardToView(AFFPROGRAM_DETAILS_REQ_MAPPING + "/"+affProgramId, AFFPROGRAM_DETAILS_REQ_MAPPING, "program", program);

        
        /*
         * after return statement, the redirection goes to view with same requestMapping as one that bring control to this function ( it happens automaticly by spring
         * control-->view resolver )
         */
    }

//=========================== viewAffiliates ===================================  
    @RequestMapping(value = AFF_LIST_REQ_MAPPING, method = RequestMethod.GET)
    public @ModelAttribute("data")
    Collection<Affiliate> viewAffiliates() {

        Collection<Affiliate> affiliates = affiliateService.findAllAffiliates();

        return affiliates;
    }
//============================= addUser ========================================

    @RequestMapping(value = AFF_ADD_REQ_MAPPING, method = RequestMethod.POST)
    public ModelAndView addUser(@ModelAttribute("affiliate") Affiliate affiliate,
            SecurityContextHolderAwareRequestWrapper securityContext) {

        String status = null;
        try {
            affiliateService.createAffiliate(affiliate);
            status = "User:" + affiliate.getAffiliateName() + " created successfully";
        } catch (Exception e) {
            status = handleException(e, "create", "affiliate", affiliate.getAffiliateName());
        }

        ModelAndView mav = null;
        if (securityContext.isUserInRole("ROLE_ADMIN")) {
            mav = forwardToView(AFF_ADD_REQ_MAPPING, AFF_LIST_REQ_MAPPING, "data", viewAffiliates());
        } else {
            mav = forwardToView(AFF_ADD_REQ_MAPPING, "login", null, null);
        }

        mav.addObject("status", status);

        return mav;
    }
//===================================== launchParserGenerator ==================

    @RequestMapping(value = APPS_PARSERGEN_REQ_MAPPING, method = RequestMethod.GET)
    public @ModelAttribute("codebase")
    String launchParserGenerator(HttpServletRequest request) {

        String codebase = "http://" + request.getServerName() + ":"
                + request.getServerPort()
                + request.getServletContext().getContextPath()
                + "/apps";

        logger.info("Returning codebase=" + codebase);
        return codebase;
    }

    @RequestMapping(value = AFF_NEW_REQ_MAPPING, method = RequestMethod.GET)
    public @ModelAttribute("affiliate")
    Affiliate viewRegistrationForm() {

        return new Affiliate();
    }

//============================= getAffProgramGroup =============================
    @RequestMapping(value = AFFPROG_GROUPS + "/{pgId}", method = RequestMethod.GET)
    public void getAffProgramGroup(@PathVariable int pgId, HttpServletResponse response) {
        try {
            AffProgramGroup pg = apgService.findById(pgId);
            pg.setProgramCollection(null);
            pg.setAffiliateId(null);
            String result = gson.toJson(pg);
            logger.info(result);
            response.getWriter().write(result);
        } catch (IOException e) {
            String errMsg = ",Exception:" + e.getClass().getSimpleName()
                    + ((e.getMessage() == null) ? "" : " ,Message:"
                    + e.getMessage());

            logger.error("failed to retrieve affprogram  group (id={},Exception:{})", pgId, errMsg);
        }
    }
//============================= viewBillingProjects ============================

    @RequestMapping(value = BLNG_LIST_REQ_MAPPING, method = RequestMethod.GET)
    public @ModelAttribute("data")
    Collection<Project> viewBillingProjects() {

        Collection<Project> result = billingProjectService.findAllBillingProjects();

        return result;
    }
//============================= uploadBillingSpecification =====================

    /**
     * This function open Billing specification file, then create billing Project and store it in db.
     */
    @RequestMapping(value = BLNG_ADD_REQ_MAPPING, method = RequestMethod.POST)
    public ModelAndView uploadBillingSpecification(@ModelAttribute("billingSpec") BillingSpec blngSpec,
            SecurityContextHolderAwareRequestWrapper securityContext) {

        String status = null;
        MultipartFile specFile = null;  //spec file handler
        try {
            //get spec file that uploaded
            specFile = blngSpec.getSpecFile();

            //create new Billing Project form uploaded file data
            Project proj = (Project) jaxbUnmarshaller.unmarshal(specFile.getInputStream());


            //save Billing Project to DB
            billingProjectService.createProject(proj);

            //compose status massege
            status = "File " + specFile.getOriginalFilename() + " uploaded successfuly";

        } catch (Exception e) {
            String specName = "";
            if (specFile != null) {
                specName += specFile.getName();
            }
            status = handleException(e, "upload", "billingSpec", specName);
        }

        ModelAndView mav = null;
        mav = forwardToView(BLNG_ADD_REQ_MAPPING, BLNG_LIST_REQ_MAPPING, "data", viewBillingProjects());
        mav.addObject("status", status);

        return mav;
    }
//============================= importBillingData ==============================

    /**
     * This function start importing process of data for specified AffProgramm this function invoked from by web link and has context variable affProgramId
     */
    @RequestMapping(value = BLNG_IMPORT_REQ_MAPPING + "/{affProgramId}", method = RequestMethod.GET)
    public ModelAndView importBillingData(@PathVariable final int affProgramId, SecurityContextHolderAwareRequestWrapper securityContextWrapper) {

        String status = null; // hold msg that will apear to user upon failure/success.

        //get current affName
        String affName = securityContextWrapper.getUserPrincipal().getName();
        //find curent affiliate by name
        final Affiliate aff = affiliateService.findAffiliateWithAffPrograms(affName);
        //get security context data
        final SecurityContext securityContext = SecurityContextHolder.getContext();
        //find wanted affProgram
        final AffProgram program = affProgramService.findAffProgramByID(affProgramId);

        /**
         * TODO verify that current affiliate is owner of subject affProgram and only then proceed to data importing task!!!
         */
        //run data collection in separate Thread:
        try {
            new Thread() {
                //prepare new thread function 

                @Override
                public void run() {
                    //set security context of this Thread
                    SecurityContextHolder.setContext(securityContext);
                    //set thred name
                    setName(BILLING + "DataImportThread" + " programId " + affProgramId);

                    //go collect data:
                    //old: billingProjectService.importBillingData(aff,blngProjId);
                    billingProjectService.importBillingData(program);
                }
                //start thread execution
            }.start();

                
            status = "Started importing data for program id=" + affProgramId;
        } catch (Exception e) {
            status = handleException(e, "import", "Program Data", "id=" + affProgramId);
        }

        //transfer user back to import page(same place he came from)

        ModelAndView mav = viewProgramDetails(affProgramId);
        mav.addObject("status", status);

        return mav;
    }
//============================= viewUploadSpecForm =============================

    @RequestMapping(value = BLNG_NEW_REQ_MAPPING, method = RequestMethod.GET)
    public @ModelAttribute("billingSpec") BillingSpec viewUploadSpecForm() {

        return new BillingSpec();
    }
    //================================================== viewBillingProjectDetails ================================
    @RequestMapping(value = BLNG_DETAILS_REQ_MAPPING+"/{projectId}", method = RequestMethod.GET)
    public ModelAndView viewBillingProjectDetails(@PathVariable int projectId) {
        
        Project proj = billingProjectService.findProjectById(projectId);
        
        return forwardToView(BLNG_DETAILS_REQ_MAPPING+"/projectId", BLNG_DETAILS_REQ_MAPPING, "project", proj);
        
    }
    
    //================================================== deleteBillingProjectDetails ================================
    @RequestMapping(value = BLNG_DELETE_REQ_MAPPING+"/{projectId}", method = RequestMethod.GET)
    public ModelAndView deleteBillingProjectDetails(@PathVariable int projectId) {
        
        billingProjectService.delete(projectId);
        
        ModelAndView result = forwardToView(BLNG_DELETE_REQ_MAPPING+"/projectId", BLNG_LIST_REQ_MAPPING, "status", "Successfully deleted project:"+projectId);

        result.addObject("data", viewBillingProjects());
        
        return result;
        
    }    

////============================= viewAccessLog =================================
//    @RequestMapping(value ="/{programId}" + ACS_LIST_REQ_MAPPING , method = RequestMethod.GET)
    public @ModelAttribute("data")
    Collection<AccessLog> viewAccessLog() {
//        Collection<AccessLog> result = accessLogService.findAllAccessLog();
//
     //   return result;
         return null;
    }

//============================= handleException ================================
    private String handleException(Exception e, String opType, String entityType, String entityName) {
        String errMsg = ",Exception:" + e.getClass().getSimpleName()
                + ((e.getMessage() == null) ? "" : " ,Message:"
                + e.getMessage());

        String status = "Failed to " + opType + " " + entityType + " : "
                + entityName + errMsg;

        logger.error(status);
        return status;
    }
//============================= forwardToView ==================================

    private ModelAndView forwardToView(String requestMapping, String viewName, String key, Object data) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName(viewName);
        if (key != null && data != null) {
            mav.addObject(key, data);
        }
        logger.info("{} --> {}", requestMapping, viewName);
        return mav;
    }
//================================= SETERS =====================================
//============================= setDataGenerator ===============================
    @Autowired
    public void setDataGenerator(DataGenService dataGenerator) {
        this.dataGenerator = dataGenerator;
    }
//============================= setAffiliateService ============================

    @Autowired
    public void setAffiliateService(AffiliateService affiliateService) {
        this.affiliateService = affiliateService;
    }
//============================= setAffProgramService ===========================

    @Autowired
    public void setAffProgramService(AffProgramService affprogramService) {

        logger.info("AdvertManagerController:setAffProgramService...");
        this.affProgramService = affprogramService;
    }

//============================= setAffProgramGroupService ======================
    @Autowired
    public void setAffProgramGroupService(AffProgramGroupService pgService) {
        this.apgService = pgService;
    }
//============================= setBillingProjectService =======================    

    @Autowired
    public void setBillingProjectService(BillingProjectService billingProjectService) {
        this.billingProjectService = billingProjectService;
    }
//============================= setAccessLogService ============================

    @Autowired
    public void setAccessLogService(AccessLogService accessLogService) {
        this.accessLogService = accessLogService;
    }
}