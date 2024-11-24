package com.shoppingplatform.product.controller;

import com.shoppingplatform.product.model.DiscountType;
import com.shoppingplatform.product.model.Product;
import com.shoppingplatform.product.service.DiscountService;
import com.shoppingplatform.product.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

//todo: add better error handling
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final DiscountService discountService;

    public ProductController(ProductService productService, DiscountService discountService) {
        this.productService = productService;
        this.discountService = discountService;
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable UUID productId) {
        return productService.getProductById(productId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("{productId}/apply-discount/{discountType}")
    public ResponseEntity<Product> applyDiscount(@PathVariable DiscountType discountType, @PathVariable UUID productId) {
        return discountService.applyDiscountToProduct(productId, discountType)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}