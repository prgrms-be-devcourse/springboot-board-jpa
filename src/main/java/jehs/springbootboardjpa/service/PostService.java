package jehs.springbootboardjpa.service;

import jehs.springbootboardjpa.dto.PostCreateRequest;
import jehs.springbootboardjpa.dto.PostResponse;
import jehs.springbootboardjpa.dto.PostUpdateRequest;
import jehs.springbootboardjpa.entity.Post;
import jehs.springbootboardjpa.entity.User;
import jehs.springbootboardjpa.exception.PostErrorMessage;
import jehs.springbootboardjpa.exception.PostException;
import jehs.springbootboardjpa.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;

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
    public Page<PostResponse> getAllPostsWithUser(Pageable pageable) {
        return postRepository.findAllWithUser(pageable)
                .map(PostResponse::new);
    }
}
