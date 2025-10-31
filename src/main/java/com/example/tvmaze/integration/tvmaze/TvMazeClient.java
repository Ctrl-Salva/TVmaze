package com.example.tvmaze.integration.tvmaze;

import java.time.LocalDate;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;

@Component
public class TvMazeClient {
    
    private static final String BASE_URL = "https://api.tvmaze.com";
    private final RestTemplate restTemplate;
    private final Gson gson;

    public TvMazeClient() {
        this.restTemplate = new RestTemplate();
        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, 
                    (JsonDeserializer<LocalDate>) (json, typeOfT, context) ->
                        json.isJsonNull() ? null : LocalDate.parse(json.getAsString()))
                .create();
    }

    public <T> T get(String endpoint, Class<T> clazz) {
        String json = restTemplate.getForObject(BASE_URL + endpoint, String.class);
        return gson.fromJson(json, clazz);
    }

    public <T> T[] getList(String endpoint, Class<T[]> clazz) {
        String json = restTemplate.getForObject(BASE_URL + endpoint, String.class);
        return gson.fromJson(json, clazz);
    }
}
