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
- [X] 엔티티 설계
  - [X] 엔티티 설계 & 테이블과 매핑
  - [X] 연관관계 매핑
    - [X] 연관관계 편의 메서드 작성
  - [X] 설계도 문서화
- [X] 게시글 Repository 개발 (Spring Data JPA 이용)
- API 개발
  - 게시글 조회
    - [X] 페이징 조회 (GET "/posts")
      - [X] Service, Persistence Layer 개발
      - [X] Controller 개발
      - [X] Repository Layer fetch join으로 리펙토링
    - [X] 단건 조회 (Get "/posts/{id}")
      - [X] Service, Persistence Layer 개발
      - [X] Controller 개발
  - [X] 게시글 작성 (POST "/posts")
    - [X] Service, Persistence Layer 개발
    - [X] Controller 개발
  - [X] 게시글 수정 (PATCH ":/posts/{id}")
    - [X] Service, Persistence Layer 개발
    - [X] Controller 개발
  - [X] REST - DOCS 이용 문서화


## 피드백

- [X] 사용하지 않는 필드는 제거 고민하기 (BaseEntity의 createdBy)
- [X] ExceptionHandler는 Controller와 분리해 관리하자.
- [X] Post Request에는 HttpStatus Created(201)와 생성된 리소스 조회 URL을 응답에 담기!
- [X] 일급 컬렉션에서 전체 원소들을 반환해 줄때 불변 컬렉션으로 반환하자. (은닉화)