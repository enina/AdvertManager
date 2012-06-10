/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.advertmanager.web.infra;

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import javax.servlet.ServletContextEvent;
import org.slf4j.bridge.SLF4JBridgeHandler;
//import org.quartz.*;
//import org.quartz.impl.StdSchedulerFactory;
//import static org.quartz.JobBuilder.newJob;
//import static org.quartz.TriggerBuilder.newTrigger;
//import static org.quartz.CronScheduleBuilder.dailyAtHourAndMinute;
import org.springframework.web.context.ContextLoaderListener;

/**
 * class extends standard spring class ContextLoaderListener which reads application context configuration files
 * specified by context-param contextConfigLocation in web.xml and creates all the beans in the context
 * @author Nina Eidelshtein and Misha Lebedev
 */
public class AdvertManagerWebContextListener  extends ContextLoaderListener {
    private static final Logger logger = Logger.getLogger(AdvertManagerWebContextListener.class.getName());
    
    //private Scheduler scheduler;

    /**
     * called by the container upon context destruction.Used to cleanup resources
     * @param event 
     */
    @Override
    public void contextDestroyed(ServletContextEvent event) {
        try {
            super.contextDestroyed(event);

        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Context shutdown failed with exception {0}", ex.getClass().getSimpleName());
        }
    }

    
    /**
     * called by the container upon context initialization  Used to create resources
     * @param event 
     */    
    @Override
    public void contextInitialized(ServletContextEvent event) {
        try {
            super.contextInitialized(event);
            LogManager.getLogManager().reset();
            SLF4JBridgeHandler.install();
            Logger.getLogger("com.mne").setLevel(Level.FINEST);
        }catch(Exception ex) {
            logger.log(Level.SEVERE, "Context initialization failed with exception {0}", ex.getClass().getSimpleName());
        }
    }
    
}
