package com.kdt.apis;

import static com.kdt.apis.PostApi.POSTS;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kdt.domain.post.PostRepository;
import com.kdt.domain.user.User;
import com.kdt.domain.user.UserRepository;
import com.kdt.post.dto.PostDto;
import com.kdt.user.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class PostApiTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    @BeforeEach
    void setUp() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("게시물 정보를 받아 저장하는 API Test")
    void addPost() throws Exception {
        //given
        User user = userRepository.save(User.builder().name("tester").age(20).build());

        PostDto postDto = givenPostDto(user.getId());

        //when
        //then
        mockMvc.perform(post(POSTS)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(postDto)))
                .andDo(print())
                .andExpect(status().isCreated());

    }

    private PostDto givenPostDto(Long userId) {
        UserDto userDto = new UserDto();
        userDto.setId(userId);
        userDto.setName("tester");
        userDto.setAge(20);

        PostDto postDto = new PostDto();
        postDto.setTitle("스프링 스터디 모집");
        postDto.setContent("스프링을 주제로 주 1회 발표하며 공부하실 스터디원을 모집합니다.");
        postDto.setUserDto(userDto);
        return postDto;
    }


}