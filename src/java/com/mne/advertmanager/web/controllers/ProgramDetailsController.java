/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.advertmanager.web.controllers;

import com.mne.advertmanager.model.AccessLog;
import com.mne.advertmanager.model.AffProgram;
import com.mne.advertmanager.model.Partner;
import com.mne.advertmanager.model.PurchaseOrder;
import com.mne.advertmanager.service.AccessLogService;
import com.mne.advertmanager.service.AffProgramService;
import com.mne.advertmanager.service.PartnerService;
import com.mne.advertmanager.service.PurchaseOrderService;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
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
    
    private static final String  DETAILS = "/details";
    private static final String  ORDERS = "/orders";
    private static final String  ACCESS = "/access";
    private static final String  FINANCE = "/finance";
    
    

    private static final String AFFPROGRAM_DETAILS_REQ_MAPPING = AFFPROGRAM +DETAILS;
    private static final String AFFPROGRAM_ACCESS_REQ_MAPPING = AFFPROGRAM +ACCESS;
    private static final String AFFPROGRAM_ORDERS_REQ_MAPPING = AFFPROGRAM +ORDERS;
    private static final String AFFPROGRAM_FINANCE_REQ_MAPPING = AFFPROGRAM +FINANCE;


    
    //variables and object declarations
    private AffProgramService affProgramService;
    private AccessLogService accessLogService;
    private PurchaseOrderService purchaseOrderService;
    private PartnerService partnerService;
    
    //functions
    
    
//==============================================================================
    //function that responce to access list request 
    
    
    
    
//========================== viewAffProgDefintionForm ==========================
    @RequestMapping(value = AFFPROGRAM_DETAILS_REQ_MAPPING +"/{programId}", method = RequestMethod.GET)
    public ModelAndView viewProgramDetails(@PathVariable int programId) {
         
        //ATTENTION BE CAREFUL WITH "programId" PARAMETER THAT YOU RECEIVE BECAUSE EVE MAY INCLUDE
        //SQL INJECTION HERE SO YOU MUST CHECK IT BEFORE QUERY IT!!
             
        //prepare all data :
        //0.check recieved parameter
       
        //2.get all data requiered to calculate overview ( LOT OF DATA )bu now for simplicity dont do that
        //3.desplay program specification; 
        
        //1.find program by id
        AffProgram program  =  affProgramService.findAffProgramByID(programId);
        
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
    
//======================== SETERS ==============================================    
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
    
}
