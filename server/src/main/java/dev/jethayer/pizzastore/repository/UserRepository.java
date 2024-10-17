package dev.jethayer.pizzastore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.jethayer.pizzastore.model.Users;

/**
 * Repository interface for accessing user data in the database.
 */
@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {
    /**
     * Finds a user by their username.
     * 
     * @param username the username of the user
     * @return the user associated with the given username
     */
    Users findByUsername(String username);

    /**
     * Checks if a user exists with the given username.
     * 
     * @param username the username to check
     * @return true if a user exists, false otherwise
     */
    boolean existsByUsername(String username);
}
