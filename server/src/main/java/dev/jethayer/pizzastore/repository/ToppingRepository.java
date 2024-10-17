package dev.jethayer.pizzastore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.jethayer.pizzastore.model.Topping;

import java.util.Optional;

/**
 * Repository interface for Topping entity.
 * Provides CRUD operations and additional query methods.
 */
@Repository
public interface ToppingRepository extends JpaRepository<Topping, Long> {

    /**
     * Find a topping by its name.
     *
     * @param name the name of the topping
     * @return an Optional containing the topping if found, or an empty Optional if not found
     */
    Optional<Topping> findByName(String name);
}
