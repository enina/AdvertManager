/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.advertmanager.model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Nina Eidelshtein and Misha Lebedev
 */
@Entity
@Table(name = "access_log")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccessLog.findAll", query = "SELECT a FROM AccessLog a order by a.accessTime"),
    @NamedQuery(name = "AccessLog.findById", query = "SELECT a FROM AccessLog a WHERE a.id = :id"),
    @NamedQuery(name = "AccessLog.findByAccessTime", query = "SELECT a FROM AccessLog a WHERE a.accessTime = :accessTime"),
    @NamedQuery(name = "AccessLog.findByIpAddress", query = "SELECT a FROM AccessLog a WHERE a.ipAddress = :ipAddress"),
    @NamedQuery(name = "AccessLog.findByLocation", query = "SELECT a FROM AccessLog a WHERE a.location = :location"),
    @NamedQuery(name = "AccessLog.findByUrl", query = "SELECT a FROM AccessLog a WHERE a.url = :url")})
public class AccessLog implements Serializable {
    private static final long serialVersionUID = 1L;
    private static DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "access_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date accessTime;
    
    @Size(max = 256)
    @Column(name = "ip_address")
    private String ipAddress;
    
    @Size(max = 2048)
    @Column(name = "location")
    private String location;
    
    @Size(max = 256)
    @Column(name = "url")
    private String url;
    
    @JoinColumn(name = "source_domain_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private AccessSource sourceDomain;
    
    @JoinColumn(name = "affprogram_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private AffProgram affProgram;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "accessId")
    private Collection<PurchaseOrder> purchaseOrderCollection;

    public AccessLog() {
    }

    public AccessLog(Integer id) {
        this.id = id;
    }

    public AccessLog(Integer id, Date accessTime) {
        this.id = id;
        this.accessTime = accessTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getAccessTime() {
        return accessTime;
    }
    
    public String getTimeAsString() {
        return df.format(accessTime);
    }

    public void setAccessTime(Date accessTime) {
        this.accessTime = accessTime;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public AccessSource getSourceDomain() {
        return sourceDomain;
    }

    public void setSourceDomainId(AccessSource sourceDomain) {
        this.sourceDomain = sourceDomain;
    }

    public AffProgram getAffProgram() {
        return affProgram;
    }

    public void setAffProgram(AffProgram affProgram) {
        this.affProgram = affProgram;
    }

    @XmlTransient
    public Collection<PurchaseOrder> getPurchaseOrderCollection() {
        return purchaseOrderCollection;
    }

    public void setPurchaseOrderCollection(Collection<PurchaseOrder> purchaseOrderCollection) {
        this.purchaseOrderCollection = purchaseOrderCollection;
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
        if (!(object instanceof AccessLog)) {
            return false;
        }
        AccessLog other = (AccessLog) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mne.advertmanager.model.AccessLog[ id=" + id + " ]";
    }
    
}
