/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.advertmanager.util;

import com.mne.advertmanager.model.AffProgram;
import com.mne.advertmanager.model.Partner;
import com.mne.advertmanager.model.PurchaseOrder;
import com.mne.advertmanager.service.AccessLogService;
import com.mne.advertmanager.service.PartnerService;
import com.mne.advertmanager.service.PurchaseOrderService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author ilyae
 */
public class PurchaseOrderImporter implements BillingDataImporter {
    
    private static final Logger logger = LoggerFactory.getLogger(PurchaseOrderImporter.class);
    private SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    private PurchaseOrderService poService;
    private PartnerService  partnerService;
    private AccessLogService aclService;
    
    @Override
    public Object importDataItemProperty(Object target, String itemName, String itemValue) {
        
        PurchaseOrder po = (PurchaseOrder) target;
        
        if (("ID").equals(itemName)) {
            po.setOriginalOrderId(itemValue);
        } else if (("Status").equals(itemName)) {
            po.setOriginalOrderStatus(itemValue);
        } else if (("TrackingNumber").equals(itemName)) {
            po.setTrackingID(itemValue);
        } else if (("Country").equals(itemName)) {
            po.setCountry(itemValue);
        } else if (("City").equals(itemName)) {
            po.setCity(itemValue);
        } else if (("PurchaseOrderSum").equals(itemName)) {
            po.setPOSum(processSum(itemValue, po));
        } else if (("Commision").equals(itemName)) {
            po.setCommision(processCommision(itemValue));
        } else if (("IP").equals(itemName)) {
            po.setIPAddress(itemValue);
        } else if (("Date").equals(itemName)) {
            po.setOrdertime(processTime(itemValue));
        }else if (("Partner").equals(itemName)) {
            po.setPartner(processPartner(itemValue));
        }
        
        
        return target;
    }
    
    @Override
    public void saveDataItem(AffProgram program, Object dataItem) {
        PurchaseOrder po = (PurchaseOrder) dataItem;
        po.setAffProgram(program);
        
        poService.createPurchaseOrder(po);
    }

    public void setPoService(PurchaseOrderService poService) {
        this.poService = poService;
    }

    public void setPartnerService(PartnerService partnerService) {
        this.partnerService = partnerService;
    }

    public void setAclService(AccessLogService aclService) {
        this.aclService = aclService;
    }
    
    
    
    private float processSum(String itemValue, PurchaseOrder po) {
        float result = 0;
        String[] items = itemValue.split(" ");
        if (items != null) {
            if (items.length > 0) {
                try {
                    result = Float.parseFloat(items[0]);
                }catch(Exception ex) {
                    logger.error("Error {} parsing float from {}",ex.toString() , items[0]);
                    result= 0f;
                }
            }
            if (items.length > 1) {
                po.setCurrency(items[1]);
            }
        }
        
        return result;
    }
    
    private float processCommision(String itemValue) {
        float result = 0;
        String[] items = itemValue.split(" ");
        if (items != null) {
            if (items.length > 0) {
                 try {
                    result = Float.parseFloat(items[0]);
                }catch(Exception ex) {
                    logger.error("Error {} parsing float from {}",ex.toString(),items[0]);
                    result= 0f;
                }
            }
        }
        return result;
        
    }
    
    private Date processTime(String itemValue) {
        try {
            return df.parse(itemValue);
        } catch (ParseException ex) {
            logger.error(ex.toString());
            return Calendar.getInstance().getTime();
        }
    }

    private Partner processPartner(String itemValue) {
        Partner result = partnerService.findPartnerByName(itemValue);
        return result;
    }
}
