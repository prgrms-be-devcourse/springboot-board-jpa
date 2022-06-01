package com.hyunji.jpaboard.domain.post.service;

import com.hyunji.jpaboard.domain.post.domain.Post;
import com.hyunji.jpaboard.domain.post.domain.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DefaultPostService implements PostService {

    public static final int POST_PAGE_SIZE = 10;
    private final PostRepository postRepository;

    @Override
    @Transactional
    public void save(Post post) {
        postRepository.save(post);
        log.info("post saved: id={}", post.getId());
    }

    @Override
    public Page<Post> findPage(int pageNum) {
        PageRequest pageRequest = PageRequest.of(pageNum, POST_PAGE_SIZE);
        return postRepository.findPageWithUser(pageRequest);
    }

    @Override
    public Post findPostByIdWithUser(Long id) {
        return postRepository.findPostByIdWithUser(id)
                .orElseThrow(() -> new IllegalArgumentException(MessageFormat.format("post가 존재 하지 않습니다. id={0}", id)));
    }
}
