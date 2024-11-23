package com.shoppingplatform.product;

import java.math.BigDecimal;
import java.util.UUID;

public record Product(UUID id, BigDecimal amount, BigDecimal price) {
}
