# Spring Boot JPA로 게시판 구현

## 프로젝트 설명
- Spring Data JPA를 설정한다.(h2 database 사용)
- 엔티티를 구성한다.
- API를 구현한다.
- 테스트코드를 작성한다.
- REST-DOCS를 이용해 문서화 한다.
---
## 개발 환경
* 개발 언어 : Java 11
* 프레임워크 : SpringBoot 2.4.5
* 빌드도구 : Gradle 7.2
---
## 요구 사항과 구현 내용
### 엔티티 구성
- 회원(User)
    - id (PK) (auto increment)
    - name
    - age
    - hobby
    - postList
    - created_at
    - modifiedAt
- 게시글(Post)
    - id (PK) (auto increment)
    - title
    - content
    - user
    - created_at
    - modifiedAt
    
- 게시글과 회원에 대한 연관관계를 설정한다.
    - 게시글과 회원은 N : 1 양방향 관계이다.

---

## API 구현

### 게시글 작성

#### (POST) /api/posts

Request

    POST /api/posts HTTP/1.1
    Content-Type: application/json;charset=UTF-8
    Accept: application/json
    Content-Length: 39
    Host: localhost:8080
    Cookie: userid=1
    
    {"title":"제목1","content":"내용1"}

<table class="tableblock frame-all grid-all stretch"><colgroup><col style="width: 33.3333%;"> <col style="width: 33.3333%;"> <col style="width: 33.3334%;"></colgroup> 

<thead>

<tr>

<th class="tableblock halign-left valign-top">Path</th>

<th class="tableblock halign-left valign-top">Type</th>

<th class="tableblock halign-left valign-top">Description</th>

</tr>

</thead>

<tbody>

<tr>

<td class="tableblock halign-left valign-top">

`title`

</td>

<td class="tableblock halign-left valign-top">

`String`

</td>

<td class="tableblock halign-left valign-top">

게시글 제목

</td>

</tr>

<tr>

<td class="tableblock halign-left valign-top">

`content`

</td>

<td class="tableblock halign-left valign-top">

`String`

</td>

<td class="tableblock halign-left valign-top">

게시글 내용

</td>

</tr>

</tbody>

</table>

Response

    HTTP/1.1 200 OK
    Content-Type: application/json;charset=UTF-8
    Content-Length: 102
    
    {"statusCode":200,"message":"게시글 생성 성공","data":1,"serverDatetime":"2021-10-15 22:23:10"}

<table class="tableblock frame-all grid-all stretch"><colgroup><col style="width: 33.3333%;"> <col style="width: 33.3333%;"> <col style="width: 33.3334%;"></colgroup> 

<thead>

<tr>

<th class="tableblock halign-left valign-top">Path</th>

<th class="tableblock halign-left valign-top">Type</th>

<th class="tableblock halign-left valign-top">Description</th>

</tr>

</thead>

<tbody>

<tr>

<td class="tableblock halign-left valign-top">

`statusCode`

</td>

<td class="tableblock halign-left valign-top">

`Number`

</td>

<td class="tableblock halign-left valign-top">

HTTP 상태코드

</td>

</tr>

<tr>

<td class="tableblock halign-left valign-top">

`message`

</td>

<td class="tableblock halign-left valign-top">

`String`

</td>

<td class="tableblock halign-left valign-top">

응답 설명

</td>

</tr>

<tr>

<td class="tableblock halign-left valign-top">

`data`

</td>

<td class="tableblock halign-left valign-top">

`Number`

</td>

<td class="tableblock halign-left valign-top">

작성된 게시글 번호

</td>

</tr>

<tr>

<td class="tableblock halign-left valign-top">

`serverDatetime`

</td>

<td class="tableblock halign-left valign-top">

`String`

</td>

<td class="tableblock halign-left valign-top">

서버 응답시간

</td>

</tr>

</tbody>

</table>

### 게시글 수정

#### (PUT) /api/posts/{id}

