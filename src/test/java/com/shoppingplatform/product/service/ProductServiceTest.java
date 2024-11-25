package com.shoppingplatform.product.service;

import com.shoppingplatform.product.infrastructure.ProductDao;
import com.shoppingplatform.product.infrastructure.entity.ProductEntity;
import com.shoppingplatform.product.model.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static java.math.BigDecimal.ONE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    private static final int TWO = 2;

    @Mock
    private ProductDao productDao;

    @InjectMocks
    private ProductService productService;

    @Test
    void shouldGetProductById() {
        //given
        UUID productId = UUID.fromString("efa591fe-1805-46d6-8cbc-5ba0567ba834");
        when(productDao.findById(productId)).thenReturn(Optional.of(new ProductEntity(productId, TWO, ONE)));

        //when
        Optional<Product> actual = productService.getProductById(productId);

        //then
        assertThat(actual).contains(new Product(productId, TWO, ONE));
    }

    @Test
    void shouldNotFoundProductById() {
        //given
        UUID productId = UUID.fromString("21297543-24b5-4f6c-ab0f-a325c1edbbe9");
        when(productDao.findById(productId)).thenReturn(Optional.empty());

        //when
        Optional<Product> actual = productService.getProductById(productId);

        //then
        assertThat(actual).isEmpty();
    }
}