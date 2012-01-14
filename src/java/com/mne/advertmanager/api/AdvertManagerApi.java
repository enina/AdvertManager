/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.advertmanager.api;

/**
 *
 * @author tieboss
 */
public interface AdvertManagerApi {
    public Author[] getAuthors();
    public Product  getProduct(int productId);
    public Product[] getAllProducts();
    public Order     getOrder(int orderId);
    public Order[]   getAllOrders();
    public int       getTotalAccessNumber();
    public Access[]  getAccessBulk(int first,int last);
}
