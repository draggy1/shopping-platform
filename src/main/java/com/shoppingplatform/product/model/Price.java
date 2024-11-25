package com.shoppingplatform.product.model;

import java.math.BigDecimal;
import java.util.Currency;

import static java.math.RoundingMode.HALF_UP;

public record Price(BigDecimal priceValue, Currency currency) {

    public Price(BigDecimal priceValue, Currency currency) {
        this.priceValue = priceValue.setScale(2, HALF_UP);
        this.currency = currency;
    }
}