/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.advertmanager.util;

import com.mne.advertmanager.model.AccessLog;
import com.mne.advertmanager.model.AccessSource;
import com.mne.advertmanager.model.Product;
import com.mne.advertmanager.service.AccessLogService;
import com.mne.advertmanager.service.AccessSourceService;
import com.mne.advertmanager.service.ProductService;
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
    
    private AccessLogService     accessLogService;
    private AccessSourceService  accessSourceService;
    private ProductService       productService;
    
    private DateFormat df = DateFormat.getDateInstance();
    
    
    public void setAccessLogService(AccessLogService accessLogService) {
        this.accessLogService = accessLogService;
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    public void setAccessSourceService(AccessSourceService accessSourceService) {
        this.accessSourceService = accessSourceService;
    }
    
    
    @Override
    public Object importDataItemProperty(Object target,String itemName,String itemValue) {
        
        AccessLog access = (AccessLog)target;

        if (("DateTime").equals(itemName))
            access.setAccessTime(processDate(itemValue));
        else if (("IP").equals(itemName))
            access.setIpAddress(itemValue);
        else if (("Referer").equals(itemName))
            processReferer(access, itemValue);
        else if (("Target").equals(itemName))
            access.setUrl(itemValue);

        return target;
    }

    private void processReferer(AccessLog access, String itemValue) {
        AccessSource referer = accessSourceService.findByDomain("http://yandex.ru");
        if (referer == null) {
            referer = new AccessSource(-1,"http://yandex.ru","imported domain");
            accessSourceService.create(referer);
        }
        access.setSourceDomainId(referer);
        access.setLocation(itemValue);
    }

    @Override
    public void saveDataItem(Object dataItem) {
        AccessLog access = (AccessLog)dataItem;
        Product p = productService.findProductByLink(access.getUrl());
        if (p==null) {
            p = new Product();
            p.setName("ImportedProduct");
            p.setProductLink(access.getUrl());
        }
        access.setProductId(p);
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
