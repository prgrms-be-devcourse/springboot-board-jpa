package com.prgrms.java.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


class PostTest {

    @DisplayName("게시판 제목은,")
    @Nested
    class title {
        @DisplayName("수정될 수 있다.")
        @Test
        void editTitle() {
            // given
            Post post = new Post("test", "test test");

            // when
            post.editTitle("edit test");

            // then
            assertThat(post.getTitle())
                    .isEqualTo("edit test");
        }

        @DisplayName("30글자를 초과할 수 없다.")
        @Test
        void editTitleOver30() {
            // given
            Post post = new Post("test", "test test");

            // when then
            assertThatThrownBy(() -> post.editTitle("over the thirty characters. haha")).isInstanceOf(IllegalStateException.class);
        }

        @DisplayName("빈 문자열이 될 수 없다.")
        @Test
        void editTitleEmpty() {
            // given
            Post post = new Post("test", "test test");

            // when then
            assertThatThrownBy(() -> post.editTitle("")).isInstanceOf(IllegalArgumentException.class);
            assertThatThrownBy(() -> post.editTitle(null)).isInstanceOf(IllegalArgumentException.class);
        }
    }


    @DisplayName("게시판 내용은,")
    @Nested
    class content {
        @DisplayName("수정될 수 있다.")
        @Test
        void editContent() {
            // given
            Post post = new Post("test", "test test");

            // when
            post.editContent("edit test test");

            // then
            assertThat(post.getContent())
                    .isEqualTo("edit test test");
        }

        @DisplayName("빈 문자열이 될 수 없다.")
        @Test
        void editContentEmpty() {
            // given
            Post post = new Post("test", "test test");

            // when then
            assertThatThrownBy(() -> post.editContent("")).isInstanceOf(IllegalArgumentException.class);
            assertThatThrownBy(() -> post.editContent(null)).isInstanceOf(IllegalArgumentException.class);
        }
    }

}