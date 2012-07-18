/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.advertmanager.util;

import java.math.BigInteger;

/**
 *
 * @author ilyae
 */
public class POStats {

    private int poId;
    private int aclCount;
    private int aclId;
    
    private long             poAmount;
    private String           purchaseDay;

    public POStats() {
        
    }
    
    public POStats(int poId, int aclCount, int aclId) {
        this.poId = poId;
        this.aclCount = aclCount;
        this.aclId = aclId;
    }

    public int getAclCount() {
        return aclCount;
    }

    public void setAclcount(Number aclCount) {
        this.aclCount = aclCount.intValue();
    }

    public int getAclId() {
        return aclId;
    }

    public void setAclid(Number aclId) {
        this.aclId = aclId.intValue();
    }

    public int getPoId() {
        return poId;
    }

    public void setPoid(Number poId) {
        this.poId = poId.intValue();
    }

    public long getPoAmount() {
        return poAmount;
    }

    public void setPoamount(Number poAmount) {
        this.poAmount = poAmount.longValue();
    }

    public String getPurchaseDay() {
        return purchaseDay;
    }

    public void setPurchaseday(String purchaseDay) {
        this.purchaseDay = purchaseDay;
    }
    
    
}
