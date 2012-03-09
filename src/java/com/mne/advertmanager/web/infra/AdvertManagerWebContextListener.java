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
 * class extends standard spring class ContextLoaderListener which reads application context configuration files
 * specified by context-param contextConfigLocation in web.xml and creates all the beans in the context
 * @author Nina Eidelshtein and Misha Lebedev
 */
public class AdvertManagerWebContextListener  extends ContextLoaderListener {
    private static final Logger logger = Logger.getLogger(AdvertManagerWebContextListener.class.getName());

    /**
     * called by the container upon context destruction.Used to cleanup resources
     * @param event 
     */
    @Override
    public void contextDestroyed(ServletContextEvent event) {
        super.contextDestroyed(event);
    }

    
    /**
     * called by the container upon context initialization  Used to create resources
     * @param event 
     */    
    @Override
    public void contextInitialized(ServletContextEvent event) {
        try {
            super.contextInitialized(event);
        }catch(Exception ex) {
            logger.log(Level.SEVERE, "Context initialization failed with exception {0}", ex.getClass().getSimpleName());
        }
    }
    
}
