package com.mm2.oauth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

@Controller
public class SecurityController {

    @RequestMapping(value="/forgot", method = RequestMethod.GET)
    public String forgotGet() {
        return "forgot";
    }

    @RequestMapping(value="/current", method = RequestMethod.GET)
    public Principal currentGet(Principal principal) {
        return principal;
    }
}
