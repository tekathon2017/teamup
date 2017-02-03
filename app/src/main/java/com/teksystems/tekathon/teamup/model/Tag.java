package com.teksystems.tekathon.teamup.model;

import java.io.Serializable;

/**
 * Created by Mayank Tiwari on 04/02/17.
 */

public class Tag implements Serializable {

    private String name;

    public Tag() {
    }

    public Tag(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "name='" + name + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
