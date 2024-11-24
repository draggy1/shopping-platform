package com.shoppingplatform.product.service;

import com.shoppingplatform.product.model.DiscountType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class DiscountStrategyFactoryTest {

    private Map<DiscountType, DiscountStrategy> givenDiscountTypeToStrategy;

    @BeforeEach
    void setUp() {
        givenDiscountTypeToStrategy = Map.of(
                DiscountType.AMOUNT, amountDiscount,
                DiscountType.PERCENTAGE, percentageDiscount
        );
    }

    @Mock
    private AmountDiscount amountDiscount;
    @Mock
    private PercentageDiscount percentageDiscount;
    @InjectMocks
    private DiscountStrategyFactory discountStrategyFactory;

    @ParameterizedTest
    @EnumSource(DiscountType.class)
    void shouldProvideCorrectDiscountStrategy(DiscountType discountType) {
        DiscountStrategy actual = discountStrategyFactory.getStrategy(discountType);
        DiscountStrategy discountStrategy = givenDiscountTypeToStrategy.get(discountType);
        assertThat(actual)
                .isInstanceOf(discountStrategy.getClass())
                .isSameAs(discountStrategy);
    }
}