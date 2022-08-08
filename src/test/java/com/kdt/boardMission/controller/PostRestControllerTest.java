package com.kdt.boardMission.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kdt.boardMission.dto.CreatePostDto;
import com.kdt.boardMission.dto.PostDto;
import com.kdt.boardMission.dto.UserDto;
import com.kdt.boardMission.repository.PostRepository;
import com.kdt.boardMission.repository.UserRepository;
import com.kdt.boardMission.service.PostService;
import com.kdt.boardMission.service.UserService;
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


import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
class PostRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void setup() {
        userRepository.deleteAll();
        postRepository.deleteAll();

        userDto = UserDto.builder()
                .name("name")
                .age(10)
                .hobby("hobby")
                .build();
        long userId = userService.saveUser(userDto);
        userDto.setId(userId);
    }

    UserDto userDto;

    @Test
    @DisplayName("게시글 작성 Post /posts")
    public void doPostTest() throws Exception {

        //given
        PostDto postDto = PostDto.builder()
                .title("title")
                .content("content")
                .build();

        CreatePostDto createPostDto = new CreatePostDto(userDto, postDto);

        //when
        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(createPostDto))
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-save",
                        requestFields(
                                fieldWithPath("userDto").type(JsonFieldType.OBJECT).description("userDto"),
                                fieldWithPath("userDto.id").type(JsonFieldType.NUMBER).description("userDto.id"),
                                fieldWithPath("userDto.name").type(JsonFieldType.STRING).description("userDto.name"),
                                fieldWithPath("userDto.age").type(JsonFieldType.NUMBER).description("userDto.age"),
                                fieldWithPath("userDto.hobby").type(JsonFieldType.STRING).description("userDto.hobby"),

                                fieldWithPath("postDto").type(JsonFieldType.OBJECT).description("postDto"),
                                fieldWithPath("postDto.id").type(JsonFieldType.NUMBER).description("postDto.id"),
                                fieldWithPath("postDto.title").type(JsonFieldType.STRING).description("postDto.title"),
                                fieldWithPath("postDto.content").type(JsonFieldType.STRING).description("postDto.content"),
                                fieldWithPath("postDto.userDto").type(JsonFieldType.NULL).description("postDto.userDto")
                        ),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("data").type(JsonFieldType.NUMBER).description("데이터"),
                                fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("응답시간")
                        )
                ));
    }

    @Test
    @DisplayName("게시글 수정 Post /posts/{id}")
    public void editPostTest() throws Exception {

        //given
        PostDto postDto = PostDto.builder()
                .title("title")
                .content("content")
                .build();

        long postId = postService.savePost(postDto, userDto);

        postDto.setTitle("new Title");
        postDto.setContent("new Content");

        //when
        mockMvc.perform(post("/posts/{id}", postId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(postDto))
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-edit",
                        requestFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("id"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("userDto").type(JsonFieldType.NULL).description("user")
                        ),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터"),
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("data.id"),
                                fieldWithPath("data.title").type(JsonFieldType.STRING).description("data.title"),
                                fieldWithPath("data.content").type(JsonFieldType.STRING).description("data.content"),
                                fieldWithPath("data.userDto").type(JsonFieldType.NULL).description("data.user"),
                                fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("응답시간")
                        )
                ));
    }

    @Test
    @DisplayName("게시글 전체조회 Get /posts")
    public void findAllPostTest() throws Exception {

        //given
        for (int i = 0; i < 10; i++) {
            PostDto postDto = PostDto.builder()
                    .title("title" + i)
                    .content("content" + i)
                    .build();

            postService.savePost(postDto, userDto);
        }

        //when
        mockMvc.perform(get("/posts")
                        .param("page", String.valueOf(0))
                        .param("size", String.valueOf(10))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-find-all",
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터"),

                                fieldWithPath("data.content[]").type(JsonFieldType.ARRAY).description("data.content[]"),
                                fieldWithPath("data.content[].id").type(JsonFieldType.NUMBER).description("data.content[].id"),
                                fieldWithPath("data.content[].title").type(JsonFieldType.STRING).description("data.content[].title"),
                                fieldWithPath("data.content[].content").type(JsonFieldType.STRING).description("data.content[].content"),

                                fieldWithPath("data.content[].userDto").type(JsonFieldType.OBJECT).description("data.content[].userDto"),
                                fieldWithPath("data.content[].userDto.id").type(JsonFieldType.NUMBER).description("data.content[].userDto.id"),
                                fieldWithPath("data.content[].userDto.name").type(JsonFieldType.STRING).description("data.content[].userDto.name"),
                                fieldWithPath("data.content[].userDto.age").type(JsonFieldType.NUMBER).description("data.content[].userDto.age"),
                                fieldWithPath("data.content[].userDto.hobby").type(JsonFieldType.STRING).description("data.content[].userDto.hobby"),

                                fieldWithPath("data.pageable").type(JsonFieldType.OBJECT).description("data.pageable"),
                                fieldWithPath("data.pageable.offset").type(JsonFieldType.NUMBER).description("data.pageable.offset"),
                                fieldWithPath("data.pageable.pageNumber").type(JsonFieldType.NUMBER).description("data.pageable.pageNumber"),
                                fieldWithPath("data.pageable.pageSize").type(JsonFieldType.NUMBER).description("data.pageable.pageSize"),
                                fieldWithPath("data.pageable.paged").type(JsonFieldType.BOOLEAN).description("data.pageable.paged"),
                                fieldWithPath("data.pageable.unpaged").type(JsonFieldType.BOOLEAN).description("data.pageable.unpaged"),

                                fieldWithPath("data.pageable.sort").type(JsonFieldType.OBJECT).description("data.pageable.sort"),
                                fieldWithPath("data.pageable.sort.empty").type(JsonFieldType.BOOLEAN).description("data.pageable.sort.empty"),
                                fieldWithPath("data.pageable.sort.sorted").type(JsonFieldType.BOOLEAN).description("data.pageable.sort.sorted"),
                                fieldWithPath("data.pageable.sort.unsorted").type(JsonFieldType.BOOLEAN).description("data.pageable.sort.unsorted"),

                                fieldWithPath("data.last").type(JsonFieldType.BOOLEAN).description("data.last"),
                                fieldWithPath("data.totalPages").type(JsonFieldType.NUMBER).description("data.totalPages"),
                                fieldWithPath("data.totalElements").type(JsonFieldType.NUMBER).description("data.totalElements"),
                                fieldWithPath("data.first").type(JsonFieldType.BOOLEAN).description("data.first"),
                                fieldWithPath("data.size").type(JsonFieldType.NUMBER).description("data.size"),
                                fieldWithPath("data.number").type(JsonFieldType.NUMBER).description("data.number"),

                                fieldWithPath("data.sort").type(JsonFieldType.OBJECT).description("data.sort"),
                                fieldWithPath("data.sort.empty").type(JsonFieldType.BOOLEAN).description("data.sort.empty"),
                                fieldWithPath("data.sort.unsorted").type(JsonFieldType.BOOLEAN).description("data.sort.unsorted"),
                                fieldWithPath("data.sort.sorted").type(JsonFieldType.BOOLEAN).description("data.sort.sorted"),

                                fieldWithPath("data.numberOfElements").type(JsonFieldType.NUMBER).description("data.numberOfElements"),
                                fieldWithPath("data.empty").type(JsonFieldType.BOOLEAN).description("data.empty"),

                                fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("응답시간")
                        )
                ));
    }

    @Test
    @DisplayName("게시글 단건 조회 Get /posts/{id}")
    public void findOnePostTest() throws Exception {

        //given
        for (int i = 0; i < 10; i++) {
            PostDto postDto = PostDto.builder()
                    .title("title" + i)
                    .content("content" + i)
                    .build();

            postService.savePost(postDto, userDto);
        }

        PostDto postDto = PostDto.builder()
                .title("title")
                .content("content")
                .build();

        long postDtoId = postService.savePost(postDto, userDto);

        //when
        mockMvc.perform(get("/posts/{id}", postDtoId)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-find-one",
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터"),

                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("data.id"),
                                fieldWithPath("data.title").type(JsonFieldType.STRING).description("data.title"),
                                fieldWithPath("data.content").type(JsonFieldType.STRING).description("data.content"),

                                fieldWithPath("data.userDto").type(JsonFieldType.OBJECT).description("data.userDto"),
                                fieldWithPath("data.userDto.id").type(JsonFieldType.NUMBER).description("data.userDto.id"),
                                fieldWithPath("data.userDto.name").type(JsonFieldType.STRING).description("data.userDto.name"),
                                fieldWithPath("data.userDto.age").type(JsonFieldType.NUMBER).description("data.userDto.age"),
                                fieldWithPath("data.userDto.hobby").type(JsonFieldType.STRING).description("data.userDto.hobby"),

                                fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("응답시간")
                        )
                ));
    }

}