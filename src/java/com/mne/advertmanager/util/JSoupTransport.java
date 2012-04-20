/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.advertmanager.util;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.mne.advertmanager.parsergen.model.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Nina Eidelshtein and Misha Lebedev
 */
public class JSoupTransport {

    private static final Logger logger = LoggerFactory.getLogger(JSoupTransport.class);
    
    public static Document retrieveDocument(Connection con, String docUrl, String method) {
        Document result = null;
        try {
            logger.debug("Retrieve URL:{} by {}",docUrl,method);
            con.url(docUrl).timeout(0);
            if ("post".equals(method)) {
                result = con.post();
            } else {
                result = con.get();
            }
        } catch (IOException ex) {
            logger.error("failed to retrieved url {}:::Exception {}:::Message {}" ,new Object[]{ docUrl, ex.getClass().getSimpleName(),ex.getMessage()});
        }
        return result;
    }

    public static Connection login(Project proj) {

        Connection result = null;
        String url="";
        try {
            //access home page without credentials. get session cookie and redirect to login form
            url=proj.getBaseURL()+proj.getHomePage();
            logger.debug("Login.Retrieve HomePage:{}",url);
            result = Jsoup.connect(url).timeout(0);
            @SuppressWarnings("unused")
            Document doc = result.get();
            //submit login form and authenticate
            String loginUrl = proj.getBaseURL()+proj.getLoginFormUrl();
            result.url(loginUrl);
            result.followRedirects(false);
            String firstCookie = result.response().cookie(proj.getCookieName());

            logger.debug("Login.Submit Login Form:{},user:{},password{}",new Object[]{url,proj.getUsername(), proj.getPasswordField()});
            doc = result.data(proj.getUserField(), proj.getUsername(), proj.getPasswordField(), proj.getPassword()).post();
            String secondCookie = result.response().cookie(proj.getCookieName());
            String sesId="";
            if (secondCookie != null) {
                sesId=secondCookie;
            }else {
                if (firstCookie!=null)
                    sesId=firstCookie;
                else
                    return null;
            }
                 

            result.followRedirects(true);

            result.request().data().clear();

            result.cookie(proj.getCookieName(), sesId);

            return result;
        } catch (Exception e) {
            logger.error("Authentication failure connecting to {}::: Exception {}:::Message {}" ,new Object[]{url,e.getClass().getSimpleName(),e.getMessage()});
            return null;
        }
    }

    public static void logout(Connection con, Project proj) {
        try {
            if (con != null) {
                String logoutURL = proj.getBaseURL()+proj.getLogoutUrl();
                con.url(logoutURL).timeout(0);
                logger.debug("Logout:{}",logoutURL);
                con.get();
            }
        } catch (IOException e) {
            logger.error("Failed to logout::: Exception {0}:::Message {}" ,e.getClass().getSimpleName(),e.getMessage());
        }
    }
}
