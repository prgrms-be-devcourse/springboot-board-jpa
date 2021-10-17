package com.programmers.iyj.springbootboard.domain.post.api;

import com.programmers.iyj.springbootboard.domain.post.domain.Post;
import com.programmers.iyj.springbootboard.domain.post.dto.PostDto;
import com.programmers.iyj.springbootboard.domain.post.service.PostService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PostApi.class)
class PostApiTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    private static PostDto postDto;

    @BeforeAll
    public static void setUp() {
        postDto = PostDto.builder()
                .id(1L)
                .title("title1")
                .content("content1")
                .build();
    }

    @Test
    @DisplayName("getOne api should response OK")
    public void getOne() throws Exception {
        // Given
        given(postService.findOneById(anyLong())).willReturn(postDto);

        // When
        ResultActions actions = mockMvc.perform(get("/posts/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        // Then
        actions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("data.title").value("title1"))
                .andExpect(jsonPath("data.content").value("content1"));
    }

    @Test
    @DisplayName("getAll api should response OK")
    void getAll() throws Exception {
        // Given
        PageRequest page = PageRequest.of(0, 10);
        List<PostDto> postDtos = new ArrayList<>();
        postDtos.add(postDto);
        Page<PostDto> postDtoPage = new PageImpl<>(postDtos);

        given(postService.findAll(page)).willReturn(postDtoPage);

        // When
        ResultActions actions = mockMvc.perform(get("/posts")
                .contentType(MediaType.APPLICATION_JSON))
                        .andDo(print());

        // Then
        actions.andExpect(status().isOk());
    }
}