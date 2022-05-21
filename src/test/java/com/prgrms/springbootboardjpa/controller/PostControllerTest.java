package com.prgrms.springbootboardjpa.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgrms.springbootboardjpa.dto.ContentDto;
import com.prgrms.springbootboardjpa.dto.PostDto;
import com.prgrms.springbootboardjpa.dto.UserDto;
import com.prgrms.springbootboardjpa.repository.PostRepository;
import com.prgrms.springbootboardjpa.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
@Slf4j
class PostControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    PostController postController;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;



    @AfterAll
    void close() {
        postRepository.deleteAll();
        userRepository.deleteAll();
    }


    @Test
    @Order(1)
    @DisplayName("포스트를 생성할 수 있다.")
    void save_post_test() throws Exception {
        UserDto userDto = new UserDto("suy2on", 25, "reading book");

        PostDto postDto = new PostDto("my-post", "this is my first post.",
            userDto);


        mockMvc.perform(post("/posts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(postDto)))
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document("save-post",
                requestFields(
                    fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                    fieldWithPath("content").type(JsonFieldType.STRING).description("title"),
                    fieldWithPath("userDto").type(JsonFieldType.OBJECT).description("userDto"),
                    fieldWithPath("userDto.name").type(JsonFieldType.STRING).description("userDto.name"),
                    fieldWithPath("userDto.age").type(JsonFieldType.NUMBER).description("userDto.age"),
                    fieldWithPath("userDto.hobby").type(JsonFieldType.STRING).description("userDto.hobby")

                ),
                responseFields(
                    fieldWithPath("code").type(JsonFieldType.NUMBER).description("상태코드"),
                    fieldWithPath("data").type(JsonFieldType.NUMBER).description("데이터"),
                    fieldWithPath("timestamp").type(JsonFieldType.STRING).description("응답시간")
                )
            ));

    }

    @Test
    @DisplayName("모든 포스트를 가져올 수 있다.")
    void get_all_post_test() throws Exception {
        mockMvc.perform(get("/posts")
            .param("page", String.valueOf(0))
            .param("size", String.valueOf(10))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document("get-all-post",
                responseFields(
                    fieldWithPath("code").type(JsonFieldType.NUMBER).description("상태코드"),
                    fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터"),
                    fieldWithPath("data.content[].title").type(JsonFieldType.STRING).description("title"),
                    fieldWithPath("data.content[].content").type(JsonFieldType.STRING).description("content"),
                    fieldWithPath("data.content[].userDto").type(JsonFieldType.OBJECT).description("userDto"),
                    fieldWithPath("data.content[].userDto.name").type(JsonFieldType.STRING).description("userDto.name"),
                    fieldWithPath("data.content[].userDto.age").type(JsonFieldType.NUMBER).description("userDto.age"),
                    fieldWithPath("data.content[].userDto.hobby").type(JsonFieldType.STRING).description("userDto.hobby"),
                    fieldWithPath("data.pageable").type(JsonFieldType.OBJECT).description("data.pageable"),
                    fieldWithPath("data.pageable.sort").type(JsonFieldType.OBJECT).description("data.pageable.sort"),
                    fieldWithPath("data.pageable.sort.empty").type(JsonFieldType.BOOLEAN).description("data.pageable.sort.empty"),
                    fieldWithPath("data.pageable.sort.sorted").type(JsonFieldType.BOOLEAN).description("data.pageable.sort.sorted"),
                    fieldWithPath("data.pageable.sort.unsorted").type(JsonFieldType.BOOLEAN).description("data.pageable.sort.unsorted"),
                    fieldWithPath("data.pageable.offset").type(JsonFieldType.NUMBER).description("data.pageable.offset"),
                    fieldWithPath("data.pageable.pageNumber").type(JsonFieldType.NUMBER).description("data.pageable.pageNumber"),
                    fieldWithPath("data.pageable.pageSize").type(JsonFieldType.NUMBER).description("data.pageable.pageSize"),
                    fieldWithPath("data.pageable.paged").type(JsonFieldType.BOOLEAN).description("data.pageable.paged"),
                    fieldWithPath("data.pageable.unpaged").type(JsonFieldType.BOOLEAN).description("data.pageable.unpaged"),
                    fieldWithPath("data.last").type(JsonFieldType.BOOLEAN).description("data.last"),
                    fieldWithPath("data.totalPages").type(JsonFieldType.NUMBER).description("data.totalPages"),
                    fieldWithPath("data.totalElements").type(JsonFieldType.NUMBER).description("data.totalElements"),
                    fieldWithPath("data.size").type(JsonFieldType.NUMBER).description("data.size"),
                    fieldWithPath("data.number").type(JsonFieldType.NUMBER).description("data.number"),
                    fieldWithPath("data.sort").type(JsonFieldType.OBJECT).description("data.sort"),
                    fieldWithPath("data.sort.empty").type(JsonFieldType.BOOLEAN).description("data.sort.empty"),
                    fieldWithPath("data.sort.sorted").type(JsonFieldType.BOOLEAN).description("data.sort.sorted"),
                    fieldWithPath("data.sort.unsorted").type(JsonFieldType.BOOLEAN).description("data.sort.unsorted"),
                    fieldWithPath("data.numberOfElements").type(JsonFieldType.NUMBER).description("data.numberOfElements"),
                    fieldWithPath("data.first").type(JsonFieldType.BOOLEAN).description("data.first"),
                    fieldWithPath("data.empty").type(JsonFieldType.BOOLEAN).description("data.empty"),
                    fieldWithPath("timestamp").type(JsonFieldType.STRING).description("응답시간")
                )
            ));
    }

    @Test
    @DisplayName("특정 id의 포스트가져오기")
    void get_one_post_test() throws Exception {
        mockMvc.perform(get("/posts/{postId}", 1L)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document("get-post-by-id",
                responseFields(
                    fieldWithPath("code").type(JsonFieldType.NUMBER).description("상태코드"),
                    fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터"),
                    fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터"),
                    fieldWithPath("data.title").type(JsonFieldType.STRING).description("title"),
                    fieldWithPath("data.content").type(JsonFieldType.STRING).description("content"),
                    fieldWithPath("data.userDto").type(JsonFieldType.OBJECT).description("userDto"),
                    fieldWithPath("data.userDto.name").type(JsonFieldType.STRING).description("userDto.name"),
                    fieldWithPath("data.userDto.age").type(JsonFieldType.NUMBER).description("userDto.age"),
                    fieldWithPath("data.userDto.hobby").type(JsonFieldType.STRING).description("userDto.hobby"),
                    fieldWithPath("timestamp").type(JsonFieldType.STRING).description("응답시간")
                )
            ));
    }

    @Test
    @DisplayName("특정 id의 포스트수정하기")
    void update_post_by_id_test() throws Exception {
        ContentDto contentDto = new ContentDto("this is my first change post");
        mockMvc.perform(patch("/posts/{postId}", 1L)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(contentDto)))
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document("update-post-content",
                requestFields(
                    fieldWithPath("content").type(JsonFieldType.STRING).description("content")
                ),
                responseFields(
                    fieldWithPath("code").type(JsonFieldType.NUMBER).description("상태코드"),
                    fieldWithPath("data").type(JsonFieldType.NUMBER).description("데이터"),
                    fieldWithPath("timestamp").type(JsonFieldType.STRING).description("응답시간")
                )
            ));
    }


    @Test
    @Transactional
    @DisplayName("엔티티와 데이터베이스의 싱크체크")
    void data_sync_check_test() {
        assertThat(userRepository.findAll().size(), is(1));
        assertThat(postRepository.findAll().size(), is(1));
        assertThat(userRepository.findById(1L).get().getPosts().size(), is(1));
        log.info("id: {}" , postRepository.findAll().get(0).getId());
        log.info("id: {}" , userRepository.findAll().get(0).getId());
        log.info("content: {}" , postRepository.findById(1L).get().getContent());


    }


}