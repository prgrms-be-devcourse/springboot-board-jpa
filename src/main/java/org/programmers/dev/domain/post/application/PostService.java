package org.programmers.dev.domain.post.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.programmers.dev.domain.post.controller.dto.CreatePostDto;
import org.programmers.dev.domain.post.controller.dto.PostResponse;
import org.programmers.dev.domain.post.controller.dto.UpdatePostDto;
import org.programmers.dev.domain.post.domain.entity.Post;
import org.programmers.dev.domain.post.infrastructure.PostRepository;
import org.programmers.dev.exception.PostNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;

    public List<PostResponse> getAll() {
        return postRepository.findAll().stream().map(PostResponse::from).toList();
    }

    public PostResponse findById(Long id) {
        return PostResponse.from(postRepository.findById(id).orElseThrow(
            () -> new PostNotFoundException(id.toString())
        ));
    }

    @Transactional
    public Long create(CreatePostDto createPostDto) {
        Post post = createPostDto.of();
        return postRepository.save(post).getId();
    }

    @Transactional
    public Long update(Long id, UpdatePostDto updatePostDto) {
        Post post = postRepository.findById(id).orElseThrow(
            () -> new PostNotFoundException(id.toString())
        );

        post.updateTitle(updatePostDto.getTitle());
        post.updateContent(updatePostDto.getContent());
        return id;
    }

}
