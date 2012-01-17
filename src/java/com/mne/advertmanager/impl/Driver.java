/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.advertmanager.impl;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;



/**
 *
 * @author tieboss
 */
public class Driver {
    
    public static void main(String[] args) {
        ApplicationContext ctx =  new FileSystemXmlApplicationContext("file:conf/spring/advertManager-appCtx.xml");
        Object bean = ctx.getBean("advertManagerC3P0DataSource");
        System.out.println(bean.getClass().getName());
    }
}
