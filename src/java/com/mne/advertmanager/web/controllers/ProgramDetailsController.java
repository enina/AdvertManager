/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.advertmanager.web.controllers;

import com.mne.advertmanager.model.AccessLog;
import com.mne.advertmanager.model.AffProgram;
import com.mne.advertmanager.model.AffProgramGroup;
import com.mne.advertmanager.service.AffProgramService;
import java.util.Collection;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Misha
 */
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
    
    //functions
    
    
//==============================================================================
    //function that responce to access list request 
    
    
    
    
//========================== viewAffProgDefintionForm ==========================
    @RequestMapping(value = AFFPROGRAM_DETAILS_REQ_MAPPING, method = RequestMethod.GET)
    public ModelAndView viewProgramDetails(SecurityContextHolderAwareRequestWrapper securityContext,
             @RequestParam("programId") final int programID) {
        
        //ATTENTION BE CAREFUL WITH "programId" PARAMETER THAT YOU RECEIVE BECAUSE EVE MAY INCLUDE
        //SQL INJECTION HERE SO YOU MUST CHECK IT BEFORE QUERY IT!!
             
        //prepare all data :
        //0.check recieved parameter
       
        //2.get all data requiered to calculate overview ( LOT OF DATA )bu now for simplicity dont do that
        //3.desplay program specification; 
        
        //1.find program by id
        AffProgram program  =  affProgramService.findAffProgramByID(programID);
        
        
        //create model veiw object
        ModelAndView mav = new ModelAndView(AFFPROGRAM_DETAILS_REQ_MAPPING);
        

        mav.addObject("program", program);
       

        return mav;

    }

    
}