Request

    PUT /api/posts/1 HTTP/1.1
    Content-Type: application/json;charset=UTF-8
    Accept: application/json
    Content-Length: 51
    Host: localhost:8080
    Cookie: userid=1
    
    {"title":"수정제목1","content":"수정내용1"}

<table class="tableblock frame-all grid-all stretch"><colgroup><col style="width: 33.3333%;"> <col style="width: 33.3333%;"> <col style="width: 33.3334%;"></colgroup> 

<thead>

<tr>

<th class="tableblock halign-left valign-top">Path</th>

<th class="tableblock halign-left valign-top">Type</th>

<th class="tableblock halign-left valign-top">Description</th>

</tr>

</thead>

<tbody>

<tr>

<td class="tableblock halign-left valign-top">

`title`

</td>

<td class="tableblock halign-left valign-top">

`String`

</td>

<td class="tableblock halign-left valign-top">

게시글 제목

</td>

</tr>

<tr>

<td class="tableblock halign-left valign-top">

`content`

</td>

<td class="tableblock halign-left valign-top">

`String`

</td>

<td class="tableblock halign-left valign-top">

게시글 내용

</td>

</tr>

</tbody>

</table>

Response

    HTTP/1.1 200 OK
    Content-Type: application/json;charset=UTF-8
    Content-Length: 102
    
    {"statusCode":200,"message":"게시글 수정 성공","data":1,"serverDatetime":"2021-10-15 22:23:10"}

<table class="tableblock frame-all grid-all stretch"><colgroup><col style="width: 33.3333%;"> <col style="width: 33.3333%;"> <col style="width: 33.3334%;"></colgroup> 

<thead>

<tr>

<th class="tableblock halign-left valign-top">Path</th>

<th class="tableblock halign-left valign-top">Type</th>

<th class="tableblock halign-left valign-top">Description</th>

</tr>

</thead>

<tbody>

<tr>

<td class="tableblock halign-left valign-top">

`statusCode`

</td>

<td class="tableblock halign-left valign-top">

`Number`

</td>

<td class="tableblock halign-left valign-top">

HTTP 상태코드

</td>

</tr>

<tr>

<td class="tableblock halign-left valign-top">

`message`

</td>

<td class="tableblock halign-left valign-top">

`String`

</td>

<td class="tableblock halign-left valign-top">

응답 설명

</td>

</tr>

<tr>

<td class="tableblock halign-left valign-top">

`data`

</td>

<td class="tableblock halign-left valign-top">

`Number`

</td>

<td class="tableblock halign-left valign-top">

수정된 게시글 번호

</td>

</tr>

<tr>

<td class="tableblock halign-left valign-top">

`serverDatetime`

</td>

<td class="tableblock halign-left valign-top">

`String`

</td>

<td class="tableblock halign-left valign-top">

서버 응답시간

</td>

</tr>

</tbody>

</table>

### 게시글 단건 조회
#### (GET) /api/posts/{id}

Request

    GET /api/posts/1 HTTP/1.1
    Host: localhost:8080

Response

    HTTP/1.1 200 OK
    Content-Type: application/json;charset=UTF-8
    Content-Length: 359
    
    {"statusCode":200,"message":"게시글 단건 조회 성공","data":{"id":1,"title":"제목1","content":"내용1","userResponse":{"userId":1,"name":"유저1","age":25,"hobby":"TV","createdAt":"2021-10-15T22:23:10","modifiedAt":"2021-10-15T22:23:10"},"createdAt":"2021-10-15T22:23:10","modifiedAt":"2021-10-15T22:23:10"},"serverDatetime":"2021-10-15 22:23:10"}

<table class="tableblock frame-all grid-all stretch"><colgroup><col style="width: 33.3333%;"> <col style="width: 33.3333%;"> <col style="width: 33.3334%;"></colgroup> 

<thead>

<tr>

<th class="tableblock halign-left valign-top">Path</th>

<th class="tableblock halign-left valign-top">Type</th>

<th class="tableblock halign-left valign-top">Description</th>

</tr>

</thead>

<tbody>

