package com.prgrms.be.app.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostConverter postConverter;

    @Transactional(readOnly = true)
    public List<Post> findAll(Pageable pageable) {
        throw new UnsupportedOperationException();
    }

    @Transactional(readOnly = true)
    public Post findById(Long id) {
        throw new UnsupportedOperationException();
    }

    @Transactional
    public Long createPost(PostDTO.CreateRequest postCreateDto) {
        User user = userRepository.findById(postCreateDto.userId())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 유저입니다."));
        Post post = postConverter.covertToPost(postCreateDto, user);

        return postRepository.save(post).getId();
    }

    @Transactional
    public Long updatePost(PostDTO.UpdateRequest postUpdateDto) {
        throw new UnsupportedOperationException();
    }
}
