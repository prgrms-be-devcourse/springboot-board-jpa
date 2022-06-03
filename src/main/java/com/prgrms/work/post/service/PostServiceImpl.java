package com.prgrms.work.post.service;

import com.prgrms.work.error.PostNotFoundException;
import com.prgrms.work.post.domain.Post;
import com.prgrms.work.post.repository.PostRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService {

    private static final Logger log = LoggerFactory.getLogger(PostServiceImpl.class);

    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    @Transactional
    public Post create(Post post) {
        log.info("게시글 생성 서비스 호출 [작성자 : {}]", post.getCreatedBy());

        return postRepository.save(post);
    }

    @Override
    public List<Post> getPosts() {
        return postRepository.findAll();
    }

    @Override
    public Post getPost(Long id) {
        return postRepository.findById(id)
            .orElseThrow(PostNotFoundException::new);
    }

    @Override
    @Transactional
    public void update(Long id, String title, String content) {
        log.info("게시글 수정 서비스 호출 [게시글 ID : {}]", id);

        Post post = getPost(id);
        post.modify(title, content);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        log.info("게시글 삭제 서비스 호출 [게시글 ID : {}]", id);

        postRepository.deleteById(id);
    }

}
