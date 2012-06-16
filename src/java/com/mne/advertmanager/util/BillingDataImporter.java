/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.advertmanager.util;

import com.mne.advertmanager.model.AffProgram;

/**
 *
 * @author Nina Eidelshtein and Misha Lebedev
 */
public interface BillingDataImporter<T> {
    
    public T importDataItemProperty(T dataItem,String itemName,String itemValue);
    public void saveDataItem(AffProgram program,T dataItem);
}
