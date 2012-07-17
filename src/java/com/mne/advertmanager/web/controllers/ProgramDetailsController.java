/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.advertmanager.web.controllers;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.mne.advertmanager.model.AccessLog;
import com.mne.advertmanager.model.AffProgram;
import com.mne.advertmanager.model.FilterableBehaviorStatistics;
import com.mne.advertmanager.model.PurchaseOrder;
import com.mne.advertmanager.service.AccessLogService;
import com.mne.advertmanager.service.AffProgramService;
import com.mne.advertmanager.service.BehaviorStatisticsService;
import com.mne.advertmanager.service.BillingProjectService;
import com.mne.advertmanager.service.PurchaseOrderService;
import com.mne.advertmanager.service.SearchQueryStatService;
import com.mne.advertmanager.util.AccessStats;
import com.mne.advertmanager.util.NoneException;
import com.mne.advertmanager.util.POStats;
import com.mne.advertmanager.util.Page;
import com.mne.advertmanager.util.PageCtrl;

/**
 *
 * @author Misha
 */
@Controller
public class ProgramDetailsController {

    //logger
    private static Logger logger = LoggerFactory.getLogger(ProgramDetailsController.class);

    
    //constant declaration
    private static final String AFFPROGRAM = "affprograms";
    private static final String DETAILS = "/details";
    private static final String ORDERS = "/orders";
    private static final String ACCESS = "/access";
    private static final String FINANCE = "/finance";
    private static final String AFFPROGRAM_DETAILS_REQ_MAPPING = AFFPROGRAM + DETAILS;
    private static final String AFFPROGRAM_ACCESS_REQ_MAPPING = AFFPROGRAM + "/{programId}/items/{items}/accessPage/{pageNumber}";
    private static final String ACCESS_PO_REQ_MAPPING = ACCESS + "/po/{orderId}";
    private static final String AFFPROGRAM_CALL_AGGR_DATA_REQ_MAPPING = AFFPROGRAM + "/{programId}/calculateAggregationData";
    @SuppressWarnings("unused")
	private static final String AFFPROGRAM_ORDERS_REQ_MAPPING = AFFPROGRAM + ORDERS;
    @SuppressWarnings("unused")
	private static final String AFFPROGRAM_FINANCE_REQ_MAPPING = AFFPROGRAM + FINANCE;
    private static final String BLNG_IMPORT_REQ_MAPPING = ControllerSupport.BILLING + "/import";
    private static final String AFFPROG_CALC_QUERY_STAT = AFFPROGRAM +"/{programId}"+"/calcQueryStats";
    
    //variables and object declarations
    private Gson gson = new Gson();
    
    private AffProgramService affProgramService;
    private AccessLogService accessLogService;
    private PurchaseOrderService purchaseOrderService;

