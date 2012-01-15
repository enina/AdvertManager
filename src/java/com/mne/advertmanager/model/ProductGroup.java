/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.advertmanager.model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Nina Eidelshtein and Misha Lebedev
 */
@Entity
@Table(name = "product_group")
@NamedQueries({
    @NamedQuery(name = "ProductGroup.findAll", query = "SELECT p FROM ProductGroup p"),
    @NamedQuery(name = "ProductGroup.findById", query = "SELECT p FROM ProductGroup p WHERE p.id = :id"),
    @NamedQuery(name = "ProductGroup.findByGroupName", query = "SELECT p FROM ProductGroup p WHERE p.groupName = :groupName"),
    @NamedQuery(name = "ProductGroup.findByDescription", query = "SELECT p FROM ProductGroup p WHERE p.description = :description")})
public class ProductGroup implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Size(max = 256)
    @Column(name = "group_name")
    private String groupName;
    @Size(max = 256)
    @Column(name = "description")
    private String description;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productGroupId")
    private Collection<Product> productCollection;
    @JoinColumn(name = "affiliate_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Affiliate affiliateId;

    public ProductGroup() {
    }

    public ProductGroup(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Collection<Product> getProductCollection() {
        return productCollection;
    }

    public void setProductCollection(Collection<Product> productCollection) {
        this.productCollection = productCollection;
    }

    public Affiliate getAffiliateId() {
        return affiliateId;
    }

    public void setAffiliateId(Affiliate affiliateId) {
        this.affiliateId = affiliateId;
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
        if (!(object instanceof ProductGroup)) {
            return false;
        }
        ProductGroup other = (ProductGroup) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mne.advertmanager.model.ProductGroup[ id=" + id + " ]";
    }
    
}
