package dev.jethayer.pizzastore.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import dev.jethayer.pizzastore.model.Pizza;
import dev.jethayer.pizzastore.service.PizzaService;

import java.util.List;

/**
 * REST Controller for managing pizza-related operations.
 */
@RestController
@RequestMapping("/pizzas")
public class PizzaController {

    private final PizzaService pizzaService;

    /**
     * Constructor to inject the PizzaService.
     *
     * @param pizzaService the service to handle pizza operations
     */
    public PizzaController(PizzaService pizzaService) {
        this.pizzaService = pizzaService;
    }

    /**
     * Fetches all pizzas.
     *
     * @return a list of pizzas
     */
    @GetMapping
    public List<Pizza> getAllPizzas() {
        return pizzaService.getAllPizzas();
    }

    /**
     * Creates a new pizza.
     *
     * @param pizza the pizza to create
     * @return the created pizza
     */
    @PostMapping
    public ResponseEntity<Pizza> createPizza(@RequestBody Pizza pizza) {
        return ResponseEntity.ok(pizzaService.createPizza(pizza));
    }

    /**
     * Updates an existing pizza.
     *
     * @param id    the ID of the pizza to update
     * @param pizza the updated pizza details
     * @return the updated pizza
     */
    @PutMapping("/{id}")
    public ResponseEntity<Pizza> updatePizza(@PathVariable Long id, @RequestBody Pizza pizza) {
        return ResponseEntity.ok(pizzaService.updatePizza(id, pizza));
    }

    /**
     * Deletes a pizza by ID.
     *
     * @param id the ID of the pizza to delete
     * @return a response entity indicating no content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePizza(@PathVariable Long id) {
        pizzaService.deletePizza(id);
        return ResponseEntity.noContent().build();
    }
}
