/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.advertmanager.util;

import com.mne.advertmanager.model.*;
import com.mne.advertmanager.service.AccessLogService;
import com.mne.advertmanager.service.AccessSourceService;
import com.mne.advertmanager.service.ProductService;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.mozilla.universalchardet.UniversalDetector;
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
    
    //14.04.2012 18:51
    private DateFormat df = null;
    private UniversalDetector detector  = null;

    public AccessLogImporter() {
        detector = new UniversalDetector(null);
        df = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    }
    
    
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
        try {
            
            String location = null;
            byte[] data = itemValue.getBytes("UTF-8");
            detector.handleData(data, 0, data.length);
            detector.dataEnd();
            String detectedCharset = detector.getDetectedCharset();
            
            if ("UTF-8".equals(detectedCharset))
                location = URLDecoder.decode(itemValue,"UTF-8");
            else
                location=URLDecoder.decode(itemValue,"windows-1251");
            
            access.setLocation(location);
            logger.info("detectedCharset:{}, result:{}",detectedCharset,location);

        } catch (UnsupportedEncodingException ex) {
            access.setLocation("unknown");
        }
    }

    @Override
    public void saveDataItem(Affiliate aff,Object dataItem) {
        AccessLog access = (AccessLog)dataItem;
        Product p = productService.findProductByLink(access.getUrl());
        if (p==null) {
            Author a = new Author(0, "ImportedProductAuthor", "Author@email.com");
            p = new Product();
            p.setName("ImportedProduct");
            p.setProductLink(access.getUrl());
            p.setProductGroupId(aff.getProductGroupCollection().iterator().next());
            p.setAuthorId(a);
            productService.createProduct(p);
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
