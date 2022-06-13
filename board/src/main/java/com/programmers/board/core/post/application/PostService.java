package com.programmers.board.core.post.application;

import com.programmers.board.common.exception.NotFoundException;
import com.programmers.board.core.post.application.dto.PostCreateRequest;
import com.programmers.board.core.post.application.dto.ResponsePost;
import com.programmers.board.core.post.application.dto.PostUpdateRequest;
import com.programmers.board.core.post.domain.Post;
import com.programmers.board.core.post.domain.repository.PostRepository;
import com.programmers.board.core.user.domain.User;
import com.programmers.board.core.user.domain.repository.UserRepository;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public ResponsePost save(PostCreateRequest postCreateRequest){
        User user = userRepository.findById(postCreateRequest.getUserId())
                .orElseThrow(NotFoundException::new);

        Post post = postRepository.save(postCreateRequest.toEntity(user));
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
    public ResponsePost update(Long id, PostUpdateRequest postDto){
        Post retrievedPost = postRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("id와 일치하는 포스팅이 없습니다."));

        retrievedPost.updateTitle(postDto.getTitle());
        retrievedPost.updateContent(postDto.getContent());

        return ResponsePost.of(retrievedPost);
    }

}
