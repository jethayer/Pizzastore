package dev.jethayer.pizzastore.controller;

import dev.jethayer.pizzastore.model.Pizza;
import dev.jethayer.pizzastore.service.PizzaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PizzaControllerTest {

    @Mock
    private PizzaService pizzaService;

    @InjectMocks
    private PizzaController pizzaController;

    private Pizza pizza;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        pizza = new Pizza(1L, "Margherita", null);
    }

    @Test
    void testGetAllPizzas() {
        List<Pizza> pizzas = new ArrayList<>();
        pizzas.add(pizza);
        when(pizzaService.getAllPizzas()).thenReturn(pizzas);

        List<Pizza> result = pizzaController.getAllPizzas();
        
        assertEquals(1, result.size());
        assertEquals("Margherita", result.get(0).getName());
        verify(pizzaService, times(1)).getAllPizzas();
    }

    @Test
    void testCreatePizza() {
        when(pizzaService.createPizza(pizza)).thenReturn(pizza);

        ResponseEntity<Pizza> response = pizzaController.createPizza(pizza);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(pizza, response.getBody());
        verify(pizzaService, times(1)).createPizza(pizza);
    }

    @Test
    void testUpdatePizza() {
        when(pizzaService.updatePizza(1L, pizza)).thenReturn(pizza);

        ResponseEntity<Pizza> response = pizzaController.updatePizza(1L, pizza);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(pizza, response.getBody());
        verify(pizzaService, times(1)).updatePizza(1L, pizza);
    }

    @Test
    void testDeletePizza() {
        doNothing().when(pizzaService).deletePizza(1L);

        ResponseEntity<Void> response = pizzaController.deletePizza(1L);

        assertEquals(204, response.getStatusCode().value());
        verify(pizzaService, times(1)).deletePizza(1L);
    }
}
