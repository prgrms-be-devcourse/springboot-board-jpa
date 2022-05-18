# Spring Boot JPA 게시판 API

## 요구사항

### **SpringDataJPA 를 설정**

- datasource
    - prod : mariaDB
    - prod : h2

### **엔티티 구성**

- 사용자(User)
    - id (PK) (auto increment)
    - name
    - age
    - hobby
    - created_at
    - updated_at
    - is_deleted
- 게시글(Post)
    - id (PK) (auto increment)
    - title
    - content
    - created_at
    - updated_at
    - is_deleted
- 회원과 게시글에 대한 연관관계를 설정한다.
    - 회원과 게시글은 1:N 관계이다.
- 회원 Repository를 구현한다. (UserRepository)
- 게시글 Repository를 구현한다. (PostRepository)

### **API를 구현**

- 사용자 조회
    - 페이징 조회 (GET "/users")
    - 단건 조회 (GET "/users/{id}")
- 사용자 작성 (POST "/users")
- 사용자 수정 (PUT "/users/{id}")

- 게시글 조회
    - 페이징 조회 (GET "/posts")
    - 단건 조회 (GET "/posts/{id}")
- 게시글 작성 (POST "/posts")
- 게시글 수정 (PUT "/posts/{id}")

### **REST-DOCS를 이용해서 문서화**