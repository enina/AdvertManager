/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.advertmanager.web.infra;

import com.mne.advertmanager.util.AggregationDataCalculationJob;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContextEvent;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;
import static org.quartz.CronScheduleBuilder.dailyAtHourAndMinute;
import org.springframework.web.context.ContextLoaderListener;

/**
 * class extends standard spring class ContextLoaderListener which reads application context configuration files
 * specified by context-param contextConfigLocation in web.xml and creates all the beans in the context
 * @author Nina Eidelshtein and Misha Lebedev
 */
public class AdvertManagerWebContextListener  extends ContextLoaderListener {
    private static final Logger logger = Logger.getLogger(AdvertManagerWebContextListener.class.getName());
    
    private Scheduler scheduler;

    /**
     * called by the container upon context destruction.Used to cleanup resources
     * @param event 
     */
    @Override
    public void contextDestroyed(ServletContextEvent event) {
        try {
            super.contextDestroyed(event);
            scheduler.shutdown(false);
        } catch (SchedulerException ex) {
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
//            SchedulerFactory sf = new StdSchedulerFactory();
//            scheduler = sf.getScheduler();
//            // Scheduler will not execute jobs until it has been started (though they can be scheduled before start())
//            scheduler.start();
//            Object service = getCurrentWebApplicationContext().getBean("poAggrService");
//            JobDataMap data = new JobDataMap();
//            data.put("service", service);
//            JobDetail job1 =  newJob(AggregationDataCalculationJob.class).
//                                    withIdentity("job1", "group1").
//                                    usingJobData(data).build();
//            // fire every day at 04:00
//            Trigger trigger = newTrigger().
//                    withIdentity("trigger3", "group1").
//                    startNow().
//                    withSchedule(dailyAtHourAndMinute(4, 0)).build();
//            
//            scheduler.scheduleJob(job1, trigger);
        }catch(Exception ex) {
            logger.log(Level.SEVERE, "Context initialization failed with exception {0}", ex.getClass().getSimpleName());
        }
    }
    
}
