package com.shoppingplatform.product.model;

import org.junit.jupiter.api.Test;

import static com.shoppingplatform.product.service.TestFactoryMethods.price;
import static com.shoppingplatform.product.service.TestFactoryMethods.product;
import static org.assertj.core.api.Assertions.assertThat;

class ProductTest {

    @Test
    void shouldApplyNewPrice() {
        Product given = product(4, price(3.99));

        Product actual = given.applyNewPrice(price(5.55).priceValue());

        assertThat(actual).isEqualTo(new Product(given.id(), 4, price(5.55)));
    }
}