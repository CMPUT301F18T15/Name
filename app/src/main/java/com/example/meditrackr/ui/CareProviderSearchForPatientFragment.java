package com.example.meditrackr.ui;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meditrackr.R;
import com.example.meditrackr.controllers.ElasticSearchController;
import com.example.meditrackr.models.Patient;
import com.example.meditrackr.models.Profile;

/**
 * Created by Skryt on Nov 08, 2018
 */

public class CareProviderSearchForPatientFragment extends Fragment {
    private Profile profile;
    private ConstraintLayout searchLayout;
    private ConstraintLayout searchDisplayPatient;

    public static CareProviderSearchForPatientFragment newInstance(){
        CareProviderSearchForPatientFragment fragment = new CareProviderSearchForPatientFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_add_patient, container, false);


        searchLayout = (ConstraintLayout) rootView.findViewById(R.id.search_constraint);
        searchDisplayPatient = (ConstraintLayout) rootView.findViewById(R.id.search_display_patient);

        final EditText searchPatient = (EditText) rootView.findViewById(R.id.search_patient);
        final Button searchPatientButton = (Button) rootView.findViewById(R.id.careprovider_search_for_patient_button);

        final ImageView patientProfileImage = (ImageView) rootView.findViewById(R.id.patient_image);
        final TextView patientUsername = (TextView) rootView.findViewById(R.id.search_username);
        final TextView patientEmail = (TextView) rootView.findViewById(R.id.search_email);
        final TextView patientPhone = (TextView) rootView.findViewById(R.id.search_phone);
        final Button addPatientButton = (Button) rootView.findViewById(R.id.search_add_patient_button);



        changeViewVisibility(1);

        // search for patient
        searchPatientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = searchPatient.getText().toString();
                profile = ElasticSearchController.searchProfile(username);
                if(profile != null) {
                    Patient patient = (Patient) profile;
                    patientUsername.setText(patient.getUsername());
                    patientEmail.setText(patient.getEmail());
                    patientPhone.setText(patient.getPhone());
                    changeViewVisibility(0);
                }
                else {
                    Toast.makeText(getContext(), "User not found!", Toast.LENGTH_LONG).show();

                }


            }
        });








        return rootView;
    }

    // 1 for search mode layout, any other value set to Add Patient
    public void changeViewVisibility(int value){
        if(value == 1){
            searchLayout.setVisibility(View.VISIBLE);
            searchDisplayPatient.setVisibility(View.INVISIBLE);
        }
        else {
            searchLayout.setVisibility(View.INVISIBLE);
            searchDisplayPatient.setVisibility(View.VISIBLE);
        }
    }
}
