package com.shoppingplatform.product.service;

import com.shoppingplatform.product.model.Product;

import java.math.BigDecimal;

public sealed interface DiscountStrategy permits AmountDiscount, PercentageDiscount {

    BigDecimal calculateNewPrice(Product product);

}