package com.example.application.services;

import com.example.application.WeatherRepo;
import com.example.application.models.Run;
import com.example.application.models.Weather;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;

@Service
public class WeatherService {

    private final WebClient.Builder builder;
    private final WeatherRepo weatherRepo;

    public WeatherService(WeatherRepo weatherRepo) {
        this.weatherRepo = weatherRepo;
        builder = WebClient.builder();
    }

    // Used for the fake run generation (Preset Variables)
    public Weather getCurrentWeather() {
        URI placeHolderWeather = URI.create("http://localhost:8081/weather?trackName=Edinburg%20motorsports%20park");

        // Return Weather Class after API Call
        return builder.build()
                .get()
                .uri(placeHolderWeather)
                .retrieve()
                .bodyToMono(Weather.class)
                .block();
    }

    // Used to generate real runs (User Selects)
    public Weather getCurrentWeather(String raceTrack) {
        // Reformat String and append it to URI
        raceTrack = reformatTrack(raceTrack);
        URI weatherURI = URI.create("http://localhost:8081/weather?trackName=" + raceTrack);

        // Return Weather Class after API Call
        return builder.build()
                .get()
                .uri(weatherURI)
                .retrieve()
                .bodyToMono(Weather.class)
                .block();
    }

    // Used to reformat spaces into %20 for proper URL format
    private String reformatTrack(String raceTrack) {
       return raceTrack.replaceAll("\\s", "%20");
    }

    public void updateWeather(Run runToEdit) {
        
        Weather currentWeather = runToEdit.getWeather();
        Weather newWeather = getCurrentWeather(runToEdit.getTrack());

        // Set currentWeather attributes to newWeather attributes
        currentWeather.setTemperature(newWeather.getTemperature());
        currentWeather.setRelativeHumidity(newWeather.getRelativeHumidity());
        currentWeather.setUncorrectedBarometer(newWeather.getUncorrectedBarometer());
        currentWeather.setCorrectedBarometer(newWeather.getCorrectedBarometer());
        currentWeather.setWindDirection(newWeather.getWindSpeed());
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
    }
}
