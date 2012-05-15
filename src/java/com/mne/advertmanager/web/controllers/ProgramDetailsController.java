/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.advertmanager.web.controllers;

import com.mne.advertmanager.model.AccessLog;
import com.mne.advertmanager.model.AffProgram;
import com.mne.advertmanager.model.Affiliate;
import com.mne.advertmanager.model.PurchaseOrder;
import com.mne.advertmanager.service.*;
import java.util.Collection;
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

/**
 *
 * @author Misha
 */
@Controller
@RequestMapping("/")
public class ProgramDetailsController {

    //constant declaration
    private static final String AFFPROGRAM = "affprograms";
    private static final String DETAILS = "/details";
    private static final String ORDERS = "/orders";
    private static final String ACCESS = "/access";
    private static final String FINANCE = "/finance";
    private static final String AFFPROGRAM_DETAILS_REQ_MAPPING = AFFPROGRAM + DETAILS;
    private static final String AFFPROGRAM_ACCESS_REQ_MAPPING = AFFPROGRAM + ACCESS;
    private static final String AFFPROGRAM_ORDERS_REQ_MAPPING = AFFPROGRAM + ORDERS;
    private static final String AFFPROGRAM_FINANCE_REQ_MAPPING = AFFPROGRAM + FINANCE;
    private static final String BLNG_IMPORT_REQ_MAPPING = ControllerSupport.BILLING + "/import";
    //variables and object declarations
    private AffProgramService affProgramService;
    private AccessLogService accessLogService;
    private PurchaseOrderService purchaseOrderService;
    private PartnerService partnerService;
    private AffiliateService affiliateService;
    private BillingProjectService billingProjectService;
    //logger
    private static Logger logger = LoggerFactory.getLogger(ProgramDetailsController.class);

//functions
//==============================================================================
//function that respond to access list request 
//========================== viewAffProgDefintionForm ==========================
    @RequestMapping(value = AFFPROGRAM_DETAILS_REQ_MAPPING + "/{programId}", method = RequestMethod.GET)
    public ModelAndView viewProgramDetails(@PathVariable int programId) {

        //ATTENTION BE CAREFUL WITH "programId" PARAMETER THAT YOU RECEIVE BECAUSE EVE MAY INCLUDE
        //SQL INJECTION HERE SO YOU MUST CHECK IT BEFORE QUERY IT!!

        //prepare all data :
        //0.check recieved parameter

        //2.get all data requiered to calculate overview ( LOT OF DATA )bu now for simplicity dont do that
        //3.desplay program specification; 

        //1.find program by id
        AffProgram program = affProgramService.findAffProgramByID(programId);

        //find all accesses related to this program
        Collection<AccessLog> acessList = accessLogService.findAccessByAffProgamId(program);
        Collection<PurchaseOrder> orderList = purchaseOrderService.findPurchaseOrdersByAffProgamId(program);
        // Collection<Partner> partnerList = partnerService.findAllAccessLog();
        //create model veiw object




        ModelAndView mav = new ModelAndView();
        mav.addObject("program", program);
        mav.addObject("acessList", acessList);
        mav.addObject("orderList", orderList);
//        mav.addObject("partnerList", partnerList);

        mav.setViewName(AFFPROGRAM_DETAILS_REQ_MAPPING);

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
        String affName = securityContextWrapper.getUserPrincipal().getName();
        //find curent affiliate by name
        final Affiliate aff = affiliateService.findAffiliateWithAffPrograms(affName);
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
                    setName(ControllerSupport.BILLING + "DataImportThread" + " programId " + affProgramId);
                    //go collect data:
                    billingProjectService.importBillingData(program);
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

//======================== SETTERS ==============================================    
    @Autowired
    public void setAccessLogService(AccessLogService accessLogService) {
        this.accessLogService = accessLogService;
    }

    @Autowired
    public void setAffProgramService(AffProgramService affProgramService) {
        this.affProgramService = affProgramService;
    }

    @Autowired
    public void setPartnerService(PartnerService partnerService) {
        this.partnerService = partnerService;
    }

    @Autowired
    public void setPurchaseOrderService(PurchaseOrderService purchaseOrderService) {
        this.purchaseOrderService = purchaseOrderService;
    }
//============================= setAffiliateService ============================

    @Autowired
    public void setAffiliateService(AffiliateService affiliateService) {
        this.affiliateService = affiliateService;
    }

//============================= setBillingProjectService =======================    
    @Autowired
    public void setBillingProjectService(BillingProjectService billingProjectService) {
        this.billingProjectService = billingProjectService;
    }
}
