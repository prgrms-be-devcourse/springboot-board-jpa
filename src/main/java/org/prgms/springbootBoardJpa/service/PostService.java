package org.prgms.springbootBoardJpa.service;

import org.prgms.springbootBoardJpa.domain.Post;
import org.prgms.springbootBoardJpa.domain.PostRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Transactional
    public void create(PostCreateRequest request) {
        Post post = request.toEntity();
        postRepository.save(post);
    }

    @Transactional
    public void update(Long id, PostUpdateRequest request) {
        Post post = postRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("존재하는 게시물이 없습니다."));
        post.updateTitle(request.title());
        post.updateContent(request.content());
    }

    public PostResponse findOne(Long id) {
        Post post = postRepository.findById(id)
                                .orElseThrow(() -> new IllegalArgumentException("존재하는 게시물이 없습니다."));
        return PostResponse.from(post);
    }

    public List<PostResponse> findAll(Pageable pageable) {
        return postRepository.findAll(pageable)
            .stream()
            .map(PostResponse::from)
            .toList();
    }

}
