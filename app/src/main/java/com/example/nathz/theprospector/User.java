package com.example.nathz.theprospector;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class User extends Application implements Serializable{
    private static Context mContext;
    public static int idCounter=0;
    public static ArrayList<User> usersArrayList = new ArrayList<>();
    public String region;
    public String firstName;
    public String lastName;
    public String primaryContactPreference;
    public String secondaryContactPreference;
    public String primContactData;
    public String secContactData;

    public String getRecommendation() {
        return recommendation;
    }

    public String recommendation;
    public int interestLevel, id, timesContacted;
    static ArrayList<String> basicProspectInfo=new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }
    public static Context getContext() {
        return mContext;
    }

    public User(String region, String firstName, String lastName, int interestLevel, String primaryContactpreference, String primContactData, String secondaryContactPreference, String secContactData) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.region = region;
        this.id = ++idCounter;
        this.interestLevel = interestLevel;
        this.primaryContactPreference = primaryContactpreference;
        this.primContactData = primContactData;
        this.secondaryContactPreference = secondaryContactPreference;
        this.timesContacted = 0;
        this.recommendation = String.valueOf(R.string.contact_soon);
        if (secContactData.length()<3){
            this.secContactData = "-";
        }else
            this.secContactData = secContactData;
    }

    public void contactUser(){
        this.timesContacted++;
    }
    public void setRecommendation(String r){
        this.recommendation=r;
    }
    public String toString() {
        return "ID#:" + id + " " + firstName + " " + lastName + " (" + region + ")";
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof User)) return false;
        User o = (User) obj;
        return o.id == this.id;
    }

}
