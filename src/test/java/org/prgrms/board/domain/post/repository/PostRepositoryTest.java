package org.prgrms.board.domain.post.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.prgrms.board.domain.post.domain.Post;
import org.prgrms.board.domain.user.domain.Email;
import org.prgrms.board.domain.user.domain.Name;
import org.prgrms.board.domain.user.domain.Password;
import org.prgrms.board.domain.user.domain.User;
import org.prgrms.board.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TestEntityManager testEntityManager;

    User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .age(27)
                .email(new Email("dbslzld15@naver.com"))
                .name(Name.builder()
                        .firstName("우진")
                        .lastName("박")
                        .build())
                .password(new Password("abcd12345@"))
                .build();
        userRepository.save(user);
    }

    @AfterEach
    void tearDown() {
        postRepository.deleteAll();
    }

    @Test
    void 게시글을_저장할수있다(){
        //given
        Post post = Post.builder()
                .title("제목")
                .content("본문")
                .user(user)
                .build();
        //when
        Post savedPost = postRepository.save(post);
        //then
        assertThat(savedPost).usingRecursiveComparison().isEqualTo(post);
    }

    @Test
    void 저장된_게시글을_조회할수있다(){
        //given
        Post post = Post.builder()
                .title("제목")
                .content("본문")
                .user(user)
                .build();
        postRepository.save(post);
        //when
        Post findPost = postRepository.findById(post.getId()).get();
        //then
        assertThat(findPost).usingRecursiveComparison().isEqualTo(post);
    }

    @Test
    void 게시글을_작성한_사용자를_조회할수있다(){
        //given
        Post post = Post.builder()
                .title("제목")
                .content("본문")
                .user(user)
                .build();
        postRepository.save(post);
        //when
        testEntityManager.flush();
        testEntityManager.clear();
        Post findPost = postRepository.findById(post.getId()).get();
        //then
        assertThat(findPost.getUser().getId()).isEqualTo(user.getId());
    }


    @Test
    void 저장된_게시글_전부를_조회할수있다(){
        //given
        Post post1 = Post.builder()
                .title("제목1")
                .content("본문1")
                .user(user)
                .build();
        Post post2 = Post.builder()
                .title("제목2")
                .content("본문2")
                .user(user)
                .build();
        postRepository.save(post1);
        postRepository.save(post2);
        //when
        List<Post> postList = postRepository.findAll();
        //then
        assertThat(postList).contains(post1, post2);
    }

    @Test
    void 저장된_게시글을_수정할수_있다(){
        //given
        Post post = Post.builder()
                .title("제목1")
                .content("본문1")
                .user(user)
                .build();
        Post savedPost = postRepository.save(post);
        //when
        String updateTitle = "변경된 제목입니다.";
        String updateContent = "변경된 본문 내용입니다.";
        savedPost.update(updateTitle, updateContent);
        testEntityManager.flush();
        //then
        Post updatedPost = postRepository.findById(savedPost.getId()).get();
        assertThat(updatedPost.getTitle()).isEqualTo(updateTitle);
        assertThat(updatedPost.getContent()).isEqualTo(updateContent);
    }

    @Test
    void 저장된_게시글을_삭제할수_있다(){
        //given
        Post post = Post.builder()
                .title("제목1")
                .content("본문1")
                .user(user)
                .build();
        Post savedPost = postRepository.save(post);
        //when
        postRepository.deleteById(savedPost.getId());
        //then
        Optional<Post> findPost = postRepository.findById(savedPost.getId());
        assertThat(findPost).isEmpty();
    }
}