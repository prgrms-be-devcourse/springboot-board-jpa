//package com.example.boardbackend.controller;
//
//import com.example.boardbackend.domain.User;
//import com.example.boardbackend.domain.embeded.Email;
//import com.example.boardbackend.dto.PostDto;
//import com.example.boardbackend.dto.UserDto;
//import com.example.boardbackend.dto.request.UpdatePostRequest;
//import com.example.boardbackend.dto.request.UpdateViewRequest;
//import com.example.boardbackend.repository.PostRepository;
//import com.example.boardbackend.repository.UserRepository;
//import com.example.boardbackend.service.PostService;
//import com.example.boardbackend.service.UserService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.restdocs.payload.JsonFieldType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultActions;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDateTime;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
//import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
//import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
//import static org.springframework.restdocs.payload.PayloadDocumentation.*;
//import static org.springframework.restdocs.request.RequestDocumentation.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@AutoConfigureRestDocs
//@AutoConfigureMockMvc
//@SpringBootTest
//class PostControllerTest {
//
//    @Autowired
//    MockMvc mockMvc;
//    @Autowired
//    PostService postService;
//    @Autowired
//    UserService userService;
//    @Autowired
//    PostRepository postRepository;
//    @Autowired
//    UserRepository userRepository;
//    @Autowired
//    ObjectMapper objectMapper;
//
//    Long postId;
//    Long userId;
//
//    @BeforeAll
//    void saveUser() {
//        UserDto userDto = UserDto.builder()
//                .email("test@mail.com")
//                .password("1234")
//                .name("test")
//                .age(20)
//                .hobby("코딩")
//                .build();
//        userId = userService.saveUser(userDto).getId();
//    }
//
//    @BeforeEach
//    void setUp() {
//        UserDto userDto = UserDto.builder()
//                .id(userId)
//                .email("test@mail.com")
//                .password("1234")
//                .name("test")
//                .age(20)
//                .hobby("코딩")
//                .createdAt(LocalDateTime.now())
//                .build();
//        PostDto postDto = PostDto.builder()
//                .title("제목")
//                .content("내용")
//                .userDto(userDto)
//                .build();
//        postId = postService.savePost(postDto).getId();
//    }
//
//    @AfterAll
//    void cleanUp(){
//        userRepository.deleteAll();
//    }
//
//    @AfterEach
//    void tearDown() {
//        postRepository.deleteAll();
//    }
//
////    ------------------------------------------------------------------------------------
//
//    @Test
//    @DisplayName("게시물 삽입 요청을 받을 수 있다.")
//    void createPost_test() throws Exception {
//        // Given
//        UserDto userDto = UserDto.builder()
//                .id(userId)
//                .email("test@mail.com")
//                .password("1234")
//                .name("test")
//                .age(20)
//                .hobby("코딩")
//                .createdAt(LocalDateTime.now())
//                .build();
//        PostDto newPostDto = PostDto.builder()
//                .title("제목")
//                .content("내용")
//                .userDto(userDto)
//                .build();
//        String request = objectMapper.writeValueAsString(newPostDto);
//
//        // When
//        ResultActions resultActions = mockMvc.perform(
//                post("/api/post")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(request)
//        );
//
//        // Then
//        resultActions
//                .andExpect(status().isOk())
//                .andDo(print())
//                .andDo(
//                        document(
//                                "post/create",  preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
//                                requestFields(
//                                        fieldWithPath("id").type(JsonFieldType.NULL).description("id"),
//                                        fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
//                                        fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
//                                        fieldWithPath("view").type(JsonFieldType.NULL).description("view"),
//                                        fieldWithPath("createdAt").type(JsonFieldType.NULL).description("createdAt"),
//
//                                        fieldWithPath("userDto").type(JsonFieldType.OBJECT).description("userDto"),
//                                        fieldWithPath("userDto.id").type(JsonFieldType.NUMBER).description("userDto.id"),
//                                        fieldWithPath("userDto.email").type(JsonFieldType.STRING).description("userDto.email"),
//                                        fieldWithPath("userDto.password").type(JsonFieldType.STRING).description("userDto.password"),
//                                        fieldWithPath("userDto.name").type(JsonFieldType.STRING).description("userDto.name"),
//                                        fieldWithPath("userDto.age").type(JsonFieldType.NUMBER).description("userDto.age"),
//                                        fieldWithPath("userDto.hobby").type(JsonFieldType.STRING).description("userDto.hobby"),
//                                        fieldWithPath("userDto.createdAt").type(JsonFieldType.STRING).description("userDto.createdAt")
//                                ),
//                                responseFields(
//                                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("id"),
//                                        fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
//                                        fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
//                                        fieldWithPath("view").type(JsonFieldType.NULL).description("view"),
//                                        fieldWithPath("createdAt").type(JsonFieldType.STRING).description("createdAt"),
//
//                                        fieldWithPath("userDto").type(JsonFieldType.OBJECT).description("userDto"),
//                                        fieldWithPath("userDto.id").type(JsonFieldType.NUMBER).description("userDto.id"),
//                                        fieldWithPath("userDto.email").type(JsonFieldType.STRING).description("userDto.email"),
//                                        fieldWithPath("userDto.password").type(JsonFieldType.STRING).description("userDto.password"),
//                                        fieldWithPath("userDto.name").type(JsonFieldType.STRING).description("userDto.name"),
//                                        fieldWithPath("userDto.age").type(JsonFieldType.NUMBER).description("userDto.age"),
//                                        fieldWithPath("userDto.hobby").type(JsonFieldType.STRING).description("userDto.hobby"),
//                                        fieldWithPath("userDto.createdAt").type(JsonFieldType.STRING).description("userDto.createdAt")
//                                )
//                        )
//                );
//    }
//
//    @Test
//    @DisplayName("모든 게시물을 조회하는 요청을 pagination하여 응답할 수 있다.")
//    void getAllPosts_test() throws Exception {
//        // Given + When
//        ResultActions resultActions = mockMvc.perform(
//                get("/api/post?page=0&size=10&sort=id,DESC")
//                        .contentType(MediaType.APPLICATION_JSON)
//        );
//
//        // Then
//        resultActions
//                .andExpect(status().isOk())
//                .andDo(print())
//                .andDo(
//                        document(
//                                "post/get_pageable", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
//                                requestParameters(
//                                        parameterWithName("page").description("pageable-page"),
//                                        parameterWithName("size").description("pageable-size"),
//                                        parameterWithName("sort").description("pageable-sort")
//                                ),
//                                responseFields(
//                                        fieldWithPath("content[]").type(JsonFieldType.ARRAY).description("postList"),
//                                        fieldWithPath("content[].id").type(JsonFieldType.NUMBER).description("postId"),
//                                        fieldWithPath("content[].title").type(JsonFieldType.STRING).description("title"),
//                                        fieldWithPath("content[].view").type(JsonFieldType.NUMBER).description("view"),
//                                        fieldWithPath("content[].createdBy").type(JsonFieldType.STRING).description("createdBy"),
//                                        // pageable의 부가적인 response
//                                        fieldWithPath("pageable").type(JsonFieldType.OBJECT).description("pagable"),
//                                        fieldWithPath("pageable.sort").type(JsonFieldType.OBJECT).description("pageable.sort"),
//                                        fieldWithPath("pageable.sort.empty").type(JsonFieldType.BOOLEAN).description("pageable.sort.empty"),
//                                        fieldWithPath("pageable.sort.sorted").type(JsonFieldType.BOOLEAN).description("pageable.sort.sorted"),
//                                        fieldWithPath("pageable.sort.unsorted").type(JsonFieldType.BOOLEAN).description("pageable.sort.unsorted"),
//                                        fieldWithPath("pageable.offset").type(JsonFieldType.NUMBER).description("pageable.offset"),
//                                        fieldWithPath("pageable.pageNumber").type(JsonFieldType.NUMBER).description("pageable.pageNumber"),
//                                        fieldWithPath("pageable.pageSize").type(JsonFieldType.NUMBER).description("pageable.pageSize"),
//                                        fieldWithPath("pageable.paged").type(JsonFieldType.BOOLEAN).description("pageable.paged"),
//                                        fieldWithPath("pageable.unpaged").type(JsonFieldType.BOOLEAN).description("pageable.unpaged"),
//                                        fieldWithPath("totalPages").type(JsonFieldType.NUMBER).description("totalPages"),
//                                        fieldWithPath("totalElements").type(JsonFieldType.NUMBER).description("totalElements"),
//                                        fieldWithPath("last").type(JsonFieldType.BOOLEAN).description("last"),
//                                        fieldWithPath("size").type(JsonFieldType.NUMBER).description("size"),
//                                        fieldWithPath("number").type(JsonFieldType.NUMBER).description("number"),
//                                        fieldWithPath("sort").type(JsonFieldType.OBJECT).description("sort"),
//                                        fieldWithPath("sort.empty").type(JsonFieldType.BOOLEAN).description("sort.empty"),
//                                        fieldWithPath("sort.sorted").type(JsonFieldType.BOOLEAN).description("sort.sorted"),
//                                        fieldWithPath("sort.unsorted").type(JsonFieldType.BOOLEAN).description("sort.unsorted"),
//                                        fieldWithPath("numberOfElements").type(JsonFieldType.NUMBER).description("numberOfElements"),
//                                        fieldWithPath("first").type(JsonFieldType.BOOLEAN).description("first"),
//                                        fieldWithPath("empty").type(JsonFieldType.BOOLEAN).description("empty")
//                                )
//                        )
//                );
//    }
//
//    @Test
//    @DisplayName("특정 유저의 게시물을 조회하는 요청을 받을 수 있다.")
//    void getUserPosts_test() throws Exception {
//        // Given + When
//        ResultActions resultActions = mockMvc.perform(
//                get("/api/post/user/{id}", userId)
//                        .contentType(MediaType.APPLICATION_JSON)
//        );
//
//        // Then
//        resultActions
//                .andExpect(status().isOk())
//                .andDo(print())
//                .andDo(
//                        document(
//                                "post/get_userId", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
//                                pathParameters(
//                                        parameterWithName("id").description("userId")
//                                ),
//                                responseFields(
//                                        fieldWithPath("[]").type(JsonFieldType.ARRAY).description("postList"),
//                                        fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("id"),
//                                        fieldWithPath("[].title").type(JsonFieldType.STRING).description("title"),
//                                        fieldWithPath("[].view").type(JsonFieldType.NUMBER).description("view"),
//                                        fieldWithPath("[].createdBy").type(JsonFieldType.STRING).description("createdBy")
//                                )
//                        )
//                );
//    }
//
//    @Test
//    @DisplayName("게시물의 상세정보 조회 요청을 받을 수 있다.")
//    void getPost_test() throws Exception {
//        // Given + When
//        ResultActions resultActions = mockMvc.perform(
//                get("/api/post/{id}", postId)
//                        .contentType(MediaType.APPLICATION_JSON)
//        );
//
//        // Then
//        resultActions
//                .andExpect(status().isOk())
//                .andDo(print())
//                .andDo(
//                        document(
//                                "post/get_postId", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
//                                pathParameters(
//                                        parameterWithName("id").description("postId")
//                                ),
//                                responseFields(
//                                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("id"),
//                                        fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
//                                        fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
//                                        fieldWithPath("view").type(JsonFieldType.NUMBER).description("view"),
//                                        fieldWithPath("createdAt").type(JsonFieldType.STRING).description("createdAt"),
//
//                                        fieldWithPath("userDto").type(JsonFieldType.OBJECT).description("userDto"),
//                                        fieldWithPath("userDto.id").type(JsonFieldType.NUMBER).description("userDto.id"),
//                                        fieldWithPath("userDto.email").type(JsonFieldType.STRING).description("userDto.email"),
//                                        fieldWithPath("userDto.password").type(JsonFieldType.STRING).description("userDto.password"),
//                                        fieldWithPath("userDto.name").type(JsonFieldType.STRING).description("userDto.name"),
//                                        fieldWithPath("userDto.age").type(JsonFieldType.NUMBER).description("userDto.age"),
//                                        fieldWithPath("userDto.hobby").type(JsonFieldType.STRING).description("userDto.hobby"),
//                                        fieldWithPath("userDto.createdAt").type(JsonFieldType.STRING).description("userDto.createdAt")
//                                )
//                        )
//                );
//    }
//
//    @Test
//    @DisplayName("게시물 업데이트 요청을 받을 수 있다.")
//    void updatePost_test() throws Exception {
//        // Given
//        UpdatePostRequest updateDto = UpdatePostRequest.builder()
//                .title("update")
//                .content("update")
//                .build();
//        String request = objectMapper.writeValueAsString(updateDto);
//
//        // When
//        ResultActions resultActions = mockMvc.perform(
//                patch("/api/post/{id}", postId)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(request)
//        );
//
//        // Then
//        resultActions
//                .andExpect(status().isOk())
//                .andDo(print())
//                .andDo(
//                        document(
//                                "post/update",  preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
//                                pathParameters(
//                                        parameterWithName("id").description("postId")
//                                ),
//                                requestFields(
//                                        fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
//                                        fieldWithPath("content").type(JsonFieldType.STRING).description("content")
//                                ),
//                                responseFields(
//                                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("id"),
//                                        fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
//                                        fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
//                                        fieldWithPath("view").type(JsonFieldType.NUMBER).description("view"),
//                                        fieldWithPath("createdAt").type(JsonFieldType.STRING).description("createdAt"),
//
//                                        fieldWithPath("userDto").type(JsonFieldType.OBJECT).description("userDto"),
//                                        fieldWithPath("userDto.id").type(JsonFieldType.NUMBER).description("userDto.id"),
//                                        fieldWithPath("userDto.email").type(JsonFieldType.STRING).description("userDto.email"),
//                                        fieldWithPath("userDto.password").type(JsonFieldType.STRING).description("userDto.password"),
//                                        fieldWithPath("userDto.name").type(JsonFieldType.STRING).description("userDto.name"),
//                                        fieldWithPath("userDto.age").type(JsonFieldType.NUMBER).description("userDto.age"),
//                                        fieldWithPath("userDto.hobby").type(JsonFieldType.STRING).description("userDto.hobby"),
//                                        fieldWithPath("userDto.createdAt").type(JsonFieldType.STRING).description("userDto.createdAt")
//                                )
//                        )
//                );
//    }
//
////    @Test
////    void updateView_test() throws Exception {
////        // Given
////        UpdateViewRequest viewDto = UpdateViewRequest.builder().newView(1L).build();
////        String request = objectMapper.writeValueAsString(viewDto);
////
////        // When
////        ResultActions resultActions = mockMvc.perform(
////                patch("/api/post/{id}/view", postId)
////                        .contentType(MediaType.APPLICATION_JSON)
////                        .content(request)
////        );
////
////        // Then
////        resultActions
////                .andExpect(status().isOk())
////                .andDo(print())
////                .andDo(
////                        document(
////                                "post/update_view",
////                                pathParameters(
////                                        parameterWithName("id").description("postId")
////                                ),
////                                requestFields(
////                                        fieldWithPath("newView").type(JsonFieldType.NUMBER).description("newView")
////                                ),
////                                responseFields(
////                                        fieldWithPath("").type(JsonFieldType.NUMBER).description("view")
////                                )
////                        )
////                );
////    }
//
//    @Test
//    @DisplayName("게시물 삭제 요청을 받을 수 있다.")
//    void deletePost_test() throws Exception {
//        // Given + When
//        ResultActions resultActions = mockMvc.perform(
//                delete("/api/post/{id}", postId)
//                        .contentType(MediaType.APPLICATION_JSON)
//        );
//
//        // Then
//        resultActions
//                .andExpect(status().isOk())
//                .andDo(print())
//                .andDo(
//                        document(
//                                "post/delete", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
//                                pathParameters(
//                                        parameterWithName("id").description("postId")
//                                )
//                        )
//                );
//    }
//}