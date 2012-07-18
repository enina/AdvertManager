/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.advertmanager.util;

import java.util.Set;

/**
 *
 * @author ilyae
 */
public interface BatchingSaverService<T> {
    void saveBatch(Set<T> batch);
}
