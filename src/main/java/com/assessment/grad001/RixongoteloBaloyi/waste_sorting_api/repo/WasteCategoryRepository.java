package com.assessment.grad001.RixongoteloBaloyi.waste_sorting_api.repo;

import com.assessment.grad001.RixongoteloBaloyi.waste_sorting_api.model.WasteCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WasteCategoryRepository extends JpaRepository<WasteCategory, Long> {

    boolean existsByName(String categoryName);
}
