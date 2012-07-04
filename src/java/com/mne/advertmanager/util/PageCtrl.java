/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.advertmanager.util;

/**
 *
 * @author ilyae
 */
public class PageCtrl {

    private int pageIdx = 0;
    private int totalPages = 0;
    private int pageSize = 5;
    
    public PageCtrl() {
        
    }
    
    public PageCtrl(int pageNumber) {
        this(pageNumber,0,5);
    }    
    
    
    public PageCtrl(int pageNumber, int totalPages, int pageSize) {
        setCurrentPage(pageNumber); 
        setTotalPages(totalPages);
        setPageSize(pageSize);
    }
    
    public int getCurrentPage() {
        return pageIdx+1;
    }

    public void setCurrentPage(int pageNumber) {
        this.pageIdx = pageNumber-1;
    }

    public int getPageSize() {
        return pageSize;
    }
    
    public int getNextPage() {
        return Math.min(getCurrentPage()+1, totalPages);
    }
    
    public int getPrevPage() {
        return Math.max(getCurrentPage()-1, 1);
    }
    

    public void setPageSize(int pageSize) {
        if (pageSize >0)
            this.pageSize = pageSize;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getFirstResult() {
        
        return pageIdx*pageSize;
        
    }
}
