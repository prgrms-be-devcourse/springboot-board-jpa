package com.example.board.service;

import com.example.board.dto.PostDetailResponseDto;
import com.example.board.dto.PostDto;
import com.example.board.dto.PostResponseDto;
import com.example.board.dto.PostUpdateDto;
import com.example.board.exception.BaseException;
import com.example.board.exception.ErrorMessage;
import com.example.board.model.Post;
import com.example.board.repository.PostRepository;
import com.example.board.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
class PostServiceTest {

    @Autowired
    PostService postService;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    // TODO: user 생성 후 그 id 적용해보기, userName 유효성 검사
    @Nested
    @DisplayName("게시글 생성")
    class PostNew {
        @Test
        @DisplayName("정상적으로 PostDto를 받아 글 작성을 완료하는 경우")
        void postSuccessInService() {
            PostDto postDto = new PostDto(1L, "test", "test Contents");

            Long save = postService.save(postDto);
            Optional<Post> savedPosts = postRepository.findById(save);

            assertThat(savedPosts).isNotEmpty();
        }

        @Test
        @DisplayName("제목이 20자 이상인 경우 실패한다.")
        void postFailByTitleLength() {
            // given
            PostDto postDto = new PostDto(1L, "testtesttesttesttesttesttest", "test Contents");

            // when, then
            assertThatThrownBy(() -> postService.save(postDto))
                    .isInstanceOf(BaseException.class)
                    .hasMessage(ErrorMessage.WRONG_TITLE_VALUE.getMessage());
        }

        @ParameterizedTest(name = "title이 `{0}`인 경우 저장 로직에서 실패한다.")
        @ValueSource(strings = {" ", "   "})
        @NullAndEmptySource
        @DisplayName("제목이 null이거나 빈 공백인 경우 실패한다.")
        void postFailByTitleNullOrBlank(String input) {
            // given
            PostDto postDto = new PostDto(1L, input, "test Contents");

            // when, then
            assertThatThrownBy(() -> postService.save(postDto))
                    .isInstanceOf(BaseException.class)
                    .hasMessage(ErrorMessage.WRONG_TITLE_VALUE.getMessage());
        }

        @ParameterizedTest(name = "contents가 `{0}`인 경우 저장 로직에서 실패한다.")
        @ValueSource(strings = {" ", "   "})
        @NullAndEmptySource
        @DisplayName("내용이 null이거나 빈 공백인 경우 실패한다.")
        void postFailByContents(String input) {
            // given
            PostDto postDto = new PostDto(1L, "title", input);

            // when, then
            assertThatThrownBy(() -> postService.save(postDto))
                    .isInstanceOf(BaseException.class)
                    .hasMessage(ErrorMessage.WRONG_CONTENTS_VALUE.getMessage());
        }

        @Test
        @DisplayName("존재하지 않는 유저가 글을 작성하는 경우 예외를 발생시킨다.")
        void postFailWithAnonymousUser() {
            PostDto postDto = new PostDto(0L, "test", "test Contents");

            assertThatThrownBy(() -> postService.save(postDto))
                    .isInstanceOf(BaseException.class)
                    .hasMessage(ErrorMessage.USER_NOT_FOUND.getMessage());
        }
    }

    @Nested
    @DisplayName("게시글 전체 조회")
    class PostGetAll {
        @Test
        @DisplayName("정상적으로 모든 게시물들을 받아온다.")
        void readAllSuccess() {
            for (int i = 0; i < 15; i++) {
                PostDto postDto = new PostDto(1L, "title" + i, "contents" + i);
                postService.save(postDto);
            }
            // TODO: pageable 직접 설정하는 것이 괜찮을까?
            Pageable pageable = PageRequest.of(0, 10);
            Page<PostResponseDto> postPage = postService.readAllPost(pageable);
            assertThat(postPage.getContent()).hasSize(10);
            assertThat(postPage.getTotalPages()).isEqualTo(2);

            Pageable pageable2 = PageRequest.of(1, 10);
            Page<PostResponseDto> postPage2 = postService.readAllPost(pageable2);
            assertThat(postPage2.getContent()).hasSize(5);
        }
    }

    @Nested
    @DisplayName("게시글 단건 조회")
    class PostGetOne {
        @Test
        @DisplayName("정상적으로 특정 게시물 하나를 받아온다.")
        void getPostByIdSuccess() {
            PostDto postDto = new PostDto(1L, "title", "contents");
            Long savedId = postService.save(postDto);

            PostDetailResponseDto postDetailResponseDto = postService.readPostDetail(savedId);
            assertThat(postDetailResponseDto.postId()).isEqualTo(savedId);
        }

