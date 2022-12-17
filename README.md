# SpringBoot JPA Example Project

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
    │   │     └── com.example.springjpa
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

- 요구사항 정리 (--는 로그인 기능 구현 후에 구현)
  - 주제 : 간단한 쇼핑몰
  - **기능 목록**
    - 회원 기능
      - 회원등록
      - ~~회원구분(관리자(ADMIN), 판매자(SELLER), 구매자(BUYER))~~
      ~~- 회원조회(관리자만)~~
      - `~~로그인(spring security)~~`
        - ~~1차적으로 session방식으로 구현한다.~~
        - ~~jwt로 추후 리팩토링 한다.~~
    - 상품 기능 ~~: 관리자, 판매자 회원~~
      - 상품등록
      - 상품수정
      - 상품삭제
      - 상품조회
      - ~~관리자는 모든 판매자의 상품을 조회 할 수 있다.~~
      - ~~판매자는 자신이 올린 상품에 대해서만 조회 및 수정, 삭제 할 수 있다.~~
    - 주문 기능 : ~~구매자 회원~~
      - 상품주문
      - 주문내역 조회
      - 주문취소
    - 기타 요구사항
      - 상품은 재고 관리가 필요하다.
      - 상품의 종류는 도서, 음반, 영화가 있다.(item category)
      - 상품을 카테고리로 구분할 수 있다.
      - 상품 주문 시 배송 정보를 입력할 수 있다.