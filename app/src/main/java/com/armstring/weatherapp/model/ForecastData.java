package com.armstring.weatherapp.model;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.armstring.weatherapp.callback.ForecastListAsyncResponse;
import com.armstring.weatherapp.application.AppController;
import com.armstring.weatherapp.bean.Forecast;
import com.armstring.weatherapp.contracts.MainContracts;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class ForecastData implements MainContracts.MainModel{
    public static final String TAG = "Armstring";
    private ArrayList<Forecast> forecastArrayList;
    private ForecastListAsyncResponse callback;
    public ForecastData(ForecastListAsyncResponse callback) {//needed for initialization.
        forecastArrayList = new ArrayList<>();
        this.callback = callback;
    }

    public void getForecast(String locationText){
        forecastArrayList.clear();
        String urlLeft = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22";
        String urlRight = "%22)&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";
        String url = urlLeft + locationText + urlRight;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String city;
                        String country;
                        String date;
                        String currentTemp;
                        String desc;
                        try {
                            //start from here.
                            JSONObject query = response.getJSONObject("query");
                            JSONObject result = query.getJSONObject("results");
                            JSONObject channel = result.getJSONObject("channel");
                            JSONObject location = channel.getJSONObject("location");
                            JSONObject item = channel.getJSONObject("item");
                            JSONObject condition = item.getJSONObject("condition");
                            city = location.getString("city");
                            country = location.getString("country");
                            date = condition.getString("date");
                            currentTemp = convertFehToCel(condition.getString("temp"));
                            desc = item.getString("description");
                            Forecast newForecast = new Forecast();
                            loopJsonArray(item, currentTemp, date, city, country, desc);
                            callback.processFinished(forecastArrayList);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }
        );
        AppController.getInstance().addToRequestQueue(jsonObjectRequest);
    }

    public void loopJsonArray(JSONObject itemObject, String currentTemp, String date, String city, String region, String desc){
        try {
            JSONArray jsonArray = itemObject.getJSONArray("forecast");
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Forecast forecast = new Forecast();
                forecast.setForecastDate(jsonObject.getString("date"));
                forecast.setForecastDay(jsonObject.getString("day"));
                forecast.setForecastHighTemp("High: " + convertFehToCel(jsonObject.getString("high")) + "C");
                forecast.setForecastLowTemp("Low: " + convertFehToCel(jsonObject.getString("low")) + "C");
                forecast.setWeatherDescription(jsonObject.getString("text"));
                forecast.setCurrentTemp(currentTemp);
                forecast.setDate(date);
                forecast.setCity(city);
                forecast.setRegion(region);
                forecast.setHtmlDescription(desc);
                forecastArrayList.add(forecast);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static String convertFehToCel(String feh){
        String cel = "";
        Integer f = Integer.valueOf(feh);
        Double c = (double) (f - 32) * (5.0/9.0);
        cel = String.valueOf(c.intValue());
        return cel;
    }
}
