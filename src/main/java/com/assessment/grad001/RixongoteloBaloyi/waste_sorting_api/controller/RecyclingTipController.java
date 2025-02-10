package com.assessment.grad001.RixongoteloBaloyi.waste_sorting_api.controller;

import com.assessment.grad001.RixongoteloBaloyi.waste_sorting_api.model.RecyclingTip;
import com.assessment.grad001.RixongoteloBaloyi.waste_sorting_api.service.RecyclingTipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import jakarta.validation.Valid;

/**
 * Controller class for handling requests related to recycling tips and waste categories.
 */
@RestController
@RequestMapping("/api/v1/recycling-tips")
@Validated
public class RecyclingTipController {

    private final RecyclingTipService recyclingTipService;

    @Autowired
    public RecyclingTipController(RecyclingTipService recyclingTipService) {
        this.recyclingTipService = recyclingTipService;
    }

    /**
     * Adds a new recycling tip.
     * @param recyclingTip The recycling tip to be added.
     * @param result Validation result.
     * @return ResponseEntity with the status of the operation.
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addRecyclingTip(@RequestBody @Valid RecyclingTip recyclingTip, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body("Invalid input data.");
        }
        return recyclingTipService.addRecyclingTip(recyclingTip);
    }

    /**
     * Gets all recycling tips.
     * @return ResponseEntity containing the list of all recycling tips.
     */
    @GetMapping
    public ResponseEntity<?> getAllRecyclingTips() {
        return recyclingTipService.getAllRecyclingTips();
    }

    /**
     * Gets a specific recycling tip by its ID.
     * @param id The ID of the recycling tip to retrieve.
     * @return ResponseEntity containing the recycling tip.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getRecyclingTipById(@PathVariable @Valid Long id) {
        return recyclingTipService.getRecyclingTipById(id);
    }

    /**
     * Updates an existing recycling tip.
     * @param id The ID of the recycling tip to update.
     * @param recyclingTip The updated recycling tip data.
     * @param result Validation result.
     * @return ResponseEntity with the status of the operation.
     */
    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateRecyclingTip(@PathVariable Long id, @RequestBody @Valid RecyclingTip recyclingTip, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body("Invalid input data.");
        }
        return recyclingTipService.updateRecyclingTip(id, recyclingTip);
    }

    /**
     * Deletes a recycling tip by its ID.
     * @param id The ID of the recycling tip to delete.
     * @return ResponseEntity with the status of the operation.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteRecyclingTip(@PathVariable Long id) {
        return recyclingTipService.deleteRecyclingTip(id);
    }

    /**
     * Retrieves recycling tips for a specific waste category.
     * Returns the waste category details along with a list of recycling tips.
     *
     * @param categoryId The ID of the waste category.
     * @return ResponseEntity with the waste category and recycling tips.
     */
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<?> getTipsByWasteCategory(@PathVariable Long categoryId) {
        return recyclingTipService.getTipsByWasteCategory(categoryId);
    }

    /**
     * Global exception handler for input validation errors.
     * @param ex Validation error exception.
     * @return ResponseEntity with validation error details.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return ResponseEntity.badRequest().body("Validation failed: " + ex.getBindingResult().getAllErrors().toString());
    }
}
