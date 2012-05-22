/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.advertmanager.util;

import com.mne.advertmanager.service.BillingProjectService;
import com.mne.advertmanager.service.PurchaseOrderAggregationService;
import org.springframework.scheduling.annotation.Scheduled;


/**
 *
 * @author Nina Eidelshtein and Misha Lebedev
 */
public class AggregationDataCalculationJob {
    
    private PurchaseOrderAggregationService poAggrService;
    private BillingProjectService blngService;

    public void setBlngService(BillingProjectService blngService) {
        this.blngService = blngService;
    }

    public void setPoAggrService(PurchaseOrderAggregationService poAggrService) {
        this.poAggrService = poAggrService;
    }

    
    
    @Scheduled(cron="0 4 * * *")
    public void execute() {
        blngService.importBillingData(null);
        poAggrService.calculateAggrData();
    }


    
    
}
