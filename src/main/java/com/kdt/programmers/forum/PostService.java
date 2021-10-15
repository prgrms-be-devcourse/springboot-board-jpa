package com.kdt.programmers.forum;

import com.kdt.programmers.forum.domain.Post;
import com.kdt.programmers.forum.transfer.PostDto;
import com.kdt.programmers.forum.transfer.request.PostRequest;
import com.kdt.programmers.forum.utils.PostConverter;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostService {

    private final PostJpaRepository postRepository;

    private final PostConverter postConverter;

    public PostService(PostJpaRepository postRepository, PostConverter postConverter) {
        this.postRepository = postRepository;
        this.postConverter = postConverter;
    }

    @Transactional
    public PostDto savePost(PostRequest postRequest) {
        Post post = postConverter.convertToPost(postRequest);
        Post entity = postRepository.save(post);
        return postConverter.convertToPostDto(entity);
    }

    @Transactional
    public PostDto findPostById(Long id) throws NotFoundException {
        return postRepository.findById(id)
            .map(postConverter::convertToPostDto)
            .orElseThrow(() -> new NotFoundException("post not found"));
    }

    @Transactional
    public Page<PostDto> findPostsByPage(Pageable pageable) {
        return postRepository.findAll(pageable)
            .map(postConverter::convertToPostDto);
    }

    @Transactional
    public PostDto updatePost(Long postId, PostRequest postRequest) throws NotFoundException {
        Post entity = postRepository.findById(postId)
            .orElseThrow(() -> new NotFoundException("post does not exist"));

        entity.update(postRequest.getTitle(), postRequest.getContent());
        return postConverter.convertToPostDto(entity);
    }
}
