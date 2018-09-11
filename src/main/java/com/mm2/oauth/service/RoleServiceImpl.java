package com.mm2.oauth.service;

import com.mm2.oauth.domain.Role;
import com.mm2.oauth.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository repository;

    @Override
    public Role findByDescription(String description) {
        return repository.findByDescription(description);
    }
}
