/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.advertmanager.util;

import com.mne.advertmanager.model.Product;
import com.mne.advertmanager.model.AccessLog;
import com.mne.advertmanager.model.AccessSource;
import com.mne.advertmanager.model.Affiliate;
import com.mne.advertmanager.model.Author;
import com.mne.advertmanager.model.ProductGroup;
import com.mne.advertmanager.model.PurchaseOrder;
import java.util.Calendar;
import java.util.Random;

/**
 *
 * @author Nina Eidelshtein and Misha Lebedev
 */
public class EntityFactory {

    
    private int magicNum = -1;
    
    public EntityFactory() {
        magicNum = new Random(System.currentTimeMillis()).nextInt();
        if (magicNum < 0 )
            magicNum*=-1;
    }
    
    public Product  makeProduct() {
        
        Product result = new Product();

        result.setCommision( (++magicNum % 100)/100);
        result.setName("ProductName"+ ++magicNum);
        result.setDescription("ProductDescription"+ ++magicNum);
        result.setPrice(++magicNum);
        result.setProductLink("http://site/productLink/"+ ++magicNum);
        result.setRedirectLink("http://site/redirectLink/"+ ++magicNum);
        result.setSyncStatus(++magicNum%2);

        return result;
    }
    public Affiliate  makeAffiliate() {
        Affiliate result = new Affiliate();
        result.setAffiliateName("AffiliateName"+ ++magicNum);
        result.setPassword(result.getAffiliateName());
        result.setEnabled((++magicNum%2)==1);
        result.setEmail("AffiliateEmail"+ ++magicNum);
        return result;

    }
    public Author  makeAuthor() {

        Author result = new Author();

        result.setAuthorName("AuthorName"+ ++magicNum);
        result.setEmail("Author@email.address."+ ++magicNum);

        return result;
    }
    public ProductGroup  makeProductGroup() {
        
        ProductGroup result = new ProductGroup();

        result.setDescription("ProductGroupDescription"+ ++magicNum);
        result.setGroupName("ProductGroupName"+ ++magicNum);

        return result;

    }

    public AccessSource  makeAccessSource() {
    
        AccessSource result = new AccessSource();

        result.setAccessSourceDomain("www.domain"+ ++magicNum+".com");
        result.setDescription("AccessSourceDescription"+ ++magicNum);

        return result;

    }
    public AccessLog  makeAccessLog() {

        AccessLog result = new AccessLog();

        result.setAccessTime(Calendar.getInstance().getTime());
        result.setIpAddress(++magicNum%256+"."+ ++magicNum%256+"."+ ++magicNum%256+"."+ ++magicNum%256);
        result.setLocation("AccessLocation"+ ++magicNum);
        result.setUrl("http://wwww.access.com/url"+ ++magicNum);

        return result;
    }

    public PurchaseOrder makePurchaseOrder() {

        PurchaseOrder result = new PurchaseOrder();

        result.setDiscount((++magicNum % 100)/100);
        result.setOrdertime(Calendar.getInstance().getTime());
        result.setStatus(++magicNum%2);

        return result;

    }
}
