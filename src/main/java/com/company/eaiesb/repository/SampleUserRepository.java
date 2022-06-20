package com.company.eaiesb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.company.eaiesb.models.UserSample;


@Repository
public interface SampleUserRepository extends MongoRepository<UserSample,String> {

	public UserSample findByEmail(String email);
}
