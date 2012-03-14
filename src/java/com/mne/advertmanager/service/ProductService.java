/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.advertmanager.service;

import com.mne.advertmanager.dao.GenericDao;
import com.mne.advertmanager.model.Author;
import com.mne.advertmanager.model.Product;
import com.mne.advertmanager.model.ProductGroup;
import java.security.Principal;
import java.util.Collection;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Nina Eidelshtein and Misha Lebedev
 */
public class ProductService {

    private GenericDao<Product, Long> productDao;
    private AuthorService authorService;
    private ProductGroupService pgService;

    public void setProductDao(GenericDao<Product, Long> productDao) {
        this.productDao = productDao;
    }

    public void setAuthorService(AuthorService authorService) {
        this.authorService = authorService;
    }

    public void setProductGroupService(ProductGroupService pgService) {
        this.pgService = pgService;
    }
    
    

//============================ findAllProducts =================================
    @Transactional(readOnly = true)//, propagation = Propagation.REQUIRED)
    public Collection<Product> findAllProducts() {
        return productDao.findByQuery("Product.findAll");
    }

//============================ createProduct ===================================
    @Transactional
    public void createProduct(Product product) {
        
        Author author=null;
        ProductGroup pg = null;
        author = product.getAuthorId();
        pg = product.getProductGroupId();
        
        if (author!=null )
            author = authorService.createOrUpdate(author);
        if (pg != null)
            pg = pgService.createOrUpdate(pg);
        
        product.setAuthorId(author);
        product.setProductGroupId(pg);
        productDao.create(product);
    }
    
}
