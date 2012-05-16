/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.advertmanager.util;

import java.util.List;

/**
 *
 * @author ilyae
 */
public class Page <T> {
    
    private PageCtrl pageCtrl;
    private List<T> items;

    public Page(List<T> items,PageCtrl pageCtrl) {
        setItems(items);
        setPageCtrl(pageCtrl);
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public PageCtrl getPageCtrl() {
        return pageCtrl;
    }

    public void setPageCtrl(PageCtrl pageCtrl) {
        this.pageCtrl = pageCtrl;
    }
    
    


}
