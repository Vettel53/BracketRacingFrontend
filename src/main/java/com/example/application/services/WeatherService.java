package com.example.application.services;

import com.example.application.models.Weather;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;

@Service
public class WeatherService {

    private final URI BASE_URI = URI.create("http://localhost:8081/weather?trackName=Edinburg%20motorsports%20park");
    private final WebClient.Builder builder;

    public WeatherService() {
        builder = WebClient.builder();
    }

    public Weather getCurrentWeather() {
        Weather response = builder.build()
                .get()
                .uri(BASE_URI)
                .retrieve()
                .bodyToMono(Weather.class)
                .block();
        return response;
    }

}
