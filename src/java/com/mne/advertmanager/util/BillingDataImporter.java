/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.advertmanager.util;

/**
 *
 * @author Nina Eidelshtein and Misha Lebedev
 */
public interface BillingDataImporter {
    
    public Object importDataItemProperty(Object dataItem,String itemName,String itemValue);
    public void saveDataItem(Object dataItem);
}
