package com.mm2.oauth.service;


import com.mm2.oauth.domain.Role;

public interface RoleService {

    Role findByDescription(String role);
}
