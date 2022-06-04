package org.programmers.springboardjpa.domain.post.service;

import org.programmers.springboardjpa.domain.post.dto.PostRequest.PostCreateRequestDto;
import org.programmers.springboardjpa.domain.post.dto.PostRequest.PostUpdateRequestDto;
import org.programmers.springboardjpa.domain.post.dto.PostResponse.PostResponseDto;
import org.programmers.springboardjpa.domain.post.exception.NoExistIdException;
import org.programmers.springboardjpa.domain.post.domain.Post;
import org.programmers.springboardjpa.domain.post.repository.PostRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PostDefaultService implements PostService {

    private final PostRepository postRepository;

    private final PostConverter postConverter;

    public PostDefaultService(PostRepository postRepository, PostConverter postConverter) {
        this.postRepository = postRepository;
        this.postConverter = postConverter;
    }

    @Override
    @Transactional
    public PostResponseDto savePost(PostCreateRequestDto createRequest) {
        Post post = postConverter.convertPost(createRequest);
        Post entity = postRepository.save(post);
        return postConverter.convertPostDto(entity);
    }

    @Override
    public PostResponseDto getPost(Long id) {
        return postRepository.findById(id)
                .map(postConverter::convertPostDto)
                .orElseThrow(NoExistIdException::new);
    }

    @Override
    public List<PostResponseDto> getPostList(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(postConverter::convertPostDto)
                .toList();
    }

    @Override
    @Transactional
    public PostResponseDto updatePost(Long id, PostUpdateRequestDto updateRequest) {
        Post post = postRepository.findById(id).orElseThrow(NoExistIdException::new);

        post.changeTitle(updateRequest.title());
        post.changeContent(updateRequest.content());

        return postConverter.convertPostDto(post);
    }
}
