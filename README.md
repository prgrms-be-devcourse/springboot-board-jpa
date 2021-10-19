#  Spring Boot JPA로 게시판 구현
> Spring Boot JPA - Rest API를 강의를 듣고, 게시판 구현 미션을 수행해봅시다.

# **요구 사항**

## **SpringDataJPA 를 설정한다.**

- [x] datasource : h2 or mysql

## **엔티티를 구성한다**

- [x] 회원(User)
    - id (PK) (auto increment)
    - name
    - age
    - hobby
    - **created_at**
    - **created_by**
- [x] 게시글(Post)
    - id (PK) (auto increment)
    - title
    - content
    - **created_at**
    - **created_by**
- [x] 회원과 게시글에 대한 연관관계를 설정한다.
    - 회원과 게시글은 1:N 관계이다.

- [x] 회원 Repository를 구현한다. (UserRepository)
- [x] 게시글 Repository를 구현한다. (PostRepository)

## **API를 구현한다.**

- 게시글 조회
    - [x] 페이징 조회 (GET "/posts")
    - [x] 단건 조회 (GET "/posts/{id}")
- [x] 게시글 작성 (POST "/posts")
- [x] 게시글 수정 (POST "/posts/{id}")
- 회원 조회
    - [x] 회원 조회 (GET "/users")
    - [x] 단건 조회 (GET "/users/{id}")
- [x] 회원 작성 (POST "/users")
- [x] 회원 수정 (POST "/users/{id}")

## **REST-DOCS를 이용해서 문서화한다.**
- [x] REST DOCS 작성
