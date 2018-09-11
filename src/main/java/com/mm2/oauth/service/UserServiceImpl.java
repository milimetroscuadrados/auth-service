package com.mm2.oauth.service;

import com.mm2.oauth.domain.User;
import com.mm2.oauth.domain.errorCodes.ErrorCodes;
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
        User existing = repository.findByUsername(user.getUsername());

        if (existing != null)
            throw new ServiceException(
                    "user already exists: " + user.getUsername(),
                    ErrorCodes.USER_EXIST.code());
//            throw new UniqueConstraintException("user already exists: " + user.getUsername());

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
    public User findById(Long id) {
        return repository.findOne(id);
    }

    @Override
    public User findByUsername(String username) {
        return repository.findByUsername(username);
    }
}
