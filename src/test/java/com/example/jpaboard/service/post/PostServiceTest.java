package com.example.jpaboard.service.post;

import com.example.jpaboard.domain.post.Post;
import com.example.jpaboard.domain.post.PostRepository;
import com.example.jpaboard.domain.user.User;
import com.example.jpaboard.domain.user.UserRepository;
import com.example.jpaboard.exception.CustomException;
import com.example.jpaboard.service.dto.post.PostResponse;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import static com.example.jpaboard.exception.ErrorCode.INVALID_REQUEST;
import static com.example.jpaboard.exception.ErrorCode.POST_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class PostServiceTest {
    @InjectMocks
    PostServiceImpl postService;

    @Mock
    PostRepository postRepository;

    @Mock
    UserRepository userRepository;

    private static final User user = new User("이름", 10, "운동");
    private static final Post post = new Post("제목", "내용", user);

    @BeforeAll
    public static void setup() {
        Field postId;
        Field userId;
        try {
            postId = post.getClass().getDeclaredField("id");
            postId.setAccessible(true);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e.getMessage());
        }

        try {
            userId = user.getClass().getDeclaredField("id");
            userId.setAccessible(true);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e.getMessage());
        }

        try {
            postId.set(post, 1L);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e.getMessage());
        }

        try {
            userId.set(user, 1L);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Nested
    class findAll메서드는 {
        private final PageRequest pageRequest = PageRequest.of(0, 10);

        @Test
        @DisplayName("전체 게시글을 조회한다")
        void 전체_게시글을_조회한다() {
            //given
            List<Post> createdPosts = Arrays.asList(new Post("제목1", "내용1", user), new Post("제목2", "내용2", user));
            given(postRepository.findWithPagination(any(Pageable.class))).willReturn(createdPosts);

            //when
            List<PostResponse> posts = postService.findAll(pageRequest);

            //then
            verify(postRepository).findWithPagination(any(Pageable.class));
            assertThat(posts.size()).isEqualTo(2);
        }

        @Nested
        class 저장된_게시글이_없다면 {
            @Test
            @DisplayName("빈 리스트를 반환한다")
            void 빈_리스트를_반환한다() {
                //given
                //when
                List<PostResponse> posts = postService.findAll(pageRequest);

                //then
                assertThat(posts).isEmpty();
            }
        }

        @Nested
        class findById메서드는 {
            @Test
            @DisplayName("아이디로 게시글을 한건 조회한다")
            void 아이디로_게시글을_한건_조회한다() {
                //given
                given(postRepository.findById(anyLong())).willReturn(Optional.of(post));

                //when
                PostResponse postResponse = postService.findById(post.getId());

                //then
                verify(postRepository).findById(anyLong());
                assertThat(postResponse).isNotNull();
            }

            @Nested
            class 아이디가_null이라면 {
                @Test
                @DisplayName("BadRequest 응답을 보낸다")
                void BadRequest_응답을_보낸다() {
                    //given
                    given(postRepository.findById(anyLong())).willThrow(new CustomException(INVALID_REQUEST));

                    //when,then
                    assertThatThrownBy(() ->
                            postService.findById(post.getId()))
                                       .isInstanceOf(CustomException.class)
                                       .hasMessage("필수 입력 값이 없습니다.");
                }
            }

            @Nested
            class 아이디에_해당되는_게시글이_없다면 {
                @Test
                @DisplayName("NotFound 응답을 보낸다")
                void NotFound_응답을_보낸다() {
                    //given
                    given(postRepository.findById(anyLong())).willThrow(new CustomException(POST_NOT_FOUND));

                    //when,then
                    assertThatThrownBy(() ->
                            postService.findById(post.getId()))
                                       .isInstanceOf(CustomException.class)
                                       .hasMessage("해당 게시글을 찾을 수 없습니다.");
                }
            }
        }
    }

    @Nested
    class save메서드는 {
        @Test
        @DisplayName("게시글을 저장한다")
        void 게시글을_저장한다() {
            //given
            given(userRepository.findById(anyLong())).willReturn(Optional.of(user));

            //when
            postService.save(1L, post.getTitle(), post.getContent());

            //then
            verify(userRepository).findById(anyLong());
            verify(postRepository).save(any());
        }

        @Nested
        class 유저_아이디_또는_제목_또는_내용이_null이라면 {
            @DisplayName("BadRequest 응답을 보낸다")
            @ParameterizedTest
            @MethodSource("com.example.jpaboard.service.post.PostServiceProvider#provideStrings")
            void BadRequest_응답을_보낸다(Long userId, String title, String content) {
                //given
                //when,then
                assertThatThrownBy(() ->
                        postService.save(userId, title, content))
                                   .isInstanceOf(CustomException.class)
                                   .hasMessage("필수 입력 값이 없습니다.");
            }
        }

        @Nested
        class 유저를_찾을_수_없다면 {
            @Test
            @DisplayName("NotFound 응답을 보낸다")
            void NotFound_응답을_보낸다() {
                //given
                given(userRepository.findById(anyLong())).willReturn(Optional.empty());

                //when,then
                assertThatThrownBy(() ->
                        postService.save(1L, post.getTitle(), post.getContent()))
                                   .isInstanceOf(CustomException.class)
                                   .hasMessage("해당 사용자를 찾을 수 없습니다.");
            }
        }
    }

    @Nested
    class update메서드는 {
        @Test
        @DisplayName("게시글을 수정하고 수정된 게시글을 반환한다")
        void 게시글을_수정하고_수정된_게시글을_반환한다() {
            //given
            given(userRepository.findById(anyLong())).willReturn(Optional.of(user));
            given(postRepository.findById(anyLong())).willReturn(Optional.of(post));

            //when
            PostResponse postResponse = postService.update(post.getId(), 1L, post.getTitle(), post.getContent());

            //then
            verify(userRepository).findById(anyLong());
            verify(postRepository).findById(anyLong());
            assertThat(postResponse).isNotNull();
        }

        @Nested
        class 유저_아이디_또는_제목_또는_내용이_null이라면 {
            @DisplayName("BadRequest 응답을 보낸다")
            @ParameterizedTest
            @MethodSource("com.example.jpaboard.service.post.PostServiceProvider#provideStrings")
            void BadRequest_응답을_보낸다(Long userId, String title, String content) {
                //given
                //when,then
                assertThatThrownBy(() ->
                        postService.save(userId, title, content))
                                   .isInstanceOf(CustomException.class)
                                   .hasMessage("필수 입력 값이 없습니다.");
            }
        }

        @Nested
        class 유저를_찾을_수_없다면 {
            @Test
            @DisplayName("NotFound 응답을 보낸다")
            void NotFound_응답을_보낸다() {
                //given
                given(userRepository.findById(anyLong())).willReturn(Optional.empty());

                //when,then
                assertThatThrownBy(() ->
                        postService.update(post.getId(), 1L, post.getTitle(), post.getContent()))
                                   .isInstanceOf(CustomException.class)
                                   .hasMessage("해당 사용자를 찾을 수 없습니다.");
            }
        }

        @Nested
        class 게시글을_찾을_수_없다면 {
            @Test
            @DisplayName("NotFound 응답을 보낸다")
            void NotFound_응답을_보낸다() {
                //given
                given(userRepository.findById(anyLong())).willReturn(Optional.of(user));
                given(postRepository.findById(anyLong())).willReturn(Optional.empty());

                //when,then
                assertThatThrownBy(() ->
                        postService.update(post.getId(), 1L, post.getTitle(), post.getContent()))
                                   .isInstanceOf(CustomException.class)
                                   .hasMessage("해당 게시글을 찾을 수 없습니다.");
            }
        }

        @Nested
        class 게시글의_작성자와_유저가_다르다면 {
            @Test
            @DisplayName("Forbidden 응답을 보낸다")
            void Forbidden_응답을_보낸다() {
                //given
                given(userRepository.findById(anyLong())).willReturn(Optional.of(user));
                given(postRepository.findById(anyLong())).willReturn(Optional.of(new Post("제목", "내용", new User("이름", 10, "운동"))));

                //when,then
                assertThatThrownBy(() ->
                        postService.update(post.getId(), 1L, post.getTitle(), post.getContent()))
                                   .isInstanceOf(CustomException.class)
                                   .hasMessage("접근 권한이 없습니다.");
            }
        }
    }
}

class PostServiceProvider {
    public static Stream<Arguments> provideStrings() { // argument source method
        return Stream.of(Arguments.of(null, "제목", "내용"),
                         Arguments.of(1L, null, "내용"),
                         Arguments.of(1L, "제목", null));
    }
}
