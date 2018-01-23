package com.armstring.weatherapp.ui.MainView;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.armstring.weatherapp.bean.Forecast;
import com.armstring.weatherapp.callback.ForecastListAsyncResponse;
import com.armstring.weatherapp.contracts.MainContracts;
import com.armstring.weatherapp.model.ForecastData;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainPresenter implements MainContracts.MainPresenter, ForecastListAsyncResponse {
    public static final String TAG = "Armstring";
    private MainContracts.MainView view;
    private MainContracts.MainModel model;
    MainPresenter(MainActivity view) {
        model = new ForecastData(this);
        this.view = view;
    }

    @Override
    public String getImageUrl(String html) {
        String imgRegex = "(?i)<img[^>]+?src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>";
        String imgSource = null;


        Pattern p = Pattern.compile(imgRegex);
        Matcher m = p.matcher(html);
        while (m.find()){
            imgSource = m.group(1);
        }
        return imgSource;
    }

    @Override
    public void getForecastArrayList(String location) {
        model.getForecast(location);
    }


    @Override
    public void processFinished(ArrayList<Forecast> forecastArrayList) {
        view.applyData(forecastArrayList);
    }
}
