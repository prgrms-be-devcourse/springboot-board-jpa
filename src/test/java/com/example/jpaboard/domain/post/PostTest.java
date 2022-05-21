package com.example.jpaboard.domain.post;

import com.example.jpaboard.domain.user.User;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.transaction.annotation.Transactional;
import java.lang.reflect.Field;
import static org.assertj.core.api.Assertions.*;

@Transactional
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class PostTest {
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
            e.printStackTrace();
        }

        try {
            userId.set(user, 1L);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Nested
    class isSameWriter메서드는 {
        @Nested
        class 동일하다면 {
            @Test
            @DisplayName("true를 반환한다")
            void true를_반환한다() {
                //given
                //when
                boolean result = post.isSameWriter(user);

                //then
                assertThat(result).isTrue();
            }
        }

        @Nested
        class 동일하지_않다면 {
            @Test
            @DisplayName("false를 반환한다")
            void false를_반환한다() {
                //given
                //when
                boolean result = post.isSameWriter(new User("새로운 유저", 10, "독서"));

                //then
                assertThat(result).isFalse();
            }
        }
    }

    @Nested
    class update메서드는 {
        @Test
        @DisplayName("필드 값을 변경한다")
        void 필드_값을_변경한다() {
            //given
            String updateTitle = "제목2";
            String updateContent = "내용2";

            //when
            post.update(updateTitle, updateContent);

            //then
            assertThat(post.getTitle()).isEqualTo(updateTitle);
            assertThat(post.getContent()).isEqualTo(updateContent);
        }

        @Nested
        class 파라미터_값이_null이거나_공백_또는_빈_값이면 {
            @DisplayName("값을 변경하지 않는다")
            @ParameterizedTest
            @NullAndEmptySource
            void 값을_변경하지_않는다(String title) {
                //given
                String currentTitle = post.getTitle();
                String currentContent = post.getContent();

                //when
                post.update(title, currentContent);

                //then
                assertThat(post.getTitle()).isEqualTo(currentTitle);
                assertThat(post.getContent()).isEqualTo(currentContent);
            }
        }
    }
}
