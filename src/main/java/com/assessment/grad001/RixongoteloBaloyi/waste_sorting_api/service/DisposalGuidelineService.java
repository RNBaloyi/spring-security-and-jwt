package com.assessment.grad001.RixongoteloBaloyi.waste_sorting_api.service;

import com.assessment.grad001.RixongoteloBaloyi.waste_sorting_api.model.DisposalGuideline;
import org.springframework.http.ResponseEntity;

/**
 * Service interface for handling operations related to disposal guidelines.
 */
public interface DisposalGuidelineService {

    /**
     * Adds a new disposal guideline.
     * @param disposalGuideline The disposal guideline to be added.
     * @return ResponseEntity with the status of the operation.
     */
    ResponseEntity<?> addDisposalGuideline(DisposalGuideline disposalGuideline);

    /**
     * Gets all disposal guidelines.
     * @return ResponseEntity containing the list of all disposal guidelines.
     */
    ResponseEntity<?> getAllDisposalGuidelines();

    /**
     * Gets a specific disposal guideline by its ID.
     * @param id The ID of the disposal guideline to retrieve.
     * @return ResponseEntity containing the disposal guideline.
     */
    ResponseEntity<?> getDisposalGuidelineById(Long id);

    /**
     * Updates an existing disposal guideline.
     * @param id The ID of the disposal guideline to update.
     * @param disposalGuideline The updated disposal guideline data.
     * @return ResponseEntity with the status of the operation.
     */
    ResponseEntity<?> updateDisposalGuideline(Long id, DisposalGuideline disposalGuideline);

    /**
     * Deletes a disposal guideline by its ID.
     * @param id The ID of the disposal guideline to delete.
     * @return ResponseEntity with the status of the operation.
     */
    ResponseEntity<?> deleteDisposalGuideline(Long id);

    /**
     * Retrieves all disposal guidelines for a given waste category.
     * @param categoryId The ID of the waste category.
     * @return ResponseEntity containing the list of disposal guidelines.
     */
    ResponseEntity<?> getGuidelinesByWasteCategory(Long categoryId);

}

