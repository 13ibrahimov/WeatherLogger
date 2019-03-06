package com.wordpress.iibrahimov.weatherlogger;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

import com.wordpress.iibrahimov.weatherlogger.weatherdata.Miscellaneous;
import com.wordpress.iibrahimov.weatherlogger.weatherdata.Weather;

public class WeatherDataParser {

    public static Weather getWeather(String apiData) throws JSONException {

        //Miscellaneous and Weather instances to store API data
        Miscellaneous misc = new Miscellaneous();
        Weather weather = new Weather();

        //JSON object to be created from returned API data
        //is then used to fill Miscellaneous and Weather class parameters
        // (i.e weather conditions and other data to be used or saved)
        JSONObject jObj = new JSONObject(apiData);

        JSONObject coord = getObject("coord", jObj);
        misc.setLongitude(getFloat("lat", coord));
        misc.setLatitude(getFloat("lon", coord));

        JSONObject sys = getObject("sys", jObj);
        misc.setCountry(getString("country", sys));
        misc.setCity(getString("name", jObj));
        misc.setSunrise(getInt("sunrise", sys));
        misc.setSunset(getInt("sunset", sys));

        //Miscellaneous class parameters are filled
        //Update Miscellaneous instance of Weather class
        weather.miscellaneous = misc;

        //Now filling remaining parameters of Weather class

        //API returns "weather" parameter as an array
        JSONArray jArray = jObj.getJSONArray("weather");
        //All relevant info is stored in the fist value of array
        JSONObject weatherObj = jArray.getJSONObject(0);
        weather.weatherInfo.setId(getInt("id", weatherObj));
        weather.weatherInfo.setDescr(getString("description", weatherObj));
        weather.weatherInfo.setStatus(getString("main", weatherObj));

        JSONObject temp = getObject("main", jObj);
        weather.temperature.setTemp(getFloat("temp", temp));
        weather.temperature.setPressure(getInt("pressure", temp));
        weather.temperature.setHumidity(getInt("humidity", temp));
        weather.temperature.setMinTemp(getFloat("temp_min", temp));
        weather.temperature.setMaxTemp(getFloat("temp_max", temp));

        JSONObject wind = getObject("wind", jObj);
        weather.wind.setSpeed(getFloat("speed", wind));
        weather.wind.setDegree(getFloat("deg", wind));

        JSONObject clouds = getObject("clouds", jObj);
        weather.clouds.setPercentage(getInt("all", clouds));

        weather.setVisibility(getInt("visibility", jObj));

        return weather;
    }

    /*
    Private methods to account for possible JSONException
    when reading from JSON file returned by API
     */
    private static JSONObject getObject(String param, JSONObject jObj)  throws JSONException {
        return jObj.getJSONObject(param);
    }

    private static String getString(String param, JSONObject jObj) throws JSONException {
        return jObj.getString(param);
    }

    private static float  getFloat(String param, JSONObject jObj) throws JSONException {
        return (float) jObj.getDouble(param);
    }

    private static int  getInt(String param, JSONObject jObj) throws JSONException {
        return jObj.getInt(param);
    }

}
