/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.advertmanager.model;

/**
 *
 * @author Nina Eidelshtein and Misha Lebedev
 */
public class FilterableBehaviorStatistics {
    
    private Integer id;
    private AffProgram affProgram;
    private AccessSource source;
    private String countryName;
    private long accessAmount;
    private long purchaseAmount;
    private double totalCommision;

    public FilterableBehaviorStatistics() {

    }
    
    public FilterableBehaviorStatistics(Integer id) {
        this.id = id;
    }
    
    
    public FilterableBehaviorStatistics(long accessAmount, AccessSource source, AffProgram affProgram ) {
        this.accessAmount = accessAmount;
        this.source = source;
        this.affProgram = affProgram;
    }    
    
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public FilterableBehaviorStatistics(long purchaseAmount,double totalSum,AffProgram affProgram) {
        setAffProgram(affProgram);
        setTotalCommision(totalSum);
        setPurchaseAmount(purchaseAmount);
    }    
    
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public FilterableBehaviorStatistics(long accessAmount, int purchaseAmount,double totalSum,AffProgram affProgram) {
        setAffProgram(affProgram);
        setTotalCommision(totalSum);
        setAccessAmount(accessAmount);
        setPurchaseAmount(purchaseAmount);
    }    
    
    public FilterableBehaviorStatistics(Integer id, AffProgram affProgram, AccessSource source, String countryName, long accessAmount, long purchaseAmount) {
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
    
        public double getConversionRate() {
        double result = 0L;
        if (accessAmount !=0)
            result = ((double)purchaseAmount)/((double)(accessAmount));
        return result;
    }

    public double getTotalCommision() {
        return totalCommision;
    }

    public  void setTotalCommision(double totalCommision) {
        this.totalCommision = totalCommision;
    }

        
    public void add(FilterableBehaviorStatistics curStat) {
        setAccessAmount(accessAmount+curStat.getAccessAmount());
        setPurchaseAmount(purchaseAmount+curStat.getPurchaseAmount());
        setTotalCommision(totalCommision+curStat.getTotalCommision());
    }
    
    public void copyFrom(FilterableBehaviorStatistics curStat) {
        setAccessAmount(curStat.getAccessAmount());
        setPurchaseAmount(curStat.getPurchaseAmount());
        setTotalCommision(curStat.getTotalCommision());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final FilterableBehaviorStatistics other = (FilterableBehaviorStatistics) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 13 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    

}
