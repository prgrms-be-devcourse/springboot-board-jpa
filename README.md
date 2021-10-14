# [미션] Spring Boot JPA로 게시판 구현

## 미션 소개 😎

Spring Boot JPA - Rest API를 강의를 듣고, 게시판 구현 미션을 수행해봅시다. 최종 제출일은 10/19(일)입니다.

## Spec
- https://ancient-citron-06b.notion.site/SpringBoot-Part4-7-632dd10678164daca96a5ea185a02b60

### **SpringDataJPA 를 설정한다.**

- datasource : h2 or mysql

### **엔티티를 구성한다**

- 회원(User)
   - id (PK) (auto increment)
   - name
   - age
   - hobby
   - **created_at**
   - **created_by**
- 게시글(Post)
   - id (PK) (auto increment)
   - title
   - content
   - **created_at**
   - **created_by**
- 회원과 게시글에 대한 연관관계를 설정한다.
   - 회원과 게시글은 1:N 관계이다.
- 게시글 Repository를 구현한다. (PostRepository)

### **API를 구현한다.**

- 게시글 조회
   - 페이징 조회 (GET "/posts")
   - 단건 조회 (GET "/posts/{id}")
- 게시글 작성 (POST "/posts")
- 게시글 수정 (POST "/posts/{id}")

### **REST-DOCS를 이용해서 문서화한다.** 

# API Doc.
# 포스트

## 포스트 조회

**Request**

    GET /api/v1/posts/1 HTTP/1.1
    Host: localhost:8080

**Response**

    HTTP/1.1 200 OK
    Content-Type: application/json;charset=UTF-8
    Content-Length: 176

    {"success":true,"http_method":"GET","status_code":200,"data":{"title":"title","content":"content","post_id":1,"created_by":"username","created_at":"2021-10-14T01:26:36.49377"}}

<table>
<colgroup>
<col style="width: 33%" />
<col style="width: 33%" />
<col style="width: 33%" />
</colgroup>
<tbody>
<tr class="odd">
<td style="text-align: left;"><p>Path</p></td>
<td style="text-align: left;"><p>Type</p></td>
<td style="text-align: left;"><p>Description</p></td>
</tr>
<tr class="even">
<td style="text-align: left;"><p><code>success</code></p></td>
<td style="text-align: left;"><p><code>Boolean</code></p></td>
<td style="text-align: left;"><p>Is successful response</p></td>
</tr>
<tr class="odd">
<td style="text-align: left;"><p><code>status_code</code></p></td>
<td style="text-align: left;"><p><code>Number</code></p></td>
<td style="text-align: left;"><p>status code of response</p></td>
</tr>
<tr class="even">
<td style="text-align: left;"><p><code>http_method</code></p></td>
<td style="text-align: left;"><p><code>String</code></p></td>
<td style="text-align: left;"><p>http method of request</p></td>
</tr>
<tr class="odd">
<td style="text-align: left;"><p><code>data</code></p></td>
<td style="text-align: left;"><p><code>Object</code></p></td>
<td style="text-align: left;"><p>post info</p></td>
</tr>
<tr class="even">
<td style="text-align: left;"><p><code>data.title</code></p></td>
<td style="text-align: left;"><p><code>String</code></p></td>
<td style="text-align: left;"><p>title of post</p></td>
</tr>
<tr class="odd">
<td style="text-align: left;"><p><code>data.content</code></p></td>
<td style="text-align: left;"><p><code>String</code></p></td>
<td style="text-align: left;"><p>content of post</p></td>
</tr>
<tr class="even">
<td style="text-align: left;"><p><code>data.post_id</code></p></td>
<td style="text-align: left;"><p><code>Number</code></p></td>
<td style="text-align: left;"><p>post id</p></td>
</tr>
<tr class="odd">
<td style="text-align: left;"><p><code>data.created_by</code></p></td>
<td style="text-align: left;"><p><code>String</code></p></td>
<td style="text-align: left;"><p>post created by</p></td>
</tr>
<tr class="even">
<td style="text-align: left;"><p><code>data.created_at</code></p></td>
<td style="text-align: left;"><p><code>String</code></p></td>
<td style="text-align: left;"><p>post created at</p></td>
</tr>
</tbody>
</table>

