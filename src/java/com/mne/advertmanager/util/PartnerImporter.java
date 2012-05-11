/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.advertmanager.util;

import com.mne.advertmanager.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mne.advertmanager.service.PartnerService;
/**
 *
 * @author Misha
 */
public class PartnerImporter  implements BillingDataImporter{
    
    private static final Logger logger = LoggerFactory.getLogger(BillingDataImporter.class);
    
    private PartnerService  partnerService;

    /**C-tor: empty*/
    public PartnerImporter() {
        
    }
    
    //seter used by Spring context injection
    public void setPartnerService(PartnerService partnerService) {
        this.partnerService = partnerService;
    }

 

//=========================== importDataItemProperty ===========================
 //this function fill target(Partner obj) with given data(itemName:itemValue)
    @Override
    public Object importDataItemProperty(Object target,String itemName,String itemValue) {
        
        Partner partner = (Partner)target;

        if (("Name").equals(itemName))
            partner.setName(itemValue);
        else if (("Email").equals(itemName))
            partner.setEmail(itemValue);

        return target;
    }
    
//==================================== saveDataItem ============================
//this function save given Partner(dataItem) to DB
    @Override
    public void saveDataItem(AffProgram program,Object dataItem) {
        Partner partner = (Partner)dataItem;
//
//        aff.getId()
//        //save to Partner table :
//        //save to affiliate_to_partner table :
//        if (p==null) {
//            Author a = new Author(0, "ImportedProductAuthor", "Author@email.com");
//            p = new Product();
//            p.setName("ImportedProduct");
//            p.setProductLink(access.getUrl());
//            p.setProductGroupId(aff.getProductGroupCollection().iterator().next());
//            p.setAuthorId(a);
//            productService.createProduct(p);
//        }
//        access.setProductId(p);
//        accessLogService.createAccessLog(access);
    }
    

}
