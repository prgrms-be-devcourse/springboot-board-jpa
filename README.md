# Spring Boot JPA로 게시판 구현

> 스프링부트와 JPA로 구현한 게시한 API 

> MADE BY DONGGEON

### 도메인 및 API

> 자세한 내용은 WIKI를 참고해주세요!

- MEMBER
    + 회원 가입
    + 회원 수정
    + 회원 삭제
    + 회원 조회
    + 전체 회원 조회

- POST
    + 게시물 추가
    + 게시물 수정
    + 게시물 삭제
    + 게시물 조회
    + 전체 게시물 조회

### 환경

- SpringBoot 2
- JPA
- MYSQL 8
- RESTDOCS
- JAVA 11

### 패키지 구조

- springbootboard
    + common
      + domain
      + dto
    + config
    + exception
      + error
    + member
        + application
        + converter
        + domain
            + vo
        + dto
        + infrastructure
        + presentation
    + post
        + application
        + converter
        + domain
            + vo
        + dto
        + infrastructure
        + presentation