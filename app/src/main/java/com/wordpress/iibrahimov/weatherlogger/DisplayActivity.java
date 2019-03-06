package com.wordpress.iibrahimov.weatherlogger;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Locale;

import com.wordpress.iibrahimov.weatherlogger.weatherdata.Weather;

public class DisplayActivity extends AppCompatActivity {

    private TextView coordinates;
    private TextView temperature;
    private TextView date;

    protected String saveDate;
    protected double latitude,longitude;

    /*
    Used to pass additional info returned from API to
    MoreDetailsActivity in case of More Details button click
     */
    Bundle detailExtras = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        coordinates = (TextView) findViewById(R.id.textViewCoord);
        temperature = (TextView) findViewById(R.id.tempCelsius);
        date = (TextView) findViewById(R.id.dateNum);

        //Receiving Date, Time and current Coordinates from MainActivity
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            saveDate = extras.getString("DATE");
            latitude = extras.getDouble("LATITUDE");
            longitude = extras.getDouble("LONGITUDE");

            //User's coordinates in the required form to be passed to API
            String apiCoord = "lat=" + latitude + "&" + "lon=" + longitude;

            //Async Task call goes here
            new WeatherDataTask(this).execute(apiCoord);
            //new WeatherDataTask(this).execute(new String[]{apiCoord});

        }

    }

    private static class WeatherDataTask extends AsyncTask<String, Void, Weather> {

        /*
        To prevent memory leaks from AsyncTask, it is made static
        and a weak reference to the activity is retained(collectible by garbage collector)
        for accessing UI views and member variables
         */
        private WeakReference<DisplayActivity> activityReference;

        WeatherDataTask(DisplayActivity context) {
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected Weather doInBackground(String... params) {

            //Create a new Weather object
            Weather weather = new Weather();
            //Retrieve data from API using APIHandler
            //params[0] is the coordinates which is passed into AsyncTask call
            String data = new APIHandler().getWeatherData(params[0]);

            //Parse the data returned from API using WeatherDataParser class
            //All the relevant information is saved in Weather class object weather
            try {
                weather = WeatherDataParser.getWeather(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //Returns Weather class object filled with info returned from API
            return weather;
        }

        @Override
        protected void onPostExecute(Weather weather) {
            super.onPostExecute(weather);

            //If activity is still live, get a reference to it
            DisplayActivity activity = activityReference.get();
            if (activity == null || activity.isFinishing()) return;

            //Display relevant information on screen
            //\u2103 stands for celsius
            //temp is displayed as int not double for better user interaction
            activity.temperature.setText(String.valueOf((int)weather.temperature.getTemp() + " \u2103"));
            activity.date.setText(activity.saveDate);

            //Get coordinates from lat and long and display them on screen
            //latitude and longitude values are rounded to 2 decimal places for better user interaction
            String lat = String.format(Locale.US, "%.2f", activity.latitude);
            String lon = String.format(Locale.US, "%.2f", activity.longitude);
            String latlong = lat + "," + lon;
            activity.coordinates.setText(latlong);

            /*
            All relevant information returned by API which will be passed into MoreDetailsActivity
            is added to Bundle
             */
            activity.detailExtras.putString("CITY", weather.miscellaneous.getCity());
            activity.detailExtras.putString("COUNTRY", weather.miscellaneous.getCountry());
            activity.detailExtras.putString("CONDITION", weather.weatherInfo.getDescription());
            activity.detailExtras.putFloat("TEMPERATURE", weather.temperature.getTemp());
            activity.detailExtras.putFloat("PRESSURE", weather.temperature.getPressure());
            activity.detailExtras.putFloat("HUMIDITY", weather.temperature.getHumidity());
            activity.detailExtras.putFloat("WIND_SPEED", weather.wind.getSpeed());
            activity.detailExtras.putFloat("WIND_DEGREE", weather.wind.getDegree());
            activity.detailExtras.putInt("CLOUDINESS", weather.clouds.getPercentage());
            activity.detailExtras.putInt("VISIBILITY", weather.getVisibility()/1000);
            activity.detailExtras.putLong("SUNRISE", weather.miscellaneous.getSunrise());
            activity.detailExtras.putLong("SUNSET", weather.miscellaneous.getSunset());


            //SAVE FILE HANDLING START

            FileHandler fileHandler = new FileHandler();
            //If external storage is available for read and write
            if (fileHandler.isExternalStorageWritable()) {
                //Create a dir inside Downloads dir named WeatherLogger
                //And get that folder
                File saveDir = fileHandler.getDownloadsDir("WeatherLogger");

                //Logs.txt file
                File logfile = new File(saveDir, "Logs.txt");
                //FileWriter writer;

                try {
                    if (!fileHandler.doesFileExist("/WeatherLogger/Logs.txt")) {
                        //Creating Logs.txt
                        logfile.createNewFile();
                    }
                    FileWriter writer = new FileWriter(logfile, true);
                    String logText = activity.saveDate + " -> "
                            + (int)weather.temperature.getTemp() + " \u2103" + "\n";
                    writer.append(logText);
                    writer.flush();
                    writer.close();
                    Toast.makeText(activity, "Done! Weather info logged to Logs.txt"
                            , Toast.LENGTH_LONG).show();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            //SAVE FILE HANDLING END
        }
    }

    //Method to handle More Details button click
    public void moreDetails (View view) {

        Intent intent = new Intent(this, MoreDetailsActivity.class);
        intent.putExtras(detailExtras);
        //Start MoreDetailsActivity
        startActivity(intent);
    }

}
