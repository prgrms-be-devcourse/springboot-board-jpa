# [데브코스 미션] Spring Boot JPA로 게시판 구현

## 미션 소개 😎
Spring Boot JPA - Rest API를 강의를 듣고, 게시판 구현 미션을 수행해봅시다.

### **1. SpringDataJPA 를 설정한다.**

- datasource : h2 or mysql

### **2. 엔티티를 구성한다**
- 회원(User)
- 게시글(Post)
- 회원과 게시글에 대한 연관관계 설정
    - 회원과 게시글은 1:N 관계이다.
- 게시글 Repository를 구현한다. (PostRepository)

### **3. API를 구현한다.**

- 게시글 조회
    - 페이징 조회
    - 단건 조회 
- 게시글 작성 
- 게시글 수정 

### **4. REST-DOCS를 이용해서 문서화한다.**
------------
## 추가할 내용
- [ ]  Post 삭제 구현
- [ ]  사용자 가입, 조회, 정보수정, 삭제 구현
- [ ]  페이지 제작
