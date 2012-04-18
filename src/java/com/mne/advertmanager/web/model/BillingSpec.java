/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.advertmanager.web.model;

import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author ilyae
 */
public class BillingSpec {
    
    private MultipartFile specFile;

    public BillingSpec() {

    }

    
    
    public MultipartFile getSpecFile() {
        return specFile;
    }

    public void setSpecFile(MultipartFile specFile) {
        this.specFile = specFile;
    }
    
    
}
