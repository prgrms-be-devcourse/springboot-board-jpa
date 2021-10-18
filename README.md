# Spring Boot JPA로 게시판 구현

> 스프링부트와 JPA로 구현한 게시한 API

> MADE BY DONGGEON

### 도메인 및 API

> 자세한 내용은 WIKI를 참고해주세요! [WIKI](https://github.com/DongGeon0908/SpringBoot-Board/wiki)

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

### TODO
- TODO LINK를 자동화시키자!
- 헤이티오스를 분리하는건??? 컨트롤러에서는, 데이터만쏴주고, 헤이티오스를 담당하는 컨트롤러를 만들어서, 링크를 연결
- 메서드 공통부분 빼기, 어노테이션 인터셉터(스프링 컨테이너 관련)로..(aop도 쓸 필요없다..)
- 메서드 나누기, 어노테이션 만들기, 어떤 데이터를 넣을지, 리턴 값을 꺼내서 사용?? 찾아보기.. 영준님코드 보기
