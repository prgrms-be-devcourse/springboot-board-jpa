package com.will.jpapractice.domain.post.application;

import com.will.jpapractice.domain.post.dto.PostRequest;
import com.will.jpapractice.domain.post.dto.PostResponse;
import com.will.jpapractice.domain.user.domain.User;
import com.will.jpapractice.domain.user.repository.UserRepository;
import com.will.jpapractice.global.converter.Converter;
import com.will.jpapractice.domain.post.domain.Post;
import com.will.jpapractice.domain.post.repository.PostRepository;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final Converter converter;

    @Transactional
    public Long save(Long userId, PostRequest postRequest) throws NotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("유저를 찾을 수 없습니다."));

        Post post = converter.toPost(postRequest, user);

        return postRepository.save(post).getId();
    }

    @Transactional
    public Long update(Long id, PostRequest postRequest) throws NotFoundException {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("포스트를 찾을 수 없습니다."));

        post.setContent(postRequest.getContent());
        post.setTitle(postRequest.getTitle());

        return post.getId();
    }

    public Page<PostResponse> findPosts(Pageable pageable) {
        return postRepository.findAll(pageable).map(converter::toPostResponse);
    }

    public PostResponse findPost(Long id) throws NotFoundException {
        return postRepository.findById(id)
                .map(converter::toPostResponse)
                .orElseThrow(() -> new NotFoundException("포스트를 찾을 수 없습니다."));
    }

}
