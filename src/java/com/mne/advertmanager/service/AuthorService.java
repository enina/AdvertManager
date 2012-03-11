/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.advertmanager.service;

import com.mne.advertmanager.dao.GenericDao;
import com.mne.advertmanager.model.Author;
import java.util.Collection;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Nina Eidelshtein and Misha Lebedev
 */
public class AuthorService {

    private GenericDao<Author, Long> authorDao;

    public void setAuthorDao(GenericDao<Author, Long> authorDao) {
        this.authorDao = authorDao;
    }

//============================ findAllProducts =================================
    @Transactional(readOnly = true)//, propagation = Propagation.REQUIRED)
    public Collection<Author> findAllAuthors() {
        return authorDao.findByQuery("Author.findAll");
    }

    @Transactional
    public void createAuthor(Author author) {
        authorDao.create(author);
    }
}
