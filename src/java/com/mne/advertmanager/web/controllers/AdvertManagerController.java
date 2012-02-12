/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.advertmanager.web.controllers;

import com.mne.advertmanager.model.Affiliate;
import com.mne.advertmanager.model.Product;
import com.mne.advertmanager.model.User;
import com.mne.advertmanager.service.AffiliateService;
import com.mne.advertmanager.service.DataGenService;
import com.mne.advertmanager.service.ProductService;
import com.mne.advertmanager.service.UserService;
import java.util.Collection;
import javax.enterprise.inject.spi.Producer;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
    private ProductService productService;


    private UserService userService;
    private static final String USERS = "users";
    private static final String AFFILIATES = "affiliates";
    private static final String DATAGEN = "dataGen";
    private static final String APPS = "apps";
    

    private static final String APPS_PARSERGEN_REQ_MAPPING = APPS+"/parsergen";
    
    private static final String AFF_LIST_REQ_MAPPING = AFFILIATES+"/list";
    private static final String DG_GEN_REQ_MAPPING = DATAGEN+"/generate";

    
    private static final String USER_LIST_REQ_MAPPING = USERS+"/list";    
    private static final String USER_NEW_REQ_MAPPING = USERS+"/new";    
    private static final String USER_ADD_REQ_MAPPING = USERS+"/add";

    
    @RequestMapping("/")
    public String redirect() { 
        logger.info("redirecting to home page");
        return "redirect:home.do/";
    }
    
    @RequestMapping(value="home", method = RequestMethod.GET)
    public @ModelAttribute("message") String generateHome() {
        return  "Greetings from AdMan !";
    }
    
    
    
    @RequestMapping(value=DG_GEN_REQ_MAPPING, method = RequestMethod.GET)
    public ModelAndView generateData() {
        new Thread() {
            @Override
            public void run() {
                setName(DATAGEN+"Thread");
                dataGenerator.generateDummyData();
            }
        }.start();
        
        return  forwardToView(DG_GEN_REQ_MAPPING,"home","message","Greetings from AdMan DataGen .Dummy Data is being generated!");
    }
//================================ viewProducts ================================
    @RequestMapping(value= "products/list", method= RequestMethod.GET)
    public @ModelAttribute("data") Collection<Product> veiwProducts(){

        
        logger.info("getting product data");
        
        Collection<Product> products = productService.findAllProducts();
        
        int size = (products!=null)?products.size():0;
        
        logger.info("return product data to view. found {} products",size);
       
        
        return products;
    }
//================================ viewAffiliates ==============================
    @RequestMapping(value=AFF_LIST_REQ_MAPPING, method = RequestMethod.GET)
    public @ModelAttribute("data") Collection<Affiliate> viewAffiliates() {
        
        Collection<Affiliate> affiliates = affiliateService.findAllAffiliates();

        return  affiliates;
    }
//================================ viewUsers ===================================
    @RequestMapping(value=USER_LIST_REQ_MAPPING,method = RequestMethod.GET)
    public @ModelAttribute("data") Collection<User> viewUsers() {
        
        Collection<User> users = userService.findAllUsers();

        return  users;
    }
//================================ addUser =====================================
    @RequestMapping(value=USER_ADD_REQ_MAPPING, method = RequestMethod.POST)
    public ModelAndView addUser(@ModelAttribute("user")User user) {
        
        String errMsg=null;
        try {
            userService.createUser(user);
        }catch(Exception e) {
            errMsg=" Exception:"+e.getClass().getSimpleName()+ ((e.getMessage()==null)?"":
                   " ,Message:"+e.getMessage());
        }
        
        ModelAndView mav = forwardToView(USER_ADD_REQ_MAPPING,USER_LIST_REQ_MAPPING,"data",viewUsers());
        
        mav.addObject("status", (errMsg!=null)?"failed to create user:"+user.getUsername()+errMsg:
                                               "User:"+user.getUsername()+" created successfully");
        return  mav;
    }
        
    @RequestMapping(value=APPS_PARSERGEN_REQ_MAPPING, method = RequestMethod.GET)
    public @ModelAttribute("codebase") String launchParserGenerator(HttpServletRequest request) {
        
        String codebase="http://"+request.getServerName()+":"+request.getServerPort()+request.getServletContext().getContextPath()+"/apps";
        logger.info("Returning codebase="+codebase);
        return  codebase;
    }    
    
    @RequestMapping(value=USER_NEW_REQ_MAPPING, method = RequestMethod.GET)
    public @ModelAttribute("user") User viewRegistrationForm() {
        
        return  new User();
    }    
    
    
  
    


    private ModelAndView forwardToView(String requestMapping , String viewName,String key,Object data) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName(viewName);
        mav.addObject(key, data);
        logger.info("{} --> {}",requestMapping,viewName);
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
    
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
//======================== setProductService ===================================
    @Autowired
    public void setProductService(ProductService productService) {
        
        System.out.println("AdvertManagerController:setProductService...");
        this.productService = productService;
    }
   
}