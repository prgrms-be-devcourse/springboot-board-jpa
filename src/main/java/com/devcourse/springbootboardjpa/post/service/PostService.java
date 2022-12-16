package com.devcourse.springbootboardjpa.post.service;

import com.devcourse.springbootboardjpa.post.domain.dto.PostDTO;
import com.devcourse.springbootboardjpa.post.util.PostConverter;
import com.devcourse.springbootboardjpa.user.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final PostConverter postConverter;

    @Transactional(readOnly = true)
    public Page<PostDTO.FindResponse> findAllPostsPage(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(postConverter::postToFindResponse);
    }
}
