package com.mm2.oauth.controller;

import com.mm2.oauth.domain.User;
import com.mm2.oauth.exception.EntityNotFoundException;
import com.mm2.oauth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<User> get() {
        return userService.findAll();
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public User getByUsername(@PathVariable String username) {
        User user = userService.findByUsername(username);
        if (user == null) throw new EntityNotFoundException("Username: " + username + " not found.");
        return user;
    }

    @RequestMapping(method = RequestMethod.PUT)
    public void modifyUser(@Valid @RequestBody User user) {
        userService.modify(user);
    }

//    @PreAuthorize("#oauth2.hasScope('read')")
    @RequestMapping(value="/current", method = RequestMethod.POST)
    public Principal current(Principal principal) {
        return principal;
    }
}
