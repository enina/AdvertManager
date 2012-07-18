/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.advertmanager.parsergen.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Nina Eidelshtein and Misha Lebedev
 */
@XmlType(propOrder={"name","selector"})
@Entity
@Table(name = "selectable_data_item")
public class SelectableItem implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id = -1;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "name")
    private String name;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "selector")
    private String selector;
    
    @JoinColumn(name = "dataspec_id", referencedColumnName = "id")
    @ManyToOne(fetch=FetchType.LAZY)
    @SuppressWarnings("unused")
    private DataSpec dataSpec;

    public SelectableItem() {
    }

    public SelectableItem(String name) {
        this.name = name;
    }
    @XmlTransient
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSelector() {
        return selector;
    }

    public void setSelector(String selector) {
        this.selector = selector;
    }

    public boolean isValid() {
        return isStringValid(name) && isStringValid(selector);
    }

    public void setDataSpec(DataSpec dataSpec) {
        this.dataSpec = dataSpec;
    }
    
    protected boolean isStringValid(String data) {
        return data!=null && data.length()>0;
    }

    
    
    
}
