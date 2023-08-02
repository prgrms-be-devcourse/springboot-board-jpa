## 게시판 구현 미션 요구사항

### 1. 기본 요구사항

#### **SpringDataJPA 를 설정한다.**

- [x] datasource : h2

#### **엔티티를 구성한다**

- [x] 회원(User)
    - [x] id (PK) (auto increment)
    - [x] name
    - [x] age
    - [x] hobby
    - [x] **created_at**
    - [x] **created_by**
- [x] 게시글(Post)
    - [x] id (PK) (auto increment)
    - [x] title
    - [x] content
    - [x] **created_at**
    - [x] **created_by**
- [x] 회원과 게시글에 대한 연관관계를 설정한다.
    - [x] 회원과 게시글은 1:N 관계이다.
    - [x] 게시글 Repository를 구현한다. (PostRepository)

#### **API를 구현한다.**

- [x] 게시글 조회
    - [x] 페이징 조회 (GET "/posts")
    - [x] 단건 조회 (GET "/posts/{id}")
- [x] 게시글 작성 (POST "/posts")
- [x] 게시글 수정 (PATCH "/posts/{id}")

#### REST-DOCS를 이용해서 문서화한다.

- [x] 유저 API 문서화
- [x] 게시글 API 문서화

<hr>

### 2. 🔥 미션 요구사항 외 추가 구현 사항

**게시글**

- [x] 게시글 삭제 (DELETE "/posts/{id}")

**유저**

- [x] 유저 조회
    - [x] 페이징 조회 (GET "/users")
    - [x] 단건 조회 (GET "/users/{id}")
- [x] 유저 생성 (POST "/users")
- [x] 유저 수정 (UPDATE "/users/{id}")
- [x] 유저 삭제 (DELETE "/users/{id}")

**예외**

- [x] Custom Exception 구현
- [x] RestControllerAdvice를 통한 전역 예외 처리

**Entity**

- [x] Post, User Entity에 BaseEntity를 생성하여 MappedSuperclass 적용
    - [x] `@CreatedDate` 이용

**Mapper**

- [x] DTO와 Entity간 변환 작업 **MapStruct** 이용

**DTO**

- [x] `@Validate`을 통한 dto 검증 구현(검증 순서 적용)
- [x] 직렬화, 역직렬화시 EnumValue와 Enum의 변환이 가능하도록 `@JsonCreator`, `@JsonValue` 적용
- [x] 응답 코드의 통일성을 위해 ApiResponse 클래스 생성
