package com.prgrms.board.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgrms.board.controller.request.CreatePostRequest;
import com.prgrms.board.controller.request.UpdatePostRequest;
import com.prgrms.board.controller.utils.DatabaseCleaner;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@TestPropertySource("classpath:h2.properties")
@SpringBootTest
class PostApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    private DatabaseCleaner databaseCleaner;
    private static final Set<String> tableNameSet = Set.of("post", "users");

    @Autowired
    public void setDatabaseCleaner(DatabaseCleaner databaseCleaner) {
        this.databaseCleaner = databaseCleaner;
    }

//    @BeforeEach
//    void cleanUpDB() {
//        databaseCleaner.dbCleanUp(tableNameSet);
//    }


    @DisplayName("게시글 작성")
    @Test
    @Transactional
    void testCreatePost() throws Exception {
        // given
        String content = getCreatePostRequest(1L, "title1", "content1");

        // when
        ResultActions resultActions = createPost(content);

        // then
        resultActions.andExpectAll(
                        jsonPath("$.postId").isNumber()
                )
                .andDo(print())
                .andDo(
                        document("create-post",
                                requestFields(
                                        fieldWithPath("userId").type(JsonFieldType.NUMBER).description("사용자 아이디"),
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                                        fieldWithPath("content").type(JsonFieldType.STRING).description("내용")
                                ),
                                responseFields(
                                        fieldWithPath("postId").type(JsonFieldType.NUMBER).description("게시글 아이디")
                                )
                        )
                );
    }

    @DisplayName("게시글 수정")
    @Test
    void testUpdatePost() throws Exception {
        // given
        String postId = createPostAndGetPostId(1L, "title1", "content1");
        UpdatePostRequest updatePost = new UpdatePostRequest("updatePost");
        String updateContent = objectMapper.writeValueAsString(updatePost);

        // when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .post("/posts/" + postId)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateContent));

        // then
        resultActions.andExpectAll(
                        jsonPath("$.postId").isNumber()
                )
                .andDo(print())
                .andDo(
                        document("update-post",
                                requestFields(
                                        fieldWithPath("content").type(JsonFieldType.STRING).description("수정된 게시글")
                                ),
                                responseFields(
                                        fieldWithPath("postId").type(JsonFieldType.NUMBER).description("수정된 게시글 아이디")
                                ))
                );
    }

    @DisplayName("게시글 전체 페이징 조회")
    @Test
    void testGetPosts() throws Exception {
        // given
        String content = getCreatePostRequest(1L, "testTitle", "testContent");
        createPost(content);

        // when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .get("/posts")
        );

        // then
        resultActions.andExpectAll(
                        jsonPath("$.count").isNotEmpty(),
                        jsonPath("$.findPostResponseList").isArray()
                )
                .andDo(print())
                .andDo(document("get-posts",
                        responseFields(
                                fieldWithPath("count").type(JsonFieldType.NUMBER).description("전체 게시글 갯수"),
                                fieldWithPath("findPostResponseList").type(JsonFieldType.ARRAY).description("전체 게시글"),
                                fieldWithPath("findPostResponseList[].id").type(JsonFieldType.NUMBER).description("게시글 id"),
                                fieldWithPath("findPostResponseList[].title").type(JsonFieldType.STRING).description("게시글 제목"),
                                fieldWithPath("findPostResponseList[].content").type(JsonFieldType.STRING).description("게시글 내용")
                        )
                    )
                );

    }

    @DisplayName("게시글 단일 조회")
    @Test
    void testGetPost() throws Exception {
        // given
        String postId = createPostAndGetPostId(1L, "testTitle", "testContent");

        // when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .get("/posts/" + postId)
        );

        // then
        resultActions.andExpectAll(
                jsonPath("$.id").isNumber(),
                jsonPath("$.title").isString(),
                jsonPath("$.content").isString()
        )
                .andDo(print())
                .andDo(document("get-post",
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("게시글 id"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("내용")
                        )
                ));
    }

    private ResultActions createPost(String content) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders
                .post("/posts")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));
    }

    private String getCreatePostRequest(Long id, String title, String content) throws JsonProcessingException {
        CreatePostRequest createPostRequest = new CreatePostRequest(id, title, content);
        return objectMapper.writeValueAsString(createPostRequest);
    }

    private String createPostAndGetPostId(Long id, String title, String content) throws Exception {
        ResultActions resultActions = createPost(getCreatePostRequest(id, title, content));
        String stringResponse = resultActions.andReturn().getResponse().getContentAsString();
        JSONObject jsonObject = new JSONObject(stringResponse);
        return jsonObject.getString("postId");
    }
}