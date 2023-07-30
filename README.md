## 게시판 구현 미션 요구사항

<hr>

### 1️⃣ feature/domain

<hr>

#### **SpringDataJPA 를 설정한다.**

- [x] datasource : h2

#### **엔티티를 구성한다**

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

<hr>

### 2️⃣ feature/api

<hr>

#### **API를 구현한다.**

- [ ] 게시글 조회
    - [ ] 페이징 조회 (GET "/posts")
    - [ ] 단건 조회 (GET "/posts/{id}")
- [ ] 게시글 작성 (POST "/posts")
- [ ] 게시글 수정 (POST "/posts/{id}")

<hr>

### 3️⃣ feature/restdocs

<hr>

#### **REST-DOCS를 이용해서 문서화한다.**
