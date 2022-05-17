package com.blessing333.boardapi.controller;

import com.blessing333.boardapi.TestDataProvider;
import com.blessing333.boardapi.controller.dto.PostInformation;
import com.blessing333.boardapi.entity.Post;
import com.blessing333.boardapi.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PostApiControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private TestDataProvider dataProvider;
    private User testUser;
    private Post testPost;

    @BeforeAll
    void init() {
        testUser = dataProvider.insertUserToDb("tester", 26);
        testPost = dataProvider.insertPostToDb("title", "content", testUser);
    }

    @DisplayName("단일 게시글 조회 요청")
    @Test
    void getPostInformation() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/api/v1/posts/" + testPost.getId()))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        String jsonString = mvcResult.getResponse().getContentAsString();
        PostInformation result = mapper.readValue(jsonString, PostInformation.class);

        assertThat(result.getId()).isEqualTo(testPost.getId());
        assertThat(result.getTitle()).isEqualTo(testPost.getTitle());
        assertThat(result.getContent()).isEqualTo(testPost.getContent());
        assertThat(result.getCreatedBy()).isEqualTo(testPost.getCreatedBy());
    }

    @DisplayName("존재하지 않는 게시글 조회 요청 시 PostNotFoundException 발생하고 응답으로 404 Not found code 반환")
    @Test
    void getPostInformationWithInvalidId() throws Exception {
        String invalidId = "-1";
        MvcResult mvcResult = mockMvc.perform(get("/api/v1/posts/" + invalidId))
                .andExpect(status().is4xxClientError())
                .andReturn();

        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

}