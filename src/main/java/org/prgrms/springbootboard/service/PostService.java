package org.prgrms.springbootboard.service;

import org.prgrms.springbootboard.converter.PostConverter;
import org.prgrms.springbootboard.domain.Post;
import org.prgrms.springbootboard.domain.PostRepository;
import org.prgrms.springbootboard.domain.User;
import org.prgrms.springbootboard.domain.UserRepository;
import org.prgrms.springbootboard.dto.PostCreateRequest;
import org.prgrms.springbootboard.dto.PostResponse;
import org.prgrms.springbootboard.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public PostResponse createPost(PostCreateRequest request) {
        User writer = getWriter(request.getWriterId());

        Post post = PostConverter.convertCreateRequestToEntity(request, writer);
        Post saved = postRepository.save(post);

        return PostConverter.convertEntityToResponse(saved);
    }

    public PostResponse findById(Long id) {
        Post post = getPost(id);
        return PostConverter.convertEntityToResponse(post);
    }

    public Page<PostResponse> findAll(Pageable pageable) {
        return postRepository.findAll(pageable)
            .map(PostConverter::convertEntityToResponse);
    }

    public Page<PostResponse> findAllByWriter(Long writerId, Pageable pageable) {
        User writer = getWriter(writerId);

        return postRepository.findAllByWriter(writer, pageable)
            .map(PostConverter::convertEntityToResponse);
    }

    @Transactional
    public PostResponse update(Long id, String title, String content) {
        Post post = getPost(id);
        post.changeTitle(title);
        post.changeContent(content);
        return PostConverter.convertEntityToResponse(post);
    }

    @Transactional
    public void deleteById(Long id) {
        postRepository.deleteById(id);
    }

    private Post getPost(Long id) {
        return postRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("존재하지 않는 post입니다, id: " + id));
    }

    private User getWriter(Long writerId) {
        return userRepository.findById(writerId)
            .orElseThrow(() -> new NotFoundException("존재하지 않는 user입니다, id: " + writerId));
    }
}
