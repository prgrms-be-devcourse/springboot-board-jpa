# Spring Boot JPA로 게시판 구현

## 📌 프로젝트 설명
- SpringDataJPA를 설정한다. (h2 or MySQL)
- 엔티티를 구성한다.
- API를 구현한다.
- 테스트코드를 작성한다.
- REST-DOCS를 이용해 문서화 한다.
---
## 💻 개발 환경
* IDE : IntelliJ
* 개발 언어 : Java 17
* 프레임워크 : SpringBoot 2.5.5
* 라이브러리 : JPA
* 빌드도구 : Maven 3.8.2
* 데이터베이스 : AWS RDS MySQL 8.0.26
---
## 👩‍💻 요구 사항과 구현 내용
### 📍 엔티티 구성
- 회원(User)
    - id (PK) (auto increment)
    - name
    - age
    - hobby
    - **created_at**
    - **updated_at**
- 게시글(Post)
    - id (PK) (auto increment)
    - title
    - content
    - **created_at**
    - **created_at**
- 게시글과 회원에 대한 연관관계를 설정한다.
    - 게시글과 회원은 N : 1 단방향 관계이다.

### 📍 API 구현

### User API
    유저 생성
    * POST /users

    유저 조회
    * GET /users/{userId}

    유저 수정
    * PUT /users/{userId}

    유저 삭제
    * DELETE /users/{userId}

### Post API
    포스트 생성
    * POST /posts

    포스트 조회
    * GET /posts/{postId}

    특정 유저가 작성한 포스트 전체 조회
    * GET /posts/list/{postId}

    포스트 수정
    * PUT /posts/{postId}

    포스트 삭제
    * DELETE /posts/{postId}

### ERD
<img src="https://user-images.githubusercontent.com/81504103/137440998-72ef919c-a4fc-49af-ae79-81ec78ee50c2.png" width="400" height="350">

### Directory
<img src="https://user-images.githubusercontent.com/81504103/137441117-8043f020-be37-4466-8ed3-56ea44906475.png" width="350" height="500">