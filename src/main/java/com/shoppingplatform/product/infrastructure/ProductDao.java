package com.shoppingplatform.product.infrastructure;

import com.shoppingplatform.product.infrastructure.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductDao extends JpaRepository<ProductEntity, UUID> {
}