<tr>

<td class="tableblock halign-left valign-top">

`statusCode`

</td>

<td class="tableblock halign-left valign-top">

`Number`

</td>

<td class="tableblock halign-left valign-top">

HTTP 상태코드

</td>

</tr>

<tr>

<td class="tableblock halign-left valign-top">

`message`

</td>

<td class="tableblock halign-left valign-top">

`String`

</td>

<td class="tableblock halign-left valign-top">

응답 설명

</td>

</tr>

<tr>

<td class="tableblock halign-left valign-top">

`data`

</td>

<td class="tableblock halign-left valign-top">

`Object`

</td>

<td class="tableblock halign-left valign-top">

응답 데이터

</td>

</tr>

<tr>

<td class="tableblock halign-left valign-top">

`data.id`

</td>

<td class="tableblock halign-left valign-top">

`Number`

</td>

<td class="tableblock halign-left valign-top">

게시글 번호

</td>

</tr>

<tr>

<td class="tableblock halign-left valign-top">

`data.title`

</td>

<td class="tableblock halign-left valign-top">

`String`

</td>

<td class="tableblock halign-left valign-top">

게시글 제목

</td>

</tr>

<tr>

<td class="tableblock halign-left valign-top">

`data.content`

</td>

<td class="tableblock halign-left valign-top">

`String`

</td>

<td class="tableblock halign-left valign-top">

게시글 내용

</td>

</tr>

<tr>

<td class="tableblock halign-left valign-top">

`data.userResponse`

</td>

<td class="tableblock halign-left valign-top">

`Object`

</td>

<td class="tableblock halign-left valign-top">

게시글 작성자 정보

</td>

</tr>

<tr>

<td class="tableblock halign-left valign-top">

`data.userResponse.userId`

</td>

<td class="tableblock halign-left valign-top">

`Number`

</td>

<td class="tableblock halign-left valign-top">

게시글 작성자 번호

</td>

</tr>

<tr>

<td class="tableblock halign-left valign-top">

`data.userResponse.name`

</td>

<td class="tableblock halign-left valign-top">

`String`

</td>

<td class="tableblock halign-left valign-top">

게시글 작성자 이름

</td>

</tr>

<tr>

<td class="tableblock halign-left valign-top">

`data.userResponse.age`

</td>

<td class="tableblock halign-left valign-top">

`Number`

</td>

<td class="tableblock halign-left valign-top">

게시글 작성자 나이

</td>

</tr>

<tr>

<td class="tableblock halign-left valign-top">

`data.userResponse.hobby`

</td>

<td class="tableblock halign-left valign-top">

`String`

</td>

<td class="tableblock halign-left valign-top">

게시글 작성자 취미

</td>

</tr>

<tr>

<td class="tableblock halign-left valign-top">

`data.userResponse.createdAt`

</td>

<td class="tableblock halign-left valign-top">

`String`

</td>

<td class="tableblock halign-left valign-top">

게시글 작성자 회원가입 시간

</td>

</tr>

<tr>

<td class="tableblock halign-left valign-top">

`data.userResponse.modifiedAt`

</td>

<td class="tableblock halign-left valign-top">

`String`

</td>

<td class="tableblock halign-left valign-top">

게시글 작성자 마지막 회원정보 수정 시간

</td>

</tr>

<tr>

<td class="tableblock halign-left valign-top">

`data.createdAt`

</td>

<td class="tableblock halign-left valign-top">

`String`

</td>

<td class="tableblock halign-left valign-top">

게시글 작성 시간

</td>

</tr>

<tr>

<td class="tableblock halign-left valign-top">

`data.modifiedAt`

</td>

<td class="tableblock halign-left valign-top">

`String`

</td>

<td class="tableblock halign-left valign-top">

게시글 마지막 수정 시간

</td>

</tr>

<tr>

<td class="tableblock halign-left valign-top">

`serverDatetime`

</td>

<td class="tableblock halign-left valign-top">

`String`

</td>

<td class="tableblock halign-left valign-top">

