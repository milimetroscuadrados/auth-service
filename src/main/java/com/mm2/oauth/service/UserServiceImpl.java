package com.mm2.oauth.service;

import com.mm2.oauth.domain.User;
import com.mm2.oauth.exception.ServiceException;
import com.mm2.oauth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Autowired
    private UserRepository repository;

    @Override
    public User create(User user) {
        User existing = repository.findByEmail(user.getEmail());

        if (existing != null)
            throw new ServiceException(
                    "user already exists: " + user.getEmail(),
                    "USER_EXIST");

        String hash = encoder.encode(user.getPassword());
        user.setPassword(hash);

        return repository.save(user);
    }

    @Override
    public void delete(User user) {
        repository.delete(user);
    }

    @Override
    public User modify(User user) {
        return repository.save(user);
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public User findById(String id) {
        return repository.findById(id).get();
    }

    @Override
    public User findByEmail(String username) {
        return repository.findByEmail(username);
    }
}
