package com.example.board.domain.comment.service;

import com.example.board.domain.comment.dto.CommentCreateRequest;
import com.example.board.domain.comment.dto.CommentResponse;
import com.example.board.domain.comment.entity.Comment;
import com.example.board.domain.comment.repository.CommentRepository;
import com.example.board.domain.member.entity.Member;
import com.example.board.domain.member.repository.MemberRepository;
import com.example.board.domain.post.entity.Post;
import com.example.board.domain.post.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.example.board.factory.comment.CommentFactory.createComment;
import static com.example.board.factory.post.PostFactory.createPost;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @InjectMocks
    private CommentService commentService;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private PostRepository postRepository;

    private Post post = createPost();

    private Member member = post.getMember();

    private Comment comment = createComment(post, member, null);

    @Test
    void 댓글_생성_테스트() {
        // Given
        CommentCreateRequest request = new CommentCreateRequest(null, comment.getContent());
        given(memberRepository.findById(member.getId())).willReturn(Optional.of(member));
        given(postRepository.findById(post.getId())).willReturn(Optional.of(post));
        given(commentRepository.save(any())).willReturn(createComment(null));

        // When
        commentService.createComment(post.getId(), member.getId(), request);

        // Then
        verify(memberRepository, times(1)).findById(member.getId());
        verify(postRepository, times(1)).findById(post.getId());
        verify(commentRepository).save(any(Comment.class));
    }

    @Test
    void 댓글_전체_조회_테스트() {
        // Given
        given(commentRepository.findAllOrderByParentIdAscNullsFirstByPost(anyLong()))
            .willReturn(
                List.of(createComment(null), createComment(null))
            );
        given(postRepository.findById(post.getId())).willReturn(Optional.ofNullable(post));

        // When
        List<CommentResponse> result = commentService.findAllCommentsByPostId(post.getId());

        // Then
        assertThat(result.size()).isEqualTo(2);
    }
}
