package com.shoppingplatform.product.model;

import java.math.BigDecimal;
import java.util.UUID;

public record Product(UUID id, int amount, BigDecimal price) {

    public Product applyNewPrice(BigDecimal newPrice) {
        return new Product(id, amount, newPrice);
    }
}
