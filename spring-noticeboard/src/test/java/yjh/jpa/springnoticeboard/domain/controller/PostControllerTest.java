package yjh.jpa.springnoticeboard.domain.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import yjh.jpa.springnoticeboard.domain.dto.PostDto;
import yjh.jpa.springnoticeboard.domain.dto.UserDto;
import yjh.jpa.springnoticeboard.domain.repository.UserRepository;
import yjh.jpa.springnoticeboard.domain.service.PostService;
import yjh.jpa.springnoticeboard.domain.service.UserService;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@Slf4j
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UserService userService;

    @Autowired
    PostService postService;

    private static Long postId;

    @BeforeEach
    void set_up_test() throws NotFoundException {
        UserDto userDto = UserDto.builder()
                .age(23)
                .name("유리구슬")
                .hobby("문열기")
                .build();
        Long userId = userService.createUser(userDto);
        userDto.setId(userId);
        PostDto postDto = PostDto.builder()
                .user(userDto)
                .title("게시판 제목")
                .content("블라블라블라")
                .build();

        //when
        postId = postService.createPost(postDto);

        //then
        PostDto findPost = postService.findPost(postId);
        assertThat(postId).isEqualTo(findPost.getId());
    }

    @Test
    @DisplayName("게시글 작성,저장 기능 테스트")
    void insertUser_call_test() throws Exception {
        //given
        UserDto userDto = UserDto.builder()
                .age(2332)
                .name("제발그만오류나라")
                .hobby("잠자고싶어요")
                .build();
        Long userId = userService.createUser(userDto);
        userDto.setId(userId);
        PostDto postDto = PostDto.builder()
                .id(3L)
                .user(userDto)
                .title("게시판 제모오옥")
                .content("블라블라블라불불불")
                .build();


        log.info("test post 정보 {}",postDto.getUser());
        log.info("test post 정보 {}",postDto.getTitle());
        log.info("test post 정보 {}",postDto.getContent());
        log.info("test post 정보 {}",postDto.getUser());

        //when
        mockMvc.perform(post("/board/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postDto)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-save",
                        requestFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("ID"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("user").type(JsonFieldType.OBJECT).description("userDto"),
                                fieldWithPath("user.id").type(JsonFieldType.NUMBER).description("userDto.id"),
                                fieldWithPath("user.age").type(JsonFieldType.NUMBER).description("userDto.age"),
                                fieldWithPath("user.name").type(JsonFieldType.STRING).description("userDto.name"),
                                fieldWithPath("user.hobby").type(JsonFieldType.STRING).description("userDto.hobby")
                        ),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("data").type(JsonFieldType.NUMBER).description("데이터"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("응답시간")
                        )
                ));
    }

    @Test
    @DisplayName("게시글 단건 조회 기능 테스트")
    void findByPostId() throws Exception {
        mockMvc.perform(get("/board/posts/{postId}",postId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("게시글 전체 페이징 조회")
    void findAll() throws Exception {
        mockMvc.perform(get("/board/posts")
                        .param("page", String.valueOf(0))
                        .param("size", String.valueOf(20))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("게시글 삭제 기능")
    void deleteByPostId() throws Exception {
        mockMvc.perform(delete("/board/posts/{postId}",postId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("게시글 수정 기능")
    void updateByPostId() throws Exception {
        PostDto findPost = postService.findPost(postId);
        findPost.setTitle("업데이트 타이틀");
        findPost.setContent("업데이트 게시글");

        mockMvc.perform(patch("/board/posts/{postId}",postId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(findPost)))
                .andExpect(status().isOk())
                .andDo(print());
    }

}
