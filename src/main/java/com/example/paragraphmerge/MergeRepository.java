package com.example.paragraphmerge;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MergeRepository extends MongoRepository<Text, String> {

}