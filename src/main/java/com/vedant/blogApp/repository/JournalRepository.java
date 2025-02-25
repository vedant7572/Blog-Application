package com.vedant.blogApp.repository;

import com.vedant.blogApp.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JournalRepository extends MongoRepository<JournalEntry, ObjectId> {

    //this much is enough and we can use the functions of the MongoRepo using our extended repo interface
}
