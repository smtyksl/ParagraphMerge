package com.example.paragraphmerge;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MergedRepository extends MongoRepository<MergedText, String> {

}
