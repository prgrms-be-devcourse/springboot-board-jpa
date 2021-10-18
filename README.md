# [미션] Spring Boot JPA로 게시판 구현

### 1. SpringDataJPA 설정

- datasource : mysql

### 2. Entity

- 회원(User)
    - user_id (PK) (auto increment)
    - name
    - age
    - hobby
    - created_at
    - modified_at
- 게시글(Post)
    - post_id (PK) (auto increment)
    - title
    - content
    - created_at
    - created_by
    - modified_at
    - modified_by
- 회원과 게시글에 대한 연관관계
    - 회원과 게시글은 1:N 관계이다.

### 3. API 구현

- 게시글 조회
    - 페이징 조회 (GET "/posts")
    - 단건 조회 (GET "/posts/{postId}")
- 게시글 작성 (POST "/posts")
- 게시글 수정 (PUT "/posts/{postId}")

### 4. REST-DOCS 문서화