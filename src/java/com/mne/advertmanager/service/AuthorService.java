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

    private GenericDao<Author, Integer> authorDao;

    public void setAuthorDao(GenericDao<Author, Integer> authorDao) {
        this.authorDao = authorDao;
    }

//============================ findAllProducts =================================
    @Transactional(readOnly = true)
    public Collection<Author> findAllAuthors() {
        return authorDao.findByQuery("Author.findAll");
    }

    @Transactional
    public void createAuthor(Author author) {
        authorDao.create(author);
    }
    @Transactional(readOnly = true)
    public Author findById(int authorId) {
        return authorDao.read(authorId);
    }

    public Author createOrUpdate(Author author) {
        
        if (author.getId()==0) {
            authorDao.create(author);
        }else {
            authorDao.update(author);
        }
        return author;
    }
}
