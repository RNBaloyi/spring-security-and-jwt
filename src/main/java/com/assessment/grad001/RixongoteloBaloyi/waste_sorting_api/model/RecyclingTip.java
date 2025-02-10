package com.assessment.grad001.RixongoteloBaloyi.waste_sorting_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



/**
 * Represents a recycling tip for a specific waste category.
 * This entity stores suggestions and best practices on how to recycle waste effectively.
 * It is associated with a specific WasteCategory entity.
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class RecyclingTip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "waste_category_id", nullable = false)
    private WasteCategory wasteCategory;

    @NotNull(message = "Recycling tip must not be null")
    @Size(min = 3, max = 255, message = "Recycling tip must be between 3 and 255 characters")
    private String tip;


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

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }
}