서버 응답시간

</td>

</tr>

</tbody>

</table>

### 게시글 다건 조회
#### (GET) /api/posts

Request

    GET /api/posts HTTP/1.1
    Host: localhost:8080

Response

    HTTP/1.1 200 OK
    Content-Type: application/json;charset=UTF-8
    Content-Length: 676
    
    {"statusCode":200,"message":"게시글 다건 조회 성공","data":{"content":[{"id":1,"title":"제목1","content":"내용1","userResponse":{"userId":1,"name":"유저1","age":25,"hobby":"TV","createdAt":"2021-10-15T22:23:08","modifiedAt":"2021-10-15T22:23:08"},"createdAt":"2021-10-15T22:23:08","modifiedAt":"2021-10-15T22:23:08"}],"pageable":{"sort":{"sorted":false,"unsorted":true,"empty":true},"offset":0,"pageNumber":0,"pageSize":10,"paged":true,"unpaged":false},"last":true,"totalPages":1,"totalElements":10,"size":10,"number":0,"sort":{"sorted":false,"unsorted":true,"empty":true},"numberOfElements":1,"first":true,"empty":false},"serverDatetime":"2021-10-15 22:23:10"}

<table class="tableblock frame-all grid-all stretch"><colgroup><col style="width: 33.3333%;"> <col style="width: 33.3333%;"> <col style="width: 33.3334%;"></colgroup> 

<thead>

<tr>

<th class="tableblock halign-left valign-top">Path</th>

<th class="tableblock halign-left valign-top">Type</th>

<th class="tableblock halign-left valign-top">Description</th>

</tr>

</thead>

<tbody>

<tr>

<td class="tableblock halign-left valign-top">

`statusCode`

</td>

<td class="tableblock halign-left valign-top">

`Number`

</td>

<td class="tableblock halign-left valign-top">

HTTP 상태코드

</td>

</tr>

<tr>

<td class="tableblock halign-left valign-top">

`message`

</td>

<td class="tableblock halign-left valign-top">

`String`

</td>

<td class="tableblock halign-left valign-top">

응답 설명

</td>

</tr>

<tr>

<td class="tableblock halign-left valign-top">

`data`

</td>

<td class="tableblock halign-left valign-top">

`Object`

</td>

<td class="tableblock halign-left valign-top">

응답 데이터

</td>

</tr>

<tr>

<td class="tableblock halign-left valign-top">

`data.content`

</td>

<td class="tableblock halign-left valign-top">

`Array`

</td>

<td class="tableblock halign-left valign-top">

해당 페이지의 게시글 정보

</td>

</tr>

<tr>

<td class="tableblock halign-left valign-top">

`data.content.[].id`

</td>

<td class="tableblock halign-left valign-top">

`Number`

</td>

<td class="tableblock halign-left valign-top">

게시글 번호

</td>

</tr>

<tr>

<td class="tableblock halign-left valign-top">

`data.content.[].title`

</td>

<td class="tableblock halign-left valign-top">

`String`

</td>

<td class="tableblock halign-left valign-top">

게시글 제목

</td>

</tr>

<tr>

<td class="tableblock halign-left valign-top">

`data.content.[].content`

</td>

<td class="tableblock halign-left valign-top">

`String`

</td>

<td class="tableblock halign-left valign-top">

게시글 내용

</td>

</tr>

<tr>

<td class="tableblock halign-left valign-top">

`data.content.[].userResponse`

</td>

<td class="tableblock halign-left valign-top">

`Object`

</td>

<td class="tableblock halign-left valign-top">

게시글 작성자 정보

</td>

</tr>

<tr>

<td class="tableblock halign-left valign-top">

`data.content.[].userResponse.userId`

</td>

<td class="tableblock halign-left valign-top">

`Number`

</td>

<td class="tableblock halign-left valign-top">

게시글 작성자 번호

</td>

</tr>

<tr>

<td class="tableblock halign-left valign-top">

`data.content.[].userResponse.name`

</td>

