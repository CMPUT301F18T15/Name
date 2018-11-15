package com.example.meditrackr.models.record;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

/**
 * This class will create a Record.
 * a record will store the following: an image, a Date, a description, a title, and a body location.
 *
 * to store all of those it uses a getter function for that data type
 * and then use a setter function related to that data type as well which will set the variable as
 * that value that the getter function retrieved
 *
 * @author  Orest Cokan
 * @version 1.0 Oct 24, 2018.
 */

// A basic record class that holds all information pertaining to record
public class Record {
    private Bitmap[] images;
    private String date;
    private String description;
    private String title;
    private BodyLocation bodyLocation;
    // geolocation is an array of LONGITUDE, LATITUDE in degrees
    private Geolocation geoLocation;

    // Constructor
    public Record(String title, String description, @NonNull String date, Bitmap[] images, BodyLocation bodylocation, Geolocation geoLocation) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.images = images;
        this.bodyLocation = bodylocation;
        this.geoLocation = geoLocation;
    }

    // Getters/Setters
    public Bitmap[] getImages() {
        return images;
    }

    public void setImages(Bitmap[] images) {
        this.images = images;
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
        this.description= description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BodyLocation getBodyLocation() {
        return bodyLocation;
    }

    public void setBodyLocation(BodyLocation bodyLocation) {
        this.bodyLocation = bodyLocation;
    }

    public Geolocation getGeoLocation() {
        return geoLocation;
    }

    public void setGeoLocation(Geolocation geoLocation) {
        this.geoLocation = geoLocation;
    }



}
