package com.juwoong.springbootboardjpa.post.api;

import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.juwoong.springbootboardjpa.post.api.model.PostRequest;
import com.juwoong.springbootboardjpa.post.application.PostService;
import com.juwoong.springbootboardjpa.post.application.model.PostDto;
import com.juwoong.springbootboardjpa.user.application.UserService;

@WebMvcTest
@AutoConfigureRestDocs
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private PostService postService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .apply(documentationConfiguration(restDocumentation))
            .build();
    }

    @DisplayName("Post 생성에 대한 성공 테스트")
    @Test
    public void CreatePostTest() throws Exception {
        // Given
        PostRequest request = new PostRequest(1L, "Sample Post", "This is a sample post content.");

        Long postId = 1L;
        PostDto postDto = new PostDto(postId, request.getUserId(), request.getPostTitle(), request.getPostContent(),
            null, null);

        when(postService.createPost(request.getUserId(), request.getPostTitle(), request.getPostContent())).thenReturn(
            postDto);

        // When and Then
        mockMvc.perform(post("/api/post/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(postId))
            .andExpect(jsonPath("$.userId").value(request.getUserId()))
            .andExpect(jsonPath("$.title").value(request.getPostTitle()))
            .andExpect(jsonPath("$.content").value(request.getPostContent()))
            .andDo(document("create-post",
                requestFields(
                    fieldWithPath("postId").description("The ID of the created post"),
                    fieldWithPath("userId").description("The ID of the user who creates the post"),
                    fieldWithPath("postTitle").description("The title of the post"),
                    fieldWithPath("postContent").description("The content of the post")
                ),
                responseFields(
                    fieldWithPath("id").description("The ID of the created post"),
                    fieldWithPath("userId").description("The ID of the user who created the post"),
                    fieldWithPath("title").description("The title of the post"),
                    fieldWithPath("content").description("The content of the post"),
                    fieldWithPath("createdAt").description("Post creation timestamp"),
                    fieldWithPath("updatedAt").description("Post update timestamp")
                )
            ));
    }

    @DisplayName("Post ID 조회에 대한 성공 테스트")
    @Test
    public void SearchByIdTest() throws Exception {
        // Given
        Long postId = 1L;
        PostDto postDto = new PostDto(postId, null, "Sample Post", "This is a sample post content.", null, null);

        when(postService.searchById(postId)).thenReturn(postDto);

        // When and Then
        mockMvc.perform(get("/api/post/search/{id}", postId))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(postId))
            .andExpect(jsonPath("$.title").value("Sample Post"))
            .andExpect(jsonPath("$.content").value("This is a sample post content."))
            .andDo(document("search-by-id",
                responseFields(
                    fieldWithPath("id").description("The postID of the post"),
                    fieldWithPath("userId").description("The userID of the post"),
                    fieldWithPath("title").description("The title of the post"),
                    fieldWithPath("content").description("The content of the post"),
                    fieldWithPath("createdAt").description("Post creation timestamp"),
                    fieldWithPath("updatedAt").description("Post update timestamp")
                )
            ));
    }

    @DisplayName("Post 페이징 전체조회에 대한 성공 테스트")
    @Test
    public void testGetAllUsers() throws Exception {
        // Given
        int page = 0;
        int size = 2;

        List<PostDto> postDtoList = new ArrayList<>();
        postDtoList.add(new PostDto(1L, null, "Post 1", "Content 1", null, null));
        postDtoList.add(new PostDto(2L, null, "Post 2", "Content 2", null, null));
        Page<PostDto> pageResult = new PageImpl<>(postDtoList, PageRequest.of(page, size), 2);

        when(postService.searchAll(any(PageRequest.class))).thenReturn(pageResult);

        // When and Then
        mockMvc.perform(get("/api/post/search")
                .param("page", String.valueOf(page))
                .param("size", String.valueOf(size)))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content[0].id").value(1L))
            .andExpect(jsonPath("$.content[0].title").value("Post 1"))
            .andExpect(jsonPath("$.content[0].content").value("Content 1"))
            .andExpect(jsonPath("$.content[1].id").value(2L))
            .andExpect(jsonPath("$.content[1].title").value("Post 2"))
            .andExpect(jsonPath("$.content[1].content").value("Content 2"))
            .andDo(document("get-all-posts",
                responseFields(
                    fieldWithPath("content[].id").description("The ID of the post"),
                    fieldWithPath("content[].userId").description("The ID of the user who created the post"),
                    fieldWithPath("content[].title").description("The title of the post"),
                    fieldWithPath("content[].content").description("The content of the post"),
                    fieldWithPath("content[].createdAt").description("Post creation timestamp"),
                    fieldWithPath("content[].updatedAt").description("Post update timestamp"),
                    fieldWithPath("pageable.sort.empty").description("Whether the sort is empty"),
                    fieldWithPath("pageable.sort.unsorted").description("Whether the sort is unsorted"),
                    fieldWithPath("pageable.sort.sorted").description("Whether the sort is sorted"),
                    fieldWithPath("pageable.offset").description("The offset of the page"),
                    fieldWithPath("pageable.pageNumber").description("The current page number"),
                    fieldWithPath("pageable.pageSize").description("The page size"),
                    fieldWithPath("pageable.paged").description("Whether the result is paged"),
                    fieldWithPath("pageable.unpaged").description("Whether the result is unpaged"),
                    fieldWithPath("totalElements").description("Total number of posts"),
                    fieldWithPath("totalPages").description("Total number of pages"),
                    fieldWithPath("last").description("Whether this is the last page"),
                    fieldWithPath("size").description("Number of posts per page"),
                    fieldWithPath("number").description("Current page number"),
                    fieldWithPath("sort.empty").description("Whether the sort is empty"),
                    fieldWithPath("sort.unsorted").description("Whether the sort is unsorted"),
                    fieldWithPath("sort.sorted").description("Whether the sort is sorted"),
                    fieldWithPath("numberOfElements").description("Number of posts on the current page"),
                    fieldWithPath("first").description("Whether this is the first page"),
                    fieldWithPath("empty").description("Whether the result is empty")
                )
            ));
    }

    @DisplayName("Post 수정에 대한 성공 테스트")
    @Test
    public void testEditPost() throws Exception {
        // Given
        PostRequest request = new PostRequest(1L, "Sample Post", "This is a sample post content.");

        Long postId = 2L;
        PostDto postDto = new PostDto(postId, request.getUserId(), request.getPostTitle(), request.getPostContent(),
            null, null);

        when(postService.editPost(request.getPostId(), request.getPostTitle(), request.getPostContent())).thenReturn(
            postDto);

        // When and Then
        mockMvc.perform(put("/api/post/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(postId))
            .andExpect(jsonPath("$.userId").value(request.getUserId()))
            .andExpect(jsonPath("$.title").value(request.getPostTitle()))
            .andExpect(jsonPath("$.content").value(request.getPostContent()))
            .andDo(document("edit-post",
                requestFields(
                    fieldWithPath("postId").description("The ID of the created post"),
                    fieldWithPath("userId").description("The ID of the user who creates the post"),
                    fieldWithPath("postTitle").description("The title of the post"),
                    fieldWithPath("postContent").description("The content of the post")
                ),
                responseFields(
                    fieldWithPath("id").description("The ID of the created post"),
                    fieldWithPath("userId").description("The ID of the user who created the post"),
                    fieldWithPath("title").description("The title of the post"),
                    fieldWithPath("content").description("The content of the post"),
                    fieldWithPath("createdAt").description("Post creation timestamp"),
                    fieldWithPath("updatedAt").description("Post update timestamp")
                )
            ));
    }

}
