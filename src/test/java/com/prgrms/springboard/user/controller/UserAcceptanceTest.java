package com.prgrms.springboard.user.controller;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.jdbc.Sql;

import com.prgrms.springboard.AcceptanceTest;
import com.prgrms.springboard.user.dto.CreateUserRequest;
import com.prgrms.springboard.user.dto.UserResponse;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

@Sql("classpath:db/init.sql")
class UserAcceptanceTest extends AcceptanceTest {

    private static final CreateUserRequest CREATE_USER_REQUEST = new CreateUserRequest("유민환", 26, "낚시");
    private static final String USERS_URI = "/api/v1/users";
    private static final String USERS_URI_WITH_ID = USERS_URI + "/{id}";

    @DisplayName("회원을 생성한다.")
    @Test
    void createUser() {
        // given
        // when
        ExtractableResponse<Response> createResponse = RestAssured.given(this.spec).log().all()
            .body(CREATE_USER_REQUEST)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .filter(document("user-save",
                requestFields(
                    fieldWithPath("name").type(JsonFieldType.STRING).description("name"),
                    fieldWithPath("age").type(JsonFieldType.NUMBER).description("age"),
                    fieldWithPath("hobby").type(JsonFieldType.STRING).description("hobby")
                ),
                responseFields(
                    fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                    fieldWithPath("data").type(JsonFieldType.NUMBER).description("데이터"),
                    fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("응답시간")
                )
            ))
            .when().post(USERS_URI)
            .then().log().all()
            .extract();

        // then
        assertAll(
            () -> assertThat(createResponse.statusCode()).isEqualTo(HttpStatus.CREATED.value()),
            () -> assertThat(createResponse.header("LOCATION")).isNotBlank(),
            () -> assertThat(extractId(createResponse)).isEqualTo(1L)
        );
    }

    @DisplayName("회원을 조회한다.")
    @Test
    void findOne() {
        // given
        ExtractableResponse<Response> createUserResponse = post(USERS_URI, CREATE_USER_REQUEST);
        Long id = extractId(createUserResponse);

        // when
        ExtractableResponse<Response> findResponse = RestAssured.given(this.spec).log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .filter(document("user-findOne",
                pathParameters(
                    parameterWithName("id").description("유저 id")
                ),
                responseFields(
                    fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                    fieldWithPath("data").type(JsonFieldType.OBJECT).description("data"),
                    fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("data.id"),
                    fieldWithPath("data.name").type(JsonFieldType.STRING).description("data.name"),
                    fieldWithPath("data.age").type(JsonFieldType.NUMBER).description("data.age"),
                    fieldWithPath("data.hobby").type(JsonFieldType.STRING).description("data.hobby"),
                    fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("응답시간")
                )
            ))
            .when()
            .get(USERS_URI_WITH_ID, id)
            .then().log().all()
            .extract();

        // then
        assertAll(
            () -> assertThat(findResponse.statusCode()).isEqualTo(HttpStatus.OK.value()),
            () -> assertThat(findResponse.jsonPath().getObject("data", UserResponse.class).getId()).isEqualTo(1L)
        );
    }

    @DisplayName("존재하지 않는 회원 조회 시 NOT_FOUND 응답코드를 반환한다. ")
    @Test
    void findOne_NotFound() {
        // given
        Long id = 2L;

        // when
        ExtractableResponse<Response> findResponse = RestAssured.given(this.spec).log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .filter(document("user-notFound",
                pathParameters(
                    parameterWithName("id").description("유저 id")
                ),
                responseFields(
                    fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                    fieldWithPath("data").type(JsonFieldType.STRING).description("에러 메시지"),
                    fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("응답시간")
                )
            ))
            .when()
            .get("/api/v1/users/{id}", id)
            .then().log().all()
            .extract();

        // then
        assertAll(
            () -> assertThat(findResponse.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value()),
            () -> assertThat(extractMessage(findResponse)).isEqualTo("ID가 2인 회원은 없습니다.")
        );
    }

}
