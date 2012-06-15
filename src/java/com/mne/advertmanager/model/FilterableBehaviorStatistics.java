/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.advertmanager.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 *
 * @author Nina Eidelshtein and Misha Lebedev
 */
@Entity
@Table(name = "behavior_stats")
@NamedQueries({
    @NamedQuery(name = "Fbs.findAll", query = "SELECT fbs FROM FilterableBehaviorStatistics fbs"),
    
    @NamedQuery(name = "Fbs.findAffProgCountryTypeStats",
                query = "SELECT fbs FROM FilterableBehaviorStatistics fbs left join fetch fbs.affProgram affProg where "
                        + "affProg.id=? and fbs.countryName=? and fbs.type=?"),
    
    @NamedQuery(name = "Fbs.findAffProgTypeStats",
                query = "SELECT fbs FROM FilterableBehaviorStatistics fbs left join fetch fbs.affProgram affProg where "
                        + "affProg.id=? and fbs.type=?"),
        
    @NamedQuery(name = "Fbs.findAffProgCountryStats",
                query = "SELECT fbs FROM FilterableBehaviorStatistics fbs left join fetch fbs.affProgram affProg where affProg.id=? and fbs.countryName=?"),
    
    @NamedQuery(name = "Fbs.findAffiliateCountryTypeStats",
                query = "select fbs from FilterableBehaviorStatistics fbs " +
                        "left join fetch fbs.affProgram prog "              + 
                        "left join fetch prog.apg apg "                     + 
                        "left join fetch apg.affiliateId affiliate "        + 
                        "where affiliate.id=? and fbs.countryName=? and fbs.type=?"),
    
/*
 * 
 *         SELECT
            new com.mne.advertmanager.model.FilterableBehaviorStatistics(count(po),sum(po.commision),po.countryName,po.countryCode,po.affProgram)
        FROM
            PurchaseOrder po
	WHERE
	    po in (select po from PurchaseOrder po  where po.affProgram.id = ? and po.ordertime > ?  group by po.ipAddress)
        GROUP BY po.countryName

 * 
 * 
 * 
 */
    @NamedQuery(name = "Fbs.calcAffProgPeriodFinancialStats",
                query = "SELECT " +
                        "new com.mne.advertmanager.model.FilterableBehaviorStatistics(count (po),sum(po.commision),po.affProgram) " +
                        "FROM  PurchaseOrder po "  + 
                        "WHERE po.affProgram.id = ? and po.ordertime > ?"),

    @NamedQuery(name = "Fbs.calcAffProgPeriodFinancialCountryStats",
                query = "SELECT " +
                        "new com.mne.advertmanager.model.FilterableBehaviorStatistics(count(po),sum(po.commision),po.countryName,po.countryCode,po.affProgram) " +
                        "FROM  PurchaseOrder po "  + 
                        "WHERE po in  " +
			"(select po from PurchaseOrder po  where po.affProgram.id = ? and po.ordertime > ?  group by po.ipAddress) " +
			"GROUP BY po.countryName"),

    
    @NamedQuery(name = "Fbs.calcAffProgPeriodAccessDomainStats",
                query = "SELECT " +
                        "new com.mne.advertmanager.model.FilterableBehaviorStatistics(count (acl),acl.sourceDomain,acl.affProgram) " +
                        "FROM  AccessLog acl "  + 
                        "WHERE acl in " +
                        "(select acl from AccessLog acl  where acl.affProgram.id = ? and acl.accessTime > ?  group by ip_address) " +
                        "GROUP BY acl.sourceDomain")   ,
    
    @NamedQuery(name = "Fbs.calcAffProgPeriodAccessCountryStats",
                query = "SELECT " +
                        "new com.mne.advertmanager.model.FilterableBehaviorStatistics (count (acl),acl.countryName,acl.countryCode ,acl.affProgram) " +
                        "FROM  AccessLog acl "  + 
                        "WHERE acl in " +
                        "(select acl from AccessLog acl  where acl.affProgram.id = ? and acl.accessTime > ?  group by ip_address) " +
                        "GROUP BY acl.countryName") ,
    
    @NamedQuery(name = "Fbs.deleteAffProgTypeStats",
                query = "delete from FilterableBehaviorStatistics fbs where fbs.affProgram.id = ? and fbs.type = ? "),
    
    @NamedQuery(name = "Fbs.findTypeAccessByDomain",
		query=	"from FilterableBehaviorStatistics fbs " +
			"left join fetch fbs.source src "+
			"left join fetch fbs.affProgram prog " +
			"where src is not null and prog.id=? and fbs.type=? " +
			"order by fbs.accessAmount desc"),
    
    @NamedQuery(name = "Fbs.findTypeAccessByCountry",
		query=	"from FilterableBehaviorStatistics fbs " +
			"left join fetch fbs.affProgram prog " +
			"where fbs.countryName is not null and fbs.countryName != 'Filter.All' and prog.id=? and fbs.type=? " +
			"order by fbs.accessAmount desc")
  
})
/*
 * delete from PrevMonthBehaviorStats where affProgram.id=?
 */
