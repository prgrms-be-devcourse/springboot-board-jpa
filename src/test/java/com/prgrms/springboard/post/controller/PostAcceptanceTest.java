package com.prgrms.springboard.post.controller;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.jdbc.Sql;

import com.prgrms.springboard.AcceptanceTest;
import com.prgrms.springboard.post.dto.CreatePostRequest;
import com.prgrms.springboard.post.dto.ModifyPostRequest;
import com.prgrms.springboard.post.dto.PostResponse;
import com.prgrms.springboard.post.dto.PostsResponse;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

@Sql({"classpath:db/init.sql", "classpath:db/data_user.sql"})
class PostAcceptanceTest extends AcceptanceTest {

    private static final CreatePostRequest CREATE_POST_REQUEST = new CreatePostRequest(1L, "제목입니다.", "내용입니다.");
    private static final ModifyPostRequest MODIFY_POST_REQUEST = new ModifyPostRequest(1L, "수정 제목입니다.", "수정 내용입니다.");
    private static final String POST_URI = "api/v1/posts";
    private static final String POST_URI_WITH_ID = POST_URI + "/{id}";

    @DisplayName("게시글을 작성한다.")
    @Test
    void createPost() {
        // given
        // when
        ExtractableResponse<Response> createResponse = RestAssured.given(this.spec).log().all()
            .body(CREATE_POST_REQUEST)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .filter(document("post-save",
                requestFields(
                    fieldWithPath("userId").description("회원 번호"),
                    fieldWithPath("title").description("제목"),
                    fieldWithPath("content").description("내용")
                ),
                responseFields(
                    fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                    fieldWithPath("data").type(JsonFieldType.NUMBER).description("게시글 아이디"),
                    fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("응답시간")
                )
            ))
            .when().post(POST_URI)
            .then().log().all()
            .extract();

        // then
        assertThat(createResponse.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(extractId(createResponse)).isEqualTo(1L);
    }

    @DisplayName("게시글을 단건 조회한다.")
    @Test
    void findPost() {
        // given
        ExtractableResponse<Response> createResponse = post(POST_URI, CREATE_POST_REQUEST);
        Long postId = extractId(createResponse);

        // when
        ExtractableResponse<Response> findResponse = RestAssured.given(this.spec).log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .filter(document("post-findOne",
                pathParameters(
                    parameterWithName("id").description("게시글 번호")
                ),
                responseFields(
                    fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                    fieldWithPath("data").type(JsonFieldType.OBJECT).description("게시글"),
                    fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("게시글 번호"),
                    fieldWithPath("data.title").type(JsonFieldType.STRING).description("게시글 제목"),
                    fieldWithPath("data.content").type(JsonFieldType.STRING).description("게시글 내용"),
                    fieldWithPath("data.user").type(JsonFieldType.OBJECT).description("작성 회원"),
                    fieldWithPath("data.user.id").type(JsonFieldType.NUMBER).description("작성 회원 번호"),
                    fieldWithPath("data.user.name").type(JsonFieldType.STRING).description("작성 회원 이름"),
                    fieldWithPath("data.user.age").type(JsonFieldType.NUMBER).description("작성 회원 나이"),
                    fieldWithPath("data.user.hobby").type(JsonFieldType.STRING).description("작성 회원 취미"),
                    fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("응답시간")
                )
            ))
            .when().get(POST_URI_WITH_ID, postId)
            .then().log().all()
            .extract();

        // then
        PostResponse postResponse = findResponse.jsonPath().getObject("data", PostResponse.class);
        assertAll(
            () -> assertThat(findResponse.statusCode()).isEqualTo(HttpStatus.OK.value()),
            () -> assertThat(postResponse.getId()).isEqualTo(postId),
            () -> assertThat(postResponse.getUser().getId()).isEqualTo(1L)
        );
    }

    @DisplayName("존재하지 않는 게시글 조회 시 NOT_FOUND 응답코드를 반환한다.")
    @Test
    void findPost_NotFound() {
        // given
        Long postId = 2L;

        // when
        ExtractableResponse<Response> findResponse = RestAssured.given(this.spec).log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .filter(document("post-notFoundError",
                pathParameters(
                    parameterWithName("id").description("게시글 번호")
                ),
                responseFields(
                    fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                    fieldWithPath("data").type(JsonFieldType.STRING).description("에러 메시지"),
                    fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("응답시간")
                )
            ))
            .when().get(POST_URI_WITH_ID, postId)
            .then().log().all()
            .extract();

        // then
        assertAll(
            () -> assertThat(findResponse.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value()),
            () -> assertThat(extractMessage(findResponse)).isEqualTo("ID가 2인 게시글은 없습니다.")
        );

    }

    @DisplayName("게시글을 페이징 조회한다.")
    @Test
    void findPosts() {
        // given
        for (int i = 0; i < 30; i++) {
            post(POST_URI, CREATE_POST_REQUEST);
        }
        Map<String, Object> params = new HashMap<>();
        params.put("page", 1);
        params.put("size", 10);
        params.put("direction", Sort.Direction.ASC);

        // when
        ExtractableResponse<Response> pageResponse = RestAssured.given(this.spec).log().all()
            .params(params)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .filter(document("post-page",
                requestParameters(
                    parameterWithName("page").description("페이지 번호"),
                    parameterWithName("size").description("한 페이지의 게시글 수"),
                    parameterWithName("direction").description("정렬").optional()
                ),
                responseFields(
                    fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                    fieldWithPath("data").type(JsonFieldType.OBJECT).description("게시글 응답 데이터"),
                    fieldWithPath("data.totalRowCount").type(JsonFieldType.NUMBER).description("게시글 총 갯수"),
                    fieldWithPath("data.pageSize").type(JsonFieldType.NUMBER).description("페이지 게시글 수"),
                    fieldWithPath("data.pageNumber").type(JsonFieldType.NUMBER).description("페이지 번호"),
                    fieldWithPath("data.sort").type(JsonFieldType.BOOLEAN).description("정렬 유무"),
                    fieldWithPath("data.posts").type(JsonFieldType.ARRAY).description("게시글 리스트"),
                    fieldWithPath("data.posts[].id").type(JsonFieldType.NUMBER).description("게시글 번호"),
                    fieldWithPath("data.posts[].title").type(JsonFieldType.STRING).description("게시글 제목"),
                    fieldWithPath("data.posts[].content").type(JsonFieldType.STRING).description("게시글 내용"),
                    fieldWithPath("data.posts[].writer").type(JsonFieldType.STRING).description("게시글 작성자"),
                    fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("응답시간")
                )
            ))
            .when().get(POST_URI)
            .then().log().all()
            .extract();

        // then
        List<PostsResponse.PagePostResponse> postsResponses = pageResponse.jsonPath()
            .getList("data.posts", PostsResponse.PagePostResponse.class);

        assertAll(
            () -> assertThat(pageResponse.statusCode()).isEqualTo(HttpStatus.OK.value()),
            () -> assertThat(postsResponses).hasSize(10),
            () -> assertThat(postsResponses).extracting("id").contains(11L, 20L)
        );

    }

    @DisplayName("게시글을 수정한다.")
    @Test
    void modifyPost() {
        // given
        ExtractableResponse<Response> createResponse = post(POST_URI, CREATE_POST_REQUEST);
        String uri = createResponse.header("LOCATION");
        Long postId = extractId(createResponse);

        // when
        ExtractableResponse<Response> modifyResponse = RestAssured.given(this.spec).log().all()
            .body(MODIFY_POST_REQUEST)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .filter(document("post-modify",
                pathParameters(
                    parameterWithName("id").description("게시글 번호")
                ),
                requestFields(
                    fieldWithPath("userId").description("회원 번호"),
                    fieldWithPath("title").description("제목"),
                    fieldWithPath("content").description("내용")
                ),
                responseFields(
                    fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                    fieldWithPath("data").type(JsonFieldType.STRING).description("메시지"),
                    fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("응답시간")
                )
            ))
            .when().put(POST_URI_WITH_ID, postId)
            .then().log().all()
            .extract();

        // then
        PostResponse postResponse = get(uri).jsonPath().getObject("data", PostResponse.class);
        assertAll(
            () -> assertThat(modifyResponse.statusCode()).isEqualTo(HttpStatus.OK.value()),
            () -> assertThat(extractMessage(modifyResponse)).isEqualTo("정상적으로 수정되었습니다."),
            () -> assertThat(postResponse.getTitle()).isEqualTo(MODIFY_POST_REQUEST.getTitle()),
            () -> assertThat(postResponse.getContent()).isEqualTo(MODIFY_POST_REQUEST.getContent())
        );
    }

    @DisplayName("글 수정시 작성자가 아닐 시 FORBIDDEN 응답코드를 반환한다.")
    @Test
    void modifyPost_Forbidden() {
        // given
        ExtractableResponse<Response> createResponse = post(POST_URI, CREATE_POST_REQUEST);
        Long postId = extractId(createResponse);

        // when
        ExtractableResponse<Response> modifyResponse = RestAssured.given(this.spec).log().all()
            .body(new ModifyPostRequest(2L, "수정에러입니다.", "수정에러입니다."))
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .filter(document("post-permissionError",
                pathParameters(
                    parameterWithName("id").description("게시글 번호")
                ),
                requestFields(
                    fieldWithPath("userId").description("회원 번호"),
                    fieldWithPath("title").description("제목"),
                    fieldWithPath("content").description("내용")
                ),
                responseFields(
                    fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                    fieldWithPath("data").type(JsonFieldType.STRING).description("에러 메시지"),
                    fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("응답시간")
                )
            ))
            .when().put(POST_URI_WITH_ID, postId)
            .then().log().all()
            .extract();

        // then
        assertAll(
            () -> assertThat(modifyResponse.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value()),
            () -> assertThat(extractMessage(modifyResponse)).isEqualTo("ID가 2인 회원은 해당 글을 수정할 권한이 없습니다.")
        );
    }

}
