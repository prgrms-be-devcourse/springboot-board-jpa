package com.prgrms.java.domain;

import com.prgrms.java.repository.PostRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceUnit;
import jakarta.validation.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.TransactionSystemException;

import java.util.Set;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PostTest {

    @DisplayName("게시판 제목은,")
    @Nested
    class title {

        @DisplayName("30글자 초과로 생성 될 수 없다.")
        @Test
        void titleOver30() {
            // when then
            assertThrows(IllegalStateException.class, () -> new Post("over the thirty characters. haha", "test test"));
        }

        @DisplayName("빈 문자열로 생성 될 수 없다.")
        @NullAndEmptySource
        @ParameterizedTest
        void titleEmpty(String invalid) {
            // when then
            assertThrows(IllegalStateException.class, () -> new Post(invalid, "test test"));
        }

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

        @DisplayName("30글자 초과로 수정할 수 없다.")
        @Test
        @Rollback(value = false)
        void editTitleOver30() {
            // given
            Post post = new Post("test", "test test");

            // when then
            assertThrows(IllegalStateException.class, () -> post.editTitle("over the thirty characters. haha"));
        }

        @DisplayName("빈 문자열로 수정할 수 없다.")
        @NullAndEmptySource
        @ParameterizedTest
        void editTitleEmpty(String invalid) {
            // given
            Post post = new Post("test", "test test");

            // when then
            assertThrows(IllegalStateException.class, () -> post.editTitle(invalid));
        }
    }


    @DisplayName("게시판 내용은,")
    @Nested
    class content {

        @DisplayName("빈 문자열로 생성 될 수 없다.")
        @NullAndEmptySource
        @ParameterizedTest
        void contentEmpty(String invalid) {
            // when then
            assertThrows(IllegalStateException.class, () -> new Post("test", invalid));
        }

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

        @DisplayName("빈 문자열로 수정될 수 없다.")
        @NullAndEmptySource
        @ParameterizedTest
        void editContentEmpty(String invalid) {
            // given
            Post post = new Post("test", "test test");

            // when then
            assertThrows(IllegalStateException.class, () -> post.editTitle(invalid));
        }
    }

}