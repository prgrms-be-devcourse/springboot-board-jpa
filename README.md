# [미션] Spring Boot JPA로 게시판 구현

## 미션 소개 😎
Spring Boot JPA - Rest API를 강의를 듣고, 게시판 구현 미션을 수행해봅시다.

## 프로젝트 설계도
![JPA게시판](https://github.com/sujjangOvO/query-builder/assets/89267864/1efa5c80-bc33-4940-833d-ab4df20dc259)

## 작업 목록
- [x] 📝설계도 작성 및 작업 목록 구상
- [ ] :tada:프로젝트 init
- [ ] :white_check_mark:SpringDataJpa, mysql 설정
- [ ] :sparkles:BaseEntity 추가
- [ ] :sparkles:User Entity 구현
- [ ] :sparkles:Post Entity 구현
- [ ] :sparkles:User, Post 1:n 연관 관계 설정
- [ ] :sparkles:UserController 구현
- [ ] :sparkles:UserService 구현
- [ ] :sparkles:UserDto 구현
- [ ] :sparkles:JPAUserRepository 구현
- [ ] :sparkles:PostRestRepository 구현
  - 페이징 조회(GET /posts)
  - 단건 조회(GET post/{id})
  - 게시글 작성(POST /posts)
  - 게시글 수정(POST /posts/{id})
- [ ] :sparkles:PostService 구현
- [ ] :sparkles:PostDto 구현
- [ ] :sparkles:JPAPostRepository 구현
- [ ] :white_check_mark:REST-DOCS 의존성 추가
- [ ] 📝REST-DOCS을 이용한 API 명세 작성
