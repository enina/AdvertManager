/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.advertmanager.util;

import com.mne.advertmanager.model.AffProgram;
import com.mne.advertmanager.model.PurchaseOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author ilyae
 */
public class PurchaseOrderImporter implements BillingDataImporter{

    private static final Logger logger = LoggerFactory.getLogger(PurchaseOrderImporter.class);
    
    @Override
    public Object importDataItemProperty(Object target, String itemName, String itemValue) {
        
        PurchaseOrder po = (PurchaseOrder)target;
        //{"ID","Status","TrackingNumber","Country","City","PurchaseOrderSum","Commision","Partner","IP","Date"});
        if (("ID").equals(itemName))
            po.setOriginalOrderId(itemValue);
        else if (("Status").equals(itemName))
            po.setOriginalOrderStatus(itemValue);
        else if (("TrackingNumber").equals(itemName))
          po.setTrackingNumber(itemValue);
        else if (("Country").equals(itemName))
           po.setCountry(itemValue);
        else if (("City").equals(itemName))
           po.setCity(itemValue);
        else if (("PurchaseOrderSum").equals(itemName))
           po.setPOSum(itemValue);
        else if (("Commision").equals(itemName))
           po.setCommison(itemValue);
        else if (("Commision").equals(itemName))
           po.setCommison(itemValue);        
        
        

        return target;
    }

    @Override
    public void saveDataItem(AffProgram program, Object dataItem) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    
}
