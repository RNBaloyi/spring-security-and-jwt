package com.assessment.grad001.RixongoteloBaloyi.waste_sorting_api.service;

import com.assessment.grad001.RixongoteloBaloyi.waste_sorting_api.exception.CategoryNotFoundException;
import com.assessment.grad001.RixongoteloBaloyi.waste_sorting_api.model.WasteCategory;
import com.assessment.grad001.RixongoteloBaloyi.waste_sorting_api.repo.DisposalGuidelineRepository;
import com.assessment.grad001.RixongoteloBaloyi.waste_sorting_api.repo.RecyclingTipRepository;
import com.assessment.grad001.RixongoteloBaloyi.waste_sorting_api.repo.WasteCategoryRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Implementation of the WasteCategoryService.
 * Provides CRUD operations for managing waste categories.
 */
@Service
public class WasteCategoryServiceImpl implements WasteCategoryService {

    private final WasteCategoryRepository wasteCategoryRepository;
    private final DisposalGuidelineRepository disposalGuidelineRepository;
    private final RecyclingTipRepository recyclingTipRepository;
    Logger logger = LoggerFactory.getLogger(getClass());


    @Autowired
    public WasteCategoryServiceImpl(WasteCategoryRepository wasteCategoryRepository, DisposalGuidelineRepository disposalGuidelineRepository, RecyclingTipRepository recyclingTipRepository) {
        this.wasteCategoryRepository = wasteCategoryRepository;
        this.disposalGuidelineRepository = disposalGuidelineRepository;
        this.recyclingTipRepository = recyclingTipRepository;
    }

    /**
     * Retrieves all waste categories.
     * @return ResponseEntity containing list of waste categories.
     */
    @Override
    public ResponseEntity<?> getAllCategories() {
        List<WasteCategory> categories = wasteCategoryRepository.findAll();
        if (categories.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No categories found");
        }
        return ResponseEntity.ok(categories);
    }

