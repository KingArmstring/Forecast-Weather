package com.armstring.weatherapp.utils;

import android.app.Activity;
import android.content.SharedPreferences;

/**
 * Created by Darkwood on 1/12/2018.
 */

public class Preferences {
    private SharedPreferences preferences;
    public static final String LOCATION_KEY = "LOCATION_KEY";
    public Preferences(Activity activity) {
        preferences = activity.getPreferences(Activity.MODE_PRIVATE);
    }

    public void setLocation(String location){
        this.preferences.edit().putString(LOCATION_KEY, location).apply();

    }
    public String getLocation(){
        return this.preferences.getString(LOCATION_KEY, "Alexandria eg");
    }
}