## 포스트 페이지 조회

## /posts

**Request**

    GET /api/v1/posts?page=1&size=2 HTTP/1.1
    Host: localhost:8080

**Response**

    HTTP/1.1 200 OK
    Content-Type: application/json;charset=UTF-8
    Content-Length: 613

    {"success":true,"http_method":"GET","status_code":200,"data":{"content":[{"title":"title2","content":"content2","post_id":3,"created_by":"username","created_at":"2021-10-14T01:26:36.560556"},{"title":"title3","content":"content3","post_id":4,"created_by":"username","created_at":"2021-10-14T01:26:36.583204"}],"pageable":{"sort":{"empty":true,"sorted":false,"unsorted":true},"offset":2,"pageNumber":1,"pageSize":2,"paged":true,"unpaged":false},"totalPages":3,"totalElements":5,"last":false,"size":2,"number":1,"sort":{"empty":true,"sorted":false,"unsorted":true},"numberOfElements":2,"first":false,"empty":false}}

<table>
<colgroup>
<col style="width: 33%" />
<col style="width: 33%" />
<col style="width: 33%" />
</colgroup>
<tbody>
<tr class="odd">
<td style="text-align: left;"><p>Path</p></td>
<td style="text-align: left;"><p>Type</p></td>
<td style="text-align: left;"><p>Description</p></td>
</tr>
<tr class="even">
<td style="text-align: left;"><p><code>success</code></p></td>
<td style="text-align: left;"><p><code>Boolean</code></p></td>
<td style="text-align: left;"><p>Is successful response</p></td>
</tr>
<tr class="odd">
<td style="text-align: left;"><p><code>status_code</code></p></td>
<td style="text-align: left;"><p><code>Number</code></p></td>
<td style="text-align: left;"><p>status code of response</p></td>
</tr>
<tr class="even">
<td style="text-align: left;"><p><code>http_method</code></p></td>
<td style="text-align: left;"><p><code>String</code></p></td>
<td style="text-align: left;"><p>http method of request</p></td>
</tr>
<tr class="odd">
<td style="text-align: left;"><p><code>data</code></p></td>
<td style="text-align: left;"><p><code>Object</code></p></td>
<td style="text-align: left;"><p>data retrieved</p></td>
</tr>
<tr class="even">
<td style="text-align: left;"><p><code>data.content</code></p></td>
<td style="text-align: left;"><p><code>Array</code></p></td>
<td style="text-align: left;"><p>posts</p></td>
</tr>
<tr class="odd">
<td style="text-align: left;"><p><code>data.content[].title</code></p></td>
<td style="text-align: left;"><p><code>String</code></p></td>
<td style="text-align: left;"><p>title of post</p></td>
</tr>
<tr class="even">
<td style="text-align: left;"><p><code>data.content[].content</code></p></td>
<td style="text-align: left;"><p><code>String</code></p></td>
<td style="text-align: left;"><p>content of post</p></td>
</tr>
<tr class="odd">
<td style="text-align: left;"><p><code>data.content[].post_id</code></p></td>
<td style="text-align: left;"><p><code>Number</code></p></td>
<td style="text-align: left;"><p>post id</p></td>
</tr>
<tr class="even">
<td style="text-align: left;"><p><code>data.content[].created_by</code></p></td>
<td style="text-align: left;"><p><code>String</code></p></td>
<td style="text-align: left;"><p>post created by</p></td>
</tr>
<tr class="odd">
<td style="text-align: left;"><p><code>data.content[].created_at</code></p></td>
<td style="text-align: left;"><p><code>String</code></p></td>
<td style="text-align: left;"><p>post created at</p></td>
</tr>
<tr class="even">
<td style="text-align: left;"><p><code>data.pageable</code></p></td>
<td style="text-align: left;"><p><code>Object</code></p></td>
<td style="text-align: left;"><p>pagable</p></td>
</tr>
<tr class="odd">
<td style="text-align: left;"><p><code>data.pageable.sort</code></p></td>
<td style="text-align: left;"><p><code>Object</code></p></td>
<td style="text-align: left;"><p>data.pageable.sort</p></td>
</tr>
<tr class="even">
<td style="text-align: left;"><p><code>data.pageable.sort.empty</code></p></td>
<td style="text-align: left;"><p><code>Boolean</code></p></td>
<td style="text-align: left;"><p>data.pageable.sort.empty</p></td>
</tr>
<tr class="odd">
<td style="text-align: left;"><p><code>data.pageable.sort.sorted</code></p></td>
<td style="text-align: left;"><p><code>Boolean</code></p></td>
<td style="text-align: left;"><p>data.pageable.sort.sorted</p></td>
</tr>
<tr class="even">
<td style="text-align: left;"><p><code>data.pageable.sort.unsorted</code></p></td>
<td style="text-align: left;"><p><code>Boolean</code></p></td>
<td style="text-align: left;"><p>data.pageable.sort.unsorted</p></td>
</tr>
<tr class="odd">
<td style="text-align: left;"><p><code>data.pageable.offset</code></p></td>
<td style="text-align: left;"><p><code>Number</code></p></td>
<td style="text-align: left;"><p>data.pageable.offset</p></td>
</tr>
<tr class="even">
<td style="text-align: left;"><p><code>data.pageable.pageNumber</code></p></td>
<td style="text-align: left;"><p><code>Number</code></p></td>
<td style="text-align: left;"><p>data.pageable.pageNumber</p></td>
</tr>
<tr class="odd">
<td style="text-align: left;"><p><code>data.pageable.pageSize</code></p></td>
<td style="text-align: left;"><p><code>Number</code></p></td>
<td style="text-align: left;"><p>data.pageable.pageSize</p></td>
</tr>
<tr class="even">
<td style="text-align: left;"><p><code>data.pageable.paged</code></p></td>
<td style="text-align: left;"><p><code>Boolean</code></p></td>
<td style="text-align: left;"><p>data.pageable.paged</p></td>
</tr>
<tr class="odd">
<td style="text-align: left;"><p><code>data.pageable.unpaged</code></p></td>
<td style="text-align: left;"><p><code>Boolean</code></p></td>
<td style="text-align: left;"><p>data.pageable.unpaged</p></td>
</tr>
<tr class="even">
<td style="text-align: left;"><p><code>data.totalPages</code></p></td>
<td style="text-align: left;"><p><code>Number</code></p></td>
<td style="text-align: left;"><p>data.totalPages</p></td>
</tr>
<tr class="odd">
<td style="text-align: left;"><p><code>data.totalElements</code></p></td>
<td style="text-align: left;"><p><code>Number</code></p></td>
<td style="text-align: left;"><p>data.totalElements</p></td>
</tr>
<tr class="even">
<td style="text-align: left;"><p><code>data.last</code></p></td>
<td style="text-align: left;"><p><code>Boolean</code></p></td>
<td style="text-align: left;"><p>data.last</p></td>
</tr>
<tr class="odd">
<td style="text-align: left;"><p><code>data.size</code></p></td>
<td style="text-align: left;"><p><code>Number</code></p></td>
<td style="text-align: left;"><p>data.size</p></td>
</tr>
<tr class="even">
<td style="text-align: left;"><p><code>data.number</code></p></td>
<td style="text-align: left;"><p><code>Number</code></p></td>
<td style="text-align: left;"><p>data.number</p></td>
</tr>
<tr class="odd">
<td style="text-align: left;"><p><code>data.sort</code></p></td>
<td style="text-align: left;"><p><code>Object</code></p></td>
<td style="text-align: left;"><p>data.sort</p></td>
</tr>
<tr class="even">
<td style="text-align: left;"><p><code>data.sort.empty</code></p></td>
<td style="text-align: left;"><p><code>Boolean</code></p></td>
<td style="text-align: left;"><p>data.sort.empty</p></td>
</tr>
<tr class="odd">
<td style="text-align: left;"><p><code>data.sort.sorted</code></p></td>
<td style="text-align: left;"><p><code>Boolean</code></p></td>
<td style="text-align: left;"><p>data.sort.sorted</p></td>
</tr>
<tr class="even">
<td style="text-align: left;"><p><code>data.sort.unsorted</code></p></td>
<td style="text-align: left;"><p><code>Boolean</code></p></td>
<td style="text-align: left;"><p>data.sort.unsorted</p></td>
</tr>
<tr class="odd">
<td style="text-align: left;"><p><code>data.numberOfElements</code></p></td>
<td style="text-align: left;"><p><code>Number</code></p></td>
<td style="text-align: left;"><p>data.numberOfElements</p></td>
</tr>
<tr class="even">
<td style="text-align: left;"><p><code>data.first</code></p></td>
<td style="text-align: left;"><p><code>Boolean</code></p></td>
<td style="text-align: left;"><p>data.first</p></td>
</tr>
<tr class="odd">
<td style="text-align: left;"><p><code>data.empty</code></p></td>
<td style="text-align: left;"><p><code>Boolean</code></p></td>
<td style="text-align: left;"><p>data.empty</p></td>
</tr>
</tbody>
</table>

