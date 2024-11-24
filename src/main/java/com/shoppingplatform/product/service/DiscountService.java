package com.shoppingplatform.product.service;

import com.shoppingplatform.product.model.DiscountType;
import com.shoppingplatform.product.model.Product;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Service
public class DiscountService {

    private final DiscountStrategyFactory strategyFactory;
    private final ProductService productService;

    public DiscountService(DiscountStrategyFactory strategyFactory, ProductService productService) {
        this.strategyFactory = strategyFactory;
        this.productService = productService;
    }

    public Optional<Product> applyDiscountToProduct(UUID productId, DiscountType discountType) {
        DiscountStrategy strategy = strategyFactory.getStrategy(discountType);
        return productService.getProductById(productId)
                .map(product -> applyDiscountToProduct(product, strategy));
    }

    private Product applyDiscountToProduct(Product product, DiscountStrategy strategy) {
        BigDecimal newPrice = strategy.calculateNewPrice(product);
        return product.applyNewPrice(newPrice);
    }
}