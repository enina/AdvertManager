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
    
    @Column(name = "rating")
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

    public AffProgram getAffProgram() {
        return affProgram;
    }

    public int getId() {
        return id;
    }

    public String getQuery() {
        return query;
    }

    public long getRating() {
        return rating;
    }
    
    
    
}
