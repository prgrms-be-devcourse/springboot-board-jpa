package com.kdt.devboard;

import com.kdt.devboard.post.Dto.PostInsertRequest;
import com.kdt.devboard.post.Dto.PostResponse;
import com.kdt.devboard.post.Dto.PostUpdateRequest;
import com.kdt.devboard.post.repository.PostRepository;
import com.kdt.devboard.post.service.PostService;
import com.kdt.devboard.user.domain.User;
import com.kdt.devboard.user.repository.UserRepository;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@SpringBootTest
@Slf4j
public class PostServiceTest {

    private User user;
    private PostInsertRequest postRequest;
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
                .name("김선호")
                .age(26)
                .hobby("아무것도 안하기")
                .build();

        User save = userRepository.save(user);
        userId = save.getId();

        postRequest = PostInsertRequest.builder()
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
    @DisplayName("게시물을 페이징 처리해서 조회")
    void findPosts() {
        IntStream.range(1,10).mapToObj(i -> PostInsertRequest.builder()
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
        PostUpdateRequest updatedPost = PostUpdateRequest.builder()
                .postId(postId)
                .title("제에목")
                .content("내에에용")
                .build();

        PostResponse update = service.update(updatedPost);

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