        @Test
        @DisplayName("존재하지 않는 게시물을 호출하여 단건 조회에 실패한다.")
        void getPostByIdNotFoundFail() {
            assertThatThrownBy(() -> postService.readPostDetail(0L))
                    .isInstanceOf(BaseException.class)
                    .hasMessage(ErrorMessage.POST_NOT_FOUND.getMessage());
        }
    }

    @Nested
    @DisplayName("게시글 수정")
    class PostUpdate {
        @Test
        @DisplayName("정상적으로 PostDto를 받아 글 수정을 완료하는 경우")
        void updateSuccessInService() {
            PostDto postDto = new PostDto(1L, "test", "test Contents");
            Long savedId = postService.save(postDto);

            String updatedTitle = "test update";
            String updatedContents = "test Contents update";
            PostUpdateDto postUpdateDto = new PostUpdateDto(1L, updatedTitle, updatedContents);

            Long userId = postUpdateDto.userId();
            postService.update(savedId, postUpdateDto, userId);
            PostDetailResponseDto postDetailResponseDto = postService.readPostDetail(savedId);

            assertThat(postDetailResponseDto.title()).isEqualTo(updatedTitle);
            assertThat(postDetailResponseDto.contents()).isEqualTo(updatedContents);
        }

        @Test
        @DisplayName("존재하지 않는 게시물을 호출하여 수정에 실패한다.")
        void updateByIdNotFoundFail() {
            Long userId = 1L;
            String updatedTitle = "test update";
            String updatedContents = "test Contents update";
            PostUpdateDto postUpdateDto = new PostUpdateDto(userId, updatedTitle, updatedContents);
            assertThatThrownBy(() -> postService.update(0L, postUpdateDto, postUpdateDto.userId()))
                    .isInstanceOf(BaseException.class).hasMessage(ErrorMessage.POST_NOT_FOUND.getMessage());
        }

        @Test
        @DisplayName("제목이 20자 이상인 경우 수정에 실패한다.")
        void updateFailByTitleLength() {
            // given
            PostDto postDto = new PostDto(1L, "test", "test Contents");
            Long savedId = postService.save(postDto);

            PostUpdateDto postUpdateDto = new PostUpdateDto(1L, "testtesttesttesttesttesttest", "test Contents");

            // when, then
            assertThatThrownBy(() -> postService.update(savedId, postUpdateDto, postUpdateDto.userId()))
                    .isInstanceOf(BaseException.class)
                    .hasMessage(ErrorMessage.WRONG_TITLE_VALUE.getMessage());
        }

        @ParameterizedTest(name = "title이 `{0}`인 경우 수정 로직에서 실패한다.")
        @ValueSource(strings = {" ", "   "})
        @NullAndEmptySource
        @DisplayName("제목이 null이거나 빈 공백인 경우 수정에 실패한다.")
        void updateFailByTitleNullOrBlank(String input) {
            // given
            PostDto postDto = new PostDto(1L, "test", "test Contents");
            Long savedId = postService.save(postDto);

            PostUpdateDto postUpdateDto = new PostUpdateDto(1L, input, "test Contents");

            // when, then
            assertThatThrownBy(() -> postService.update(savedId, postUpdateDto, postUpdateDto.userId()))
                    .isInstanceOf(BaseException.class)
                    .hasMessage(ErrorMessage.WRONG_TITLE_VALUE.getMessage());
        }

        @ParameterizedTest(name = "contents가 `{0}`인 경우 수정 로직에서 실패한다.")
        @ValueSource(strings = {" ", "   "})
        @NullAndEmptySource
        @DisplayName("내용이 null이거나 빈 공백인 경우 실패한다.")
        void updateFailByContents(String input) {
            // given
            PostDto postDto = new PostDto(1L, "test", "test Contents");
            Long savedId = postService.save(postDto);

            PostUpdateDto postUpdateDto = new PostUpdateDto(1L, "title", input);

            // when, then
            assertThatThrownBy(() -> postService.update(savedId, postUpdateDto, postUpdateDto.userId()))
                    .isInstanceOf(BaseException.class)
                    .hasMessage(ErrorMessage.WRONG_CONTENTS_VALUE.getMessage());
        }

        @Test
        @DisplayName("존재하지 않는 유저가 글을 수정하는 경우 예외를 발생시킨다.")
        void updateFailWithAnonymousUser() {
            // given
            PostDto postDto = new PostDto(1L, "test", "test Contents");
            Long savedId = postService.save(postDto);

            PostUpdateDto postUpdateDto = new PostUpdateDto(0L, "title update", "test Contents update");


            // when, then
            assertThatThrownBy(() -> postService.update(savedId, postUpdateDto, postUpdateDto.userId()))
                    .isInstanceOf(BaseException.class)
                    .hasMessage(ErrorMessage.USER_NOT_FOUND.getMessage());
        }
    }
}
