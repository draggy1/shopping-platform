# ADR 001: Product API - Part of Shopping Platform

## Context

Product management API is part of the **shopping platform**. The API provides a REST interface for calculating a given product's price and amount. The API should apply discounts based on two policies:

- **Count-based:** The more pieces of the product are ordered, the bigger the discount is.
- **Percentage-based:** Discounts applied as a percentage of the product price.
- Policies should be **configurable**.

Additionally, given the current phase of the project, the scale is not expected to be large. Traffic to the API is anticipated to be low, and no high concurrency operations are required at this time.

---

## Decision

1. Discounts will be independent resources and not part of the product's persistent state.
2. The API will not implement multi-threading or non-blocking operations during the initial phase of the project.
3. Discount type will be provided dynamically in the API, while the configurable elements will include quantity ranges that determine the applicable discounts.
4. For `AMOUNT` discounts, if the discount exceeds the product price, a fallback to the `PERCENTAGE` discount type will be applied.

---

## Rationale

### Independent discounts as resources

- Decoupling discounts improves **reusability** and **scalability**.
- Simplifies the product model, adhering to the **single responsibility principle** (SRP).
- Enables dynamic and flexible application of discounts without persisting them as part of the product.
- Ensures modularity, allowing discounts to be reused across multiple products or categories.

### No multi-threading in the initial phase

- **Simplicity:** Reduces implementation complexity.
- **Focus:** Enables faster development of core features (discount logic and price calculations) without adding additional layers of complexity for thread management or concurrency.
- **Lack of immediate need:** The expected traffic is low, and operations are not compute-intensive.
- **Ease of maintenance:** Synchronous code is easier to debug, test, and understand.

### Dynamic discount type with configurable quantity ranges

- Allowing the discount type (`PERCENTAGE` or `AMOUNT`) to be provided dynamically in the API offers greater flexibility to clients.
- This decision avoids "cementing" a single discount policy at application start, ensuring that clients can choose discount types dynamically as needed during runtime.

### Fallback to `PERCENTAGE` discount if `AMOUNT` exceeds product price

- Ensures no negative pricing occurs in the system, maintaining data integrity.
- Provides a safe default behavior in edge cases where `AMOUNT` discounts would lead to invalid pricing scenarios.
- Enhances user experience by ensuring a valid discount is always applied, even if the primary configuration cannot be used.

---

## Consequences

### Independent discounts as resources

**Positive:**
- Modular and scalable API design.
- Reusable discount logic.
- No need to store discounts in the database.
- No additional issues when discounts are deleted or edited.

**Negative:**
- Requires additional requests to apply discounts (e.g., first retrieving the product and then applying a discount).

### No multi-threading in the initial phase

**Positive:**
- Simple and maintainable implementation.
- Faster delivery of the initial version.
- Easier debugging and testing.

**Negative:**
- Limited scalability for concurrent requests.
- Migration to a non-blocking or multi-threaded architecture in the future may require significant refactoring.
- Potential delays in response time under heavy traffic conditions.

### Dynamic discount type with configurable quantity ranges

**Positive:**
- Greater flexibility for clients to dynamically select discount policies.
- Configurable ranges provide fine control for administrators without locking into a rigid discounting strategy.
- Enhances adaptability of the API to changing requirements.

**Negative:**
- Requires additional validation logic in the API to ensure correct and safe application of discount ranges.

### Fallback to `PERCENTAGE` discount if `AMOUNT` exceeds product price

**Positive:**
- Prevents invalid or negative pricing.
- Ensures that a valid discount is always applied.
- Provides a better experience for clients and avoids unexpected behavior.

**Negative:**
- Adds additional fallback logic in the discount calculation process.
- Requires clear communication to API consumers about the fallback behavior to avoid confusion.

---

## Alternatives Considered

### Discounts stored in the product model
- **Rejected:** Leads to tight coupling of discounts and products, reducing flexibility.
- **Problems:**
  - Difficult to reuse discounts across multiple products or categories.
  - Increases the complexity of product management when discounts are modified or deleted.

### Implementing multi-threading from the start
- **Rejected:** Adds unnecessary complexity for the current scale of the project.
- Multi-threading or non-blocking operations would only be required if the API is expected to handle high concurrency or traffic.

### Fixed discount policy with configuration at startup
- **Rejected:** Limits flexibility for clients, as the discount policy would be "frozen" during runtime.
- Does not align with the dynamic needs of the shopping platform, where clients may require different discount policies for various products or use cases.

---

## Next Steps

There are several areas for improvement and future implementation:

1. **Better Logging:**
  - Currently, logging is implemented only at the controller level.
  - Extend logging to services and other application layers for better observability.

2. **Improved Validation:**
  - Add database constraints to enforce business rules:
    - `price` must be non-negative.
    - `amount` must be non-negative.

3. **Secure Secrets Management:**
  - Replace plaintext passwords stored in the repository with a secure method, such as:
    - Environment variables.
    - Secrets managers like AWS Secrets Manager or Vault.

4. **Enhanced Test Cases:**
  - Add tests for `BigDecimal` rounding accuracy to ensure precision in financial calculations.
  - Implement mutation tests to verify robustness and correctness of the application under unexpected changes.

---

## Decision Summary

- **Discount type** will be provided dynamically in the API.
- **Configurable elements** will include quantity ranges, ensuring flexibility for applying discounts.
- **Fallback to `PERCENTAGE` discounts** will be implemented for cases where the `AMOUNT` discount exceeds the product price.
- Future steps include improved logging, validation, secrets management, and enhanced test cases to ensure a maintainable and robust system.

---
