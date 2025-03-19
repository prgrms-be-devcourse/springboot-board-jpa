package com.example.board.model;

import com.example.board.dto.PostCreateDto;
import com.example.board.dto.PostUpdateDto;
import com.example.board.exception.BaseException;
import com.example.board.exception.ErrorMessage;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class PostTest {

    @Mock
    User mockUser;

    static Post featurePost;

    @BeforeEach
    void setup(){
        when(mockUser.getId()).thenReturn(1L);
        when(mockUser.getName()).thenReturn("mockUser");
        featurePost = Post.from(mockUser, new PostCreateDto(mockUser.getId(), "title", "contents"));
    }
    
    @Nested
    @DisplayName("Post save 시 생성하는 객체 유효성 검사 테스트")
    class PostCreate {
        private static List<String> invalidTitle() {
            String string = "asdf";
            return List.of(string.repeat(6));
        }

        @Test
        @DisplayName("post 생성 시 정상적으로 저장을 위한 Post 객체가 생성되는 경우")
        void saveEntitySuccess() {
            // given
            PostCreateDto postDto = new PostCreateDto(mockUser.getId(), "title", "contents");

            //when
            Post post = Post.from(mockUser, postDto);

            //then
            assertThat(post.getTitle()).isEqualTo(postDto.title());
            assertThat(post.getContents()).isEqualTo(postDto.contents());
        }

        //TODO: 이 테스트를 유지하는 것이 맞는가, 너무 억지스럽게 된 것 같다.
        @Test
        @DisplayName("post 생성 시 User 객체가 null 값이라 불러올 수 없는 경우")
        void saveEntityFailWithNullUser() {
            PostCreateDto postDto = new PostCreateDto(mockUser.getId(), "title", "contents");
            mockUser = null;

            //when
            assertThatThrownBy(() ->Post.from(mockUser, postDto))
                    .isInstanceOf(BaseException.class)
                    .hasMessage(ErrorMessage.USER_NOT_FOUND.getMessage());

        }

        @ParameterizedTest(name = "title이 `{0}`인 경우 저장을 위한 객체 생성이 실패한다.")
        @ValueSource(strings = {" "})
        @MethodSource("invalidTitle")
        @NullAndEmptySource
        @DisplayName("post 생성 시 제목이 null, 빈 공백, 20자 이상인 경우 post 객체 생성에 실패한다.")
        void saveEntityFailWithInvalidTitle(String input) {
            // given
            PostCreateDto postDto = new PostCreateDto(1L, input, "test Contents");

            // when, then
            assertThatThrownBy(() -> Post.from(mockUser, postDto))
                    .isInstanceOf(BaseException.class)
                    .hasMessage(ErrorMessage.WRONG_TITLE_VALUE.getMessage());
        }

        @ParameterizedTest(name = "contents가 `{0}`인 경우 저장을 위한 객체 생성이 실패한다.")
        @ValueSource(strings = {" "})
        @NullAndEmptySource
        @DisplayName("post 생성 시 본문 내용이 null, 빈 공백인 경우 post 객체 생성에 실패한다.")
        void saveEntityFailWithInvalidContents(String input) {
            // given
            PostCreateDto postDto = new PostCreateDto(1L, "title", input);

            // when, then
            assertThatThrownBy(() -> Post.from(mockUser, postDto))
                    .isInstanceOf(BaseException.class)
                    .hasMessage(ErrorMessage.WRONG_CONTENTS_VALUE.getMessage());
        }

        @ParameterizedTest(name = "user의 이름이 `{0}`인 경우 저장을 위한 객체 생성이 실패한다.")
        @ValueSource(strings = {" "})
        @NullAndEmptySource
        @DisplayName("post 생성 시 본문 수정 시 저장하는 user의 이름이 null, 빈 공백인 경우 post 객체 생성에 실패한다.")
        void saveEntityFailWithInvalidUserName(String input) {
            // given
            when(mockUser.getName()).thenReturn(input);
            PostCreateDto postDto = new PostCreateDto(mockUser.getId(), "title", "contents");

            //when
            assertThatThrownBy(() -> Post.from(mockUser, postDto))
                    .isInstanceOf(BaseException.class)
                    .hasMessage(ErrorMessage.WRONG_USER_NAME.getMessage());
        }
    }

    @Nested
    @DisplayName("Post update 시 생성하는 객체 유효성 검사 테스트")
    class PostUpdate {
        private static List<String> invalidTitle() {
            String string = "asdf";
            return List.of(string.repeat(6));
        }

        @Test
        @DisplayName("post 수정 시 정상적으로 수정을 위한 Post 객체가 생성되는 경우")
        void saveEntitySuccess() {
            // given
            when(mockUser.getId()).thenReturn(1L);
            when(mockUser.getName()).thenReturn("mock user");

            PostCreateDto postDto = new PostCreateDto(mockUser.getId(), "title", "contents");

            Post post = Post.from(mockUser, postDto);

            PostUpdateDto updateDto = new PostUpdateDto(mockUser.getId(), "title", "contents");

            //when
            post.update(updateDto);

            //then
            assertThat(post.getTitle()).isEqualTo(updateDto.title());
            assertThat(post.getContents()).isEqualTo(updateDto.contents());
        }

        @ParameterizedTest(name = "title이 `{0}`인 경우 저장을 위한 객체 생성이 실패한다.")
        @ValueSource(strings = {" "})
        @MethodSource("invalidTitle")
        @NullAndEmptySource
        @DisplayName("post 수정 시 제목이 null, 빈 공백, 20자 이상인 경우 post 객체 생성에 실패한다.")
        void saveEntityFailWithInvalidTitle(String input) {
            // given
            PostUpdateDto updateDto = new PostUpdateDto(mockUser.getId(), input, "test Contents");

            // when, then
            assertThatThrownBy(() -> featurePost.update(updateDto))
                    .isInstanceOf(BaseException.class)
                    .hasMessage(ErrorMessage.WRONG_TITLE_VALUE.getMessage());
        }

        @ParameterizedTest(name = "contents가 `{0}`인 경우 저장을 위한 객체 생성이 실패한다.")
        @ValueSource(strings = {" "})
        @NullAndEmptySource
        @DisplayName("본문 수정 시 내용이 null, 빈 공백인 경우 post 객체 생성에 실패한다.")
        void updateEntityFailWithInvalidContents(String input) {
            // given
            PostUpdateDto postDto = new PostUpdateDto(1L, "title", input);

            // when, then
            assertThatThrownBy(() -> featurePost.update(postDto))
                    .isInstanceOf(BaseException.class)
                    .hasMessage(ErrorMessage.WRONG_CONTENTS_VALUE.getMessage());
        }

    }

}