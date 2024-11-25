package com.shoppingplatform.product.service;

import com.shoppingplatform.product.model.Product;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.TreeMap;

import static java.math.RoundingMode.HALF_UP;

@Service
public final class PercentageDiscount implements DiscountStrategy {
    private final TreeMap<Integer, BigDecimal> percentageDiscountMap;

    public PercentageDiscount(@Qualifier("getPercentageDiscountMap")
                              TreeMap<Integer, BigDecimal> percentageDiscountMap) {
        this.percentageDiscountMap = percentageDiscountMap;
    }

    @Override
    public BigDecimal calculateNewPrice(Product product) {
        return getPercentageDiscount(product.amount())
                .map(percentage -> calculateNewPrice(percentage, product.getPriceValue()))
                .orElse(product.getPriceValue());
    }

    private static BigDecimal calculateNewPrice(BigDecimal percentage, BigDecimal price) {
        return price.subtract(percentage.multiply(price).setScale(2, HALF_UP));
    }

    private Optional<BigDecimal> getPercentageDiscount(int productAmount) {
        Integer discountAmount = percentageDiscountMap.floorKey(productAmount);
        return discountAmount != null ?
                Optional.ofNullable(percentageDiscountMap.get(discountAmount)) :
                Optional.empty();
    }
}
