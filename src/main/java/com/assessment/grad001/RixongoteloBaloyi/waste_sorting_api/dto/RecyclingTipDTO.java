package com.assessment.grad001.RixongoteloBaloyi.waste_sorting_api.dto;

import com.assessment.grad001.RixongoteloBaloyi.waste_sorting_api.model.RecyclingTip;

public class RecyclingTipDTO {

    private RecyclingTip recyclingTip;
    private String wasteCategory;

    public RecyclingTipDTO(RecyclingTip recyclingTip, String wasteCategory) {
        this.recyclingTip = recyclingTip;
        this.wasteCategory = wasteCategory;
    }

    public RecyclingTip getRecyclingTip() {
        return recyclingTip;
    }

    public void setRecyclingTip(RecyclingTip recyclingTip) {
        this.recyclingTip = recyclingTip;
    }

    public String getWasteCategory() {
        return wasteCategory;
    }

    public void setWasteCategory(String wasteCategory) {
        this.wasteCategory = wasteCategory;
    }
}
