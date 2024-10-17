package dev.jethayer.pizzastore.model;

import lombok.*;

import jakarta.persistence.*;

/**
 * Entity representing a Topping.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Topping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    /**
     * Sets the name of the topping, converting it to lowercase.
     *
     * @param name the name of the pizza
     */
    public void setName(String name) {
        this.name = name.toLowerCase();
    }
}
