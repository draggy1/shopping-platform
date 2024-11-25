package com.shoppingplatform.product.model;

import java.math.BigDecimal;
import java.util.Currency;

public record Price(BigDecimal priceValue, Currency currency) {
}
