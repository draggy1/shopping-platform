package com.shoppingplatform.product.service;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.util.TreeMap;

@Validated
@Component
@ConfigurationProperties(prefix = "discount")
public class DiscountConfig {

    @NotEmpty(message = "Not provided percentage discount configuration")
    private TreeMap<Integer, BigDecimal> percentage;
    @NotEmpty(message = "Not provided amount discount configuration")
    private TreeMap<Integer, BigDecimal> amount;

    public TreeMap<Integer, BigDecimal> getPercentage() {
        return percentage;
    }

    public void setPercentage(TreeMap<Integer, BigDecimal> percentage) {
        this.percentage = percentage;
    }

    public TreeMap<Integer, BigDecimal> getAmount() {
        return amount;
    }

    public void setAmount(TreeMap<Integer, BigDecimal> amount) {
        this.amount = amount;
    }
}