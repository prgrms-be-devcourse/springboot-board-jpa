# Spring Boot JPA로 게시판 구현

## 미션 소개 😎

Spring Boot JPA - Rest API를 강의를 듣고, 게시판 구현 미션을 수행해봅시다. 최종 제출일은 10/19(일)입니다.

# 요구사항

### **1. SpringDataJPA 를 설정한다.**

- [ ] datasource : ~~h2~~ or `mysql`

### **2. 엔티티를 구성한다**

- [ ] 회원(User)
  - [ ] id (PK) (auto increment)
  - [ ] name
  - [ ] age
  - [ ] hobby
  - [ ] **created_at**
  - [ ] **created_by**
- [ ] 게시글(Post)
  - [ ] id (PK) (auto increment)
  - [ ] title
  - [ ] content
  - [ ] **created_at**
  - [ ] **created_by**
- [ ] 회원과 게시글에 대한 연관관계를 설정한다.
  - [ ] 회원과 게시글은 1:N 관계이다.
- [ ] 게시글 Repository를 구현한다. (PostRepository)

### **3. API를 구현한다.**

- [ ] 게시글 조회
  - [ ] 페이징 조회 (GET "/posts")
  - [ ] 단건 조회 (GET "/posts/{id}")
- [ ] 게시글 작성 (POST "/posts")
- [ ] 게시글 수정 (POST "/posts/{id}")

### 4. REST-DOCS를 이용해서 문서화한다.

# 프로젝트 구조

~~사진 업로드 & 설명 예정~~

# Database Entity Relation Diagram

~~사진 업로드~~

# 프로젝트의 주요 관심사

### 공통사항

- 지속적인 성능 개선
- 나쁜 냄새가 나는 코드에 대한 리팩토링

### 코딩 컨벤션 

- [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html) 을 준수 (intellij-java-google-style.xml : [link](https://github.com/google/styleguide))

### 성능 최적화

- 서버 부하를 줄이기 위해 캐싱 서버 적극 활용
  - DB 서버와의 통신을 최소화 (N+1 문제)
- 인덱스와 쿼리 튜닝을 활용

### 브랜치 관리 전략

- **Git-flow** 전략을 사용하여 작업.
- `master` : 제품으로 출시될 수 있는 브랜치
- `develop` : 다음 출시 버전을 개발하는 브랜치
- `feature` : 기능을 개발하는 브랜치
- `release` : 이번 출시 버전을 준비하는 브랜치
- `hotfix` : 출시 버전에서 발생한 버그를 수정 하는 브랜치

### 테스트

- Mockito Framework를 활용하여 고립된 테스트 코드 작성

### 그 외

- Project Wiki 참고

# 사용 기술 및 환경

Java 11, Spring Boot, Maven, JPA, Lombok, MySQL, Mockito

# Wiki

wiki에 기술 이슈에 대한 고민과 해결 방법을 포스팅한 블로그 url을 포함한다.

