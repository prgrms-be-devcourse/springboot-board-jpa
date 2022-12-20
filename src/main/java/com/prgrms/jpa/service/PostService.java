package com.prgrms.jpa.service;

import com.prgrms.jpa.controller.dto.post.request.CreatePostRequest;
import com.prgrms.jpa.controller.dto.post.request.FindAllPostRequest;
import com.prgrms.jpa.controller.dto.post.request.UpdatePostRequest;
import com.prgrms.jpa.domain.Post;
import com.prgrms.jpa.exception.EntityNotFoundException;
import com.prgrms.jpa.exception.ExceptionMessage;
import com.prgrms.jpa.repository.PostRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.prgrms.jpa.utils.PostEntityDtoMapper.toPageable;
import static com.prgrms.jpa.utils.PostEntityDtoMapper.toPost;

@Service
public class PostService {

    private static final String POST = "게시글";

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Transactional
    public Post create(CreatePostRequest createPostRequest) {
        return postRepository.save(toPost(createPostRequest));
    }

    @Transactional(readOnly = true)
    public List<Post> findAll(FindAllPostRequest findAllPostRequest) {
        Pageable pageable = toPageable(findAllPostRequest.getSize());
        if (findAllPostRequest.getPostId() == null) {
            return postRepository.findAllByOrderByCreatedAtDesc(pageable);
        }
         return postRepository.findByIdLessThanOrderByIdDescCreatedAtDesc(findAllPostRequest.getPostId(), pageable);
    }

    @Transactional(readOnly = true)
    public Post getById(long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(ExceptionMessage.ENTITY_NOT_FOUND.name(), POST)));
    }

    @Transactional
    public void update(Post post, UpdatePostRequest updatePostRequest) {
        post.change(updatePostRequest.getTitle(), updatePostRequest.getContent());
    }
}
