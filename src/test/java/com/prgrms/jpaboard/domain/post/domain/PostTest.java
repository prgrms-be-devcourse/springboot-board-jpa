package com.prgrms.jpaboard.domain.post.domain;

import com.prgrms.jpaboard.domain.post.dto.request.PostUpdateDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PostTest {
    private Post post;
    private PostUpdateDto postUpdateDto;

    @BeforeEach
    void setUp() {
        post = Post.builder()
                .title("test title")
                .content("this is content of test")
                .build();

        postUpdateDto =
                new PostUpdateDto("수정된 제목", "수정된 내용");
    }

    @Test
    @DisplayName("게시물 제목 수정 테스트")
    void testUpdateTitle() {
        post.updateTitle(postUpdateDto.getTitle());

        assertThat(post.getTitle()).isEqualTo(postUpdateDto.getTitle());
    }

    @Test
    @DisplayName("게시물 본문 수정 테스트")
    void testUpdateContent() {
        post.updateContent(postUpdateDto.getContent());

        assertThat(post.getContent()).isEqualTo(postUpdateDto.getContent());
    }
}