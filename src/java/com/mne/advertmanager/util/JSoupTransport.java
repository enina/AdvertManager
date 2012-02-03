/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.advertmanager.util;

import com.mne.advertmanager.parsergen.model.Project;
import java.io.IOException;
import java.sql.Driver;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 *
 * @author Nina Eidelshtein and Misha Lebedev
 */
public class JSoupTransport {

    private static final Logger logger = Logger.getLogger(JSoupTransport.class.getName());

    public static Document retrieveDocument(Connection con, String docUrl, String method) {
        Document result = null;
        try {
            con.url(docUrl);
            if ("post".equals(method)) {
                result = con.post();
            } else {
                result = con.get();
            }
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "failed to retrieved url:" + docUrl, ex);
        }
        return result;
    }

    public static Connection login(Project proj) {

        Connection result = null;

        try {
            //access home page without credentials. get session cookie and redirect to login form
            result = Jsoup.connect(proj.getBaseURL());
            result.get();
            //submit login form and authenticate
            result.url(proj.getLoginFormUrl());
            result.followRedirects(false);

            result.data(proj.getUserField(), proj.getUsername(), proj.getPasswordField(), proj.getPassword()).post();
            String sesId = result.response().cookie(proj.getCookieName());
            if (sesId == null) {
                return null;
            }

            result.followRedirects(true);

            result.request().data().clear();

            result.cookie(proj.getCookieName(), sesId);

            return result;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "authentication failure", e);
            return null;
        }
    }

    public static void logout(Connection con, Project proj) {
        try {
            if (con != null) {
                con.url(proj.getLogoutUrl());
                con.get();
            }
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "failed to logout", ex);
        }

    }
}
