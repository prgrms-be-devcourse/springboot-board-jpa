package com.kdt.board.repository;

import com.kdt.board.domain.Post;
import com.kdt.board.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
// note : Transactional을 해주면 rollback 설정이 true가 돼서 계속 db가 초기화됨
// question : 왜 test class 전체를 돌리면 rollback 때문에 에러나지?
@Transactional
//@Rollback(value = false)
class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    private final Long userId = 1L;
    private final Long postId = 2L;

    @AfterEach
    void tearDown() {
        postRepository.deleteAll();
    }

    @BeforeEach
    void 게시글CREATE() {
        // Given
        // note : userId를 설정해줄것인가
        User user = User.builder()
                .name("이하정")
                .build();
        userRepository.save(user);

        Post post = Post.builder()
                .title("title은 제목")
                .content("content는 내용")
                .user(user)
                .build();
        postRepository.save(post);

        // When, Then
        // TODO: 2021-10-18 findOne()이 인자로 받는 Example?
        log.info("{}이(가) 생성한 Post : {}",
                userRepository.findAll().get(0).getName(), postRepository.findAll().get(0).toString());
    }

    @Test
    void 게시글READ() {
        // Given
        Post postCreated = postRepository.findAll().get(0);
        // When
        Post postFoundById = postRepository.findById(postId).get();
        // Then
        assertEquals(postCreated, postFoundById);
    }

    @Test
    void 게시글UPDATE() {
        // Given
        Post postCreated = postRepository.findById(postId).get();
        User user = userRepository.findByName(postCreated.getUserName()).get();
        // When
        postCreated.changePost("updated title은 수정한 제목", "updated content는 수정한 내용", user);
        // Then
        log.info("수정 후 게시글 제목 : {}", postRepository.findById(postId).get().getTitle());
        log.info("수정 후 게시글 내용 : {}", postRepository.findById(postId).get().getContent());
    }

    @Test
    void 게시글DELETE() {
        // Given
        log.info("id가 2L인 post가 존재하는가? : {}", postRepository.existsById(postId));
        // When
        postRepository.deleteById(postId);
        // Then
        log.info("id가 2L인 post가 존재하는가? : {}", postRepository.existsById(postId));
    }
}