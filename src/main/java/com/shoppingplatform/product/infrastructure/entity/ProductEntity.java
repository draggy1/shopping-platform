package com.shoppingplatform.product.infrastructure.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;

@Entity
@Table(name = "products")
public class ProductEntity {
    public ProductEntity() {
    }

    public ProductEntity(UUID id, int amount, BigDecimal price, Currency currency) {
        this.amount = amount;
        this.id = id;
        this.price = price;
        this.currency = currency;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private int amount;

    @Column(precision = 12, scale = 2, nullable = false)
    private BigDecimal price;

    @Convert(converter = CurrencyConverter.class)
    @Column(nullable = false, length = 3)
    private Currency currency;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}