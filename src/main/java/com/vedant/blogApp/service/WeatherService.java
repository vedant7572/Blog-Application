package com.vedant.blogApp.service;

import com.vedant.blogApp.api.response.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherService {
    private static final String apiKey="1bfbea369f3b4488b9f161958241706";

    private static final String  API="https://api.weatherapi.com/v1/forecast.json?key=$API_KEY&days=7&q=$city";

    @Autowired
    private RestTemplate restTemplate;


    public WeatherResponse getWeather(String city){
        String request=API.replace("$API_KEY",apiKey).replace("$city",city);

        ResponseEntity<WeatherResponse> response = restTemplate.exchange(request, HttpMethod.GET, null, WeatherResponse.class);

        WeatherResponse body=response.getBody();
        return body  ;
    }

}
