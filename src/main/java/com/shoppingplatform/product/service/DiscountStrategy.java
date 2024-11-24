package com.shoppingplatform.product.service;

import com.shoppingplatform.product.model.Product;

import java.math.BigDecimal;

public abstract sealed class DiscountStrategy permits AmountDiscount, PercentageDiscount {

    protected final DiscountConfig discountConfig;

    protected DiscountStrategy(DiscountConfig discountConfig) {
        this.discountConfig = discountConfig;
    }

    abstract BigDecimal calculateNewPrice(Product product);
}