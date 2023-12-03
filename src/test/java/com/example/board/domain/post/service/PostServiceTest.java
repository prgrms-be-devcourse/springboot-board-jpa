package com.example.board.domain.post.service;

import com.example.board.domain.member.entity.Member;
import com.example.board.domain.member.repository.MemberRepository;
import com.example.board.domain.post.dto.PostCreateRequest;
import com.example.board.domain.post.dto.PostPageCondition;
import com.example.board.domain.post.dto.PostResponse;
import com.example.board.domain.post.dto.PostUpdateRequest;
import com.example.board.domain.post.repository.PostRepository;
import com.example.board.global.exception.BusinessException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MemberRepository memberRepository;

    private Member member;

    private PostCreateRequest postCreateRequest = new PostCreateRequest("제목1", "내용1");

    @BeforeEach
    void setUp() {
        member = new Member("test@gmail.com", "홍길동", "test1234!", 22, "배드민턴");
        memberRepository.save(member);
    }

    @AfterEach
    void tearDown() {
        postRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    void 게시글_생성_테스트() {
        // Given

        // When
        PostResponse savedPost = postService.createPost(member.getEmail(), postCreateRequest);

        // Then
        assertThat(savedPost.title()).isEqualTo(postCreateRequest.title());
        assertThat(savedPost.content()).isEqualTo(postCreateRequest.content());
        assertThat(savedPost.name()).isEqualTo(member.getName());
    }

    @Test
    void 게시글_생성_실패_테스트() {
        // Given
        String notExistEmail = "notExist123@gmail.com";

        // When & Then
        assertThatThrownBy(() -> postService.createPost(notExistEmail, postCreateRequest))
            .isInstanceOf(BusinessException.class);
    }

    @Test
    void 게시글_아이디로_조회_테스트() {
        // Given
        PostResponse savedPost = postService.createPost(member.getEmail(), postCreateRequest);

        // When
        PostResponse findPost = postService.findPostById(savedPost.id());

        // Then
        assertThat(savedPost.title()).isEqualTo(findPost.title());
        assertThat(savedPost.content()).isEqualTo(findPost.content());
        assertThat(savedPost.name()).isEqualTo(findPost.name());
    }

    @Test
    void 게시글_아이디로_조회_실패_테스트() {
        // Given
        Long notExistId = 2L;

        // When & Then
        assertThatThrownBy(() -> postService.findPostById(notExistId))
            .isInstanceOf(BusinessException.class);
    }

    @Test
    void 게시글_전체_조회_테스트() {
        // Given
        postService.createPost(member.getEmail(), postCreateRequest);
        PostPageCondition condition = PostPageCondition.builder()
            .build();

        // When
        Page<PostResponse> posts = postService.findPostsByCondition(condition);

        // Then
        assertThat(posts.getTotalElements()).isEqualTo(1);
        assertThat(posts.getContent().get(0).title()).isEqualTo(postCreateRequest.title());
        assertThat(posts.getContent().get(0).content()).isEqualTo(postCreateRequest.content());
    }

    @Test
    void 게시글_제목으로_조회_테스트() {
        // Given
        PostCreateRequest request = new PostCreateRequest("제목2", "내용2");
        postService.createPost(member.getEmail(), postCreateRequest);
        postService.createPost(member.getEmail(), request);
        PostPageCondition condition = PostPageCondition.builder()
            .title("제목1")
            .build();

        // When
        Page<PostResponse> posts = postService.findPostsByCondition(condition);

        // Then
        assertThat(posts.getTotalElements()).isEqualTo(1);
        assertThat(posts.getContent().get(0).title()).isEqualTo(postCreateRequest.title());
        assertThat(posts.getContent().get(0).content()).isEqualTo(postCreateRequest.content());
    }

    @Test
    void 게시글_작성자_이메일로_조회_테스트() {
        // Given
        postService.createPost(member.getEmail(), postCreateRequest);
        PostPageCondition condition = PostPageCondition.builder()
            .email("test@gmail.com")
            .build();

        // When
        Page<PostResponse> posts = postService.findPostsByCondition(condition);

        // Then
        assertThat(posts.getTotalElements()).isEqualTo(1);
        assertThat(posts.getContent().get(0).title()).isEqualTo(postCreateRequest.title());
        assertThat(posts.getContent().get(0).content()).isEqualTo(postCreateRequest.content());
    }

    @Test
    void 게시글_수정_테스트() {
        // Given
        PostResponse originPost = postService.createPost(member.getEmail(), postCreateRequest);
        PostUpdateRequest request = new PostUpdateRequest("수정된 제목", "수정된 내용");

        // When
        PostResponse updatedPost = postService.updatePost(originPost.id(), member.getEmail(), request);

        // Then
        assertThat(updatedPost.title()).isEqualTo(request.title());
        assertThat(updatedPost.content()).isEqualTo(request.content());
    }

    @Test
    void 게시글_수정_실패_테스트() {
        // Given
        String notExistEmail = "notExist123@gmail.com";
        PostResponse originPost = postService.createPost(member.getEmail(), postCreateRequest);
        PostUpdateRequest request = new PostUpdateRequest("수정된 제목", "수정된 내용");

        // When & Then
        assertThatThrownBy(() -> postService.updatePost(originPost.id(), notExistEmail, request))
            .isInstanceOf(BusinessException.class);
    }

    @Test
    void 게시글_아이디로_삭제_테스트() {
        // Given
        PostResponse originPost = postService.createPost(member.getEmail(), postCreateRequest);

        // When
        postService.deletePostById(originPost.id(), member.getEmail());

        // Then
        assertThat(postRepository.findAll()).hasSize(0);
    }

    @Test
    void 게시글_아이디로_삭제_실패_테스트() {
        // Given
        Long notExistId = 2L;

        // When & Then
        assertThatThrownBy(() -> postService.deletePostById(notExistId, member.getEmail()))
                .isInstanceOf(BusinessException.class);
    }

    @Test
    void 게시글_전체_삭제_테스트() {
        // Given
        postService.createPost(member.getEmail(), postCreateRequest);

        // When
        postService.deleteAllPosts();

        // Then
        assertThat(postRepository.findAll()).hasSize(0);
    }
}
