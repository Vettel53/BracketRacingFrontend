package com.example.application.services;

import com.example.application.RunRepo;
import com.example.application.WeatherRepo;
import com.example.application.models.Run;
import com.example.application.models.Weather;
import com.vaadin.flow.component.notification.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;

@Service
public class WeatherService {

    private final WebClient.Builder builder;
    private final WeatherRepo weatherRepo;
    private final RunRepo runRepo;
    @Autowired
    private Environment environment;

    public WeatherService(WeatherRepo weatherRepo, RunRepo runRepo) {
        this.weatherRepo = weatherRepo;
        builder = WebClient.builder();
        this.runRepo = runRepo;
    }

    // Used for the fake run generation (Preset/Hardcoded Variables)
    public Weather getCurrentWeather() {
        // Try and connect to API and error handle
        String fakeRaceTrack = "Edinburg%20motorsports%20park";
        try {
            URI placeHolderWeather = URI.create(getAPIURL() + fakeRaceTrack);

            // Return Weather Class after API Call
            return builder.build()
                    .get()
                    .uri(placeHolderWeather)
                    .retrieve()
                    .bodyToMono(Weather.class)
                    .block();
        } catch (Exception e) {
            System.out.println("Weather API most likely down!");
            return null; // Return null after exception caught
        }
    }

    // Used to generate real runs (User Selects)
    public Weather getCurrentWeather(String raceTrack) {
        // Reformat String and append it to URI
        raceTrack = reformatTrack(raceTrack);
        try {
            URI weatherURI = URI.create(getAPIURL() + raceTrack);

            // Return Weather Class after API Call
            return builder.build()
                    .get()
                    .uri(weatherURI)
                    .retrieve()
                    .bodyToMono(Weather.class)
                    .block();
        } catch (Exception e) {
            System.out.println("Weather API Most likely down!");
            return null; // Return null after exception caught
        }
    }

    // Used to reformat spaces into %20 for proper URL format
    private String reformatTrack(String raceTrack) {
       return raceTrack.replaceAll("\\s", "%20");
    }

    public boolean updateWeather(Run runToEdit, String newTrack) {
        Weather currentWeather = runToEdit.getWeather();

        // Get new weather from new track
        Weather newWeather = getCurrentWeather(newTrack);
        if (newWeather == null) {
            Notification.show("Weather API down, please try again later...");
            return false; // Weather was not updated (API Error)
        }

        // Set currentWeather attributes to newWeather attributes
        currentWeather.setTemperature(newWeather.getTemperature());
        currentWeather.setRelativeHumidity(newWeather.getRelativeHumidity());
        currentWeather.setUncorrectedBarometer(newWeather.getUncorrectedBarometer());
        currentWeather.setCorrectedBarometer(newWeather.getCorrectedBarometer());
        currentWeather.setWindSpeed(newWeather.getWindSpeed());
        currentWeather.setWindDirection(newWeather.getWindDirection());
        currentWeather.setDewPoint(newWeather.getDewPoint());
        currentWeather.setSaturationPressure(newWeather.getSaturationPressure());
        currentWeather.setVaporPressure(newWeather.getVaporPressure());
        currentWeather.setGrains(newWeather.getGrains());
        currentWeather.setAirDensityNoWaterVapor(newWeather.getAirDensityNoWaterVapor());
        currentWeather.setAirDensityWithWaterVapor(newWeather.getAirDensityWithWaterVapor());
        currentWeather.setDensityAltitude(newWeather.getDensityAltitude());

        // Set the created run's weather
        runToEdit.setWeather(currentWeather);

        // Save weather to H2 database
        weatherRepo.save(currentWeather);

        return true;
    }

    private String getAPIURL() {
        if(environment.matchesProfiles("h2")) { // H2 database (testing/development most likely)
            return "http://localhost:8081/weather?trackName=";
        } else { // Can only be railway spring profile, in production.
            return "https://trackweatherapi-production.up.railway.app/weather?trackName=";
        }
        // Note: URL's still need concatenation at end for trackName
    }
}
