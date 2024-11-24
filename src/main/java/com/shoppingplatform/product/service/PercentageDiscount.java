package com.shoppingplatform.product.service;

import com.shoppingplatform.product.model.Product;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.TreeMap;

import static java.math.RoundingMode.HALF_UP;

@Service
public final class PercentageDiscount extends DiscountStrategy {

    PercentageDiscount(DiscountConfig discountConfig) {
        super(discountConfig);
    }

    @Override
    public BigDecimal calculateNewPrice(Product product) {
        return getPercentageDiscount(product.amount())
                .map(percentage -> calculateNewPrice(percentage, product.price()))
                .orElse(product.price());
    }

    private static BigDecimal calculateNewPrice(BigDecimal percentage, BigDecimal price) {
        return price.subtract(percentage.multiply(price).setScale(2, HALF_UP));
    }

    private Optional<BigDecimal> getPercentageDiscount(int productAmount) {
        TreeMap<Integer, BigDecimal> amountToPercentageDiscountMap = discountConfig.getPercentage();
        Integer discountAmount = amountToPercentageDiscountMap.floorKey(productAmount);
        return discountAmount != null ?
                Optional.ofNullable(amountToPercentageDiscountMap.get(discountAmount)) :
                Optional.empty();
    }
}
