package com.example.springbootboardjpa.model;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@Slf4j
class PostTest {

    static final String NO_TITLE = "(제목없음)";


    @Test
    @DisplayName("title 빈칸 입력시 (제목없음)으로 저장")
    public void titleBlankSave() {
        var user = new User("영지", 28);
        var post = new Post("   ", "제목없는 게시글", user);

        assertThat(post.getTitle()).isEqualTo(NO_TITLE);
    }

    @Test
    @DisplayName("title은 null이면 안된다.")
    public void titleNullTest() {
        var user = new User("영지", 28);

        assertThatThrownBy(() ->
                new Post(null, "게시글", user))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("제목은 Null 일 수 없습니다");
    }

    @Test
    @DisplayName("title 길이는 50을 넘으면 안된다.")
    public void titleOverLengthTest() {
        var user = new User("영지", 28);

        assertThatThrownBy(() ->
                new Post("012345678901234567890123456789012345678901234567891",
                        "게시글", user))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("제목은 50자 이하여야합니다.");
    }

    @Test
    @DisplayName("content는 null이면 안된다.")
    public void contentNullTest() {
        var user = new User("영지", 28);

        assertThatThrownBy(() ->
                new Post("제목", null, user))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("내용은 Null 일 수 없습니다.");
    }

    @Test
    @DisplayName("User은 null이면 안된다.")
    public void UserNullTest() {
        assertThatThrownBy(() ->
                new Post("제목", "게시글", null))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("user는 Null 일 수 없습니다.");
    }

}