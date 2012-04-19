/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.advertmanager.util;

import com.mne.advertmanager.model.AccessLog;
import com.mne.advertmanager.service.AccessLogService;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Nina Eidelshtein and Misha Lebedev
 */
public class AccessLogImporter implements BillingDataImporter{
 
    private static final Logger logger = LoggerFactory.getLogger(BillingDataImporter.class);
    
    private AccessLogService  accessLogService;
    
    private DateFormat df = DateFormat.getDateInstance();
    
    
    
    
    public void setAccessLogService(AccessLogService accessLogService) {
        this.accessLogService = accessLogService;
    }
    
    

    
    @Override
    public Object importDataItemProperty(Object target,String itemName,String itemValue) {
        
        AccessLog access = (AccessLog)target;

        if (("DateTime").equals(itemName))
            access.setAccessTime(processDate(itemValue));
        else if (("IP").equals(itemName))
            access.setIpAddress(itemValue);
        else if (("Referer").equals(itemName))
            access.setLocation(itemValue);
        else if (("Target").equals(itemName))
            access.setUrl(itemValue);

        return target;
    }

    @Override
    public void saveDataItem(Object dataItem) {
        AccessLog access = (AccessLog)dataItem;
        accessLogService.createAccessLog(access);
    }
    
    private Date processDate(String value) {
        Date result = null;
        try {
            result = df.parse(value);
        }catch(Exception ex) {
            result = Calendar.getInstance().getTime();
            logger.error(ex.toString());
        }
        return result;
    }
    
}