    private BillingProjectService billingProjectService;
    private BehaviorStatisticsService fbsService;
    private SearchQueryStatService searchQueryStatService;

//functions
//==============================================================================
//function that respond to access list request 
//========================== viewAffProgDefintionForm ==========================
    @RequestMapping(value = AFFPROGRAM_DETAILS_REQ_MAPPING + "/{programId}", method = RequestMethod.GET)
    public ModelAndView viewProgramDetails(@PathVariable int programId) {

        //ATTENTION BE CAREFUL WITH "programId" PARAMETER THAT YOU RECEIVE BECAUSE EVE MAY INCLUDE
        //SQL INJECTION HERE SO YOU MUST CHECK IT BEFORE QUERY IT!!

        AffProgram program = affProgramService.findAffProgramByID(programId);
//
        Page<AccessLog> accessPage = null;
        Collection<PurchaseOrder> orderList = null;
        Set<FilterableBehaviorStatistics> domainStats = null;
        Collection<FilterableBehaviorStatistics> countryStats = null;
        Set<FilterableBehaviorStatistics> periodicStats = null;
        Collection<POStats> poStats = null;
        Collection<AccessStats> aclStats = null;
        if (program != null) {
            //only find data for valid programs
            //find all accesses related to this program
            accessPage = accessLogService.findAccessByAffProgamId(new PageCtrl(), programId);
            orderList  = purchaseOrderService.findPurchaseOrdersByAffProgamId(programId);
            periodicStats = fbsService.findAffProgramStatistics(programId);
           
            domainStats =  fbsService.findTotalAccessAmountByDomain(programId);
            countryStats = fbsService.findTotalAccessByCountry(programId);
            aclStats = accessLogService.findAccessAffProgStats(program);
            poStats = purchaseOrderService.retrieveAffProgPOByDateStats(program);
        }

        
        ModelAndView mav = ControllerSupport.forwardToView(logger, AFFPROGRAM_DETAILS_REQ_MAPPING + "/" + programId, AFFPROGRAM_DETAILS_REQ_MAPPING, "program", program);
        mav.addObject("accessPage", accessPage);
        mav.addObject("orderList", orderList);
        mav.addObject("partnerList", program.getPartners());
        
        FilterableBehaviorStatistics emptyStats = new FilterableBehaviorStatistics();
        HashMap<String,FilterableBehaviorStatistics> statMap = new HashMap<String,FilterableBehaviorStatistics>();
        for (FilterableBehaviorStatistics fbs : periodicStats) {
	    if (fbs != null)
		statMap.put(fbs.getType().name(), fbs);
        }
        statMap.put("empty", emptyStats);
        mav.addObject("statMap", statMap);
        mav.addObject("domainStats", domainStats);
        mav.addObject("countryStats", countryStats);
        mav.addObject("aclStats", aclStats);
        mav.addObject("poStats", poStats);

        return mav;

    }

//============================= importBillingData ==============================
    /**
     * This function start importing process of data for specified AffProgramm this function invoked from by web link and has context variable affProgramId
     */
    @RequestMapping(value = BLNG_IMPORT_REQ_MAPPING + "/{affProgramId}", method = RequestMethod.GET)
    public ModelAndView importBillingData(@PathVariable final int affProgramId, SecurityContextHolderAwareRequestWrapper securityContextWrapper, HttpServletResponse response) {

        String status = null; // hold msg that will apear to user upon failure/success.

        //get current affName
        //String affName = securityContextWrapper.getUserPrincipal().getName();
        //find curent affiliate by name
        //final Affiliate aff = affiliateService.findAffiliateWithAffPrograms(affName);
        //get security context data
        final SecurityContext securityContext = SecurityContextHolder.getContext();
        //find wanted affProgram
        final AffProgram program = affProgramService.findAffProgramByID(affProgramId);

        //run data collection in separate Thread:
        try {
            new Thread() {
                //prepare new thread function 

                @Override
                public void run() {
                    //set security context of this Thread
                    SecurityContextHolder.setContext(securityContext);
                    //set thread name for debug purposes
                    setName("Import-"+program.getName());
                    //go collect data:
                    billingProjectService.importBillingData(program);
                    
                    affProgramService.save(program);
                }
                //start thread execution
            }.start();


            status = "Started importing data for program id=" + affProgramId;
        } catch (Exception e) {
            status = ControllerSupport.handleException(logger, e, "import", "Program Data", "id=" + affProgramId);
        }

        //transfer user back to import page(same place he came from)
        ModelAndView mav = viewProgramDetails(affProgramId);
        mav.addObject("status", status);

        return mav;
    }
////=============================== calculateAggregationData ================================

