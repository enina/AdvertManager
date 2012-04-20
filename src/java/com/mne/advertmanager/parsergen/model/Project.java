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
import javax.xml.bind.annotation.*;

/**
 *
 * @author Nina Eidelshtein and Misha Lebedev
 */

@XmlSeeAlso({com.mne.advertmanager.parsergen.model.DataSpec.class,com.mne.advertmanager.parsergen.model.SelectableItem.class})
@XmlRootElement
@XmlType(propOrder={"valid","name","homeDirectory", "baseURL" ,"homePage", "username", "userField", "password","passwordField",
                    "method","cookieName","loginFormUrl","logoutUrl","selector","dataSpecList"})
@Entity
@Table(name = "billing_project_spec")
@NamedQueries({
    @NamedQuery(name = "Project.findAll", 
        query = "SELECT p FROM Project p")
                //+
                //" left join fetch p.dataSpecList ds"  +
                //" left join fetch ds.subItems")
})
public class Project implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @XmlTransient
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "name")
    private String name;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1024)
    @Column(name = "base_url")    
    private String baseURL;
    
    private String homeDirectory;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "username")    
    private String username;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "user_field")    
    private String userField;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "password")    
    private String password;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 16)
    @Column(name = "password_field")    
    private String passwordField;

    @Basic(optional = false)
    @NotNull
    @Size(min = 3, max = 8)
    @Column(name = "method")    
    private String method="get";

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "cookie_name")    
    private String cookieName;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "login_url")    
    private String loginFormUrl;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "logout_url")    
    private String logoutUrl;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "selector")    
    private String selector;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "home_page")    
    private String homePage;

    private boolean isValid = false;

    @XmlElementWrapper(name = "dataSpecList")    
    @XmlElement(name = "dataSpec")
    @OneToMany(mappedBy = "project")
    private List<DataSpec> dataSpecList = new ArrayList<DataSpec>();

    
    public Project() {

    }    
    
    public Project(Integer id) {
        this.id = id;
    }
    
    public String getBaseURL() {
        return baseURL;
    }

    public void setBaseURL(String baseURL) {
        this.baseURL = baseURL;
        normalizeBaseUrl();
    }

    public String getHomeDirectory() {
        return homeDirectory;
    }

    public void setHomeDirectory(String homeDirectory) {
        this.homeDirectory = homeDirectory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordField() {
        return passwordField;
    }

    public void setPasswordField(String passwordField) {
        this.passwordField = passwordField;
    }

    public String getUserField() {
        return userField;
    }

    public void setUserField(String userField) {
        this.userField = userField;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getCookieName() {
        return cookieName;
    }

    public void setCookieName(String cookieName) {
        this.cookieName = cookieName;
    }

    public String getLoginFormUrl() {
        return loginFormUrl;
    }

    public void setLoginFormUrl(String loginFormUrl) {
        this.loginFormUrl = loginFormUrl;
    }

    public String getSelector() {
        return selector;
    }
    
    public void setSelector(String selector) {
        this.selector = selector;
    }

    public String getLogoutUrl() {
        return logoutUrl;
    }

    public void setLogoutUrl(String logoutUrl) {
        this.logoutUrl = logoutUrl;
    }
    
    public void addDataSpec(DataSpec dataSpec) {
        dataSpecList.add( dataSpec);
    }
    
    public DataSpec getDataSpec(String name) {
        DataSpec result = null;
        for (DataSpec ds: dataSpecList) {
            if (ds.getName().equals(name)) {
                result = ds;
                break;
            }
        }
        return result;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean isValid) {
        this.isValid = isValid;
    }

    public String getHomePage() {
        return homePage;
    }

    public void setHomePage(String homePage) {
        this.homePage = homePage;
    }

    @XmlTransient
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    @XmlTransient
    public List<DataSpec> getDataSpecList() {
        return dataSpecList;
    }

    
    
    
    @Override
    public String toString() {
        return "name="+name+"\n"+
               "baseURL="+baseURL+"\n"+
               "homeDirectory="+homeDirectory+"\n"+
               "username="+username+"\n"+
                "userField="+userField+"\n"+
        "password="+password+"\n"+
        "passwordField="+passwordField+"\n"+
        "method="+method+"\n"+
        "cookieName="+cookieName+"\n"+
        "loginFormUrl="+loginFormUrl+"\n"+
        "logoutUrl="+logoutUrl+"\n"+
        "selector="+selector+"\n";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Project other = (Project) obj;
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

    private void normalizeBaseUrl() {
        if (!baseURL.endsWith("/"))
            baseURL+="/";
    }
}
