package org.prgrms.springbootboard.service;

import org.prgrms.springbootboard.converter.PostConverter;
import org.prgrms.springbootboard.domain.Post;
import org.prgrms.springbootboard.domain.PostRepository;
import org.prgrms.springbootboard.domain.User;
import org.prgrms.springbootboard.domain.UserRepository;
import org.prgrms.springbootboard.dto.PostCreateRequest;
import org.prgrms.springbootboard.dto.PostResponse;
import org.prgrms.springbootboard.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public PostResponse createPost(PostCreateRequest request) {
        Long writerId = request.getWriterId();
        User user = userRepository.findById(writerId)
            .orElseThrow(() -> new NotFoundException("존재하지 않는 user입니다, id: " + writerId));

        Post post = PostConverter.convertCreateRequestToEntity(request, user);
        Post saved = postRepository.save(post);

        return PostConverter.convertEntityToResponse(saved);
    }
}
