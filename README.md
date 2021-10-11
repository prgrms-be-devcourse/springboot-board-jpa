# [미션] Spring Boot JPA로 게시판 구현

### 1. 요구사항 😎

#### 1-1. SpringDataJPA 를 설정한다.

- datasource : h2 or mysql

#### 1-2. 엔티티를 구성한다

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

#### 1-3. API를 구현한다.

- 게시글 조회
    - 페이징 조회 (GET "/posts")
    - 단건 조회 (GET "/posts/{id}")
- 게시글 작성 (POST "/posts")
- 게시글 수정 (POST "/posts/{id}")

#### 1-4. REST-DOCS를 이용해서 문서화한다.

<br><br>
## 수행한 내용 😎

### 1. 프로젝트를 진행하기 앞서, 미션 요구사항을 바탕으로 "구체적인 요구사항"을 작성해 보았습니다. 
- 첫 학기에 같은 반끼리 과제 제출하는 게시판 입니다.
- 담임 선생님께서 반 학생들의 계정을 일괄 생성합니다. 
- 계정 구분은 이름(User.name)으로 합니다. 만약 같은 이름의 학생이 있을 경우, 홍길동(A), 홍길동(B)로 이름으로 계정을 생성합니다.
- 학생들은 필수적으로 "할당된 이름을 넣어" 게시물을 생성합니다. 
- 기본 페이지 (가정)
  - 리스트 페이지 
    ![image](https://user-images.githubusercontent.com/88185304/136758530-51801ce0-dd0b-4478-8f67-79deef6e60cf.png)
  - 게시물 작성 페이지
    ![image](https://user-images.githubusercontent.com/88185304/136759443-f0461356-4989-437a-9d29-116d87efb87d.png)

### 2. 데이터 베이스
- 데이터베이스: h2

- ERD

  ![image](https://user-images.githubusercontent.com/88185304/136759014-6e6bd0f6-abbf-4d20-ab89-74d010cafb59.png)
```sql
CREATE TABLE users
(
    id          INT(4) NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name        VARCHAR(20) NOT NULL,
    age         INT(2) NOT NULL,
    hobby       VARCHAR(50) DEFAULT NULL,
    created_by  VARCHAR(30) NOT NULL,
    created_at  TIMESTAMP(6) DEFAULT NOW(),
    updated_at  TIMESTAMP(6)  DEFAULT NULL
);

CREATE TABLE posts
(
    id           INT(4) NOT NULL PRIMARY KEY AUTO_INCREMENT,
    title        VARCHAR(50) NOT NULL,
    content      VARCHAR(5000) DEFAULT NULL,
    created_by   VARCHAR(30) NOT NULL,
    created_at   TIMESTAMP(6) DEFAULT NOW(),
    updated_at   TIMESTAMP(6)  DEFAULT NULL,
    CONSTRAINT fk_posts_to_users FOREIGN KEY (id) REFERENCES users (id) ON DELETE CASCADE
);
```
### 3. API 명세서
- REST-DOCS로 제공.
