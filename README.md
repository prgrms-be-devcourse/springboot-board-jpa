# Spring Boot JPA 게시판 API 구현

## 도메인

- User

- POST
  - 게시물 추가
  - 게시물 수정
  - 게시물 삭제
  - 게시물 조회
  - 전체 게시물 조회

## 개발 환경

- SpringBoot
- Spring MVC
- Spring Data Jpa
- Hibernate
- h2
- Java 16
- RESTDOCS
- Intellij

## 기능 요구 사항
- 게시글 조회
   - 페이징 조회 (GET "/posts")
   - 단건 조회 (GET "/posts/{id}")
- 게시글 작성 (POST "/posts")
- 게시글 수정 (PUT "/posts/{id}")

## API 명세

### Post

| Method |      URI      | Description |
|--------|---------------|-------------|
|  POST  | /api/v1/posts | 게시물 생성   |
|  GET  | /api/v1/posts/{postId} | 게시물 단건 조회 |
|  GET  | /api/v1/posts?page={pageNum}&size={pageSize}&direction={direction} | 게시물 페이징 조회|
|  PUT  | /api/v1/posts/{postId} | 게시물 수정   |
|  DELETE  | /api/v1/posts/{postId} | 게시물 삭제   |