<td class="tableblock halign-left valign-top">

`String`

</td>

<td class="tableblock halign-left valign-top">

게시글 작성자 이름

</td>

</tr>

<tr>

<td class="tableblock halign-left valign-top">

`data.content.[].userResponse.age`

</td>

<td class="tableblock halign-left valign-top">

`Number`

</td>

<td class="tableblock halign-left valign-top">

게시글 작성자 나이

</td>

</tr>

<tr>

<td class="tableblock halign-left valign-top">

`data.content.[].userResponse.hobby`

</td>

<td class="tableblock halign-left valign-top">

`String`

</td>

<td class="tableblock halign-left valign-top">

게시글 작성자 취미

</td>

</tr>

<tr>

<td class="tableblock halign-left valign-top">

`data.content.[].userResponse.createdAt`

</td>

<td class="tableblock halign-left valign-top">

`String`

</td>

<td class="tableblock halign-left valign-top">

게시글 작성자 회원가입 시간

</td>

</tr>

<tr>

<td class="tableblock halign-left valign-top">

`data.content.[].userResponse.modifiedAt`

</td>

<td class="tableblock halign-left valign-top">

`String`

</td>

<td class="tableblock halign-left valign-top">

게시글 작성자 마지막 회원정보 수정 시간

</td>

</tr>

<tr>

<td class="tableblock halign-left valign-top">

`data.content.[].createdAt`

</td>

<td class="tableblock halign-left valign-top">

`String`

</td>

<td class="tableblock halign-left valign-top">

게시글 작성 시간

</td>

</tr>

<tr>

<td class="tableblock halign-left valign-top">

`data.content.[].modifiedAt`

</td>

<td class="tableblock halign-left valign-top">

`String`

</td>

<td class="tableblock halign-left valign-top">

게시글 마지막 수정 시간

</td>

</tr>

<tr>

<td class="tableblock halign-left valign-top">

`data.pageable`

</td>

<td class="tableblock halign-left valign-top">

`Object`

</td>

<td class="tableblock halign-left valign-top">

페이징 옵션 정보

</td>

</tr>

<tr>

<td class="tableblock halign-left valign-top">

`data.pageable.sort`

</td>

<td class="tableblock halign-left valign-top">

`Object`

</td>

<td class="tableblock halign-left valign-top">

페이징 옵션 - 정렬 정보

</td>

</tr>

<tr>

<td class="tableblock halign-left valign-top">

`data.pageable.sort.empty`

</td>

<td class="tableblock halign-left valign-top">

`Boolean`

</td>

<td class="tableblock halign-left valign-top">

정렬조건 존재 여부

</td>

</tr>

<tr>

<td class="tableblock halign-left valign-top">

`data.pageable.sort.sorted`

</td>

<td class="tableblock halign-left valign-top">

`Boolean`

</td>

<td class="tableblock halign-left valign-top">

정렬 되었는지 여부

</td>

</tr>

<tr>

<td class="tableblock halign-left valign-top">

`data.pageable.sort.unsorted`

</td>

<td class="tableblock halign-left valign-top">

`Boolean`

</td>

<td class="tableblock halign-left valign-top">

정렬 안 되었는지 여부

</td>

</tr>

<tr>

<td class="tableblock halign-left valign-top">

`data.pageable.offset`

</td>

<td class="tableblock halign-left valign-top">

`Number`

</td>

<td class="tableblock halign-left valign-top">

해당 페이지의 첫 번째 원소의 수

</td>

</tr>

<tr>

<td class="tableblock halign-left valign-top">

`data.pageable.pageNumber`

</td>

<td class="tableblock halign-left valign-top">

`Number`

</td>

<td class="tableblock halign-left valign-top">

페이지 번호

</td>

</tr>

<tr>

<td class="tableblock halign-left valign-top">

`data.pageable.pageSize`

</td>

<td class="tableblock halign-left valign-top">

`Number`

</td>

<td class="tableblock halign-left valign-top">

한 페이지당 게시글 수

</td>

</tr>

<tr>

