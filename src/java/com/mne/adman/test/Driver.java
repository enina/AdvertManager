/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.adman.test;

import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.helper.HttpConnection.KeyVal;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;



/**
 *
 * @author tieboss
 */
public class Driver {
    
    public static void main(String[] args) {
        
        Document doc = null;
        Connection con = null;
        try {
            //access home page without credentials. get session cookie and redirect to login form
            con = Jsoup.connect("http://friedmanspace:8080/AdvertManager/");
            con.get();
            //submit login form and authenticate
            con.url("http://friedmanspace:8080/AdvertManager/j_spring_security_check");
            con.followRedirects(false);

            doc = con.data("j_username", "ilya", "j_password",  "ilya").post();
            String sesId = con.response().cookie("JSESSIONID");
            con.followRedirects(true);
            //enter the system read home page
            Iterator iter = con.request().data().iterator();
            while (iter.hasNext()) {
                KeyVal item = (KeyVal)iter.next();
                if (item.key().equals("j_username") ||
                    item.key().equals("j_password") )
                    iter.remove();
            }
            
            con.url("http://friedmanspace:8080/AdvertManager/");
            con.cookie("JSESSIONID", sesId);
            doc = con.get();
            Element contentDiv = doc.select("html > body div[id=content]").first();
            if (contentDiv!=null)
                System.out.println("success");
            else
                System.out.println("failure");
        }catch(Exception e) {
            int x = 1;
            System.out.println("failure");
        }finally {
            try {
                if (con!=null) {
                   con.url("http://friedmanspace:8080/AdvertManager/j_spring_security_logout");
                   con.get();
                }
            } catch (IOException ex) {
                Logger.getLogger(Driver.class.getName()).log(Level.SEVERE, "failed to logout", ex);
            }
        }

        
        
    }   
}
