/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.advertmanager.model;

/**
 *
 * @author Nina Eidelshtein and Misha Lebedev
 */
public class _FilterableBehaviorStatistics {
    
    private Integer id;

    private AffProgram affProgram;
    
    private double totalCommision;
    
    private long accessAmount;
    
    private long purchaseAmount;
    
    public _FilterableBehaviorStatistics() {

    }    
    
    public _FilterableBehaviorStatistics(Integer id) {
        this.id = id;
    }

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public _FilterableBehaviorStatistics(long accessAmount, int purchaseAmount,double totalSum,AffProgram affProgram) {
        setAffProgram(affProgram);
        setTotalCommision(totalSum);
        setAccessAmount(accessAmount);
        setPurchaseAmount(purchaseAmount);
    }

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public _FilterableBehaviorStatistics(long purchaseAmount,double totalSum,AffProgram affProgram) {
        setAffProgram(affProgram);
        setTotalCommision(totalSum);
        setPurchaseAmount(purchaseAmount);
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

    public  void setAffProgram(AffProgram affProgram) {
        this.affProgram = affProgram;
    }

    public double getConversionRate() {
        double result = 0L;
        if (accessAmount !=0)
            result = ((double)purchaseAmount)/((double)(accessAmount));
        return result;
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

    public  void setPurchaseAmount(long purchaseAmount) {
        this.purchaseAmount = purchaseAmount;
    }

    public double getTotalCommision() {
        return totalCommision;
    }

    public  void setTotalCommision(double totalCommision) {
        this.totalCommision = totalCommision;
    }
    
    public void setConversionRate (double rate) {

    }

    public void addData(FilterableBehaviorStatistics curAggrData) {
        setAccessAmount(accessAmount+curAggrData.getAccessAmount());
        setPurchaseAmount(purchaseAmount+curAggrData.getPurchaseAmount());
        setTotalCommision(totalCommision+curAggrData.getTotalCommision());
    }
    
    public void copyFrom(FilterableBehaviorStatistics curAggrData) {
        setAccessAmount(curAggrData.getAccessAmount());
        setPurchaseAmount(curAggrData.getPurchaseAmount());
        setTotalCommision(curAggrData.getTotalCommision());
    }
    
    
    
    
    
}
