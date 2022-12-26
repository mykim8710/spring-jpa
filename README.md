# SpringBoot JPA Study Project

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

- 요구사항 정리
  - 주제 : 간단한 쇼핑몰
  - **기능 목록**
    - 회원 기능
      - 회원등록
      - 회원조회
    - 상품 기능
      - 상품등록
      - 상품수정
      - 상품삭제
      - 상품조회
    - 주문 기능
      - 상품주문
      - 주문내역 조회
      - 주문취소
    - 기타 요구사항
      - 상품은 재고 관리가 필요하다.
      - 상품의 종류는 도서, 음반, 영화가 있다.(item category)
      - 상품을 카테고리로 구분할 수 있다.
      - 상품 주문 시 배송 정보를 입력할 수 있다.

- 