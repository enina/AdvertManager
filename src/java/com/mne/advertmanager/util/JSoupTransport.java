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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nina Eidelshtein and Misha Lebedev
 */
public class JSoupTransport {

    private static final Logger logger = Logger.getLogger(JSoupTransport.class.getName());

    
    public static Document retrieveDocument(Connection con, String docUrl, String method) {
        Document result = null;
        try {
            logger.log(Level.FINE,"RetrieveDocument:::Start : URL={0} ,Method={1}",new Object[]{docUrl,method});
            con.url(docUrl).timeout(100000);
            if ("post".equals(method)) {
                result = con.post();
            } else {
                result = con.get();
            }
            logger.log(Level.FINE,"RetrieveDocument:::Finish : URL={0} ,Method={1}",new Object[]{docUrl,method});
        } catch (IOException ex) {
            logger.log(Level.SEVERE,"RetrieveDocument:::Failure : Url={0} , Exception={1} , Message={2}" ,new Object[]{ docUrl, ex.getClass().getSimpleName(),ex.getMessage()});
        }
        return result;
    }
//================================ login =======================================
/**
 */
    public static Connection login(Project proj) {

        Connection result = null;
        String url="";
        try {
            //access home page without credentials. get session cookie and 
            //redirect to login form
            url=proj.getBaseURL()+proj.getHomePage();
            logger.log(Level.FINE,"Login:::Retrieve HomePage::: URL:{0}",url);
            result = Jsoup.connect(url).timeout(100000);
            Document doc = result.get();
            //submit login form and authenticate
            String loginUrl = proj.getBaseURL()+proj.getLoginFormUrl();
            result.url(loginUrl);
            result.followRedirects(false);
            String firstCookie = result.response().cookie(proj.getCookieName());

            logger.log(Level.FINE,"Login:::Submit Login ::: Form:{0},user:{1},password:{2}",new Object[]{loginUrl,proj.getUsername(), "******"});
            doc = result.data(proj.getUserField(), proj.getUsername(), proj.getPasswordField(), proj.getPassword()).post();
            String secondCookie = result.response().cookie(proj.getCookieName());
            String sesId="";
            if (secondCookie != null) {
                sesId=secondCookie;
            }else {
                if (firstCookie!=null) {
                    sesId=firstCookie;
                }
                else {
                    sesId="";
                }
            }
                 

            result.followRedirects(true);

            result.request().data().clear();
            
            if (sesId != null && sesId.length() > 0) {
                result.cookie(proj.getCookieName(), sesId);
            }

            return result;
        } catch (Exception e) {
            logger.log(Level.SEVERE,"Authentication failure connecting to {0} , Exception {1}, Message {2}" ,new Object[]{url,e.getClass().getSimpleName(),e.getMessage()});
            return null;
        }
    }
//=============================== logout =======================================
    public static void logout(Connection con, Project proj) {
        try {
            if (con != null) {
                String logoutURL = proj.getBaseURL()+proj.getLogoutUrl();
                con.url(logoutURL).timeout(1000);
                logger.log(Level.FINE,"Logout:::URL:{0}",logoutURL);
                con.followRedirects(false);
                Document result = con.get();
                String resDoc = result.toString();
                logger.log(Level.FINE ,"Logout:::ResultDoc:{0}",resDoc);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE,"Failed to logout::: Exception {0}:::Message {1}" ,new Object[]{e.getClass().getSimpleName(),e.getMessage()});
        }
    }
}
