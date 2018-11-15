package com.example.meditrackr.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.meditrackr.R;
import com.example.meditrackr.controllers.ProfileManager;
import com.example.meditrackr.models.Profile;
import com.example.meditrackr.ui.patient.AddRecordFragment;


/**
 * this fragment shows the users username, phone number, email and an image of the patient if they added one
 * there is also a butto that will take the user to an edit profile fragment (UserEditFragment)
 * @author  Orest Cokan
 * @version 2.0 Nov 4, 2018.
 * @see UserEditFragment
 */

public class UserFragment extends Fragment {
    Profile profile = ProfileManager.getProfile();
    public static UserFragment newInstance(){
        UserFragment fragment = new UserFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_user, container, false);

        final Bundle bundle = getArguments();

        // set ui definitions
        ImageView user_image = rootView.findViewById(R.id.patient_image);
        TextView username = rootView.findViewById(R.id.patient_username);
        TextView email = rootView.findViewById(R.id.patient_email);
        TextView phone = rootView.findViewById(R.id.patient_phone);
        Button editButton = rootView.findViewById(R.id.edit_button);

        username.setText(profile.getUsername());
        email.setText(profile.getEmail());
        phone.setText(profile.getPhone());

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.addToBackStack(null);
                UserEditFragment fragment = UserEditFragment.newInstance().newInstance();
                transaction.replace(R.id.content, fragment);
                transaction.commit();
            }
        });








        return rootView;
    }

}