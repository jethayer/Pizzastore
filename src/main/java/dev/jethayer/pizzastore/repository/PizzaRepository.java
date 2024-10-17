package dev.jethayer.pizzastore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.jethayer.pizzastore.model.Pizza;

import java.util.Optional;

/**
 * Repository interface for Pizza entity.
 * Provides CRUD operations and additional query methods.
 */
@Repository
public interface PizzaRepository extends JpaRepository<Pizza, Long> {

    /**
     * Find a pizza by its name.
     *
     * @param name the name of the pizza
     * @return an Optional containing the pizza if found, or an empty Optional if not found
     */
    Optional<Pizza> findByName(String name);
}
