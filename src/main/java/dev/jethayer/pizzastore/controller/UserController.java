package dev.jethayer.pizzastore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import dev.jethayer.pizzastore.model.Users;
import dev.jethayer.pizzastore.service.UserService;

/**
 * REST Controller for handling user registration and login operations.
 */
@RestController
public class UserController {

    @Autowired
    private UserService service;

    /**
     * Registers a new user.
     *
     * @param user the user details to register
     * @return the registered user
     */
    @PostMapping("/register")
    public Users register(@RequestBody Users user) {
        return service.register(user);
    }

    /**
     * Logs in a user by verifying credentials.
     *
     * @param user the login details of the user
     * @return a verification response (e.g., token or message)
     */
    @PostMapping("/login")
    public String login(@RequestBody Users user) {
        return service.verify(user);
    }
}
