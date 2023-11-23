package kdt.jpa.board.post.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("[Post 테스트]")
class PostTest {

    @Test
    @DisplayName("[Post 를 수정한다]")
    void edit() {
        //given
        Post post = new Post("title", "contents", null);
        String title = "title1";
        String content = "content1";

        //when
        post.edit(title, content);

        //then
        Assertions.assertThat(post.getTitle()).isEqualTo(title);
        Assertions.assertThat(post.getContent()).isEqualTo(content);
    }
}
