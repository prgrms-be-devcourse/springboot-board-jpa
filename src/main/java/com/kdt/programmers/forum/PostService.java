package com.kdt.programmers.forum;

import com.kdt.programmers.forum.domain.Post;
import com.kdt.programmers.forum.exception.PostNotFoundException;
import com.kdt.programmers.forum.transfer.PostDto;
import com.kdt.programmers.forum.transfer.request.PostRequest;
import com.kdt.programmers.forum.utils.PostConverter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    public PostDto savePost(PostRequest postRequest) {
        Post post = postConverter.convertToPost(postRequest);
        Post entity = postRepository.save(post);
        return postConverter.convertToPostDto(entity);
    }

    public PostDto findPostById(Long postId) {
        return postRepository.findById(postId)
            .map(postConverter::convertToPostDto)
            .orElseThrow(() -> new PostNotFoundException("post with id " + postId + " not found"));
    }

    public Page<PostDto> findPostsByPage(Pageable pageable) {
        return postRepository.findAll(pageable)
            .map(postConverter::convertToPostDto);
    }

    @Transactional
    public PostDto updatePost(Long postId, PostRequest postRequest) {
        Optional<Post> possibleEntity = postRepository.findById(postId);

        if (possibleEntity.isEmpty())
            return this.savePost(postRequest);

        Post entity = possibleEntity.get();
        entity.update(postRequest.getTitle(), postRequest.getContent());
        return postConverter.convertToPostDto(entity);
    }
}
