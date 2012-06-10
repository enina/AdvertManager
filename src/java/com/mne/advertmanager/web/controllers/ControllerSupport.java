/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.advertmanager.web.controllers;

import org.slf4j.Logger;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Nina Eidelshtein and Misha Lebedev
 */
public class ControllerSupport {
    
        public static final String BILLING = "billing";    
        //const definitions
    public static final String ADD = "/add";
    public static final String NEW = "/new";
    public static final String LIST = "/list";
    public static final String REMOVE = "/remove";
    
//============================= handleException ================================
    public static String handleException(Logger logger,Exception e, String opType, String entityType, String entityName) {
        
        String errMsg = ",Exception:" + e.getClass().getSimpleName()
                + ((e.getMessage() == null) ? "" : " ,Message:"
                + e.getMessage());

        String status = "Failed to " + opType + " " + entityType + " : " + entityName + errMsg;

        logger.error(status);
        
        return status;
    }
//============================= forwardToView ==================================

    public static ModelAndView forwardToView(Logger logger,String requestMapping, String viewName, String key, Object data) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName(viewName);
        if (key != null && data != null) {
            mav.addObject(key, data);
        }
        logger.info("{} --> {}", requestMapping, viewName);
        return mav;
    }
}
