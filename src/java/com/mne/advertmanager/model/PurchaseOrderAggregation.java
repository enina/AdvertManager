/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.advertmanager.model;

/**
 *
 * @author Nina Eidelshtein and Misha Lebedev
 */
public class PurchaseOrderAggregation {
    
    private Integer id;

    private AffProgram affProgram;
    
    private double totalSum;
    
    private int accessAmount;
    
    private int purchaseAmount;
    
    private double conversionRate;
    
    
    public PurchaseOrderAggregation() {

    }    
    
    public PurchaseOrderAggregation(Integer id) {
        this.id = id;
    }

    public int getAccessAmount() {
        return accessAmount;
    }

    public void setAccessAmount(int accessAmount) {
        this.accessAmount = accessAmount;
    }

    public AffProgram getAffProgram() {
        return affProgram;
    }

    public void setAffProgram(AffProgram affProgram) {
        this.affProgram = affProgram;
    }

    public double getConversionRate() {
        return conversionRate;
    }

    public void setConversionRate(double conversionRate) {
        this.conversionRate = conversionRate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getPurchaseAmount() {
        return purchaseAmount;
    }

    public void setPurchaseAmount(int purchaseAmount) {
        this.purchaseAmount = purchaseAmount;
    }

    public double getTotalSum() {
        return totalSum;
    }

    public void setTotalSum(double totalSum) {
        this.totalSum = totalSum;
    }
    
    
    
    
    
}
