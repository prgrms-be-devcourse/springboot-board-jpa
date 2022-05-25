package com.prgrms.boardjpa.post.service;

import com.prgrms.boardjpa.domain.User;
import com.prgrms.boardjpa.post.dao.PostRepository;
import com.prgrms.boardjpa.post.dto.PostReqDto;
import com.prgrms.boardjpa.post.dto.PostResDto;
import com.prgrms.boardjpa.post.dto.PostUpdateDto;
import com.prgrms.boardjpa.user.dao.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import static org.assertj.core.api.Assertions.*;

@Slf4j
@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    private Long postId;

    private User testUser;

    @BeforeEach
    void setUp (){
        User insertUser = new User();
        insertUser.setAge(24);
        insertUser.setHobby("soccer");
        insertUser.setName("test user");
        // Augo Generate Identity로 걸어두면 id set해도 그 값으로 들어가지 않음. identity 방식 다시 공부하기

        testUser = userRepository.save(insertUser);

        PostReqDto postDto = PostReqDto.builder()
                .title("새 게시글")
                .content("새 내용")
                .userId(testUser.getId())
                .build();

        postId = postService.save(postDto).getId();
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("페이지로 게시글 조회")
    void findPosts(){
        // map으로 순회하면서 비교까지 해야 더 신뢰도 높을 듯.
        Long testId = testUser.getId();
        postService.save(new PostReqDto("제목1", "본문1", testId));
        postService.save(new PostReqDto("제목2", "본문2", testId));
        postService.save(new PostReqDto("제목3", "본문3", testId));
        postService.save(new PostReqDto("제목4", "본문4", testId));
        postService.save(new PostReqDto("제목5", "본문5", testId));
        postService.save(new PostReqDto("제목6", "본문6", testId));

        Page<PostResDto> firstPosts = postService.findPosts(PageRequest.of(0, 3));
        Page<PostResDto> lastPosts = postService.findPosts(PageRequest.of(2, 3));

        assertThat(firstPosts.getTotalElements()).isEqualTo(7);
        assertThat(firstPosts.getNumberOfElements()).isEqualTo(3);
        assertThat(firstPosts.getNumber()).isEqualTo(0);
        assertThat(firstPosts.getTotalPages()).isEqualTo(3);
        assertThat(lastPosts.getNumberOfElements()).isEqualTo(1);
    }

    @Test
    @DisplayName("게시글 단건 조회")
    void findOne(){
        PostResDto post = postService.findOne(postId);
        assertThat(post.getId()).isEqualTo(postId);
    }

    @Test
    @DisplayName("게시글 작성")
    void savePost(){
        PostReqDto postDto = PostReqDto.builder()
                .title("게시글 작성 테스트 제목")
                .content("게시글 작성 테스트 내용")
                .userId(testUser.getId())
                .build();

        PostResDto saveResult = postService.save(postDto);

        assertThat(saveResult.getTitle()).isEqualTo(postDto.getTitle());
        assertThat(saveResult.getContent()).isEqualTo(postDto.getContent());
        assertThat(saveResult.getAuthor().getId()).isEqualTo(postDto.getUserId());
    }

    @Test
    @DisplayName("게시글 수정")
    void updatePost(){
        PostUpdateDto postDto = PostUpdateDto.builder()
                .title("게시글 제목 수정")
                .content("게시글 본문 수정")
                .build();

        PostResDto updateResult = postService.update(postId, postDto);

        assertThat(updateResult.getId()).isEqualTo(postId);
        assertThat(updateResult.getTitle()).isEqualTo(postDto.getTitle());
        assertThat(updateResult.getContent()).isEqualTo(postDto.getContent());
    }
}