package com.shoppingplatform.product.service;

import com.shoppingplatform.product.model.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static com.shoppingplatform.product.model.DiscountType.AMOUNT;
import static com.shoppingplatform.product.model.DiscountType.PERCENTAGE;
import static com.shoppingplatform.product.service.TestFactoryMethods.price;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DiscountServiceTest {

    private final UUID productId = UUID.randomUUID();
    private final Product product = new Product(productId, 4, price(4.99));

    @Mock
    private ProductService productService;

    @Mock
    private DiscountStrategyFactory discountStrategyFactory;

    @Mock
    private AmountDiscount amountDiscount;

    @Mock
    private PercentageDiscount percentageDiscount;

    @InjectMocks
    private DiscountService discountService;

    @Test
    void shouldApplyPercentageDiscountToProduct() {
        when(productService.getProductById(productId)).thenReturn(Optional.of(product));
        when(discountStrategyFactory.getStrategy(PERCENTAGE)).thenReturn(percentageDiscount);
        when(percentageDiscount.calculateNewPrice(product)).thenReturn(price(3.99));

        Optional<Product> actual = discountService.applyDiscountToProduct(productId, PERCENTAGE);
        assertThat(actual).contains(new Product(productId, 4, price(3.99)));
    }

    @Test
    void shouldApplyAmountDiscountToProduct() {
        when(productService.getProductById(productId)).thenReturn(Optional.of(product));
        when(discountStrategyFactory.getStrategy(AMOUNT)).thenReturn(amountDiscount);
        when(amountDiscount.calculateNewPrice(product)).thenReturn(price(2.99));

        Optional<Product> actual = discountService.applyDiscountToProduct(productId, AMOUNT);
        assertThat(actual).contains(new Product(productId, 4, price(2.99)));
    }

    @Test
    void shouldHandleNotFoundProduct() {
        when(productService.getProductById(productId)).thenReturn(Optional.empty());
        verifyNoInteractions(discountStrategyFactory);

        Optional<Product> actual = discountService.applyDiscountToProduct(productId, AMOUNT);
        assertThat(actual).isEmpty();
    }
}