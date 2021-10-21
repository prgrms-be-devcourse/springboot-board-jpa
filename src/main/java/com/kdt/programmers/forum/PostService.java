package com.kdt.programmers.forum;

import com.kdt.programmers.forum.domain.Post;
import com.kdt.programmers.forum.exception.PostNotFoundException;
import com.kdt.programmers.forum.transfer.PostDto;
import com.kdt.programmers.forum.transfer.request.PostRequest;
import com.kdt.programmers.forum.utils.PostConverter;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class PostService {

    private final PostJpaRepository postRepository;

    private final PostConverter postConverter;

    public PostDto savePost(PostRequest request) {
        Post post = postConverter.toPost(request);
        Post entity = postRepository.save(post);

        return postConverter.toPostDto(entity);
    }

    public PostDto findPostById(Long id) {
        return postRepository.findById(id)
            .map(postConverter::toPostDto)
            .orElseThrow(() -> new PostNotFoundException("post with id " + id + " not found"));
    }

    public Page<PostDto> findPostsByPage(Pageable pageable) {
        return postRepository.findAll(pageable)
            .map(postConverter::toPostDto);
    }

    @Transactional
    public Optional<PostDto> updatePost(Long id, PostRequest request) {
        Optional<Post> possibleEntity = postRepository.findById(id);

        if (possibleEntity.isEmpty())
            return Optional.empty();

        Post entity = possibleEntity.get();
        entity.update(request.getTitle(), request.getContent());

        return Optional.of(postConverter.toPostDto(entity));
    }
}
