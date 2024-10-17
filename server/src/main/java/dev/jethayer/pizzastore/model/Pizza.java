package dev.jethayer.pizzastore.model;

import lombok.*;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Entity representing a Pizza.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pizza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(
        name = "pizza_toppings",
        joinColumns = @JoinColumn(name = "pizza_id"),
        inverseJoinColumns = @JoinColumn(name = "topping_id")
    )
    private Set<Topping> toppings = new HashSet<>();

    /**
     * Sets the name of the pizza, converting it to uppercase.
     *
     * @param name the name of the pizza
     */
    public void setName(String name) {
        this.name = name.toUpperCase();
    }

    public Set<Topping> getToppings() {
        return toppings;
    }

    public void setToppings(Set<Topping> toppings) {
        this.toppings = toppings != null ? toppings : new HashSet<>();
    }
}
