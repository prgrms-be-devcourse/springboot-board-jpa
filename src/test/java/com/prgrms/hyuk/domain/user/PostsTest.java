package com.prgrms.hyuk.domain.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.prgrms.hyuk.domain.post.Content;
import com.prgrms.hyuk.domain.post.Post;
import com.prgrms.hyuk.domain.post.Title;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PostsTest {

    private Posts posts;
    private Post post;

    @BeforeEach
    void setUp() {
        posts = new Posts();
        post = new Post(
            new Title("this is title..."),
            new Content("blah blah...")
        );
    }

    @Test
    @DisplayName("post 추가")
    void testAddSuccess() {
        //given
        //when
        posts.add(post);

        //then
        assertThat(posts.getAllPost()).hasSize(1);
    }

    @Test
    @DisplayName("post 삭제")
    void testRemovePost() {
        //given
        posts.add(post);

        //when
        posts.remove(post);

        //then
        assertThat(posts.getAllPost()).isEmpty();
    }

    @Test
    @DisplayName("GetAllPost 반환타입은 불변 컬렉션 따라서 수정할 수 없다.")
    void testGetAllPostAddFail() {
        //given
        posts.add(post);

        List<Post> postList = posts.getAllPost();

        Post newPost = new Post(
            new Title("this is new title..."),
            new Content("blah blah blah...")
        );

        //when, then
        assertThatThrownBy(() -> postList.add(newPost))
            .isInstanceOf(UnsupportedOperationException.class);
    }
}
