/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.advertmanager.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Nina Eidelshtein and Misha Lebedev
 */
@Entity
@Table(name = "product")
@NamedQueries({
    @NamedQuery(name = "Product.findAll",
        query = "SELECT p FROM Product p "
                + "left join fetch p.productGroupId pg "
                + "left join fetch pg.affiliateId "
                + "left join fetch p.authorId"
        ),
    @NamedQuery(name = "Product.findById", query = "SELECT p FROM Product p WHERE p.id = :id"),
    @NamedQuery(name = "Product.findByDescription", query = "SELECT p FROM Product p WHERE p.description = :description"),
    @NamedQuery(name = "Product.findByPrice", query = "SELECT p FROM Product p WHERE p.price = :price"),
    @NamedQuery(name = "Product.findByCommision", query = "SELECT p FROM Product p WHERE p.commision = :commision"),
    @NamedQuery(name = "Product.findBySyncStatus", query = "SELECT p FROM Product p WHERE p.syncStatus = :syncStatus"),
    @NamedQuery(name = "Product.findByProductLink", query = "SELECT p FROM Product p WHERE p.productLink = :productLink"),
    @NamedQuery(name = "Product.findByRedirectLink", query = "SELECT p FROM Product p WHERE p.redirectLink = :redirectLink")})
public class Product implements Serializable {
    
    private static final long serialVersionUID = 1L;    
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productId")
    private Set<AccessLog> accessLogCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productId")
    private Set<PurchaseOrder> purchaseOrderCollection;
    

    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @Size(max = 256)
    @Column(name = "description")
    private String description;
    

    @NotNull
    @Column(name = "price")
    private int price;
    

    @NotNull
    @Column(name = "commision")
    private int commision;
    

    @NotNull
    @Column(name = "sync_status")
    private int syncStatus;
    

    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "product_link")
    private String productLink;
    
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "redirect_link")
    private String redirectLink;
    
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    @ManyToOne(fetch= FetchType.LAZY)
    private Author authorId;
    
    @JoinColumn(name = "product_group_id", referencedColumnName = "id")
    @ManyToOne(fetch= FetchType.LAZY)
    private ProductGroup productGroupId;

    public Product() {
    }

    public Product(Integer id) {
        this.id = id;
    }

    public Product(Integer id, int price, int commision, int syncStatus, String productLink, String redirectLink) {
        this.id = id;
        this.price = price;
        this.commision = commision;
        this.syncStatus = syncStatus;
        this.productLink = productLink;
        this.redirectLink = redirectLink;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCommision() {
        return commision;
    }

    public void setCommision(int commision) {
        this.commision = commision;
    }

    public int getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(int syncStatus) {
        this.syncStatus = syncStatus;
    }

    public String getProductLink() {
        return productLink;
    }

    public void setProductLink(String productLink) {
        this.productLink = productLink;
    }

    public String getRedirectLink() {
        return redirectLink;
    }

    public void setRedirectLink(String redirectLink) {
        this.redirectLink = redirectLink;
    }

    public Author getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Author authorId) {
        this.authorId = authorId;
    }

    public ProductGroup getProductGroupId() {
        return productGroupId;
    }

    public void setProductGroupId(ProductGroup productGroupId) {
        this.productGroupId = productGroupId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Product)) {
            return false;
        }
        Product other = (Product) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mne.advertmanager.model.Product[ id=" + id + " ]";
    }

    @XmlTransient
    public Set<AccessLog> getAccessLogCollection() {
        return accessLogCollection;
    }

    public void setAccessLogCollection(Set<AccessLog> accessLogCollection) {
        this.accessLogCollection = accessLogCollection;
    }

    @XmlTransient
    public Set<PurchaseOrder> getPurchaseOrderCollection() {
        return purchaseOrderCollection;
    }

    public void setPurchaseOrderCollection(Set<PurchaseOrder> purchaseOrderCollection) {
        this.purchaseOrderCollection = purchaseOrderCollection;
    }
    
}
