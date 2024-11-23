# ADR 001: Product Management API - Part of Shopping Platform

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
- Increased complexity of implementing dynamic logic for applying multiple discounts in sequence.

### No multi-threading in the initial phase

**Positive:**
- Simple and maintainable implementation.
- Faster delivery of the initial version.
- Easier debugging and testing.

**Negative:**
- Limited scalability for concurrent requests.
- Migration to a non-blocking or multi-threaded architecture in the future may require significant refactoring.
- Potential delays in response time under heavy traffic conditions.

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

---