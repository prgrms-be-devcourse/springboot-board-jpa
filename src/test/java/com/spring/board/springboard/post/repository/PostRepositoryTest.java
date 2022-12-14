package com.spring.board.springboard.post.repository;


import com.spring.board.springboard.post.domain.Post;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Test
    @DisplayName("게시물을 생성할 수 있다.")
    void create_post_test(){
        // given
        Post post = new Post();
        post.setTitle("새로운 게시물입니다.");
        post.setContent("새로운 게시물의 내용입니다.");

        // when
        postRepository.save(post);

        Integer id = post.getId();

        Post findPost = postRepository.findById(id).get();

        // then
        assertThat(post).isEqualTo(findPost);
    }

    @Test
    @DisplayName("게시물을 삭제할 수 있다.")
    void delete_post_test(){
        // given
        Post post = new Post();
        post.setTitle("삭제할 게시물");
        post.setContent("곧 삭제됩니다.");

        postRepository.save(post);

        // when
        Integer id = post.getId();
        postRepository.deleteById(id);

        // then
        Optional<Post> findPost = postRepository.findById(1);
        assertThat(findPost).isEmpty();
    }

    @Test
    @DisplayName("게시물의 제목을 수정할 수 있다.")
    void update_title_test(){
        // given
        String beforeTitle = "수정 전 제목입니다.";
        String afterTitle = "수정 후 제목입니다.";
        String content = "내용은 변하지 않습니다.";

        Post post = new Post();
        post.setTitle(beforeTitle);
        post.setContent(content);

        postRepository.save(post);

        // when
        post.changeTitle(afterTitle);

        // then
        Integer id = post.getId();
        Post findPost = postRepository.findById(id).get();
        String findTitle = findPost.getTitle();
        String findContent = findPost.getContent();

        assertThat(findTitle).isEqualTo(afterTitle);
        assertThat(findContent).isEqualTo(content);
        assertThat(post).isEqualTo(findPost);
    }

    @Test
    @DisplayName("게시물의 내용을 수정할 수 있다.")
    void update_content_test(){
        // given
        String beforeContent = "수정 전 제목입니다.";
        String afterContent = "수정 후 제목입니다.";
        String title = "제목은 변하지 않습니다.";

        Post post = new Post();
        post.setTitle(title);
        post.setContent(beforeContent);

        postRepository.save(post);

        // when
        post.changeContent(afterContent);

        // then
        Integer id = post.getId();
        Post findPost = postRepository.findById(id).get();
        String findTitle = findPost.getTitle();
        String findContent = findPost.getContent();

        assertThat(findTitle).isEqualTo(title);
        assertThat(findContent).isEqualTo(afterContent);
        assertThat(post).isEqualTo(findPost);
    }

    @Test
    @DisplayName("모든 게시물을 조회할 수 있다.")
    void find_all_test(){
        // given
        Post post1 = new Post();
        post1.setTitle("첫번째 게시물입니다.");
        post1.setContent("첫번째 게시물 내용입니다.");

        Post post2 = new Post();
        post2.setTitle("두번째 게시물입니다.");
        post2.setContent("두번째 게시물 내용입니다.");

        postRepository.saveAll(
                Lists.newArrayList(post1, post2));

        // when
        List<Post> postList = postRepository.findAll(Sort.by("id"));

        // then
        Integer expectedSize = 2;
        Integer realPostListSize = postList.size();

        Post findFirstPost = postList.get(0);
        Post findSecondPost = postList.get(1);

        assertThat(expectedSize).isEqualTo(realPostListSize);
        assertThat(findFirstPost).isEqualTo(post1);
        assertThat(findSecondPost).isEqualTo(post2);
    }

    @Test
    @DisplayName("모든 게시물을 삭제할 수 있다.")
    void find_all_delete_test(){
        // given
        Post post1 = new Post();
        post1.setTitle("첫번째 게시물입니다.");
        post1.setContent("첫번째 게시물 내용입니다.");

        Post post2 = new Post();
        post2.setTitle("두번째 게시물입니다.");
        post2.setContent("두번째 게시물 내용입니다.");

        postRepository.saveAll(
                Lists.newArrayList(post1, post2));

        // when
        postRepository.deleteAll();

        // then
        List<Post> findPostList = postRepository.findAll();

        assertThat(findPostList).isEmpty();
    }
}