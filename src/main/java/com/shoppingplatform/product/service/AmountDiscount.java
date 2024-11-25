package com.shoppingplatform.product.service;

import com.shoppingplatform.product.model.Product;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.TreeMap;

import static java.math.BigDecimal.ZERO;

@Service
public final class AmountDiscount implements DiscountStrategy {

    private final PercentageDiscount percentageDiscount;
    private final TreeMap<Integer, BigDecimal> amountDiscountMap;

    public AmountDiscount(PercentageDiscount percentageDiscount,
                          @Qualifier("getAmountDiscountMap")
                          TreeMap<Integer, BigDecimal> amountDiscountMap) {
        this.percentageDiscount = percentageDiscount;
        this.amountDiscountMap = amountDiscountMap;
    }


    @Override
    public BigDecimal calculateNewPrice(Product product) {
        BigDecimal discount = getAmountDiscount(product.amount());
        return isPriceGreaterThenDiscount(product.getPriceValue(), discount) ?
                product.getPriceValue().subtract(discount) :
                percentageDiscount.calculateNewPrice(product);
    }

    private static boolean isPriceGreaterThenDiscount(BigDecimal price, BigDecimal discount) {
        return price.compareTo(discount) > 0;
    }

    private BigDecimal getAmountDiscount(int productAmount) {
        Integer discountAmount = amountDiscountMap.floorKey(productAmount);
        return discountAmount != null ?
                amountDiscountMap.getOrDefault(discountAmount, ZERO) :
                ZERO;
    }
}