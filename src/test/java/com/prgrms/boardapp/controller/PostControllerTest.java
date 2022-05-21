package com.prgrms.boardapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgrms.boardapp.dto.PostDto;
import com.prgrms.boardapp.service.PostService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static com.prgrms.boardapp.common.PostCreateUtil.createPostDto;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PostController.class)
@AutoConfigureMockMvc
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    @Autowired
    private ObjectMapper objectMapper;

    PostDto postDto = createPostDto();

    @Test
    @DisplayName("새로운 Post를 생성하는 API 호출")
    void testSave() throws Exception {
        given(postService.save(postDto)).willReturn(postDto.getId());

        mockMvc.perform(post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postDto))
        )
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(postDto.getId()))) // body check
                .andDo(print())
        ;
    }

    @Test
    @DisplayName("postId를 pathVariable로 API 조회할 수 있다.")
    void testFindById() throws Exception {
        given(postService.findById(postDto.getId())).willReturn(postDto);

        mockMvc.perform(get("/posts/{postId}", postDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("페이징 처리하여 모든 데이터를 조회할 수 있다.")
    void testFindAllWithPaging() throws Exception {

        PageRequest pageRequest = PageRequest.of(0, 2);
        List<PostDto> posts = List.of(
                createPostDto(),
                createPostDto()
        );
        Page<PostDto> pageResponse = new PageImpl<>(posts);
        given(postService.findAll(pageRequest)).willReturn(pageResponse);

        mockMvc.perform(get("/posts")
                .param("page", String.valueOf(pageRequest.getPageNumber()))
                .param("size", String.valueOf(pageRequest.getPageSize()))
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("존재하지 않는 아이디는 404를 반환")
    void testFindById404() throws Exception {
        given(postService.findById(postDto.getId())).willThrow(new EntityNotFoundException("Throws EntityNotFoundException Message"));

        mockMvc.perform(get("/posts/{postId}", postDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @DisplayName("서버 장의 경우 500을 반환")
    void testFindById500() throws Exception {
        given(postService.findById(postDto.getId())).willThrow(new RuntimeException("Throws RuntimeException Message"));

        mockMvc.perform(get("/posts/{postId}", postDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isInternalServerError())
                .andDo(print());
    }

}