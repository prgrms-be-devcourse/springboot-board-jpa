## 📌 과제 설명
JPA를 사용한 게시판 구현

**사용 기술 : SpringBoot, JPA, H2**
## 👩‍💻 요구 사항과 구현 내용
### **SpringDataJPA 설정**

- datasource : h2

### **엔티티 구성**

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
- 회원과 게시글에 대한 연관관계 설정
    - 회원과 게시글은 1:N 관계
- 게시글 Repository를 구현 (PostRepository)

### **API 구현**

- 게시글 조회
    - 페이징 조회 (GET "/posts")
    - 단건 조회 (GET "/posts/{id}")
- 게시글 작성 (POST "/posts")
- 게시글 수정 (POST "/posts/{id}")

### **REST-DOCS를 이용해서 문서화**
