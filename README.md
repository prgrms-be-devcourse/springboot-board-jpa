# Spring Boot JPA로 게시판 구현

## 미션 소개 😎
Spring Boot JPA - Rest API를 강의를 듣고, 게시판 구현 미션을 수행해봅시다.

## 1. 그림 그리기 & 소개하기 🎨
![스크린샷 2023-08-04 오후 7.20.19.png](..%2F..%2F..%2F..%2Fvar%2Ffolders%2Fyc%2Fx3cfhq_s1xl_7cf7d784pkq40000gn%2FT%2FTemporaryItems%2FNSIRD_screencaptureui_a5IQ2k%2F%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202023-08-04%20%EC%98%A4%ED%9B%84%207.20.19.png)

## 2. 요구사항
- [X] datasource : h2 or mysql

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

- [X] 게시글 조회
    - 페이징 조회 (GET "/posts")
    - 단건 조회 (GET "/posts/{id}")
- [X] 게시글 작성 (POST "/posts")
- [X] 게시글 수정 (POST "/posts/{id}")

- [ ] REST Docs 적용