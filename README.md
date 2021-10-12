# Spring Boot JPA로 게시판 구현

## 프로젝트 설명

Spring Boot JPA - Rest API를 강의를 통해 배운 지식을 바탕으로 게시판 구현 미션 진행

## 개발 환경

- IntelliJ 2021.2.2
- Java 11
- SpringBoot 2.5.5
- Maven 3.8.2
- JPA
- MySQL 8.0.26

## 요구사항 & 구현 기능

- 유저
    - 회원가입
    - 회원 정보 조회
    - 회원 정보 수정
    - 탈퇴 (물리 삭제)


- 게시판
    - 게시글 페이징 조회(다건)
    - 게시글 상세 조회(단건)
    - 게시글 수정
    - 게시글 삭제


- REST DOCS API 문서화

## TODO

- HTTP Request 데이터 검증
- API 버전 관리
- 검색 기능(복합 조건 쿼리를 인메모리 방식으로 쿼리 검증)
- 인덱스 사용(단일, 복합)
- View 테이블 사용

## 참고 자료

- [JPA Auditing 기능이란?](https://webcoding-start.tistory.com/53)
- [ReponseEntity란](https://devlog-wjdrbs96.tistory.com/182)
- [Exception 처리](https://bcp0109.tistory.com/303), [예외 관리](https://jeong-pro.tistory.com/195)
- [HTTP Status Code](https://sanghaklee.tistory.com/61)
- [날짜 타입 JSON 변환 - JasonFormat](https://jojoldu.tistory.com/361)
