package com.shoppingplatform.product.config;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.util.TreeMap;

@Validated
@Configuration
@ConfigurationProperties(prefix = "discount")
public class DiscountConfig {

    @NotEmpty(message = "Not provided percentage discount configuration")
    private TreeMap<Integer, BigDecimal> percentage;
    @NotEmpty(message = "Not provided amount discount configuration")
    private TreeMap<Integer, BigDecimal> amount;

    @Bean
    public TreeMap<Integer, BigDecimal> getPercentageDiscountMap() {
        return percentage;
    }

    public void setPercentage(TreeMap<Integer, BigDecimal> percentage) {
        this.percentage = percentage;
    }

    @Bean
    public TreeMap<Integer, BigDecimal> getAmountDiscountMap() {
        return amount;
    }

    public void setAmount(TreeMap<Integer, BigDecimal> amount) {
        this.amount = amount;
    }
}