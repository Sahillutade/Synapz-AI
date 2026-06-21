package com.example.backend.dto;

public class PackageFeatureDTO {

    private Long id;
    private String featureName;

    public PackageFeatureDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFeatureName() {
        return featureName;
    }

    public void setFeatureName(String featureName) {
        this.featureName = featureName;
    }

    public PackageFeatureDTO(Long id, String featureName) {
        this.id = id;
        this.featureName = featureName;
    }

}
