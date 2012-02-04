/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.advertmanager.parsergen.model;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Nina Eidelshtein and Misha Lebedev
 */
public class Project {
    
    private String name;
    private String baseURL;
    private String homeDirectory;
    private String username;
    private String userField;
    private String password;
    private String passwordField;
    private String method;
    private String cookieName;
    private String loginFormUrl;
    private String logoutUrl;
    private String selector;
    private boolean isValid = false;
    
    private Map<String,DataSpec> dataSpecMap = new HashMap<String,DataSpec>();

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
        this.loginFormUrl = baseURL + loginFormUrl;
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
        this.logoutUrl = baseURL + logoutUrl;
    }
    
    public void addDataSpec(DataSpec dataSpec) {
        dataSpecMap.put(dataSpec.getName(), dataSpec);
    }
    
    public DataSpec getDataSpec(String name) {
        return dataSpecMap.get(name);
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean isValid) {
        this.isValid = isValid;
    }

    
    
    private void normalizeBaseUrl() {
        if (!baseURL.endsWith("/"))
            baseURL+="/";
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
    
}
