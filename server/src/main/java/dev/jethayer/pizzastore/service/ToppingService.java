package dev.jethayer.pizzastore.service;

import org.springframework.stereotype.Service;

import dev.jethayer.pizzastore.model.Topping;
import dev.jethayer.pizzastore.repository.ToppingRepository;

import java.util.List;

/**
 * Service for managing pizza toppings.
 */
@Service
public class ToppingService {

    private final ToppingRepository toppingRepository;

    public ToppingService(ToppingRepository toppingRepository) {
        this.toppingRepository = toppingRepository;
    }

    /**
     * Retrieves all toppings.
     *
     * @return List of all toppings
     */
    public List<Topping> getAllToppings() {
        return toppingRepository.findAll();
    }

    /**
     * Creates a new topping after validating its name.
     *
     * @param topping the topping to create
     * @return the created topping
     * @throws IllegalArgumentException if the topping already exists
     */
    public Topping createTopping(Topping topping) {
        if (toppingRepository.findByName(topping.getName().toLowerCase()).isPresent()) {
            throw new IllegalArgumentException("Topping already exists");
        }
        return toppingRepository.save(topping);
    }

    /**
     * Updates an existing topping after validating its name.
     *
     * @param id      the ID of the topping to update
     * @param topping the topping data to update
     * @return the updated topping
     * @throws IllegalArgumentException if the topping is not found or the name is taken
     */
    public Topping updateTopping(Long id, Topping topping) {
        if (toppingRepository.findById(id).isPresent()) {
            topping.setId(id);

            if (toppingRepository.findByName(topping.getName().toLowerCase()).isPresent()) {
                throw new IllegalArgumentException("Topping Name Taken");
            }

            return toppingRepository.save(topping);
        }
        throw new IllegalArgumentException("Topping not found");
    }

    /**
     * Deletes a topping by its ID.
     *
     * @param id the ID of the topping to delete
     */
    public void deleteTopping(Long id) {
        toppingRepository.deleteById(id);
    }
}
