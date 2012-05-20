/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.advertmanager.util;

/**
 *
 * @author Nina Eidelshtein and Misha Lebedev
 */
public class POAggrData {
    
    private int poAmount;
    private double totalPoSum;

    public POAggrData() {
    }

    
    
    
    public POAggrData(int poAmount, double totalPoSum) {
        this.poAmount = poAmount;
        this.totalPoSum = totalPoSum;
    }

    public int getPoAmount() {
        return poAmount;
    }

    public void setPoAmount(int poAmount) {
        this.poAmount = poAmount;
    }

    public double getTotalPoSum() {
        return totalPoSum;
    }

    public void setTotalPoSum(double totalPoSum) {
        this.totalPoSum = totalPoSum;
    }
    
    
}
