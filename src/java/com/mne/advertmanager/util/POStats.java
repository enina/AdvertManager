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

    public void setAclCount(int aclCount) {
        this.aclCount = aclCount;
    }

    public int getAclId() {
        return aclId;
    }

    public void setAclId(int aclId) {
        this.aclId = aclId;
    }

    public int getPoId() {
        return poId;
    }

    public void setPoId(int poId) {
        this.poId = poId;
    }

    public long getPoAmount() {
        return poAmount;
    }

    public void setPoAmount(BigInteger poAmount) {
        this.poAmount = poAmount.longValue();
    }

    public String getPurchaseDay() {
        return purchaseDay;
    }

    public void setPurchaseDay(String purchaseDay) {
        this.purchaseDay = purchaseDay;
    }
    
    
}
