package kdt.prgrms.devrun.post.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kdt.prgrms.devrun.post.dto.DetailPostDto;
import kdt.prgrms.devrun.post.dto.PostForm;
import kdt.prgrms.devrun.post.dto.SimplePostDto;
import kdt.prgrms.devrun.post.service.PostService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class PostApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    PostService postService;

    final Long VALID_POST_ID = 1L;
    final Long INVALID_POST_ID = 1000L;

    @Test
    void posts() throws Exception {

        final PageRequest pageRequest = PageRequest.of(0, 2);
        Page<SimplePostDto> postDtoPage = Page.empty();
        given(postService.getPostPagingList(pageRequest)).willReturn(postDtoPage);

        mockMvc.perform(get("/api/v1/posts")
            .param("page", String.valueOf(0))
            .param("size", String.valueOf(10))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print());

    }

    @Test
    void post_success() throws Exception {

        final DetailPostDto detailPostDto = DetailPostDto.builder()
            .id(VALID_POST_ID)
            .title("title")
            .content("content")
            .createdBy("devrunner21")
            .createdAt(LocalDateTime.now())
            .build();

        given(postService.getPostById(VALID_POST_ID)).willReturn(detailPostDto);

        mockMvc.perform(get("/api/v1/post/{id}", VALID_POST_ID)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print());

    }

    @Test
    void createTest() throws Exception {

        final PostForm postForm = PostForm.builder()
            .title("New Post Title")
            .content("New Post Content")
            .createdBy("devrunner21")
            .build();

        given(postService.createPost(postForm)).willReturn(VALID_POST_ID);

        mockMvc.perform(post("/api/v1/post")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(postForm)))
            .andExpect(status().isOk())
            .andDo(print());

    }

    @Test
    void updateTest() throws Exception {

        final PostForm postForm = PostForm.builder()
            .title("New Post Title")
            .content("New Post Content")
            .createdBy("devrunner21")
            .build();

        given(postService.updatePost(VALID_POST_ID, postForm)).willReturn(VALID_POST_ID);

        mockMvc.perform(patch("/api/v1/post/{id}", VALID_POST_ID)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(postForm)))
            .andExpect(status().isOk())
            .andDo(print());
    }

    @Test
    void deleteTest() throws Exception {
        mockMvc.perform(delete("/api/v1/post/{id}", VALID_POST_ID)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print());
    }

}
