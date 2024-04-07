## Spring Boot application for bookstore inventory management.

<details>
<summary>
Project Dependencies and Technologies
</summary>

Core dependencies and technologies

>[org.springframework.boot](https://spring.io/projects/spring-boot)
*- Spring Boot framework is used for creating Spring-based web application.*

>[org.hibernate.validator](https://hibernate.org/validator/)
*- Hibernate Validator used in project for validating entity fields.*

>[org.mapstruct](https://mapstruct.org/)
*- Mapstruct is used to map entities to DTO.*

>[io.grpc](https://grpc.io/)
*- gRPC framework used for fast communication between API and user.*

>[org.projectlombok](https://projectlombok.org/)
*- Lombok is used to reduce the amount of boilerplate code writing.*

>[PostgreSQL](https://www.postgresql.org/)
*- used as a storage for all books.*

>[org.flywaydb](https://flywaydb.org/)
*- used for Postgres database migrations.*

>[com.fasterxml.jackson.core](https://github.com/FasterXML/jackson)
*- Jackson used to convert object to JSON.*

>[junit 5](https://junit.org/junit5/)
*- framework used for writing unit tests.*

>[org.mockito](https://site.mockito.org/)
*- used for mocking in unit tests.*

>[org.testcontainers](https://testcontainers.com/)
*- used to create postgresdb in Docker container for tests.*

>[org.citrusframework](https://citrusframework.org/)
*- framework for creating automated integration tests.*

---



</details>

<details>
<summary>
API Navigation
</summary>

### Add Book

```
http://localhost:8080/addBook
or
https://king-prawn-app-3iiik.ondigitalocean.app/addBook
```
JSON
```
{
"title": "Effective Java",
"author": "Joshua Bloch",
"genre": "REFERENCE",
"isbn": "9786581345100",
"quantity": 6
}
```

### UPDATE BOOK
```
http://localhost:8080/updateBook
or
https://king-prawn-app-3iiik.ondigitalocean.app/updateBook
```
JSON
```
{
"title": "Java Concurrency in Practice",
"author": "Joshua Bloch",
"genre": "REFERENCE",
"isbn": "9786581345100",
"quantity": 10
}
```

### RETRIEVE BOOKS

#### BY ISBN
```
http://localhost:8080/book/BY_ISBN/9786581345100
or
https://king-prawn-app-3iiik.ondigitalocean.app/book/BY_ISBN/9786581345100
```

#### BY TITLE
```
http://localhost:8080/book/BY_TITLE/Java Concurrency in Practice
or
https://king-prawn-app-3iiik.ondigitalocean.app/book/BY_TITLE/Java Concurrency in Practice
```

#### BY AUTHOR
```
http://localhost:8080/book/BY_AUTHOR/Joshua Bloch
or
https://king-prawn-app-3iiik.ondigitalocean.app/book/BY_AUTHOR/Joshua Bloch
```

#### BY GENRE
```
http://localhost:8080/book/BY_GENRE/REFERENCE
or
https://king-prawn-app-3iiik.ondigitalocean.app/book/BY_GENRE/REFERENCE
```

---
</details>

Below is the running of gradle tasks, running development environment with Docker 
Compose and running unit and integration tests.

### Build

---
```bash
./gradlew build
```

### Run

---
```bash
docker-compose.exe -f ./docker-compose.yml -p bookstore_inventory_management_system up -d
```

### Run unit tests

---
```bash
./gradlew test --tests 'com.kaktooth.bookstore.inventory_management.unit.*'
```

### Run integration tests

Run docker compose dev environment or 'com.kaktooth.bookstore.inventory_management.integration.TestBookStoreInventoryManagementApplication' before this tests. 

---
```bash
./gradlew test --tests 'com.kaktooth.bookstore.inventory_management.integration.*'
```

### Test Reports

[citrus-test-results.html](target%2Fcitrus-reports%2Fcitrus-test-results.html)

[jacoco report](reports%2Fjacoco%2Ftest%2Fhtml%2Findex.html)

[test summary](reports%2Ftests%2Ftest%2Findex.html)