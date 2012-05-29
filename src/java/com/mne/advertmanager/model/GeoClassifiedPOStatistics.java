/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.advertmanager.model;

/**
 *
 * @author Nina Eidelshtein and Misha Lebedev
 */
public class GeoClassifiedPOStatistics {
    
    private Integer id;
    private AffProgram affProgram;
    private AccessSource source;
    private String countryName;
    private long accessAmount;
    private long purchaseAmount;

    public GeoClassifiedPOStatistics() {

    }
    
    public GeoClassifiedPOStatistics(Integer id) {
        this.id = id;
    }
    
    public GeoClassifiedPOStatistics(Integer id, AffProgram affProgram, AccessSource source, String countryName, long accessAmount, long purchaseAmount) {
        this.id = id;
        this.affProgram = affProgram;
        this.source = source;
        this.countryName = countryName;
        this.accessAmount = accessAmount;
        this.purchaseAmount = purchaseAmount;
    }

    public long getAccessAmount() {
        return accessAmount;
    }

    public void setAccessAmount(long accessAmount) {
        this.accessAmount = accessAmount;
    }

    public AffProgram getAffProgram() {
        return affProgram;
    }

    public void setAffProgram(AffProgram affProgram) {
        this.affProgram = affProgram;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public long getPurchaseAmount() {
        return purchaseAmount;
    }

    public void setPurchaseAmount(long purchaseAmount) {
        this.purchaseAmount = purchaseAmount;
    }

    public AccessSource getSource() {
        return source;
    }

    public void setSource(AccessSource source) {
        this.source = source;
    }
    

    

}
