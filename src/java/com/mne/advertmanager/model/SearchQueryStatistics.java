/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.advertmanager.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 *
 * @author Misha
 */
@Entity
@Table(name = "search_query_stat")

//@NamedQueries({
//    @NamedQuery(name = "AccessLog.findAll", query = "SELECT a FROM AccessLog a order by a.accessTime"),
//    @NamedQuery(name = "AccessLog.countAccessLog", query = "SELECT count (*) FROM AccessLog a "),
//    @NamedQuery(name = "AccessLog.findById", query = "SELECT a FROM AccessLog a WHERE a.id = ?"),
//    @NamedQuery(name = "AccessLog.findByAccessTime", query = "SELECT a FROM AccessLog a WHERE a.accessTime = ?"),
//    @NamedQuery(name = "AccessLog.findByIpAddress", query = "SELECT a FROM AccessLog a WHERE a.ipAddress = ?"),
//    @NamedQuery(name = "AccessLog.findByCountry", query = "SELECT a FROM AccessLog a WHERE a.countryName = ?"),
//    @NamedQuery(name = "AccessLog.findByAffProgramId", query = "SELECT a FROM AccessLog a left join fetch a.sourceDomain WHERE a.affProgram.id = ? "),
//    @NamedQuery(name = "AccessLog.countAffProgramAccessLog", query = "SELECT count (*) FROM AccessLog a WHERE a.affProgram.id = ?"),
//    @NamedQuery(name = "AccessLog.countAffProgramAccessByDate", query = "SELECT count (*) FROM AccessLog a WHERE a.affProgram.id = ? and accessTime > ?"),
//    @NamedQuery(name = "AccessLog.findByUrl", query = "SELECT a FROM AccessLog a WHERE a.targetURL = ?")
//})
//find best 10 querys by program id
//
//select count(*) as accessAmount,access_log.query as query,access_log.affprogram_id as affprogram_id
//    from 
//     access_log 
//    where
//    access_log.affprogram_id = 2 and
//    access_log.query is not null 
//
//    and   CHAR_LENGTH(access_log.query) > 4
//    group by 
//    access_log.query 
//    order by accessAmount desc 
@NamedQueries({
    @NamedQuery(name = "SearchQueryStatistics.calcStatistics", 
        query="SELECT "
        + " new com.mne.advertmanager.model.SearchQueryStatistics (count (acLog),acLog.query, acLog.affProgram)"
        + " FROM AccessLog acLog WHERE"
        + " acLog.affProgram = ? and"
        + " acLog.query is not null and"
        + " length(acLog.query) >4"
        + " group by acLog.query"
        ),
    @NamedQuery(name = "SearchQueryStatistics.findAll", query= "SELECT p FROM SearchQueryStatistics p WHERE p.affProgram = ? order by p.rating desc"),
    @NamedQuery(name = "SearchQueryStatistics.deleteByAffProgram", query= "DELETE SearchQueryStatistics p WHERE p.affProgram = ? ")
})
public class SearchQueryStatistics implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private int id;
    
    @JoinColumn(name = "affprogram_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private AffProgram affProgram;
    
    @Size(max = 255)
    @Column(name = "query")
    private String query;
    
    @Column(name = "reting")
    private long rating;

    //C-tor
    public SearchQueryStatistics() {
    }

    //C-tor

//===================== Setters ================================================

    public SearchQueryStatistics(long rating, String query, AffProgram affProgram ) {
        this.affProgram = affProgram;
        this.query = query;
        this.rating = rating;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
    
    
    
}
