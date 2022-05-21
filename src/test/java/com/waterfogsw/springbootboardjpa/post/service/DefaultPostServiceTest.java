package com.waterfogsw.springbootboardjpa.post.service;

import com.waterfogsw.springbootboardjpa.common.exception.AuthenticationException;
import com.waterfogsw.springbootboardjpa.common.exception.ResourceNotFoundException;
import com.waterfogsw.springbootboardjpa.post.entity.Post;
import com.waterfogsw.springbootboardjpa.post.repository.PostRepository;
import com.waterfogsw.springbootboardjpa.user.entity.User;
import com.waterfogsw.springbootboardjpa.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultPostServiceTest {

    @Mock
    UserService userService;

    @Mock
    PostRepository postRepository;

    @InjectMocks
    DefaultPostService defaultPostService;

    private User generateTestUser(String name, String email) {
        return User.builder()
                .name(name)
                .email(email)
                .build();
    }

    private Post generateTestPost(String title, String content) {
        return Post.builder()
                .title(title)
                .content(content)
                .build();
    }

    @Nested
    @DisplayName("addPost 메서드는")
    class Describe_addPost {

        @Nested
        @DisplayName("모든 값이 유효하면")
        class Context_with_ValidData {

            @Test
            @DisplayName("작성자를 설정하고, Repository 의 save 메서드를 호출한다")
            void It_UpdateAuthorAndCallSave() {
                //given
                final var userId = 1L;
                final var testUser = generateTestUser("test", "test@naver.com");
                final var testPost = generateTestPost("test", "test");

                given(userService.getOne(anyLong())).willReturn(testUser);
                given(postRepository.save(any(Post.class))).willReturn(testPost);

                //when
                defaultPostService.addPost(userId, testPost);

                //then
                assertNotNull(testPost.getUser());
                verify(postRepository).save(any(Post.class));
            }
        }

        @Nested
        @DisplayName("userId 가 양수가 아니면")
        class Context_with_NotPositiveUserId {

            @ParameterizedTest
            @ValueSource(longs = {0, -1, Long.MIN_VALUE})
            @DisplayName("IllegalArgumentException 이 발생한다")
            void It_ThrowsIllegalArgumentException(long src) {
                //given
                final var testPost = generateTestPost("test", "test");

                //when, then
                assertThrows(IllegalArgumentException.class,
                        () -> defaultPostService.addPost(src, testPost));
            }
        }

        @Nested
        @DisplayName("post 가 null 이면")
        class Context_with_NullPost {

            @Test
            @DisplayName("IllegalArgumentException 이 발생한다")
            void It_ThrowsIllegalArgumentException() {
                //given
                final var userId = 1L;

                //when, then
                assertThrows(IllegalArgumentException.class,
                        () -> defaultPostService.addPost(userId, null));
            }
        }
    }

    @Nested
    @DisplayName("getOne 메서드는")
    class Describe_getOne {

        @Nested
        @DisplayName("id 값이 양수이고, 해당 엔티티가 존재하면")
        class Context_with_PositiveIdAndEntityExist {

            @ParameterizedTest
            @ValueSource(longs = {1, Long.MAX_VALUE})
            @DisplayName("해당 엔티티를 반환한다")
            void It_ResponseEntity(long src) {
                //given
                final var testPost = generateTestPost("test", "test");
                given(postRepository.findById(anyLong())).willReturn(Optional.of(testPost));

                //when
                defaultPostService.getOne(src);

                //then
                verify(postRepository).findById(anyLong());
            }
        }

        @Nested
        @DisplayName("id 값이 양수이고, 해당 엔티티가 존재하지 않으면")
        class Context_with_PositiveIdAndEntityNotExist {

            @ParameterizedTest
            @ValueSource(longs = {1, Long.MAX_VALUE})
            @DisplayName("ResourceNotFoundException 이 발생한다")
            void It_ThrowsResourceNotFoundException(long src) {
                //given
                given(postRepository.findById(anyLong())).willReturn(Optional.empty());

                //when, then
                assertThrows(ResourceNotFoundException.class, () -> defaultPostService.getOne(src));
            }
        }

        @Nested
        @DisplayName("id 값이 양수가 아닌경우")
        class Context_with_NotPositiveId {

            @ParameterizedTest
            @ValueSource(longs = {0, -1, Long.MIN_VALUE})
            @DisplayName("IllegalArgumentException 이 발생한다")
            void It_ThrowsIllegalArgumentException(long src) {
                //when, then
                assertThrows(IllegalArgumentException.class,
                        () -> defaultPostService.getOne(src));
            }
        }
    }

    @Nested
    @DisplayName("getAll 메서드는")
    class Context_with_getAll {

        @Test
        @DisplayName("pageable 이 null 이면")
        void It_() {
            //when, then
            assertThrows(IllegalArgumentException.class,
                    () -> defaultPostService.getAll(null));

        }
    }

    @Nested
    @DisplayName("updatePost 메서드는")
    class Describe_updatePost {

        @Nested
        @DisplayName("모든 값이 유효하면")
        class Context_with_ValidData {

            @ParameterizedTest
            @ValueSource(longs = {1, Long.MAX_VALUE})
            @DisplayName("해당 post 를 업데이트 한다")
            void It_UpdatePost(long src) {
                //given
                final var testUser = generateTestUser("test", "test@naver.com");
                final var updatePost = generateTestPost("updatedTitle", "updatedContent");
                final var savedPost = generateTestPost("test", "test");

                ReflectionTestUtils.setField(testUser, "id", src);
                ReflectionTestUtils.setField(savedPost, "id", src);
                savedPost.updateAuthor(testUser);

                when(userService.getOne(anyLong())).thenReturn(testUser);
                when(postRepository.findById(anyLong())).thenReturn(Optional.of(savedPost));

                //when
                defaultPostService.updatePost(src, src, updatePost);

                // then
                assertEquals("updatedTitle", updatePost.getTitle());
                assertEquals("updatedContent", updatePost.getContent());
            }
        }

        @Nested
        @DisplayName("userId 값이 양수가 아니면")
        class Context_with_NotPositiveUserId {

            @ParameterizedTest
            @ValueSource(longs = {0, -1, Long.MIN_VALUE})
            @DisplayName("IllegalArgumentException 예외를 던진다")
            void It_ThrowIllegalArgumentException(long src) {
                //given
                final var testPost = generateTestPost("test", "test");
                final var testPostId = 1L;

                //when, then
                assertThrows(IllegalArgumentException.class,
                        () -> defaultPostService.updatePost(src, testPostId, testPost));
            }
        }

        @Nested
        @DisplayName("postId 값이 양수가 아니면")
        class Context_with_NotPositivePostId {

            @ParameterizedTest
            @ValueSource(longs = {0, -1, Long.MIN_VALUE})
            @DisplayName("IllegalArgumentException 예외를 던진다")
            void It_ThrowIllegalArgumentException(long src) {
                //given
                final var testPost = generateTestPost("test", "test");
                final var testUserId = 1L;

                //when, then
                assertThrows(IllegalArgumentException.class,
                        () -> defaultPostService.updatePost(testUserId, src, testPost));
            }
        }

        @Nested
        @DisplayName("post 가 null 이면")
        class Context_with_NullPost {

            @Test
            @DisplayName("IllegalArgumentException 예외를 던진다")
            void It_ThrowIllegalArgumentException() {
                //given
                final var testUserId = 1L;
                final var testPostId = 1L;

                //when, then
                assertThrows(IllegalArgumentException.class,
                        () -> defaultPostService.updatePost(testUserId, testPostId, null));
            }
        }
        
        @Nested
        @DisplayName("수정하는 유저 id 와 게시글 작성 유저 id 가 일치하지 않으면")
        class Context_with_NotMatch {

            @Test
            @DisplayName("AuthenticationException 예외가 발생한다")
            void It_ResponseAuthenticationException() {
                //given
                final var originUserId = 1L;
                final var updatingUserId = 2L;
                final var savedPostId = 1L;

                final var updatingUser = generateTestUser("test", "test@naver.com");
                final var originUser = generateTestUser("test", "test@naver.com");

                ReflectionTestUtils.setField(updatingUser, "id", updatingUserId);
                ReflectionTestUtils.setField(originUser, "id", originUserId);

                final var updatePost = generateTestPost("updatedTitle", "updatedContent");
                final var savedPost = generateTestPost("test", "test");

                ReflectionTestUtils.setField(savedPost, "id", savedPostId);
                savedPost.updateAuthor(originUser);

                when(userService.getOne(anyLong())).thenReturn(updatingUser);
                when(postRepository.findById(anyLong())).thenReturn(Optional.of(savedPost));

                //when, then
                assertThrows(AuthenticationException.class,
                        () -> defaultPostService.updatePost(updatingUserId, savedPostId, updatePost));
            }
        }
        
    }
}
