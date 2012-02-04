/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.advertmanager.parsergen.model;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author nina
 */
public class DataSpec extends SelectableItem{

    private String dataURL;
    private String method;
    private String listEntrySelector;
    
    private Map<String,SelectableItem> subItems =  new HashMap<String,SelectableItem>();


    private DataSpec(){
    }
    
    public DataSpec(String name) {
        super(name);
    }
    
    public String getDataURL() {
        return dataURL;
    }

    public void setDataURL(String dataURL) {
        this.dataURL = dataURL;
    }

    public Map<String,SelectableItem> getSubItems() {
        return subItems;
    }

    public void setSubItems(Map<String,SelectableItem> subItems) {
        this.subItems = subItems;
    }

    public void addSubItem(SelectableItem subItem) {
        subItems.put(subItem.getName(),subItem);
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public SelectableItem getSubItem(String subItem) {
        SelectableItem result = subItems.get(subItem);
        if (result==null) {
            result = new SelectableItem(subItem);
            addSubItem(result);
        }
        return result;
    }

    public String getListEntrySelector() {
        return listEntrySelector;
    }

    public void setListEntrySelector(String listEntrySelector) {
        this.listEntrySelector = listEntrySelector;
    }

    @Override
    public boolean isValid() {
        
        boolean result = super.isValid() && isStringValid(dataURL) && isStringValid(listEntrySelector);
        
        for (SelectableItem si:subItems.values())
            result  = result && si.isValid();
        
        return result;
    }
    
    
    
}
