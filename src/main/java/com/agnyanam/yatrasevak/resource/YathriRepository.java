package com.agnyanam.yatrasevak.resource;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.agnyanam.yatrasevak.model.Yathri;


public interface YathriRepository extends MongoRepository<Yathri, String>  {

	public Yathri findByUserName(String userName);
}