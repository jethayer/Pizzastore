package dev.jethayer.pizzastore.service;

import org.springframework.stereotype.Service;

import dev.jethayer.pizzastore.model.Pizza;
import dev.jethayer.pizzastore.model.Topping;
import dev.jethayer.pizzastore.repository.PizzaRepository;
import dev.jethayer.pizzastore.repository.ToppingRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Service for managing pizzas and their toppings.
 */
@Service
public class PizzaService {

    private final PizzaRepository pizzaRepository;
    private final ToppingRepository toppingRepository;

    public PizzaService(PizzaRepository pizzaRepository, ToppingRepository toppingRepository) {
        this.pizzaRepository = pizzaRepository;
        this.toppingRepository = toppingRepository;
    }

    /**
     * Retrieves all pizzas.
     *
     * @return List of all pizzas
     */
    public List<Pizza> getAllPizzas() {
        return pizzaRepository.findAll();
    }

    /**
     * Creates a new pizza after validating its name and toppings.
     *
     * @param pizza the pizza to create
     * @return the created pizza
     * @throws IllegalArgumentException if the pizza already exists
     */
    public Pizza createPizza(Pizza pizza) {
        if (pizzaRepository.findByName(pizza.getName().toUpperCase()).isPresent()) {
            throw new IllegalArgumentException("Pizza already exists");
        }

        // Ensure toppings are valid before saving
        Set<Topping> toppings = new HashSet<>();
        for (Topping topping : pizza.getToppings()) {
            Optional<Topping> existingTopping = toppingRepository.findById(topping.getId());
            existingTopping.ifPresent(toppings::add); // Add only if topping exists
        }
        pizza.setToppings(toppings); // Set valid toppings
        return pizzaRepository.save(pizza);
    }

    /**
     * Updates an existing pizza after validating its toppings.
     *
     * @param id    the ID of the pizza to update
     * @param pizza the pizza data to update
     * @return the updated pizza
     * @throws IllegalArgumentException if the pizza is not found
     */
    public Pizza updatePizza(Long id, Pizza pizza) {
        if (pizzaRepository.findById(id).isPresent()) {
            pizza.setId(id);

            // Ensure toppings are valid before updating
            Set<Topping> toppings = new HashSet<>();
            for (Topping topping : pizza.getToppings()) {
                Optional<Topping> existingTopping = toppingRepository.findById(topping.getId());
                existingTopping.ifPresent(toppings::add); // Add only if topping exists
            }
            pizza.setToppings(toppings); // Set valid toppings

            return pizzaRepository.save(pizza);
        }
        throw new IllegalArgumentException("Pizza not found");
    }

    /**
     * Deletes a pizza by its ID.
     *
     * @param id the ID of the pizza to delete
     */
    public void deletePizza(Long id) {
        pizzaRepository.deleteById(id);
    }
}
