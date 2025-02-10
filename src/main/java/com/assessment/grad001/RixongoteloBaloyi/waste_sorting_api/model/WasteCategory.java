package com.assessment.grad001.RixongoteloBaloyi.waste_sorting_api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;



/**
 * Represents a waste category in the system.
 * This entity stores the different types of waste (e.g., Plastic, Paper, Organic).
 * It includes a name for the category and a description for additional details.
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor

public class WasteCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Category name must not be null")
    @Size(min = 3, max = 50, message = "Category name must be between 3 and 50 characters")
    private String name;

    private String description;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
