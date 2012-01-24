/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.adman.test;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.ResourceLoader;

/**
 *
 * @author Nina Eidelshtein and Misha Lebedev
 */
public class DummySessionFactory  implements FactoryBean<SessionFactory>, ResourceLoaderAware, InitializingBean, DisposableBean{

    @Override
    public SessionFactory getObject() throws Exception {
        return null;
    }

    @Override
    public Class<?> getObjectType() {
        return DummySessionFactory.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void setResourceLoader(ResourceLoader rl) {
        
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        
    }

    @Override
    public void destroy() throws Exception {
        
    }
    
}
