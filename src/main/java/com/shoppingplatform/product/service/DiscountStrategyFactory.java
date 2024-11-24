package com.shoppingplatform.product.service;

import com.shoppingplatform.product.model.DiscountType;
import org.springframework.stereotype.Component;

@Component
public class DiscountStrategyFactory {

    private final PercentageDiscount percentageDiscount;
    private final AmountDiscount amountDiscount;

    public DiscountStrategyFactory(AmountDiscount amountDiscount, PercentageDiscount percentageDiscount) {
        this.amountDiscount = amountDiscount;
        this.percentageDiscount = percentageDiscount;
    }

    public DiscountStrategy getStrategy(DiscountType type) {
        return switch (type) {
            case PERCENTAGE -> percentageDiscount;
            case AMOUNT -> amountDiscount;
        };
    }
}