<td class="tableblock halign-left valign-top">

`data.pageable.paged`

</td>

<td class="tableblock halign-left valign-top">

`Boolean`

</td>

<td class="tableblock halign-left valign-top">

페이징 처리 했는지 여부

</td>

</tr>

<tr>

<td class="tableblock halign-left valign-top">

`data.pageable.unpaged`

</td>

<td class="tableblock halign-left valign-top">

`Boolean`

</td>

<td class="tableblock halign-left valign-top">

페이징 처리 안 했는지 여부

</td>

</tr>

<tr>

<td class="tableblock halign-left valign-top">

`data.last`

</td>

<td class="tableblock halign-left valign-top">

`Boolean`

</td>

<td class="tableblock halign-left valign-top">

마지막 페이지인지 여부

</td>

</tr>

<tr>

<td class="tableblock halign-left valign-top">

`data.totalPages`

</td>

<td class="tableblock halign-left valign-top">

`Number`

</td>

<td class="tableblock halign-left valign-top">

전체 페이지 수

</td>

</tr>

<tr>

<td class="tableblock halign-left valign-top">

`data.totalElements`

</td>

<td class="tableblock halign-left valign-top">

`Number`

</td>

<td class="tableblock halign-left valign-top">

페이지 수에 따른 최대 게시글 수

</td>

</tr>

<tr>

<td class="tableblock halign-left valign-top">

`data.size`

</td>

<td class="tableblock halign-left valign-top">

`Number`

</td>

<td class="tableblock halign-left valign-top">

페이지 내 최대 게시글 수

</td>

</tr>

<tr>

<td class="tableblock halign-left valign-top">

`data.number`

</td>

<td class="tableblock halign-left valign-top">

`Number`

</td>

<td class="tableblock halign-left valign-top">

현재 페이지 번호

</td>

</tr>

<tr>

<td class="tableblock halign-left valign-top">

`data.sort`

</td>

<td class="tableblock halign-left valign-top">

`Object`

</td>

<td class="tableblock halign-left valign-top">

현재 페이지 정렬 설정

</td>

</tr>

<tr>

<td class="tableblock halign-left valign-top">

`data.sort.empty`

</td>

<td class="tableblock halign-left valign-top">

`Boolean`

</td>

<td class="tableblock halign-left valign-top">

현재 페이지 정렬 기준 존재 여부

</td>

</tr>

<tr>

<td class="tableblock halign-left valign-top">

`data.sort.sorted`

</td>

<td class="tableblock halign-left valign-top">

`Boolean`

</td>

<td class="tableblock halign-left valign-top">

현재 페이지 정렬 여부

</td>

</tr>

<tr>

<td class="tableblock halign-left valign-top">

`data.sort.unsorted`

</td>

<td class="tableblock halign-left valign-top">

`Boolean`

</td>

<td class="tableblock halign-left valign-top">

현재 페이지 비정렬 여부

</td>

</tr>

<tr>

<td class="tableblock halign-left valign-top">

`data.first`

</td>

<td class="tableblock halign-left valign-top">

`Boolean`

</td>

<td class="tableblock halign-left valign-top">

첫 번째 페이지인지 여부

</td>

</tr>

<tr>

<td class="tableblock halign-left valign-top">

`data.numberOfElements`

</td>

<td class="tableblock halign-left valign-top">

`Number`

</td>

<td class="tableblock halign-left valign-top">

현재 페이지에 있는 게시글 수

</td>

</tr>

<tr>

<td class="tableblock halign-left valign-top">

`data.empty`

</td>

<td class="tableblock halign-left valign-top">

`Boolean`

</td>

<td class="tableblock halign-left valign-top">

페이지에 게시글이 없는지 여부

</td>

</tr>

<tr>

<td class="tableblock halign-left valign-top">

`serverDatetime`

</td>

<td class="tableblock halign-left valign-top">

`String`

</td>

<td class="tableblock halign-left valign-top">

서버 응답시간

</td>

</tr>

</tbody>

</table>