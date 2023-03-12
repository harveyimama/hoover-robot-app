package com.yoti.harveyimama.hoover.model.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.yoti.harveyimama.hoover.model.entities.DriveResponse;

@Repository
public interface DriveResponseRepository  extends MongoRepository<DriveResponse, String> { 

}
