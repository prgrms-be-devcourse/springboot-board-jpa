# Spring Boot JPA 게시판 구현 (김영주)

## 1. 요구사항
- [x] Spring Data JPA를 설정한다.


- [x] 회원(User) 엔티티를 구성한다.
    - id (PK) (auto increment)
    - name
    - age
    - hobby
    - **created_at**
    - **created_by**


- [x] 게시글(Post) 엔티티를 구성한다.
    - id (PK) (auto increment)
    - title
    - content
    - **created_at**
    - **created_by**


- [x] 회원과 게시글에 대한 연관관계를 설정한다.
    - 회원과 게시글은 1:N 관계이다.


- [x] 사용자 Repository를 구현한다. (UserRepository)
- [x] 게시글 Repository를 구현한다. (PostRepository)


- [x] API를 구현한다.
  - 사용자 생성 (`POST /api/v1/users`)
  - 게시물 생성 (`POST /api/v1/posts`)
  - 전체 게시물 조회 (`GET /api/v1/posts`)
  - 상세 게시물 조회 (`GET /api/v1/posts/{id}`)
  - 게시물 수정 (`PATCH /api/v1/posts/{id}`)
  - 게시물 삭제 (`DELETE /api/v1/posts/{id}`)


- [x] Spring Rest Docs를 이용해서 문서화한다.

---

## 2. Rest Docs 적용화면
![image](https://user-images.githubusercontent.com/49775540/257755677-85db2a31-e6a8-4360-8f22-d7cb60f5b361.png)

