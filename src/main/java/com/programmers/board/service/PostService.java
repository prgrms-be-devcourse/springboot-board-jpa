package com.programmers.board.service;

import com.programmers.board.dto.PostDto;
import com.programmers.board.entity.Post;
import com.programmers.board.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostService {
    PostRepository postRepository;

    @Transactional
    public Long save(Post post){
        Post entity = postRepository.save(post);
        return entity.getId();
    }

    @Transactional
    public Page<Post> findAll(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    @Transactional
    public Post find(Long id) throws RuntimeException{
        return postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("데이터가 존재하지 않습니다"));
    }

}
