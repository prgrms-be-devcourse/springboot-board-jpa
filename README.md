# [미션] Spring Boot JPA로 게시판 구현

## 미션 소개 😎

Spring Boot JPA - Rest API를 강의를 듣고, 게시판 구현 미션을 수행해봅시다.

## 이곳은 공개 Repo입니다.

1. 여러분의 포트폴리오로 사용하셔도 됩니다.
2. 때문에 이 repo를 fork한 뒤
3. 여러분의 개인 Repo에 작업하며
4. 이 Repo에 PR을 보내어 멘토의 코드 리뷰와 피드백을 받으세요.

## Branch 명명 규칙

1. 여러분 repo는 알아서 해주시고 😀(본인 레포니 main으로 하셔두 되져)
2. prgrms-be-devcourse/spring-board 레포로 PR시 branch는 본인 username을 적어주세요 :)  
   base repo : `여기repo` base : `username` ← head repo : `여러분repo` compare : `main`


## To do 
- [ ] 엔티티 설계
  - [X] 엔티티 설계 & 테이블과 매핑
  - [X] 연관관계 매핑
    - [X] 연관관계 편의 메서드 작성
  - [ ] 설계도 문서화
- [ ] 게시글 Repository 개발 (Spring Data JPA 이용)
- API 개발
  - 게시글 조회
    - [ ] 페이징 조회 (GET "/posts")
      - [X] Service, Persistence Layer 개발
      - [X] Controller 개발
      - [ ] Repository Layer fetch join으로 리펙토링
    - [ ] 단건 조회 (Get "/posts/{id}")
      - [X] Service, Persistence Layer 개발
      - [X] Controller 개발
  - [ ] 게시글 작성 (POST "/posts")
    - [X] Service, Persistence Layer 개발
    - [X] Controller 개발
  - [ ] 게시글 수정 (POST ":/posts/{id}")
    - [X] Service, Persistence Layer 개발
    - [ ] Controller 개발
  - [ ] REST - DOCS 이용 문서화