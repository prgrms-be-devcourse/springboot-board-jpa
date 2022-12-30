package com.prgrms.domain.post;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgrms.domain.user.UserService;
import com.prgrms.dto.PostDto;
import com.prgrms.dto.PostDto.Update;
import com.prgrms.dto.UserDto;
import com.prgrms.dto.UserDto.Request;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private final long userId = 1L;
    private final long postId = 1L;

    @BeforeAll
    void setUpUser() {
        String userName = "홍길동";
        String hobby = "독서";
        int age = 15;
        String email = "guildong@gmail.com";
        String password = "hong1234!";

        UserDto.Request userDto = new Request(userName, hobby, age, email, password);
        userService.insertUser(userDto);

        String title = "타이틀 테스트1";
        String content = "컨텐트 테스트1";

        PostDto.Request postDto = new PostDto.Request(title, content, userId);

        postService.insertPost(postDto);
    }


    @DisplayName("post 를 저장한다")
    @Test
    void savePost() throws Exception {
        String title = "타이틀 테스트2";
        String content = "컨텐트 테스트2";

        PostDto.Request postDto = new PostDto.Request(title, content, userId);

        mockMvc.perform(post("/api/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postDto)))
            .andExpect(status().isCreated())
            .andDo(print())
            .andDo(document("post-save",
                requestFields(
                    fieldWithPath("title").type(JsonFieldType.STRING).description("postTitle"),
                    fieldWithPath("content").type(JsonFieldType.STRING).description("postContent"),
                    fieldWithPath("userId").type(JsonFieldType.NUMBER).description("userId")
                )));
    }

    @DisplayName("특정 id의 post를 리턴한다")
    @Test
    void postFindById() throws Exception {

        mockMvc.perform(get("/api/posts/" + postId))
            .andExpect(status().isOk())
            .andDo(document("post-find-by-id",
                responseFields(
                    fieldWithPath("id").type(JsonFieldType.NUMBER).description("postId"),
                    fieldWithPath("title").type(JsonFieldType.STRING).description("postTitle"),
                    fieldWithPath("content").type(JsonFieldType.STRING).description("postContent"),
                    fieldWithPath("userId").type(JsonFieldType.NUMBER).description("userId"),
                    fieldWithPath("userName").type(JsonFieldType.STRING).description("userName")
                )));
    }

    @DisplayName("pageable 조건에 맞는 post 를 리턴한다")
    @Test
    void postFindByPage() throws Exception {
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("page", "0");
        paramMap.add("size", "5");

        mockMvc.perform(get("/api/posts")
                .params(paramMap))
            .andExpect(status().isOk())
            .andDo(document("post-find-by-pageable",
                responseFields(
                    fieldWithPath("postDtos[].id").type(JsonFieldType.NUMBER).description("postId"),
                    fieldWithPath("postDtos[].title").type(JsonFieldType.STRING)
                        .description("postTitle"),
                    fieldWithPath("postDtos[].content").type(JsonFieldType.STRING)
                        .description("postContent"),
                    fieldWithPath("postDtos[].userId").type(JsonFieldType.NUMBER)
                        .description("userId"),
                    fieldWithPath("postDtos[].userName").type(JsonFieldType.STRING)
                        .description("userName")
                )));
    }

    @DisplayName("post 의 title 과 content 를 수정할수 있다")
    @Test
    void updatePost() throws Exception {
        String title = "업데이트 타이틀";
        String content = "업데이트 컨텐트";

        Update postDto = new Update(title, content);
        String resource = objectMapper.writeValueAsString(postDto);

        mockMvc.perform(put("/api/posts/" + postId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(resource))
            .andExpect(status().isOk())
            .andDo(document("post-update",
                requestFields(
                    fieldWithPath("title").type(JsonFieldType.STRING).description("postTitle"),
                    fieldWithPath("content").type(JsonFieldType.STRING).description("postContent")
                ),
                responseFields(
                    fieldWithPath("id").type(JsonFieldType.NUMBER).description("postId"),
                    fieldWithPath("title").type(JsonFieldType.STRING).description("postTitle"),
                    fieldWithPath("content").type(JsonFieldType.STRING).description("postContent"),
                    fieldWithPath("userId").type(JsonFieldType.NUMBER).description("userId"),
                    fieldWithPath("userName").type(JsonFieldType.STRING).description("userName")
                )));
    }

}
