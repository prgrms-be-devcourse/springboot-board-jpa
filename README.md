# Spring Boot JPA로 게시판 구현

> Spring Boot + JPA + MySQL



#### 개발환경

```
• IDE : IntelliJ 2020.03
• 개발 언어 : Java 11
• 프레임워크 : SpringBoot 2.5.5
• 영속성 프레임워크 : JPA
• 빌드도구 : Gradle 7.2
• 데이터베이스 : MySQL 8.0.26
```



---



## 기능 요구 사항

- 사용자 상세 정보 조회, 사용자 추가, 사용자 정보 수정, 삭제 API 구현
- 모든 게시글 조회, 특정 사용자의 모든 게시글 조회, 특정 게시글 상세 조회, 게시글 등록, 수정, 삭제 API 구현

- 댓글 등록, 수정, 삭제 API 구현



## API 명세서

#### User(사용자)

|             Action             | Verbs  |     URL Pattern     |
| :----------------------------: | :----: | :-----------------: |
|   특정 사용자 정보 조회하기    |  GET   | /api/users/{userId} |
|        사용자 추가하기         |  POST  |     /api/users      |
|   특정 사용자 정보 수정하기    |  PUT   | /api/users/{userId} |
| 특정 사용자 삭제(비활성화)하기 | DELETE | /api/users/{userId} |



#### Post(게시글)

|               Action               | Verbs  |         URL Pattern          |
| :--------------------------------: | :----: | :--------------------------: |
|        모든 게시글 조회하기        |  GET   |          /api/posts          |
| 특정 사용자의 모든 게시글 조회하기 |  GET   |     /api/posts/{userId}      |
|     특정 게시글 상세 조회하기      |  GET   |   /api/posts/post/{postId}   |
|          게시글 등록하기           |  POST  |          /api/posts          |
|        특정 게시글 수정하기        |  PUT   | /api/posts/{userId}/{postId} |
|        특정 게시글 삭제하기        | DELETE | /api/posts/{userId}/{postId} |



#### Comment(댓글)

|       Action       | Verbs  |            URL Pattern             |
| :----------------: | :----: | :--------------------------------: |
|   댓글 등록하기    |  POST  |  /api/comments/{userId}/{postId}   |
| 특정 댓글 수정하기 |  PUT   | /api/comments/{userId}/{commentId} |
| 특정 댓글 삭제하기 | DELETE | /api/comments/{userId}/{commentId} |



## ERD

<img src="images/erd.png" alt="erd" style="zoom:50%;" />



### **REST DOCS**
