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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @InjectMocks
    private PostService postService;

    @Mock
    private PostRepository postRepository;

    @Mock
    private MemberRepository memberRepository;

    private Member member = createMemberWithRoleUser();

    private Post post = PostFactory.createPostWithMember(member);

    @Test
    void 게시글_아이디로_조회_테스트() {
        // Given
        given(postRepository.findByIdWithPessimisticLock(post.getId())).willReturn(Optional.of(post));

        // When
        PostResponse findPost = postService.findPostByIdAndUpdateView(post.getId());

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

        List<Post> posts = List.of(
                post,
                newPost
        );
        Pageable pageable = PageRequest.of(condition.getPage(), condition.getSize());
        Page<Post> resultPage = PageableExecutionUtils.getPage(posts, pageable, posts::size);
        given(postRepository.findPostsByCondition(condition)).willReturn(resultPage);

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

        List<Post> posts = List.of(
                post,
                newPost
        );
        Pageable pageable = PageRequest.of(condition.getPage(), condition.getSize());
        Page<Post> resultPage = PageableExecutionUtils.getPage(List.of(posts.get(0)), pageable, posts::size);
        given(postRepository.findPostsByCondition(condition)).willReturn(resultPage);

        // When
        Page<PostResponse> responses = postService.findPostsByCondition(condition);

        // Then
        assertThat(responses.getContent().size()).isEqualTo(1);
    }

    @Test
    void 게시글_작성자_이메일로_조회_테스트() {
        // Given
        PostPageCondition condition = new PostPageCondition(1, 10, "홍길동", null);
        Post newPost = Post.builder()
                .id(2L)
                .title("게시글 2번")
                .content("게시글 2번 내용")
                .member(member)
                .build();

        List<Post> posts = List.of(
                post,
                newPost
        );
        Pageable pageable = PageRequest.of(condition.getPage(), condition.getSize());
        Page<Post> resultPage = PageableExecutionUtils.getPage(posts, pageable, posts::size);
        given(postRepository.findPostsByCondition(condition)).willReturn(resultPage);

        // When
        Page<PostResponse> responses = postService.findPostsByCondition(condition);

        // Then
        assertThat(responses.getContent().size()).isEqualTo(2);
    }

    @Test
    void 게시글_수정_테스트() {
        // Given
        PostUpdateRequest request = new PostUpdateRequest("수정된 제목", "수정된 내용");
        given(postRepository.findByIdWithPessimisticLock(post.getId())).willReturn(Optional.of(post));

        // When
        PostResponse updatedPost = postService.updatePost(post.getId(), request);

        // Then
        assertThat(updatedPost.title()).isEqualTo(request.title());
        assertThat(updatedPost.content()).isEqualTo(request.content());
    }

    @Test
    void 게시글_수정_실패_테스트() {
        // Given
        Long notExistId = 2L;
        PostUpdateRequest request = new PostUpdateRequest("수정된 제목", "수정된 내용");
        given(postRepository.findByIdWithPessimisticLock(notExistId)).willReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> postService.updatePost(notExistId, request))
            .isInstanceOf(CustomException.class);
    }

    @Test
    void 게시글_아이디로_삭제_테스트() {
        // Given
        given(postRepository.findById(post.getId())).willReturn(Optional.of(post));

        // When
        postService.deletePostById(post.getId());

        // Then
        verify(postRepository, times(1)).deleteById(post.getId());
    }

    @Test
    void 게시글_아이디로_삭제_실패_테스트() {
        // Given
        Long notExistId = 2L;
        given(postRepository.findById(notExistId)).willReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> postService.deletePostById(notExistId))
                .isInstanceOf(CustomException.class);
    }

    @Test
    void 게시글_아이디_리스트로_다건_삭제_테스트() {
        // Given
        List<Long> postIds = List.of(1L);

        // When
        postService.deletePostsByIds(postIds);

        // Then
        verify(postRepository,times(1)).deletePostsByIds(postIds);
    }

    @Test
    void 게시글_전체_삭제_테스트() {
        // Given

        // When
        postService.deleteAllPosts();

        // Then
        verify(postRepository, times(1)).deleteAll();
    }
}
