package com.prgrms.board.domain.post.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgrms.board.domain.post.dto.request.PostCreateRequest;
import com.prgrms.board.domain.post.dto.request.PostUpdateRequest;
import com.prgrms.board.domain.post.entity.Post;
import com.prgrms.board.domain.post.repository.PostRepository;
import com.prgrms.board.domain.user.entity.User;
import com.prgrms.board.domain.user.repository.UserRepository;
import com.prgrms.board.support.PostFixture;
import com.prgrms.board.support.UserFixture;

@Transactional
@SpringBootTest
@AutoConfigureRestDocs
@AutoConfigureMockMvc
class ApiPostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void init() {
        user = UserFixture.user().build();
        userRepository.save(user);
    }

    @Test
    @DisplayName("게시물 생성에 성공한다.")
    void create_post_success() throws Exception {
        // given
        PostCreateRequest request = new PostCreateRequest(user.getId(), "제목", "내용");

        // when & then
        mockMvc.perform(post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andDo(print())
            .andDo(document("posts/createPost",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()))
            )
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.message").value("게시물 생성 성공"))
            .andExpect(jsonPath("$.data.postId").exists())
            .andExpect(jsonPath("$.data.title").value(request.title()))
            .andExpect(jsonPath("$.data.content").value(request.content()));
    }

    @Test
    @DisplayName("게시물 전체 조회에 성공한다.")
    void get_posts_success() throws Exception {
        // given
        Post post1 = PostFixture.post().user(user).title("제목1").content("내용1").build();
        Post post2 = PostFixture.post().user(user).title("제목2").content("내용2").build();
        Post post3 = PostFixture.post().user(user).title("제목3").content("내용3").build();
        List<Post> posts = List.of(post1, post2, post3);
        postRepository.saveAll(posts);

        int page = 0;
        int size = 2;
        int totalCount = posts.size();
        int totalPage = (int)Math.ceil((double)posts.size() / size);

        // when & then
        mockMvc.perform(get("/posts")
                .param("page", String.valueOf(page))
                .param("size", String.valueOf(size))
            )
            .andDo(print())
            .andDo(document("posts/getPosts",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()))
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("게시물 전체 조회 성공"))
            .andExpect(jsonPath("$.data.totalCount").value(totalCount))
            .andExpect(jsonPath("$.data.totalPage").value(totalPage))
            .andExpect(jsonPath("$.data.page").value(page))
            .andExpect(jsonPath("$.data.size").value(size))
            .andExpect(jsonPath("$.data.items").isArray())
            .andExpect(jsonPath("$.data.items.length()").value(size));
    }

    @Test
    @DisplayName("게시물 조회에 성공한다.")
    void get_post_success() throws Exception {
        // given
        Post post = PostFixture.post().user(user).build();
        postRepository.save(post);

        // when & then
        mockMvc.perform(get("/posts/" + post.getId()))
            .andDo(print())
            .andDo(document("posts/getPost",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()))
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("게시물 조회 성공"))
            .andExpect(jsonPath("$.data.postId").value(post.getId()))
            .andExpect(jsonPath("$.data.title").value(post.getTitle()))
            .andExpect(jsonPath("$.data.content").value(post.getContent()))
            .andExpect(jsonPath("$.data.userId").value(user.getId()))
            .andExpect(jsonPath("$.data.username").value(user.getName()))
            .andExpect(jsonPath("$.data.createdAt").exists())
            .andExpect(jsonPath("$.data.updatedAt").exists());
    }

    @Test
    @DisplayName("게시물 수정에 성공한다.")
    void update_post_success() throws Exception {
        // given
        Post post = PostFixture.post().user(user).build();
        postRepository.save(post);
        PostUpdateRequest request = new PostUpdateRequest("수정한 제목", "수정한 내용");

        // when & then
        mockMvc.perform(patch("/posts/" + post.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
            )
            .andDo(print())
            .andDo(document("posts/updatePost",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()))
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("게시물 수정 성공"))
            .andExpect(jsonPath("$.data.postId").value(post.getId()))
            .andExpect(jsonPath("$.data.title").value(request.title()))
            .andExpect(jsonPath("$.data.content").value(request.content()));
    }
}
