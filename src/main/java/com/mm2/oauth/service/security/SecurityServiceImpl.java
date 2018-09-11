package com.mm2.oauth.service.security;

import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl implements SecurityService {

    @Override
    public void forgot(String email) {
        //todo: implement send email with change password link.
        System.out.println("Email: " + email);
    }
}
