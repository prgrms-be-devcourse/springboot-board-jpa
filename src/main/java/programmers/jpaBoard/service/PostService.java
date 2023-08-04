package programmers.jpaBoard.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import programmers.jpaBoard.dto.PostRequest;
import programmers.jpaBoard.dto.PostResponse;
import programmers.jpaBoard.entity.Post;
import programmers.jpaBoard.repository.PostRepository;

import java.util.NoSuchElementException;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public PostResponse create(PostRequest request) {
        Post post = new Post(request.getTitle(), request.getContent(), request.getUser());

        Post savedPost = postRepository.save(post);
        return toDto(savedPost);
    }

    public PostResponse getPost(Long id) {
        Post post = findPostById(id);

        return toDto(post);
    }

    public Page<PostResponse> getAllPost(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(this::toDto);
    }

    private Post findPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 게시글이 없습니다"));
    }

    public PostResponse update(Long id, PostRequest request) {
        Post post = findPostById(id);
        post.updatePost(request.getTitle(), request.getContent());

        return toDto(post);
    }

    private PostResponse toDto(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .user(post.getUser())
                .createdAt(post.getCreatedAt())
                .build();
    }
}
