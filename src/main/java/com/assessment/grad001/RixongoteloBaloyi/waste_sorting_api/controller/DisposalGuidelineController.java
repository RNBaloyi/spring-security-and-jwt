package com.assessment.grad001.RixongoteloBaloyi.waste_sorting_api.controller;

import com.assessment.grad001.RixongoteloBaloyi.waste_sorting_api.model.DisposalGuideline;
import com.assessment.grad001.RixongoteloBaloyi.waste_sorting_api.service.DisposalGuidelineService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for handling disposal guideline-related operations.
 */
@RestController
@RequestMapping("/api/v1/disposal-guidelines")
@Validated
public class DisposalGuidelineController {

    private final DisposalGuidelineService disposalGuidelineService;

    public DisposalGuidelineController(DisposalGuidelineService disposalGuidelineService) {
        this.disposalGuidelineService = disposalGuidelineService;
    }

    /**
     * Adds a new disposal guideline.
     * @param disposalGuideline The disposal guideline to be added.
     * @return ResponseEntity with the status of the operation.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> addDisposalGuideline(@RequestBody @Valid DisposalGuideline disposalGuideline) {
        return disposalGuidelineService.addDisposalGuideline(disposalGuideline);
    }

    /**
     * Retrieves all disposal guidelines.
     * @return ResponseEntity containing the list of all disposal guidelines.
     */
    @GetMapping
    public ResponseEntity<?> getAllDisposalGuidelines() {
        return disposalGuidelineService.getAllDisposalGuidelines();
    }

    /**
     * Retrieves a specific disposal guideline by its ID.
     * @param id The ID of the disposal guideline to retrieve.
     * @return ResponseEntity containing the disposal guideline.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getDisposalGuidelineById(@PathVariable Long id) {
        return disposalGuidelineService.getDisposalGuidelineById(id);
    }

    /**
     * Updates an existing disposal guideline.
     * @param id The ID of the disposal guideline to update.
     * @param disposalGuideline The updated disposal guideline data.
     * @return ResponseEntity with the status of the operation.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateDisposalGuideline(@PathVariable Long id, @RequestBody  @Valid DisposalGuideline disposalGuideline) {
        return disposalGuidelineService.updateDisposalGuideline(id, disposalGuideline);
    }

    /**
     * Deletes a disposal guideline by its ID.
     * @param id The ID of the disposal guideline to delete.
     * @return ResponseEntity with the status of the operation.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDisposalGuideline(@PathVariable Long id) {
        return disposalGuidelineService.deleteDisposalGuideline(id);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<?> getGuidelinesByCategory(@PathVariable Long categoryId) {
        return disposalGuidelineService.getGuidelinesByWasteCategory(categoryId);
    }

}
