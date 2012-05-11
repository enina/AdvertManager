/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.advertmanager.util;

import com.mne.advertmanager.model.*;
import com.mne.advertmanager.service.AccessLogService;
import com.mne.advertmanager.service.AccessSourceService;
import com.mne.advertmanager.service.AffProgramService;
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
 
    //crate logger
    private static final Logger logger = LoggerFactory.getLogger(BillingDataImporter.class);
    
    
    private AccessLogService     accessLogService;
    private AccessSourceService  accessSourceService;
    private AffProgramService       AffProgramService;
    
    //14.04.2012 18:51
    private DateFormat df = null;
    private UniversalDetector detector  = null;

    /**C-tor: set text encoding detector and data format*/
    public AccessLogImporter() {
        detector = new UniversalDetector(null);
        df = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    }
    
    //seter used by Spring context injection
    public void setAccessLogService(AccessLogService accessLogService) {
        this.accessLogService = accessLogService;
    }
    //seter used by Spring context injection
    public void setAffProgramService(AffProgramService AffProgramService) {
        this.AffProgramService = AffProgramService;
    }
    //seter used by Spring context injection
    public void setAccessSourceService(AccessSourceService accessSourceService) {
        this.accessSourceService = accessSourceService;
    }
    
//======================== importDataItemProperty ==============================
//this function fill target(AccessLog obj) with given data(itemName:itemValue)
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
//================================== processReferer ============================
/**This function try to find domain name of given access. if domain 
 * name(acessSource) not exist in DB, then create new one and store it in DB.
 * then the function try to guess link(URL) encoding format, and convert
 * it to this format if proper format found.
 */
    private void processReferer(AccessLog access, String itemValue) {
        
        //find accessSource (domain) of given access in DB.
        AccessSource referer;
        referer = accessSourceService.findByDomain("http://yandex.ru");//instead of "http://yandex.ru"
                                                                       //should be: access.getAccesSource(),
                                                                       //or access.getDomainName().
        //if this access source dosen't exist in DB, then create one. 
        if (referer == null) {
            referer = new AccessSource(-1,"http://yandex.ru","imported domain");
            accessSourceService.create(referer);
        }
        //set update access sourceDomainId property with one of referer.
        access.setSourceDomainId(referer);
        
        try {
            //try to convert given access data (link string) to something readable,
            //by guessing proper encoding of link's URL
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
            //if encoding guessin faild, mark as unknown
            access.setLocation("unknown");
        }
    }
//========================= saveDataItem =======================================
    @Override
    public void saveDataItem(AffProgram program,Object dataItem) {
        AccessLog access = (AccessLog)dataItem;
//        AffProgram p = AffProgramService.findAffProgramByLink(access.getUrl());
//        if (p==null) {
//            p = new AffProgram();
//            p.setName("ImportedAffProgram");
//            p.setAffProgramLink(access.getUrl());
//            p.setAffProgramGroup(aff.getApgCollection().iterator().next());
//            AffProgramService.createAffProgram(p);
//        }
        access.setAffProgram(program);
        accessLogService.createAccessLog(access);
    }
//============================== processDate ===================================
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
