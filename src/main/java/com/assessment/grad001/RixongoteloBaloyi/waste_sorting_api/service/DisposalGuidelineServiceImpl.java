package com.assessment.grad001.RixongoteloBaloyi.waste_sorting_api.service;

import com.assessment.grad001.RixongoteloBaloyi.waste_sorting_api.dto.DisposalGuidelineDTO;
import com.assessment.grad001.RixongoteloBaloyi.waste_sorting_api.exception.CategoryNotFoundException;
import com.assessment.grad001.RixongoteloBaloyi.waste_sorting_api.exception.ResourceNotFoundException;
import com.assessment.grad001.RixongoteloBaloyi.waste_sorting_api.model.DisposalGuideline;
import com.assessment.grad001.RixongoteloBaloyi.waste_sorting_api.model.WasteCategory;
import com.assessment.grad001.RixongoteloBaloyi.waste_sorting_api.repo.DisposalGuidelineRepository;
import com.assessment.grad001.RixongoteloBaloyi.waste_sorting_api.repo.WasteCategoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Implementation of the DisposalGuidelineService interface.
 * Handles business logic for managing disposal guidelines.
 */
@Service
public class DisposalGuidelineServiceImpl implements DisposalGuidelineService {

    private final DisposalGuidelineRepository disposalGuidelineRepository;
    private final WasteCategoryRepository wasteCategoryRepository;

    public DisposalGuidelineServiceImpl(DisposalGuidelineRepository disposalGuidelineRepository,
                                        WasteCategoryRepository wasteCategoryRepository) {
        this.disposalGuidelineRepository = disposalGuidelineRepository;
        this.wasteCategoryRepository = wasteCategoryRepository;
    }

    /**
     * Adds a new disposal guideline to the system.
     * @param disposalGuideline The disposal guideline to be added.
     * @return ResponseEntity with the created disposal guideline.
     */
    @Override
    public ResponseEntity<?> addDisposalGuideline(DisposalGuideline disposalGuideline) {
        WasteCategory wasteCategory = wasteCategoryRepository.findById(disposalGuideline.getWasteCategory().getId())
                .orElseThrow(() -> new CategoryNotFoundException("Waste category not found"));
        disposalGuideline.setWasteCategory(wasteCategory);
        DisposalGuideline savedGuideline = disposalGuidelineRepository.save(disposalGuideline);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedGuideline);
    }

    /**
     * Retrieves all disposal guidelines.
     * @return ResponseEntity containing a list of all disposal guidelines.
     */
    @Override
    public ResponseEntity<?> getAllDisposalGuidelines() {
        List<DisposalGuideline> guidelines = disposalGuidelineRepository.findAll();
        return ResponseEntity.ok(guidelines);
    }

    /**
     * Retrieves a specific disposal guideline by ID.
     * @param id The ID of the disposal guideline.
     * @return ResponseEntity containing the requested disposal guideline.
     */
    @Override
    public ResponseEntity<?> getDisposalGuidelineById(Long id) {
        DisposalGuideline guideline = disposalGuidelineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Disposal guideline not found with id: " + id));
        return ResponseEntity.ok(guideline);
    }

    /**
     * Updates an existing disposal guideline.
     * @param id The ID of the disposal guideline to update.
     * @param disposalGuideline The updated disposal guideline data.
     * @return ResponseEntity with a success message.
     */
    @Override
    public ResponseEntity<?> updateDisposalGuideline(Long id, DisposalGuideline disposalGuideline) {
        DisposalGuideline existingGuideline = disposalGuidelineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Disposal guideline not found with id: " + id));

        if (disposalGuideline.getGuideline() != null && !disposalGuideline.getGuideline().isEmpty()) {
            existingGuideline.setGuideline(disposalGuideline.getGuideline());
        }

        if (disposalGuideline.getWasteCategory() != null) {
            WasteCategory wasteCategory = wasteCategoryRepository.findById(disposalGuideline.getWasteCategory().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Waste category not found"));
            existingGuideline.setWasteCategory(wasteCategory);
        }

        disposalGuidelineRepository.save(existingGuideline);
        return ResponseEntity.ok("Disposal guideline updated successfully");
    }

    /**
     * Deletes a disposal guideline by ID.
     * @param id The ID of the disposal guideline to delete.
     * @return ResponseEntity with a success message.
     */
    @Override
    public ResponseEntity<?> deleteDisposalGuideline(Long id) {
        DisposalGuideline guideline = disposalGuidelineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Disposal guideline not found with id: " + id));
        disposalGuidelineRepository.delete(guideline);
        return ResponseEntity.ok("Disposal guideline deleted successfully");
    }


    /**
     * Retrieves a list of disposal guidelines associated with a specific waste category.
     * @param categoryId The ID of the waste category.
     * @return ResponseEntity containing the waste category and its associated disposal guidelines.
     * @throws ResourceNotFoundException if the waste category with the specified ID is not found.
     */
    @Override
    public ResponseEntity<?> getGuidelinesByWasteCategory(Long categoryId) {
        WasteCategory wasteCategory = wasteCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Waste category not found with id: " + categoryId));

        List<DisposalGuideline> guidelines = disposalGuidelineRepository.findByWasteCategory(wasteCategory);

        List<DisposalGuidelineDTO> guidelineDTOs = guidelines.stream()
                .map(g -> new DisposalGuidelineDTO(g.getId(), g.getGuideline()))
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("wasteCategory", wasteCategory);
        response.put("guidelines", guidelineDTOs);

        return ResponseEntity.ok(response);
    }


}
