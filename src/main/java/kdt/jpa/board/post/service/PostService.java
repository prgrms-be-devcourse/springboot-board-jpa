package kdt.jpa.board.post.service;

import kdt.jpa.board.global.exception.BoardException;
import kdt.jpa.board.post.domain.Post;
import kdt.jpa.board.post.repository.PostRepository;
import kdt.jpa.board.post.service.dto.request.CreatePostRequest;
import kdt.jpa.board.post.service.dto.request.EditPostRequest;
import kdt.jpa.board.post.service.dto.response.PostListResponse;
import kdt.jpa.board.post.service.dto.response.PostResponse;
import kdt.jpa.board.post.service.utils.PostMapper;
import kdt.jpa.board.user.domain.User;
import kdt.jpa.board.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long createPost(CreatePostRequest request) {
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new BoardException("존재하지 않는 회원입니다"));
        Post post = new Post(request.title(), request.content(), user);

        return postRepository.save(post).getId();
    }

    @Transactional(readOnly = true)
    public PostResponse getById(long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new BoardException("존재하지 않는 포스트입니다"));
        return PostMapper.toPostResponse(post);
    }

    @Transactional(readOnly = true)
    public PostListResponse getPosts(Pageable pageable) {
        Page<Post> pagedPost = postRepository.findAll(pageable);
        return PostMapper.toPostListResponse(pagedPost);
    }

    @Transactional
    public boolean editPost(EditPostRequest request) {
        Post post = postRepository.findById(request.postId())
                .orElseThrow(() -> new BoardException("존재하지 않는 포스트입니다"));

        post.edit(request.title(), request.content());
        return true;
    }
}
