package com.shoppingplatform.product.infrastructure;

import com.shoppingplatform.product.model.Product;

import java.util.Optional;
import java.util.UUID;

public interface ProductDao {
    Optional<Product> getProductById(UUID id);
}