## 포스트 생성

## /posts

**Request**

    POST /api/v1/posts HTTP/1.1
    Content-Type: application/json;charset=UTF-8
    Content-Length: 69
    Host: localhost:8080

    {"id":null,"title":"title","content":"content","username":"username"}

<table>
<colgroup>
<col style="width: 33%" />
<col style="width: 33%" />
<col style="width: 33%" />
</colgroup>
<tbody>
<tr class="odd">
<td style="text-align: left;"><p>Path</p></td>
<td style="text-align: left;"><p>Type</p></td>
<td style="text-align: left;"><p>Description</p></td>
</tr>
<tr class="even">
<td style="text-align: left;"><p><code>id</code></p></td>
<td style="text-align: left;"><p><code>Null</code></p></td>
<td style="text-align: left;"><p>id (IGNORED)</p></td>
</tr>
<tr class="odd">
<td style="text-align: left;"><p><code>title</code></p></td>
<td style="text-align: left;"><p><code>String</code></p></td>
<td style="text-align: left;"><p>title</p></td>
</tr>
<tr class="even">
<td style="text-align: left;"><p><code>content</code></p></td>
<td style="text-align: left;"><p><code>String</code></p></td>
<td style="text-align: left;"><p>content</p></td>
</tr>
<tr class="odd">
<td style="text-align: left;"><p><code>username</code></p></td>
<td style="text-align: left;"><p><code>String</code></p></td>
<td style="text-align: left;"><p>username</p></td>
</tr>
</tbody>
</table>

