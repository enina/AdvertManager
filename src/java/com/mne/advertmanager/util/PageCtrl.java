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

    private int pageNumber = 1;
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
        return pageNumber;
    }

    public void setCurrentPage(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }
    
    public int getNextPage() {
        return Math.min(pageNumber+1, totalPages);
    }
    
    public int getPrevPage() {
        return Math.max(pageNumber-1, 1);
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
        
        return pageNumber*pageSize;
        
    }
}
