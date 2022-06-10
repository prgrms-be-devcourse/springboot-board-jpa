package com.prgrms.work.post.service;

import com.prgrms.work.error.PostNotFoundException;
import com.prgrms.work.post.domain.Post;
import com.prgrms.work.post.repository.PostRepository;
import java.text.MessageFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Page<Post> getPosts(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    @Override
    public Post getPost(Long id) {
        return postRepository.findById(id)
            .orElseThrow(() -> new PostNotFoundException(
                    MessageFormat.format("게시글을 찾을 수 없습니다. [요청한 아이디 : {0}]", id)
                )
            );
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
