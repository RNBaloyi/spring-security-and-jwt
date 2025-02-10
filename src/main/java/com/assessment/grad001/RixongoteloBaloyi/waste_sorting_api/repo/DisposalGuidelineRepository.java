package com.assessment.grad001.RixongoteloBaloyi.waste_sorting_api.repo;

import com.assessment.grad001.RixongoteloBaloyi.waste_sorting_api.model.DisposalGuideline;
import com.assessment.grad001.RixongoteloBaloyi.waste_sorting_api.model.WasteCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DisposalGuidelineRepository extends JpaRepository<DisposalGuideline, Long> {

    List<DisposalGuideline> findByWasteCategory(WasteCategory wasteCategory);

    void deleteByWasteCategoryId(Long id);
}
