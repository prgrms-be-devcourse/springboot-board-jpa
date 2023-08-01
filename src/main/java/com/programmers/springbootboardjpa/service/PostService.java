package com.programmers.springbootboardjpa.service;

import com.programmers.springbootboardjpa.domain.post.Post;
import com.programmers.springbootboardjpa.domain.user.User;
import com.programmers.springbootboardjpa.dto.post.PostCreateRequest;
import com.programmers.springbootboardjpa.dto.post.PostResponse;
import com.programmers.springbootboardjpa.dto.post.PostUpdateRequest;
import com.programmers.springbootboardjpa.mapper.user.PostMapper;
import com.programmers.springbootboardjpa.repository.PostRepository;
import com.programmers.springbootboardjpa.repository.UserRepository;
import java.util.List;
import java.util.NoSuchElementException;
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
                .orElseThrow(() -> new NoSuchElementException("게시글을 저장하기 위해 작성자의 ID를 조회해보았으나 해당 아이디는 없는 아이디입니다."));

        Post post = postMapper.toPost(postCreateRequest);
        post.setUser(user);
        post.setCreatedBy(user.getName());

        postRepository.save(post);
    }

    public PostResponse findById(Long id) {
        Post post = findById(id, "찾는 게시글이 없습니다.");

        return postMapper.toPostResponse(post);
    }

    public List<PostResponse> findAll(Pageable pageable) {
        return postRepository.findAllByOrderByCreatedAtDesc(pageable)
                .map(postMapper::toPostResponse)
                .toList();
    }

    @Transactional
    public void deleteById(Long id) {
        findById(id, "삭제하려는 게시글이 없습니다.");

        postRepository.deleteById(id);
    }

    @Transactional
    public void updateById(Long id, PostUpdateRequest postUpdateRequest) {
        Post post = findById(id, "업데이트하려는 게시글이 없습니다.");

        post.updateTitle(postUpdateRequest.getTitle());
        post.updateContent(postUpdateRequest.getContent());
    }

    private Post findById(Long id, String exceptionMessage) {
        return postRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(exceptionMessage));
    }

}
