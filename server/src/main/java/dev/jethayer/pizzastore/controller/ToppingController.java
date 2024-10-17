package dev.jethayer.pizzastore.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import dev.jethayer.pizzastore.model.Topping;
import dev.jethayer.pizzastore.service.ToppingService;

import java.util.List;

/**
 * REST Controller for managing topping-related operations.
 */
@RestController
@RequestMapping("/toppings")
public class ToppingController {

    private final ToppingService toppingService;

    /**
     * Constructor to inject the ToppingService.
     *
     * @param toppingService the service to handle topping operations
     */
    public ToppingController(ToppingService toppingService) {
        this.toppingService = toppingService;
    }

    /**
     * Fetches all toppings.
     *
     * @return a list of toppings
     */
    @GetMapping
    public List<Topping> getAllToppings() {
        return toppingService.getAllToppings();
    }

    /**
     * Creates a new topping.
     *
     * @param topping the topping to create
     * @return the created topping
     */
    @PostMapping
    public ResponseEntity<Topping> createTopping(@RequestBody Topping topping) {
        return ResponseEntity.ok(toppingService.createTopping(topping));
    }

    /**
     * Updates an existing topping.
     *
     * @param id      the ID of the topping to update
     * @param topping the updated topping details
     * @return the updated topping
     */
    @PutMapping("/{id}")
    public ResponseEntity<Topping> updateTopping(@PathVariable Long id, @RequestBody Topping topping) {
        return ResponseEntity.ok(toppingService.updateTopping(id, topping));
    }

    /**
     * Deletes a topping by ID.
     *
     * @param id the ID of the topping to delete
     * @return a response entity indicating no content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTopping(@PathVariable Long id) {
        toppingService.deleteTopping(id);
        return ResponseEntity.noContent().build();
    }
}
