/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.advertmanager.parsergen.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author nina
 */
@XmlType(propOrder={"name","dataURL" ,"method","numPages","pageParam","selector","listEntrySelector","subItems"})
@Entity
@Table(name = "billing_data_spec")
public class DataSpec implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    

    @Basic(optional = false)
    @NotNull
    @Size(min = 8, max = 512)
    @Column(name = "data_url")    
    private String dataURL;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 3, max = 8)
    @Column(name = "method")    
    private String method;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 512)
    @Column(name = "page_param")    
    private String pageParam;

    @Basic(optional = false)
    @NotNull
    @Column(name = "num_pages")    
    private int    numPages;
    
    @XmlElement
    private String name;
    
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    @ManyToOne(fetch=FetchType.LAZY)
    @SuppressWarnings("unused")
    private Project project;
    
    @XmlElementWrapper(name = "subItemList")    
    @XmlElement(name = "selectableItem")
    @OneToMany(mappedBy = "dataSpec")    
    private List<SelectableItem> subItems =  new ArrayList<SelectableItem>();


    @SuppressWarnings("unused")
    protected DataSpec(){
        id=-1;
        subItems.add(new SelectableItem("root"));
        subItems.add(new SelectableItem("listEntry"));
    }
    
    
    public DataSpec(String name) {
        this();
        this.name = name;
    }

    public DataSpec(Integer id) {
        this();
        this.id = id;
    }

    
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    
    
    
    public String getDataURL() {
        return dataURL;
    }

    public void setDataURL(String dataURL) {
        reset();
        this.dataURL = dataURL;
    }

    public List<SelectableItem> getAllSubItems() {
        return subItems;
    }

    public void setSubItems(List<SelectableItem> subItems) {
        this.subItems.addAll(subItems);
    }

    public void addSubItem(SelectableItem subItem) {
        subItems.add(subItem);
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public SelectableItem getSubItem(String subItem) {
        
        SelectableItem result = null;
        for (SelectableItem item:subItems) {
            if (item.getName().equals(subItem)) {
                result = item;
                break;
            }
        }

        if (result==null) {
            result = new SelectableItem(subItem);
            addSubItem(result);
        }
        return result;
    }

    public String getListEntrySelector() {
        return getSubItem("listEntry").getSelector();
    }

    public void setListEntrySelector(String listEntrySelector) {
        getSubItem("listEntry").setSelector(listEntrySelector);
    }

    public int getNumPages() {
        return numPages;
    }

    public void setNumPages(int numPages) {
        this.numPages = numPages;
    }

    public String getPageParam() {
        return pageParam;
    }

    public void setPageParam(String pageParam) {
        this.pageParam = pageParam;
    }

    public boolean isValid() {
        
        boolean result = (dataURL!=null) && (dataURL.length()>0);
        
        for (SelectableItem si:subItems)
            result  = result && si.isValid();
        
        return result;
    }
    
    public void reset() {
        setMethod("");
        setListEntrySelector("");
        setPageParam("");
        numPages=-1;
        for (SelectableItem si:subItems)
            si.setSelector("");
    }

    public String getName() {
        return name;
    }

    public String getSelector() {
        return getSubItem("root").getSelector();
    }

    public void setSelector(String text) {
        getSubItem("root").setSelector(text);
    }
    
    public List<SelectableItem> getDataItems() {
        List<SelectableItem> dataItems =  new ArrayList<SelectableItem>();

        for (SelectableItem item:subItems) {
            if (!item.getName().equals("root") || !item.getName().equals("listEntry")) {
                dataItems.add(item);
                break;
            }
        }
       
        return dataItems;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DataSpec other = (DataSpec) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }    
    
}
