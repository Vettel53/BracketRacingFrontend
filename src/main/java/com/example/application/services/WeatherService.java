package com.example.application.services;

import com.example.application.models.Weather;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;

@Service
public class WeatherService {

    private final WebClient.Builder builder;

    public WeatherService() {
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

}
