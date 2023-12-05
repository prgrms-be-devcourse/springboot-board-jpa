package com.example.board.domain.post.service;

import com.example.board.domain.member.entity.Member;
import com.example.board.domain.member.repository.MemberRepository;
import com.example.board.domain.post.dto.PostPageCondition;
import com.example.board.domain.post.dto.PostResponse;
import com.example.board.domain.post.dto.PostUpdateRequest;
import com.example.board.domain.post.entity.Post;
import com.example.board.domain.post.repository.PostRepository;
import com.example.board.factory.post.PostFactory;
import com.example.board.global.exception.CustomException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;
import java.util.Optional;

import static com.example.board.factory.member.MemberFactory.createMemberWithRoleUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MemberRepository memberRepository;

    private Member member;

    private Post post;

    @BeforeEach
    void setUp() {
        member = memberRepository.findById(2L).get();
        post = PostFactory.createPostWithMember(member);

        postRepository.save(post);
    }

    @AfterEach
    void tearDown() {
        postRepository.deleteAll();
    }

    @Test
    void 게시글_아이디로_조회_테스트() {
        // Given
        Long postId = post.getId();

        // When
        PostResponse findPost = postService.findPostByIdAndUpdateView(postId);

        // Then
        assertThat(findPost.title()).isEqualTo(post.getTitle());
        assertThat(findPost.content()).isEqualTo(post.getContent());
        assertThat(findPost.name()).isEqualTo(member.getName());
    }

    @Test
    void 게시글_아이디로_조회_실패_테스트() {
        // Given
        Long notExistId = 2L;

        // When & Then
        assertThatThrownBy(() -> postService.findPostByIdAndUpdateView(notExistId))
            .isInstanceOf(CustomException.class);
    }

    @Test
    void 게시글_전체_조회_테스트() {
        // Given
        PostPageCondition condition = new PostPageCondition(1, 10, null, null);
        Post newPost = Post.builder()
                .id(2L)
                .title("게시글 2번")
                .content("게시글 2번 내용")
                .member(member)
                .build();
        postRepository.save(newPost);

        // When
        Page<PostResponse> responses = postService.findPostsByCondition(condition);

        // Then
        assertThat(responses.getContent().size()).isEqualTo(2);
    }

    @Test
    void 게시글_제목으로_조회_테스트() {
        // Given
        PostPageCondition condition = new PostPageCondition(1, 10, null, "게시글 1번");
        Post newPost = Post.builder()
                .id(2L)
                .title("게시글 2번")
                .content("게시글 2번 내용")
                .member(member)
                .build();
        postRepository.save(newPost);

        // When
        Page<PostResponse> responses = postService.findPostsByCondition(condition);

        // Then
        assertThat(responses.getContent().size()).isEqualTo(1);
    }

    @Test
    void 게시글_작성자_이름으로_조회_테스트() {
        // Given
        PostPageCondition condition = new PostPageCondition(1, 10, "user", null);
        Post newPost = Post.builder()
                .id(2L)
                .title("게시글 2번")
                .content("게시글 2번 내용")
                .member(member)
                .build();
        postRepository.save(newPost);

        // When
        Page<PostResponse> responses = postService.findPostsByCondition(condition);
        List<PostResponse> content = responses.getContent();
        System.out.println(content.size());

        // Then
        assertThat(responses.getContent().size()).isEqualTo(2);
    }

    @Test
    void 게시글_수정_테스트() {
        // Given
        Long postId = post.getId();
        PostUpdateRequest request = new PostUpdateRequest("수정된 제목", "수정된 내용");

        // When
        PostResponse updatedPost = postService.updatePost(postId, request);

        // Then
        assertThat(updatedPost.title()).isEqualTo(request.title());
        assertThat(updatedPost.content()).isEqualTo(request.content());
    }

    @Test
    void 게시글_수정_실패_테스트() {
        // Given
        Long notExistId = 2L;
        PostUpdateRequest request = new PostUpdateRequest("수정된 제목", "수정된 내용");

        // When & Then
        assertThatThrownBy(() -> postService.updatePost(notExistId, request))
            .isInstanceOf(CustomException.class);
    }

    @Test
    void 게시글_아이디로_삭제_실패_테스트() {
        // Given
        Long notExistId = 2L;

        // When & Then
        assertThatThrownBy(() -> postService.deletePostById(notExistId))
                .isInstanceOf(CustomException.class);
    }

    @Test
    void 게시글_조회_동시에_100개의_요청() throws InterruptedException {
        Long postId = post.getId();
        int threadCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for(int i=0; i< threadCount; i++) {
            executorService.submit(() -> {
                try {
                    postService.findPostByIdAndUpdateView(postId);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        Post post = postRepository.findById(postId).orElseThrow();
        assertThat(post.getView()).isEqualTo(100);
    }
}
