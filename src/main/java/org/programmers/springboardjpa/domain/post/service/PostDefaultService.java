package org.programmers.springboardjpa.domain.post.service;

import org.programmers.springboardjpa.domain.post.domain.Post;
import org.programmers.springboardjpa.domain.post.dto.PostRequest.PostCreateRequest;
import org.programmers.springboardjpa.domain.post.dto.PostRequest.PostUpdateRequest;
import org.programmers.springboardjpa.domain.post.dto.PostResponse.PostResponseDto;
import org.programmers.springboardjpa.domain.post.exception.NotExistPostException;
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
    public PostResponseDto savePost(PostCreateRequest createRequest) {
        Post post = postRepository.save(postConverter.toPost(createRequest));
        return postConverter.toPostDto(post);
    }

    @Override
    public PostResponseDto getPost(Long id) {
        return postRepository.findById(id)
                .map(postConverter::toPostDto)
                .orElseThrow(NotExistPostException::new);
    }

    @Override
    public List<PostResponseDto> getPostList(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(postConverter::toPostDto)
                .toList();
    }

    @Override
    @Transactional
    public PostResponseDto updatePost(Long id, PostUpdateRequest updateRequest) {
        return postRepository.findById(id)
                .map(findPost -> {
                    findPost.changeTitle(updateRequest.title());
                    findPost.changeContent(updateRequest.content());
                    return findPost;
                })
                .map(postConverter::toPostDto)
                .orElseThrow(NotExistPostException::new);
    }
}
