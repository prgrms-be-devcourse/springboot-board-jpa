package com.prgrms.springboard.user.controller;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.jdbc.Sql;

import com.prgrms.springboard.user.dto.CreateUserRequest;
import com.prgrms.springboard.user.dto.UserResponse;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql("classpath:db/init.sql")
@ExtendWith(RestDocumentationExtension.class)
class UserAcceptanceTest {

    private static final CreateUserRequest CREATE_USER_REQUEST = new CreateUserRequest("유민환", 26, "낚시");

    @LocalServerPort
    int port;

    private RequestSpecification spec;

    @BeforeEach
    public void setup(RestDocumentationContextProvider restDocumentation) {
        RestAssured.port = port;
        this.spec = new RequestSpecBuilder().addFilter(documentationConfiguration(restDocumentation))
            .build();
    }

    @DisplayName("회원을 생성한다.")
    @Test
    void createUser() {
        // given
        // when
        ExtractableResponse<Response> response = RestAssured.given(this.spec).log().all()
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
            .when().post("/api/v1/users")
            .then().log().all()
            .extract();

        // then
        assertAll(
            () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value()),
            () -> assertThat(response.header("LOCATION")).isNotBlank(),
            () -> assertThat(response.jsonPath().getLong("data")).isEqualTo(1L)
        );
    }

    @DisplayName("회원을 조회한다.")
    @Test
    void findOne() {
        // given
        ExtractableResponse<Response> createUserResponse = createUserResponse(CREATE_USER_REQUEST);
        Long id = createUserResponse.jsonPath().getObject("data", Long.class);

        // when
        ExtractableResponse<Response> response = RestAssured.given(this.spec).log().all()
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
            .get("/api/v1/users/{id}", id)
            .then().log().all()
            .extract();

        // then
        assertAll(
            () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
            () -> assertThat(response.jsonPath().getObject("data", UserResponse.class).getId()).isEqualTo(1L)
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
            () -> assertThat(findResponse.jsonPath().getString("data")).isEqualTo("ID가 2인 회원은 없습니다.")
        );
    }

    private ExtractableResponse<Response> createUserResponse(CreateUserRequest userRequest) {
        return RestAssured.given().log().all()
            .body(userRequest).contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().post("/api/v1/users")
            .then().log().all()
            .extract();
    }
}
