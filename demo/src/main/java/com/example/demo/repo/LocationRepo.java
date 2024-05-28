package com.example.demo.repo;

import com.example.demo.model.Location;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface LocationRepo extends MongoRepository<Location, String> {
}
