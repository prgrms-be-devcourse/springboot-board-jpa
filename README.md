# Spring Boot JPA로 게시판 구현

## 미션 소개 😎

Spring Boot JPA - Rest API를 강의를 듣고, 게시판 구현 미션을 수행해봅시다. 최종 제출일은 10/19(일)입니다.

# 요구사항

### **1. SpringDataJPA 를 설정한다.**

- [x] datasource : h2
- [x] Java : 11
- [x] Buildtool : Maven
- [x] IDE : IntelliJ
- [x] 그외 : Lombok, Mockito

### **2. 엔티티를 구성한다**

- [x] 회원(User)
    - [x] id (PK) (auto increment)
    - [x] name
    - [x] age
    - [x] hobby
    - [x] **created_at**
    - [x] **created_by**
- [x] 게시글(Post)
    - [x] id (PK) (auto increment)
    - [x] title
    - [x] content
    - [x] **created_at**
    - [x] **created_by**
- [x] 회원과 게시글에 대한 연관관계를 설정한다.
    - [x] 회원과 게시글은 1:N 관계이다.
- [x] 게시글 Repository를 구현한다. (PostRepository)

### **3. API를 구현한다.**

- [x] 게시글 조회
    - [x] 페이징 조회 (GET "/posts")
    - [x] 단건 조회 (GET "/posts/{id}")
- [x] 게시글 작성 (POST "/posts")
- [x] 게시글 수정 (POST "/posts/{id}")
- [x] 게시글 삭제 (Delete "/posts/{id}")

### 4. REST-DOCS를 이용해서 문서화한다.
