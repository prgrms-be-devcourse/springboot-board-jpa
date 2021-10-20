package com.kdt.devboard.post.service;

import com.kdt.devboard.post.Dto.PostRequest;
import com.kdt.devboard.post.Dto.PostResponse;
import com.kdt.devboard.post.domain.Post;
import com.kdt.devboard.post.repository.PostRepository;
import com.kdt.devboard.user.domain.User;
import com.kdt.devboard.user.repository.UserRepository;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public Page<PostResponse> findAll(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(PostResponse::new);
    }

    @Transactional(readOnly = true)
    public PostResponse findOne(Long id) throws NotFoundException {
        return postRepository.findById(id)
                .map(PostResponse::new)
                .orElseThrow(() -> new NotFoundException("해당 게시물을 찾을 수 없습니다."));
    }

    public Long save(PostRequest postDto) throws NotFoundException {
        User user = userRepository.findById(postDto.getUserId())
                .orElseThrow(() -> new NotFoundException("해당 사용자를 찾을 수 없습니다."));

        Post post = postDto.toEntity(user);

        Post entity = postRepository.save(post);
        return entity.getId();
    }

    public PostResponse update(Long postId, PostRequest postDto) throws NotFoundException {
        Post findPost = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("해당 게시물을 찾을 수 없습니다."));
        findPost.changeInfo(postDto.getTitle(), postDto.getContent());

        return new PostResponse(findPost);
    }

    public void deleteById(Long id) throws NotFoundException {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("해당 게시물을 찾을 수 없습니다."));
        postRepository.delete(post);
    }


}
