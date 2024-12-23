package com.shoppingplatform.product.service;

import com.shoppingplatform.product.model.Price;
import com.shoppingplatform.product.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.TreeMap;
import java.util.stream.Stream;

import static com.shoppingplatform.product.service.TestFactoryMethods.price;
import static com.shoppingplatform.product.service.TestFactoryMethods.product;
import static java.math.BigDecimal.valueOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.of;

class PercentageDiscountTest {
    private PercentageDiscount percentageDiscount;

    @BeforeEach
    void setUp() {
        TreeMap<Integer, BigDecimal> percentageMap = new TreeMap<>();
        percentageMap.put(20, valueOf(0.05));
        percentageMap.put(200, valueOf(0.1));
        percentageMap.put(2000, valueOf(0.2));

        percentageDiscount = new PercentageDiscount(percentageMap);
    }

    @ParameterizedTest
    @MethodSource("percentageDiscountTestData")
    void shouldCalculateNewPrice(Product givenProduct, Price newPrice) {
        BigDecimal actual = percentageDiscount.calculateNewPrice(givenProduct);
        assertThat(actual).isEqualTo(newPrice.priceValue());
    }

    private static Stream<Arguments> percentageDiscountTestData() {
        return Stream.of(
                of(product(5, price(100.)), price(100.)),
                of(product(19, price(100.)), price(100.)),
                of(product(20, price(100.)), price(95.)),
                of(product(21, price(100.)), price(95.)),
                of(product(199, price(100.)), price(95.)),
                of(product(200, price(100.)), price(90.)),
                of(product(201, price(100.)), price(90.)),
                of(product(1999, price(100.)), price(90.)),
                of(product(2000, price(100.)), price(80.)),
                of(product(2001, price(100.)), price(80.)),
                of(product(10000, price(100.)), price(80.))
        );
    }
}