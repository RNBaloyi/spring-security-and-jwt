package com.assessment.grad001.RixongoteloBaloyi.waste_sorting_api.dto;

public class RecyclingTipGuidelineDTO {

    private Long id;
    private String tip;

    public RecyclingTipGuidelineDTO(Long id, String tip) {
        this.id = id;
        this.tip = tip;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }
}
