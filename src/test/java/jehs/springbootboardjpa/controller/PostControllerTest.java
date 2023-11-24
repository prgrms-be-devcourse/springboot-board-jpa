package jehs.springbootboardjpa.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jehs.springbootboardjpa.dto.PostCreateRequest;
import jehs.springbootboardjpa.dto.PostUpdateRequest;
import jehs.springbootboardjpa.entity.Post;
import jehs.springbootboardjpa.entity.User;
import jehs.springbootboardjpa.repository.PostRepository;
import jehs.springbootboardjpa.repository.UserRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("ID로 Post를 조회할 수 있습니다.")
    @Transactional
    void testGetPostById() throws Exception {
        // Given
        User user = userRepository.findById(1L).orElseThrow();
        Post post = postRepository.save(Post.builder()
                .title("Test Title")
                .content("Test Content")
                .user(user)
                .postType(Post.PostType.FREE)
                .build()
        );

        // When
        ResultActions perform = mockMvc.perform(get("/api/v1/posts/{postId}", post.getId())
                .contentType(MediaType.APPLICATION_JSON));

        // Then
        perform
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("성공적으로 게시글이 조회되었습니다."))
                .andExpect(jsonPath("$.data.id").value(post.getId()))
                .andExpect(jsonPath("$.data.title").value(post.getTitle()))
                .andExpect(jsonPath("$.data.content").value(post.getContent()))
                .andExpect(jsonPath("$.data.postType").value(post.getPostType().toString()))
                .andExpect(jsonPath("$.data.userResponse.id").value(user.getId()))
                .andExpect(jsonPath("$.data.userResponse.name").value(user.getName()))
                .andExpect(jsonPath("$.data.userResponse.age").value(user.getAge()))
                .andExpect(jsonPath("$.data.userResponse.hobby").value(user.getHobby()))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andDo(document("getPostById",
                        pathParameters(parameterWithName("postId").description("게시글 아이디")),
                        responseFields(
                                fieldWithPath("message").description("성공 메시지"),
                                fieldWithPath("data.id").description("게시글 아이디"),
                                fieldWithPath("data.title").description("게시글 제목"),
                                fieldWithPath("data.content").description("게시글 내용"),
                                fieldWithPath("data.postType").description("게시글 종류"),
                                fieldWithPath("data.createdAt").description("게시글 생성시간"),
                                fieldWithPath("data.updatedAt").description("게시글 수정시간"),
                                fieldWithPath("data.userResponse.id").description("게시자 아이디"),
                                fieldWithPath("data.userResponse.name").description("게시자 이름"),
                                fieldWithPath("data.userResponse.age").description("게시자 나이"),
                                fieldWithPath("data.userResponse.hobby").description("게시자 취미")
                        ))
                );
    }

    @DisplayName("페이지네이션을 통해 모든 게시글을 조회할 수 있습니다")
    @CsvSource({
            "0, 10",
            "0, 30",
            "1, 20",
            "2, 10",
            "3, 10",
    })
    @ParameterizedTest
    @Transactional
    void testGetPostPagination(int page, int size) throws Exception {
        // Given
        User user = userRepository.findById(1L).orElseThrow();
        List<Post> postList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            postList.add(
                    postRepository.save(Post.builder()
                            .title("Test Title")
                            .content("Test Content")
                            .user(user)
                            .postType(Post.PostType.FREE)
                            .build()
                    )
            );
        }

        // When
        ResultActions perform = mockMvc.perform(get("/api/v1/posts")
                .param("page", String.valueOf(page))
                .param("size", String.valueOf(size))
                .contentType(MediaType.APPLICATION_JSON));

        // Then
        final int index = page * size;
        Post post = postList.get(index);
        perform
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("성공적으로 모든 게시글이 조회되었습니다."))
                .andExpect(jsonPath("$.data.content[0].id").value(post.getId()))
                .andExpect(jsonPath("$.data.content[0].title").value(post.getTitle()))
                .andExpect(jsonPath("$.data.content[0].content").value(post.getContent()))
                .andExpect(jsonPath("$.data.content[0].postType").value(post.getPostType().toString()))
                .andExpect(jsonPath("$.data.content[0].userResponse.id").value(user.getId()))
                .andExpect(jsonPath("$.data.content[0].userResponse.name").value(user.getName()))
                .andExpect(jsonPath("$.data.content[0].userResponse.age").value(user.getAge()))
                .andExpect(jsonPath("$.data.content[0].userResponse.hobby").value(user.getHobby()))
                .andExpect(jsonPath("$.data.content", Matchers.hasSize(size)))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andDo(document("getAllPosts",
                        responseFields(
                                fieldWithPath("message").description("성공 메시지"),
                                fieldWithPath("data.content[].id").description("게시글 아이디"),
                                fieldWithPath("data.content[].title").description("게시글 제목"),
                                fieldWithPath("data.content[].content").description("게시글 내용"),
                                fieldWithPath("data.content[].postType").description("게시글 종류"),
                                fieldWithPath("data.content[].createdAt").description("게시글 생성시간"),
                                fieldWithPath("data.content[].updatedAt").description("게시글 수정시간"),
                                fieldWithPath("data.content[].userResponse.id").description("게시자 아이디"),
                                fieldWithPath("data.content[].userResponse.name").description("게시자 이름"),
                                fieldWithPath("data.content[].userResponse.age").description("게시자 나이"),
                                fieldWithPath("data.content[].userResponse.hobby").description("게시자 취미"),
                                fieldWithPath("data.pageable").description("페이징 정보").optional(),
                                fieldWithPath("data.pageable.pageNumber").description("현재 페이지 번호"),
                                fieldWithPath("data.pageable.pageSize").description("페이지 크기"),
                                fieldWithPath("data.pageable.sort").description("정렬 정보").optional(),
                                fieldWithPath("data.pageable.sort.empty").description("정렬이 비어있는지 여부"),
                                fieldWithPath("data.pageable.sort.unsorted").description("정렬되지 않았는지 여부"),
                                fieldWithPath("data.pageable.sort.sorted").description("정렬되었는지 여부"),
                                fieldWithPath("data.pageable.offset").description("현재 페이지의 시작 오프셋"),
                                fieldWithPath("data.pageable.unpaged").description("페이징되지 않은지 여부"),
                                fieldWithPath("data.pageable.paged").description("페이징된지 여부"),
                                fieldWithPath("data.last").description("마지막 페이지 여부"),
                                fieldWithPath("data.totalPages").description("전체 페이지 수"),
                                fieldWithPath("data.totalElements").description("전체 엘리먼트 수"),
                                fieldWithPath("data.first").description("첫 번째 페이지 여부"),
                                fieldWithPath("data.size").description("현재 페이지의 엘리먼트 수"),
                                fieldWithPath("data.number").description("현재 페이지 번호"),
                                fieldWithPath("data.sort").description("정렬 정보").optional(),
                                fieldWithPath("data.sort.empty").description("정렬이 비어있는지 여부"),
                                fieldWithPath("data.sort.unsorted").description("정렬되지 않았는지 여부"),
                                fieldWithPath("data.sort.sorted").description("정렬되었는지 여부"),
                                fieldWithPath("data.numberOfElements").description("현재 페이지의 엘리먼트 수"),
                                fieldWithPath("data.empty").description("데이터가 비어있는지 여부")
                        ))
                );
    }

    @Test
    @DisplayName("게시글을 생성할 수 있습니다.")
    @Transactional
    void testCreatePost() throws Exception {
        // Given
        final String testTitle = "Test Title";
        PostCreateRequest postCreateRequest = new PostCreateRequest(
                testTitle,
                "Test Content",
                1L,
                Post.PostType.FREE
        );

        // When
        ResultActions perform = mockMvc.perform(post("/api/v1/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(postCreateRequest)));

        // Then
        perform
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("성공적으로 게시글이 생성되었습니다."))
                .andDo(document("createPost",
                                requestFields(
                                        fieldWithPath("title").description("게시글 제목"),
                                        fieldWithPath("content").description("게시글 내용"),
                                        fieldWithPath("userId").description("게시자 아이디"),
                                        fieldWithPath("postType").description("게시글 종류")
                                ),
                                responseFields(
                                        fieldWithPath("message").description("성공 메시지")
                                )
                        )
                );
        List<Post> postList = postRepository.findAll();
        assertThat(postList).hasSize(1);
    }

    @Test
    @DisplayName("게시글을 수정할 수 있습니다.")
    @Transactional
    void testUpdatePost() throws Exception {
        // Given
        User user = userRepository.findById(1L).orElseThrow();
        Post post = postRepository.save(Post.builder()
                .title("Test Title")
                .content("Test Content")
                .user(user)
                .postType(Post.PostType.FREE)
                .build()
        );
        final String updatedTitle = "Updated Title";
        PostUpdateRequest postUpdateRequest = new PostUpdateRequest(
                updatedTitle,
                "Updated Content",
                1L
        );

        // When
        ResultActions perform = mockMvc.perform(patch("/api/v1/posts/{postId}", post.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(postUpdateRequest)));

        // Then
        perform
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("성공적으로 게시글이 수정되었습니다."))
                .andDo(document("updatePost",
                                requestFields(
                                        fieldWithPath("title").description("게시글 제목"),
                                        fieldWithPath("content").description("게시글 내용"),
                                        fieldWithPath("userId").description("게시자 아이디")
                                ),
                                responseFields(
                                        fieldWithPath("message").description("성공 메시지")
                                )
                        )
                );
        Optional<Post> foundPost = postRepository.findById(post.getId());
        assertThat(foundPost).isNotEmpty();
        assertThat(foundPost.get().getTitle()).isEqualTo(updatedTitle);
    }
}
