# [미션] Spring Boot JPA로 게시판 구현

# 미션 소개 😎
## **요구 사항**

### **SpringDataJPA 를 설정한다.**

- datasource : h2 or mysql

### **엔티티를 구성한다**

- 회원(User)
   - id (PK) (auto increment)
   - name
   - age
   - hobby
   - **created_at**
   - **created_by**
- 게시글(Post)
   - id (PK) (auto increment)
   - title
   - content
   - **created_at**
   - **created_by**
- 회원과 게시글에 대한 연관관계를 설정한다.
   - 회원과 게시글은 1:N 관계이다.
- 게시글 Repository를 구현한다. (PostRepository)

### **API를 구현한다.**

- 게시글 조회
   - 페이징 조회 (GET "/posts")
   - 단건 조회 (GET "/posts/{id}")
- 게시글 작성 (POST "/posts")
- 게시글 수정 (POST "/posts/{id}")

### **REST-DOCS를 이용해서 문서화한다.**

