package com.assignment.bulletinboard.post;

import com.assignment.bulletinboard.user.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@SpringBootTest
class PostTest {

    @Autowired
    PostRepository postRepository;

    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .name("찬호박")
                .hobby("야구")
                .build();

        Post post1 = Post.builder()
                .title("Post 작성 1")
                .content("이번엔 내용을 작성해보려고 합니다. 걱정을 안하셔도 되는게 내용을 작성하는건 생각보다 쉬운일입니다. 작성 하니까 생각나는 일화는데 떄는 제가 1994년 LA에 있었을 때...")
                .user(user)
                .build();

        post1.setCreatedAt(LocalDateTime.now());
        post1.setCreatedBy("admin");


        postRepository.save(post1);

        user = User.builder()
                .name("찬호박")
                .hobby("야구")
                .build();

        Post post2 = Post.builder()
                .title("Post 작성 2")
                .content("제가 코딩을 하다보면 오류에 막혀서 머리를 잡아 뜯게 되는데요 이럴 때 머리를 자꾸 잡아 뜯게 되면...")
                .user(user)
                .build();

        post2.setCreatedAt(LocalDateTime.now());
        post2.setCreatedBy("admin");

        postRepository.save(post2);


        user = User.builder()
                .name("홍길동")
                .hobby("무술")
                .build();

        Post post3 = Post.builder()
                .title("Post 작성 3")
                .content("동해번쩍 서해번쩍!")
                .user(user)
                .build();

        post3.setCreatedAt(LocalDateTime.now());
        post3.setCreatedBy("admin");

        postRepository.save(post3);
    }

//    @AfterEach
//    void tearDown() {
//        postRepository.deleteAll();
//    }

    @Test
    @DisplayName("Post 를 조회할 수 있다.")
    void read_post() {
        List<Post> userPost = postRepository.findByUserName("찬호박");
        userPost.forEach(post -> log.info(post.getContent()));

    }

    @Test
    @DisplayName("모든 Post 를 조회할 수 있다.")
    void read_AllPost() {
        List<Post> allPost = postRepository.findAll();
        log.info(String.valueOf(allPost.size()));
        allPost.forEach(post -> log.info(post.getContent()));

    }

    @Test
    @DisplayName("Post 를 수정할 수 있다.")
    void update_post() {
        Optional<Post> post1 = postRepository.findById(1L);
        post1.ifPresent(selectedPost -> System.out.println("**수정 전 Title : "+selectedPost.getTitle()));
        post1.ifPresent(selectedPost -> {
            selectedPost.changeTitle("찬호박 인터뷰");
            postRepository.save(selectedPost);
        });
        log.info("**수정 후 Title : "+postRepository.findById(1L).get().getTitle());
        List<Post> userPost = postRepository.findByUserName("찬호박");
        userPost.forEach(post -> log.info(post.getTitle()));
    }

    @Test
    @DisplayName("Post 를 삭제할 수 있다.")
    void delete_post() {
        log.info("기존의 postRepository "+String.valueOf(postRepository.findAll()));
        postRepository.deleteAll();
        log.info("삭제 후 postRepository "+String.valueOf(postRepository.findAll()));
    }

}