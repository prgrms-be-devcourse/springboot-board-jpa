# 게시판 만들기

## 요구사항

### Spring Data JPA를 설정한다

- [x]  datasource : h2 or MySQL

### **엔티티를 구성한다**

- [x]  회원(User)
    - id (PK) (auto increment)
    - name
    - age
    - hobby
    - **created_at (mapped superclass)**
    - **created_by (mapped superclass)**
- [x]  게시글(Post)
    - id (PK) (auto increment)
    - title
    - content
    - **created_at (mapped superclass)**
    - **created_by (mapped superclass)**
- [x]  회원과 게시글에 대한 연관관계를 설정한다.
    - 회원과 게시글은 1:N 관계이다.
    - mappedBy
    - cascade
    - orphan
- [x]  게시글 Repository를 구현한다. (PostRepository)

### **API를 구현한다.**

- [x]  게시글 조회
    - 페이징 조회 (GET "/posts")
    - 단건 조회 (GET "/posts/{id}")
- [x]  게시글 작성 (POST "/posts")
- [x]  게시글 수정 (POST "/posts/{id}")

### **REST-DOCS를 이용해서 문서화한다.**

- [x]  API 문서화

## TO DO :

- [ ] Pageable을 PageRequest로 변경합니다.

- [ ] BaseEntity의 creted_by를 Security의 인증과 연관시켜 구현합니다.

- [ ] 에러나 예외 상황에 대한 테스트 케이스 검증을 추가 합니다.

### 고민 중 :

entity에 대한 검증을 어떤 방식으로 구현을 해야할까?
- `@Valid`? 아니면 내부 메소드를 통한 검증?
