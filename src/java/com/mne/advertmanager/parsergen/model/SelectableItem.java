/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.advertmanager.parsergen.model;

/**
 *
 * @author Nina Eidelshtein and Misha Lebedev
 */
public class SelectableItem {
    
    private String name;
    private String selector;

    SelectableItem() {
        
    }
    public SelectableItem(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSelector() {
        return selector;
    }

    public void setSelector(String selector) {
        this.selector = selector;
    }
    
    
    
}
