/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.advertmanager.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author Nina Eidelshtein and Misha Lebedev
 */
@Entity
@Table(name = "behavior_stats") 
public class FilterableBehaviorStatistics implements Serializable {

     
    public enum StatType {CurDay,CurMonth,PrevMonth,Total}; 
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")    
    private Integer id;
    
    @JoinColumn(name = "affprogram_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private AffProgram affProgram;
   
    @JoinColumn(name = "domain_id", referencedColumnName = "id")
    @ManyToOne(optional = false)    
    private AccessSource source;
    
    @Column(name = "stat_type")
    private StatType type;
    
    @Size(max = 256)
    @Column(name = "country_name")    
    private String countryName;
    
    @Column(name = "access_amount")
    private long accessAmount = 0;
    
    @Column(name = "purchase_amount")
    private long purchaseAmount = 0;
    
    @Column(name = "total_commision")
    private double totalCommision = 0;
    
    //C-tor
    public FilterableBehaviorStatistics() {

    }
    //C-tor
    public FilterableBehaviorStatistics(Integer id) {
        this.id = id;
    }
//=============================== FilterableBehaviorStatistics C-tor ===========
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public FilterableBehaviorStatistics(long accessAmount, AccessSource sourceDomain,AffProgram affProgram ) {
        setAccessAmount(accessAmount);
	setSource(sourceDomain);
        setAffProgram( affProgram);
    }    
//=============================== FilterableBehaviorStatistics C-tor ===========
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public FilterableBehaviorStatistics(long purchaseAmount,double totalSum,AffProgram affProgram) {
        setAffProgram(affProgram);
        setTotalCommision(totalSum);
        setPurchaseAmount(purchaseAmount);
    }    
//=============================== FilterableBehaviorStatistics C-tor ===========
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public FilterableBehaviorStatistics(long accessAmount, int purchaseAmount,double totalSum,AffProgram affProgram) {
        setAffProgram(affProgram);
        setTotalCommision(totalSum);
        setAccessAmount(accessAmount);
        setPurchaseAmount(purchaseAmount);
    }    
//=============================== FilterableBehaviorStatistics C-tor ===========
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public FilterableBehaviorStatistics(long accessAmount,String countryName,AffProgram affProgram) {
        setAffProgram(affProgram);
        setAccessAmount(accessAmount);
        setCountryName(countryName);
    }
//=============================== FilterableBehaviorStatistics C-tor ===========
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public FilterableBehaviorStatistics(long purchaseAmount,double totalSum,String countryName,AffProgram affProgram) {
        setAffProgram(affProgram);
        setPurchaseAmount(purchaseAmount);
	setTotalCommision(totalSum);
        setCountryName(countryName);
    }     
//=============================== FilterableBehaviorStatistics C-tor ===========
//====================== FilterableBehaviorStatistics Copy C-tor ===============
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public FilterableBehaviorStatistics(FilterableBehaviorStatistics source) {
	setAffProgram(source.getAffProgram());
	setAccessAmount(source.getAccessAmount());
	setCountryName(source.getCountryName());
	setPurchaseAmount(source.getPurchaseAmount());
	setSource(source.getSource());
	setTotalCommision(source.getTotalCommision());
    }

    
//======================== getters & setters ===================================
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

    public StatType getType() {
        return type;
    }

    public void setType(StatType type) {
        this.type = type;
    }

 //============================  add ============================================
    public void add(FilterableBehaviorStatistics curStat) {
        setAccessAmount(accessAmount+curStat.getAccessAmount());
        setPurchaseAmount(purchaseAmount+curStat.getPurchaseAmount());
        setTotalCommision(totalCommision+curStat.getTotalCommision());
    }
//===============================  equals ======================================
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
            return getKey().equals(other.getKey());
        }
        return true;
    }
//===============================  hashCode ====================================
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 13 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }
//===============================  getKey ======================================
    public String getKey() {

        if (source!=null)
            return source.getId().toString();
	else {
	    String result="";
            if (countryName != null) 
		result = countryName;
	    return result;
	}
    }

    

}
