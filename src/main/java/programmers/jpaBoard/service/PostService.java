package programmers.jpaBoard.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import programmers.jpaBoard.dto.PostRequest;
import programmers.jpaBoard.dto.PostResponse;
import programmers.jpaBoard.entity.Post;
import programmers.jpaBoard.repository.PostRepository;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public PostResponse create(PostRequest request) {
        Post post = new Post(request.getTitle(), request.getContent(), request.getUser());

        Post savedPost = postRepository.save(post);
        return toDto(savedPost);
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
