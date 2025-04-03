package com.example.application.models;

import jakarta.persistence.*;

@Entity
public class Weather {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "run_id")
    private Run run;

    private String temperature;
    private String humidity;
    private String uncorrectedBarometer;
    private String correctedBarometer;
    private String windSpeed;
    private String windDirection;
    private String dewPoint;
    private String saturationPressure;
    private String vaporPressure;
    private String grains;
    private String airDensityNoVapor;
    private String airDensityWithVapor;
    private String densityAltitude;

    public Weather() {
    }

    public Weather(Long id, String temperature, String humidity, String uncorrectedBarometer, String correctedBarometer, String windSpeed, String windDirection, String dewPoint, String saturationPressure, String vaporPressure, String grains, String airDensityNoVapor, String airDensityWithVapor, String densityAltitude) {
        this.id = id;
        this.temperature = temperature;
        this.humidity = humidity;
        this.uncorrectedBarometer = uncorrectedBarometer;
        this.correctedBarometer = correctedBarometer;
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
        this.dewPoint = dewPoint;
        this.saturationPressure = saturationPressure;
        this.vaporPressure = vaporPressure;
        this.grains = grains;
        this.airDensityNoVapor = airDensityNoVapor;
        this.airDensityWithVapor = airDensityWithVapor;
        this.densityAltitude = densityAltitude;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getUncorrectedBarometer() {
        return uncorrectedBarometer;
    }

    public void setUncorrectedBarometer(String uncorrectedBarometer) {
        this.uncorrectedBarometer = uncorrectedBarometer;
    }

    public String getCorrectedBarometer() {
        return correctedBarometer;
    }

    public void setCorrectedBarometer(String correctedBarometer) {
        this.correctedBarometer = correctedBarometer;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public String getDewPoint() {
        return dewPoint;
    }

    public void setDewPoint(String dewPoint) {
        this.dewPoint = dewPoint;
    }

    public String getSaturationPressure() {
        return saturationPressure;
    }

    public void setSaturationPressure(String saturationPressure) {
        this.saturationPressure = saturationPressure;
    }

    public String getVaporPressure() {
        return vaporPressure;
    }

    public void setVaporPressure(String vaporPressure) {
        this.vaporPressure = vaporPressure;
    }

    public String getGrains() {
        return grains;
    }

    public void setGrains(String grains) {
        this.grains = grains;
    }

    public String getAirDensityNoVapor() {
        return airDensityNoVapor;
    }

    public void setAirDensityNoVapor(String airDensityNoVapor) {
        this.airDensityNoVapor = airDensityNoVapor;
    }

    public String getAirDensityWithVapor() {
        return airDensityWithVapor;
    }

    public void setAirDensityWithVapor(String airDensityWithVapor) {
        this.airDensityWithVapor = airDensityWithVapor;
    }

    public String getDensityAltitude() {
        return densityAltitude;
    }

    public void setDensityAltitude(String densityAltitude) {
        this.densityAltitude = densityAltitude;
    }
}
