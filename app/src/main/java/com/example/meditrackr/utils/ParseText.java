package com.example.meditrackr.utils;

import android.util.Log;

import com.example.meditrackr.models.Patient;
import com.example.meditrackr.models.Problem;
import com.example.meditrackr.models.record.Geolocation;
import com.example.meditrackr.models.record.Record;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

/**
 * Crated by Skryt on Nov 29, 2018
 */

public class ParseText {

    // Parse records/problems only
    public static ArrayList<CustomFilter> parseRecordProblem(String query, Patient patient, ArrayList<CustomFilter> customFilter){
        String[] keywords = query.split(" ");

        for (String keyword : keywords) {
            for (int i = 0; i < patient.getProblems().getSize(); i++) {
                Problem problem = patient.getProblem(i);
                if (problem.getDescription().contains(keyword)
                        || problem.getTitle().contains(keyword)) {
                    CustomFilter filter = new CustomFilter(
                            patient.getUsername(),
                            false,
                            problem.getTitle(),
                            problem.getDescription(),
                            problem.getDate(),
                            i);
                    customFilter.add(filter);
                }

                for (int j = 0; j < problem.getRecords().getSize(); j++) {
                    Record record = problem.getRecord(j);
                    if (record.getDescription().contains(keyword)
                            || record.getTitle().contains(keyword)) {
                        CustomFilter filter = new CustomFilter(
                                patient.getUsername(),
                                true,
                                record.getTitle(),
                                record.getDescription(),
                                record.getDate(),
                                record.getGeoLocation(),
                                i,
                                j);
                        customFilter.add(filter);

                    }
                }
            }
        }
        return customFilter;
    }

    public static ArrayList<CustomFilter> parseGeolocation(String query, Patient patient, ArrayList<CustomFilter> customFilter, double latitude, double longtitude){
        String[] keywords = query.split(" ");
        for (String keyword : keywords) {
            for (int i = 0; i < patient.getProblems().getSize(); i++) {
                Problem problem = patient.getProblem(i);
                for (int j = 0; j < problem.getRecords().getSize(); j++) {
                    Record record = problem.getRecord(j);
                    if (record.getDescription().contains(keyword)
                            || record.getTitle().contains(keyword)) {
                        Geolocation geolocation = record.getGeoLocation();
                        String distance = distance(latitude,
                                geolocation.getLatitude(),
                                longtitude,
                                geolocation.getLongitude(),
                                0,
                                0);
                        Log.d("DISTANCEDANK", "distance is: " + distance);
                        geolocation.setDistance(distance);
                        CustomFilter filter = new CustomFilter(
                                patient.getUsername(),
                                true,
                                record.getTitle(),
                                record.getDescription(),
                                record.getDate(),
                                record.getGeoLocation(),
                                i,
                                j);
                        customFilter.add(filter);

                    }
                }
            }
        }
        // sort it by distance
        Collections.sort(customFilter, new Comparator<CustomFilter>() {
            @Override
            public int compare(CustomFilter o1, CustomFilter o2) {
                if(o1.getGeolocation().getDistance().compareTo(o2.getGeolocation().getDistance()) <= 0){
                    return -1;
                }
                return 0;
            }
        });

        return customFilter;
    }


    public static ArrayList<CustomFilter> parseBodylocation(){
        return null;
    }


    /**
     * Calculate distance between two points in latitude and longitude taking
     * into account height difference. If you are not interested in height
     * difference pass 0.0. Uses Haversine method as its base.
     *
     * lat1, lon1 Start point lat2, lon2 End point el1 Start altitude in meters
     * el2 End altitude in meters
     * @returns Distance in km
     */
    public static String distance(double lat1, double lat2, double lon1,
                                  double lon2, double el1, double el2) {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return new DecimalFormat("#.00km").format(Math.sqrt(distance)/1000);
    }
}