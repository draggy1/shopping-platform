package com.shoppingplatform.product.service;

import com.shoppingplatform.product.model.Price;
import com.shoppingplatform.product.model.Product;

import static java.math.BigDecimal.valueOf;
import static java.math.RoundingMode.HALF_DOWN;
import static java.util.Currency.getInstance;
import static java.util.UUID.randomUUID;

public class TestFactoryMethods {
    public static Product product(int amount, Price price) {
        return new Product(
                randomUUID(),
                amount,
                price
        );
    }

    public static Price price(Double price) {
        return new Price(valueOf(price).setScale(2, HALF_DOWN), getInstance("USD"));
    }
}