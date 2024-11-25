package com.shoppingplatform.product.controller;

import com.shoppingplatform.product.model.DiscountType;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@TestPropertySource(properties = {
        "discount.percentage.10=0.1",
        "discount.amount.10=2.0",
})
class ProductControllerIntegrationTest {

    @Container
    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:17")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass")
            .withInitScript("data.sql");

    @LocalServerPort
    private int port;
    private final UUID productId = UUID.fromString("86b4efdd-5b10-4bfc-a5d8-83f59f8cd745");

    @BeforeEach
    void setup() {
        RestAssured.port = port;
    }

    @DynamicPropertySource
    static void configurePostgres(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresContainer::getUsername);
        registry.add("spring.datasource.password", postgresContainer::getPassword);
        registry.add("spring.datasource.driver-class-name", postgresContainer::getDriverClassName);
    }

    @Test
    void shouldReturnProductById() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/products/{productId}", productId)
                .then()
                .statusCode(200)
                .body("id", equalTo(productId.toString()))
                .body("amount", equalTo(1))
                .body("price", equalTo(10.0F));
    }

    @Test
    void shouldApplyDiscountToProduct() {
        UUID productIdToDiscount = UUID.fromString("90f850e9-496c-429d-9d70-36bb1951d678");
        DiscountType discountType = DiscountType.PERCENTAGE;

        given()
                .contentType(ContentType.JSON)
                .when()
                .post("/products/{productId}/apply-discount/{discountType}", productIdToDiscount, discountType)
                .then()
                .statusCode(200)
                .body("id", equalTo(productIdToDiscount.toString()))
                .body("amount", equalTo(10))
                .body("price", equalTo(90.00f));
    }

    @Test
    void shouldReturnNotFoundWhenProductDoesNotExist() {
        UUID notExistingProductId = UUID.fromString("21297543-24b5-4f6c-ab0f-a325c1edbbe9");

        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/products/{productId}", notExistingProductId)
                .then()
                .statusCode(NOT_FOUND.value());
    }

    @Test
    void shouldReturnBadRequestWhenProductIdNotUUID() {
        String productIdNotUUID = "Some string";

        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/products/{productId}", productIdNotUUID)
                .then()
                .statusCode(BAD_REQUEST.value());
    }
}