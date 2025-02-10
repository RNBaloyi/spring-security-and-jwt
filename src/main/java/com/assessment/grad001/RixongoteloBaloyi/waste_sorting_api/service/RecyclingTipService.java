package com.assessment.grad001.RixongoteloBaloyi.waste_sorting_api.service;

import com.assessment.grad001.RixongoteloBaloyi.waste_sorting_api.model.RecyclingTip;
import org.springframework.http.ResponseEntity;

/**
 * Service interface for handling operations related to recycling tips.
 */
public interface RecyclingTipService {

    /**
     * Adds a new recycling tip.
     * @param recyclingTip The recycling tip to be added.
     * @return ResponseEntity with the status of the operation.
     */
    ResponseEntity<?> addRecyclingTip(RecyclingTip recyclingTip);

    /**
     * Gets all recycling tips.
     * @return ResponseEntity containing the list of all recycling tips.
     */
    ResponseEntity<?> getAllRecyclingTips();

    /**
     * Gets a specific recycling tip by its ID.
     * @param id The ID of the recycling tip to retrieve.
     * @return ResponseEntity containing the recycling tip.
     */
    ResponseEntity<?> getRecyclingTipById(Long id);

    /**
     * Updates an existing recycling tip.
     * @param id The ID of the recycling tip to update.
     * @param recyclingTip The updated recycling tip data.
     * @return ResponseEntity with the status of the operation.
     */
    ResponseEntity<?> updateRecyclingTip(Long id, RecyclingTip recyclingTip);

    /**
     * Deletes a recycling tip by its ID.
     * @param id The ID of the recycling tip to delete.
     * @return ResponseEntity with the status of the operation.
     */
    ResponseEntity<?> deleteRecyclingTip(Long id);

    /**
     * Gets recycling tips by waste category ID.
     * @param categoryId The ID of the waste category.
     * @return ResponseEntity containing the list of recycling tips for the specified category.
     */
    ResponseEntity<?> getTipsByWasteCategory(Long categoryId);
}
