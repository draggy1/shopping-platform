package com.shoppingplatform.product.infrastructure;

import com.shoppingplatform.product.model.Product;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.*;

import static java.math.BigDecimal.valueOf;

//todo: replace this fake by real db
@Repository
public class ProductDaoInMemoryImpl implements ProductDao {

    private static final int ONE = 1;
    private static final int TWO = 2;
    private static final int TEN = 10;

    private final List<Product> products = List.of(
        new Product(getUuid("86b4efdd-5b10-4bfc-a5d8-83f59f8cd745"), ONE, BigDecimal.TEN),
        new Product(getUuid("efa591fe-1805-46d6-8cbc-5ba0567ba834"), TWO, BigDecimal.ONE),
        new Product(getUuid("90f850e9-496c-429d-9d70-36bb1951d678"), TEN, valueOf(100))
    );

    private static UUID getUuid(String uuid) {
        return UUID.fromString(uuid);
    }

    @Override
    public Optional<Product> getProductById(UUID id) {
        return products.stream()
                .filter(product -> product.id().equals(id))
                .findFirst();
    }
}