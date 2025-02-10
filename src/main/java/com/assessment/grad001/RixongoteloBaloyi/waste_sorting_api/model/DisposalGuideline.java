package com.assessment.grad001.RixongoteloBaloyi.waste_sorting_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;



/**
 * Represents a disposal guideline for a specific waste category.
 * This entity contains instructions on how to properly dispose of different types of waste.
 * It is associated with a specific WasteCategory entity.
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class DisposalGuideline {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "waste_category_id", nullable = false)
    private WasteCategory wasteCategory;

    @NotNull(message = "Disposal guideline must not be null")
    @Size(min = 3, max = 255, message = "Disposal guideline must be between 3 and 255 characters")
    private String guideline;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WasteCategory getWasteCategory() {
        return wasteCategory;
    }

    public void setWasteCategory(WasteCategory wasteCategory) {
        this.wasteCategory = wasteCategory;
    }

    public String getGuideline() {
        return guideline;
    }

    public void setGuideline(String guideline) {
        this.guideline = guideline;
    }
}
