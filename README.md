# Online Order Backend System

This is a Spring Boot backend application that powers a simple online food ordering system. It handles user registration, authentication, restaurant and menu listing, cart functionality, and checkout.

## Features

- User registration and authentication with Spring Security
- Restaurant and menu listing APIs
- Cart system to add, update, and remove menu items
- Checkout process
- Caching using Spring Cache
- Unit tests with Mockito and JUnit

## API Endpoints

### Public
- `POST /signup` – Register a new user
- `POST /login` – Login a user
- `GET /restaurants/menu` – View all restaurants and their menus
- `GET /restaurant/{restaurantId}/menu` – View menu by restaurant

### Protected (requires login)
- `GET /cart` – Get current user’s cart
- `POST /cart` – Add an item to the cart
- `POST /cart/checkout` – Checkout and clear cart

## Technologies Used

- Java 17
- Spring Boot 3
- Spring Security
- Spring Data JDBC
- PostgreSQL (or another RDBMS)
- JUnit & Mockito

## How to Run

### Prerequisites

- Java 17+ installed
- Maven installed
- PostgreSQL (or another compatible DB) running
- IDE or terminal

### Environment Setup

1. Clone the repo:

```bash
git clone https://github.com/j-r-yi/onlineorder.git
cd online-order
```

2. Configure your database connection in `application.properties`:

```
spring.datasource.url=jdbc:postgresql://localhost:5432/yourdb
spring.datasource.username=yourusername
spring.datasource.password=yourpassword
spring.datasource.driver-class-name=org.postgresql.Driver
```

3. Build and run the application:

```bash
mvn spring-boot:run
```

4. The server will start at `http://localhost:8080`.

### Run Tests

```bash
mvn test
```

## Security

- Passwords are encoded using `PasswordEncoder`.
- Role-based access control is enforced via `HttpSecurity` configuration.

## Note

- The application uses in-code SQL setup for user creation; make sure the database schema is initialized correctly.
- Default test data can be created using `DevRunner` class.

## License

This project is licensed under the MIT License.
