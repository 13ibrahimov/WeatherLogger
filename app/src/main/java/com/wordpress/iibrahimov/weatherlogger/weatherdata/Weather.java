package com.wordpress.iibrahimov.weatherlogger.weatherdata;

//A class to handle data (related to weather)
//Info other than temperature is not used but could be used for app development
public class Weather {

    /*Miscellaneous info(saved in Miscellaneous class) matching this Weather,
    added as a component to Weather class to be used in parsing
     */
    public Miscellaneous miscellaneous;

    public WeatherInfo weatherInfo = new WeatherInfo();
    public Temperature temperature = new Temperature();
    public Wind wind = new Wind();
    public Clouds clouds = new Clouds();

    //Currently not used
    //public Rain rain = new Rain();
    //public Snow snow = new Snow();

    private int visibility;

    public class WeatherInfo {
        private int id;
        private String status;
        private String description;
        //private String icon;

        /*
        Info on icon(weather icon)
        was deemed irrelevant, thus is not saved for further development
        */

        public int getId() {
            return id;
        }
        public void setId(int id) {
            this.id = id;
        }
        public String getStatus() {
            return status;
        }
        public void setStatus(String status) {
            this.status = status;
        }
        public String getDescription() {
            return description;
        }
        public void setDescr(String description) {
            this.description = description;
        }
    }

    public class Temperature {
        private float temp;
        private float pressure;
        private float humidity;
        private float minTemp;
        private float maxTemp;

        public float getTemp() {
            return temp;
        }
        public void setTemp(float temp) {
            this.temp = temp;
        }
        public float getHumidity() {
            return humidity;
        }
        public void setHumidity(float humidity) {
            this.humidity = humidity;
        }
        public float getPressure() {
            return pressure;
        }
        public void setPressure(float pressure) {
            this.pressure = pressure;
        }
        public float getMinTemp() {
            return minTemp;
        }
        public void setMinTemp(float minTemp) {
            this.minTemp = minTemp;
        }
        public float getMaxTemp() {
            return maxTemp;
        }
        public void setMaxTemp(float maxTemp) {
            this.maxTemp = maxTemp;
        }
    }

    public  class Wind {
        private float speed;
        private float degree;

        public float getSpeed() {
            return speed;
        }
        public void setSpeed(float speed) {
            this.speed = speed;
        }
        public float getDegree() {
            return degree;
        }
        public void setDegree(float degree) {
            this.degree = degree;
        }
    }

    public  class Clouds {
        private int percentage;

        public int getPercentage() {
            return percentage;
        }

        public void setPercentage(int percentage) {
            this.percentage = percentage;
        }
    }

    /*
    Rain and Snow classes are not currently used
    Written in case if API data returned on rain and snow is relevant
    for app development
     */
    public  class Rain {
        private String time;
        private float volume;

        public String getTime() {
            return time;
        }
        public void setTime(String time) {
            this.time = time;
        }
        public float getVolume() {
            return volume;
        }
        public void setAmmount(float volume) {
            this.volume = volume;
        }
    }

    public  class Snow {
        private String time;
        private float volume;

        public String getTime() {
            return time;
        }
        public void setTime(String time) {
            this.time = time;
        }
        public float getVolume() {
            return volume;
        }
        public void setAmmount(float volume) {
            this.volume = volume;
        }
    }

    public int getVisibility() {
        return visibility;
    }
    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }
}
