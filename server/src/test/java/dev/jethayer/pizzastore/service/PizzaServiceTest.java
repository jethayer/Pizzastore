package dev.jethayer.pizzastore.service;

import dev.jethayer.pizzastore.model.Pizza;
import dev.jethayer.pizzastore.model.Topping;
import dev.jethayer.pizzastore.repository.PizzaRepository;
import dev.jethayer.pizzastore.repository.ToppingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PizzaServiceTest {

    @Mock
    private PizzaRepository pizzaRepository;

    @Mock
    private ToppingRepository toppingRepository;

    @InjectMocks
    private PizzaService pizzaService;

    private Pizza pizza;
    private Topping topping;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        topping = new Topping(1L, "Cheese");
        pizza = new Pizza(1L, "Margherita", Set.of(topping));
    }

    @Test
    void testGetAllPizzas() {
        List<Pizza> pizzas = new ArrayList<>();
        pizzas.add(pizza);
        when(pizzaRepository.findAll()).thenReturn(pizzas);

        List<Pizza> result = pizzaService.getAllPizzas();

        assertEquals(1, result.size());
        assertEquals("Margherita", result.get(0).getName());
        verify(pizzaRepository, times(1)).findAll();
    }

    @Test
    void testCreatePizza() {
        when(pizzaRepository.findByName("MARGHERITA")).thenReturn(Optional.empty());
        when(pizzaRepository.save(pizza)).thenReturn(pizza);
        when(toppingRepository.findById(topping.getId())).thenReturn(Optional.of(topping));

        Pizza result = pizzaService.createPizza(pizza);

        assertEquals(pizza, result);
        verify(pizzaRepository, times(1)).save(pizza);
    }

    @Test
    void testCreatePizzaAlreadyExists() {
        when(pizzaRepository.findByName("MARGHERITA")).thenReturn(Optional.of(pizza));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            pizzaService.createPizza(pizza);
        });

        assertEquals("Pizza already exists", exception.getMessage());
        verify(pizzaRepository, never()).save(any(Pizza.class));
    }

    @Test
    void testUpdatePizza() {
        when(pizzaRepository.findById(1L)).thenReturn(Optional.of(pizza));
        when(pizzaRepository.save(pizza)).thenReturn(pizza);
        when(toppingRepository.findById(topping.getId())).thenReturn(Optional.of(topping));

        Pizza result = pizzaService.updatePizza(1L, pizza);

        assertEquals(pizza, result);
        verify(pizzaRepository, times(1)).save(pizza);
    }

    @Test
    void testDeletePizza() {
        doNothing().when(pizzaRepository).deleteById(1L);

        pizzaService.deletePizza(1L);

        verify(pizzaRepository, times(1)).deleteById(1L);
    }
}
