package com.devcourse.springbootboardjpa.post.service;

import com.devcourse.springbootboardjpa.post.domain.User;
import com.devcourse.springbootboardjpa.post.domain.dto.PostDTO;
import com.devcourse.springbootboardjpa.post.repository.UserRepository;
import com.devcourse.springbootboardjpa.post.util.PostConverter;
import com.devcourse.springbootboardjpa.user.domain.Post;
import com.devcourse.springbootboardjpa.user.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    private final PostConverter postConverter;

    public Long savePost(PostDTO.SaveRequest saveRequest) {

        // TODO : 비즈니스 예외 만들기
        User user = userRepository.findById(saveRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 회원입니다."));

        Post savedPost = postRepository.save(postConverter.saveRequestToPost(saveRequest, user));

        return savedPost.getId();
    }

    @Transactional(readOnly = true)
    public PostDTO.FindResponse findPost(Long id) {

        // TODO : 비즈니스 예외 만들기
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 게시글입니다."));

        return postConverter.postToFindResponse(post);
    }

    @Transactional(readOnly = true)
    public Page<PostDTO.FindResponse> findAllPostsPage(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(postConverter::postToFindResponse);
    }

    public Long updatePost(Long id, PostDTO.UpdateRequest postUpdateRequest) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 게시글입니다."));

        post.changeTitle(postUpdateRequest.getTitle());
        post.changeContent(postUpdateRequest.getContent());

        return post.getId();
    }

}
