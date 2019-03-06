package com.wordpress.iibrahimov.weatherlogger;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.MalformedURLException;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.IOException;

/*
 APIHandler class handles:
 i)  connection to and;
 ii) requesting and reading data from
     OpenWeatherMap API
 */
public class APIHandler {

    private String url = "http://api.openweathermap.org/data/2.5/weather?";

    //GET your own personal APPID from OpenWeatherMap and EDIT this field
    private String APPID = "21acc258e3d02d98da8a138835641680";

    /*
    WeatherLogger App DOES use cleartext network traffic(cleartext HTTP)
    Thus "android:usesCleartextTraffic="true"" is added to manifest file
    to ignore java.io.IOException: Cleartext HTTP traffic to ... not permitted
     */

    public String getWeatherData(String coordinates) {

        url = url + coordinates + "&units=metric" + "&APPID=" + APPID;

        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            /*
            By default request method is GET
            doInput is true(WeatherLogger reads data from url)
            doOutput is false(WeatherLogger doesn't write to url)
             */
            //Connection
            connection = (HttpURLConnection) (new URL(url)).openConnection();

            //Reading response
            InputStream inputStream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuffer buffer = new StringBuffer();
            String line = null;

            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\r\n");
            }

            return buffer.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
