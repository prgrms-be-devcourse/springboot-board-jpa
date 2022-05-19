package com.kdt.board.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kdt.board.dto.PostDTO;
import com.kdt.board.dto.UserDTO;
import com.kdt.board.service.PostService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostService postService;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void beforeEach() {
        //given
        PostDTO postDTO = PostDTO.builder()
            .title("훈기의 여행")
            .content("여행을 합니다.")
            .userDTO(UserDTO.builder()
                .name("김훈기")
                .age(27)
                .hobby("게임").build()
            )
            .build();
        //when
        Long id = postService.savePost(postDTO);
        //then
        Assertions.assertThat(postDTO.getTitle())
            .isEqualTo(postService.findPostById(id).getTitle());
    }


    @Test
    void savePost_정상_테스트() throws Exception {
        //given
        PostDTO postDTO = PostDTO.builder()
            .title("훈기의 여행 Sample")
            .content("여행을 합니다 Sample.")
            .userDTO(UserDTO.builder()
                .name("김훈기2")
                .age(27)
                .hobby("게임2").build()
            )
            .build();
        //when //then
        mockMvc.perform(post("/api/v1/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postDTO)))
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document("post-save", requestFields(
                fieldWithPath("title").type(JsonFieldType.STRING).description("Title"),
                fieldWithPath("id").type(JsonFieldType.NULL).description("ID"),
                fieldWithPath("content").type(JsonFieldType.STRING).description("Content"),
                fieldWithPath("userDTO.id").type(JsonFieldType.NULL).description("userID"),
                fieldWithPath("userDTO.name").type(JsonFieldType.STRING).description("userName"),
                fieldWithPath("userDTO.age").type(JsonFieldType.NUMBER).description("userAge"),
                fieldWithPath("userDTO.hobby").type(JsonFieldType.STRING).description("userHobby"),
                fieldWithPath("userDTO.postDTOs").type(JsonFieldType.NULL).description("userPosts"),
                fieldWithPath("userDTO.createdAt").type(JsonFieldType.NULL)
                    .description("userCreatedAt"),
                fieldWithPath("userDTO.createdBy").type(JsonFieldType.NULL)
                    .description("userCreatedBy"),
                fieldWithPath("createdAt").type(JsonFieldType.NULL).description("postCreatedAt"),
                fieldWithPath("createdBy").type(JsonFieldType.NULL).description("postCreatedBy")
            ), responseFields(
                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                fieldWithPath("data").type(JsonFieldType.NUMBER).description("데이터"),
                fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("응답시간")
            )));
    }

    @Test
    void savePost_정상2_테스트() throws Exception {
        //given
        PostDTO postDTO = PostDTO.builder()
            .title("훈기의 여행 Sample")
            .content("여행을 합니다 Sample.")
            .userDTO(UserDTO.builder()
                .name("김훈기2")
                .age(27)
                .hobby("게임2").build()
            )
            .build();
        Long id = postService.savePost(postDTO);
        Long userId = postService.findPostById(id).getUserDTO().getId();
        PostDTO postDTO2 = PostDTO.builder()
            .title("훈기의 여행2")
            .content("여행을 합니다.2")
            .userDTO(UserDTO.builder()
                .id(userId)
                .build()
            )
            .build();
        //when //then
        mockMvc.perform(post("/api/v1/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postDTO2)))
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document("post-save2", requestFields(
                fieldWithPath("title").type(JsonFieldType.STRING).description("Title"),
                fieldWithPath("id").type(JsonFieldType.NULL).description("ID"),
                fieldWithPath("content").type(JsonFieldType.STRING).description("Content"),
                fieldWithPath("userDTO.id").type(JsonFieldType.NUMBER).description("userID"),
                fieldWithPath("userDTO.name").type(JsonFieldType.NULL).description("userName"),
                fieldWithPath("userDTO.age").type(JsonFieldType.NULL).description("userAge"),
                fieldWithPath("userDTO.hobby").type(JsonFieldType.NULL).description("userHobby"),
                fieldWithPath("userDTO.postDTOs").type(JsonFieldType.NULL).description("userPosts"),
                fieldWithPath("userDTO.createdAt").type(JsonFieldType.NULL)
                    .description("userCreatedAt"),
                fieldWithPath("userDTO.createdBy").type(JsonFieldType.NULL)
                    .description("userCreatedBy"),
                fieldWithPath("createdAt").type(JsonFieldType.NULL).description("postCreatedAt"),
                fieldWithPath("createdBy").type(JsonFieldType.NULL).description("postCreatedBy")
            ), responseFields(
                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                fieldWithPath("data").type(JsonFieldType.NUMBER).description("데이터"),
                fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("응답시간")
            )));
    }

    @Test
    void getPages_정상_테스트() throws Exception {
        //given
        PostDTO postDTO = PostDTO.builder()
            .title("훈기의 여행 Sample")
            .content("여행을 합니다 Sample.")
            .userDTO(UserDTO.builder()
                .name("김훈기2")
                .age(27)
                .hobby("게임2").build()
            )
            .build();
        postService.savePost(postDTO);
        //when //then
        mockMvc.perform(get("/api/v1/posts")
                .param("page", String.valueOf(0))
                .param("size", String.valueOf(5))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document("post-pages",
                requestParameters(
                    parameterWithName("page").description("Page"),
                    parameterWithName("size").description("Size")
                ),
                responseFields(
                    fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                    fieldWithPath("data.content.[].title").type(JsonFieldType.STRING)
                        .description("Title"),
                    fieldWithPath("data.content.[].id").type(JsonFieldType.NUMBER)
                        .description("ID"),
                    fieldWithPath("data.content.[].content").type(JsonFieldType.STRING)
                        .description("Content"),
                    fieldWithPath("data.content.[].userDTO.id").type(JsonFieldType.NUMBER)
                        .description("userID"),
                    fieldWithPath("data.content.[].userDTO.name").type(JsonFieldType.STRING)
                        .description("userName"),
                    fieldWithPath("data.content.[].userDTO.age").type(JsonFieldType.NUMBER)
                        .description("userAge"),
                    fieldWithPath("data.content.[].userDTO.hobby").type(JsonFieldType.STRING)
                        .description("userHobby"),
                    fieldWithPath("data.content.[].userDTO.postDTOs").type(JsonFieldType.NULL)
                        .description("userPosts"),
                    fieldWithPath("data.content.[].userDTO.createdAt").type(JsonFieldType.STRING)
                        .description("userCreatedAt"),
                    fieldWithPath("data.content.[].userDTO.createdBy").type(JsonFieldType.STRING)
                        .description("userCreatedBy"),
                    fieldWithPath("data.content.[].createdAt").type(JsonFieldType.STRING)
                        .description("postCreatedAt"),
                    fieldWithPath("data.content.[].createdBy").type(JsonFieldType.STRING)
                        .description("postCreatedBy"),
                    fieldWithPath("data.pageable.sort.empty").type(JsonFieldType.BOOLEAN)
                        .description("pageableSortEmpty"),
                    fieldWithPath("data.pageable.sort.sorted").type(JsonFieldType.BOOLEAN)
                        .description("pageableSortSorted"),
                    fieldWithPath("data.pageable.sort.unsorted").type(JsonFieldType.BOOLEAN)
                        .description("pageableSortUnsorted"),
                    fieldWithPath("data.pageable.offset").type(JsonFieldType.NUMBER)
                        .description("pageableOffset"),
                    fieldWithPath("data.pageable.pageNumber").type(JsonFieldType.NUMBER)
                        .description("pageablePageNumber"),
                    fieldWithPath("data.pageable.pageSize").type(JsonFieldType.NUMBER)
                        .description("pageableSize"),
                    fieldWithPath("data.pageable.paged").type(JsonFieldType.BOOLEAN)
                        .description("pageablePaged"),
                    fieldWithPath("data.pageable.unpaged").type(JsonFieldType.BOOLEAN)
                        .description("pageableUnpaged"),
                    fieldWithPath("data.totalPages").type(JsonFieldType.NUMBER)
                        .description("totalPages"),
                    fieldWithPath("data.totalElements").type(JsonFieldType.NUMBER)
                        .description("totalElements"),
                    fieldWithPath("data.last").type(JsonFieldType.BOOLEAN).description("last"),
                    fieldWithPath("data.size").type(JsonFieldType.NUMBER).description("size"),
                    fieldWithPath("data.number").type(JsonFieldType.NUMBER).description("number"),
                    fieldWithPath("data.sort.empty").type(JsonFieldType.BOOLEAN)
                        .description("sortEmpty"),
                    fieldWithPath("data.sort.unsorted").type(JsonFieldType.BOOLEAN)
                        .description("sortUnsorted"),
                    fieldWithPath("data.sort.sorted").type(JsonFieldType.BOOLEAN)
                        .description("sortSorted"),
                    fieldWithPath("data.numberOfElements").type(JsonFieldType.NUMBER)
                        .description("numberOfElements"),
                    fieldWithPath("data.first").type(JsonFieldType.BOOLEAN).description("first"),
                    fieldWithPath("data.empty").type(JsonFieldType.BOOLEAN).description("empty"),
                    fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("응답시간")
                )));
    }

    @Test
    void getPost_정상_테스트() throws Exception {
        //given
        PostDTO postDTO = PostDTO.builder()
            .title("훈기의 여행 Sample")
            .content("여행을 합니다 Sample.")
            .userDTO(UserDTO.builder()
                .name("김훈기2")
                .age(27)
                .hobby("게임2").build()
            )
            .build();
        Long id = postService.savePost(postDTO);
        //when //then
        mockMvc.perform(get("/api/v1/posts/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document("post-get", responseFields(
                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                fieldWithPath("data.title").type(JsonFieldType.STRING)
                    .description("Title"),
                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("ID"),
                fieldWithPath("data.content").type(JsonFieldType.STRING)
                    .description("Content"),
                fieldWithPath("data.userDTO.id").type(JsonFieldType.NUMBER)
                    .description("userID"),
                fieldWithPath("data.userDTO.name").type(JsonFieldType.STRING)
                    .description("userName"),
                fieldWithPath("data.userDTO.age").type(JsonFieldType.NUMBER)
                    .description("userAge"),
                fieldWithPath("data.userDTO.hobby").type(JsonFieldType.STRING)
                    .description("userHobby"),
                fieldWithPath("data.userDTO.postDTOs").type(JsonFieldType.NULL)
                    .description("userPosts"),
                fieldWithPath("data.userDTO.createdAt").type(JsonFieldType.STRING)
                    .description("userCreatedAt"),
                fieldWithPath("data.userDTO.createdBy").type(JsonFieldType.STRING)
                    .description("userCreatedBy"),
                fieldWithPath("data.createdAt").type(JsonFieldType.STRING)
                    .description("postCreatedAt"),
                fieldWithPath("data.createdBy").type(JsonFieldType.STRING)
                    .description("postCreatedBy"),
                fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("응답시간")
            )));
    }

    @Test
    void updatePost_정상_테스트() throws Exception {
        //given
        PostDTO postDTO = PostDTO.builder()
            .title("훈기의 여행 Sample")
            .content("여행을 합니다 Sample.")
            .userDTO(UserDTO.builder()
                .name("김훈기2")
                .age(27)
                .hobby("게임2").build()
            )
            .build();
        Long id = postService.savePost(postDTO);
        PostDTO updatedPostDTO = PostDTO.builder()
            .title("훈기의 여행 수정을 했습니다.")
            .content("여행을 합니다 수정된 여행.")
            .userDTO(UserDTO.builder()
                .id(id)
                .build()
            )
            .build();
        //when //then
        mockMvc.perform(post("/api/v1/posts/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedPostDTO)))
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document("post-update", requestFields(
                    fieldWithPath("title").type(JsonFieldType.STRING).description("Title"),
                    fieldWithPath("id").type(JsonFieldType.NULL).description("ID"),
                    fieldWithPath("content").type(JsonFieldType.STRING).description("Content"),
                    fieldWithPath("userDTO.id").type(JsonFieldType.NUMBER).description("userID"),
                    fieldWithPath("userDTO.name").type(JsonFieldType.NULL).description("userName"),
                    fieldWithPath("userDTO.age").type(JsonFieldType.NULL).description("userAge"),
                    fieldWithPath("userDTO.hobby").type(JsonFieldType.NULL).description("userHobby"),
                    fieldWithPath("userDTO.postDTOs").type(JsonFieldType.NULL).description("userPosts"),
                    fieldWithPath("userDTO.createdAt").type(JsonFieldType.NULL)
                        .description("userCreatedAt"),
                    fieldWithPath("userDTO.createdBy").type(JsonFieldType.NULL)
                        .description("userCreatedBy"),
                    fieldWithPath("createdAt").type(JsonFieldType.NULL).description("postCreatedAt"),
                    fieldWithPath("createdBy").type(JsonFieldType.NULL).description("postCreatedBy")
                ),
                responseFields(
                    fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                    fieldWithPath("data.title").type(JsonFieldType.STRING)
                        .description("Title"),
                    fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("ID"),
                    fieldWithPath("data.content").type(JsonFieldType.STRING)
                        .description("Content"),
                    fieldWithPath("data.userDTO.id").type(JsonFieldType.NUMBER)
                        .description("userID"),
                    fieldWithPath("data.userDTO.name").type(JsonFieldType.STRING)
                        .description("userName"),
                    fieldWithPath("data.userDTO.age").type(JsonFieldType.NUMBER)
                        .description("userAge"),
                    fieldWithPath("data.userDTO.hobby").type(JsonFieldType.STRING)
                        .description("userHobby"),
                    fieldWithPath("data.userDTO.postDTOs").type(JsonFieldType.NULL)
                        .description("userPosts"),
                    fieldWithPath("data.userDTO.createdAt").type(JsonFieldType.STRING)
                        .description("userCreatedAt"),
                    fieldWithPath("data.userDTO.createdBy").type(JsonFieldType.STRING)
                        .description("userCreatedBy"),
                    fieldWithPath("data.createdAt").type(JsonFieldType.STRING)
                        .description("postCreatedAt"),
                    fieldWithPath("data.createdBy").type(JsonFieldType.STRING)
                        .description("postCreatedBy"),
                    fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("응답시간")
                )));
    }
}