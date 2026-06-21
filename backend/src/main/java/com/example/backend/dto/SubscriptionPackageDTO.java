package com.example.backend.dto;

import java.math.BigDecimal;
import java.util.List;

import com.example.backend.enums.BillingCycle;

public class SubscriptionPackageDTO {

    private Long id;
    private String packagename;
    private String description;
    private BigDecimal price;
    private BillingCycle billingCycle;
    private List<PackageFeatureDTO> features;
    
    public SubscriptionPackageDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPackagename() {
        return packagename;
    }

    public void setPackagename(String packagename) {
        this.packagename = packagename;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BillingCycle getBillingCycle() {
        return billingCycle;
    }

    public void setBillingCycle(BillingCycle billingCycle) {
        this.billingCycle = billingCycle;
    }

    public List<PackageFeatureDTO> getFeatures() {
        return features;
    }

    public void setFeatures(List<PackageFeatureDTO> features) {
        this.features = features;
    }

    public SubscriptionPackageDTO(Long id, String packagename, String description, BigDecimal price,
            BillingCycle billingCycle, List<PackageFeatureDTO> features) {
        this.id = id;
        this.packagename = packagename;
        this.description = description;
        this.price = price;
        this.billingCycle = billingCycle;
        this.features = features;
    }

}
