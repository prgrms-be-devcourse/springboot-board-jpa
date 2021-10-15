package com.kdt.Board.repository;

import com.kdt.Board.dto.PostRequest;
import com.kdt.Board.entity.Post;
import com.kdt.Board.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
@ActiveProfiles("test")
class PostRepositoryTest {

    @Autowired private PostRepository postRepository;
    private Post savedPost;

    @BeforeEach
    void init() {
        Post post = Post.builder()
                .title("제목1")
                .content("내용1")
                .user(User.builder().build())
                .build();
        this.savedPost = postRepository.save(post);
    }

    @AfterEach
    void clear() {
        postRepository.deleteAll();
    }

    @Test
    void 게시글_단건_조회() {
        //given
        //when
        final Post foundPost = postRepository.findById(savedPost.getId()).get();

        //then
        Assertions.assertThat(foundPost.getTitle()).isEqualTo(savedPost.getTitle());
        Assertions.assertThat(foundPost.getContent()).isEqualTo(savedPost.getContent());
    }

    @Test
    void 게시글_다건_조회() {
        //given
        Post post2 = Post.builder()
                .title("제목2")
                .content("내용2")
                .user(User.builder().build())
                .build();
        //when
        final List<Post> postList = postRepository.findAll();
        postRepository.save(post2);
        final List<Post> postList2 = postRepository.findAll();

        //then
        assertThat(postList.size(), is(1));
        assertThat(postList2.size(), is(2));

    }

    @Test
    void 게시글_생성() {
        //given
        Post post2 = Post.builder()
                .title("제목2")
                .content("내용2")
                .user(User.builder().build())
                .build();

        //when
        final Post savedPost2 = postRepository.save(post2);

        //then
        Assertions.assertThat(savedPost2.getTitle()).isEqualTo(post2.getTitle());
        Assertions.assertThat(savedPost2.getContent()).isEqualTo(post2.getContent());
    }

    @Test
    void 게시글_수정() {
        //given
        //when
        this.savedPost.editPost(new PostRequest("수정제목2", "수정내용2"));
        final Post editedPost = postRepository.save(this.savedPost);

        //then
        Assertions.assertThat(editedPost.getTitle()).isEqualTo(savedPost.getTitle());
        Assertions.assertThat(editedPost.getContent()).isEqualTo(savedPost.getContent());
    }
}