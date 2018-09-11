package com.mm2.oauth.controller;

import com.mm2.oauth.service.security.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

@Controller
public class SecurityController {

    @Autowired
    private SecurityService securityService;

    @RequestMapping(value="/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value="/forgot", method = RequestMethod.GET)
    public String forgotGet() {
        return "forgot";
    }

    @RequestMapping(value="/forgot", method = RequestMethod.POST)
    public void forgotPost(@RequestBody String email) {
        this.securityService.forgot(email);
    }


    @RequestMapping(value="/current", method = RequestMethod.GET)
    public Principal currentGet(Principal principal) {
        return principal;
    }
}
