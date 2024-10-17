package dev.jethayer.pizzastore.controller;

import dev.jethayer.pizzastore.model.Topping;
import dev.jethayer.pizzastore.service.ToppingService;
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

class ToppingControllerTest {

    @Mock
    private ToppingService toppingService;

    @InjectMocks
    private ToppingController toppingController;

    private Topping topping;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        topping = new Topping(1L, "Cheese");
    }

    @Test
    void testGetAllToppings() {
        List<Topping> toppings = new ArrayList<>();
        toppings.add(topping);
        when(toppingService.getAllToppings()).thenReturn(toppings);

        List<Topping> result = toppingController.getAllToppings();
        
        assertEquals(1, result.size());
        assertEquals("Cheese", result.get(0).getName());
        verify(toppingService, times(1)).getAllToppings();
    }

    @Test
    void testCreateTopping() {
        when(toppingService.createTopping(topping)).thenReturn(topping);

        ResponseEntity<Topping> response = toppingController.createTopping(topping);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(topping, response.getBody());
        verify(toppingService, times(1)).createTopping(topping);
    }

    @Test
    void testUpdateTopping() {
        when(toppingService.updateTopping(1L, topping)).thenReturn(topping);

        ResponseEntity<Topping> response = toppingController.updateTopping(1L, topping);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(topping, response.getBody());
        verify(toppingService, times(1)).updateTopping(1L, topping);
    }

    @Test
    void testDeleteTopping() {
        doNothing().when(toppingService).deleteTopping(1L);

        ResponseEntity<Void> response = toppingController.deleteTopping(1L);

        assertEquals(204, response.getStatusCode().value());
        verify(toppingService, times(1)).deleteTopping(1L);
    }
}
