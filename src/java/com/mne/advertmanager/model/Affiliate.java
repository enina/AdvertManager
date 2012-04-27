/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.advertmanager.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "affiliate")
@NamedQueries({
    @NamedQuery(name = "Affiliate.findAll", query = "SELECT a FROM Affiliate a"),
    @NamedQuery(name = "Affiliate.findById", query = "SELECT a FROM Affiliate a WHERE a.id = :id"),
    @NamedQuery(name = "Affiliate.findByName", query = "SELECT a FROM Affiliate a WHERE a.affiliateName = ?"),
    @NamedQuery(name = "Affiliate.findByAffiliateNameWithAffProgramsAndGroups",
        query = "SELECT a FROM Affiliate a "
                + " left join fetch a.AffProgramGroupCollection pg"
                + " left join fetch pg.AffProgramCollection prd"
                + " left join fetch prd.authorId"
                + " WHERE a.affiliateName = ?"),
    @NamedQuery(name = "Affiliate.findByEmail", query = "SELECT a FROM Affiliate a WHERE a.email = :email")})
public class Affiliate implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "affiliateName")
    private String affiliateName;
    
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "email")
    private String email;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "password")
    private String password;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "enabled")
    private boolean enabled;    
    
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "affiliateId")
    private Set<AffProgramGroup> apgCollection;

    public Affiliate() {
    }

    public Affiliate(Integer id) {
        this.id = id;
    }

    public Affiliate(Integer id, String affiliateName, String email) {
        this.id = id;
        this.affiliateName = affiliateName;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAffiliateName() {
        return affiliateName;
    }

    public void setAffiliateName(String affiliateName) {
        this.affiliateName = affiliateName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    

    public Set<AffProgramGroup> getAffProgramGroupCollection() {
        return apgCollection;
    }

    public void setAffProgramGroupCollection(Set<AffProgramGroup> AffProgramGroupCollection) {
        this.apgCollection = AffProgramGroupCollection;
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
        if (!(object instanceof Affiliate)) {
            return false;
        }
        Affiliate other = (Affiliate) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mne.advertmanager.model.Affiliate[ id=" + id + " ]";
    }
    
}
