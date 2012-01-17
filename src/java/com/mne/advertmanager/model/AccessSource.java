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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Nina Eidelshtein and Misha Lebedev
 */
@Entity
@Table(name = "access_source")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccessSource.findAll", query = "SELECT a FROM AccessSource a"),
    @NamedQuery(name = "AccessSource.findById", query = "SELECT a FROM AccessSource a WHERE a.id = :id"),
    @NamedQuery(name = "AccessSource.findByAccessSourceDomain", query = "SELECT a FROM AccessSource a WHERE a.accessSourceDomain = :accessSourceDomain"),
    @NamedQuery(name = "AccessSource.findByDescription", query = "SELECT a FROM AccessSource a WHERE a.description = :description")})
public class AccessSource implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "access_source_domain")
    private String accessSourceDomain;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "description")
    private String description;

    public AccessSource() {
    }

    public AccessSource(Integer id) {
        this.id = id;
    }

    public AccessSource(Integer id, String accessSourceDomain, String description) {
        this.id = id;
        this.accessSourceDomain = accessSourceDomain;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccessSourceDomain() {
        return accessSourceDomain;
    }

    public void setAccessSourceDomain(String accessSourceDomain) {
        this.accessSourceDomain = accessSourceDomain;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        if (!(object instanceof AccessSource)) {
            return false;
        }
        AccessSource other = (AccessSource) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mne.advertmanager.model.AccessSource[ id=" + id + " ]";
    }
    
}
