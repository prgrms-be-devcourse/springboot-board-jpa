package com.example.board.domain.service;


import com.example.board.domain.entity.Post;
import com.example.board.domain.entity.User;
import com.example.board.domain.entity.repository.PostRepository;
import com.example.board.dto.post.PostRequestDto;
import com.example.board.dto.post.PostResponseDto;
import com.example.board.dto.post.PostWithCommentResponseDto;
import com.example.board.exception.post.NotFoundPostException;
import com.example.board.exception.post.PostAccessDeniedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;
    // 질문 2
    // 순환 참조가 발생하지 않게 조심
    //post가 user에 의존하고 있으니 userService에서는 postService를 의존하지 않는 다면 순환 참조가 발생하지 않아
    //서비스 계층에서 서비스 계층의 의존이 가능한지 여쭤보고 싶습니다.

    @Transactional
    public Long createPost(PostRequestDto requestDto) {
        User user = userService.getUser(requestDto.getUserId());
        Post post = requestDto.toEntity(user);
        return postRepository.save(post).getId();
    }

    //게시글 단건 조회
    @Transactional(readOnly = true)
    public PostResponseDto findPost(Long postId) {
        //fetch 를 통한 user 조회
        Post findPost = getPostWithUser(postId);
        return findPost.from();
    }

    //게시글 단건 조회 + 댓글
    @Transactional(readOnly = true)
    public PostWithCommentResponseDto findPostWithComment(Long postId) {
        Post findPost = getPostWithUserAndComment(postId);
        return findPost.fromWithComment();
    }

    //게시글 페이지 조회
    @Transactional(readOnly = true)
    public Page<PostResponseDto> findPostsPaged(Pageable pageable) {
        //최신순 조회
        return postRepository.findAll(pageable)
                .map(Post::from);
    }

    //게시글 수정 (dirty checking)
    @Transactional
    public void updatePost(Long postId, PostRequestDto requestDto) {
        User user = userService.getUser(requestDto.getUserId());
        Post post = getPostWithUser(postId);
        checkUserPermission(user, post);
        //dirtyChecking
        post.update(requestDto);
    }

    //게시글 삭제 (댓글까지 삭제)
    @Transactional
    public void deletePost(Long postId) {
        Post post = getPost(postId);
        postRepository.delete(post);
    }

    public Post getPost(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundPostException("아이디와 일치하는 게시글을 찾을 수 없습니다"));
    }

    private Post getPostWithUser(Long postId) {
        return postRepository.findByIdWithUser(postId)
                .orElseThrow(() -> new NotFoundPostException("아이디와 일치하는 게시글을 찾을 수 없습니다"));
    }

    private Post getPostWithUserAndComment(Long postId) {
        return postRepository.findByIdWithUserAndComments(postId)
                .orElseThrow(() -> new NotFoundPostException("아이디와 일치하는 게시글을 찾을 수 없습니다"));
    }

    private void checkUserPermission(User user, Post post) {
        if (!user.equals(post.getUser())) {
            throw new PostAccessDeniedException("해당 게시글을 수정할 권한이 없습니다");
        }
    }
}
