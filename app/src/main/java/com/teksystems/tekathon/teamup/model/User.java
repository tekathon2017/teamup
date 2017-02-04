package com.teksystems.tekathon.teamup.model;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mayank Tiwari on 04/02/17.
 */

public class User implements Serializable {

    private String name;
    private String email;
    private long phoneNumber;
    private String address;
    private List<Tag> interests;
    private LatLng latLng;


    public User() {
    }

    public User(String name, List<Tag> interests) {
        this.name = name;
        this.interests = interests;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", interests=" + interests +
                '}';
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Tag> getInterests() {
        if(interests == null){
            interests = new ArrayList<>();
        }
        return interests;
    }

    public void setInterests(List<Tag> interests) {
        this.interests = interests;
    }
}