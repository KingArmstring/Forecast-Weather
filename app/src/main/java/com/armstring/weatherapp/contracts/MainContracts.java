package com.armstring.weatherapp.contracts;


import android.content.Context;
import android.support.constraint.ConstraintLayout;

import com.armstring.weatherapp.bean.Forecast;
import com.armstring.weatherapp.callback.ForecastListAsyncResponse;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public interface MainContracts {

    public interface MainView{
        void applyData(ArrayList<Forecast> data);
        void getWeather(String currentLocation);
    }
    public interface MainPresenter{
        void getForecastArrayList(String location);
        String getImageUrl(String html);
    }
    public interface MainModel{
        void loopJsonArray(JSONObject itemObject, String currentTemp, String date, String city, String region, String desc);
        void getForecast(String locationText);
    }
}
