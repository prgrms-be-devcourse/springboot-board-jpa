package com.kdt.post.service;

import com.kdt.domain.post.PostRepository;
import com.kdt.post.dto.PostDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final PostConvertor postConvertor;

    @Transactional
    public Long save(PostDto postDto) {
        return postRepository.save(postConvertor.convertPostDtoToPost(postDto)).getId();
    }

}
