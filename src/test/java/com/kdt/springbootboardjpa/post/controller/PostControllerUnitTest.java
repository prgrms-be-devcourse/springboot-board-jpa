package com.kdt.springbootboardjpa.post.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kdt.springbootboardjpa.post.service.PostService;
import com.kdt.springbootboardjpa.post.service.dto.CreatePostRequest;
import com.kdt.springbootboardjpa.post.service.dto.PostResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class PostControllerUnitTest {

    @InjectMocks
    private PostController postController;

    @Mock
    private PostService postService;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;


    @Test
    @DisplayName("게시판(Post) 저장할 수 있다.")
    void save() throws Exception {
        // given
        long memberId = 1L;
        CreatePostRequest postRequestDto = createdPostRequestDto(memberId);

        mockMvc.perform(post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postRequestDto))
        )
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("게시판 아이디로 게시판(Post)을 조회할 수 있다.")
    void findById() {
        // given
        Long postId = 1L;
        PostResponse postResponse = createdPostResponseDto();

        given(postService.findById(postId)).willReturn(postResponse);

        // when
        ResponseEntity<PostResponse> actual = postController.findById(postId);

        // then
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(actual.getBody().getId()).isEqualTo(postId);
        assertThat(actual.getBody().getTitle()).isEqualTo(postResponse.getTitle());
        assertThat(actual.getBody().getContent()).isEqualTo(postResponse.getContent());
        assertThat(actual.getBody().getMemberName()).isEqualTo(postResponse.getMemberName());
    }

    @Test
    @DisplayName("게시판(Post) 전체 조회할 수 있다.")
    void findAll() {
        // given
        PostResponse postResponse = createdPostResponseDto();
        Pageable pageable = PageRequest.of(0, 10);

        given(postService.findAll(pageable)).willReturn(postResponseDtoPage(List.of(postResponse)));

        // when
        ResponseEntity<List<PostResponse>> actual = postController.findAll(pageable);

        // then
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    public Page<PostResponse> postResponseDtoPage(List<PostResponse> postResponseDto) {
        return new PageImpl<>(postResponseDto);
    }

    public PostResponse createdPostResponseDto() {
        return PostResponse.builder()
                .id(1L)
                .title("게시판 제목")
                .content("게시판 내용")
                .memberName("최은비")
                .build();
    }

    public CreatePostRequest createdPostRequestDto(Long memberId) {
        return CreatePostRequest.builder()
                .title("게시판 제목")
                .content("게시판 내용")
                .memberId(memberId)
                .build();
    }
}
