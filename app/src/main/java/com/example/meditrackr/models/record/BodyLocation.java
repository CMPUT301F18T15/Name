/*--------------------------------------------------------------------------
 * FILE: BodyLocation.java
 *
 * PURPOSE: Holds all body location information stored about a record,
 *          including the photo name, photoID, and position on the
 *          photo.
 *
 *     Apache 2.0 License Notice
 *
 * Copyright 2018 CMPUT301F18T15
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 --------------------------------------------------------------------------*/

package com.example.meditrackr.models.record;

import java.io.Serializable;

/**
 * This class will create a Body coordinate on our model and then associate it with with a body location
 * it uses getters and setters to get the body coordinate
 * it also uses getters and setters to get the bodyFace
 *
 *
 * @author  Orest Cokan
 * @version 1.0 Oct 24, 2018.
 */

// A BodyLocation class that holds all information pertaining to BodyLocation
public class BodyLocation implements Serializable {

    // Initialize class variables
    private float x;
    private float y;
    private String bodyLocationPhotoName;
    private byte[] image;
    private String photoID;

    // Constructor
    public BodyLocation(String bodyLocationPhotoName, byte[] image, String photoID){
        this.bodyLocationPhotoName = bodyLocationPhotoName;
        this.image = image;
        this.photoID = photoID;
    }

    // Constructor
    public BodyLocation(String bodyLocationPhotoName, byte[] image, String photoID, float x, float y){
        this.bodyLocationPhotoName = bodyLocationPhotoName;
        this.image = image;
        this.photoID = photoID;
        this.x = x;
        this.y = y;
    }





    // Getters/Setters
    public float getXCoordinate() {
        return x;
    }

    public void setXCoordinate(int x) { this.x = x; }

    public float getYCoordinate() {
        return y;
    }

    public void setYCoordinate(int y) { this.y = y; }

    public String getName() {
        return bodyLocationPhotoName;
    }

    public void setName(String bodyLocationPhotoName) {
        this.bodyLocationPhotoName = bodyLocationPhotoName;
    }

    public void setID(String id) { photoID = id; }

    public String getID() { return photoID; }


    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }


}

