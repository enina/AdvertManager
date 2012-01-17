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
    public AuthorDTO[] getAuthors();
    public ProductDTO  getProduct(int productId);
    public ProductDTO[] getAllProducts();
    public OrderDTO     getOrder(int orderId);
    public OrderDTO[]   getAllOrders();
    public int       getTotalAccessNumber();
    public AccessDTO[]  getAccessBulk(int first,int last);
}
