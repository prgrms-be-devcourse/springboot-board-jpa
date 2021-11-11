package com.board.project.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.board.project.domain.User;
import com.board.project.dto.PostRequest;
import com.board.project.dto.PostResponse;
import com.board.project.repository.PostRepository;
import com.board.project.repository.UserRepository;
import java.util.stream.IntStream;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@SpringBootTest
@Slf4j
class PostServiceTest {
    private User user;
    private PostRequest postRequest;
    private Long userId;
    private Long postId;

    @Autowired
    PostService service;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void setUp() throws NotFoundException {
        user = User.builder()
            .name("nasangwon")
            .age(28)
            .hobby("아무것도 안하기")
            .build();

        User save = userRepository.save(user);
        userId = save.getId();

        postRequest = PostRequest.builder()
            .content("내용")
            .title("제목")
            .userId(userId)
            .build();
        postId = service.save(postRequest);
    }

    @AfterEach
    void tearUp() {

        userRepository.deleteAll();

    }

    @Test
    @DisplayName("게시글 단전조회")
    void getOnePostTest() throws NotFoundException {
        PostResponse one = service.findOne(postId);
        assertThat(one.getTitle()).isEqualTo("제목");
        assertThat(one.getUserId()).isEqualTo(userId);
    }

    @Test
    @DisplayName("게시물을 페이징 처리해서 조회 - 따로 독립적으로 실행 하여야 함.")
    void findPosts() {
        IntStream.range(1,10).mapToObj(i -> PostRequest.builder()
            .title("제목")
            .content("내용")
            .userId(userId)
            .build()).forEach(post-> {
            try {
                service.save(post);
            } catch (NotFoundException e) {
                e.printStackTrace();
            }
        });
        PageRequest pageable = PageRequest.of(0,10);

        Page<PostResponse> all = service.findAll(pageable);

        assertThat(all.getTotalPages()).isEqualTo(1);
        assertThat(all.getTotalElements()).isEqualTo(10);
    }

    @Test
    @DisplayName("게시물을 수정할 수 있다.")
    void update() throws NotFoundException {
        PostRequest updatedPost = PostRequest.builder()
            .title("제에목")
            .content("내에에용")
            .userId(userId)
            .build();

        PostResponse update = service.update(postId,updatedPost);

        assertThat(postRepository.findById(postId).get().getTitle()).isEqualTo(updatedPost.getTitle());
        assertThat(postRepository.findById(postId).get().getContent()).isEqualTo(updatedPost.getContent());
    }

    @Test
    @DisplayName("게시물을 id로 삭제할수 있다")
    void deleteOneTest() throws NotFoundException {
        service.deleteById(postId);

        assertThatThrownBy(() -> service.findOne(postId))
            .isInstanceOf(NotFoundException.class);
    }

}
