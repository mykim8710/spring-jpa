# Spring - DB Transaction Study project

## Project Spec
- 프로젝트 선택
    - Project: Gradle Project
    - Spring Boot: 2.7.5
    - Language: Java
    - Packaging: Jar
    - Java: 11
- Project Metadata
  - Group: com.example
  - Artifact: springjpa
  - Name: spring-jpa
  - Package name: com.example.springjpa
- Dependencies: **Spring Web**, **Thymeleaf**, **Lombok**, **Spring Data JPA**, **H2 Database**, **Validation**
- DB : H2 database

## Package Design
```
└── src
    ├── main
    │   ├── java
    │   │   └── com.example.springjpa
    │   │               └── SpringJpaApplication(C)
    │   └── resource
    │          ├── static
    │          │     └── index.html
    │          ├── template
    │          └── application.properties
    ├── test
    │   ├── java
    │   │     ├── com.example.springjpa
    │   │                 └── SpringJpaApplicationTests(C)
```
