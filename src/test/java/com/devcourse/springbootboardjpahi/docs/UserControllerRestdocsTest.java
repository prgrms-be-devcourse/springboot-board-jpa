package com.devcourse.springbootboardjpahi.docs;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.devcourse.springbootboardjpahi.controller.UserController;
import com.devcourse.springbootboardjpahi.dto.CreateUserRequest;
import com.devcourse.springbootboardjpahi.dto.UserResponse;
import com.devcourse.springbootboardjpahi.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

@AutoConfigureRestDocs
@WebMvcTest(UserController.class)
public class UserControllerRestdocsTest {

    static final Faker faker = new Faker();

    @MockBean
    UserService userService;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @DisplayName("[GET] 사용자 NoContent 조회 API")
    @Test
    void testFindAllNoContentAPI() throws Exception {
        // given
        given(userService.findAll())
                .willReturn(Collections.emptyList());

        // when
        ResultActions actions = mockMvc.perform(get("/api/v1/users"));

        // then
        actions.andDo(document("user-find-all-no-content"))
                .andDo(print());
    }

    @DisplayName("[GET] 전체 사용자 조회 API")
    @Test
    void testFindAllAPI() throws Exception {
        // given
        List<UserResponse> mockResponses = List.of(generateUserResponse(), generateUserResponse());

        given(userService.findAll())
                .willReturn(mockResponses);

        // when
        ResultActions actions = mockMvc.perform(get("/api/v1/users"));

        // then
        actions.andDo(document("user-find-all",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                field("[].id", JsonFieldType.NUMBER, "Id"),
                                field("[].name", JsonFieldType.STRING, "Name"),
                                field("[].age", JsonFieldType.NUMBER, "Age"),
                                nullableField("[].hobby", JsonFieldType.STRING, "Hobby"),
                                field("[].createdAt", JsonFieldType.STRING, "Creation Datetime"))))
                .andDo(print());
    }

    @DisplayName("[POST] 사용자 추가 API")
    @Test
    void testCreateAPI() throws Exception {
        // given
        CreateUserRequest createUserRequest = generateCreateUserRequest();
        UserResponse userResponse = UserResponse.builder()
                .id(generateId())
                .name(createUserRequest.name())
                .age(createUserRequest.age())
                .hobby(createUserRequest.hobby())
                .createdAt(LocalDateTime.now())
                .build();

        given(userService.create(createUserRequest))
                .willReturn(userResponse);

        // when
        MockHttpServletRequestBuilder docsPutRequest = RestDocumentationRequestBuilders.post("/api/v1/users");
        ResultActions actions = mockMvc.perform(docsPutRequest
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createUserRequest)));

        // then
        actions.andDo(document("user-create",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                field("id", JsonFieldType.NUMBER, "Id"),
                                field("name", JsonFieldType.STRING, "Name"),
                                field("age", JsonFieldType.NUMBER, "Age"),
                                nullableField("hobby", JsonFieldType.STRING, "Hobby"),
                                field("createdAt", JsonFieldType.STRING, "Creation Datetime"))))
                .andDo(print());
    }

    CreateUserRequest generateCreateUserRequest() {
        String name = faker.name().firstName();
        int age = faker.number().numberBetween(0, 120);
        String hobby = faker.esports().game();

        return new CreateUserRequest(name, age, hobby);
    }

    UserResponse generateUserResponse() {
        long id = generateId();
        String name = faker.name().firstName();
        int age = faker.number().numberBetween(0, 120);
        String hobby = faker.esports().game();
        LocalDateTime createdAt = LocalDateTime.now();

        return UserResponse.builder()
                .id(id)
                .name(name)
                .age(age)
                .hobby(hobby)
                .createdAt(createdAt)
                .build();
    }

    private FieldDescriptor field(String name, Object type, String description) {
        return fieldWithPath(name)
                .type(type)
                .description(description);
    }

    private FieldDescriptor nullableField(String name, Object type, String description) {
        return fieldWithPath(name)
                .type(type)
                .description(description)
                .optional();
    }

    private long generateId() {
        return Math.abs(faker.number().randomDigitNotZero());
    }
}
