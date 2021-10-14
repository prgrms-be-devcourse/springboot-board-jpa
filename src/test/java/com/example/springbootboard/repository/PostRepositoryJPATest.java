package com.example.springbootboard.repository;

import com.example.springbootboard.domain.Post;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Slf4j
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
public class PostRepositoryJPATest {

    @Autowired
    private PostRepository repository;
    private Post newPost;

    @BeforeEach
    void setUp(){
        // 게시글 엔티티
        log.info("setup");
        newPost = new Post("안녕하세요","글입니다");
    }

    @AfterEach
    void tearDown(){
        log.info("tearDown");
        repository.deleteAll();
    }

    @Test
    @Order(1)
    @DisplayName("생성 및 조회 기능을 확인한다.")
    void POST_생성_조회() {
        //When
        repository.save(newPost);

        //Then
        Post findPost = repository.findById(newPost.getId()).get();
        log.info("created_at 비교 : {} with {}",newPost.getCreatedAt(),findPost.getCreatedAt());
        log.info("updated_at 비교 : {} with {}",newPost.getUpdatedAt(),findPost.getUpdatedAt());
        assertThat(findPost,samePropertyValuesAs(findPost, "createdAt", "updatedAt"));

    }

    @Test
    @Order(2)
    @DisplayName("수정 기능을 확인한다.")
    void POST_수정() {
        //Given
        repository.save(newPost);

        log.info("수정 전 Post 정보: Id->{}, content->{}, title->{}",newPost.getId(),newPost.getContent(),newPost.getTitle());

        //When
        newPost.setContent("바뀐 내용");
        newPost.setTitle("바뀐 제목");

        repository.save(newPost);

        log.info("수정 후 Post 정보: Id->{}, content->{}, title->{}",newPost.getId(),newPost.getContent(),newPost.getTitle());

        //Then
        Post findPost = repository.findById(newPost.getId()).get();
        assertThat(findPost,samePropertyValuesAs(findPost, "createdAt", "updatedAt"));
    }

    @Test
    @Order(3)
    @DisplayName("삭제 기능을 확인한다.")
    void POST_삭제() {
        //Given
        repository.save(newPost);
        log.info("삽입한  Post 정보: Id->{}, content->{}, title->{}",newPost.getId(),newPost.getContent(),newPost.getTitle());

        //When
        repository.delete(newPost);

        //Then
        Optional<Post> deletePost = repository.findById(newPost.getId());
        assertThat(deletePost.isEmpty(),is(true));
    }

}
