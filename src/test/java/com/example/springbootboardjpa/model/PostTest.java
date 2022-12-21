package com.example.springbootboardjpa.model;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class PostTest {

    static final String NO_TITLE = "(제목없음)";

    @Test
    @DisplayName("title 빈칸 입력시 (제목없음) 저장")
    public void titleBlankSave(){
        var user = new User("영지",28);
        var post = new Post("   ", "제목없는 게시글", user );

       assertThat(post.getTitle()).isEqualTo(NO_TITLE);
    }

}