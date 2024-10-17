package dev.jethayer.pizzastore.service;

import dev.jethayer.pizzastore.model.Topping;
import dev.jethayer.pizzastore.repository.ToppingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ToppingServiceTest {

    @Mock
    private ToppingRepository toppingRepository;

    @InjectMocks
    private ToppingService toppingService;

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
        when(toppingRepository.findAll()).thenReturn(toppings);

        List<Topping> result = toppingService.getAllToppings();

        assertEquals(1, result.size());
        assertEquals("Cheese", result.get(0).getName());
        verify(toppingRepository, times(1)).findAll();
    }

    @Test
    void testCreateTopping() {
        when(toppingRepository.findByName("cheese")).thenReturn(Optional.empty());
        when(toppingRepository.save(topping)).thenReturn(topping);

        Topping result = toppingService.createTopping(topping);

        assertEquals(topping, result);
        verify(toppingRepository, times(1)).save(topping);
    }

    @Test
    void testCreateToppingAlreadyExists() {
        when(toppingRepository.findByName("cheese")).thenReturn(Optional.of(topping));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            toppingService.createTopping(topping);
        });

        assertEquals("Topping already exists", exception.getMessage());
        verify(toppingRepository, never()).save(any(Topping.class));
    }

    @Test
    void testUpdateTopping() {
        when(toppingRepository.findById(1L)).thenReturn(Optional.of(topping));
        when(toppingRepository.save(topping)).thenReturn(topping);

        Topping result = toppingService.updateTopping(1L, topping);

        assertEquals(topping, result);
        verify(toppingRepository, times(1)).save(topping);
    }

    @Test
    void testDeleteTopping() {
        doNothing().when(toppingRepository).deleteById(1L);

        toppingService.deleteTopping(1L);

        verify(toppingRepository, times(1)).deleteById(1L);
    }
}
