package com.programmers.board.core.post.application;

import com.programmers.board.core.post.application.dto.CreateRequestPost;
import com.programmers.board.core.post.application.dto.ResponsePost;
import com.programmers.board.core.post.application.dto.UpdateRequestPost;
import com.programmers.board.core.post.domain.Post;
import com.programmers.board.core.post.domain.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public ResponsePost save(CreateRequestPost createRequestPost){
        Post post = createRequestPost.toEntity();
        Post savedPost = postRepository.save(post);
        return ResponsePost.of(post);
    }

    @Transactional
    public ResponsePost findOne(Long id){
        return postRepository.findById(id)
                .map(ResponsePost::of)
                .orElseThrow(()-> new EntityNotFoundException("id와 일치하는 포스팅이 없습니다."));
    }

    @Transactional
    public Page<ResponsePost> findPosts(Pageable pageable){
        return postRepository.findAll(pageable)
                .map(ResponsePost::of);
    }

    @Transactional
    public ResponsePost update(Long id, UpdateRequestPost postDto){
        Post retrievedPost = postRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("id와 일치하는 포스팅이 없습니다."));

        retrievedPost.updateTitle(postDto.getTitle());
        retrievedPost.updateContent(postDto.getContent());

        return ResponsePost.of(retrievedPost);
    }

}
