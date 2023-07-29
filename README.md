요구 사항
# **요구 사항**

### **SpringDataJPA 를 설정한다.**

- datasource : h2 or mysql

### **엔티티를 구성한다**
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

### **API를 구현한다.**

- [X] 게시글 조회
    - 페이징 조회 (GET "/posts")
    - 단건 조회 (GET "/posts/{id}")
- [X] 게시글 작성 (POST "/posts")
- [X] 게시글 수정 (POST "/posts/{id}")

### **REST-DOCS를 이용해서 문서화한다.**
- [X] 게시글 조회
    - 페이징 조회 (GET "/posts")
    - 단건 조회 (GET "/posts/{id}")
- [X] 게시글 작성 (POST "/posts")
- [X] 게시글 수정 (POST "/posts/{id}")


---
### 테스트 컨벤션
테스트 할 기능만 작성하자.
테스트 메서드를 식별하는 방법으로 주석을 사용하고 있기 때문에, 테스트 할 기능만 간단하게 쓰는 것이 더 낫다는 의견이 많다. 
또한 코드의 악취를 방지하고, 문서화 된 형태의 유닛 테스트를 수행하므로 권장되는 방법이다.