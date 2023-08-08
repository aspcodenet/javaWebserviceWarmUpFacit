package se.systementor.javawebservicewarmupfacit.models;

import java.time.LocalDateTime;
import java.util.UUID;

public class WeatherPrediction {
    private UUID id;
    private int date;
    private int hour;

    private float temperature;
    public WeatherPrediction(UUID id) {
        this.id = id;
    }

    public String SortOrder(){
        return String.valueOf( date) + hour;
    }

    public WeatherPrediction() {

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }




    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }
}
