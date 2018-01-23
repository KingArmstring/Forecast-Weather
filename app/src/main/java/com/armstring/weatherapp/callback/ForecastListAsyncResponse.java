package com.armstring.weatherapp.callback;

import com.armstring.weatherapp.bean.Forecast;

import java.util.ArrayList;


public interface ForecastListAsyncResponse {
    void processFinished(ArrayList<Forecast> forecastArrayList);
}
