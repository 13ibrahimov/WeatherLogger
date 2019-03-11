package com.wordpress.iibrahimov.weatherlogger;

import android.Manifest;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.content.pm.PackageManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    //Location
    protected LocationManager locationManager;
    protected Location location;
    protected double latitude,longitude;
    //protected TextView textLoc;
    public static int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION =1;

    //Time
    protected long currentTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Asks for Location and Write to External Storage permissions, if not already granted to app
        //Check if app has permissions
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            //If permissions are not already granted ask for permissions
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                            android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    0);
        }

    }

    public void saveClick(View view) {

        currentTime = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.US);
        Date currentDate = new Date(currentTime);
        String saveDate = sdf.format(currentDate);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationListener myLocationListener = new MyLocationListener();

        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, myLocationListener);
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            // Wait until phone determines its location
            if(location != null) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();

                latitude = location.getLatitude();
                longitude = location.getLongitude();

                //Sending Date, Time and current Coordinates to Display Activity
                Intent intent = new Intent(this, DisplayActivity.class);
                Bundle extras = new Bundle();
                extras.putString("DATE", saveDate);
                extras.putDouble("LATITUDE", latitude);
                extras.putDouble("LONGITUDE", longitude);
                intent.putExtras(extras);
                //Start DisplayActivity
                startActivity(intent);
            }
            else {
                String message = "Waiting for location...";
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
            }

        } catch (SecurityException e) {
            Toast.makeText(MainActivity.this, "WeatherLogger does not have permission to use your location.", Toast.LENGTH_LONG).show(); // lets the user know there is a problem with the gps
            e.printStackTrace();
        }
    }

    //to get location updates continuously in case location changes
    private class MyLocationListener implements LocationListener {

        public void onLocationChanged(Location loc) {
            /* String message = String.format(
                    "New Location \n Latitude: %1$s \n Longitude: %2$s",
                    loc.getLatitude(), loc.getLongitude()
            );
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show(); */
        }
        public void onProviderDisabled(String provider) {

        }
        public void onProviderEnabled(String provider) {

        }
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

    }
}
