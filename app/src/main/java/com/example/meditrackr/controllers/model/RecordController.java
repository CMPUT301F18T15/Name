/*
 *    Apache 2.0 License Notice
 *
 *    Copyright 2018 CMPUT301F18T15
 *
 *Licensed under the Apache License, Version 2.0 (the "License");
 *you may not use this file except in compliance with the License.
 *You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *Unless required by applicable law or agreed to in writing, software
 *distributed under the License is distributed on an "AS IS" BASIS,
 *WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *See the License for the specific language governing permissions and
 *limitations under the License.
 *
 */

package com.example.meditrackr.controllers.model;

//imports
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.meditrackr.controllers.ElasticSearchController;
import com.example.meditrackr.controllers.SaveLoadController;
import com.example.meditrackr.controllers.ThreadSaveController;
import com.example.meditrackr.models.Patient;
import com.example.meditrackr.models.record.Record;
import com.example.meditrackr.controllers.LazyLoadingManager;

import es.dmoral.toasty.Toasty;

/**
 * RecordController:
 *
 * Allows for the adding and saving of
 * a record into both Elastic Search and
 * Memory
 *
 * @author  Veronica Salm
 * @version 1.0 Nov 10, 2018.
 */

// Controller class for record objects
public class RecordController {

    /**
     * adds problem to elastic search and locally
     *
     * @param context   the context the controller will user
     * @param record    the record we will add to the database
     * @param position  the position of the problem
     */
    public static void addRecord(Context context, Record record, int position) {
        // First, get the patient's profile
        Patient patient = LazyLoadingManager.getPatient();

        // Make sure all the images are properly added
        // to the patient's list for saving and restoring
        for (int i = 0; i < record.getImages().getSize(); ++i) {
            byte[] imageSave = record.getImages().getImage(i);
            patient.getProblem(position).getImageAll().addImage(imageSave);
        }

        // Add the record
        patient.getProblem(position).getRecords().addRecord(record);

        // Save in ElasticSearch and memory
        ThreadSaveController.save(context, patient);
        //ElasticSearchController.updateUser(patient);
        //SaveLoadController.saveProfile(context, patient);
        Log.d("RecordAdd", "Profile: " + patient.getUsername()
                + " Records: " + patient.getProblem(position).getRecords());

        // let the user know everything was successful
        Toasty.success(context, "Record successfully added", Toast.LENGTH_SHORT).show();

    }
}

