/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.advertmanager.web.controllers;

import com.mne.advertmanager.model.Affiliate;
import com.mne.advertmanager.service.AffiliateService;
import com.mne.advertmanager.service.DataGenService;
import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Nina Eidelshtein and Misha Lebedev
 */
@Controller
@RequestMapping("/")
public class AdvertManagerController {
    
    private static Logger logger = LoggerFactory.getLogger(AdvertManagerController.class);
    private DataGenService   dataGenerator;
    private AffiliateService affiliateService;


    
    @RequestMapping("/")
    public String redirect() { 
        logger.info("redirecting to home page");
        return "redirect:home.do/";
    }
    
    @RequestMapping("home")
    public ModelAndView generateHome() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("adman");
        mav.addObject("message", "Greetings from AdMan !");
        logger.info("forwarding to view: adman");
        return mav;
    }
    
    
    
    @RequestMapping("dataGen")
    public ModelAndView generateData() {
        new Thread() {
            @Override
            public void run() {
                dataGenerator.generateDummyData();
            }
        }.start();
        
        ModelAndView mav = new ModelAndView();
        mav.setViewName("adman");
        mav.addObject("message", "Greetings from AdMan DataGen .Dummy Data is being generated!");
        return mav;
    }
    
    @RequestMapping("affiliates")
    public ModelAndView viewAffiliates() {
        
        ModelAndView mav = new ModelAndView();
        Collection<Affiliate> affiliates = affiliateService.findAllAffiliates();
        mav.setViewName("affiliate");
        mav.addObject("data", affiliates);
        return mav;
    }
    
    @Autowired
    public void setDataGenerator(DataGenService dataGenerator) {
        this.dataGenerator = dataGenerator;
    }

    @Autowired
    public void setAffiliateService(AffiliateService affiliateService) {
        this.affiliateService = affiliateService;
    }

}
