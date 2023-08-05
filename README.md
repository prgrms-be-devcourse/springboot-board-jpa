# Spring Boot JPA로 게시판 구현

## 미션 소개 😎
Spring Boot JPA - Rest API를 강의를 듣고, 게시판 구현 미션을 수행해봅시다.

## 1. 그림 그리기 & 소개하기 🎨

<img width="1011" alt="스크린샷 2023-08-04 오후 7 28 00" src="https://github.com/kys0411/springboot-board-jpa/assets/62236238/5915a14e-8777-430e-b68d-8a7f54246c3f">


## 2. 요구사항
- [X] datasource : h2 or mysql

- [X] 회원(User)
    - id (PK) (auto increment)
    - name
    - age
    - hobby
    - **created_at**
    - **created_by**

- [X] 게시글(Post)
    - id (PK) (auto increment)
    - title
    - content
    - **created_at**
    - **created_by**
- [X] 회원과 게시글에 대한 연관관계를 설정한다.
    - 회원과 게시글은 1:N 관계이다.
- [X] 게시글 Repository를 구현한다. (PostRepository)

- [X] 게시글 조회
    - 페이징 조회 (GET "/posts")
    - 단건 조회 (GET "/posts/{id}")
- [X] 게시글 작성 (POST "/posts")
- [X] 게시글 수정 (POST "/posts/{id}")

- [ ] REST Docs 적용
