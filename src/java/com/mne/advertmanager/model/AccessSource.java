

/**AccessSource class:
 * This class is entity representation of access_source table in DB.
 * purpose: hold distinct access source domain names. eg. when client write in
 * google some search and then thru result of his search come to 
 * purchase site. then in billing system we see that source of client access
 * is google... something, and destination is the  purchase site.
 * so we store those access source domain for statistical purpose. we may for
 * example retrieve most popular domains, that generated lots of accesses
 * to purchase sites etc.    
 */
package com.mne.advertmanager.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Nina Eidelshtein and Misha Lebedev
 */
@Entity                         //tell to hibernate that this class is DB entity
@Table(name = "access_source")  //tell to hibernate what table in db this class represent
@XmlRootElement                 //used to persist this class to file in xml maner and viceversa  ( not needed! )

@NamedQueries({                 //known DB queries : 
    @NamedQuery(name = "AccessSource.findAll", query = "SELECT a FROM AccessSource a"),
    @NamedQuery(name = "AccessSource.findById", query = "SELECT a FROM AccessSource a WHERE a.id = :id"),
    @NamedQuery(name = "AccessSource.findByAccessSourceDomain", query = "SELECT a FROM AccessSource a WHERE a.accessSourceDomain = ?"),
    @NamedQuery(name = "AccessSource.findByDescription", query = "SELECT a FROM AccessSource a WHERE a.description = :description")})
public class AccessSource implements Serializable {
    private static final long serialVersionUID = 1L;
    
    
    @Id                                                 //id: unique key
    @GeneratedValue(strategy = GenerationType.AUTO) //in DB.
    @Basic(optional = false)                            //
    @Column(name = "id")                                //
    private Integer id;                                 //
    
    @Basic(optional = false)                //accessSourceDomain: ex. 
    @NotNull                                //http://www.google.co.il/
    @Size(min = 1, max = 256)               //
    @Column(name = "access_source_domain")  //
    private String accessSourceDomain;      //
    
    
    @Basic(optional = false)            //description: ex. search engine
    @NotNull                            //
    @Size(min = 1, max = 256)           //
    @Column(name = "description")       //
    private String description;         //

   /**empty C-tor*/
    public AccessSource() {
    }
    /**C-tor: params: id. set obj id with given id*/
    public AccessSource(Integer id) {
        this.id = id;
    }
    /**C-tor: params: id, accessSourceDomain, description.
     * set obj properties with those given in params. */
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
