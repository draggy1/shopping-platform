package com.shoppingplatform.product.service;

import com.shoppingplatform.product.infrastructure.ProductDao;
import com.shoppingplatform.product.infrastructure.entity.ProductEntity;
import com.shoppingplatform.product.model.Price;
import com.shoppingplatform.product.model.Product;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {

    private final ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public Optional<Product> getProductById(UUID productId) {
        return productDao.findById(productId)
                .map(ProductService::fromEntity);
    }

    private static Product fromEntity(ProductEntity productEntity) {
        return new Product(
                productEntity.getId(),
                productEntity.getAmount(),
                new Price(productEntity.getPrice(), productEntity.getCurrency())
        );
    }
}