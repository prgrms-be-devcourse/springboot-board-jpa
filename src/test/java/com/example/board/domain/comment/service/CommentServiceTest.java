package com.example.board.domain.comment.service;

import com.example.board.domain.comment.dto.CommentCreateRequest;
import com.example.board.domain.comment.dto.CommentResponse;
import com.example.board.domain.comment.dto.CommentUpdateRequest;
import com.example.board.domain.comment.entity.Comment;
import com.example.board.domain.comment.repository.CommentRepository;
import com.example.board.domain.member.entity.Member;
import com.example.board.domain.member.repository.MemberRepository;
import com.example.board.domain.post.entity.Post;
import com.example.board.domain.post.repository.PostRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.example.board.factory.comment.CommentFactory.createComment;
import static com.example.board.factory.post.PostFactory.createPost;
import static com.example.board.factory.post.PostFactory.createPostWithMember;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class CommentServiceTest {

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PostRepository postRepository;

    private Post post;

    private Member member;

    private Comment comment;

    @BeforeEach
    void setUp() {
        member = memberRepository.findById(2L).get();
        post = createPostWithMember(member);
        comment = createComment(post, member, null);

        post = postRepository.save(post);
        comment = commentRepository.save(comment);
    }

    @AfterEach
    void tearDown() {
        postRepository.deleteAll();
        commentRepository.deleteAll();
    }

    @Test
    void 댓글_생성_테스트() {
        // Given
        CommentCreateRequest request = new CommentCreateRequest(null, comment.getContent());

        // When
        CommentResponse result = commentService.createComment(post.getId(), member.getId(), request);

        // Then
        assertThat(result.getContent()).isEqualTo(request.content());
    }

    @Test
    void 댓글_전체_조회_테스트() {
        // Given
        Long postId = post.getId();
        Comment comment2 = commentRepository.save(createComment(post, member, comment));
        Comment comment3 = commentRepository.save(createComment(post, member, comment));
        Comment comment4 = commentRepository.save(createComment(post, member, comment2));
        Comment comment5 = commentRepository.save(createComment(post, member, comment));

        // When
        List<CommentResponse> result = commentService.findAllCommentsByPostId(postId);
        System.out.println(result.size());

        // Then
        assertThat(result.get(0).getChildren().size()).isEqualTo(3);
        assertThat(result.get(0).getChildren().get(0).getChildren().get(0).getCommentId()).isEqualTo(comment4.getId());
    }

    @Test
    void 댓글_수정_테스트() {
        // Given
        Long commentId = comment.getId();
        CommentUpdateRequest commentUpdateRequest = new CommentUpdateRequest("수정된 내용");

        // When
        CommentResponse commentResponse = commentService.updateComment(commentId, commentUpdateRequest);

        // Then
        assertThat(commentResponse.getContent()).isEqualTo(commentUpdateRequest.content());
    }
}