    /**
     * Retrieves a specific waste category by ID.
     * @param id The ID of the waste category.
     * @return ResponseEntity containing the waste category or a 404 if not found.
     */
    @Override
    public ResponseEntity<?> getCategoryById(Long id) {
        WasteCategory category = wasteCategoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + id));
        return ResponseEntity.ok(category);
    }

    /**
     * Adds a new waste category to the system.
     * @param wasteCategory The category to be added.
     * @return ResponseEntity with the status of the operation.
     */
    @Override
    public ResponseEntity<?> addCategory(WasteCategory wasteCategory) {
        if (wasteCategory.getName() == null || wasteCategory.getName().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Category name must not be empty");
        }

        if (wasteCategory.getDescription() == null || wasteCategory.getDescription().isEmpty()) {
            wasteCategory.setDescription(getPredefinedDescription(wasteCategory.getName()));
        }

        wasteCategoryRepository.save(wasteCategory);
        return ResponseEntity.status(HttpStatus.CREATED).body("Category added successfully");
    }


    /**
     * Updates an existing waste category based on the given category ID and partial category data.
     * Only non-null values for name and description are updated.
     * @param categoryId The ID of the category to update.
     * @param wasteCategory The waste category object containing the updated fields.
     * @return ResponseEntity with the status of the operation.
     */
    @Override
    public ResponseEntity<?> updateCategory(Long categoryId, WasteCategory wasteCategory) {
        WasteCategory existingCategory = wasteCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + categoryId));

        if (wasteCategory.getName() != null && !wasteCategory.getName().isEmpty()) {
            existingCategory.setName(wasteCategory.getName());
        }

        if (wasteCategory.getDescription() != null && !wasteCategory.getDescription().isEmpty()) {
            existingCategory.setDescription(wasteCategory.getDescription());
        } else {
            existingCategory.setDescription(getPredefinedDescription(existingCategory.getName()));
        }

        wasteCategoryRepository.save(existingCategory);
        return ResponseEntity.status(HttpStatus.OK).body("Category updated successfully");
    }



    /**
     * Deletes a waste category and its associated disposal guidelines and recycling tips.
     * This method first attempts to delete any related entities (DisposalGuideline and RecyclingTip) linked
     * to the waste category. If any of the deletions fail, the transaction is rolled back, and no data is
     * deleted to maintain referential integrity. After deleting the related entities, the waste category is
     * deleted. If any part of the process fails, an error message is returned.
     *
     * @param id The ID of the waste category to delete.
     * @return ResponseEntity containing the status of the operation.
     * @throws CategoryNotFoundException If no category is found with the provided ID.
     */
    @Transactional
    @Override
    public ResponseEntity<?> deleteCategory(Long id) {
        logger.info("Attempting to delete category with ID: {}", id);

        WasteCategory existingCategory = wasteCategoryRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Category not found with ID: {}", id);
                    return new CategoryNotFoundException("Category not found with id: " + id);
                });

        // Deleting related entities (DisposalGuidelines and RecyclingTips)
        ResponseEntity<?> deleteRelatedEntitiesResponse = deleteRelatedEntities(id);
        if (deleteRelatedEntitiesResponse.getStatusCode() != HttpStatus.OK) {
            return deleteRelatedEntitiesResponse;
        }

        return deleteWasteCategory(id);
    }


    /**
     * A helper method to get predefined descriptions for specific categories.
     *
     * @param categoryName The name of the category.
     * @return The description for the category.
     */
    private String getPredefinedDescription(String categoryName) {
        return switch (categoryName) {
            case "Plastic" -> "Plastic waste includes bottles, bags, and containers. Ensure you clean and sort before recycling.";
            case "Paper" -> "Paper waste includes newspapers, magazines, and office papers. Please keep it dry and uncontaminated.";
            case "Glass" -> "Glass waste includes bottles, jars, and other glass containers. Clean thoroughly before recycling.";
            case "Metal" -> "Metal waste includes aluminum cans, tins, and other metal items. Clean and sort before recycling.";
            default -> "This is a waste category. Please follow general waste disposal guidelines.";
        };
    }


    /**
     * Helper method to delete related entities (DisposalGuidelines and RecyclingTips).
     * It handles exceptions and returns appropriate responses based on success or failure.
     */
    private ResponseEntity<?> deleteRelatedEntities(Long id) {
        try {
            logger.info("Deleting related DisposalGuidelines and RecyclingTips for category ID: {}", id);
            disposalGuidelineRepository.deleteByWasteCategoryId(id);
            recyclingTipRepository.deleteByWasteCategoryId(id);
            logger.info("Related entities for category ID: {} deleted successfully.", id);
            return ResponseEntity.ok("Related entities deleted successfully.");
        } catch (ConstraintViolationException ex) {
            logger.error("Constraint violation while deleting related entities for category ID: {}. Error: {}", id, ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Failed to delete related entities due to constraint violation: " + ex.getMessage());
        } catch (Exception ex) {
            logger.error("Failed to delete related entities for category ID: {}. Error: {}", id, ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete related entities: " + ex.getMessage());
        }
    }

    /**
     * Helper method to delete the WasteCategory.
     * It handles exceptions and returns appropriate responses based on success or failure.
     */
    private ResponseEntity<?> deleteWasteCategory(Long id) {
        try {
            logger.info("Deleting WasteCategory with ID: {}", id);
            wasteCategoryRepository.deleteById(id);
            logger.info("WasteCategory with ID: {} deleted successfully.", id);
            return ResponseEntity.ok("Category and associated guidelines/tips deleted successfully.");
        } catch (ConstraintViolationException ex) {
            logger.error("Constraint violation while deleting WasteCategory with ID: {}. Error: {}", id, ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Failed to delete category due to constraint violation: " + ex.getMessage());
        } catch (Exception ex) {
            logger.error("Failed to delete WasteCategory with ID: {}. Error: {}", id, ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete category: " + ex.getMessage());
        }
    }

}
