package com.vedant.blogApp.cache;


import com.vedant.blogApp.entity.ConfigJournalAppEntity;
import com.vedant.blogApp.repository.ConfigJournalRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {
    public Map<String,String> appCache;
    @Autowired
    private ConfigJournalRepository configJournalRepository;

    //fetches the api from the database and stores it in the cache which is used by the WeatherService

    @PostConstruct
    void init(){
        appCache=new HashMap<>();
        List<ConfigJournalAppEntity> all = configJournalRepository.findAll();

        for(ConfigJournalAppEntity entity:all){
            appCache.put(entity.getKey(),entity.getValue());
        }
    }

}
