package com.kdt.api;

import static com.kdt.api.PostApi.POSTS;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.JsonFieldType.ARRAY;
import static org.springframework.restdocs.payload.JsonFieldType.BOOLEAN;
import static org.springframework.restdocs.payload.JsonFieldType.NULL;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.OBJECT;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kdt.domain.post.Post;
import com.kdt.domain.post.PostRepository;
import com.kdt.domain.user.User;
import com.kdt.domain.user.UserRepository;
import com.kdt.post.dto.PostDto;
import com.kdt.post.service.PostConvertor;
import com.kdt.user.dto.UserDto;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@ActiveProfiles("test")
class PostApiTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    PostConvertor postConvertor;

    @BeforeEach
    void setUp() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("게시물 저장 요청")
    void addPost() throws Exception {
        User user = userRepository.save(User.builder().name("tester").age(20).build());

        PostDto postDto = givenPostDto(user.getId());

        mockMvc.perform(post(POSTS)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(postDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andDo(document("save-post",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("id").type(NULL).description("id"),
                                fieldWithPath("title").type(STRING).description("title"),
                                fieldWithPath("content").type(STRING).description("content"),
                                fieldWithPath("user").type(OBJECT).description("user"),
                                fieldWithPath("user.id").type(NUMBER).description("id"),
                                fieldWithPath("user.name").type(STRING).description("name"),
                                fieldWithPath("user.age").type(NUMBER).description("age")
                        ),
                        responseFields(
                                fieldWithPath("data").type(NUMBER).description("Post id"),
                                fieldWithPath("serverDatetime").type(STRING).description("sever response time")
                        )
                ));

    }

    @Test
    @DisplayName("게시물 저장 요청 실패 (게시물의 제목과 내용이 없는 경우)")
    void savePostFailToTitleAndContentIsNull() throws Exception {
        User user = userRepository.save(User.builder().name("tester").age(20).build());

        PostDto postDto = givenPostDto(user.getId());
        postDto.setTitle("");
        postDto.setContent("");
        mockMvc.perform(post(POSTS)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(postDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andDo(document("invalid-save-post",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("id").type(NULL).description("id"),
                                fieldWithPath("title").type(STRING).description("title"),
                                fieldWithPath("content").type(STRING).description("content"),
                                fieldWithPath("user").type(OBJECT).description("user"),
                                fieldWithPath("user.id").type(NUMBER).description("id"),
                                fieldWithPath("user.name").type(STRING).description("name"),
                                fieldWithPath("user.age").type(NUMBER).description("age")
                        ),
                        responseFields(
                                fieldWithPath("errors").type(ARRAY).description("Errors"),
                                fieldWithPath("errors[].field").type(STRING).description("error field"),
                                fieldWithPath("errors[].objectName").type(STRING).description("error objectName"),
                                fieldWithPath("errors[].code").type(STRING).description("error code"),
                                fieldWithPath("errors[].defaultMessage").type(STRING).description("error defaultMessage"),
                                fieldWithPath("serverDatetime").type(STRING).description("sever response time")
                        )
                ));
    }


    @Test
    @DisplayName("게시물 페이지 요청")
    void getPosts() throws Exception {
        User user = userRepository.save(User.builder().name("tester").age(20).build());
        IntStream.range(0, 30).forEach(i -> postRepository.save(Post.builder().title("제목 " + i).content("내용").user(user).build()));

        mockMvc.perform(get(POSTS)
                .param("page", "2")
                .param("size", "10")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("get-posts",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("data").type(OBJECT).description("Posts"),
                                fieldWithPath("serverDatetime").type(STRING).description("sever response time"),
                                fieldWithPath("data.content[].id").type(NUMBER).description("id"),
                                fieldWithPath("data.content[].title").type(STRING).description("title"),
                                fieldWithPath("data.content[].content").type(STRING).description("content"),
                                fieldWithPath("data.content[].user").type(OBJECT).description("user"),
                                fieldWithPath("data.content[].user.id").type(NUMBER).description("id"),
                                fieldWithPath("data.content[].user.name").type(STRING).description("name"),
                                fieldWithPath("data.content[].user.age").type(NUMBER).description("age"),
                                fieldWithPath("data.content[].user.age").type(NUMBER).description("age"),
                                fieldWithPath("data.pageable.sort.empty").type(BOOLEAN).description("sort.empty"),
                                fieldWithPath("data.pageable.sort.sorted").type(BOOLEAN).description("sort.sorted"),
                                fieldWithPath("data.pageable.sort.unsorted").type(BOOLEAN).description("sort.unsorted"),
                                fieldWithPath("data.pageable.offset").type(NUMBER).description("offset"),
                                fieldWithPath("data.pageable.pageNumber").type(NUMBER).description("pageNumber"),
                                fieldWithPath("data.pageable.pageSize").type(NUMBER).description("pageSize"),
                                fieldWithPath("data.pageable.paged").type(BOOLEAN).description("paged"),
                                fieldWithPath("data.pageable.unpaged").type(BOOLEAN).description("unpaged"),
                                fieldWithPath("data.last").type(BOOLEAN).description("last"),
                                fieldWithPath("data.totalPages").type(NUMBER).description("totalPages"),
                                fieldWithPath("data.totalElements").type(NUMBER).description("totalElements"),
                                fieldWithPath("data.size").type(NUMBER).description("size"),
                                fieldWithPath("data.number").type(NUMBER).description("number"),
                                fieldWithPath("data.sort.empty").type(BOOLEAN).description("empty"),
                                fieldWithPath("data.sort.sorted").type(BOOLEAN).description("sorted"),
                                fieldWithPath("data.sort.unsorted").type(BOOLEAN).description("unsorted"),
                                fieldWithPath("data.numberOfElements").type(NUMBER).description("numberOfElements"),
                                fieldWithPath("data.first").type(BOOLEAN).description("first"),
                                fieldWithPath("data.empty").type(BOOLEAN).description("empty")

                        )
                ));
    }

    @Test
    @DisplayName("게시물 단건 조회")
    void getPost() throws Exception {
        User user = userRepository.save(User.builder().name("tester").age(20).build());
        Post post = postRepository.save(Post.builder().title("제목").content("내용").user(user).build());

        mockMvc.perform(get(POSTS + "/{id}", post.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("get-post",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("data").type(OBJECT).description("Posts"),
                                fieldWithPath("serverDatetime").type(STRING).description("sever response time"),
                                fieldWithPath("data.id").type(NUMBER).description("id"),
                                fieldWithPath("data.title").type(STRING).description("title"),
                                fieldWithPath("data.content").type(STRING).description("content"),
                                fieldWithPath("data.user").type(OBJECT).description("user"),
                                fieldWithPath("data.user.id").type(NUMBER).description("id"),
                                fieldWithPath("data.user.name").type(STRING).description("name"),
                                fieldWithPath("data.user.age").type(NUMBER).description("age"),
                                fieldWithPath("data.user.age").type(NUMBER).description("age")
                        )
                ));
    }

    @Test
    @DisplayName("게시물 수정")
    void updatePost() throws Exception {
        User user = userRepository.save(User.builder().name("tester").age(20).build());
        Post post = postRepository.save(Post.builder().title("제목").content("내용").user(user).build());

        PostDto postDto = postConvertor.convertPostToPostDto(post);
        postDto.setTitle("변경한 제목");
        postDto.setContent("변경한 내용");

        mockMvc.perform(post(POSTS + "/{id}", post.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(postDto)))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andDo(document("update-post",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("id").type(NUMBER).description("id"),
                                fieldWithPath("title").type(STRING).description("title"),
                                fieldWithPath("content").type(STRING).description("content"),
                                fieldWithPath("user").type(OBJECT).description("user"),
                                fieldWithPath("user.id").type(NUMBER).description("id"),
                                fieldWithPath("user.name").type(STRING).description("name"),
                                fieldWithPath("user.age").type(NUMBER).description("age")
                        ),
                        responseFields(
                                fieldWithPath("data").type(NUMBER).description("Post id"),
                                fieldWithPath("serverDatetime").type(STRING).description("sever response time")
                        )
                ));

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