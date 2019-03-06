package com.wordpress.iibrahimov.weatherlogger;

import android.os.Environment;
import android.util.Log;

import java.io.File;

public class FileHandler {

    private static final String LOG_TAG = FileHandler.class.getSimpleName();

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Creates and gets the appropriate directory(appName) inside the external storage(here DOWNLOADS directory),
     to save WeatherLogger file(Logs.txt) */
    public File getDownloadsDir(String appName) {

        // Get the directory for the user's downloads directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS), appName);
        if (!file.mkdirs()) {
            if (file.isDirectory()) {
                Log.i(LOG_TAG, "Directory already exists");
            }
            else {
                Log.e(LOG_TAG, "Directory not created");
            }
        }
        return file;
    }

    /* Checks if file exists in the specified directory, returns false if file doesn't exist */
    //Note: path argument includes file name
    public boolean doesFileExist(String path) {
        //File extStorage = Environment.getExternalStorageDirectory();
        File extStorage = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS);
        File file = new File(extStorage.getAbsolutePath() + path);

        if (file.exists()) {
            Log.i(LOG_TAG, "File already exists");
            return true;
        }
        Log.i(LOG_TAG, "File should be created");
        return false;
    }
}
