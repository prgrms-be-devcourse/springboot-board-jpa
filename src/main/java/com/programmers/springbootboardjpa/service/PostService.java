package com.programmers.springbootboardjpa.service;

import static com.programmers.springbootboardjpa.global.exception.rule.PostRule.POST_NOT_FOUND_FOR_DELETE;
import static com.programmers.springbootboardjpa.global.exception.rule.PostRule.POST_NOT_FOUND_FOR_FIND;
import static com.programmers.springbootboardjpa.global.exception.rule.PostRule.POST_NOT_FOUND_FOR_UPDATE;
import static com.programmers.springbootboardjpa.global.exception.rule.UserRule.USER_NOT_FOUND_FOR_FIND;

import com.programmers.springbootboardjpa.domain.post.Post;
import com.programmers.springbootboardjpa.domain.user.User;
import com.programmers.springbootboardjpa.dto.post.PostCreateRequest;
import com.programmers.springbootboardjpa.dto.post.PostResponse;
import com.programmers.springbootboardjpa.dto.post.PostUpdateRequest;
import com.programmers.springbootboardjpa.global.exception.PostException;
import com.programmers.springbootboardjpa.global.exception.UserException;
import com.programmers.springbootboardjpa.global.exception.rule.PostRule;
import com.programmers.springbootboardjpa.mapper.user.PostMapper;
import com.programmers.springbootboardjpa.repository.PostRepository;
import com.programmers.springbootboardjpa.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostMapper postMapper;

    @Transactional
    public void save(PostCreateRequest postCreateRequest) {
        User user = userRepository.findById(postCreateRequest.getUserId())
                .orElseThrow(() -> new UserException(USER_NOT_FOUND_FOR_FIND));

        Post post = postMapper.toPost(postCreateRequest);
        post.setUser(user);

        postRepository.save(post);
    }

    public PostResponse findById(Long id) {
        Post post = findById(id, POST_NOT_FOUND_FOR_FIND);

        return postMapper.toPostResponse(post);
    }

    public List<PostResponse> findAll(Pageable pageable) {
        return postRepository.findAllByOrderByCreatedAtDesc(pageable)
                .map(postMapper::toPostResponse)
                .toList();
    }

    @Transactional
    public void deleteById(Long id) {
        findById(id, POST_NOT_FOUND_FOR_DELETE);

        postRepository.deleteById(id);
    }

    @Transactional
    public void updateById(Long id, PostUpdateRequest postUpdateRequest) {
        Post post = findById(id, POST_NOT_FOUND_FOR_UPDATE);

        post.updateTitle(postUpdateRequest.getTitle());
        post.updateContent(postUpdateRequest.getContent());
    }

    private Post findById(Long id, PostRule postRule) {
        return postRepository.findById(id)
                .orElseThrow(() -> new PostException(postRule));
    }

}
