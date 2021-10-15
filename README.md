# Spring Boot JPA로 게시판 구현

## 미션 소개 😎

Spring Boot JPA - Rest API를 강의를 듣고, 게시판 구현 미션을 수행해봅시다. 최종 제출일은 10/19(일)입니다.

# 요구사항

### **1. SpringDataJPA 를 설정한다.**

- [v] datasource : h2

### **2. 엔티티를 구성한다**

- [v] 회원(User)
    - [v] id (PK) (auto increment)
    - [v] name
    - [v] age
    - [v] hobby
    - [v] **created_at**
    - [v] **created_by**
- [x] 게시글(Post)
    - [v] id (PK) (auto increment)
    - [v] title
    - [v] content
    - [v] **created_at**
    - [v] **created_by**
- [v] 회원과 게시글에 대한 연관관계를 설정한다.
    - [v] 회원과 게시글은 1:N 관계이다.
- [v] 게시글 Repository를 구현한다. (PostRepository)

### **3. API를 구현한다.**

- [v] 게시글 조회
    - [v] 페이징 조회 (GET "/posts")
    - [v] 단건 조회 (GET "/posts/{id}")
- [v] 게시글 작성 (POST "/posts")
- [v] 게시글 수정 (POST "/posts/{id}")
- [v] 게시글 삭제 (Delete "/posts/{id}")

### 4. REST-DOCS를 이용해서 문서화한다.
