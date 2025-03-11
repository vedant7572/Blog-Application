package com.vedant.blogApp.repository;

import com.vedant.blogApp.entity.ConfigJournalAppEntity;
import com.vedant.blogApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConfigJournalRepository extends MongoRepository<ConfigJournalAppEntity, ObjectId> {

}
