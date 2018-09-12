package com.mm2.oauth.repository;

import com.mm2.oauth.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by alejandro on 12/09/18.
 */
@Repository
public interface UserRepository extends MongoRepository<User, String> {

    User findByEmail(String username);
}