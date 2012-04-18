/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.advertmanager.service;

import com.mne.advertmanager.dao.GenericDao;
import com.mne.advertmanager.parsergen.model.Project;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Nina Eidelshtein and Misha Lebedev
 */
public class BillingProjectService {
 
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(BillingProjectService.class);
        
    private GenericDao<Project,Long> projectDao;

    Unmarshaller jaxbUnmarshaller;
    
    public BillingProjectService() {
        try {
            JAXBContext jaxbCtx = JAXBContext.newInstance(com.mne.advertmanager.parsergen.model.Project.class);
            jaxbUnmarshaller = jaxbCtx.createUnmarshaller();
        } catch (JAXBException ex) {
            logger.error(ex.toString());
        }
    }
    
    /**
     * for each property defined on bean element in application context we must have a corresponding setter functions
     * For example:
     *  <property name="projectDao">
            <ref bean="projectDao"/>
        </property>
     * Corresponding setter functions is setProjectDao
     * @param projectDao 
     */
    public void setProjectDao(GenericDao<Project, Long> projectDao) {
        this.projectDao = projectDao;
    }

    
    
    @Transactional(readOnly = true)
    public Set<Project> findAllBillingProjects() {
        
        HashSet<Project> result = new HashSet<Project>();
        
        Collection<Project> data = projectDao.findByQuery("Project.findAll");

        if (data != null)
            result.addAll(data);
        
        return result;
    }
    
    /**

     * @param project 
     */
    @Transactional
    public void createProject(Project project) {
        projectDao.create(project);
    }

    
}
