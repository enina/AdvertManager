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
    
    private long accessAmount;
    
    private long purchaseAmount;
    
    public PurchaseOrderAggregation() {

    }    
    
    public PurchaseOrderAggregation(Integer id) {
        this.id = id;
    }

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public PurchaseOrderAggregation(long accessAmount, int purchaseAmount,double totalSum,AffProgram affProgram) {
        setAffProgram(affProgram);
        setTotalSum(totalSum);
        setAccessAmount(accessAmount);
        setPurchaseAmount(purchaseAmount);
    }

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public PurchaseOrderAggregation(long purchaseAmount,double totalSum,AffProgram affProgram) {
        setAffProgram(affProgram);
        setTotalSum(totalSum);
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

    public double getTotalSum() {
        return totalSum;
    }

    public  void setTotalSum(double totalSum) {
        this.totalSum = totalSum;
    }
    
    public void setConversionRate (double rate) {

    }

    public void addData(PurchaseOrderAggregation curAggrData) {
        setAccessAmount(accessAmount+curAggrData.getAccessAmount());
        setPurchaseAmount(purchaseAmount+curAggrData.getPurchaseAmount());
        setTotalSum(totalSum+curAggrData.getTotalSum());
    }
    
    public void copyFrom(PurchaseOrderAggregation curAggrData) {
        setAccessAmount(curAggrData.getAccessAmount());
        setPurchaseAmount(curAggrData.getPurchaseAmount());
        setTotalSum(curAggrData.getTotalSum());
    }
    
    
    
    
    
}
