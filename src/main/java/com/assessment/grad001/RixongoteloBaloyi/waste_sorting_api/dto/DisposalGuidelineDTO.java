package com.assessment.grad001.RixongoteloBaloyi.waste_sorting_api.dto;

public class DisposalGuidelineDTO {
    private Long id;
    private String guideline;


    public DisposalGuidelineDTO(Long id, String guideline) {
        this.id = id;
        this.guideline = guideline;
    }


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getGuideline() { return guideline; }
    public void setGuideline(String guideline) { this.guideline = guideline; }
}