**Response**

    HTTP/1.1 200 OK
    Content-Type: application/json;charset=UTF-8
    Content-Length: 178

    {"success":true,"http_method":"POST","status_code":200,"data":{"title":"title","content":"content","post_id":6,"created_by":"username","created_at":"2021-10-14T01:26:37.391299"}}

<table>
<colgroup>
<col style="width: 33%" />
<col style="width: 33%" />
<col style="width: 33%" />
</colgroup>
<tbody>
<tr class="odd">
<td style="text-align: left;"><p>Path</p></td>
<td style="text-align: left;"><p>Type</p></td>
<td style="text-align: left;"><p>Description</p></td>
</tr>
<tr class="even">
<td style="text-align: left;"><p><code>success</code></p></td>
<td style="text-align: left;"><p><code>Boolean</code></p></td>
<td style="text-align: left;"><p>Is successful response</p></td>
</tr>
<tr class="odd">
<td style="text-align: left;"><p><code>status_code</code></p></td>
<td style="text-align: left;"><p><code>Number</code></p></td>
<td style="text-align: left;"><p>status code of response</p></td>
</tr>
<tr class="even">
<td style="text-align: left;"><p><code>http_method</code></p></td>
<td style="text-align: left;"><p><code>String</code></p></td>
<td style="text-align: left;"><p>http method of request</p></td>
</tr>
<tr class="odd">
<td style="text-align: left;"><p><code>data</code></p></td>
<td style="text-align: left;"><p><code>Object</code></p></td>
<td style="text-align: left;"><p>created post info</p></td>
</tr>
<tr class="even">
<td style="text-align: left;"><p><code>data.title</code></p></td>
<td style="text-align: left;"><p><code>String</code></p></td>
<td style="text-align: left;"><p>title of post</p></td>
</tr>
<tr class="odd">
<td style="text-align: left;"><p><code>data.content</code></p></td>
<td style="text-align: left;"><p><code>String</code></p></td>
<td style="text-align: left;"><p>content of post</p></td>
</tr>
<tr class="even">
<td style="text-align: left;"><p><code>data.post_id</code></p></td>
<td style="text-align: left;"><p><code>Number</code></p></td>
<td style="text-align: left;"><p>post id</p></td>
</tr>
<tr class="odd">
<td style="text-align: left;"><p><code>data.created_by</code></p></td>
<td style="text-align: left;"><p><code>String</code></p></td>
<td style="text-align: left;"><p>post created by</p></td>
</tr>
<tr class="even">
<td style="text-align: left;"><p><code>data.created_at</code></p></td>
<td style="text-align: left;"><p><code>String</code></p></td>
<td style="text-align: left;"><p>post created at</p></td>
</tr>
</tbody>
</table>

