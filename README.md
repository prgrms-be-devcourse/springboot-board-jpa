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
- 첫 학기에 같은 반끼리 게시물을 공유하는 게시판 입니다.
- 담임 선생님께서 반 학생들의 계정을 일괄 생성합니다. 
- 계정 구분은 이름(User.name)으로 합니다. 만약 같은 이름의 학생이 있을 경우, 홍길동(A), 홍길동(B)로 이름으로 계정을 생성합니다.
- 학생들은 필수적으로 "할당된 이름을 넣어" 게시물을 생성합니다. 
- 게시물을 수정할 경우에는, '작성할 때 설정한 비밀번호'를 기입하여야 수정이 가능합니다.
- 게시물의 종류는 총 4가지 입니다. (과제게시물, 소개게시물, 공지게시물, 질문게시물)
- 모든 게시물은 "게시물 리스트 페이지"에서 보입니다. 게시물 상세 정보 보기 및 수정 할 경우, 게시물 타입마다 볼 수 있는 정보 및 작성할 수 있는 정보가 조금씩 차이가 납니다.
- 기본 페이지 (가정)
  - 게시물 리스트 페이지 
    ![image](https://user-images.githubusercontent.com/88185304/137440087-75c2b51b-875a-44cd-ae58-f527e725fb11.png)
  - 게시물 작성 페이지 (게시물 타입 중, 소개 게시물을 예시로 들었습니다.)
    ![image](https://user-images.githubusercontent.com/88185304/137440304-339e43c2-0874-4215-a292-ed9b972ab231.png)

### 2. Entity 관계
![image](https://user-images.githubusercontent.com/88185304/137444741-5a257721-1ff7-4aff-bc1d-10f738b246a0.png)

### 3. 데이터 베이스
- 데이터베이스: h2

- ERD

  ![image](https://user-images.githubusercontent.com/88185304/137441909-51bf50f3-ed3f-4422-85b5-fcfd66a2ad26.png)
```sql
    create table users (
        id bigint not null,
        created_at TIMESTAMP DEFAULT NOW(),
        created_by varchar(255) not null,
        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
        age integer not null,
        hobby varchar(50),
        name varchar(20) not null,
        primary key (id)
    );
    
    create table posts (
        dtype varchar(31) not null,
        id bigint not null,
        created_at TIMESTAMP DEFAULT NOW(),
        created_by varchar(255) not null,
        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
        content TEXT,
        password varchar(64) not null,
        title varchar(50) not null,
        homework_status integer,
        expire_date TIMESTAMP,
        post_scope varchar(255),
        user_id bigint,
        primary key (id)
        CONSTRAINT fk_posts_to_users FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
    );
```
### 4. API 명세서
- REST-DOCS로 제공.
