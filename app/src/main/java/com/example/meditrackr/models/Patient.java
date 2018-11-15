package com.example.meditrackr.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * this class creates a patient which stores all information associated with the patient.
 * the stored information is as follows: (id, username, email, phone, isCareProvider)
 * NOTE: isCareProvider should be false
 *
 * this class can use getCareProviders to show the patient all care providers that this patient has
 * this class can use deleteCareProvider to delete a care provider from their list
 * this class can use careProviderExists to show if that care provider is in their list of assigned care providers
 * this class can use  getProblems to see all problems associated with the patient
 * this class can also use getProblem which will give the details about a specific problem from a given index
 *
 * @author  Orest Cokan
 * @version 1.0 Oct 24, 2018.
 */

// A Patient class holding all information pertaining to Patient
public class Patient extends Profile implements Serializable{
    private CareProviderList careProviders = new CareProviderList();
    private ProblemList problems = new ProblemList();

    // Patient constructor
    public Patient(String id, String username, String email, String phone, boolean isCareProvider) {
        super(id, username, email, phone, isCareProvider);
    }

    // Getters/Setters
    public CareProviderList getCareProviders() {
        return careProviders;
    }

    public void deleteCareProvider(CareProvider careProvider) {
        careProviders.deleteCareProvider(careProvider);
    }
    public Boolean careProviderExists(CareProvider careProvider){
        return careProviders.careProviderExists(careProvider.getUsername());
    }

    public ProblemList getProblems() {return this.problems;}

    public Problem getProblem(int index){
        return problems.getProblem(index);
    }
}

