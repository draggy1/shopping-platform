package com.shoppingplatform.product.model;

import java.math.BigDecimal;
import java.util.UUID;

public record Product(UUID id, int amount, Price price) {

    public Product applyNewPrice(BigDecimal newPrice) {
        return new Product(id, amount, new Price(newPrice, price.currency()));
    }

    public BigDecimal getPriceValue() {
        return price.priceValue();
    }
}
