package dev.jpaboard.user.api;

import com.epages.restdocs.apispec.Schema;
import dev.jpaboard.ControllerTest;
import dev.jpaboard.user.application.UserService;
import dev.jpaboard.user.dto.request.UserUpdateRequest;
import dev.jpaboard.user.dto.response.UserInfoResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.resourceDetails;
import static com.epages.restdocs.apispec.ResourceDocumentation.headerWithName;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest extends ControllerTest {

    @MockBean
    UserService userService;

    @DisplayName("로그인한 유저는 본인의 정보를 조회할 수 있다.")
    @Test
    void findUserTest() throws Exception {
        UserInfoResponse response = new UserInfoResponse("qwer123@naver.com", "데브코스", 4, "코딩");
        given(userService.findUser(SESSION_USERID_VALUE)).willReturn(response);

        mockMvc.perform(get("/api/users/me")
                        .session(session)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(document("user-me",
                                resourceDetails().tags("User").description("본인 정보 조회")
                                        .responseSchema(Schema.schema("UserInfoResponse")),
                                responseFields(
                                        fieldWithPath("email").type(JsonFieldType.STRING)
                                                .description("이메일"),
                                        fieldWithPath("name").type(JsonFieldType.STRING)
                                                .description("이름"),
                                        fieldWithPath("age").type(JsonFieldType.NUMBER)
                                                .description("나이"),
                                        fieldWithPath("hobby").type(JsonFieldType.STRING)
                                                .description("취미")
                                )
                        )
                ).andExpect(status().isOk());
    }

    @DisplayName("로그인한 유저는 본인의 정보를 수정할 수 있다.")
    @Test
    void updateUserTest() throws Exception {
        UserUpdateRequest request = new UserUpdateRequest("이름", "취미");
        doNothing().when(userService).update(request, SESSION_USERID_VALUE);

        mockMvc.perform(patch("/api/users")
                        .session(session)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andDo(document("user-update",
                                resourceDetails().tags("User")
                                        .description("유저 업데이트")
                                        .requestSchema(Schema.schema("UserUpdateRequest")),
                                requestHeaders(
                                        headerWithName("JsessionID").description("세션").getAttributes()
                                ),
                                requestFields(
                                        fieldWithPath("name").type(JsonFieldType.STRING).description("수정할 이름"),
                                        fieldWithPath("hobby").type(JsonFieldType.STRING).description("수정할 취미")
                                )
                        )
                ).andExpect(status().isNoContent());
    }

    @DisplayName("로그인한 유저는 회원탈퇴를 할 수 있다.")
    @Test
    void deleteUserTest() throws Exception {
        doNothing().when(userService).delete(SESSION_USERID_VALUE);

        mockMvc.perform(delete("/api/users/me")
                        .session(session)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(document("user-delete",
                                resourceDetails().tags("User").description("회원탈퇴"),
                                requestHeaders(
                                        headerWithName("JsessionID").description("세션").getAttributes()
                                )
                        )
                )
                .andExpect(status().isOk());
    }

}
