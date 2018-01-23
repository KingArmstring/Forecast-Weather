package com.armstring.weatherapp.bean;

import java.io.Serializable;

/**
 * Created by Darkwood on 1/10/2018.
 */

public class Forecast implements Serializable {
    private String city;
    private String country;
    private String region;

    private String date;
    private String currentTemp;
    private String currentWeatherTemp;

    private String forecastDate;
    private String forecastDay;
    private String forecastHighTemp;
    private String forecastLowTemp;
    private String forecastWaltherTemp;
    private String htmlDescription;
    private String weatherDescription;

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getRegion() {
        return region;
    }

    public String getDate() {
        return date;
    }

    public String getCurrentTemp() {
        return currentTemp;
    }

    public String getCurrentWeatherTemp() {
        return currentWeatherTemp;
    }

    public String getForecastDate() {
        return forecastDate;
    }

    public String getForecastDay() {
        return forecastDay;
    }

    public String getForecastHighTemp() {
        return forecastHighTemp;
    }

    public String getForecastLowTemp() {
        return forecastLowTemp;
    }

    public String getForecastWaltherTemp() {
        return forecastWaltherTemp;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public String getHtmlDescription() {
        return htmlDescription;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setCurrentTemp(String currentTemp) {
        this.currentTemp = currentTemp;
    }

    public void setCurrentWeatherTemp(String currentWeatherTemp) {
        this.currentWeatherTemp = currentWeatherTemp;
    }

    public void setForecastDate(String forecastDate) {
        this.forecastDate = forecastDate;
    }

    public void setForecastDay(String forecastDay) {
        this.forecastDay = forecastDay;
    }

    public void setForecastHighTemp(String forecastHighTemp) {
        this.forecastHighTemp = forecastHighTemp;
    }

    public void setForecastLowTemp(String forecastLowTemp) {
        this.forecastLowTemp = forecastLowTemp;
    }

    public void setForecastWaltherTemp(String forecastWaltherTemp) {
        this.forecastWaltherTemp = forecastWaltherTemp;
    }

    public void setWeatherDescription(String weatherDescription) {
        this.weatherDescription = weatherDescription;
    }

    public void setHtmlDescription(String htmlDescription) {
        this.htmlDescription = htmlDescription;
    }
}
