package com.example.meditrackr.ui;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meditrackr.controllers.ProfileManager;
import com.example.meditrackr.controllers.ElasticSearchController;
import com.example.meditrackr.controllers.SaveLoadController;
import com.example.meditrackr.models.CareProvider;
import com.example.meditrackr.models.Patient;
import com.example.meditrackr.R;

/**
 * this fragment lets a user create a profile
 * the main parts to this fragment is a text box where user can input a username for their account
 * if the username is already in use or invalid there will be a toast message letting the user try again
 *
 * there is a spot for the user to enter their phone number if phone number is invalid a toast message
 * will prompt the user to try again
 *
 * there is a spot for the user to enter their email if the email is invalid a toast message
 * will prompt the user to try again
 *
 * there are also two button for the user to indicate if they are a patient or care provider.
 * if user doesn't choose one a toast message will appear to tell user to pick one
 *
 * lastly there is an login button which a user must press for all of the data they entered to
 * be put into the database. if all entry's are good then user will be taken to the main page (MainActivity)
 *
 * there is also a already_member button which will take the user to the login page page (RegisterFragment)
 *
 * @author  Orest Cokan
 * @version 2.0 Nov 13, 2018.
 * @see MainActivity
 * @see LoginFragment
 */


public class RegisterFragment extends Fragment {
    public boolean isCareProvider;

    public static RegisterFragment newInstance(){
        RegisterFragment fragment = new RegisterFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_signup, container, false);

        // ui definitions
        final EditText username = (EditText) rootView.findViewById(R.id.patient_username);
        final EditText email = (EditText) rootView.findViewById(R.id.patient_phone);
        final EditText phoneNumber = (EditText) rootView.findViewById(R.id.phone_number);
        final TextView careProviderTitle = (TextView) rootView.findViewById(R.id.display_careprovider);
        final TextView patientTitle = (TextView) rootView.findViewById(R.id.display_patient);
        final ImageView doctorImage = (ImageView) rootView.findViewById(R.id.CareProvider);
        final ImageView patientImage = (ImageView) rootView.findViewById(R.id.Patient);
        final Button createAccount = (Button) rootView.findViewById(R.id.signup_button);
        final TextView alreadyMember = (TextView) rootView.findViewById(R.id.already_member);


        // onclick listener for create account
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkInputs(username, email, phoneNumber, doctorImage, patientImage)){
                    Bundle bundle = new Bundle();
                    boolean done;
                    boolean finish;
                    if(doctorImage.isSelected()){
                        CareProvider careProvider = new CareProvider(
                                username.getText().toString().trim(),
                                email.getText().toString().trim(),
                                phoneNumber.getText().toString().trim(),
                                isCareProvider
                        );
                        done = ElasticSearchController.addProfile(careProvider);
                        finish = SaveLoadController.addNewProfile(getContext(),careProvider);
                        Log.d("Success", "elastic returned: " + done + " saveload returned " + finish);
                        if(finish || done){
                            bundle.putSerializable("CareProvider", careProvider);
                            Log.d("AddProfile", "Username: " + careProvider.getUsername() + " IsCareProvider: " + careProvider.getisCareProvider());
                            ProfileManager.setProfile(careProvider);
                        }

                    }
                    else {
                        Patient patient = new Patient(
                                username.getText().toString().trim(),
                                email.getText().toString().trim(),
                                phoneNumber.getText().toString().trim(),
                                isCareProvider
                        );
                        finish = SaveLoadController.addNewProfile(getContext(), patient);
                        done = ElasticSearchController.addProfile(patient);
                        Log.d("Success", "elastic returned: " + done + " saveload returned " + finish);
                        if(finish || done) {
                            bundle.putSerializable("Patient", patient);
                            Log.d("Success", "Username: " + patient.getUsername() + " isCareProvider: " + patient.getisCareProvider());
                            ProfileManager.setProfile(patient);
                        }
                    }
                    if(!finish || !done)
                        Toast.makeText(getContext(), "Duplicated UserName", Toast.LENGTH_SHORT).show();
                    else{
                        Toast.makeText(getContext(), "Success to Sign Up", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                }
            }
        });


        // onclick listener for login button
        alreadyMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.addToBackStack(null);
                LoginFragment fragment = LoginFragment.newInstance();
                transaction.replace(R.id.content, fragment);
                transaction.commit();
            }
        });

        // onclick listener for doctor selected, does some UI stuff as well
        doctorImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                doctorImage.setSelected(true);
                isCareProvider = true;
                careProviderTitle.setTypeface(careProviderTitle.getTypeface(), Typeface.BOLD);
                patientImage.setSelected(false);
                patientTitle.setTypeface(null, Typeface.NORMAL);

            }
        });

        // onclick listener for patient select, does some UI stuff as well
        patientImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                patientImage.setSelected(true);
                isCareProvider = false;
                patientTitle.setTypeface(patientTitle.getTypeface(), Typeface.BOLD);
                doctorImage.setSelected(false);
                careProviderTitle.setTypeface(null, Typeface.NORMAL);

            }
        });

        return rootView;
    }



    public boolean checkInputs(EditText username, EditText email, EditText phoneNumber, ImageView doctorImage, ImageView patientImage) {
        if(username.getText().toString().trim().length() < 8){
            Toast.makeText(getActivity(), "You messed up kiddo, change username", Toast.LENGTH_LONG).show();
            username.getText().clear();
            return false;
        } else if (email.getText().toString().trim().isEmpty()) {
            Toast.makeText(getActivity(), "You messed up kiddo, change email", Toast.LENGTH_LONG).show();
            email.getText().clear();
            return false;
        } else if (phoneNumber.getText().toString().trim().isEmpty()) {
            Toast.makeText(getActivity(), "You messed up kiddo, change phone number", Toast.LENGTH_LONG).show();
            phoneNumber.getText().clear();
            return false;
        } else if (!doctorImage.isSelected() && !patientImage.isSelected()){
            Toast.makeText(getActivity(), "You messed up kiddo, choose either doctor or patient", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}


