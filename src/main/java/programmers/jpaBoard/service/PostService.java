package programmers.jpaBoard.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import programmers.jpaBoard.dto.PostDto;
import programmers.jpaBoard.entity.Post;
import programmers.jpaBoard.repository.PostRepository;

import java.util.NoSuchElementException;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public PostDto.Response create(PostDto.Request request) {
        Post post = new Post(request.title(), request.content());

        Post savedPost = postRepository.save(post);
        return toDto(savedPost);
    }

    public PostDto.Response getPost(Long id) {
        Post post = findPostById(id);

        return toDto(post);
    }

    public Page<PostDto.Response> getAllPost(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(this::toDto);
    }

    public PostDto.Response update(Long id, PostDto.Request request) {
        Post post = findPostById(id);
        post.updatePost(request.title(), request.content());

        return toDto(post);
    }

    private Post findPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 게시글이 없습니다"));
    }

    private PostDto.Response toDto(Post post) {
        return new PostDto.Response(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getCreatedAt()
        );
    }
}
