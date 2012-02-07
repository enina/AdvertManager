/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.advertmanager.web.infra;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContextEvent;

import org.springframework.web.context.ContextLoaderListener;

/**
 *
 * @author Nina Eidelshtein and Misha Lebedev
 */
public class AdvertManagerWebContextListener  extends ContextLoaderListener {
    private final Logger logger = Logger.getLogger(AdvertManagerWebContextListener.class.getName());

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        super.contextDestroyed(event);
    }

    @Override
    public void contextInitialized(ServletContextEvent event) {
        try {
            super.contextInitialized(event);
        }catch(Exception ex) {
            logger.log(Level.SEVERE, "Context initialization failed with exception {0}", ex.getClass().getSimpleName());
        }
    }
    
}
