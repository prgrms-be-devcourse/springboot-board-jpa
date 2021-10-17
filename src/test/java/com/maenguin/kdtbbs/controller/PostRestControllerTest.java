package com.maenguin.kdtbbs.controller;

import com.maenguin.kdtbbs.domain.Post;
import com.maenguin.kdtbbs.domain.User;
import com.maenguin.kdtbbs.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@SpringBootTest
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@Transactional
class PostRestControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        User user = new User("maeng", "piano");
        Post post = new Post("hello", "world");
        Post post2 = new Post("Hi", "world");
        Post post3 = new Post("Hi2", "world");
        Post post4 = new Post("Hi3", "world");
        user.addPost(post);
        user.addPost(post2);
        user.addPost(post3);
        user.addPost(post4);
        userRepository.save(user);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("게시글 목록 페이징 조회 성공 테스트 (page=0,size=3)")
    void searchAllPostsSuccessTest() throws Exception {
        ResultActions result = mockMvc.perform(
                get("/bbs/api/v1/posts")
                        .accept(MediaType.APPLICATION_JSON)
                        .param("page", "0")
                        .param("size", "3")
        );
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(PostRestController.class))
                .andExpect(handler().methodName("searchAllPosts"))
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.error", is(nullValue())))
                .andExpect(jsonPath("$.data.postList").isArray())
                .andExpect(jsonPath("$.data.postList.length()", is(3)))
                .andExpect(jsonPath("$.data.postList[0].id", is(5)))
                .andExpect(jsonPath("$.data.postList[1].id", is(4)));
    }



}