## 포스트 수정

## /posts

**Request**

    PUT /api/v1/posts/1 HTTP/1.1
    Content-Type: application/json;charset=UTF-8
    Content-Length: 68
    Host: localhost:8080

    {"id":1,"title":"title2","content":"content2","username":"username"}

<table>
<colgroup>
<col style="width: 33%" />
<col style="width: 33%" />
<col style="width: 33%" />
</colgroup>
<tbody>
<tr class="odd">
<td style="text-align: left;"><p>Path</p></td>
<td style="text-align: left;"><p>Type</p></td>
<td style="text-align: left;"><p>Description</p></td>
</tr>
<tr class="even">
<td style="text-align: left;"><p><code>id</code></p></td>
<td style="text-align: left;"><p><code>Number</code></p></td>
<td style="text-align: left;"><p>id</p></td>
</tr>
<tr class="odd">
<td style="text-align: left;"><p><code>title</code></p></td>
<td style="text-align: left;"><p><code>String</code></p></td>
<td style="text-align: left;"><p>title</p></td>
</tr>
<tr class="even">
<td style="text-align: left;"><p><code>content</code></p></td>
<td style="text-align: left;"><p><code>String</code></p></td>
<td style="text-align: left;"><p>content</p></td>
</tr>
<tr class="odd">
<td style="text-align: left;"><p><code>username</code></p></td>
<td style="text-align: left;"><p><code>String</code></p></td>
<td style="text-align: left;"><p>username (IGNORED)</p></td>
</tr>
</tbody>
</table>

**Response**

    HTTP/1.1 200 OK
    Content-Type: application/json;charset=UTF-8
    Content-Length: 178

    {"success":true,"http_method":"PUT","status_code":200,"data":{"title":"title2","content":"content2","post_id":1,"created_by":"username","created_at":"2021-10-14T01:26:36.49377"}}

