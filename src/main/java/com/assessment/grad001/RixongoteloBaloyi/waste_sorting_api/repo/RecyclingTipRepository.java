package com.assessment.grad001.RixongoteloBaloyi.waste_sorting_api.repo;

import com.assessment.grad001.RixongoteloBaloyi.waste_sorting_api.model.RecyclingTip;
import com.assessment.grad001.RixongoteloBaloyi.waste_sorting_api.model.WasteCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecyclingTipRepository extends JpaRepository<RecyclingTip, Long> {

    List<RecyclingTip> findByWasteCategory(WasteCategory wasteCategory);

    void deleteByWasteCategoryId(Long id);
}
