package com.assessment.grad001.RixongoteloBaloyi.waste_sorting_api.controller;

import com.assessment.grad001.RixongoteloBaloyi.waste_sorting_api.model.WasteCategory;
import com.assessment.grad001.RixongoteloBaloyi.waste_sorting_api.service.WasteCategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * Controller for managing waste categories.
 * Exposes endpoints for CRUD operations.
 */
@RestController
@RequestMapping("/api/v1/waste-categories")
@Validated
public class WasteCategoryController {

    private final WasteCategoryService wasteCategoryService;

    @Autowired
    public WasteCategoryController(WasteCategoryService wasteCategoryService) {
        this.wasteCategoryService = wasteCategoryService;
    }

    /**
     * Endpoint to get all waste categories.
     * @return ResponseEntity containing list of waste categories.
     */
    @GetMapping
    public ResponseEntity<?> getAllCategories() {
        return wasteCategoryService.getAllCategories();
    }

    /**
     * Endpoint to get a specific waste category by ID.
     * @param id The ID of the waste category.
     * @return ResponseEntity containing the waste category.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable Long id) {
        return wasteCategoryService.getCategoryById(id);
    }

    /**
     * Endpoint to add a new waste category.
     * @param wasteCategory The category to be added.
     * @return ResponseEntity with the operation status.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> addCategory(@Valid @RequestBody WasteCategory wasteCategory) {
        return wasteCategoryService.addCategory(wasteCategory);
    }


    /**
     * Endpoint to partially update an existing waste category by category ID.
     * Only the provided non-null values for name and description are updated.
     * @param categoryId The ID of the category to update.
     * @param wasteCategory The waste category object containing the updated fields.
     * @return ResponseEntity with the status of the operation.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{categoryId}")
    public ResponseEntity<?> updateCategory(@PathVariable Long categoryId, @RequestBody WasteCategory wasteCategory) {
        return wasteCategoryService.updateCategory(categoryId, wasteCategory);
    }


    /**
     * Endpoint to delete an existing waste category by ID.
     * @param id The ID of the category to delete.
     * @return ResponseEntity with the status of the operation.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        return wasteCategoryService.deleteCategory(id);
    }
}
