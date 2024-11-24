package com.shoppingplatform.product.service;

import com.shoppingplatform.product.model.Product;

import java.math.BigDecimal;

import static java.math.BigDecimal.valueOf;
import static java.math.RoundingMode.HALF_DOWN;
import static java.util.UUID.randomUUID;

public class TestFactoryMethods {
    public static Product product(int amount, BigDecimal price) {
        return new Product(randomUUID(), amount, price.setScale(2, HALF_DOWN));
    }

    public static BigDecimal price(Double price) {
        return valueOf(price).setScale(2, HALF_DOWN);
    }
}