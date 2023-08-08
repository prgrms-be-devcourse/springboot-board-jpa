package com.ray.springbootboard.service;

import com.ray.springbootboard.domain.Post;
import com.ray.springbootboard.repository.PostRepository;
import com.ray.springbootboard.service.vo.PostUpdateInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Override
    @Transactional
    public Long save(Post post) {
        return postRepository.save(post).getId();
    }

    @Override
    public Post getById(Long id) {
        return getPostById(id);
    }

    @Override
    public Page<Post> findAllWithPage(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public Long update(PostUpdateInfo info) {
        Post post = getPostById(info.id());
        post.update(info.title(), info.content());

        return post.getId();
    }

    private Post getPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 아이디를 가진 포스트가 없습니다"));
    }
}
