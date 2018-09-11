package com.mm2.oauth.service;

import com.mm2.oauth.domain.User;

import java.util.List;

public interface UserService {

    User create(User user);

    void delete(User user);

    User modify(User user);

    List<User> findAll();

    User findById(Long id);

    User findByUsername(String username);
}
