package com.programmers.springbootboardjpa.service;

import com.programmers.springbootboardjpa.dto.PostServiceRequestDto;
import com.programmers.springbootboardjpa.dto.PostServiceResponseDto;
import com.programmers.springbootboardjpa.entity.User;
import com.programmers.springbootboardjpa.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@DisplayName("PostService Test")
@Transactional
class PostServiceTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostService postService;

    private static final String TITLE = "테스트 제목";
    private static final String CONTENT = "테스트 내용";

    private PostServiceRequestDto requestDto;
    private User user;


    @BeforeEach
    void init() {
        user = userRepository.save(User.builder()
                .name("username")
                .age(26)
                .hobby("testHobby")
                .build());
        requestDto = PostServiceRequestDto.builder()
                .title(TITLE)
                .content(CONTENT)
                .userId(user.getUserId())
                .build();
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("포스트를 생성할 수 있다.")
    void testCreatePostSuccess() {
        // when
        PostServiceResponseDto entity = postService.create(requestDto);
        // then
        assertThat(entity.getTitle()).isEqualTo(TITLE);
        assertThat(entity.getContent()).isEqualTo(CONTENT);
        assertThat(entity.getUser().getUserId()).isEqualTo(user.getUserId());
        assertThat(entity.getUser().getName()).isEqualTo(user.getName());
        assertThat(entity.getUser().getAge()).isEqualTo(user.getAge());
        assertThat(entity.getUser().getHobby()).isEqualTo(user.getHobby());
    }

    @Test
    @DisplayName("포스트를 업데이트할 수 있다.")
    void testUpdatePostSuccess() {
        // given
        final String UPDATE_TITLE = "업데이트 테스트 제목";
        final String UPDATE_CONTENT = "업데이트 테스트 내용";
        PostServiceRequestDto updateRequestDto = PostServiceRequestDto.builder()
                .title(UPDATE_TITLE)
                .content(UPDATE_CONTENT)
                .userId(user.getUserId())
                .build();
        PostServiceResponseDto entity = postService.create(requestDto);
        // when
        postService.update(entity.getPostId(), updateRequestDto);
        entity = postService.findPostById(entity.getPostId());
        // then
        assertThat(entity.getTitle()).isEqualTo(UPDATE_TITLE);
        assertThat(entity.getContent()).isEqualTo(UPDATE_CONTENT);
        assertThat(entity.getUser().getUserId()).isEqualTo(user.getUserId());
        assertThat(entity.getUser().getName()).isEqualTo(user.getName());
        assertThat(entity.getUser().getAge()).isEqualTo(user.getAge());
        assertThat(entity.getUser().getHobby()).isEqualTo(user.getHobby());
    }

    @Test
    @DisplayName("포스트를 아이디로 조회할 수 있다.")
    void testFindById() {
        // given
        PostServiceResponseDto entity = postService.create(requestDto);
        // when
        PostServiceResponseDto findEntity = postService.findPostById(entity.getPostId());
        //then
        assertThat(findEntity.getTitle()).isEqualTo(entity.getTitle());
        assertThat(findEntity.getContent()).isEqualTo(entity.getContent());
        assertThat(findEntity.getCreatedAt()).isEqualTo(entity.getCreatedAt());
        assertThat(findEntity.getCreatedBy()).isEqualTo(entity.getCreatedBy());
        assertThat(findEntity.getUser().getUserId()).isEqualTo(entity.getUser().getUserId());
    }

    @Test
    @DisplayName("포스트를 페이징 처리를 통해 조회할 수 있다.")
    void testFindByPaging() {
        // given
        Pageable pageable = Pageable.ofSize(10)
                .withPage(0);
        PostServiceResponseDto entity = postService.create(requestDto);
        // when
        Page<PostServiceResponseDto> pageEntity = postService.findAll(pageable);
        // then
        assertThat(pageEntity.getContent()).isNotEmpty();
        assertThat(pageEntity.getContent().get(0).getPostId()).isEqualTo(entity.getPostId());
    }

}