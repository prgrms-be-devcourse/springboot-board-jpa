# Spring Boot JPA 게시판

---

## 🚀 프로젝트 설명 및 주요 기능

프로그래머스 데브코스 과정 중 JPA를 배우고 배운 지식을 
토대로 [게시판 구현 미션을](https://github.com/prgrms-be-devcourse/SpringBoot-Board) 진행한 프로젝트입니다.

JPA를 활용하여 게시글 관련 API 기능을 만들었습니다.

- 게시글 조회
   - 페이징 조회
   - 단건 조회
- 게시글 작성
- 게시글 등록

---

## ⚙ 개발 환경

**통합 환경**

- macOS
- Intellij

**백엔드 환경**

- Java 16
- Maven 3.8.2
- SpringBoot 2.5.5
- Mysql 8.0.25

---

## ▶ 실행 방법

```bash
git clone https://github.com/Programmers-Dev-Single-Project/SpringBoot-Board.git

프로젝트 경로에서
mvn package
cd target
java -jar spring-board-0.0.1-SNAPSHOT.jar
```

---

## 📝 구현할 기능 목록

### 📌 **주요 기능**

- 게시물 등록
- 게시물 단건 조회
- 게시물 전체 조회
- 게시물 수정

### 📌 **예외 처리**

- 추후 작성 예정

---

## 📖 ERD
<img width="470" alt="스크린샷 2021-10-07 오전 2 21 20" src="https://user-images.githubusercontent.com/58363663/136252902-0698f8af-3c9b-4003-8c38-93ae4846cece.png">


---

## 📑 API 명세서

### 📌 게시물 페이징 조회

GET 요청으로 게시물을 페이징 조회할 수 있다.

```http request
GET http://localhost:8080/posts
```

### 📌 게시물 단건 조회

GET 요청으로 게시물을 단건 조회할 수 있다.

```http request
GET http://localhost:8080/posts/1
```

### 📌 게시물 작성

POST 요청으로 게시물을 작성할 수 있다.

```http request
POST http://localhost:8080/posts
```

### 📌 게시물 수정

POST 요청으로 게시물을 수정할 수 있다.

```http request
GET http://localhost:8080/posts/1
```


## 📄 Git

- flow
  - dev 브랜치에서 개발 후 main 브랜치로 이동
  
- 커밋 메세지
  ```
  Feat : 코드나 테스트를 추가했을 때
  Fix : 버그를 수정했을 때
  Docs : 문서를 수정했을 때
  Refactor : 코드 리팩토링
  ```
