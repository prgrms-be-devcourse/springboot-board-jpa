package com.prgrms.springboard;

import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(RestDocumentationExtension.class)
public abstract class AcceptanceTest {

    protected RequestSpecification spec;

    @LocalServerPort
    int port;

    @BeforeEach
    protected void setup(RestDocumentationContextProvider restDocumentation) {
        RestAssured.port = port;
        this.spec = new RequestSpecBuilder().addFilter(documentationConfiguration(restDocumentation))
            .build();
    }

    protected ExtractableResponse<Response> post(String uri, Object requestBody) {
        return RestAssured.given().log().all()
            .body(requestBody)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().post(uri)
            .then().log().all()
            .extract();
    }

    protected ExtractableResponse<Response> get(String uri) {
        return RestAssured.given().log().all()
            .when().get(uri)
            .then().log().all()
            .extract();
    }

    protected Long extractId(ExtractableResponse<Response> response) {
        return response.jsonPath().getLong("data");
    }

    protected String extractMessage(ExtractableResponse<Response> findResponse) {
        return findResponse.jsonPath().getString("data");
    }

}
