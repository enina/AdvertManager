/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.advertmanager.service;

import com.mne.advertmanager.model.Product;
import com.mne.advertmanager.dao.GenericDao;
import java.util.Collection;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;



/**
 *
 * @author Nina Eidelshtein and Misha Lebedev
 */
public class ProductService {
    
    private GenericDao<Product,Long> productDao;

    public void setProductDao(GenericDao<Product,Long> productDao) {
        this.productDao = productDao;
    }
    
//============================ findAllProducts =================================

   @Transactional(readOnly = true)//, propagation = Propagation.REQUIRED)
    public Collection<Product> findAllProducts(){
        return productDao.findByQuery("Product.findAll");
    }
}
