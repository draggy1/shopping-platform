
# Shopping Platform

A simple RESTful application built with Spring Boot and containerized using Docker.

---

## Features

- RESTful API for managing products.
- Fully containerized with Docker.
- Easy setup using Docker Compose.

---

## Prerequisites

Before you start, ensure you have the following installed:

- [Docker](https://www.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/)

---

## How to Build and Run

### 1. Clone the repository
```bash
git clone https://github.com/draggy1/shopping-platform.git
cd shopping-platform
```

### 2. Build the application JAR
```bash
./gradlew build
```

### 3. Build and run the Docker containers
```bash
./gradlew dockerComposeUp
```

This will:
- Build the Docker image for the application.
- Start the containerized application on port `8080`.

### 4. Access the application
Visit the application in your browser or API client at:
```
http://localhost:8080
```

---

## API Documentation

### 1. **Get Product by ID**
Retrieve a product using its unique identifier.

**Request:**
```http
GET /products/{id}
```

**PathVariable:**
- `{id}`: A unique identifier for the product in UUID format. Example: `550e8400-e29b-41d4-a716-446655440000`.

**Example:**
```bash
curl -X GET http://localhost:8080/products/550e8400-e29b-41d4-a716-446655440000
```

**Response (Success):**

```json
{
  "status": "200",
  "message": "Product retrieved successfully",
  "data": {
    "id": "550e8400-e29b-41d4-a716-446655440000",
    "amount": 100,
    "price": {
      "priceValue": 10.00,
      "currency": "USD"
    }
  }
}
```

**Response (Error):**
If the product is not found:
```json
{
  "status": "404",
  "message": "Product not found",
  "data": null
}
```

---

### 2. **Apply Discount to a Product**
Apply a discount to a product.

**Request:**
```http
POST /products/{productId}/apply-discount/{discountType}
```

**PathVariables:**
- `{productId}`: A unique identifier for the product in UUID format. Example: `550e8400-e29b-41d4-a716-446655440000`.
- `{discountType}`: The type of discount to apply. Must be **case-sensitive** and provided in uppercase. Supported values are:
    - `PERCENTAGE`: A percentage-based discount.
    - `AMOUNT`: A fixed amount discount.

**Example:**
```bash
curl -X POST http://localhost:8080/products/550e8400-e29b-41d4-a716-446655440000/apply-discount/PERCENTAGE
```

**Response (Success):**

```json
{
  "status": 200,
  "message": "Discount applied successfully",
  "data": {
    "id": "550e8400-e29b-41d4-a716-446655440000",
    "amount": 100,
    "price": {
      "priceValue": 9.50,
      "currency": "USD"
    }
  }
}
```

**Response (Error):**
If the product is not found:
```json
{
  "status": "404",
  "message": "Product not found",
  "data": null
}
```


---

## Architectural Decision Records (ADR)

All important architectural and design decisions related to this project are documented as Architectural Decision Records (ADR).

The details regarding the design of the Product API can be found in the following ADR:
- [001: Product API Design](docks/adr/001-product-api.md)

You can navigate to the file for detailed explanations of the decisions made during the development of this project.

---

## How to Stop the Application

To stop and remove the containers, run:
```bash
./gradlew dockerComposeDown
```

---

## License

This project is licensed under the MIT License.

---

## Authors

- [Kamil Dragan](https://github.com/draggy1) - Initial work