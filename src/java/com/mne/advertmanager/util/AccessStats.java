/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.advertmanager.util;

import java.math.BigInteger;
import java.util.Date;

/**
 *
 * @author ilyae
 */
public class AccessStats {
    
    private long        accessAmount;
    private String        accessDay;

    public AccessStats() {
    }

    public AccessStats(BigInteger accessAmount, String accessDay) {
        this.accessAmount = accessAmount.longValue();
        this.accessDay = accessDay;
    }
    
    public long getAccessAmount() {
        return accessAmount;
    }

    public void setAccessamount(BigInteger  accessAmount) {
        this.accessAmount = accessAmount.longValue();
    }

    public String getAccessDay() {
        return accessDay;
    }

    public void setAccessday(String accessDay) {
        this.accessDay = accessDay;
    }
    
    
}