public class FilterableBehaviorStatistics implements Serializable {

    public enum StatType {

        CurDay, CurMonth, PrevMonth, Total
    };
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
    
    @Enumerated(EnumType.STRING)
    @Column(name = "stat_type",length=32)
    private StatType type = StatType.CurDay;
    
    
    @Size(max = 256)
    @Column(name = "cn",length=256)
    private String countryName;
    
    @Size(max = 4)
    @Column(name = "cc",length=4)
    private String countryCode;
    
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
    public FilterableBehaviorStatistics(long accessAmount, AccessSource sourceDomain, AffProgram affProgram) {
        setAccessAmount(accessAmount);
        setSource(sourceDomain);
        setAffProgram(affProgram);
    }
//=============================== FilterableBehaviorStatistics C-tor ===========

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public FilterableBehaviorStatistics(long purchaseAmount, double totalSum, AffProgram affProgram) {
        setAffProgram(affProgram);
        setTotalCommision(totalSum);
        setPurchaseAmount(purchaseAmount);
    }
//=============================== FilterableBehaviorStatistics C-tor ===========

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public FilterableBehaviorStatistics(long accessAmount, int purchaseAmount, double totalSum, AffProgram affProgram) {
        setAffProgram(affProgram);
        setTotalCommision(totalSum);
        setAccessAmount(accessAmount);
        setPurchaseAmount(purchaseAmount);
    }
//=============================== FilterableBehaviorStatistics C-tor ===========

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public FilterableBehaviorStatistics(long accessAmount, String countryName, String countryCode, AffProgram affProgram) {
        setAffProgram(affProgram);
        setAccessAmount(accessAmount);
        setCountryName(countryName);
        setCountryCode(countryCode);
    }
//=============================== FilterableBehaviorStatistics C-tor ===========

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public FilterableBehaviorStatistics(long purchaseAmount, double totalSum, String countryName, String countryCode, AffProgram affProgram) {
        setAffProgram(affProgram);
        setPurchaseAmount(purchaseAmount);
        setTotalCommision(totalSum);
        setCountryName(countryName);
        setCountryCode(countryCode);
    }
//====================== FilterableBehaviorStatistics Copy C-tor ===============

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public FilterableBehaviorStatistics(FilterableBehaviorStatistics source) {
        setAffProgram(source.getAffProgram());
        setAccessAmount(source.getAccessAmount());
        setCountryName(source.getCountryName());
        setCountryCode(source.getCountryCode());
        setPurchaseAmount(source.getPurchaseAmount());
        setSource(source.getSource());
        setTotalCommision(source.getTotalCommision());
    }
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public FilterableBehaviorStatistics(FilterableBehaviorStatistics source,StatType newType) {
        this(source);
        setType(newType);
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

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
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
        if (accessAmount != 0) {
            result = ((double) purchaseAmount) / ((double) (accessAmount));
        }

        return result;
    }

    public double getTotalCommision() {
        return totalCommision;
    }

    public void setTotalCommision(double totalCommision) {
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
        setAccessAmount(accessAmount + curStat.getAccessAmount());
        setPurchaseAmount(purchaseAmount + curStat.getPurchaseAmount());
        setTotalCommision(totalCommision + curStat.getTotalCommision());
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

        if (source != null) {
            return source.getId().toString();
        } else {
            String result = "";
            if (countryName != null) {
                result = countryName;
            }
            return result;
        }
    }
}
