package jehs.springbootboardjpa.service;

import jehs.springbootboardjpa.dto.PostCreateRequest;
import jehs.springbootboardjpa.dto.PostUpdateRequest;
import jehs.springbootboardjpa.entity.Post;
import jehs.springbootboardjpa.entity.User;
import jehs.springbootboardjpa.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;

    public void createPost(PostCreateRequest postCreateRequest) {
        User user = userService.getUserById(postCreateRequest.getUserId());
        postRepository.save(postCreateRequest.toEntity(user));
    }
    public Post getPostById(Long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new RuntimeException("존재하지 않는 게시글입니다."));
    }

    public Page<Post> getAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable);
    }
}
