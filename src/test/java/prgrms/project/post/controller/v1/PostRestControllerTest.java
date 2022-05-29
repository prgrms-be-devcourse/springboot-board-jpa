package prgrms.project.post.controller.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.payload.RequestFieldsSnippet;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import prgrms.project.post.controller.response.IdResponse;
import prgrms.project.post.service.post.PostDto;
import prgrms.project.post.service.user.HobbyDto;
import prgrms.project.post.service.user.UserDto;

import java.util.Set;

import static java.sql.JDBCType.ARRAY;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureTestDatabase
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@SpringBootTest
class PostRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    UserDto userDto;

    Long postId;

    @BeforeEach
    void setup() throws Exception {
        var userRequest = UserDto.builder().name("name").age(10).hobbies(Set.of(new HobbyDto("swim"))).build();
        var userRequestString = objectMapper.writeValueAsString(userRequest);
        var userIdResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users").content(userRequestString).contentType(APPLICATION_JSON)).andReturn();
        var userIdResponse = objectMapper.readValue(userIdResult.getResponse().getContentAsString(), IdResponse.class);
        var userDtoResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/{userId}", userIdResponse.id()).contentType(APPLICATION_JSON)).andReturn();

        userDto = objectMapper.readValue(userDtoResult.getResponse().getContentAsString(), UserDto.class);

        var postRequest = PostDto.builder().title("title").content("content").user(userDto).build();
        var postRequestString = objectMapper.writeValueAsString(postRequest);
        var postIdResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/posts").content(postRequestString).contentType(APPLICATION_JSON)).andReturn();
        var postIdResponse = objectMapper.readValue(postIdResult.getResponse().getContentAsString(), IdResponse.class);

        postId = postIdResponse.id();
    }

    @Test
    @DisplayName("Post 를 등록한다.")
    void testSavePost() throws Exception {
        var request = PostDto.builder()
                .title("new title")
                .content("new content")
                .user(userDto)
                .build();

        var requestString = objectMapper.writeValueAsString(request);

        mockMvc.perform(
            post("/api/v1/posts")
                .content(requestString)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("post-save",
                    requestSnippetForSave(),
                    responseSnippetForSaveAndUpdate()
                )
            );
    }

    @Test
    @DisplayName("Post 를 id 로 찾는다.")
    void testFindById() throws Exception {
        mockMvc.perform(get("/api/v1/posts/{postId}", postId)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(
                    document("post-find",
                        responseSnippetForFindById()
                    )
                );
    }

    @Test
    @DisplayName("Post 를 모두 찾는다.")
    void testFindAll() throws Exception {

        mockMvc.perform(get("/api/v1/posts")
            .param("page", "0")
            .param("size", "1")
            .contentType(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(
                document("post-find-all",
                    responseSnippetForFindAll()
                )
            );

    }

    @Test
    @DisplayName("Post 를 업데이트한다.")
    void testUpdatePost() throws Exception {
        var request = PostDto.builder()
                .id(postId)
                .title("updated title")
                .content("updated content")
                .user(userDto)
                .build();

        var requestString = objectMapper.writeValueAsString(request);

        mockMvc.perform(put("/api/v1/posts/{postId}", postId)
                .content(requestString)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("post-update",
                    requestSnippetForUpdate(),
                    responseSnippetForSaveAndUpdate()
                )
            );
    }

    private RequestFieldsSnippet requestSnippetForSave() {
        return requestFields(
            fieldWithPath("id").type(NULL).description("게시판아이디"),
            fieldWithPath("title").type(STRING).description("게시판제목"),
            fieldWithPath("content").type(STRING).description("게시판내용"),
            fieldWithPath("user").type(OBJECT).description("회원"),
            fieldWithPath("user.id").type(NUMBER).description("회원아이디"),
            fieldWithPath("user.name").type(STRING).description("회원이름"),
            fieldWithPath("user.age").type(NUMBER).description("회원나이"),
            fieldWithPath("user.hobbies").type(ARRAY).description("회원취미목록"),
            fieldWithPath("user.hobbies[0].hobby").type(STRING).description("회원취미")
        );
    }

    private RequestFieldsSnippet requestSnippetForUpdate() {
        return requestFields(
            fieldWithPath("id").type(NUMBER).description("게시판아이디"),
            fieldWithPath("title").type(STRING).description("게시판제목"),
            fieldWithPath("content").type(STRING).description("게시판내용"),
            fieldWithPath("user").type(OBJECT).description("회원"),
            fieldWithPath("user.id").type(NUMBER).description("회원아이디"),
            fieldWithPath("user.name").type(STRING).description("회원이름"),
            fieldWithPath("user.age").type(NUMBER).description("회원나이"),
            fieldWithPath("user.hobbies").type(ARRAY).description("회원취미목록"),
            fieldWithPath("user.hobbies[0].hobby").type(STRING).description("회원취미")
        );
    }

    private ResponseFieldsSnippet responseSnippetForFindById() {
        return responseFields(
            fieldWithPath("id").type(NUMBER).description("게시판아이디"),
            fieldWithPath("title").type(STRING).description("게시판제목"),
            fieldWithPath("content").type(STRING).description("게시판내용"),
            fieldWithPath("user").type(OBJECT).description("회원"),
            fieldWithPath("user.id").type(NUMBER).description("회원아이디"),
            fieldWithPath("user.name").type(STRING).description("회원이름"),
            fieldWithPath("user.age").type(NUMBER).description("회원나이"),
            fieldWithPath("user.hobbies").type(ARRAY).description("회원취미목록"),
            fieldWithPath("user.hobbies[0].hobby").type(STRING).description("회원취미")
        );
    }

    private ResponseFieldsSnippet responseSnippetForFindAll() {
        return responseFields(
            fieldWithPath("content").type(ARRAY).description("컨텐츠"),
            fieldWithPath("content[0].id").type(NUMBER).description("게시판아이디"),
            fieldWithPath("content[0].title").type(STRING).description("게시판제목"),
            fieldWithPath("content[0].content").type(STRING).description("게시판내용"),
            fieldWithPath("content[0].user").type(OBJECT).description("회원"),
            fieldWithPath("content[0].user.id").type(NUMBER).description("회원아이디"),
            fieldWithPath("content[0].user.name").type(STRING).description("회원이름"),
            fieldWithPath("content[0].user.age").type(NUMBER).description("회원나이"),
            fieldWithPath("content[0].user.hobbies").type(ARRAY).description("회원취미목록"),
            fieldWithPath("content[0].user.hobbies[0].hobby").type(STRING).description("회원취미"),
            fieldWithPath("pageNumber").type(NUMBER).description("페이지넘버"),
            fieldWithPath("pageSize").type(NUMBER).description("페이지사이즈"),
            fieldWithPath("first").type(BOOLEAN).description("처음"),
            fieldWithPath("last").type(BOOLEAN).description("끝")
        );
    }

    private ResponseFieldsSnippet responseSnippetForSaveAndUpdate() {
        return responseFields(
            fieldWithPath("id").type(NUMBER).description("게시판아이디")
        );
    }
}