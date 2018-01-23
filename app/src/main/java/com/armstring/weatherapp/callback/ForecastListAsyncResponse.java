package com.armstring.weatherapp.callback;

import com.armstring.weatherapp.bean.Forecast;

import java.util.ArrayList;

/**
 * Created by Darkwood on 1/11/2018.
 */

public interface ForecastListAsyncResponse {
    void processFinished(ArrayList<Forecast> forecastArrayList);
    void setTheAdapter();
}
