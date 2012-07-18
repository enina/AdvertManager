/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.advertmanager.util;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author ilyae
 */
public class BatchDBSaver<T> {
    
    private     ExecutorService     executor = null;
    private     Set<T>              batch = new HashSet<T>();
    private     int                 threadCount = 5;
    private     int                 maxBatchSize = 50;
    private     BatchingSaverService<T>  service = null;
    
    
    public BatchDBSaver(int threadCount,int maxBatchSize,BatchingSaverService<T> service) {
        this.threadCount = threadCount;
        this.maxBatchSize = maxBatchSize;
        executor = Executors.newFixedThreadPool(this.threadCount);
        this.service = service;
    }
    
    public void add(T item) {
        synchronized(batch) {
            batch.add(item);
            if (batch.size()== maxBatchSize) {
                executor.submit(new DBSaverTask(batch));
                batch.clear();
            }
        }
    }
    
    public void finish() {
        executor.submit(new DBSaverTask(batch));
        batch.clear();
    }

    
     private class DBSaverTask implements Runnable {
         
        private Set<T> curBatch = null;
        
        public DBSaverTask(Set<T> data) {
            curBatch = new HashSet<T>(data);
        }
        
        @Override
        public void run() {
            if (curBatch.size()>0) {
                service.saveBatch(curBatch);
            }
        }
    }
}
