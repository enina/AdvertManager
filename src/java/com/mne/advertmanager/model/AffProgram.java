/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.advertmanager.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Nina Eidelshtein and Misha Lebedev
 */
/*
 * entity means that objects of this class are saved in DB
 */
@Entity
/*
 * Table annotation means in which table this class is saved
 */
@Table(name = "aff_program")
@NamedQueries({
    @NamedQuery(name = "AffProgram.findAll",
    query = "SELECT p FROM AffProgram p "
    + "left join fetch p.apg apg " //apg- AffProgramGroup
    + "left join fetch apg.affiliateId "
    + "left join fetch p.partners"),
    @NamedQuery(name = "AffProgram.findByIdAndAffiliate",
    query = "SELECT p FROM AffProgram p "
    + "left join fetch p.apg apg "
    + "left join fetch apg.affiliateId aff "
    + "left join fetch p.partners "
    + "WHERE p.id = ? and aff.affiliateName=?"),
    @NamedQuery(name = "AffProgram.findByDescription", query = "SELECT p FROM AffProgram p WHERE p.description = ?"),
    @NamedQuery(name = "AffProgram.deleteById", query = "delete AffProgram p WHERE p.id = ?"),
    @NamedQuery(name = "AffProgram.findBySyncStatus", query = "SELECT p FROM AffProgram p WHERE p.syncStatus = ?"),
    @NamedQuery(name = "AffProgram.findByAffProgramLink", query = "SELECT p FROM AffProgram p WHERE p.affProgramLink = ?"),
    @NamedQuery(name = "AffProgram.findByRedirectLink", query = "SELECT p FROM AffProgram p WHERE p.redirectLink = ?")})
public class AffProgram implements Serializable {

    private static final long serialVersionUID = 1L;
    //cascade all- if programm is deleted, all access are deleted
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "affProgram")
    private Set<AccessLog> accessLogCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "affProgram")
    private Set<PurchaseOrder> purchaseOrderCollection;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Size(max = 256)
    @Column(name = "description")
    private String description = "";
    @NotNull
    @Column(name = "sync_status")
    private int syncStatus;
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "affprogram_link")
    private String affProgramLink;
    @NotNull                        //hold user name of back office 
    @Size(min = 1, max = 256)       //
    @Column(name = "userName")      //
    private String userName;        //
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "password")
    private String password;
    @Size(min = 0, max = 256)
    @Column(name = "redirect_link")
    private String redirectLink = "";
    @JoinColumn(name = "program_group_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private AffProgramGroup apg;
    @NotNull                        //hold affProgram name as defined by 
    @Size(min = 1, max = 256)       //affiliate whan he added this AffProgram
    @Column(name = "name")          //
    private String name;            //
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "affprogram_partner", joinColumns = {
        @JoinColumn(name = "program_id")}, inverseJoinColumns = {
        @JoinColumn(name = "partner_id")})
    private Set<Partner> partners = new HashSet<Partner>(0);

    /**
     * C-tor: empty
     */
    public AffProgram() {
        id = -1;
        apg = new AffProgramGroup(-1);

    }

    /**
     * C-tor: set id only
     */
    public AffProgram(Integer id) {
        this.id = id;
    }

    public AffProgramGroup getApg() {
        return apg;
    }

    public void setApg(AffProgramGroup apg) {
        this.apg = apg;
    }

    public Set<Partner> getPartners() {
        return partners;
    }

    public void setPartners(Set<Partner> partners) {
        this.partners = partners;
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

    public int getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(int syncStatus) {
        this.syncStatus = syncStatus;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAffProgramLink() {
        return affProgramLink;
    }

    public void setAffProgramLink(String AffProgramLink) {
        this.affProgramLink = AffProgramLink;
    }

    public String getRedirectLink() {
        return redirectLink;
    }

    public void setRedirectLink(String redirectLink) {
        this.redirectLink = redirectLink;
    }

    public AffProgramGroup getAffProgramGroup() {
        return apg;
    }

    public void setAffProgramGroup(AffProgramGroup affProgramGroup) {
        this.apg = affProgramGroup;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //@Override-his func' defined in base class
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AffProgram)) {
            return false;
        }
        AffProgram other = (AffProgram) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mne.advertmanager.model.AffProgram[ id=" + id + " ]";
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
