/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.advertmanager.service;

import com.mne.advertmanager.dao.ProductDaoImpl;

/**
 *
 * @author Nina Eidelshtein and Misha Lebedev
 */
public class ProductService {
    
    private ProductDaoImpl productDao;

    public void setProductDao(ProductDaoImpl productDao) {
        this.productDao = productDao;
    }
    
}
