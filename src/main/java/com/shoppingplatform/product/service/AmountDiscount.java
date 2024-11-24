package com.shoppingplatform.product.service;

import com.shoppingplatform.product.model.Product;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static java.math.BigDecimal.ZERO;

@Service
public final class AmountDiscount extends DiscountStrategy {

    AmountDiscount(DiscountConfig discountConfig) {
        super(discountConfig);
    }

    @Override
    BigDecimal calculateNewPrice(Product product) {
        //todo: what happened when product.price < discount? - add validation
        BigDecimal discount = getAmountDiscount(product.amount());
        return product.price().subtract(discount);
    }

    private BigDecimal getAmountDiscount(int productAmount) {
        Integer discountAmount = discountConfig.getAmount().floorKey(productAmount);
        return discountAmount != null ?
                discountConfig.getAmount().getOrDefault(discountAmount, ZERO) :
                ZERO;
    }
}