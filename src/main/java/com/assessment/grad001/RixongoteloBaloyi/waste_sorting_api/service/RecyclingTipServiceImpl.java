package com.assessment.grad001.RixongoteloBaloyi.waste_sorting_api.service;

import com.assessment.grad001.RixongoteloBaloyi.waste_sorting_api.dto.RecyclingTipGuidelineDTO;
import com.assessment.grad001.RixongoteloBaloyi.waste_sorting_api.dto.WasteCategoryDTO;
import com.assessment.grad001.RixongoteloBaloyi.waste_sorting_api.exception.ResourceNotFoundException;
import com.assessment.grad001.RixongoteloBaloyi.waste_sorting_api.model.RecyclingTip;
import com.assessment.grad001.RixongoteloBaloyi.waste_sorting_api.model.WasteCategory;
import com.assessment.grad001.RixongoteloBaloyi.waste_sorting_api.repo.RecyclingTipRepository;
import com.assessment.grad001.RixongoteloBaloyi.waste_sorting_api.repo.WasteCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RecyclingTipServiceImpl implements RecyclingTipService {

    private final RecyclingTipRepository recyclingTipRepository;
    private final WasteCategoryRepository wasteCategoryRepository;

    @Autowired
    public RecyclingTipServiceImpl(RecyclingTipRepository recyclingTipRepository, WasteCategoryRepository wasteCategoryRepository) {
        this.recyclingTipRepository = recyclingTipRepository;
        this.wasteCategoryRepository = wasteCategoryRepository;
    }

    /**
     * Adds a new recycling tip.
     * @param recyclingTip The recycling tip to be added.
     * @return ResponseEntity with the status of the operation.
     */
    @Override
    public ResponseEntity<?> addRecyclingTip(RecyclingTip recyclingTip) {
        try {
            if (recyclingTip.getWasteCategory() == null || recyclingTip.getWasteCategory().getId() == null) {
                return ResponseEntity.status(400).body("Waste category ID must be provided.");
            }

            Optional<WasteCategory> wasteCategory = wasteCategoryRepository.findById(recyclingTip.getWasteCategory().getId());
            if (wasteCategory.isEmpty()) {
                return ResponseEntity.status(404).body("Waste category not found.");
            }

            recyclingTip.setWasteCategory(wasteCategory.get());

            RecyclingTip savedTip = recyclingTipRepository.save(recyclingTip);
            return ResponseEntity.status(201).body(savedTip);

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error saving recycling tip: " + e.getMessage());
        }
    }

    /**
     * Gets all recycling tips.
     * @return ResponseEntity containing the list of all recycling tips.
     */
    @Override
    public ResponseEntity<?> getAllRecyclingTips() {
        try {
            List<RecyclingTip> recyclingTips = recyclingTipRepository.findAll();
            if (recyclingTips.isEmpty()) {
                return ResponseEntity.status(404).body("No recycling tips found.");
            }
            return ResponseEntity.ok(recyclingTips);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching recycling tips: " + e.getMessage());
        }
    }

    /**
     * Gets a specific recycling tip by its ID.
     * @param id The ID of the recycling tip to retrieve.
     * @return ResponseEntity containing the recycling tip or a message indicating not found.
     */
    @Override
    public ResponseEntity<?> getRecyclingTipById(Long id) {
        try {
            Optional<RecyclingTip> recyclingTip = recyclingTipRepository.findById(id);

            if (recyclingTip.isEmpty()) {
                return ResponseEntity.status(404).body("No recycling tip found with id: " + id);
            }

            return ResponseEntity.ok(recyclingTip.get());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching recycling tip: " + e.getMessage());
        }
    }

    /**
     * Updates an existing recycling tip.
     * @param id The ID of the recycling tip to update.
     * @param recyclingTip The updated recycling tip data.
     * @return ResponseEntity with the status of the operation.
     */
    @Override
    public ResponseEntity<?> updateRecyclingTip(Long id, RecyclingTip recyclingTip) {
        try {
            if (!recyclingTipRepository.existsById(id)) {
                return ResponseEntity.status(404).body("Recycling tip not found.");
            }
            recyclingTip.setId(id);
            RecyclingTip updatedTip = recyclingTipRepository.save(recyclingTip);
            return ResponseEntity.ok(updatedTip);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error updating recycling tip: " + e.getMessage());
        }
    }

    /**
     * Deletes a recycling tip by its ID.
     * @param id The ID of the recycling tip to delete.
     * @return ResponseEntity with the status of the operation.
     */
    @Override
    public ResponseEntity<?> deleteRecyclingTip(Long id) {
        try {
            if (!recyclingTipRepository.existsById(id)) {
                return ResponseEntity.status(404).body("Recycling tip not found.");
            }
            recyclingTipRepository.deleteById(id);
            return ResponseEntity.ok("Recycling tip deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error deleting recycling tip: " + e.getMessage());
        }
    }

    /**
     * Retrieves recycling tips for a specific waste category.
     * Returns the waste category details along with a list of recycling tips.
     *
     * @param categoryId The ID of the waste category.
     * @return ResponseEntity with the waste category and recycling tips.
     */
    @Override
    public ResponseEntity<?> getTipsByWasteCategory(Long categoryId) {
        try {
            WasteCategory wasteCategory = wasteCategoryRepository.findById(categoryId)
                    .orElseThrow(() -> new ResourceNotFoundException("Waste category not found with id: " + categoryId));

            List<RecyclingTip> recyclingTips = recyclingTipRepository.findByWasteCategory(wasteCategory);

            List<RecyclingTipGuidelineDTO> guidelineDTOs = recyclingTips.stream()
                    .map(tip -> new RecyclingTipGuidelineDTO(tip.getId(), tip.getTip()))
                    .collect(Collectors.toList());

            Map<String, Object> response = new HashMap<>();
            response.put("wasteCategory", new WasteCategoryDTO(
                    wasteCategory.getId(),
                    wasteCategory.getName(),
                    wasteCategory.getDescription())
            );
            response.put("guidelines", guidelineDTOs);

            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error retrieving tips for category: " + e.getMessage());
        }
    }
}

