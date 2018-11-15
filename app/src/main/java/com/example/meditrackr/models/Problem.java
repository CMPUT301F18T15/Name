package com.example.meditrackr.models;

import com.example.meditrackr.models.record.RecordList;

import java.io.Serializable;

/**
 * this class creates a Problem which gets and stores all the problems title, date, description and records.
 * this class uses getters and setter to get and set all the variables associated with that variable
 *
 * @author  Orest Cokan
 * @version 1.0 Oct 24, 2018.
 */


// Profile that contains all information about Problem
public class Problem implements Serializable {
    private String title;
    private String date;
    private String description;
    private RecordList records = new RecordList();

    // Constructor
    public Problem(String title, String date, String description){
        this.title = title;
        this.date = date;
        this.description = description;
    }

    // Getters/Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RecordList getRecords() {
        return records;
    }

}
