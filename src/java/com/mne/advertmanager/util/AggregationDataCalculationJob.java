/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.advertmanager.util;

import com.mne.advertmanager.service.PurchaseOrderAggregationService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 *
 * @author Nina Eidelshtein and Misha Lebedev
 */
public class AggregationDataCalculationJob implements Job{

    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {
        PurchaseOrderAggregationService poAggrService = (PurchaseOrderAggregationService)jec.getMergedJobDataMap().get("service");
        poAggrService.calculateAggrData();
    }
    
    
}
