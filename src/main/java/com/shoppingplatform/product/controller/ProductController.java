package com.shoppingplatform.product.controller;

import com.shoppingplatform.product.model.DiscountType;
import com.shoppingplatform.product.model.Product;
import com.shoppingplatform.product.service.DiscountService;
import com.shoppingplatform.product.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.shoppingplatform.product.controller.ApiResponse.error;
import static com.shoppingplatform.product.controller.ApiResponse.success;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/products")
class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;
    private final DiscountService discountService;

    public ProductController(ProductService productService, DiscountService discountService) {
        this.productService = productService;
        this.discountService = discountService;
    }

    @GetMapping("/{productId}")
    ResponseEntity<ApiResponse<Product>> getProductById(@PathVariable UUID productId) {
        logger.info("Received request to get product with ID: {}", productId);

        return productService.getProductById(productId)
                .map(product -> {
                    logger.info("Product found: {}", product);
                    return ok(success("Product retrieved successfully", product));
                })
                .orElseGet(() -> {
                    logger.warn("Product with ID {} not found", productId);
                    return status(404).body(error(404, "Product not found"));
                });
    }

    @PostMapping("{productId}/apply-discount/{discountType}")
    ResponseEntity<ApiResponse<Product>> applyDiscount(@PathVariable DiscountType discountType,
                                                              @PathVariable UUID productId) {
        return discountService.applyDiscountToProduct(productId, discountType)
                .map(product -> {
                    logger.info("Discount applied successfully to product: {}", product);
                    return ok(success("Discount applied successfully", product));
                })
                .orElseGet(() -> {
                    logger.warn("Failed to apply discount of type {} to product ID: {}", discountType, productId);
                    return status(404).body(error(404, "Product not found"));
                });
    }
}