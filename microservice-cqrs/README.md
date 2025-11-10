# Axon CQRS Microservice

This project provides a reference implementation of a CQRS microservice using the Axon Framework, Spring Boot, and PostgreSQL. It demonstrates the use of commands, events, and projections to manage the lifecycle of an `Order` aggregate.

## Features

- Command-side aggregate with Axon command handlers
- Event sourcing for the `Order` aggregate
- JPA-backed projection for query-side reads
- REST API for dispatching commands and executing queries
- PostgreSQL persistence for both event storage and read models

## Prerequisites

- Java 21
- Maven 3.9+
- PostgreSQL 15 (or compatible version)

## Getting Started

1. Configure PostgreSQL and create a database, for example:

   ```sql
   CREATE DATABASE axon_microservice;
   CREATE USER axon WITH ENCRYPTED PASSWORD 'axon';
   GRANT ALL PRIVILEGES ON DATABASE axon_microservice TO axon;
   ```

2. Update environment variables as needed:

   ```bash
   export DB_URL=jdbc:postgresql://localhost:5432/axon_microservice
   export DB_USERNAME=axon
   export DB_PASSWORD=axon
   ```

3. Build and run the service:

   ```bash
   mvn spring-boot:run
   ```

## API Overview

### Commands

- `POST /api/orders`  
  Create a new order.

  ```json
  {
    "orderId": "order-123",
    "product": "Laptop",
    "quantity": 1,
    "price": 1999.00
  }
  ```

- `POST /api/orders/{orderId}/approve`  
  Approve an existing order.

- `POST /api/orders/{orderId}/reject`  
  Reject an existing order.

  ```json
  {
    "reason": "Payment declined"
  }
  ```

### Queries

- `GET /api/orders`  
  Retrieve all orders with their current status.

- `GET /api/orders/{orderId}`  
  Retrieve a specific order projection.

## Testing

Execute tests with:

```bash
mvn test
```

## Notes

- Axon Server is disabled; the application uses the configured relational database for storage.
- Hibernate manages schema updates automatically (`spring.jpa.hibernate.ddl-auto=update`). Adjust as required for production environments.


