/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.advertmanager.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author Nina Eidelshtein and Misha Lebedev
 */
@Entity
@Table(name = "affprog_group")
@NamedQueries({
    @NamedQuery(name = "AffProgramGroup.findAllByAffName", 
        query = "SELECT pg FROM AffProgramGroup pg" +
                 " left join fetch pg.affiliateId aff" +
                 " where aff.affiliateName=?"),
    @NamedQuery(name = "AffProgramGroup.findAll", query = "SELECT pg FROM AffProgramGroup pg"),
    @NamedQuery(name = "AffProgramGroup.findById", query = "SELECT pg FROM AffProgramGroup pg WHERE pg.id = :id"),
    @NamedQuery(name = "AffProgramGroup.findByGroupName", query = "SELECT pg FROM AffProgramGroup pg WHERE pg.groupName = :groupName"),
    @NamedQuery(name = "AffProgramGroup.findByDescription", query = "SELECT pg FROM AffProgramGroup pg WHERE pg.description = :description")})



public class AffProgramGroup implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Size(max = 256)
    @Column(name = "group_name")
    private String groupName;
    
    @Size(max = 256)
    @Column(name = "description")
    private String description;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "apg")
    private Set<AffProgram> programCollection;

    @JoinColumn(name = "affiliate_id", referencedColumnName = "id")
    @ManyToOne(fetch=FetchType.LAZY)
    private Affiliate affiliateId;

    public AffProgramGroup() {
        programCollection = new HashSet<AffProgram>();
    }

    public AffProgramGroup(Integer id) {
        super();
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

    public Set<AffProgram> getProgramCollection() {
        return programCollection;
    }

    public void setProgramCollection(Set<AffProgram> programCollection) {
        this.programCollection = programCollection;
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
        if (!(object instanceof AffProgramGroup)) {
            return false;
        }
        AffProgramGroup other = (AffProgramGroup) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mne.advertmanager.model.AffProgramGroup[ id=" + id + " ]";
    }

    public void addProgram(AffProgram curProd) {
        programCollection.add(curProd);
    }
    
}
