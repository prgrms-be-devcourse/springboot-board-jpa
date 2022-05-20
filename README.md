# [미션] Spring Boot JPA로 게시판 구현

## 미션 소개 😎

---

``
Spring Boot JPA - Rest API를 강의를 듣고,  
게시판 구현 미션을 수행해봅시다.
``

## 요구 사항

---
- [x] SpringDataJPA를 설정
  - datasource : h2
- [x] 엔티티를 구성한다.
  - 회원(User)
    - id(PK)(auto increment)
    - name
    - age
    - hobby
    - created_at
    - created_by
  - 게시글(Post)
    - id(PK)(auto increment)
    - title
    - content
    - created_at
    - created_by
  - 회원과 게시글에 대한 연관관계 설정
     - 회원과 게시글은 1:N 관계이다.
  - 게시글 Repository를 구현한다(PostRepository)

- [x] API 구현
  - 게시글 조회
    - 페이징 조회(GET "/posts")
    - 단건 조회(GET "/posts/{id}")
  - 게시글 작성(POST "/posts")
  - 게시글 수정(PUT "/posts/{id}")

- [x] REST-DOCS를 이용해서 문서화한다.

## 💡 PR 포인트 & 궁금한 점

---

1. DTO 관련  
   생성을 할 때는 id 필드가 필요 없고, 업데이트시에는 id 필드가 필요하다고 생각해 dto를 따로 만들어 사용했습니다.  
   그냥 한가지의 dto만 사용하는 것이 좋을지 궁금합니다.
2. 