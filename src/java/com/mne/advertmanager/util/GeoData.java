/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.advertmanager.util;

/**
 *
 * @author ilyae
 */
public class GeoData {
    private String countryName;
    private String countryCode;

    public GeoData() {
    }

    public GeoData(String countryName, String countryCode) {
        this.countryName = countryName;
        this.countryCode = countryCode;
    }

    
    
    
    public String getCountryCode() {
        return countryCode;
    }

    public void setCountrycode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryname(String countryName) {
        this.countryName = countryName;
    }
    
    
}
