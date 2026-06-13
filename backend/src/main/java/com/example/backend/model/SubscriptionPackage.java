package com.example.backend.model;

import java.math.BigDecimal;
import java.util.List;

import com.example.backend.enums.BillingCycle;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "subscription_packages")
public class SubscriptionPackage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String packageName;

    private String description;

    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BillingCycle billingCycle;

    @OneToMany(mappedBy = "subscriptionPackage")
    private List<PackageFeature> features;

    public SubscriptionPackage() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
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

    public List<PackageFeature> getFeatures() {
        return features;
    }

    public void setFeatures(List<PackageFeature> features) {
        this.features = features;
    }

    public SubscriptionPackage(Long id, String packageName, String description, BigDecimal price, BillingCycle billingCycle,
            List<PackageFeature> features) {
        this.id = id;
        this.packageName = packageName;
        this.description = description;
        this.price = price;
        this.billingCycle = billingCycle;
        this.features = features;
    }

    public BillingCycle getBillingCycle() {
        return billingCycle;
    }

    public void setBillingCycle(BillingCycle billingCycle) {
        this.billingCycle = billingCycle;
    }

}
