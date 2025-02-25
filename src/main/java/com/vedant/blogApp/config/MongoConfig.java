package com.vedant.blogApp.config;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;


@Configuration
public class MongoConfig {

    private final MongoTemplate mongoTemplate;

    public MongoConfig(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @PostConstruct
    public void initIndexes() {
        mongoTemplate.indexOps("users").ensureIndex(
                new Index().on("userName", org.springframework.data.domain.Sort.Direction.ASC).unique()
        );
    }
}
