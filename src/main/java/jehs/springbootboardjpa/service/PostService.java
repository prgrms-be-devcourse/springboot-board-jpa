package jehs.springbootboardjpa.service;

import jehs.springbootboardjpa.dto.PostCreateRequest;
import jehs.springbootboardjpa.dto.PostResponse;
import jehs.springbootboardjpa.dto.PostUpdateRequest;
import jehs.springbootboardjpa.dto.PostsResponse;
import jehs.springbootboardjpa.entity.Post;
import jehs.springbootboardjpa.entity.User;
import jehs.springbootboardjpa.exception.PostErrorMessage;
import jehs.springbootboardjpa.exception.PostException;
import jehs.springbootboardjpa.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;

    @Transactional
    public void createPost(PostCreateRequest postCreateRequest) {
        User user = userService.getUserById(postCreateRequest.getUserId());
        postRepository.save(postCreateRequest.toEntity(user));
    }

    @Transactional
    public void updatePost(Long postId, PostUpdateRequest postUpdateRequest) {
        Post post = getPostByIdWithUser(postId);
        User user = userService.getUserById(postUpdateRequest.getUserId());
        post.updatePost(postUpdateRequest, user);
    }

    @Transactional(readOnly = true)
    public PostResponse getPostResponseById(Long postId) {
        Post postById = getPostByIdWithUser(postId);
        return new PostResponse(postById);
    }

    public Post getPostByIdWithUser(Long postId) {
        return postRepository.findByIdWithUser(postId)
                .orElseThrow(() -> new PostException(PostErrorMessage.NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public PostsResponse getAllPostsWithUser(int page, int size) {
        return new PostsResponse(
                postRepository.findAllWithUser(PageRequest.of(page, size, Sort.by("id").descending()))
        );
    }

    @Transactional(readOnly = true)
    public Slice<PostResponse> getAllPostsWithUserByCursor(int size, Long cursorId) {
        return postRepository.findAllWithUserByCursor(cursorId, PageRequest.of(0, size))
                .map(PostResponse::new);
    }
}
