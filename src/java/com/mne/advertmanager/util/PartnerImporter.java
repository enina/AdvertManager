/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.advertmanager.util;

import com.mne.advertmanager.model.*;
import com.mne.advertmanager.service.PartnerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 *
 * @author Misha
 */
public class PartnerImporter  implements BillingDataImporter <Partner>{
    
    private static final Logger logger = LoggerFactory.getLogger(PartnerImporter.class);
    
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
    public Partner importDataItemProperty(Partner partner,String itemName,String itemValue) {
        
        if (("Name").equals(itemName)) {
            partner.setName(itemValue);
        }
        else if (("Email").equals(itemName)) {
            partner.setEmail(itemValue);
        }

        return partner;
    }
    
//==================================== saveDataItem ============================
//this function save given Partner(dataItem) to DB
    @Override
    public void saveDataItem(AffProgram program,Partner partner) {
                
        if (partnerService.findPartnerByEmail(partner.getEmail())==null) {
            program.getPartners().add(partner);
            //persist partner to db
            partnerService.createPartner(partner);
        }else {
            logger.info("Partner {} already exists in db",partner.getEmail());
        }
    }
    
    @Override
    public void finalizeImport() {
        logger.info("Done importing partner data");
    }
    

}
