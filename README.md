## 📌 과제 설명 <!-- 어떤 걸 만들었는지 대략적으로 설명해주세요 -->
## 게시판 프로젝트

### 게시판을 개발하며 사용자들이 글을 작성하고 공유하며, 댓글 및 좋아요 기능이 포함된 REST API를 개발하였습니다.

### ERD
![image](https://github.com/prgrms-be-devcourse/springboot-board-jpa/assets/112048126/142df9f1-a2cc-410f-a82f-961b47385c9c)

## 👩‍💻 요구 사항과 구현 내용 <!-- 기능을 Commit 별로 잘개 쪼개고, Commit 별로 설명해주세요 -->

## 엔티티 및 매핑 관계

## 회원 (User)

- id (PK)
- name
- email
- password
- age
- hobby
- created_at
- updated_at

  - 일대다 관계: 여러 개의 게시글(Post)과 연결
  - 일대다 관계: 여러 개의 댓글(Comment)과 연결
  - 일대다 관계: 여러 개의 좋아요(Like)와 연결

## 게시글 (Post)

- id (PK)
- title
- content
- created_at
- updated_at

  - 다대일 관계: 회원(User)과 연결
  - 일대다 관계: 여러 개의 댓글(Comment)과 연결
  - 일대다 관계: 여러 개의 게시글 좋아요(PostLike)와 연결

## 댓글 (Comment)

- id (PK)
- content
- created_at
- updated_at

  - 다대일 관계: 회원(User)과 연결
  - 다대일 관계: 게시글(Post)과 연결
  - 일대다 관계: 여러 개의 댓글 좋아요(CommentLike)와 연결

## 좋아요 (Like) [추상 클래스]

- id (PK)
- like_status
- user_id(FK)

### 게시글 좋아요 (PostLike)

  - 다대일 관계: 회원(User)과 연결
  - 다대일 관계: 게시글(Post)과 연결

### 댓글 좋아요 (CommentLike)

  - 다대일 관계: 회원(User)과 연결
  - 다대일 관계: 댓글(Comment)과 연결
# API 설명

## 회원(User) API

### 회원 가입

- URL: `POST /users`
- Request Body: 사용자 정보를 담은 요청 데이터
- Response: 생성된 회원의 ID

### 회원 조회

- URL: `GET /users/{userId}`
- Path Variable: 조회할 회원의 ID
- Response: 회원 정보

### 회원 정보 수정

- URL: `PATCH /users/{userId}`
- Path Variable: 수정할 회원의 ID
- Request Body: 수정할 사용자 정보를 담은 요청 데이터
- Response: 수정 완료

### 회원 삭제

- URL: `DELETE /users/{userId}`
- Path Variable: 삭제할 회원의 ID
- Response: 삭제 완료

## 게시글(Post) API

### 게시글 생성

- URL: `POST /posts`
- Request Body: 게시글 정보를 담은 요청 데이터
- Response: 생성된 게시글의 ID

### 게시글 페이징 조회

- URL: `GET /posts`
- Query Parameter: 페이지 번호, 페이지당 아이템 수 등
- Response: 페이지별 게시글 목록

### 게시글 단건 조회

- URL: `GET /posts/{postId}`
- Path Variable: 조회할 게시글의 ID
- Response: 게시글 상세 정보

### 게시글 및 댓글 조회

- URL: `GET /posts/{postId}/comments`
- Path Variable: 조회할 게시글의 ID
- Response: 게시글과 관련된 댓글 목록 포함한 상세 정보

### 게시글 수정

- URL: `PATCH /posts/{postId}`
- Path Variable: 수정할 게시글의 ID
- Request Body: 수정할 게시글 정보를 담은 요청 데이터
- Response: 수정 완료

### 게시글 삭제

- URL: `DELETE /posts/{postId}`
- Path Variable: 삭제할 게시글의 ID
- Response: 삭제 완료

## 댓글(Comment) API

### 댓글 생성

- URL: `POST /comments`
- Request Body: 댓글 정보를 담은 요청 데이터
- Response: 생성된 댓글의 ID

### 댓글 삭제

- URL: `DELETE /comments/{commentId}`
- Path Variable: 삭제할 댓글의 ID
- Response: 삭제 완료

## 좋아요(Like) API

### 게시글 좋아요 생성

- URL: `POST /likes/posts`
- Request Body: 게시글 좋아요 정보를 담은 요청 데이터
- Response: 생성된 좋아요의 ID


## ✅ PR 포인트 & 궁금한 점 <!-- 리뷰어 분들이 집중적으로 보셨으면 하는 내용을 적어주세요 -->
- 전체적으로 엔티티 매핑이 적절하게 되었는지 궁굼합니다(개인적으로 좋아요 엔티티를 설계하는 과정에서 어려움을 겪어 피드백을 받은 후 다시 구현 하고 싶습니다)
- 평소에 Service에서는 Repository를 주입받아 구현하고 있었는데 이번 프로젝트에서는 Service를 주입받아 중복된 로직을 제거한 뒤 구현하였는데 순환참조의 방지를 확실하게 맊을수 있다면 서비스 계층에서 서비스 계층의 의존이 가능한지 가능하다면 제가 구현한 방법이 적절한지 여쭤보고 싶습닏가.
- 평소에 엔티티 변경 과정에 있어서 dirty Checking 방식을 사용하지 않고 merge 방식으로 엔티티를 변경했으나 엔티티 변경 과정에서는 dirty Checking 방식을 사용하는 것이 권장된다고 하여 엔티티를 변경하였으나 RequestDto에 @Validation으로 검증 로직을 구현하면 동적으로 엔티티를 변경하는것에 어려움이 발생하였습니다. 이과정을 어떻게 해결해야하는지 궁굼합니다.
- 감사합니다.

