package com.wordpress.iibrahimov.weatherlogger.weatherdata;

import java.io.Serializable;

//A class to handle unused data (unrelated to weather) in case of app development
public class Miscellaneous implements Serializable{

    private float longitude;
    private float latitude;
    private String country;
    private String city;
    private long sunrise;
    private long sunset;

    /*
    Info on dt(Time of data calculation), id(city id) and cod(internal paramenter)
    was deemed irrelevant, thus is not saved for further development
     */

    public float getLongitude() {
        return  longitude;
    }
    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }
    public float getLatitude() {
        return latitude;
    }
    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public long getSunrise() {
        return sunrise;
    }
    public void setSunrise(long sunrise) {
        this.sunrise = sunrise;
    }
    public long getSunset() {
        return sunset;
    }
    public void setSunset(long sunset) {
        this.sunset = sunset;
    }
}
