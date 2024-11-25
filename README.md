
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

## API Endpoints

### Get Product by ID
**Request:**
```http
GET /products/{id}
```

**Example:**
```bash
curl -X GET http://localhost:8080/products/550e8400-e29b-41d4-a716-446655440000
```

**Response:**
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "price": 1,
  "quantity": 10
}
```

---

## How to Stop the Application

To stop and remove the containers, run:
```bash
./gradlew dockerComposeDown
```

---

## File Structure

```
.
├── build.gradle.kts          # Gradle build configuration
├── Dockerfile                # Dockerfile to build the application image
├── docker-compose.yml        # Docker Compose configuration
├── src                       # Application source code
│   ├── main
│   │   ├── java/com/example  # Java code for the application
│   │   └── resources         # Application configuration (application.properties)
└── README.md                 # This file
```

---

## Environment Variables

The following environment variables can be configured in the `docker-compose.yml` file:

- `SPRING_PROFILES_ACTIVE`: Spring profile to use (default: `docker`).
- Additional Spring configuration variables can also be added as needed.

---

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

---

## Authors

- [Kamil Dragan](https://github.com/draggy1) - Initial work