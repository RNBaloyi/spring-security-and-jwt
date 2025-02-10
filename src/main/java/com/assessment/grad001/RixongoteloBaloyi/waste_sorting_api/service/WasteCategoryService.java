package com.assessment.grad001.RixongoteloBaloyi.waste_sorting_api.service;

import com.assessment.grad001.RixongoteloBaloyi.waste_sorting_api.model.WasteCategory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Service interface for managing waste categories.
 * This interface provides methods to handle CRUD operations for waste categories.
 */
@Service
public interface WasteCategoryService {

    /**
     * Retrieves all waste categories.
     * @return ResponseEntity containing a list of all waste categories.
     */
    ResponseEntity<?> getAllCategories();

    /**
     * Retrieves a specific waste category by its ID.
     * @param id The ID of the waste category to retrieve.
     * @return ResponseEntity containing the waste category.
     */
    ResponseEntity<?> getCategoryById(Long id);

    /**
     * Adds a new waste category.
     * @param wasteCategory The waste category to add.
     * @return ResponseEntity with the status of the operation.
     */
    ResponseEntity<?> addCategory(WasteCategory wasteCategory);

    /**
     * Updates an existing waste category based on the given ID.
     * @param categoryId The ID of the category to update.
     * @param wasteCategory The waste category object containing updated information.
     * @return ResponseEntity with the status of the update operation.
     */
    ResponseEntity<?> updateCategory(Long categoryId, WasteCategory wasteCategory);

    /**
     * Deletes a waste category by its ID.
     * @param id The ID of the waste category to delete.
     * @return ResponseEntity with the status of the deletion operation.
     */
    ResponseEntity<?> deleteCategory(Long id);
}
