package com.shoppingplatform.product.service;

import com.shoppingplatform.product.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

class AmountDiscountTest {

    private AmountDiscount amountDiscount;

    @BeforeEach
    void setUp() {
        TreeMap<Integer, BigDecimal> amountDiscountMap = new TreeMap<>();
        amountDiscountMap.put(10, valueOf(8.55));
        amountDiscountMap.put(300, valueOf(19.15));
        amountDiscountMap.put(1000, valueOf(100.09));

        TreeMap<Integer, BigDecimal> percentageDiscountMap = new TreeMap<>();
        percentageDiscountMap.put(100, valueOf(0.1));

        amountDiscount = new AmountDiscount(new PercentageDiscount(percentageDiscountMap), amountDiscountMap);
    }

    @ParameterizedTest
    @MethodSource("givenProductToExpectedNewPrice")
    void shouldCalculateNewPrice(Product givenProduct, BigDecimal newPrice) {
        BigDecimal actual = amountDiscount.calculateNewPrice(givenProduct);
        assertThat(actual).isEqualTo(newPrice);
    }

    @Test
    void shouldFallbackToPercentageDiscountWhenPriceLessThenDiscount() {
        Product givenProduct = product(100, price(5.));
        BigDecimal newPrice = amountDiscount.calculateNewPrice(givenProduct);

        assertThat(newPrice).isEqualTo(price(4.5));
    }

    private static Stream<Arguments> givenProductToExpectedNewPrice() {
        return Stream.of(
                of(product(5, price(1.)), price(1.)),
                of(product(9, price(99.99)), price(99.99)),
                of(product(10, price(108.55)), price(100.00)),
                of(product(11, price(109.1)), price(100.55)),
                of(product(299, price(108.55)), price(100.)),
                of(product(300, price(119.15)), price(100.)),
                of(product(301, price(119.15)), price(100.)),
                of(product(999, price(119.15)), price(100.)),
                of(product(1000, price(200.09)), price(100.)),
                of(product(1001, price(200.09)), price(100.)),
                of(product(10000, price(200.09)), price(100.))
        );
    }
}