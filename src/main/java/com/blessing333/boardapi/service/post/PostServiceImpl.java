package com.blessing333.boardapi.service.post;

import com.blessing333.boardapi.controller.dto.PostInformation;
import com.blessing333.boardapi.converter.PostConverter;
import com.blessing333.boardapi.entity.Post;
import com.blessing333.boardapi.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService {
    private final PostRepository repository;
    private final PostConverter converter;

    @Override
    public PostInformation loadPostById(Long id) {
        Optional<Post> foundPost = repository.findPostByIdWithMember(id);
        Post post = foundPost.orElseThrow(() -> new PostNotFoundException("post not exist"));
        return converter.fromPost(post);
    }
}