<table>
<colgroup>
<col style="width: 33%" />
<col style="width: 33%" />
<col style="width: 33%" />
</colgroup>
<tbody>
<tr class="odd">
<td style="text-align: left;"><p>Path</p></td>
<td style="text-align: left;"><p>Type</p></td>
<td style="text-align: left;"><p>Description</p></td>
</tr>
<tr class="even">
<td style="text-align: left;"><p><code>success</code></p></td>
<td style="text-align: left;"><p><code>Boolean</code></p></td>
<td style="text-align: left;"><p>Is successful response</p></td>
</tr>
<tr class="odd">
<td style="text-align: left;"><p><code>status_code</code></p></td>
<td style="text-align: left;"><p><code>Number</code></p></td>
<td style="text-align: left;"><p>status code of response</p></td>
</tr>
<tr class="even">
<td style="text-align: left;"><p><code>http_method</code></p></td>
<td style="text-align: left;"><p><code>String</code></p></td>
<td style="text-align: left;"><p>http method of request</p></td>
</tr>
<tr class="odd">
<td style="text-align: left;"><p><code>data</code></p></td>
<td style="text-align: left;"><p><code>Object</code></p></td>
<td style="text-align: left;"><p>updated post info</p></td>
</tr>
<tr class="even">
<td style="text-align: left;"><p><code>data.title</code></p></td>
<td style="text-align: left;"><p><code>String</code></p></td>
<td style="text-align: left;"><p>title of post</p></td>
</tr>
<tr class="odd">
<td style="text-align: left;"><p><code>data.content</code></p></td>
<td style="text-align: left;"><p><code>String</code></p></td>
<td style="text-align: left;"><p>content of post</p></td>
</tr>
<tr class="even">
<td style="text-align: left;"><p><code>data.post_id</code></p></td>
<td style="text-align: left;"><p><code>Number</code></p></td>
<td style="text-align: left;"><p>post id</p></td>
</tr>
<tr class="odd">
<td style="text-align: left;"><p><code>data.created_by</code></p></td>
<td style="text-align: left;"><p><code>String</code></p></td>
<td style="text-align: left;"><p>post created by</p></td>
</tr>
<tr class="even">
<td style="text-align: left;"><p><code>data.created_at</code></p></td>
<td style="text-align: left;"><p><code>String</code></p></td>
<td style="text-align: left;"><p>post created at</p></td>
</tr>
</tbody>
</table>

## 에러 응답

**Response**

    HTTP/1.1 400 Bad Request
    Content-Type: application/json;charset=UTF-8
    Content-Length: 109

    {"success":false,"http_method":"GET","status_code":400,"data":{"error_message":"No Post found with id: 999"}}

<table>
<colgroup>
<col style="width: 33%" />
<col style="width: 33%" />
<col style="width: 33%" />
</colgroup>
<tbody>
<tr class="odd">
<td style="text-align: left;"><p>Path</p></td>
<td style="text-align: left;"><p>Type</p></td>
<td style="text-align: left;"><p>Description</p></td>
</tr>
<tr class="even">
<td style="text-align: left;"><p><code>success</code></p></td>
<td style="text-align: left;"><p><code>Boolean</code></p></td>
<td style="text-align: left;"><p>Is successful response</p></td>
</tr>
<tr class="odd">
<td style="text-align: left;"><p><code>status_code</code></p></td>
<td style="text-align: left;"><p><code>Number</code></p></td>
<td style="text-align: left;"><p>status code of response</p></td>
</tr>
<tr class="even">
<td style="text-align: left;"><p><code>http_method</code></p></td>
<td style="text-align: left;"><p><code>String</code></p></td>
<td style="text-align: left;"><p>http method of request</p></td>
</tr>
<tr class="odd">
<td style="text-align: left;"><p><code>data</code></p></td>
<td style="text-align: left;"><p><code>Object</code></p></td>
<td style="text-align: left;"><p>error message</p></td>
</tr>
<tr class="even">
<td style="text-align: left;"><p><code>data.error_message</code></p></td>
<td style="text-align: left;"><p><code>String</code></p></td>
<td style="text-align: left;"><p>error details</p></td>
</tr>
</tbody>
</table>