    @RequestMapping(value = AFFPROGRAM_CALL_AGGR_DATA_REQ_MAPPING, method = RequestMethod.GET)
    public ModelAndView calculateAggregationData(@PathVariable final int programId, SecurityContextHolderAwareRequestWrapper securityContextWrapper, HttpServletResponse response) {

        // hold msg that will apear to user upon failure/success.
        String status = null; 

        //get security context data
        final SecurityContext securityContext = SecurityContextHolder.getContext();
        
        //run aggregation data calculation in separate Thread:
        try {
            new Thread() {
                //prepare new thread function 
                @Override
                public void run() {
                    //set security context of this Thread
                    SecurityContextHolder.setContext(securityContext);
                    //set thread name for debug purposes
                    setName("StatsCalc" + "-P" + programId);
                    //calculate aggregation data:
                    fbsService.calculateAffProgramStatistics(programId);
                }
                //start thread execution
            }.start();
            status = "Started calculating aggregation data for program id=" + programId;
        } catch (Exception e) {
            status = ControllerSupport.handleException(logger, e, "AggregationCalculation", "Program Data", "id=" + programId);
        }

        //transfer user back to import page(same place he came from)
        ModelAndView mav = viewProgramDetails(programId);
        mav.addObject("status", status);

        return mav;
    }
    
//============================= calcQueryStats =================================
    @RequestMapping(value = AFFPROG_CALC_QUERY_STAT, method = RequestMethod.GET)
    public ModelAndView calcQueryStats(@PathVariable final int programId,SecurityContextHolderAwareRequestWrapper secCtxtWrapper){


        //get security context data
        final SecurityContext secContext = SecurityContextHolder.getContext();
        
        String status = "";
        try{
            new Thread() {
                @Override
                //prepare new thread function 
                public void run(){
                    AffProgram prog = null;
                    SecurityContextHolder.setContext(secContext);
                    prog = affProgramService.findAffProgramByID(programId);

                    if(prog == null)
                        throw new NoneException("invalid program");
     
                    //set thread name for debug purposes
                    setName(AFFPROGRAM + "searchQueryStatsCalc" + " -P" + programId);
                    searchQueryStatService.calculateQueryStats(prog);
                }
            }.start();
            status = "search query statistics calculation started";
        }catch(Exception e){
                status = ControllerSupport.handleException(logger, e, "query statistics calculation", "Program Data", "id=" + programId);
        }

        ModelAndView mav = viewProgramDetails(programId);
        mav.addObject("status", status);

        return mav;

    }

    //=============================== getAccessPage ================================
    @RequestMapping(value = AFFPROGRAM_ACCESS_REQ_MAPPING, method = RequestMethod.GET)
    void getAccessPage(@PathVariable int items, @PathVariable int programId, @PathVariable int pageNumber, HttpServletResponse response) {
        try {

            Page<AccessLog> accessPage = accessLogService.findAccessByAffProgamId(new PageCtrl(pageNumber, 0, items), programId);

            String curRequest = AFFPROGRAM + "/" + programId +"/items/"+items + "/accessPage/"+pageNumber;
            String result = gson.toJson(accessPage);
            logger.debug("GetAccessPage::request={},result={}",curRequest , result);
            response.getWriter().write(result);
        } catch (IOException e) {
            String errMsg = ",Exception:" + e.getClass().getSimpleName()
                    + ((e.getMessage() == null) ? "" : " ,Message:"
                    + e.getMessage());

            logger.error("failed to retrieve accessee of affprogram (id={},Exception:{})", programId, errMsg);
        }
    }
    
    @RequestMapping(value = ACCESS_PO_REQ_MAPPING, method = RequestMethod.GET)
    void getPoAccessPage(@PathVariable int orderId,HttpServletResponse response) {
        try {

            Collection<AccessLog> accessPage = accessLogService.findAccessLogByPO(orderId);
            for (AccessLog acl:accessPage) {
                acl.setAffProgram(null);
                acl.setSourceDomainId(null);
                acl.setPo(null);
            }
            String curRequest = ACCESS + "/po/" + orderId;
            String result = gson.toJson(accessPage);
            logger.debug("getPoAccessPage::request={},result={}",curRequest , result);
            response.getWriter().write(result);
        } catch (IOException e) {
            String errMsg = ",Exception:" + e.getClass().getSimpleName()
                    + ((e.getMessage() == null) ? "" : " ,Message:"
                    + e.getMessage());

            logger.error("failed to retrieve access of purchase order (id={},Exception:{})", orderId, errMsg);
        }
    }    

    //=============================== SETTERS ======================================
    @Autowired
    public void setAccessLogService(AccessLogService accessLogService) {
        this.accessLogService = accessLogService;
    }

    @Autowired
    public void setAffProgramService(AffProgramService affProgramService) {
        this.affProgramService = affProgramService;
    }

    @Autowired
    public void setPurchaseOrderService(PurchaseOrderService purchaseOrderService) {
        this.purchaseOrderService = purchaseOrderService;
    }

    //============================= setBillingProjectService =======================    
    @Autowired
    public void setBillingProjectService(BillingProjectService billingProjectService) {
        this.billingProjectService = billingProjectService;
    }

    //============================= setPoAggrService =======================    
    @Autowired
    public void setFbsService(BehaviorStatisticsService fbsService) {
        this.fbsService = fbsService;
    }
    
    @Autowired
    public void setSearchQueryStatService(SearchQueryStatService searchQueryStatService) {
        this.searchQueryStatService = searchQueryStatService;
    }
}
