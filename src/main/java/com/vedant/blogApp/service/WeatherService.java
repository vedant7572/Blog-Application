package com.vedant.blogApp.service;

import com.vedant.blogApp.api.response.WeatherResponse;
import com.vedant.blogApp.cache.AppCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    @Value("${weather.api.key}")
    private String apiKey;
    @Autowired
    AppCache appCache;

    @Autowired
    private RestTemplate restTemplate;


    public WeatherResponse getWeather(String city){
        String request=appCache.appCache.get("weather_api").replace("<apiKey>",apiKey).replace("<city>",city);
//        String request=API.replace("<apiKey>",apiKey).replace("<city>",city); alternative way to do the same thing hardcoding the api url
        ResponseEntity<WeatherResponse> response = restTemplate.exchange(request, HttpMethod.GET, null, WeatherResponse.class);

        WeatherResponse body=response.getBody();
        return body  ;
    }

}
