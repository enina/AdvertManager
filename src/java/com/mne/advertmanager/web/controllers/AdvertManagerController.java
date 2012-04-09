/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.advertmanager.web.controllers;

import com.google.gson.Gson;
import com.mne.advertmanager.model.Affiliate;
import com.mne.advertmanager.model.Author;
import com.mne.advertmanager.model.Product;
import com.mne.advertmanager.model.ProductGroup;
import com.mne.advertmanager.service.*;
import java.io.IOException;
import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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

    private static final String ADD = "/add";
    private static final String NEW = "/new";
    private static final String LIST = "/list";
    private static final String GET = "/get";    
    
    private static final String AFFILIATES = "affiliates";
    private static final String PRODUCTS = "products";
    private static final String PROD_GROUPS = "pgroups";
    private static final String AUTHORS = "authors";
    private static final String DATAGEN = "dataGen";
    private static final String APPS = "apps";
    


    private static final String APPS_PARSERGEN_REQ_MAPPING = APPS+"/parsergen";
    
    private static final String AFF_LIST_REQ_MAPPING = AFFILIATES+LIST;
    private static final String AFF_NEW_REQ_MAPPING = AFFILIATES+NEW;
    private static final String AFF_ADD_REQ_MAPPING = AFFILIATES+ADD;
    private static final String DG_GEN_REQ_MAPPING = DATAGEN+"/generate";
    
    
    private static final String PRODUCT_NEW_REQ_MAPPING = PRODUCTS+NEW;
    private static final String PRODUCT_ADD_REQ_MAPPING = PRODUCTS+ADD;


    //private static final String AUTHOR_GET_REQ_MAPPING = AUTHORS+GET;
    //private static final String PROD_GROUPS_GET_REQ_MAPPING = PROD_GROUPS+GET;


    
    
    
    private static Logger logger = LoggerFactory.getLogger(AdvertManagerController.class);
    private DataGenService   dataGenerator;
    private AffiliateService affiliateService;
    private ProductService productService;
    private AuthorService  authorService;
    private ProductGroupService pgService;
    private Gson gson = new Gson();



    @RequestMapping("/")
    public String redirect() { 
        logger.info("redirecting to home page");
        return "redirect:mvc/home/";
    }
    /**
     * view resolution works through tiles configuration file WEB-INF/tiles-def/templates.xml
     * tile which defines presentation automatically equals the url 
     * for example for url "home" corresponding tile is    
     *                                               <definition name="home" extends=".mainTemplate">
     *                                                   <put-attribute name="content" value="/WEB-INF/view/home.jsp" />
     *                                               </definition>
     * 
     * @param securityContext
     * @return 
     */
    @RequestMapping(value="home", method = RequestMethod.GET)
    public @ModelAttribute("data") Affiliate generateHome(SecurityContextHolderAwareRequestWrapper securityContext) {
        String affName = securityContext.getUserPrincipal().getName();
        Affiliate aff = affiliateService.findAffiliateWithProductGroupsAndProducts(affName);
        return  aff;
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
    ////////////////////////////////////////////// Products /////////////////////////////////////////////////////////////////////////////
    
    @RequestMapping(value=PRODUCT_NEW_REQ_MAPPING , method = RequestMethod.GET)
    public ModelAndView viewProductDefintionForm(SecurityContextHolderAwareRequestWrapper securityContext) {
        
        ModelAndView mav = new ModelAndView(PRODUCT_NEW_REQ_MAPPING);
        String affName = securityContext.getUserPrincipal().getName();
        
        Collection<Author>       authors   = authorService.findAllAuthors();
        Collection<ProductGroup> prdGroups = pgService.findAffiliateProductGroups(affName);
        
        mav.addObject("product", new Product());
        mav.addObject("authors", authors);
        mav.addObject("prdGroups", prdGroups);
        
        return  mav;
        
    }
    
    @RequestMapping(value=PRODUCT_ADD_REQ_MAPPING , method = RequestMethod.POST)
    public ModelAndView addProduct(@ModelAttribute("product")Product product,SecurityContextHolderAwareRequestWrapper securityContext) {
        
        String status="";
        try {
            productService.createProduct(product);
            status = "Product:"+product.getName()+" created successfully";
        }catch(Exception e) {
            status = handleException(e,"create","product",product.getName());
        }
        
        ModelAndView mav = forwardToView(PRODUCT_ADD_REQ_MAPPING,"home","data",generateHome(securityContext));

        mav.addObject("status", status);
        
        return  mav;
    }    
    
    
    @RequestMapping(value= "products/list", method= RequestMethod.GET)
    public @ModelAttribute("data") Collection<Product> viewProducts(){

        
        logger.info("getting product data");
        
        Collection<Product> products = productService.findAllProducts();
        
        int size = (products!=null)?products.size():0;
        
        logger.info("return product data to view. found {} products",size);
       
        
        return products;
    }

    //////////////////////////////////////////////////// Affiliates ////////////////////////////////////////////////////////////
    
    @RequestMapping(value=AFF_LIST_REQ_MAPPING, method = RequestMethod.GET)
    public @ModelAttribute("data") Collection<Affiliate> viewAffiliates() {
        
        Collection<Affiliate> affiliates = affiliateService.findAllAffiliates();

        return  affiliates;
    }

    @RequestMapping(value=AFF_ADD_REQ_MAPPING, method = RequestMethod.POST)
    public ModelAndView addUser(@ModelAttribute("affiliate")Affiliate affiliate,SecurityContextHolderAwareRequestWrapper securityContext) {
        
        String status = null;
        try {
            affiliateService.createAffiliate(affiliate);
            status = "User:"+affiliate.getAffiliateName()+" created successfully";
        }catch(Exception e) {
            status = handleException(e,"create","affiliate",affiliate.getAffiliateName());
        }
        
       ModelAndView mav = null; 
       if (securityContext.isUserInRole("ROLE_ADMIN")) {
            mav = forwardToView(AFF_ADD_REQ_MAPPING,AFF_LIST_REQ_MAPPING,"data",viewAffiliates());
       }else {
           mav = forwardToView(AFF_ADD_REQ_MAPPING,"login",null,null);
       }
        
        mav.addObject("status", status);
        
        return  mav;
    }

        
    @RequestMapping(value=APPS_PARSERGEN_REQ_MAPPING, method = RequestMethod.GET)
    public @ModelAttribute("codebase") String launchParserGenerator(HttpServletRequest request) {
        
        String codebase="http://"+request.getServerName()+":"+request.getServerPort()+request.getServletContext().getContextPath()+"/apps";
        logger.info("Returning codebase="+codebase);
        return  codebase;
    }    
    
    @RequestMapping(value=AFF_NEW_REQ_MAPPING, method = RequestMethod.GET)
    public @ModelAttribute("affiliate") Affiliate viewRegistrationForm() {
        
        return  new Affiliate();
    }    

    //////////////////////////////////////////////////////  Author  ////////////////////////////////////////////////////////////////////////
    @RequestMapping(value=AUTHORS+"/{authorId}", method=RequestMethod.GET)
    public void getAuthor(@PathVariable int authorId,HttpServletResponse response) {
        try {
            Author author = authorService.findById(authorId);
            author.setProductCollection(null);
            String result = gson.toJson(author);
            logger.info(result);
            response.getWriter().write(result);
        }

        catch (IOException e) {
            String errMsg = ",Exception:"+e.getClass().getSimpleName()+ ((e.getMessage()==null) ? "": " ,Message:"+e.getMessage());
            logger.error("failed to retrieve author(id={},Exception:{})",authorId,errMsg);
        }
    }
    //////////////////////////////////////////////////////  Author  ////////////////////////////////////////////////////////////////////////
    
    
    //////////////////////////////////////////////////////  Product Group ////////////////////////////////////////////////////////////////////////
    @RequestMapping(value=PROD_GROUPS+"/{pgId}", method=RequestMethod.GET)
    public void getProductGroup(@PathVariable int pgId,HttpServletResponse response) {
        try {
            ProductGroup pg = pgService.findById(pgId);
            pg.setProductCollection(null);
            pg.setAffiliateId(null);
            String result = gson.toJson(pg);
            logger.info(result);
            response.getWriter().write(result);
        }
        catch (IOException e) {
            String errMsg = ",Exception:"+e.getClass().getSimpleName()+ ((e.getMessage()==null) ? "": " ,Message:"+e.getMessage());
            logger.error("failed to retrieve product  group (id={},Exception:{})",pgId,errMsg);
        }
    }
    //////////////////////////////////////////////////////  Product Group ////////////////////////////////////////////////////////////////////////
    
    
    //////////////////////////////////////////////////////  Utility ////////////////////////////////////////////////////////////////////////
    
    private String handleException(Exception e, String opType,String entityType,String entityName) {
        String errMsg = ",Exception:"+e.getClass().getSimpleName()+ ((e.getMessage()==null) ? "": " ,Message:"+e.getMessage());
        String status = "Failed to "+opType+ " " + entityType+ " : "+entityName+errMsg;
        logger.error(status);
        return status;
    }

    private ModelAndView forwardToView(String requestMapping , String viewName,String key,Object data) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName(viewName);
        if (key!=null && data!=null)
            mav.addObject(key, data);
        logger.info("{} --> {}",requestMapping,viewName);
        return mav;
    }
    
    ///////////////////////////////////////// Setters /////////////////////////////////////////////////////////////////////////////////
    
    @Autowired
    public void setDataGenerator(DataGenService dataGenerator) {
        this.dataGenerator = dataGenerator;
    }

    @Autowired
    public void setAffiliateService(AffiliateService affiliateService) {
        this.affiliateService = affiliateService;
    }
    
    @Autowired
    public void setProductService(ProductService productService) {
        
        logger.info("AdvertManagerController:setProductService...");
        this.productService = productService;
    }

    @Autowired
    public void setAuthorService(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Autowired
    public void setProductGroupService(ProductGroupService pgService) {
        this.pgService = pgService;
    }
   
    
    
}