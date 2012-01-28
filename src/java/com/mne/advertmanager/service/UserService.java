/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.advertmanager.service;

import com.mne.advertmanager.dao.GenericDao;
import com.mne.advertmanager.model.User;
import java.util.Collection;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Nina Eidelshtein and Misha Lebedev
 */
public class UserService {
 
    private GenericDao<User,String> userDao;

    public void setUserDao(GenericDao<User,String> userDao) {
        this.userDao = userDao;
    }
    @Transactional(readOnly = true)
    public Collection<User> findAllUsers() {
        return userDao.findByQuery("User.findAll");
    }

    @Transactional
    public String createUser(User user) {
        return userDao.create(user);
    }
}
