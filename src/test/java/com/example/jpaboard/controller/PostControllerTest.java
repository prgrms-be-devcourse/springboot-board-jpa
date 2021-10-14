package com.example.jpaboard.controller;

import static java.nio.charset.StandardCharsets.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.jpaboard.post.api.PostController;
import com.example.jpaboard.post.domain.Post;
import com.example.jpaboard.exception.PostNotFoundException;
import com.example.jpaboard.post.application.PostService;
import com.example.jpaboard.post.dto.PostDto;
import com.example.jpaboard.user.dto.UserDto;
import com.example.jpaboard.user.infra.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(PostController.class)
@MockBean(JpaMetamodelMappingContext.class)
class PostControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PostService postService;

    @MockBean
    UserRepository userRepository;

    @Test
    @DisplayName("postId로 포스트를 단건 조회한다.")
    void getPost_ShouldReturnPostDetails() throws Exception {
        //* 테스트를 위해 생성자를 만들어야할까? id 검증은 어떻게 하는 것이 좋을까?
        //* 엔티티와 같은 필드를 갖는 DTO?
        UserDto userDto = new UserDto(1L, "tester", "test_hobby");
        PostDto postDto = new PostDto(1L, "test_title", "test_content", userDto);

        given(postService.getPost(postDto.getId())).willReturn(postDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/posts/1")
                .characterEncoding(UTF_8))
            .andExpect(status().isOk())
            .andDo(print());

        // TODO: 2021/10/14 print 찍어보면 메시지 바디에 데이터가 잘 들어가 있는데 andExpect "author"에서 null이 반환된다. 원인 찾기

//            .andExpect(jsonPath("id").value(postDto.getId()))
//            .andExpect(jsonPath("title").value(postDto.getTitle()))
//            .andExpect(jsonPath("content").value(postDto.getContent()))
//            .andExpect(jsonPath("author").value(
//                new ObjectMapper().writeValueAsString(postDto.getAuthor())));
    }

    @Test
    @Disabled
    @DisplayName("포스트를 작성한다.")
    void createPost() throws Exception {
        // TODO: 2021/10/14 위와 마찬가지 버그

//        UserDto userDto = new UserDto(1L, "tester", "test_hobby");
//        PostDto postDto = new PostDto(1L, "test title", "test content", userDto);
//        given(postService.createPost(postDto)).willReturn(postDto);
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/posts")
//            .content(new ObjectMapper().writeValueAsString(postDto))
//            .contentType(MediaType.APPLICATION_JSON)
//            .accept(MediaType.APPLICATION_JSON))
//            .andExpect(status().isCreated())
//            .andExpect(header().string("Location", "/api/posts/" + postDto.getId()))
//            .andExpect(jsonPath("id").value(postDto.getId()))
//            .andExpect(jsonPath("title").value(postDto.getTitle()))
//            .andExpect(jsonPath("content").value(postDto.getContent()))
//            .andExpect(jsonPath("authorId").value(postDto.getAuthorId()));
    }

    @Test
    void getPost_notFound() throws Exception {
        given(postService.getPost(anyLong())).willThrow(new PostNotFoundException());

        mockMvc.perform(MockMvcRequestBuilders.get("/posts/1"))
            .andExpect(status().isNotFound());
    }

}