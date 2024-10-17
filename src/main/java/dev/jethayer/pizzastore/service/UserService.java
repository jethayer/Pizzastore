package dev.jethayer.pizzastore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import dev.jethayer.pizzastore.model.Users;
import dev.jethayer.pizzastore.repository.UserRepository;

/**
 * Service for managing user registration and authentication.
 */
@Service
public class UserService {

    @Autowired
    private JWTService jwtService;

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    private UserRepository repo;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    /**
     * Registers a new user after validating the username.
     *
     * @param user the user to register
     * @return the registered user
     * @throws IllegalArgumentException if the username is already taken
     */
    public Users register(Users user) {
        if (repo.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("Username Taken");
        }

        user.setPassword(encoder.encode(user.getPassword()));
        repo.save(user);
        return user;
    }

    /**
     * Authenticates a user and generates a JWT token if successful.
     *
     * @param user the user to authenticate
     * @return a JSON string containing the JWT token and user role, or "fail" if authentication fails
     */
    public String verify(Users user) {
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        if (authentication.isAuthenticated()) {
            Users authenticatedUser = repo.findByUsername(user.getUsername());

            // Generate JWT token
            String token = jwtService.generateToken(user.getUsername());

            // Return both token and role as a JSON response
            return "{\"token\": \"" + token + "\", \"role\": \"" + authenticatedUser.getRole() + "\"}";
        } else {
            return "fail";
        }
    }
}
