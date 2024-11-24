package com.shoppingplatform.product.service;

import com.shoppingplatform.product.infrastructure.ProductDaoInMemoryImpl;
import com.shoppingplatform.product.model.Product;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static java.math.BigDecimal.ONE;
import static org.assertj.core.api.Assertions.assertThat;

class ProductServiceTest {
    private static final int TWO = 2;
    private final ProductService productService = new ProductService(new ProductDaoInMemoryImpl());

    @Test
    void shouldGetProductById() {
        //given
        UUID productId = UUID.fromString("efa591fe-1805-46d6-8cbc-5ba0567ba834");

        //when
        Optional<Product> actual = productService.getProductById(productId);

        //then
        assertThat(actual).contains(new Product(productId, TWO, ONE));
    }

    @Test
    void shouldNotFoundProductById() {
        //given
        UUID productId = UUID.fromString("21297543-24b5-4f6c-ab0f-a325c1edbbe9");

        //when
        Optional<Product> actual = productService.getProductById(productId);

        //then
        assertThat(actual).isEmpty();
    }
}