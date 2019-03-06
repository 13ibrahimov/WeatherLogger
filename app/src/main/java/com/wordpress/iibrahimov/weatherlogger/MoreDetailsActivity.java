package com.wordpress.iibrahimov.weatherlogger;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/*
    NOTE: Android launch mode is set to single task (android:launchMode="singleTask")
    in manifest file for Main and Display Activities
    to make sure that there is only one instance of both activities in the BackStack,
    thus the user can use back button to go to parent activity without losing
    already filled tags of weather data.
 */

public class MoreDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_details);

        TextView location = (TextView) findViewById(R.id.textLocation);
        TextView condition = (TextView) findViewById(R.id.textCondition);
        TextView temperature = (TextView) findViewById(R.id.textTemperature);
        TextView pressure = (TextView) findViewById(R.id.pressureVal);
        TextView humidity = (TextView) findViewById(R.id.humidityVal);
        TextView windText = (TextView) findViewById(R.id.windVal);
        TextView cloudiness = (TextView) findViewById(R.id.cloudinessVal);
        TextView visibility = (TextView) findViewById(R.id.visibilityVal);
        TextView sunrise = (TextView) findViewById(R.id.sunriseVal);
        TextView sunset = (TextView) findViewById(R.id.sunsetVal);

        String loc;
        String cond;
        String temp;
        String press;
        String humid;
        String wind;
        String cloud;
        String vis;
        String rise;
        String set;

        //Receiving data
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {

            loc = extras.getString("CITY") + ", " + extras.getString("COUNTRY");
            location.setText(loc);
            cond = extras.getString("CONDITION");
            condition.setText(cond);
            temp = String.valueOf((int)extras.getFloat("TEMPERATURE")) + " \u2103";
            temperature.setText(temp);
            press = String.valueOf((int)extras.getFloat("PRESSURE")) + " hpa";
            pressure.setText(press);
            humid = String.valueOf((int)extras.getFloat("HUMIDITY")) + " %";
            humidity.setText(humid);
            wind = String.valueOf(extras.getFloat("WIND_SPEED")) + " m/s" + ", "
                    + "degree ( " + String.valueOf(extras.getFloat("WIND_DEGREE")) + " )";
            windText.setText(wind);
            cloud = String.valueOf(extras.getInt("CLOUDINESS")) + " %";
            cloudiness.setText(cloud);
            vis = String.valueOf(extras.getInt("VISIBILITY")) + " km";
            visibility.setText(vis);

            //Get time of Sunrise
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.US);
            Date dateRise = new Date();
            dateRise.setTime(extras.getLong("SUNRISE")*1000);
            rise = sdf.format(dateRise);
            sunrise.setText(rise);

            //Get time of Sunset
            Date dateSet = new Date();
            dateSet.setTime(extras.getLong("SUNSET")*1000);
            set = sdf.format(dateSet);;
            sunset.setText(set);
        }
    }
}
